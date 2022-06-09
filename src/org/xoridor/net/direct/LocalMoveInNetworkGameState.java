package org.xoridor.net.direct;

import java.rmi.RemoteException;

import org.xoridor.core.BoardPosition;
import org.xoridor.core.FencePosition;
import org.xoridor.core.Game;
import org.xoridor.core.Player;

public class LocalMoveInNetworkGameState extends AbstractNetworkGameState {
    public LocalMoveInNetworkGameState(Game game, NetworkAdapter remote) {
        super(game, remote);
    }

    public boolean allowLocalMove() {
        return true;
    }
    
    public void signalNextPlayer(Player activePlayer) {
        setState(new AwaitingRemoteMoveState(getGame(), getRemote()));
    }
    
    @Override
    public void signalPostionChange(Player p, BoardPosition bp) {
        try {
            getRemote().setPosition(bp.getX(), bp.getY());
        }
        catch (RemoteException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void signalFencePlaced(Player p, FencePosition fp) {
        try {
            getRemote().placeFence(fp.getX(), fp.getY(), fp.getDirection());
        }
        catch (RemoteException exc) {
            exc.printStackTrace();
        }
    }
}