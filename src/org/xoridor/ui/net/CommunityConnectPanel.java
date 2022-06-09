/*
 * Created on 11-mrt-2006.
 *
 * This software is published under the "GNU General Public
 * license", see http://www.gnu.org/copyleft/gpl.html for 
 * additional information.
 *
 */
package org.xoridor.ui.net;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import org.xoridor.net.CommunityException;
import org.xoridor.net.ConnectToCommunityCommand;
import org.xoridor.net.InvalidNameException;
import org.xoridor.ui.Controller;
import org.xoridor.ui.DefaultExceptionHandler;
import org.xoridor.util.cmd.Command;
import org.xoridor.util.cmd.DelayedCommand;
import org.xoridor.util.i18n.AbstractButtonTextI18nListener;
import org.xoridor.util.i18n.JLabelI18nListener;
import org.xoridor.util.token.Token;
import org.xoridor.util.token.TokenTakenException;
import org.xoridor.util.ui.JWizardPanel;

public class CommunityConnectPanel extends JWizardPanel<CommunityWizard> {
    public CommunityConnectPanel(final Controller c, final ConnectToCommunityCommand ctc, final CommunityWizard wizard) {
        super(wizard);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel basicPanel = new JPanel();
        add(basicPanel);
        JLabel label = new JLabel();
        c.getI18n().addI18nListener(new JLabelI18nListener(label, "lblCommunityName"));
        basicPanel.add(label);
        name = new JComboBox(c.getOptions().getLastNames().toArray());
        try {
            name.setSelectedItem(c.getGame().getPlayerName(0));
        }
        catch (NullPointerException exc) {
            // Nothing has to be done.
        }
        // TODO: correct name length checking.
        // name.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent arg0) {
        // refresh();
        // }
        // });
        // name.addItemListener(new ItemListener() {
        // public void itemStateChanged(ItemEvent arg0) {
        // refresh();
        // }
        // });
        // name.addKeyListener(new KeyAdapter() {
        // public void keyPressed(KeyEvent arg0) {
        // refresh();
        // }
        // });
        name.setEditable(true);
        label.setLabelFor(name);
        basicPanel.add(name);
        final JToggleButton advanced = new JToggleButton();
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(advanced, "btAdvanced"));
        basicPanel.add(advanced);
        final JPanel advancedPanel = getAdvancedPanel(c, ctc);
        advancedPanel.setVisible(false);
        advanced.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(advanced, 
                        advanced.isSelected() ? "btAdvancedExpanded" : "btAdvanced"));
                advancedPanel.setVisible(advanced.isSelected());
                getWizard().pack();
            }
        });
        add(advancedPanel);
        JButton cancel = new JButton();
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(cancel, "btCancel"));
        addButton(cancel);
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                close(c);
            }
        });
        connect = new JButton();
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(connect, "btConnect"));
        setDefaultButton(connect);
        addButton(connect);
        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                connect(ctc, c);
            }
        });
        getWizard().getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESCAPE");
        getWizard().getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                close(c);
            }
        });
        // refresh();
    }

    private JPanel getAdvancedPanel(Controller c, final ConnectToCommunityCommand ctc) {
        JPanel result = new JPanel();
        result.setLayout(new BoxLayout(result, BoxLayout.PAGE_AXIS));
        JPanel checkPanel = new JPanel();
        result.add(checkPanel);
        check = new JCheckBox();
        checkPanel.add(check);
        JLabel labelProxy = new JLabel();
        labelProxy.setLabelFor(check);
        c.getI18n().addI18nListener(new JLabelI18nListener(labelProxy, "lblProxy"));
        checkPanel.add(labelProxy);
        JPanel ipPanel = new JPanel();
        result.add(ipPanel);
        labelIp = new JLabel();
        c.getI18n().addI18nListener(new JLabelI18nListener(labelIp, "lblProxyIp"));
        ipPanel.add(labelIp);
        proxyIp = new JTextField(20);
        labelIp.setLabelFor(proxyIp);
        ipPanel.add(proxyIp);
        refreshProxyIp();
        check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ctc.setUseProxy(check.isSelected());
                refreshProxyIp();
            }
        });
        return result;
    }

    protected void close(Controller c) {
        close();
        c.signalInfo("infoCancel");
    }

    public synchronized void connect(final ConnectToCommunityCommand ctc, final Controller c) {
        try {
            connectingToken.request();
            c.signalInfo("infoConnecting");
            ctc.setProxyIp(proxyIp.getText());
            ctc.setUser(name.getSelectedItem().toString());
            c.getOptions().setPlayerName(0, ctc.getUser());
            new DelayedCommand<Exception>(new Command<Exception>() {
                public void execute() {
                    try {
                        ctc.execute();
                        c.signalInfo("infoConnected");
                        getWizard().addPanel(new CommunityListPanel(c, ctc, getWizard()));
                        getWizard().nextPanel();
                        getWizard().addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent arg0) {
                                getWizard().close(c, ctc);
                            }
                        });
                        getWizard().getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESCAPE");
                        getWizard().getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
                            public void actionPerformed(ActionEvent e) {
                                getWizard().close(c, ctc);
                            }
                        });
                    }
                    catch (CommunityException exc) {
                        // TODO Auto-generated catch block
                        exc.printStackTrace();
                    }
                    catch (NullPointerException exc) {
                        exc.printStackTrace();
                        c.signalWarning(exc);
                    }
                }
            }, new DefaultExceptionHandler<Exception>(c)).execute();
        }
        catch (InvalidNameException exc) {
            exc.printStackTrace();
        }
        catch (TokenTakenException exc) {
            // Nothing has to be done: connection is busy.
        }
    }

//     private void refresh() {
//         System.out.println("Refresh");
    // connect.setEnabled(name.getSelectedItem() != null &&
    // name.getSelectedItem().toString().length() > 0);
//     }

    private void refreshProxyIp() {
        proxyIp.setEnabled(check.isSelected());
        labelIp.setEnabled(check.isSelected());
    }

    private JLabel labelIp; 
    private JTextField proxyIp;
    private JCheckBox check; 
    private JButton connect;
    private JComboBox name;
    private Token connectingToken = new Token();
}