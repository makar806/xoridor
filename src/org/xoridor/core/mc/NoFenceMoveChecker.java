package org.xoridor.core.mc;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;

public class NoFenceMoveChecker extends BoardMoveChecker {
    public NoFenceMoveChecker(Board board) {
        super(board);
    }

    @Override
    public boolean validMove(BoardPosition oldPosition,
            BoardPosition newPosition) {
        if (oldPosition.getY() == newPosition.getY())
            return validHorizontalMove(oldPosition, newPosition);
        else
            return validVerticalMove(oldPosition, newPosition);
    }

}