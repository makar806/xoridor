package org.xoridor.ui.cmd;

import javax.swing.JOptionPane;

import org.xoridor.ui.Controller;

public abstract class NewAbstractComputerGameCommand extends UICommand {
    public NewAbstractComputerGameCommand(Controller c) {
        super(c);
    }

    protected int getSelectedDifficulty(String message) {
        String easy = "Dumb";
        String average = "Smart";
        String hard = "Genius";
        String choosePcLevel = message;
        String difficulty = "Difficulty";
        Object[] level = { easy, average, hard};
        String selected = (String) JOptionPane.showInputDialog(c.getBoardFrame(), choosePcLevel, difficulty,  
                                    JOptionPane.QUESTION_MESSAGE,
                                    null, 
                                    level, 
                                    level[1]);
        if (selected != null) {
            if (selected.equals(level[0]))
                return 2;
            else if (selected.equals(level[1])) 
                return 3;
            else
                return 4;
        }
        return 3;
    }
}