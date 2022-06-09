/*
 * Created on 30-dec-2005.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.ui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xoridor.util.i18n.I18nManager;
import org.xoridor.util.i18n.JLabelI18nListener;

// TODO: fix in something a lot fancier than this
public class JTextDisplay extends JPanel {
    public JTextDisplay (String i8nKey, I18nManager i8n) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        i8n.addI18nListener(new JLabelI18nListener(caption, i8nKey));
        caption.setAlignmentX(CENTER_ALIGNMENT);
        add(caption);
        nameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        add(nameLabel);
    }
    
    public void setText (String text) {
        nameLabel.setText(text);
    }

    protected void setHighlight(boolean b) {
        setHighlight(caption, b);
        setHighlight(this, b);
        setHighlight(nameLabel, b);
        highlight = b;
    }
    
    private void setHighlight(JComponent jc, boolean b) {
        if (b != highlight) {
            Color fg = jc.getForeground();
            jc.setForeground(jc.getBackground());
            jc.setBackground(fg);
        }
    }

    private JLabel nameLabel = new JLabel();
    private JLabel caption = new JLabel();
    private boolean highlight;
}