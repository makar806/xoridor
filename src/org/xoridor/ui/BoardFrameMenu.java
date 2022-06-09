package org.xoridor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.xoridor.ui.cmd.NewCommunityGameCommand;
import org.xoridor.ui.cmd.NewComputerGameCommand;
import org.xoridor.ui.cmd.NewTwoPlayerGameCommand;
import org.xoridor.util.i18n.AbstractButtonTextI18nListener;
import org.xoridor.util.i18n.I18nMenu;
import org.xoridor.util.i18n.JMenuI18nListener;

public class BoardFrameMenu extends JMenuBar {
    public BoardFrameMenu(final Controller c, BoardFrame boardFrame) {
        add(getFileMenu(c));
        add(getViewMenu(c, boardFrame));
        add(getExtraMenu(c));
        add(getHelpMenu(c));
    }

    private JMenu getFileMenu(final Controller c) {
        JMenu mnuGame = new JMenu();
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGame, "mnuGame-Mnemonic"));
        c.getI18n().addI18nListener(new AbstractButtonTextI18nListener(mnuGame, "mnuGame"));
        add(mnuGame);
        mnuGame.add(getFileNewMenu(c));
        mnuGame.add(getFileStatsMenu(c));
        mnuGame.add(getFileExitMenu(c));
        return mnuGame;
    }

    private JMenu getFileStatsMenu(final Controller c) {
        JMenu mnuGameStats = new JMenu();
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameStats, "mnuGameStats"));
        JMenuItem mnuGameStatsVictory = new JMenuItem();
        mnuGameStats.add(mnuGameStatsVictory);
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameStatsVictory, "mnuGameStatsVictory"));
        mnuGameStatsVictory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new VictoryStatsFrame(c).setVisible(true);
            }
        });
        return mnuGameStats;
    }

    private JMenuItem getFileExitMenu(final Controller c) {
        JMenuItem mnuGameExit = new JMenuItem();
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameExit, "mnuGameExit"));
        mnuGameExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                c.exit();
            }
        });
        return mnuGameExit;
    }

    private JMenu getFileNewMenu(final Controller c) {
        JMenu mnuGameNew = new JMenu();
        mnuGameNew.setMnemonic(KeyEvent.VK_N);
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameNew, "mnuGameNew"));
        JMenuItem mnuGameNewTwo = new JMenuItem();        
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameNewTwo, "mnuGameNewTwo"));
        mnuGameNewTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new NewTwoPlayerGameCommand(c).execute();
            }
        });
        mnuGameNew.add(mnuGameNewTwo);
        JMenuItem mnuGameNewFour = new JMenuItem();        
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameNewFour, "mnuGameNewFour"));
        mnuGameNew.add(mnuGameNewFour);
        JMenuItem mnuGameNewComputer = new JMenuItem();        
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameNewComputer , "mnuGameNewComputer"));
        mnuGameNewComputer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new NewComputerGameCommand(c).execute();
            }
        });
        mnuGameNew.add(mnuGameNewComputer);
        JMenu mnuGameNewOnline = new JMenu();
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameNewOnline, "mnuGameNewOnline"));
        JMenuItem mnuGameNewOnlineDirect = new JMenuItem();        
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameNewOnlineDirect , "mnuGameNewOnlineDirect"));
        mnuGameNew.add(mnuGameNewOnlineDirect);
        JMenuItem mnuGameNewOnlineCommunity = new JMenuItem();
        mnuGameNewOnlineCommunity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new NewCommunityGameCommand(c).execute();
            }
        });
        mnuGameNewOnline.add(mnuGameNewOnlineCommunity);
        JMenuItem mnuGameNewComputerVsComputer = new JMenuItem();        
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuGameNewComputerVsComputer , "mnuGameNewComputerVsComputer"));
        mnuGameNew.add(mnuGameNewComputerVsComputer);
        return mnuGameNew;
    }

    private JMenu getViewMenu(final Controller c, final BoardFrame boardFrame) {
        JMenu mnuView = new JMenu();
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuView, "mnuView"));
        add(mnuView);
        JMenu mnuViewLanguage = new I18nMenu(c.getI18n());
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuViewLanguage, "mnuViewLanguage"));
        mnuView.add(mnuViewLanguage);
        final JCheckBoxMenuItem mnuViewStatusBar = new JCheckBoxMenuItem();
        mnuViewStatusBar.setSelected(c.getNewGame().getOptions().isStatusBarShown());
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuViewStatusBar, "mnuViewStatusBar"));
        mnuViewStatusBar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                c.getOptions().setStatusBarShown(mnuViewStatusBar.isSelected());
                boardFrame.setStatusBarShown(mnuViewStatusBar.isSelected());
            }
         });
        mnuView.add(mnuViewStatusBar);
        return mnuView;
    }

    private JMenu getExtraMenu(final Controller c) {
        JMenu mnuExtra = new JMenu();
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuExtra, "mnuExtra"));
        add(mnuExtra);
        final JCheckBoxMenuItem mnuExtraSoundEnabled = new JCheckBoxMenuItem();
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuExtraSoundEnabled, "mnuExtraSound"));
        mnuExtraSoundEnabled.setSelected(c.getNewGame().getOptions().isSound());
        mnuExtraSoundEnabled.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent ae) {
               c.getOptions().setSound(mnuExtraSoundEnabled.isSelected());
           }
        });
        mnuExtra.add(mnuExtraSoundEnabled);
        return mnuExtra;
    }
    
    private JMenu getHelpMenu(final Controller c) { 
        JMenu mnuHelp = new JMenu();
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuHelp, "mnuHelp"));
        JMenuItem mnuHelpAbout = new JMenuItem();
        c.getI18n().addI18nListener(new JMenuI18nListener(mnuHelpAbout, "mnuHelpAbout"));
        mnuHelp.add(mnuHelpAbout);
        mnuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new AboutFrame(c);
            }
        });
        return mnuHelp;
    }
}