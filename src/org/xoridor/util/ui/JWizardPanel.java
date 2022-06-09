/*
 * Created on 11-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.util.ui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JWizardPanel<JW extends JWizard> extends JPanel {
    public JWizardPanel(JW wizard) {
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.setBorder(new EmptyBorder(5,0,0,0));
        this.wizard = wizard;
    }

    protected JW getWizard() {
        return wizard;
    }
    
    public JPanel getButtonPanel() {
        return buttonPanel;
    }
    
    protected void addButton(JButton button) {
        if (buttonPanel.getComponentCount() > 0)
            buttonPanel.add(Box.createRigidArea(new Dimension(5,5)));
        buttonPanel.add(button);
    }

    protected void setDefaultButton(JButton button) {
        wizard.getRootPane().setDefaultButton(button);
    }

    protected void close() {
        getWizard().dispose();
    }

    private JPanel buttonPanel = new JPanel();
    private JW wizard;
}