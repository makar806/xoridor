/*
 * Created on 12-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.token;

public class Token {
    public synchronized void request() throws TokenTakenException {
        if (taken)
            throw new TokenTakenException();
        taken = true;
    }
    
    private boolean taken;
}