package org.xoridor.cp;

import org.xoridor.core.Game;
import org.xoridor.core.Player;

public class ComputerPlayerInLocalGameState extends SomeComputerPlayerState {
    public ComputerPlayerInLocalGameState(Game game, ComputerPlayer cp) {
        super(game, cp);
        new Thread() {
            public void run() {
                getComputerPlayer().nextMove(getGame().getBoard());
            }
        }.start();
    }

    public boolean allowLocalMove() {
        return false;
    }

    public void signalNextPlayer(Player activePlayer) {
        getGame().setState(new LocalPlayerInComputerGameState(getGame(), getComputerPlayer()));
    }
}
