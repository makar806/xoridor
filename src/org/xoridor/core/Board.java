package org.xoridor.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xoridor.core.fc.CompleteFenceChecker;
import org.xoridor.core.mc.CompleteMoveChecker;

public class Board {
    public Board(Game game) {
        super();
        this.game = game;
        mcs.add(new CompleteMoveChecker(this));
        fcs.add(new CompleteFenceChecker(this));
    }
    
    public List<BoardPosition> getAllPositions() {
        List<BoardPosition> all = new LinkedList<BoardPosition>();
        for (int i = 0; i < getNbColumns(); i++)
            for (int j = 0; j < getNbRows(); j++)
                try {
                    all.add(new BoardPosition(this, i, j));
                }
                catch (InvalidBoardPositionException exc) {
                    // This cannot happen, as for loops over range.
                    exc.printStackTrace();
                }
        return all;
    }
    
    public List<FencePosition>getAllFencePositions() {
        List<FencePosition> all = new LinkedList<FencePosition>();
        for (int i = 0; i < getNbColumns() - 1; i++)
            for (int j = 0; j < getNbRows() - 1; j++)
                try {
                    all.add(new FencePosition(this, i, j, FencePosition.HORIZONTAL));
                    all.add(new FencePosition(this, i, j, FencePosition.VERTICAL));
                }
                catch (InvalidBoardPositionException exc) {
                    exc.printStackTrace();
                }
        return all;
    }

    public int getNbColumns() {
        return game.getOptions().getDimension();
    }

    public int getNbRows() {
        return game.getOptions().getDimension();
    }

    private Game game;

    public Game getGame() {
        return game;
    }

    public void setPosition(Player p, BoardPosition bp, boolean local) throws InvalidMoveException, IllegalGameStateException {
        // Check if this isn't the first position of the player on the board.
        boolean firstPosition = !positions.containsKey(p);
        if (!firstPosition)
            checkValidMove(p, bp, local);
        // Store new position.
        positions.put(p, bp);
        // After victory, position should still be updated for remote game.
        if (!firstPosition)
            game.signalPositionChange(p, bp);
        // Check if game ends
        if (isVictory(p, bp))
            game.signalVictory(p);
    }

    public void setComputerPosition(Player p, BoardPosition bp) throws InvalidMoveException, IllegalGameStateException {
        // Check if this isn't the first position of the player on the board.
        boolean firstPosition = !positions.containsKey(p);
        if (!firstPosition) {
            for (MoveChecker mc : mcs)
                if (!mc.validMove(positions.get(p), bp))
                    throw new InvalidMoveException();

        }
        // Store new position.
        positions.put(p, bp);
        // Check if game ends
        if (isVictory(p, bp))
            game.signalVictory(p);
        if (!firstPosition & !isVictory(p, bp))
            game.signalPositionChange(p, bp);
    }


    public boolean isVictory(Player p, BoardPosition bp) {
    	int indexOf = game.getPlayers().indexOf(p);
    	switch (indexOf) {
    		case 0: 
    		return bp.getY() == getNbRows() - 1;
    		case 1: 
    		return bp.getY() == 0;
    		case 2: 
    		return bp.getX() == getNbColumns() - 1;
    		default:
    		return bp.getX() == 0;
    	}
    }

    private void checkValidMove(Player p, BoardPosition bp, boolean local) throws InvalidMoveException, IllegalGameStateException {
        if (local & !game.getState().allowLocalMove())
            throw new IllegalGameStateException();
        for (MoveChecker mc : mcs)
            if (!mc.validMove(positions.get(p), bp))
                throw new InvalidMoveException();
    }
  
    public boolean isValidMove(Player p, BoardPosition bp) {
        for (MoveChecker mc : mcs)
            if (!mc.validMove(positions.get(p), bp))
                return false;
        return true;    	
    }
    
    public BoardPosition getPosition(Player p) {
        return positions.get(p);
    }
    
    public boolean hasPiece(BoardPosition position) {
        for (Player p : getGame().getPlayers())
            if (getPosition(p).equals(position))
                return true;
        return false;
    }
    
    public void addFence(Player p, FencePosition fp, boolean local) throws InvalidFenceException, IllegalGameStateException {
        checkValidFence(p, fp, local);
        fps.add(fp);
        game.signalFencePlaced(p, fp);
    }

    public void addComputerFence(Player p, FencePosition fp) throws InvalidFenceException, IllegalGameStateException {
        for (FenceChecker fc : fcs)
            if (!fc.validFence(p, fp))
                throw new InvalidFenceException();
        fps.add(fp);
        game.signalFencePlaced(p, fp);
    }
    
    private void checkValidFence(Player p, FencePosition fp, boolean local) throws InvalidFenceException, IllegalGameStateException {
        if (local && !game.getState().allowLocalMove())
            throw new IllegalGameStateException();
        for (FenceChecker fc : fcs)
            if (!fc.validFence(p, fp))
                throw new InvalidFenceException();
    }

    public boolean isValidFence(Player p, FencePosition fp) {
        for (FenceChecker fc : fcs)
            if (!fc.validFence(p, fp))
                return false;
        return true;		
	}

    public List<FencePosition> getFences() {
        return fps;
    }

    public boolean hasFence(FencePosition fp) {
        for (FencePosition f: fps)
            if (f.equals(fp))
                return true;
        return false;
    }

    public int getNbPlacedFences(Player p) {
        try {
            return fencesByPlayer.get(p).size();
        }
        catch (NullPointerException exc) {
            return 0;
        }
    }
    
    public List<FencePosition> getFences(Player p) {
        return fencesByPlayer.get(p);
    }

    public int getNbAllowedFences(Player p) {
        return getGame().getMaxFences() - getNbPlacedFences(p); 
    }
  
    public void signalFencePlaced(Player p, FencePosition fp) {
        if (fencesByPlayer.get(p) == null)
            fencesByPlayer.put(p, new LinkedList<FencePosition>());
        fencesByPlayer.get(p).add(fp);
    }

    protected Map<Player, List<FencePosition>> fencesByPlayer = new HashMap<Player, List<FencePosition>>();

    private List<MoveChecker> mcs = new LinkedList<MoveChecker>();
    private List<FenceChecker> fcs = new LinkedList<FenceChecker>();
    protected List<FencePosition> fps = new LinkedList<FencePosition>();
    protected Map<Player, BoardPosition> positions = new HashMap<Player, BoardPosition>();
}
