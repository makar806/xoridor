package org.xoridor.ui;


import org.xoridor.core.FencePosition;
import org.xoridor.core.Game;
import org.xoridor.core.GameAdapter;
import org.xoridor.core.Player;
import org.xoridor.util.ui.JTextDisplay;

public class AlllowedNbFencesDisplay extends JTextDisplay {
    public AlllowedNbFencesDisplay(final Game game, Controller c, Player p) {
        super("lblNbAllowedFences", c.getI18n());
        this.player = p;
        signalFencePlaced(game, p);
        signalNextPlayer(game.getActivePlayer());
        game.addListener(new GameAdapter() {
            @Override
            public void signalFencePlaced(Player p, FencePosition fp) {
                AlllowedNbFencesDisplay.this.signalFencePlaced(game, p);
            }
            
            public void signalNextPlayer(Player activePlayer) {
                AlllowedNbFencesDisplay.this.signalNextPlayer(activePlayer);
            }
        });
    }
    
    public void signalFencePlaced(Game game, Player p) {
        setText("" + game.getBoard().getNbAllowedFences(player));
    }
    
    public void signalNextPlayer(Player activePlayer) {
        setHighlight(activePlayer.equals(player));
    }
    
    private Player player;
}
