package org.xoridor.core.fc;

import org.xoridor.core.Board;
import org.xoridor.core.FenceChecker;

public abstract class BoardFenceChecker extends FenceChecker {
    public BoardFenceChecker(Board board) {
        this.board = board;
    }
    
    protected Board getBoard() {
        return board;
    }
    
    private Board board;
}
