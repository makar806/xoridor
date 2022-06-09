package org.xoridor.core;

public class PriorToStartState extends State {
    public PriorToStartState(Game game, State startState) {
        super(game);
        this.startState = startState;
    }

    public boolean allowLocalMove() {
        return false;
    }

    public void signalStart() {
        getGame().setState(startState);
    }
    
    private State startState;
}
