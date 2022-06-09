package org.xoridor.core;

public abstract class State extends GameAdapter {
    public State(Game game) {
        this.game = game;
    }

    public abstract boolean allowLocalMove();

    protected Game getGame() {
        return game;
    }
    
    protected void setState(State state) {
        getGame().setState(state);
    }
    
    public void signalVictory(Player p) {
        setState(new GameFinishedState(getGame()));
    }

    private Game game;
}