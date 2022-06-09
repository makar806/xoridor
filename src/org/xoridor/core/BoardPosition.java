package org.xoridor.core;

public class BoardPosition {
    public BoardPosition(Board board, int x, int y) throws InvalidBoardPositionException {
        setBoard(board);
        setX(x);
        setY(y);
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) throws InvalidBoardPositionException {
   		int columns = board.getNbColumns();
        if (x < 0 || x >= columns)
        	throw new InvalidBoardPositionException(); 	
        this.x = x;
    }
    
    public int getY() {
        return y;
    }

    public void setY(int y) throws InvalidBoardPositionException {
        int rows = board.getNbRows();
        if (y < 0 || y >= rows)
        	throw new InvalidBoardPositionException();
        this.y = y;
    }
    
    public boolean equals (Object other) {
        try {
            BoardPosition bp = (BoardPosition)other;
            return (bp.getX() == getX()) && (bp.getY() == getY());
        }
        catch (ClassCastException exc) {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Position (" + getX() + ", " + getY() + ") on board " + getBoard();
    }

    private Board board;
    private int x;
    private int y;
}