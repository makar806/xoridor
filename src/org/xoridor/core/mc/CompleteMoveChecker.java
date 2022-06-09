package org.xoridor.core.mc;

import org.xoridor.core.Board;

public class CompleteMoveChecker extends AndMoveChecker {
    public CompleteMoveChecker(Board board) {
        add(new NoClashMoveChecker(board));
        OrMoveChecker omc = new OrMoveChecker();
        AndMoveChecker amc = new AndMoveChecker();
        amc.add(new ManhattanMoveChecker());
        amc.add(new NoFenceMoveChecker(board));
        omc.add(amc); 
        omc.add(new JumpMoveChecker(board));
        add(omc);
        
    }
}