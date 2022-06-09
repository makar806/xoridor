package org.xoridor.net.direct;

import java.rmi.Naming;
import java.rmi.server.ExportException;

import org.xoridor.core.Board;
import org.xoridor.core.Player;
import org.xoridor.util.cmd.Command;

public class ServerStartCommand implements Command<Exception> {
    private Board board;
    private Player clientPlayer;
    private int port = 1099;

    public ServerStartCommand(Board board, Player clientPlayer) {
        this.board = board;
        this.clientPlayer = clientPlayer;
    }
    
    public void execute() {
        String url = "//127.0.0.1/xoridor";
        try {
            java.rmi.registry.LocateRegistry.createRegistry(port);
            NetworkAdapter server = new NetworkAdapterImplementation(board, clientPlayer);
            Naming.rebind(url, server);
            board.getGame().setState(new AwaitingIncomingNetworkConnectionState(board.getGame()));
            if (server.canClientStart() && !(board.getGame().getActivePlayer().equals(clientPlayer)))
                board.getGame().nextPlayer();
        } 
        catch (ExportException exc) {
            exc.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}