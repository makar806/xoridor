package org.xoridor.core;

public class OngoingLocalHumanPlayersOnlyGameState extends State {
    public void signalVictory(Player p) {
        getGame().setState(new GameFinishedState(getGame()));
    }

    public OngoingLocalHumanPlayersOnlyGameState(Game game) {
        super(game);
    }

    public boolean allowLocalMove() {
        return true;
    }    
}
