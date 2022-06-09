package org.xoridor.cp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;
import org.xoridor.core.FencePosition;
import org.xoridor.core.InvalidBoardPositionException;
import org.xoridor.core.Player;

public class MinMaxComputerPlayer 
extends AbstractComputerPlayer
implements ComputerPlayer {

    private final int lookAheadLevel;

    private boolean useAlphaBetaPruning = true;
    
    private Player maxPlayer;

    private Player minPlayer;

    private int alpha;

    private int beta;

    private Random rg;

    public MinMaxComputerPlayer(int lookAheadLevel) {
        this.lookAheadLevel = lookAheadLevel;
        this.rg = new Random();
    }

    public void nextMove(Board board) {
        this.state = new NakedBoard(board);
        this.pathFinder = new PathFinder(state);
        this.maxPlayer = board.getGame().getActivePlayer();
        this.minPlayer = getOtherPlayer(board, maxPlayer);
        alpha = MIN; // holds the best (highest) max move value        
        beta = MAX; // holds the best (lowest) min move value        
        Move bestMove = maxMove(lookAheadLevel, false);
        if (! bestMove.isValid()) {
            // do arbitrary move
            bestMove = anyMove();
        }
        applyRealMove(maxPlayer, bestMove, board);
    }        
    
    private boolean useAlphaBetaPruning() {
        return useAlphaBetaPruning && alpha != MIN && beta != MAX;
    }

    private Move maxMove (int level, boolean gameEnded) {
        --level;
        if (gameEnded || level <= 0) {
            return evalGameState();
        }
        else {
            List<Move> moves = generateMoves(true);
            List<Move> best = new ArrayList<Move>();
            int bestValue = MIN;
            for (Move move: moves) {
                BoardPosition oldPosition = state.getPosition(maxPlayer);
                gameEnded = doMove(maxPlayer, move);
                Move newMove = minMove(level, gameEnded);
                gameEnded = undoMove(maxPlayer, move, oldPosition);
                if (newMove.value > bestValue) {
                    best.clear();
                    move.value = alpha = bestValue = newMove.value;
                    best.add(move);
                }      
                else if (newMove.value == bestValue) {
                    move.value = bestValue;
                    best.add(move);
                }
                if (useAlphaBetaPruning() && beta > alpha)
                    break;
            }
            return best.get(rg.nextInt(best.size()));
        }
    }

    private Move minMove (int level, boolean gameEnded) {
        --level;
        if (gameEnded || level <= 0) {
            return evalGameState();
        }
        else {
            List<Move> moves = generateMoves(false);
            List<Move> best = new ArrayList<Move>();
            int bestValue = MAX;
            for (Move move: moves) {
                BoardPosition oldPosition = state.getPosition(minPlayer);
                gameEnded = doMove(minPlayer, move);
                Move newMove = maxMove(level, gameEnded);
                gameEnded = undoMove(minPlayer, move, oldPosition);
                if (newMove.value < bestValue) {
                    best.clear();
                    move.value = beta = bestValue = newMove.value;
                    best.add(move);
                }
                else if (newMove.value == bestValue) {
                    move.value = bestValue;
                    best.add(move);
                }
                if (useAlphaBetaPruning() && beta < alpha)
                    break;
            }
            return best.get(rg.nextInt(best.size()));
        }
    }               

    /** make an evaluation of the current state of the game,
     * Move.value should be as high as possible for maxPlayer,
     * and as low as possible for minPlayer.
     */
    private Move evalGameState() {
        BoardPosition curMaxPos = state.getPosition(maxPlayer);
        PathFinder.Path maxPlayerPath = pathFinder.findShortestPath(curMaxPos.getY(), curMaxPos.getX(), maxPlayer.getGoalRow());
        BoardPosition curMinPos = state.getPosition(minPlayer);
        PathFinder.Path minPlayerPath = pathFinder.findShortestPath(curMinPos.getY(), curMinPos.getX(), minPlayer.getGoalRow());
        int shortestDistanceMaxPlayer = maxPlayerPath.getCost(); 
        int shortestDistanceMinPlayer = minPlayerPath.getCost();         
        int fencesLeftMaxPlayer = state.getNbAllowedFences(maxPlayer);
        int fencesLeftMinPlayer = state.getNbAllowedFences(minPlayer);
        int value = 11*(shortestDistanceMinPlayer - shortestDistanceMaxPlayer) - fencesLeftMinPlayer + fencesLeftMaxPlayer;        
        if (shortestDistanceMaxPlayer == 0)
            value = MAX/2;
        else if (shortestDistanceMinPlayer == 0)
            value = MIN/2;
        return new Move(value);
    }

    private Move anyMove() {
        BoardPosition curPosition = state.getPosition(maxPlayer);
        // add all possible player moves which does not make player's path longer
        for (int x = -2 ; x <= 2 ; x++)
            for (int y = -2 ; y <= 2 ; y++)
                if (absSum1or2(x,y))
                    try {
                        BoardPosition bp = new BoardPosition(state, curPosition.getX()+x, curPosition.getY()+y);
                        if (state.isValidMove(maxPlayer, bp)) {                                                                
                            return new Move(bp);                                                                     
                        }
                    } catch(InvalidBoardPositionException ie) {}
        return null;
    }

    /** Gets all possible intelligent moves: 
     *
     *  We try all player moves => 4 moves on average, 
     *  but moving the player will only be allowed if it is moved to a place which immediately shortens the shortest path,
     *  otherwise that search tree is no further investigated.
     *
     *  We try all fences, so at most 128 possible fences, but some rules will avoid stupid fences:
     *  1) only fences near a player OR which is near already placed fences => this will limit the branching factor a lot.
     *  2) fences which make the difference between your shortest path and his shortest path worse,
     *     will be no further inverstigated.
     *  These simple rules will make it possible to look ahead further in the tree.
     *
     */
    private List<Move> generateMoves(boolean maxMove) {
        Player player = maxMove ? maxPlayer : minPlayer;
        ArrayList<Move> list = new ArrayList<Move>();
        BoardPosition curPosition = state.getPosition(player);
        // calculate distance to goal
        int goalDistance = distanceToGoal(player);        
        int goalDistanceMax;
        int goalDistanceMin;
        if (maxMove) {
            goalDistanceMax = goalDistance;
            goalDistanceMin = distanceToGoal(minPlayer);
        }
        else {
            goalDistanceMax = distanceToGoal(maxPlayer); 
            goalDistanceMin = goalDistance;
        }        
        // add all possible player moves which does not make player's path longer
        for (int x = -2 ; x <= 2 ; x++)
            for (int y = -2 ; y <= 2 ; y++)
                if (absSum1or2(x,y))
                    try {
                        BoardPosition bp = new BoardPosition(state, curPosition.getX()+x, curPosition.getY()+y);
                        if (state.isValidMove(player, bp)) {        
                            state.move(player, bp);
                            if (distanceToGoal(player) <= goalDistance) {
                                list.add(new Move(bp));                  
                            }                                                                  
                            state.move(player, curPosition);                                                
                        }
                    } catch(InvalidBoardPositionException ie) {}
        // add some intelligent possible fences
        for (FencePosition fence: state.getAllFencePositions()) {            
            if (state.isValidFence(player, fence)) {
                // only add when near to a fence or player
                if (state.closeToFence(fence) || state.closeToPlayer(fence)) {
                    // check that fence is not immediately counterproductive: difference in shortest paths
                    state.addFence(player, fence);
                    int value = goalDistanceMax-distanceToGoal(maxPlayer) + distanceToGoal(minPlayer) - goalDistanceMin;
                    if (! maxMove)
                        value = -value;
                    if (value > 0)
                        list.add(new Move(fence));                    
                    state.removeFence(player, fence);
                }
            }
        }
        return list;
    }

    private int abs(int val) {
        return val >= 0 ? val:-val;
    }

    private int absSum(int x, int y) {
        return abs(x)+abs(y);
    }

    private boolean absSum1or2(int x, int y) {
        int dif = absSum(x,y);
        return dif == 1 || dif == 2;
    }

}
