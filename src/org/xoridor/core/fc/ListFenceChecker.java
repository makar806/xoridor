package org.xoridor.core.fc;

import java.util.LinkedList;
import java.util.List;

import org.xoridor.core.FenceChecker;
import org.xoridor.core.FencePosition;
import org.xoridor.core.Player;


public abstract class ListFenceChecker extends FenceChecker {
    public boolean validFence(Player p, FencePosition fp) {
        for (FenceChecker fc : fcs)
            if (fc.validFence(p, fp))
                return true;
        return false;
    }
    
    void add(FenceChecker fc) {
        fcs.add(fc);
    }
    
    protected List<FenceChecker> getFenceCheckers() {
        return fcs;
    }

    private List<FenceChecker> fcs = new LinkedList<FenceChecker>();
}
