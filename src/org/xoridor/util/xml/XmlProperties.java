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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlProperties {
    public XmlProperties(URL url) throws SAXException, IOException, ParserConfigurationException {
        this(url.openStream());
    }
    
    public XmlProperties(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        root = document.getChildNodes().item(0);
    }
    
    public String getName() {
        return root.getNodeName();
    }
    
    public int getCount() {
        return root.getChildNodes().getLength();
    }

    public String getValue(String key) throws ValueNotFoundException {
        NodeList properties = root.getChildNodes();
        for (int i = 0; i < properties.getLength(); i++)
            if (properties.item(i).getNodeName().equals(key))
                return properties.item(i).getFirstChild().getNodeValue();
        throw new ValueNotFoundException();
    }
    
//    public String getValueAt(int row, int column) {
//        return root.getChildNodes().item(row).getChildNodes().item(column).getFirstChild().getNodeValue();
//    }
    
    Node root;
}