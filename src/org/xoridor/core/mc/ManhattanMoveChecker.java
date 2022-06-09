package org.xoridor.core.mc;

import org.xoridor.core.BoardPosition;
import org.xoridor.core.MoveChecker;

public class ManhattanMoveChecker extends MoveChecker {
    public boolean validMove(BoardPosition oldPosition, BoardPosition newPosition) {
        return (getDistance(oldPosition, newPosition) == 1);
    }
}
