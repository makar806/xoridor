package org.xoridor.core;

public class FencePosition extends BoardPosition {
    public FencePosition(Board board, int x, int y, boolean direction) throws InvalidBoardPositionException {
        super(board, x, y);
        this.direction = direction;
    }

    public boolean getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Fence position (" + getX() + ", " + getY() + ") " + (direction ? "vertical" : "horizontal") + " on board " + getBoard();
    }

    @Override
    public boolean equals(Object other) {
        try {
            return super.equals(other) && (direction == ((FencePosition)other).direction);
        }
        catch (ClassCastException exc) {
            return false;
        }
    }
    
    public static final boolean HORIZONTAL = false;
    public static final boolean VERTICAL = true;

    private boolean direction;
}
