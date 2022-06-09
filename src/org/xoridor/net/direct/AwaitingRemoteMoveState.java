package org.xoridor.net.direct;

import org.xoridor.core.Game;
import org.xoridor.core.Player;

public class AwaitingRemoteMoveState extends AbstractNetworkGameState {
    public AwaitingRemoteMoveState(Game game, NetworkAdapter adapter) {
        super(game, adapter);
    }

    public boolean allowLocalMove() {
        return false;
    }

    public void signalNextPlayer(Player activePlayer) {
        setState(new LocalMoveInNetworkGameState(getGame(), getRemote()));
    }

    public void signalVictory(Player p) {
    }
}