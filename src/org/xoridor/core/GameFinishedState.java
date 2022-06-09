package org.xoridor.core;

public class GameFinishedState extends State {
    public GameFinishedState(Game game) {
        super(game);
    }

    public boolean allowLocalMove() {
        return false;
    }

}
