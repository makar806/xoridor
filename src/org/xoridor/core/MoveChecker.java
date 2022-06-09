package org.xoridor.core;

public abstract class MoveChecker {
    public abstract boolean validMove(BoardPosition oldPosition, BoardPosition newPosition);

	private int abs(int x) {
		if (x >= 0)
			return x;
	    else
			return -x;
	}

    protected int getDistance(BoardPosition bp1, BoardPosition bp2) {
        return abs(bp1.getX() - bp2.getX()) + abs(bp1.getY() - bp2.getY());
    }
}
