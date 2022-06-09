package org.xoridor.net;

import org.xoridor.core.Game;
import org.xoridor.core.State;

public class NetworkSetupState extends State {
    public NetworkSetupState(Game game) {
        super(game);
    }

    public boolean allowLocalMove() {
        return false;
    }    
}