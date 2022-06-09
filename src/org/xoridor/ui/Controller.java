package org.xoridor.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.AccessControlException;

import javax.swing.JOptionPane;

import org.xoridor.Options;
import org.xoridor.core.Game;
import org.xoridor.ui.cmd.FirstGameCommand;
import org.xoridor.util.i18n.I18nListener;
import org.xoridor.util.i18n.I18nManager;
import org.xoridor.util.i18n.XMLI18nCatalogProvider;

public class Controller {
    public Controller() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream (XORIDOR_SAV));
            options = (Options)ois.readObject();
        }
        catch (InvalidClassException exc) {
        }
        catch (FileNotFoundException exc) {
        }
        catch (AccessControlException exc) {
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
        catch (ClassNotFoundException exc) {
        }
        finally {
            if (options == null)
                options = new Options();
        }
        i18n.addI18nListener(getI18nListener());
    }

    private I18nListener getI18nListener() {
        return new I18nListener() {
            public void signalI18nChange(I18nManager i8n) {
                options.setLanguage(i8n.getCurrentLanguage());
            }
        };
    }

    public void start() {
        FirstGameCommand fgc = new FirstGameCommand(this);
        fgc.execute();
        bf = fgc.getBoardFrame();
    }
    
    public Game getGame() {
        return game;
    }

    public Game getNewGame() {
        game = new Game(options);
        return game;
    }
    
    public void signalBoardFrameClosed() {
        exit();
    }

    public I18nManager getI18n() {
        if (i18n.getCurrentLanguage() == null)
            if (options.getLanguage() == null)
                i18n.setCurrentLanguage("English");
            else
                i18n.setCurrentLanguage(options.getLanguage());
        return i18n;
    }

    public void exit() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(XORIDOR_SAV));
            oos.writeObject(options);
            oos.close();
        }
        catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
        catch (AccessControlException exc) {
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
        System.exit(0);
    }    
    
    public Options getOptions() {
        return options;
    }

    public void setGame(Game game) {
        this.game = game;
        try {
            bf.setGame(game);
        }
        catch (NullPointerException exc) {
        }
    }
    
    public void showDialog(final String text) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(bf, text);
            }
        });
    }
    
    public void signalWarning(Exception exc) {
        signalDialog(exc, JOptionPane.WARNING_MESSAGE);
    }

    public void signalError(Exception exc) {
        exc.printStackTrace();
        signalDialog(exc, JOptionPane.ERROR_MESSAGE);
    }

    private void signalDialog(Exception exc, int type) {
        JOptionPane.showMessageDialog(bf, exc, getI18n().getValue("titleWarning"),type);
    }

    public void signalInfo(String key) {
        bf.signalMessage(getI18n().getValue(key));
    }

    public BoardFrame getBoardFrame() {
        return bf;
    }

    private BoardFrame bf;
    private final I18nManager i18n = new I18nManager(new XMLI18nCatalogProvider());
    private Options options;
    private static final String XORIDOR_SAV = "xoridor.sav";
    private Game game;
}