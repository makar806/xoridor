/*
 * Created on 5-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.ui.net;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.xoridor.net.ConnectToCommunityCommand;
import org.xoridor.ui.Controller;
import org.xoridor.util.i18n.JLabelI18nListener;
import org.xoridor.util.xml.XmlTable;

public class CommunityList extends JPanel {
    public CommunityList(Controller c, ConnectToCommunityCommand ctc, final CommunityListPanel panel) {
        this.ctc = ctc;
        setLayout(new BorderLayout());
        JLabel label = new JLabel();
        c.getI18n().addI18nListener(new JLabelI18nListener(label, "lblOnlineUsers"));
        add(label, BorderLayout.NORTH);
        list = new JList();
        refresh();
        list.setLayoutOrientation(JList.VERTICAL);
        list.requestFocus();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(list);
        add(listScroller, BorderLayout.CENTER);
        this.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent arg0) {
                list.requestFocus();
            }
        });
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent arg0) {
                boolean playable = clm.isPlayable(list.getSelectedIndex());
                panel.setPlayable(playable);
                panel.getMessagePanel().setMessage(playable ? null : "lblProxyUser");
            }
        });
    }

    void refresh() {
        clm = new CommunityListModel(ctc, new JList().getCellRenderer());
        list.setModel(clm);
        list.setCellRenderer(clm);
    }
    
    public int getSelectedPlayer() {
        return list.getSelectedIndex();
    }    
    
    public XmlTable getXmlTable() {
        return clm.getXmlTable();
    }

    private CommunityListModel clm;
    private JList list;
    private ConnectToCommunityCommand ctc;
}