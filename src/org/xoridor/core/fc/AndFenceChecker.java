package org.xoridor.core.fc;

import org.xoridor.core.FenceChecker;
import org.xoridor.core.FencePosition;
import org.xoridor.core.Player;

public class AndFenceChecker extends ListFenceChecker {
    public boolean validFence(Player p, FencePosition fp) {
        for (FenceChecker fc : getFenceCheckers())
            if (!fc.validFence(p, fp))
                return false;
        return true;
    }
}
