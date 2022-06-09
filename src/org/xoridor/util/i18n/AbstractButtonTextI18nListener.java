/*
 * Created on 1-jan-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.i18n;

import javax.swing.AbstractButton;

public class AbstractButtonTextI18nListener extends I18nAdapter<AbstractButton> {
    public AbstractButtonTextI18nListener (AbstractButton t, String key) {
        super(t, key);
    }
    
    public void signalI18nChange(AbstractButton t, String value) {
        t.setText(value);
    }
}
