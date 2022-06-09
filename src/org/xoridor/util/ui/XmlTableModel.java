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

import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xoridor.util.xml.XmlTable;

public class XmlTableModel extends AbstractTableModel {
    public XmlTableModel(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
        xmlTable = new XmlTable(inputStream);
    }
    
    public int getRowCount() {
        return xmlTable.getRowCount();
    }

    public int getColumnCount() {
        return xmlTable.getColumnCount();
    }

    public Object getValueAt(int row, int column) {
        return xmlTable.getValueAt(row, column);
    }
    
    private XmlTable xmlTable;
}