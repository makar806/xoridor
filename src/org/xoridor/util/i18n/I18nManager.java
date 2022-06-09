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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class I18nManager {
   public I18nManager(I18nCatalogProvider i18nCatalogProvider) {
      this.i18nCatalogProvider = i18nCatalogProvider;
   }

   public List<String> getLanguages() {
      return i18nCatalogProvider.getLanguages();
   }

   public String getValue(String key, String language) {
      try {
         Properties properties = new Properties();
         try {
             properties.load(ClassLoader.getSystemResourceAsStream("i18n/" + i18nCatalogProvider.getResource(getCurrentLanguage())));
         }
         catch (NullPointerException exc) {
             // Files not available via class loader.
             // Maybe in Eclipse runtime?
             // Trying direct file access as fallback.
             properties.load(new FileInputStream("i18n" + File.separator + i18nCatalogProvider.getResource(getCurrentLanguage())));
         }
         return properties.getProperty(key);
      }
      catch (java.io.IOException exc) {
         exc.printStackTrace();
         return null;
      }
   }

   public String getValue(String key) {
       try {
           return getValue(key, getCurrentLanguage());
       }
       catch (NullPointerException exc) {
           return null;
       }
   }

   public String getCurrentLanguage() {
      return currentLanguage;
   }

   /** Sets the current language to a given language.
     */
   public void setCurrentLanguage(String currentLanguage) {
      this.currentLanguage = currentLanguage;
      signalI18nListeners(); 
   }

   private void signalI18nListeners() {
      for (Iterator iterator = i18nListeners.iterator(); iterator.hasNext(); )
         signalI18nListener((I18nListener)iterator.next());
   }

   private void signalI18nListener(I18nListener i18nListener) {
      if (getCurrentLanguage() != null)
         i18nListener.signalI18nChange(this);
   }

   public void addI18nListener(I18nListener i18nListener) {
      i18nListeners.add(i18nListener);
      signalI18nListener(i18nListener);
   }

   public void removeI18nListener(I18nListener i18nListener) {
      i18nListeners.remove(i18nListener);
   }
   
   private I18nCatalogProvider i18nCatalogProvider;
   private String currentLanguage;
   private List<I18nListener> i18nListeners = new LinkedList<I18nListener>();
}
