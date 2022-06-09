/*
 * Created on 11-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.cmd;

public abstract class ExceptionHandler<E extends Exception> {
    public abstract void handle(E exc);

    public abstract void handeUncheckedException(Exception exc);
}
