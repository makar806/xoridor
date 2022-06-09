package org.xoridor.core;

import java.awt.Color;

public class Player {
    public Player(String name, Color color, int goalRow) {
        setName(name);
        this.color = color;
        this.goalRow = goalRow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return getName();
    }

    private String name;
    
    private Color color;
    
    public Color getColor() {
    	return color;
    }

    private int goalRow;

    public int getGoalRow() {
        return goalRow;
    }

}
