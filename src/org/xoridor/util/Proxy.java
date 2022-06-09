/*
 * Created on 5-feb-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util;

public class Proxy<T> {
    public Proxy(T remote) {
        this.remote = remote;
    }

    public void setRemote(T state) {
        this.remote = state;
    }
    
    protected boolean hasRemote() {
        return remote != null;
    }
    
    protected T getRemote() {
        return remote;
    }

    private T remote;
}
