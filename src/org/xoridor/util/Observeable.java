/*
 * Created on 30-dec-2005.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util;

import java.util.LinkedList;
import java.util.List;

public abstract class Observeable<Listener> {
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    protected List<Listener> getListeners() {
        return listeners;
    }
    
    public List<Listener> listeners = new LinkedList<Listener>();
}