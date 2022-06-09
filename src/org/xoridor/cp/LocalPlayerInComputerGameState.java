package org.xoridor.cp;

import org.xoridor.core.Game;
import org.xoridor.core.Player;

public class LocalPlayerInComputerGameState extends SomeComputerPlayerState {
    public LocalPlayerInComputerGameState(Game game, ComputerPlayer player) {
        super(game, player);
    }

    public boolean allowLocalMove() {
        return true;
    }

    public void signalNextPlayer(Player activePlayer) {
        getGame().setState(new ComputerPlayerInLocalGameState(getGame(), getComputerPlayer()));
    }
}
