package org.xoridor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.xoridor.core.Game;
import org.xoridor.util.i18n.AbstractButtonTextI18nListener;
import org.xoridor.util.i18n.JFrameTitleI18nListener;

public class PlayerEditFrame extends JFrame {
    public PlayerEditFrame(Controller c, final Game game) {
        c.getI18n().addI18nListener(new JFrameTitleI18nListener(this, "titlePlayerEdit"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        panel.add(new PlayerNameEdit(game, c));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        JButton start = new JButton();
        getRootPane().setDefaultButton(start);
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(start, "start"));
        buttonPanel.add(start);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                game.start();
                close();
            }
        });
        panel.add(buttonPanel);
        setContentPane(panel);
        pack();
    }

    protected void close() {
        this.dispose();
    }
}
