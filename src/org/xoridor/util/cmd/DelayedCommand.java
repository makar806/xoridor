/*
 * Created on 11-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.cmd;


public class DelayedCommand<E extends Exception> implements Command<Exception> {
    public DelayedCommand(Command<E> c, ExceptionHandler<E> handler) {
        this.c = c;
        this.handler = handler;
    }

    public void execute() {
        Thread thread = new Thread() {
            @SuppressWarnings("unchecked")
            public void run() {
                try {
                    c.execute();
                }
                catch (Exception exc) {
                    try {
                        handler.handle((E)exc);
                    }
                    catch (ClassCastException exc2) {
                        handler.handeUncheckedException(exc2);
                    }
                }
            }
        };
        thread.start();
    }
    
    private Command<E> c;
    private ExceptionHandler<E> handler;
}