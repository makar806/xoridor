package org.xoridor.ui.cmd;

import org.xoridor.ui.Controller;

public class NewTwoPlayerGameCommand extends UICommand {
    public NewTwoPlayerGameCommand(Controller c) {
        super(c);
    }

    public void execute() {
        c.getOptions().setTwoPlayers();
        setLocalPlayersOnlyGame(true);
    }
}
