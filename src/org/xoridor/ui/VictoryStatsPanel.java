package org.xoridor.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.xoridor.stats.GameStatistics;
import org.xoridor.stats.NoSuchPlayerInStatisticsException;
import org.xoridor.util.i18n.JLabelI18nListener;
import org.xoridor.util.ui.SpringUtilities;

public class VictoryStatsPanel extends JPanel {
    private static final int NB_OF_PLAYERS_SHOWN = 5;

    public VictoryStatsPanel(Controller c) {
        if (c.getOptions().getStatistics().getNbVictoryPlayers() > 0) { 
            this.setLayout(new SpringLayout());
            int nbRows = 0;
            for (int i = 0; i < NB_OF_PLAYERS_SHOWN; i++)
                nbRows += showPlayerScore(c.getOptions().getStatistics(), i, c);
            SpringUtilities.makeCompactGrid(this,
                    nbRows, 2, //rows, cols
                    6, 6,        //initX, initY
                    6, 6);       //xPad, yPad
        }
        else {
            JLabel noStats = new JLabel();
            c.getI18n().addI18nListener(new JLabelI18nListener(noStats,"txtNoVictoryStats"));
            add(noStats);
        }
    }

    private int showPlayerScore(GameStatistics stats, int i, Controller c) {
        try {
            add(new JLabel(stats.getTopPlayer(i)));
            add(new JLabel(stats.getTopScore(i)));
            return 1;
        }
        catch (NoSuchPlayerInStatisticsException exc) {
            return 0;
        }
    }
}