/*
 * Created on 30-dec-2005.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.i18n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;

/*
 * Ugly XML code, based on old JAXP version.
 * 
 * Should be refactored to far more elegant xquery-like code.
 */
public class XMLI18nCatalogProvider implements I18nCatalogProvider {
   public XMLI18nCatalogProvider () {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = null;
      try {
         db = dbf.newDocumentBuilder();
      }
      catch (ParserConfigurationException pce) {
         pce.printStackTrace();
      }
      try {
         try {
//             document = db.parse(ClassLoader.getSystemResourceAsStream(LANG_FILE));
             document = db.parse(this.getClass().getClassLoader().getResourceAsStream(LANG_FILE));
            // Works for JRE both with JAR files and class files residing on file system.
            // Doesn't work in Eclipse, however :(
         }
         catch (FileNotFoundException exc) {
             document = db.parse(new File(LANG_FILE));
         }
         catch (IllegalArgumentException exc) {
//             System.out.println(LANG_FILE);
             document = db.parse(new FileInputStream(LANG_FILE));
         }
      }
      catch (org.xml.sax.SAXException exc) {
         exc.printStackTrace();
      }
      catch (java.io.IOException exc) {
         exc.printStackTrace();
      }
   }

   public List<String> getLanguages() {
      List<String> result = new LinkedList<String>();
      for (Node catalog = document.getFirstChild(); catalog != null; catalog = catalog.getNextSibling()) 
         if ((catalog.getNodeType() == Node.ELEMENT_NODE) && catalog.getNodeName().equals("catalog")) 
            for (Node ndLanguage = catalog.getFirstChild(); ndLanguage != null; ndLanguage = ndLanguage.getNextSibling()) 
               if ((ndLanguage.getNodeType() == Node.ELEMENT_NODE) && ndLanguage.getNodeName().equals("language")) 
                  result.add(ndLanguage.getAttributes().getNamedItem("name").getNodeValue());
      return result;
   }

   public String getResource(String language) {
      for (Node catalog = document.getFirstChild(); catalog != null; catalog = catalog.getNextSibling()) 
         if ((catalog.getNodeType() == Node.ELEMENT_NODE) && catalog.getNodeName().equals("catalog")) 
            for (Node ndLanguage = catalog.getFirstChild(); ndLanguage != null; ndLanguage = ndLanguage.getNextSibling()) 
               if ((ndLanguage.getNodeType() == Node.ELEMENT_NODE) && ndLanguage.getNodeName().equals("language")) 
                  if (ndLanguage.getAttributes().getNamedItem("name").getNodeValue().equals(language))
                     return ndLanguage.getAttributes().getNamedItem("resource").getNodeValue();
      throw new IllegalStateException("Language not found.");
   }

   public String getDefaultLanguage() {
      for (Node catalog = document.getFirstChild(); catalog != null; catalog = catalog.getNextSibling()) 
         if ((catalog.getNodeType() == Node.ELEMENT_NODE) && catalog.getNodeName().equals("catalog")) 
            for (Node ndLanguage = catalog.getFirstChild(); ndLanguage != null; ndLanguage = ndLanguage.getNextSibling()) 
               if ((ndLanguage.getNodeType() == Node.ELEMENT_NODE) && ndLanguage.getNodeName().equals("language")) 
                  return ndLanguage.getAttributes().getNamedItem("name").getNodeValue();
      throw new IllegalStateException("No languages found.");
   }
   
   private Node document;
   private final String LANG_FILE = "i18n/" + "languages.xml";
}
