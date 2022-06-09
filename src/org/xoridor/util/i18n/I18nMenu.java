package org.xoridor.util.i18n;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class I18nMenu extends JMenu implements I18nListener {
   public I18nMenu (final I18nManager i18nManager) {
      ButtonGroup group = new ButtonGroup();
      for (final String language : i18nManager.getLanguages()) {
         JMenuItem mnuItem = new JRadioButtonMenuItem(language, i18nManager.getCurrentLanguage().equals(language)); 
         mnuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               i18nManager.setCurrentLanguage(language);
            }
         });
         mnuItemsByLanguage.put(language, mnuItem);
         add(mnuItem);
         group.add(mnuItem);
      }
      i18nManager.addI18nListener(this);
   }

   public void signalI18nChange(I18nManager i18nManager) {
       ((JMenuItem)mnuItemsByLanguage.get(i18nManager.getCurrentLanguage())).setSelected(true);
   }

   private Map<String, JMenuItem> mnuItemsByLanguage = new Hashtable<String, JMenuItem>();
}