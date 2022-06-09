package org.xoridor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.xoridor.stats.GameStatistics;

public class Options implements Serializable {
	public int getNrOfPlayers() {
		return nrOfPlayers;
	}
	
	public void setTwoPlayers() {
		this.nrOfPlayers = 2;
	}

	public int getDimension() {
		return dimension;
	}

	public String getPlayerName(int no) {
		return playerNames[no];
	}

	public void setPlayerName(int no, String name) {
        if (!name.equals(COMPUTER_NAMES[0]) && !name.equals(COMPUTER_NAMES[1]) && name.length() > 0) {
            playerNames[no] = name;
            storeName(name);
        }
	}

    private void storeName(String name) {
        lastNames.add(name);
    }

	public int getAndAdvanceStartPlayer() {
		startPlayer = (startPlayer + 1) % nrOfPlayers;	
		return startPlayer;
	}
	
    public int getStartPlayer() {
        if (startPlayer > nrOfPlayers)
            startPlayer = startPlayer  % nrOfPlayers;  
        return startPlayer;
    }
    
	public int getMaxFences() {
		return nrOfPlayers == 2 ? 10 : 5;
	}

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }
    
    public Set<String> getLastNames() {
        return lastNames;
    }

    public GameStatistics getStatistics() {
        if (stats == null)
            stats = new GameStatistics();
        return stats;
    }
    
    public String getServer() {
        return server;
    }
    
    public void setServer (String server) {
        this.server = server;
    }

    public boolean isStatusBarShown() {
        return statusBarShown;
    }

    public void setStatusBarShown(boolean b) {
        statusBarShown = b;
    }

    private int nrOfPlayers = 2;
    
    private int dimension = 9;
    
    private String [] playerNames =
        new String [] {"A","B"};
    
    private int startPlayer = 0;
    private String language;
    private boolean sound = true;
    public static final String[] COMPUTER_NAMES = new String[] {"X", "Y"};
    private Set<String> lastNames = new HashSet<String>();
    private GameStatistics stats = new GameStatistics();
    private String server = "";
    private boolean statusBarShown = true;
}