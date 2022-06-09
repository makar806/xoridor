package org.xoridor.ui.net;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.xoridor.core.GameAdapter;
import org.xoridor.core.Player;
import org.xoridor.net.direct.ClientStartCommand;
import org.xoridor.net.direct.ServerStartCommand;
import org.xoridor.ui.BoardDisplay;
import org.xoridor.ui.Controller;
import org.xoridor.util.URLProvider;
import org.xoridor.util.i18n.AbstractButtonTextI18nListener;
import org.xoridor.util.i18n.JFrameRepackI18nListener;
import org.xoridor.util.i18n.JFrameTitleI18nListener;
import org.xoridor.util.i18n.JLabelI18nListener;

// TODO: check options for client vs. server preference
public class HostToHostFrame extends JFrame {
    public HostToHostFrame(Controller c, BoardDisplay boardDisplay) {
        try {
            this.setIconImage(new URLProvider().getIcon("icons/network.gif"));
        }
        catch (MalformedURLException exc) {
            // This shouldn't happen: icon is packed into JAR-file.
        }
        JPanel pnlRoot = new JPanel();
        pnlRoot.setLayout(new BoxLayout(pnlRoot, BoxLayout.Y_AXIS));
        pnlRoot.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        JLabel lblIntro = new JLabel();
        lblIntro.setAlignmentX(LEFT_ALIGNMENT);
        c.getI18n().addI18nListener(new JLabelI18nListener(lblIntro, "txtHostToHostIntro"));
        pnlRoot.add(lblIntro);
        c.getI18n().addI18nListener(new JFrameTitleI18nListener(this, "titleHostToHost"));
        JPanel pnlClientOrServer = new JPanel();
        pnlClientOrServer.setLayout(new BoxLayout(pnlClientOrServer, BoxLayout.Y_AXIS));
        pnlClientOrServer.setBorder(new javax.swing.border.EmptyBorder(5, 10, 5, 0));
        pnlRoot.add(pnlClientOrServer);
        ButtonGroup group = new ButtonGroup();
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                updateRemoteHostEnabled();
            }
        };
        /* Server radio button */
        btServer.setAlignmentX(LEFT_ALIGNMENT);
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(btServer, "btHostToHostServer"));
        group.add(btServer);
        btServer.addActionListener(al);
        boolean isServer = false;
        btServer.setSelected(isServer);
        pnlClientOrServer.add(btServer);
        /* Client radio button */
        btClient.setAlignmentX(LEFT_ALIGNMENT);
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(btClient, "btHostToHostClient"));
        group.add(btClient);
        btClient.addActionListener(al);
        btClient.setSelected(!isServer);
        pnlClientOrServer.add(btClient);
        JPanel pnlRemoteHost = new JPanel();
        pnlRemoteHost.setLayout(new BoxLayout(pnlRemoteHost, BoxLayout.X_AXIS));
        pnlRemoteHost.setBorder(new javax.swing.border.EmptyBorder(0, 30, 0, 0));
        pnlRemoteHost.setAlignmentX(LEFT_ALIGNMENT);
        c.getI18n().addI18nListener(new JLabelI18nListener(lblRemoteHost, "lblHostToHostRemoteHost"));
        pnlRemoteHost.add(lblRemoteHost);
        pnlRemoteHost.add(Box.createRigidArea(new java.awt.Dimension(5, 0)));
        tfRemoteHost.setText(c.getOptions().getServer());
        pnlRemoteHost.add(tfRemoteHost);
        pnlClientOrServer.add(pnlRemoteHost);
        updateRemoteHostEnabled();

        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
        pnlButtons.add(Box.createHorizontalGlue());
        pnlButtons.setAlignmentX(LEFT_ALIGNMENT);
        JButton btCancel = new JButton();
        btCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cancel();
            }
        });
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(btCancel, "btCancel"));
        pnlButtons.add(btCancel);
        pnlButtons.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        JButton btOk = new JButton();
        btOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ok();
            }
        });
        btOk.setDefaultCapable(true);
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(btOk, "btOk"));
        pnlButtons.add(btOk);
        pnlRoot.add(pnlButtons);
        getRootPane().setDefaultButton(btOk);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        setContentPane(pnlRoot);
        c.getI18n().addI18nListener(new JFrameRepackI18nListener(this));
        setVisible(true);
        this.c = c;
        this.boardDisplay = boardDisplay;
    }

    private void updateRemoteHostEnabled() {
        boolean remoteHostEnabled = btClient.isSelected();
        lblRemoteHost.setEnabled(remoteHostEnabled);
        tfRemoteHost.setEnabled(remoteHostEnabled);
    }

    private void cancel() {
        close();
    }

    private void ok() {
        // TODO: start listening or connecting
        if (btServer.isSelected())
            new ServerStartCommand(c.getGame().getBoard(), c.getGame().getPlayers().get(0)).execute();
        else
            new ClientStartCommand(tfRemoteHost.getText(), c).execute();
        c.getGame().addListener(new GameAdapter() {
            public void signalVictory(final Player p) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, c.getI18n().getValue(getKey(p)));
                    }

                    private String getKey(Player p) {
                        return (btServer.isSelected() == c.getGame().getPlayers().get(0).equals(p)) ? "txtComputerVictory" : "txtLocalToComputerVictory";
                    }
                });
            }
        });
        close();
    }

    private void close() {
        if (btClient.isEnabled())
            c.getOptions().setServer(tfRemoteHost.getText());
        dispose();
        boardDisplay.refresh();
    }

    private BoardDisplay boardDisplay;
    private JLabel lblRemoteHost = new JLabel();
    private JRadioButton btServer = new JRadioButton();
    private JRadioButton btClient = new JRadioButton();
    private JTextField tfRemoteHost = new JTextField(8);
    private Controller c;
}