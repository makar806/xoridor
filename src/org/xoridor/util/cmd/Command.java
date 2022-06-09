/*
 * Created on 29-dec-2005.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.cmd;

public interface Command<E extends Exception> {
    public void execute () throws E;
}
