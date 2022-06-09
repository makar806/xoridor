package org.xoridor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.xoridor.util.URLProvider;
import org.xoridor.util.VersionProvider;
import org.xoridor.util.VersionProvisionException;
import org.xoridor.util.i18n.AbstractButtonTextI18nListener;
import org.xoridor.util.i18n.JFrameRepackI18nListener;
import org.xoridor.util.i18n.JFrameTitleI18nListener;
import org.xoridor.util.i18n.JLabelI18nListener;

public class AboutFrame extends JFrame {
    public AboutFrame(Controller c) {
        c.getI18n().addI18nListener(new JFrameTitleI18nListener(this, "titleAbout"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        panel.add(getNewLabel("txtProject", c));
        panel.add(getNewLabel("txtURL", c));
        JPanel version = new JPanel();
        version.add(getNewLabel("txtVersion", c));
        try {
            version.add(new JLabel(new VersionProvider().getVersion()));
        }
        catch (VersionProvisionException exc) {
            exc.printStackTrace();
        }
        panel.add(version);
        JPanel copyright = new JPanel();
        copyright.add(new JLabel("(C) "));
        copyright.add(new JLabel(getCredits()));
        panel.add(copyright);
        panel.add(getNewLabel("txtNoWarranty", c));
        panel.add(getNewLabel("txtLicense", c));
        panel.add(getNewLabel("txtFreeSoftware", c));
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
        pnlButtons.add(Box.createHorizontalGlue());
        JButton btClose = new JButton();
        btClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                close();
            }
        });
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(btClose, "btClose"));
        btClose.setDefaultCapable(true);
        pnlButtons.add(btClose);
        panel.add(pnlButtons);
        getContentPane().add(panel);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        c.getI18n().addI18nListener(new JFrameRepackI18nListener(this));
        pack();
        setVisible(true);
    }

    public JComponent getNewLabel(String i18nKey, Controller c) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        c.getI18n().addI18nListener(new JLabelI18nListener(label, i18nKey));
        label.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(label);
        return panel;
    }

    private void close() {
        dispose();
    }
    
    private String getCredits() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new URLProvider().getURL(CREDITS_FILE).openStream()));
            return br.readLine();
        }
        catch (MalformedURLException exc) {
            throw new IllegalStateException(exc);
        }
        catch (IOException exc) {
            throw new IllegalStateException(exc);
        }
    }

    private static final String CREDITS_FILE = "credits.txt";
}