/*
 * Created on 5-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlTable {
    public XmlTable(URL url) throws SAXException, IOException, ParserConfigurationException {
        this(url.openStream());
    }
    
    public String getTableName() {
        return root.getNodeName();
    }
    
    public XmlTable(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        root = document.getChildNodes().item(0);
    }
    
    public int getRowCount() {
        return root.getChildNodes().getLength();
    }

    public int getColumnCount() {
        return root.getChildNodes().item(0).getChildNodes().getLength();
    }

    public String getValueAt(int row, int column) {
        return root.getChildNodes().item(row).getChildNodes().item(column).getFirstChild().getNodeValue();
    }
    
    Node root;
}