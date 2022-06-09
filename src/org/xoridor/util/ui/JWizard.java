/*
 * Created on 11-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.ui;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JWizard<W extends JWizard> extends JFrame {
    public JWizard() {
        root.setLayout(new BorderLayout());
        root.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        getContentPane().add(root);
    }
    
    public void addPanel(JWizardPanel<W> panel) {
        panels.add(panel);
        if (panels.size() == 1)
            setPanel(0);
    }
    
    public void setPanel(int index) {
        root.removeAll();
        root.add(panels.get(index), BorderLayout.CENTER);
        root.add(panels.get(index).getButtonPanel(), BorderLayout.SOUTH);
        this.index = index;
        panels.get(index).requestFocus();
        pack();
    }
    
    public void nextPanel() {
        setPanel(index + 1);
    } 

    private JPanel root = new JPanel();
    private List<JWizardPanel<W>> panels = new LinkedList<JWizardPanel<W>>();
    private int index;
}