package org.xoridor.ui;

import org.xoridor.core.Game;
import org.xoridor.core.GameAdapter;
import org.xoridor.core.State;
import org.xoridor.util.i18n.I18nListener;
import org.xoridor.util.i18n.I18nManager;
import org.xoridor.util.ui.JStatusBar;

final class StatusBarGameListener extends GameAdapter implements I18nListener {
    public StatusBarGameListener(Game game, JStatusBar statusBar, Controller c) {
        super(game);
        this.statusBar = statusBar;
        this.c = c;
        
    }

    public void signalState(State state) {
        String stateMessage = c.getI18n().getValue(state.getClass().getName());
        if (stateMessage == null)
            stateMessage = c.getI18n().getValue("txtUnknownState");
        statusBar.setMessage(stateMessage);
    }
    
    public void signalI18nChange(I18nManager i18n) {
        signalState(getGame().getState());
    }

    private final Controller c;
    private JStatusBar statusBar;
}