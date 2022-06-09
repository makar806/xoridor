package org.xoridor.ui.cmd;

import org.xoridor.ui.BoardFrame;
import org.xoridor.ui.Controller;

public class FirstGameCommand extends UICommand {
    public FirstGameCommand(Controller c) {
        super(c);
    }

    public void execute() {
        bf = new BoardFrame(newGame(), c);
        setLocalPlayersOnlyGame(false);
        bf.setGame(c.getGame());
    }

    public BoardFrame getBoardFrame() {
        return bf;
    }

    private BoardFrame bf;
}