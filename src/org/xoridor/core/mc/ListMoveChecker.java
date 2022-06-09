package org.xoridor.core.mc;

import java.util.LinkedList;
import java.util.List;

import org.xoridor.core.BoardPosition;
import org.xoridor.core.MoveChecker;

public abstract class ListMoveChecker extends MoveChecker {
    public boolean validMove(BoardPosition oldPosition, BoardPosition newPosition) {
        for (MoveChecker mc : mcs)
            if (mc.validMove(oldPosition, newPosition))
                return true;
        return false;
    }
    
    void add(MoveChecker mc) {
        mcs.add(mc);
    }
    
    protected List<MoveChecker> getMoveCheckers() {
        return mcs;
    }

    private List<MoveChecker> mcs = new LinkedList<MoveChecker>();
}
