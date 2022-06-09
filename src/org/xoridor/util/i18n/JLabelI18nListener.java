/*
 * Created on 30-dec-2005.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.i18n;

import javax.swing.JLabel;

public class JLabelI18nListener extends I18nAdapter<JLabel> {
    public JLabelI18nListener(JLabel jc, String key) {
        super(jc, key);
    }
    
    public void signalI18nChange(JLabel t, String value) {
        t.setText(value);
    }
}
