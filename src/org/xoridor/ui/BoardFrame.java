package org.xoridor.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.xoridor.core.Game;
import org.xoridor.core.GameAdapter;
import org.xoridor.util.ui.JStatusBar;

public class BoardFrame extends JFrame {
    public BoardFrame(Game game, final Controller c) {
        super("Xoridor");
        this.setJMenuBar(new BoardFrameMenu(c, this));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JPanel boardPanel = new JPanel();
        bd = new BoardDisplay(game.getBoard(), this, c);
        boardPanel.setBorder(new javax.swing.border.EmptyBorder(5, 5, 5, 5));
        boardPanel.add(bd);
        panel.add(boardPanel);
        statusBarGameListener = new StatusBarGameListener(game, statusBar, c);
        c.getI18n().addI18nListener(statusBarGameListener);
        game.addListener(statusBarGameListener);
        game.setState(game.getState());
        panel.add(statusBar);
        game.addListener(new GameAdapter() {
            public void signalStart() {
                bd.refresh();
            }
        });
        getContentPane().add(panel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                c.signalBoardFrameClosed();
            }
        });
        pack();
        setVisible(true);
        game.addListener(new SoundPlayer(game));
    }

    public void setGame(Game game) {
        bd.setBoard(game.getBoard());
        game.addListener(statusBarGameListener);
        game.setState(game.getState());
        game.addListener(new SoundPlayer(game));
    }

    public BoardDisplay getBoardDisplay() {
        return bd;
    }

    public void setStatusBarShown(boolean b) {
        statusBar.setVisible(b);
        pack();
    }

    public void signalMessage(String msg) {
        statusBar.setMessage(msg);
    }
    
    private BoardDisplay bd;
    JStatusBar statusBar = new JStatusBar();
    private StatusBarGameListener statusBarGameListener;
}