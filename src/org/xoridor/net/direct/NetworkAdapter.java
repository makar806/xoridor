package org.xoridor.net.direct;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NetworkAdapter extends Remote {
    public void start(final NetworkAdapter remote) throws RemoteException;

    public boolean canClientStart() throws RemoteException;
    
    public void setPosition(int x, int y) throws RemoteException;

    public void placeFence(int x, int y, boolean direction) throws RemoteException;
}