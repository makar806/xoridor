package org.xoridor.core.fc;

import org.xoridor.core.Board;
import org.xoridor.core.FencePosition;
import org.xoridor.core.Player;

public class NoClashFenceChecker extends BoardFenceChecker {
    public NoClashFenceChecker(Board board) {
        super(board);
    }

    public boolean validFence(Player p, FencePosition fp) {
        for (FencePosition fpTaken : getBoard().getFences())
            if (fp.getDirection() == FencePosition.VERTICAL) {
                if (fpTaken.getDirection() == FencePosition.VERTICAL)
                    if (fp.getX() == fpTaken.getX())
                        if (Math.abs(fp.getY() - fpTaken.getY()) <= 1)
                            return false;
            }
            else
                if (fpTaken.getDirection() == FencePosition.HORIZONTAL)
                    if (fp.getY() == fpTaken.getY())
                        if (Math.abs(fp.getX() - fpTaken.getX()) <= 1)
                            return false;
        return true;
    }
}
