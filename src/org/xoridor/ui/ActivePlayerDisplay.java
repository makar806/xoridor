package org.xoridor.ui;


import org.xoridor.core.Game;
import org.xoridor.core.GameAdapter;
import org.xoridor.core.Player;
import org.xoridor.util.ui.JTextDisplay;

public class ActivePlayerDisplay extends JTextDisplay {
    public ActivePlayerDisplay(Game game, Controller c) {
        super("lblCurrentPlayer", c.getI18n());
        signalNextPlayer(game.getActivePlayer());
        game.addListener(new GameAdapter() {
            public void signalNextPlayer(Player activePlayer) {
                ActivePlayerDisplay.this.signalNextPlayer(activePlayer);
            }
        });
    }
    
    void signalNextPlayer(Player activePlayer) {
        setText(activePlayer.toString());
    }
}