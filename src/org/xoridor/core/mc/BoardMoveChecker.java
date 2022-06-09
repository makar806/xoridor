package org.xoridor.core.mc;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;
import org.xoridor.core.FencePosition;
import org.xoridor.core.MoveChecker;
import org.xoridor.util.filter.Filter;
import org.xoridor.util.filter.FilteredIterator;

public abstract class BoardMoveChecker extends MoveChecker {
    public BoardMoveChecker(Board board) {
        this.board = board;
    }
    
    Board getBoard() {
        return board;
    }
    
    protected Iterable<BoardPosition> getManhattanNeighbours(final BoardPosition bp) {
        return new FilteredIterator<BoardPosition>(board.getAllPositions(), new Filter<BoardPosition>() {
            public boolean ok(BoardPosition bp2) {
                return getDistance(bp, bp2) == 1;
            }
        });
    }


    protected boolean validVerticalMove(BoardPosition oldPosition, BoardPosition newPosition) {
        for (FencePosition fp : getBoard().getFences())
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

    protected boolean validHorizontalMove(BoardPosition oldPosition, BoardPosition newPosition) {
        for (FencePosition fp : getBoard().getFences())
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

	protected boolean validManhattanMove(BoardPosition oldPosition, BoardPosition newPosition) {
        if (oldPosition.getX() == newPosition.getX())
        	return getDistance(oldPosition, newPosition) == 1 && validVerticalMove(oldPosition, newPosition);
        else 
        	return getDistance(oldPosition, newPosition) == 1 && validHorizontalMove(oldPosition, newPosition);
    } 
    
    private Board board;
}
