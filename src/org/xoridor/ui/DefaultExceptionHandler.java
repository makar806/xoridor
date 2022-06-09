package org.xoridor.ui;

import org.xoridor.util.cmd.ExceptionHandler;

public final class DefaultExceptionHandler<E extends Exception> extends ExceptionHandler<E> {
    private final Controller c;

    public DefaultExceptionHandler(Controller c) {
        super();
        this.c = c;
    }

    public void handle(E exc) {
        handeUncheckedException(exc);
    }
    
    public void handeUncheckedException(Exception exc) {
        c.signalError(exc);
    }
}