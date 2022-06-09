package org.xoridor.core;

import org.xoridor.util.Proxy;

public class GameListenerProxy extends Proxy<GameListener> implements GameListener {
    public GameListenerProxy(GameListener remote) {
        super(remote);
    }

    public void signalNextPlayer(Player activePlayer) {
        if (hasRemote())
            getRemote().signalNextPlayer(activePlayer);
    }

    public void signalVictory(Player p) {
        if (hasRemote())
            getRemote().signalVictory(p);
    }

    public void signalFencePlaced(Player p, FencePosition fp) {
        if (hasRemote())
            getRemote().signalFencePlaced(p, fp);
    }
    
    public void signalPostionChange(Player p, BoardPosition bp) {
        if (hasRemote())
            getRemote().signalPostionChange(p, bp);
    }

    public void signalStart() {
        if (hasRemote())
            getRemote().signalStart();
    }

    public void signalState(State state) {
        if (hasRemote())
            getRemote().signalState(state);
    }
}
