package org.xoridor.ui;

import java.net.MalformedURLException;

import org.xoridor.core.BoardPosition;
import org.xoridor.core.FencePosition;
import org.xoridor.core.Game;
import org.xoridor.core.GameAdapter;
import org.xoridor.core.Player;
import org.xoridor.util.URLProvider;

public class SoundPlayer extends GameAdapter {
    public SoundPlayer(Game game) {
        super(game);
    }
    
    public void signalVictory(Player p) {
        play("sounds/finished.wav");
    }

    public void signalFencePlaced(Player p, FencePosition fp) {
        play("sounds/fence.wav");
    }
    
    public void signalPostionChange(Player p, BoardPosition bp) {
        play("sounds/position.wav");
    }

    private void play(String file) {
        try {
            if (getGame().getOptions().isSound())
                java.applet.Applet.newAudioClip(new URLProvider().getURL(file)).play();
        }
        catch (MalformedURLException exc) {
        }
    }
}