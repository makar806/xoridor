/*
 * Created on 11-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.ui.net;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import org.xoridor.net.ConnectToCommunityCommand;
import org.xoridor.ui.Controller;
import org.xoridor.util.i18n.AbstractButtonTextI18nListener;
import org.xoridor.util.ui.JMessagePanel;
import org.xoridor.util.ui.JWizardPanel;

public class CommunityListPanel extends JWizardPanel<CommunityWizard> {
    public CommunityListPanel(final Controller c, ConnectToCommunityCommand ctc, CommunityWizard wizard) {
        super(wizard);
        setLayout(new BorderLayout());
        cl = new CommunityList(c, ctc, this);
        mp = new JMessagePanel(c.getI18n());
        mp.setMinimumSize(new Dimension(0,40));
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cl, mp);
        split.setOneTouchExpandable(true);
        split.setBorder(new EmptyBorder(0,0,0,0));
        add(split, BorderLayout.CENTER);
        addRefreshButton(c);
        addCloseButton(c, ctc);
        addConnectButton(c);
        this.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent arg0) {
                cl.requestFocus();
            }
        });
        setMinimumSize(new Dimension(0,400));
    }

    protected void close(Controller c, ConnectToCommunityCommand ctc) {
        close();
        // TODO: close down RMI server
        getWizard().close(c, ctc);
    }

    private void addConnectButton(Controller c) {
        play = new JButton();
        play.setEnabled(false);
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                play();
            }
        });
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(play, "btPlay"));
        this.setDefaultButton(play);
        addButton(play);
    }

    protected void play() {
        // TODO: connect to this player's ip address.
        System.out.println("Should play player #" + cl.getSelectedPlayer() + " from the list,"
                + " known by the name of \"" + cl.getXmlTable().getValueAt(cl.getSelectedPlayer(), 0) + "\""
                + " residing at " + cl.getXmlTable().getValueAt(cl.getSelectedPlayer(), 1) + ":" + cl.getXmlTable().getValueAt(cl.getSelectedPlayer(), 2) + ".");
    }

    private void addCloseButton(final Controller c, final ConnectToCommunityCommand ctc) {
        JButton close = new JButton();
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                close(c, ctc);
            }
        });
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(close, "btClose"));
        addButton(close);
    }

    private void addRefreshButton(Controller c) {
        JButton refresh = new JButton();
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cl.refresh();
            }
        });
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(refresh, "btRefresh"));
        addButton(refresh);
    }
    
    public void setPlayable(boolean playable) {
        play.setEnabled(playable);
    }

    public JMessagePanel getMessagePanel() {
        return mp;
    }

    private CommunityList cl;
    private JButton play;
    private JMessagePanel mp;
}