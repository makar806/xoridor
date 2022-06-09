/*
 * Created on 25-feb-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.i18n;

import javax.swing.JMenuItem;

public class JMenuI18nListener extends I18nAdapter<JMenuItem> {
    public JMenuI18nListener(JMenuItem t, String key) {
        super(t, key);
    }

    public void signalI18nChange(JMenuItem t, String value) {
        t.setText(value);
    }

    @Override
    public void signalI18nChange(I18nManager i18n) {
        super.signalI18nChange(i18n);
        getT().setMnemonic((i18n.getValue(getKey())+ SUFFIX).charAt(0));
    }
    
    private static final String SUFFIX = "-Mnemonic";
}
