package org.xoridor.core.mc;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;
import org.xoridor.core.InvalidBoardPositionException;

public class JumpMoveChecker extends BoardMoveChecker {
    public JumpMoveChecker(Board board) {
        super(board);
    }

    public boolean validMove(BoardPosition oldPosition, BoardPosition newPosition) {
        return validDistance(oldPosition, newPosition) &&
            isPieceInBetween(oldPosition, newPosition);
    }

    private boolean isPieceInBetween(BoardPosition oldPosition, BoardPosition newPosition) {
        for (BoardPosition maybeBetween : this.getManhattanNeighbours(oldPosition)) {
            // Check if taken.
            if (getBoard().hasPiece(maybeBetween) && ! getBoard().hasPiece(newPosition))
                // And check if taken position is effectively between the two pieces.
                if (getDistance(maybeBetween, newPosition) == 1)
                	// check if no fences block manhattan move
                	if (validManhattanMove(oldPosition, maybeBetween) 
                	   && validManhattanMove(maybeBetween, newPosition)) {
                    	if (oldPosition.getX() != newPosition.getX() && 
                    	    oldPosition.getY() != newPosition.getY()) {
                            // check if diagonal move allowed: only when straight jump is not possible
                    	 	BoardPosition straight = getStraightJumpOverPosition(oldPosition, maybeBetween);
                    	    if(straight == null || ! validManhattanMove(maybeBetween, straight))
                    	        return true;		    
                    	} 
                    	else
                    		return true;
                     }
        }
        return false;
    }

	private BoardPosition getStraightJumpOverPosition(BoardPosition pos, BoardPosition between) {
		BoardPosition str = null;
		try {
 	        int x = pos.getX() + 2*(between.getX() - pos.getX()); 
		    int y = pos.getY() + 2*(between.getY() - pos.getY());
		    str = new BoardPosition(getBoard(), x, y);	
		}
		catch (InvalidBoardPositionException io) {
			str = null;
		}
		return str;
	}

    private boolean validDistance(BoardPosition oldPosition, BoardPosition newPosition) {
        return (getDistance(oldPosition, newPosition) == 2);
    }
}