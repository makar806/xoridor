package org.xoridor.net.direct;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.xoridor.ui.Controller;
import org.xoridor.util.cmd.Command;

public class ClientStartCommand implements Command<Exception> {
    public ClientStartCommand(String server, Controller c) {
        this.server = server;
        this.c = c;
    }

    private Controller c;

    public void execute() {
        String name = "//" + server + ":" + port + "/xoridor";
        try {
            NetworkAdapter server = (NetworkAdapter)Naming.lookup(name);
            server.start(new NetworkAdapterImplementation(c.getGame().getBoard(), c.getGame().getPlayers().get(1)));
            if ((c.getOptions().getStartPlayer() == 0) != (server.canClientStart()))
                c.getGame().nextPlayer();
            c.getGame().setState(new LocalMoveInNetworkGameState(c.getGame(), server));
        }
        catch (RemoteException exc) {
            exc.printStackTrace();
        }
        catch (MalformedURLException exc) {
            exc.printStackTrace();
        }
        catch (NotBoundException exc) {
            exc.printStackTrace();
        }
    }
    
    private String server = "127.0.0.1";
    private int port = 1099;
}