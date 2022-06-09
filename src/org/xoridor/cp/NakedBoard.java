package org.xoridor.cp;

import java.util.LinkedList;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;
import org.xoridor.core.FencePosition;
import org.xoridor.core.Player;

public class NakedBoard extends Board {
    
    private boolean [][] horFence;
    private boolean [][] verFence;

    private void set(FencePosition fp, boolean add) {
        if (fp.getDirection() == FencePosition.VERTICAL) {
            verFence[fp.getY()][fp.getX()] = add;
            verFence[fp.getY()+1][fp.getX()] = add;
        } 
        else {
            horFence[fp.getY()][fp.getX()] = add;
            horFence[fp.getY()][fp.getX()+1] = add;
        }
    }

    private Board board;

    public NakedBoard(Board board) {
        super(board.getGame());
        this.board = board;
        fps.clear();
        int dim = board.getNbRows();
        horFence = new boolean[dim][dim];
        verFence = new boolean[dim][dim];
        for (FencePosition fp: board.getFences()) {
            fps.add(fp);    
            set(fp, true);
        }
        positions.clear();
        for (Player p:  board.getGame().getPlayers()) {
            positions.put(p, board.getPosition(p));
            LinkedList<FencePosition> fences = new LinkedList<FencePosition>();
            if(board.getFences(p) != null)
                for (FencePosition fence: board.getFences(p))
                    fences.add(fence);
            fencesByPlayer.put(p, fences);
        }        
    }
         
    /** should not be called when isValidMove returned false,
     *  returns true if game ends in victory for p 
     */
    public boolean move(Player p, BoardPosition bp) {        
        // Store new position.
        positions.put(p, bp);
        // Check if game ends 
        return isVictory(p, bp);
    }

    /** should not be called when isValidFence returned false
     */            
    public void addFence(Player p, FencePosition fp) {
        fps.add(fp);
        fencesByPlayer.get(p).add(fp);
        set(fp, true);
    }

    public void removeFence(Player p, FencePosition fp) {
        fps.remove(fp);
        fencesByPlayer.get(p).remove(fp);
        set(fp, false);
    }

    public boolean closeToPlayer(FencePosition fp) {
        for (Player p:  board.getGame().getPlayers()) {
            BoardPosition bp = board.getPosition(p);
            if (abs(bp.getX()-fp.getX()) <= 1 && abs(bp.getY()-fp.getY()) <= 1)
                return true;
        }        
        return false;
    }

    private int abs(int g) {
        return g >= 0 ? g:-g;
    }

    public boolean closeToFence(FencePosition fp) {
        int x = fp.getX();
        int y = fp.getY();
        if (fp.getDirection() == FencePosition.HORIZONTAL) {
            // check 2 horizontal: left and right
            if (y - 1 >= 0 && horFence[y-1][x])
                return true;
            if (y + 2 < horFence.length && horFence[y+2][x])
                return true;
            // check 6 vertical positions: up and down
            if (verFence[y][x] || verFence[y][x+1] || (x+2 < verFence.length && verFence[y][x+2]))
                return true;
            if (y - 1 >= 0 && (verFence[y-1][x] || verFence[y-1][x+1] || (x+2 < verFence.length && verFence[y-1][x+2])))
                return true;
        }
        else {
            // check 2 vertical: up and down
            if (x - 1 >= 0 && verFence[y][x-1])
                return true;
            if (x + 2 < verFence.length && verFence[y][x+2])
                return true;
            // check 6 horizontal: left/right
            if (horFence[y][x] || horFence[y+1][x] || (y+2 < horFence.length && horFence[y+2][x]))
                return true;
            if (x - 1 >= 0 && (horFence[y][x-1] || horFence[y+1][x-1] || (y+2 < horFence.length && horFence[y+2][x-1])))
                return true;            
        }
        return false;
    }
    
}
