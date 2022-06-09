package org.xoridor.core.fc;

import org.xoridor.core.Board;
import org.xoridor.core.FencePosition;
import org.xoridor.core.Player;

public class NoCrossFenceChecker extends BoardFenceChecker {
    public NoCrossFenceChecker(Board board) {
        super(board);
    }

    public boolean validFence(Player p, FencePosition fp) {
        for (FencePosition fpTaken : getBoard().getFences())
            if (fp.getDirection() != fpTaken.getDirection())
                if (fp.getX() == fpTaken.getX())
                    if (fp.getY() == fpTaken.getY())
                        return false;
        return true;
    }
}