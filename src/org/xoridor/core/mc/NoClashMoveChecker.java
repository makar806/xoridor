package org.xoridor.core.mc;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;

public class NoClashMoveChecker extends BoardMoveChecker {
    public NoClashMoveChecker(Board board) {
        super(board);
    }

    public boolean validMove(BoardPosition oldPosition, BoardPosition newPosition) {
        return !getBoard().hasPiece(newPosition);
    }
}
