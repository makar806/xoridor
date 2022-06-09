package org.xoridor.ui.cmd;

import org.xoridor.core.Game;
import org.xoridor.core.GameAdapter;
import org.xoridor.core.OngoingLocalHumanPlayersOnlyGameState;
import org.xoridor.core.Player;
import org.xoridor.core.PriorToStartState;
import org.xoridor.ui.Controller;
import org.xoridor.ui.PlayerEditFrame;
import org.xoridor.util.cmd.Command;

public abstract class UICommand implements Command<Exception>{
    public UICommand(Controller c) {
        this.c = c;
    }
    
    public abstract void execute();

    protected Game newGame() {
        c.setGame(new Game(c.getOptions()));
        return c.getGame();
    }
    
    protected void setLocalPlayersOnlyGame(boolean window) {
        final Game game = newGame();
        if (window) {
            game.setState(new PriorToStartState(game,new OngoingLocalHumanPlayersOnlyGameState(game)));
            new PlayerEditFrame(c, game).setVisible(true);
        }
        else
            game.setState(new OngoingLocalHumanPlayersOnlyGameState(game));        
        game.addListener(new GameAdapter() {
            public void signalVictory(final Player p) {
                game.getOptions().getStatistics().signalVictory(p);
                c.showDialog(c.getI18n().getValue("txtVictory") + p + ".");
            }
        });
    }
    
    protected Controller c;
}