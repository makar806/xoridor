package org.xoridor.net.direct;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;
import org.xoridor.core.FencePosition;
import org.xoridor.core.IllegalGameStateException;
import org.xoridor.core.InvalidBoardPositionException;
import org.xoridor.core.InvalidFenceException;
import org.xoridor.core.InvalidMoveException;
import org.xoridor.core.Player;

public class NetworkAdapterImplementation extends UnicastRemoteObject implements NetworkAdapter {
    public NetworkAdapterImplementation(Board board, Player remotePlayer) throws RemoteException {
        this.board = board;
        this.remotePlayer = remotePlayer;
    }
    
    public void start(NetworkAdapter remote) throws RemoteException {
        if (!started) {
            this.remote = remote;
            board.getGame().setState(new AwaitingRemoteMoveState(board.getGame(), remote));
            started = true;
        }
    }

    public void setPosition(int x, int y) throws RemoteException {
        try {
            board.setPosition(remotePlayer, new BoardPosition(board, x, y), false);
        }
        catch (InvalidMoveException exc) {
            exc.printStackTrace();
        }
        catch (IllegalGameStateException exc) {
            exc.printStackTrace();
        }
        catch (InvalidBoardPositionException exc) {
            exc.printStackTrace();
        }
    }
    
    public void placeFence(int x, int y, boolean direction) throws RemoteException {
        try {
            board.addFence(remotePlayer, new FencePosition(board, x, y, direction), false);
        }
        catch (InvalidFenceException exc) {
            exc.printStackTrace();
        }
        catch (IllegalGameStateException exc) {
            exc.printStackTrace();
        }
        catch (InvalidBoardPositionException exc) {
            exc.printStackTrace();
        }
    }

    public boolean canClientStart() throws RemoteException {
        return true;
    }
    
    private Player remotePlayer;
    private Board board;
    private NetworkAdapter remote;
    private boolean started = false;
}