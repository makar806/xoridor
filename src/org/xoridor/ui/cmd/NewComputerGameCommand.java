package org.xoridor.ui.cmd;

import javax.swing.JOptionPane;

import org.xoridor.Options;
import org.xoridor.core.Game;
import org.xoridor.core.GameAdapter;
import org.xoridor.core.Player;
import org.xoridor.cp.LocalPlayerInComputerGameState;
import org.xoridor.cp.MinMaxComputerPlayer;
import org.xoridor.ui.Controller;

public class NewComputerGameCommand extends NewAbstractComputerGameCommand {
    public NewComputerGameCommand(Controller c) {
        super(c);
    }

    public void execute() {
        c.getOptions().setTwoPlayers();
        Game game = newGame();
        int difficulty = getSelectedDifficulty("Choose Pc Level");
        game.setState(new LocalPlayerInComputerGameState(game, new MinMaxComputerPlayer(difficulty)));
        game.setPlayerName((game.getActivePlayerNb() == 0 ? 1 : 0), Options.COMPUTER_NAMES[0]);
        game.addListener(new GameAdapter() {
            public void signalVictory(final Player p) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(c.getBoardFrame(), c.getI18n().getValue(getKey(p)));
                    }

                    private String getKey(Player p) {
                        return p.getName().equals(Options.COMPUTER_NAMES[0]) 
                            ? "txtComputerVictory" 
                            : "txtLocalToComputerVictory";
                    }
                });
            }
        });
        game.getPlayers().get(0);
        game.getPlayers().get(1);
    }
}
