package org.xoridor.core.mc;

import org.xoridor.core.BoardPosition;
import org.xoridor.core.MoveChecker;

public class AndMoveChecker extends ListMoveChecker {
    public boolean validMove(BoardPosition oldPosition, BoardPosition newPosition) {
        for (MoveChecker mc : getMoveCheckers())
            if (!mc.validMove(oldPosition, newPosition)) {
                // System.out.println("Failed = " + mc);
                return false;
            }
        return true;
    }
}
