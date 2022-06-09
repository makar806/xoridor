package org.xoridor.stats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xoridor.core.Player;

public class GameStatistics implements Serializable {
    public String getTopPlayer(int i) throws NoSuchPlayerInStatisticsException {
        try {
            return victories.get(i).getName();
        }
        catch (IndexOutOfBoundsException exc) {
            throw new NoSuchPlayerInStatisticsException();
        }
    }

    public String getTopScore(int i) {
        return "" + victories.get(i).getVictories();
    }

    public void signalVictory(Player p) {
        boolean found = false;
        for (PlayerVictories pv : victories) 
            if (pv.getName().equals(p.getName())) {
                pv.signalVictory();
                found = true;
            }
        if (!found)
            victories.add(new PlayerVictories(p.getName()));
        Collections.sort(victories);
    }
    
    public int getNbVictoryPlayers() {
        return victories.size();
    }

    private List<PlayerVictories> victories = new ArrayList<PlayerVictories>();
}