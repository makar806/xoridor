/*
 * Created on 5-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.ui;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.AbstractListModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xoridor.util.xml.XmlTable;

public class XmlListModel extends AbstractListModel {
    public XmlListModel(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
        this(new XmlTable(inputStream));
    }
    
    public XmlListModel(XmlTable xmlTable) {
        this.xmlTable = xmlTable;
    }
    
    public int getSize() {
      return xmlTable.getRowCount();
    }

    public Object getElementAt(int row) {
        return xmlTable.getValueAt(row, 0);
    }

    private XmlTable xmlTable;
}