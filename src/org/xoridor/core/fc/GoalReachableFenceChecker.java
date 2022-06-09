package org.xoridor.core.fc;


import java.util.List;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;
import org.xoridor.core.FencePosition;
import org.xoridor.core.InvalidBoardPositionException;
import org.xoridor.core.Player;


public class GoalReachableFenceChecker extends BoardFenceChecker {
		
	private Board board;	
		
    public GoalReachableFenceChecker(Board board) {
        super(board);
        players = board.getGame().getPlayers();
        this.board = board;
    }

    public boolean validFence(Player p, FencePosition fp) {
    	int pNb = 0;
    	for (Player player: players) {
    		BoardPosition start = board.getPosition(player);
    	    visited = new boolean[board.getNbColumns()][board.getNbRows()];
    	    visit(start);
    	    if (! dfs(pNb, start, fp))
    	    	return false;
    	    ++pNb;
    	}
        return true;
    }
    
    private boolean dfs(int pNb, BoardPosition cur, FencePosition fp) {
    	if (canGoLeft(cur, fp)) {
    		BoardPosition bp = goLeft(cur);
    		if (goalPosition(pNb, bp))
    			return true;
    		if (dfs(pNb, bp, fp))
    			return true;	
    	}
    	if (canGoRight(cur, fp)) {
    		BoardPosition bp = goRight(cur);
    		if (goalPosition(pNb, bp))
    			return true;    		
    		if (dfs(pNb, bp, fp))
    			return true;	
    	}
    	if (canGoUp(cur, fp)) {
    		BoardPosition bp = goUp(cur); 
    		if (goalPosition(pNb, bp))
    			return true;    		
    		if (dfs(pNb, bp, fp))
    			return true;	
    	}
    	if (canGoDown(cur, fp)) {
    		BoardPosition bp = goDown(cur);
    		if (goalPosition(pNb, bp))
    			return true;    
    		if (dfs(pNb, bp, fp))
    			return true;	    					
    	}
    	return false;
    }
 
    
    private boolean canGoLeft(BoardPosition cur, FencePosition fp) {
    	boolean ok;
    	if (cur.getX() == 0)
    		return false;
    	if (visited[cur.getX() - 1][cur.getY()])
    	 	return false;	
        List<FencePosition> fences = board.getFences();
        // dirty :(
        fences.add(fp);
        try {
	  		ok = validHorizontalMove(fences, cur, new BoardPosition(board,cur.getX() - 1, cur.getY()));
	  	}	
	  	catch (InvalidBoardPositionException ie) {
	  		ok = false;
	  	}
        fences.remove(fp);
    	return ok;
    }
    
    private BoardPosition goLeft(BoardPosition cur) {
    	BoardPosition b = null;
    	try {
    		b = new BoardPosition(board,cur.getX() - 1, cur.getY());
    		visit(b);
    	}
    	catch (InvalidBoardPositionException ie) {
	  	}
    	return b;
    }
    
 
    private boolean canGoRight(BoardPosition cur, FencePosition fp) {
    	boolean ok;
    	if (cur.getX() == board.getNbColumns() - 1)
    		return false;
    	if (visited[cur.getX() + 1][cur.getY()])
    	 	return false;	    		
        List<FencePosition> fences = board.getFences();
        fences.add(fp);
        try {
	  		ok = validHorizontalMove(fences, cur, new BoardPosition(board,cur.getX() + 1, cur.getY()));
	  	}	
	  	catch (InvalidBoardPositionException ie) {
	  		ok = false;
	  	}
        fences.remove(fp);
    	return ok;
    }
    
    private BoardPosition goRight(BoardPosition cur) {
    	BoardPosition b = null;
    	try {
    		b = new BoardPosition(board,cur.getX() + 1, cur.getY());
    		visit(b);
    	}
    	catch (InvalidBoardPositionException ie) {
	  	}
    	return b;
    }
 


    private boolean canGoUp(BoardPosition cur, FencePosition fp) {
    	boolean ok;
    	if (cur.getY() == 0)
    		return false;
    	if (visited[cur.getX()][cur.getY()-1])
    	 	return false;	
        List<FencePosition> fences = board.getFences();
        fences.add(fp);
        try {
	  		ok = validVerticalMove(fences, cur, new BoardPosition(board,cur.getX(), cur.getY() - 1));
	  	}	
	  	catch (InvalidBoardPositionException ie) {
	  		ok = false;
	  	}
        fences.remove(fp);
    	return ok;
    }
    
    private BoardPosition goUp(BoardPosition cur) {
    	BoardPosition b = null;
    	try {
    		b = new BoardPosition(board,cur.getX(), cur.getY() - 1);
    		visit(b);
    	}
    	catch (InvalidBoardPositionException ie) {
	  	}
    	return b;
    }
 
 
    private boolean canGoDown(BoardPosition cur, FencePosition fp) {
    	boolean ok;
    	if (cur.getY() == board.getNbRows() - 1)
    		return false;
    	if (visited[cur.getX()][cur.getY()+1])
    	 	return false;	
        List<FencePosition> fences = board.getFences();
        fences.add(fp);
        try {
	  		ok = validVerticalMove(fences, cur, new BoardPosition(board,cur.getX(), cur.getY() + 1));
	  	}	
	  	catch (InvalidBoardPositionException ie) {
	  		ok = false;
	  	}
        fences.remove(fp);
    	return ok;
    }
    
    private BoardPosition goDown(BoardPosition cur) {
    	BoardPosition b = null;
    	try {
    		b = new BoardPosition(board,cur.getX(), cur.getY() + 1);
    		visit(b);
    	}
    	catch (InvalidBoardPositionException ie) {
	  	}
    	return b;
    }

    private void visit(BoardPosition pos) {
    	visited[pos.getX()][pos.getY()] = true;	
    }

    private boolean validVerticalMove(List<FencePosition> fences, BoardPosition oldPosition, BoardPosition newPosition) {
        for (FencePosition fp : fences)
            if (fp.getDirection() == FencePosition.HORIZONTAL)
                // The fence is a horizontal fence.
                if ((oldPosition.getX() == fp.getX()) || (oldPosition.getX() == fp.getX() + 1))
                    // The fence is on this column.
                    if (oldPosition.getY() < newPosition.getY()) {
                        // We jump to a higher Y.
                        if (fp.getY() == oldPosition.getY())
                            // The fence is on this row.
                            return false;
                    }
                    else
                        // We jump to to a lower Y.
                        if (fp.getY() == newPosition.getY())
                            // The fence is on this row.
                            return false;
        return true;
    }

    private boolean validHorizontalMove(List<FencePosition> fences, BoardPosition oldPosition, BoardPosition newPosition) {
        for (FencePosition fp : fences)
            if (fp.getDirection() == FencePosition.VERTICAL)
                // The fence is a vertical fence.
                if ((oldPosition.getY() == fp.getY()) || (oldPosition.getY() == fp.getY() + 1)) 
                    // The fence is on this row.
                    if (oldPosition.getX() < newPosition.getX()) {
                        // We jump to the right.
                        if (fp.getX() == oldPosition.getX())
                            // The fence is on this column.
                            return false;
                    }
                    else
                        // We jump to the left.
                        if (fp.getX() == newPosition.getX())
                            // The fence is on this column.
                            return false;
        return true;
    }

	private boolean goalPosition(int pNb, BoardPosition bp) {
    	switch (pNb) {
    		case 0: return bp.getY() == board.getNbRows() - 1;
    		case 1: return bp.getY() == 0;
    		case 2: return bp.getX() == board.getNbColumns() - 1;
    		default: return bp.getX() == 0;
    	}
    }
    
    
  	private List<Player> players;
    private boolean [][] visited;
}
