package org.xoridor.stats;

import java.io.Serializable;

public class PlayerVictories implements Comparable, Serializable {
    public PlayerVictories(String name) {
        this.name = name;
    }
    
    public int compareTo(Object other) {
        return ((PlayerVictories)other).victories - victories;
    }
    
    void signalVictory() {
        victories++;
    }
    
    public int getVictories() {
        return victories;
    }
    
    public String getName() {
        return name;
    }
    
    private int victories = 1;
    private String name;
}
