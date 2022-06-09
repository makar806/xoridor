/*
 * Created on 5-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.ui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class JStatusBar extends JPanel {
    public JStatusBar() {
        add(label);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(Box.createHorizontalGlue());
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        setMessage("");
    }
    
    public void setMessage(String message) {
        label.setText(message);
    }
    
    private JLabel label = new JLabel();
}
