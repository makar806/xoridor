package org.xoridor.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.xoridor.core.Game;
import org.xoridor.util.i18n.JLabelI18nListener;
import org.xoridor.util.ui.SpringUtilities;

public class PlayerNameEdit extends JPanel {
    public PlayerNameEdit(Game game, Controller c) {
        this.setLayout(new SpringLayout());
        for (int i = 0; i < game.getNbPlayers(); i++)
            addPlayer(game, i, c);
        SpringUtilities.makeCompactGrid(this,
                game.getNbPlayers(), 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
    }
    
    private void addPlayer(final Game game, final int i, Controller c) {
        JLabel label = new JLabel();
        c.getI18n().addI18nListener(new JLabelI18nListener(label, "player"+i));
        add(label);
        if (game.getOptions().getLastNames().size() > 0)
            addPlayerNameComboBox(game, i, label);
        else
            addPlayerNameTextField(game,i, label);
    }

    private void addPlayerNameTextField(final Game game, final int i, JLabel label) {
        final JTextField name = new JTextField(15);
        add(name);
        label.setLabelFor(name);
        name.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent arg0) {
                setPlayerName(game, i, name.getText());
            }
        });
    }

    private void addPlayerNameComboBox(final Game game, final int i, JLabel label) {
        final JComboBox name = new JComboBox(game.getOptions().getLastNames().toArray());
        add(name);
        label.setLabelFor(name);
        try {
            name.setSelectedItem(game.getPlayerName(i));
        }
        catch (NullPointerException exc) {
        }
        name.setEditable(true);
        name.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent arg0) {
                setPlayerName(game, i, name.getSelectedItem().toString());
            }
        });
    }

    private void setPlayerName(Game game, int i, String name) {
        if (name.length() > 0)
            game.setPlayerName(i, name);
    }
}