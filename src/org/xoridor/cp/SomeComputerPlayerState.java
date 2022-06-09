package org.xoridor.cp;

import org.xoridor.core.Game;
import org.xoridor.core.State;

public abstract class SomeComputerPlayerState extends State {
    public SomeComputerPlayerState(Game game, ComputerPlayer cp) {
        super(game);
        this.cp = cp;
    }
    
    protected ComputerPlayer getComputerPlayer() {
        return cp;
    }
    
    private ComputerPlayer cp;
}
