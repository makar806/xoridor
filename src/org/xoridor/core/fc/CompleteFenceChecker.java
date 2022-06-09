package org.xoridor.core.fc;

import org.xoridor.core.Board;

public class CompleteFenceChecker extends AndFenceChecker {
    public CompleteFenceChecker(Board board) {
        add(new NoClashFenceChecker(board));
        add(new NoCrossFenceChecker(board));
        add(new MaxFenceChecker(board));
        add(new GoalReachableFenceChecker(board));
    }
}
