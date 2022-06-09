/*
 * Created on 12-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.ui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.xoridor.util.i18n.I18nListener;
import org.xoridor.util.i18n.I18nManager;

public class JMessagePanel extends JPanel implements I18nListener {
    public JMessagePanel(I18nManager i18n) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(label);
        this.i18n = i18n;
        i18n.addI18nListener(this);
        label.setBackground(new JLabel().getBackground());
        label.setForeground(new JLabel().getForeground());
        label.setEditable(false);
    }

    public void setMessage(String key) {
        this.key = key;
        label.setText(i18n.getValue(key));
    }
    
    public void signalI18nChange(I18nManager i18n) {
        setMessage(key);
    }

//    private JLabel label = new JLabel();
    private JTextPane label = new JTextPane ();
    private I18nManager i18n;
    private String key;
}