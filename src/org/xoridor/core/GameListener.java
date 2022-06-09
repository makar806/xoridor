package org.xoridor.core;

public interface GameListener {
    void signalNextPlayer(Player activePlayer);

    void signalVictory(Player p);

    void signalFencePlaced(Player p, FencePosition fp);

    void signalPostionChange(Player p, BoardPosition bp);

    void signalStart();

    void signalState(State state);
}
