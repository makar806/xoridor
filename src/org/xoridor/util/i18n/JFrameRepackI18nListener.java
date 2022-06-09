/*
 * Created on 5-feb-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.i18n;

import javax.swing.JFrame;

public class JFrameRepackI18nListener extends I18nAdapter<JFrame> {
    public JFrameRepackI18nListener(JFrame t) {
        super(t, null);
    }

    public void signalI18nChange(JFrame t, String value) {
        t.pack();
    }
}