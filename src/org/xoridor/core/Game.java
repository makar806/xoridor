package org.xoridor.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.xoridor.Options;
import org.xoridor.util.Observeable;

public class Game extends Observeable<GameListener> {
    public State getState() {
        return state;
    }
    
    public void start() {
        for (GameListener gl : getListeners())
            gl.signalStart();
    }

    public Game(Options options) {
        super();
    	this.options = options;
        try {
            int nrOfPlayers = options.getNrOfPlayers();
            int dimension = options.getDimension();
            addPlayer(new Player(options.getPlayerName(0), Color.blue, dimension -1), new BoardPosition(getBoard(), dimension/2, 0));
            addPlayer(new Player(options.getPlayerName(1), Color.red, 0), new BoardPosition(getBoard(), dimension/2, dimension-1));
            }
        catch (InvalidBoardPositionException exc) {
            exc.printStackTrace();
        }
        // Initialise active player.
        activePlayer = options.getAndAdvanceStartPlayer(); //players.get(0);
        // Set initial state.
        setState(new OngoingLocalHumanPlayersOnlyGameState(this));
        addListener(stateListener);
    }

    public void setState(State state) {
        this.state = state;
        stateListener.setRemote(state);
        for (GameListener gl : getListeners())
            gl.signalState(state);
    }

    private void addPlayer(Player p, BoardPosition bp) {
        players.add(p);
        try {
            getBoard().setPosition(p, bp, false);
        }
        catch (InvalidMoveException exc) {
            exc.printStackTrace();
        }
        catch (IllegalGameStateException exc) {
            exc.printStackTrace();
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    void signalVictory(Player p) {
        for (GameListener gl : getListeners())
            gl.signalVictory(p);
    }
    
    void signalPositionChange(Player p, BoardPosition bp) {
        for (GameListener gl : getListeners())
            gl.signalPostionChange(p, bp);
        nextPlayer();
    }
    
    public void signalFencePlaced(Player p, FencePosition fp) {
        board.signalFencePlaced(p, fp);
        for (GameListener gl : getListeners())
            gl.signalFencePlaced(p, fp);
        nextPlayer();
    }

    public void nextPlayer() {
        setActivePlayer((activePlayer + 1) % getPlayers().size());
        for (GameListener gl : getListeners())
            gl.signalNextPlayer(getActivePlayer());
        Thread.yield();
    }

    public Player getActivePlayer() {
        return players.get(activePlayer);
    }

	public int getActivePlayerNb() {
		return activePlayer;
	}

    private void setActivePlayer(int no) {
        this.activePlayer = no;
    }

    public int getMaxFences() {
        return options.getMaxFences();
    }

    public void setPlayerName(int i, String name) {
        getPlayers().get(i).setName(name);
        options.setPlayerName(i, name);
    }
    
    public String getPlayerName(int i) {
        return getPlayers().get(i).getName();
    }

    public int getNbPlayers() {
        return getPlayers().size();
    }

    public Options getOptions() {
    	return options;
    }
   
    private Options options;
    private List<Player> players = new ArrayList<Player>();
    private Board board = new Board(this);
    // changed internal type
    private int activePlayer;
    private State state;
    private GameListenerProxy stateListener = new GameListenerProxy(null);
}