package org.xoridor.net.direct;

import org.xoridor.core.Game;
import org.xoridor.core.State;

public class AwaitingIncomingNetworkConnectionState extends State {
    public AwaitingIncomingNetworkConnectionState(Game game) {
        super(game);
    }

    public boolean allowLocalMove() {
        return false;
    }    
}