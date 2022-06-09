package org.xoridor.core.fc;

import org.xoridor.core.Board;
import org.xoridor.core.FencePosition;
import org.xoridor.core.Player;

public class MaxFenceChecker extends BoardFenceChecker {
    public MaxFenceChecker(Board board) {
        super(board);
    }

    public boolean validFence(Player p, FencePosition fp) {
        return (getBoard().getNbAllowedFences(p) > 0);
    }
}
