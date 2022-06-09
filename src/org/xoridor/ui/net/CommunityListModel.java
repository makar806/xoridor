/*
 * Created on 12-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.ui.net;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xoridor.net.ConnectToCommunityCommand;
import org.xoridor.util.xml.XmlTable;

public class CommunityListModel extends DefaultListModel implements ListCellRenderer {
    private static final int CAN_HOST_COLUMN = 3;

    public CommunityListModel(ConnectToCommunityCommand ctc, ListCellRenderer lcr) {
        this.lcr = lcr;
        try {
            xt = new XmlTable(ctc.getSessionInputStream());
        }
        catch (UnknownHostException exc) {
            // TODO: handle no network available.
            throw new IllegalStateException(exc);
        }
        catch (MalformedURLException exc) {
            // TODO Auto-generated catch block
            exc.printStackTrace();
        }
        catch (ConnectException exc) {
            // TODO handle connection refused by webserver. 
            throw new IllegalStateException(exc);
        }
        catch (IOException exc) {
            // TODO Auto-generated catch block
            exc.printStackTrace();
        }
        catch (SAXException exc) {
            // TODO Auto-generated catch block
            exc.printStackTrace();
        }
        catch (ParserConfigurationException exc) {
            // TODO Auto-generated catch block
            exc.printStackTrace();
        }
        for (int i = 0; i < xt.getRowCount(); i++)
            this.addElement(xt.getValueAt(i, 0));
    }
    
    public XmlTable getXmlTable() {
        return xt;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = lcr.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (xt.getValueAt(index, CAN_HOST_COLUMN).equals("0"))
            c.setForeground(DISABLED_COLOR);
        return c;
    }

    public boolean isPlayable(int selectedIndex) {
        return xt.getValueAt(selectedIndex, CAN_HOST_COLUMN).equals("1");
    }

    private XmlTable xt;
    private static final Color DISABLED_COLOR = Color.LIGHT_GRAY;
    private ListCellRenderer lcr;
}