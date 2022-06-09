package org.xoridor.cp;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;
import org.xoridor.core.FencePosition;
import org.xoridor.core.IllegalGameStateException;
import org.xoridor.core.InvalidFenceException;
import org.xoridor.core.InvalidMoveException;
import org.xoridor.core.Player;

public abstract class AbstractComputerPlayer implements ComputerPlayer {

    public abstract void nextMove(Board board);

    protected static final int MAX = Integer.MAX_VALUE / 2;

    protected static final int MIN = Integer.MIN_VALUE / 2;
    
    protected PathFinder pathFinder = null;

    protected NakedBoard state = null;

    protected static class Move {
        public Move(FencePosition p) {
            newFencePosition = p;
        }
        public Move(BoardPosition b) {
            newPlayerPosition = b;
        }
        public Move(int v) {
            value = v;
        }
        public boolean isValid() {
            return newFencePosition != null || newPlayerPosition != null;
        }
        public FencePosition newFencePosition = null;
        public BoardPosition newPlayerPosition = null;
        public int value;
        public String toString() {
            String s = "";
            if (newPlayerPosition != null)
                s += value + " player to " + newPlayerPosition;
            else if (newFencePosition != null)
                s += value + " fence " + newFencePosition;
            return s;                                                              
        }
    }

    protected void applyRealMove(Player player, Move bestMove, Board board) {
        try {
            if (bestMove.newPlayerPosition != null)
                board.setComputerPosition(player, bestMove.newPlayerPosition);
            else
                board.addComputerFence(player, bestMove.newFencePosition);
        }
        catch (InvalidMoveException me) {
            me.printStackTrace();
        }
        catch (InvalidFenceException fe) {
            fe.printStackTrace();
        }        
        catch (IllegalGameStateException se) {
            se.printStackTrace();
        }
    }

    /** apply the move to current state, returns true when game over */
    protected boolean doMove(Player player, Move move) {
        if (move.newPlayerPosition != null)
            return state.move(player, move.newPlayerPosition);
        else 
            state.addFence(player, move.newFencePosition);
        return false;
    }

    /** undo the move from current state, returns false */
    protected boolean undoMove(Player player, Move move, BoardPosition oldPosition) {
        if (move.newPlayerPosition != null)
            state.move(player, oldPosition);
        else 
            state.removeFence(player, move.newFencePosition);
        return false;
    }

    /** calculates current distance to goal for player */
    protected int distanceToGoal(Player player) {
        BoardPosition curPos = state.getPosition(player);
        int cost = pathFinder.findShortestPath(curPos.getY(), curPos.getX(), player.getGoalRow()).getCost();
        return cost;
    }

    /** gets the player different from p */
    protected Player getOtherPlayer(Board board, Player p) {
        for (Player other: board.getGame().getPlayers()) 
            if (p != other) 
                return other;
        return null;
    }
          
}
