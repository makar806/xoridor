package org.xoridor.net.direct;

import org.xoridor.core.Game;
import org.xoridor.core.State;

public abstract class AbstractNetworkGameState extends State {
    public AbstractNetworkGameState(Game game, NetworkAdapter remote) {
        super(game);
        this.remote = remote;
    }
    
    protected NetworkAdapter getRemote() {
        return remote;
    }

    private NetworkAdapter remote;
}
