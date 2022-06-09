package org.xoridor.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xoridor.util.i18n.AbstractButtonTextI18nListener;
import org.xoridor.util.i18n.JFrameRepackI18nListener;
import org.xoridor.util.i18n.JFrameTitleI18nListener;
import org.xoridor.util.i18n.JLabelI18nListener;

public class VictoryStatsFrame extends JFrame {
    public VictoryStatsFrame(Controller c) {
        c.getI18n().addI18nListener(new JFrameTitleI18nListener(this, "titleVictoryStats"));
        c.getI18n().addI18nListener(new JFrameRepackI18nListener(this));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.LINE_AXIS));
        JLabel label = new JLabel();
        c.getI18n().addI18nListener(new JLabelI18nListener(label, "txtVictories"));
        labelPanel.add(label);
        labelPanel.add(Box.createHorizontalGlue());
        panel.add(labelPanel);
        JComponent component = new VictoryStatsPanel(c);
        panel.add(component);
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        JButton close = new JButton();
        getRootPane().setDefaultButton(close);
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(close, "btClose"));
        buttonPanel.add(close);
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        panel.add(buttonPanel);
        setContentPane(panel);
        pack();
    }
}
