package org.xoridor.core;

public abstract class GameAdapter implements GameListener {
    protected GameAdapter() {
        // Nothing has to be done.
    }
    
    protected GameAdapter(Game game) {
        this.game = game;
    }
    
    public void signalNextPlayer(Player activePlayer) {
        // Nothing has to be done: this is an adapter.        
    }

    public void signalVictory(Player p) {
        // Nothing has to be done: this is an adapter.        
    }

    public void signalFencePlaced(Player p, FencePosition fp) {
        // Nothing has to be done: this is an adapter.        
    }
    
    public void signalPostionChange(Player p, BoardPosition bp) {
    }

    public void signalStart() {
    }

    public void signalState(State state) {
        // Nothing has to be done: this is an adapter.        
    }

    protected Game getGame() {
        return game;
    }
    
    private Game game;
}
