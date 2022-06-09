package org.xoridor.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.xoridor.core.GameListener;
import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;
import org.xoridor.core.FencePosition;
import org.xoridor.core.IllegalGameStateException;
import org.xoridor.core.InvalidFenceException;
import org.xoridor.core.InvalidMoveException;
import org.xoridor.core.Player;
import org.xoridor.core.State;

public class BoardDisplay extends JPanel
    implements GameListener
    {

    private static final int SPACE = 10;
    private static final int ROUNDING = 7;
    private static final int TILESIZE = 40;

    public void signalNextPlayer(Player activePlayer) {
    }

    public void signalVictory(Player p) {
        refresh();
    }

    public void signalFencePlaced(Player p, FencePosition fp) {
        refresh();
    }

    public void signalPostionChange(Player p, BoardPosition bp) {
        refresh();
    }

    public void signalStart() {
        refresh();
    }

    public void signalState(State state) {
        refresh();
    }
    
    public BoardDisplay(Board board, BoardFrame frame, final Controller c) {
        super();
        setBoard(board);
        this.addMouseMotionListener(new MouseMotionListener() {
        	public void mouseDragged(MouseEvent e) {
        	}
        	
 			public void mouseMoved(MouseEvent me) {
 				try {
 					BoardPosition pos = getBoardPosition(me.getX(), me.getY());
 					if (getBoard().getGame().getState().allowLocalMove() && getBoard().isValidMove(getBoard().getGame().getActivePlayer(), pos)) {
 						highLightedPlayer = getBoard().getGame().getActivePlayer();
 						highLightedMove = pos;
 						clearHighLightedFence();
 						refresh();
 					}
 					else {
 						clearHighLighted();
 						refresh();
 					}
 				}
 				catch (MouseOutOfTileException exco) {
 			 	 try {
                    FencePosition fence = getFencePosition(me.getX(), me.getY());
                    if (getBoard().getGame().getState().allowLocalMove() && getBoard().isValidFence(getBoard().getGame().getActivePlayer(), fence)) {
	                    highLightedFence = fence;
	                    clearHighLightedMove();
    	                refresh();
                    }
                    else {
                    	clearHighLighted();
                    	refresh();
                    }
                 }
                 catch (MouseOutOfFenceZoneException exc) {
                 	clearHighLighted();
                    refresh();
                 }
                }
 			}
        }
        	);
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                placePiece(me);
            }

            private void placePiece(MouseEvent me) {
                try {
                    getBoard().setPosition(getBoard().getGame().getActivePlayer(), 
                            getBoardPosition(me.getX(), me.getY()),
                            true);
                    clearHighLightedFence();
					refresh();
                }
                catch (InvalidMoveException exc) {
                }
                catch (IllegalGameStateException exc) {
                }
                catch (MouseOutOfTileException exc) {
                    placeFence(me);
                }
            }

            private void placeFence(MouseEvent me) {
                try {
                    getBoard().addFence(getBoard().getGame().getActivePlayer(), 
                            getFencePosition(me.getX(), me.getY()),
                            true);
                    clearHighLightedFence();
                    refresh();
                }
                catch (MouseOutOfFenceZoneException exc) {
                }
                catch (InvalidFenceException exc) {
                }
                catch (IllegalGameStateException exc) {
                }
            }
        });
        this.frame = frame;        
    }


	private void clearHighLighted() {
		clearHighLightedMove();
		clearHighLightedFence();
	}

	private void clearHighLightedMove() {
 		highLightedPlayer = null;
 		highLightedMove = null;		
	}

	private void clearHighLightedFence() {
        highLightedFence = null;
	}


    void setBoard(Board board) {
        this.board = board;
        board.getGame().addListener(this);
        repaint();
    }
    
    public void refresh() {
        this.repaint();
        frame.pack();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getPreferredWidth(),getPreferredHeight());
    }
    
    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintTileStructure(g2);
        paintPlayerStartBlocks(g2);
		paintPlayerTurnLine(g2);        	
        paintFencesLeft(g2);
        paintPieces(g2);
        paintFences(g2);
    }

 	
	private void paintPlayerStartBlocks(Graphics2D g2) {
		int dim = getBoard().getGame().getOptions().getDimension();
		List<Player> players = getBoard().getGame().getPlayers();
		Iterator<Player> iter = players.iterator();
		g2.setColor(iter.next().getColor());
        g2.draw(new RoundRectangle2D.Double(
        	    2*(getTileSize()+getSpace()), 0,  
                dim*(getTileSize()+getSpace()) - getSpace(), 2*(getTileSize()+getSpace()) - getSpace(), ROUNDING, ROUNDING));					
        g2.draw(new RoundRectangle2D.Double(
        	    2*(getTileSize()+getSpace())+1, 1,  
                dim*(getTileSize()+getSpace()) - getSpace()-2, 2*(getTileSize()+getSpace()) - getSpace()-2, ROUNDING, ROUNDING));				
        g2.setColor(iter.next().getColor());
        g2.draw(new RoundRectangle2D.Double(
        	    2*(getTileSize()+getSpace()), (dim+2)*(getTileSize()+getSpace()),  
                dim*(getTileSize()+getSpace()) - getSpace(), 2*(getTileSize()+getSpace()) - getSpace(), ROUNDING, ROUNDING));					
        g2.draw(new RoundRectangle2D.Double(
        	    2*(getTileSize()+getSpace()) + 1, (dim+2)*(getTileSize()+getSpace()) + 1,
                dim*(getTileSize()+getSpace()) - getSpace()-2, 2*(getTileSize()+getSpace()) - getSpace()-2, ROUNDING, ROUNDING));				                	
        if (iter.hasNext()) {
			g2.setColor(iter.next().getColor());
    	    g2.draw(new RoundRectangle2D.Double(
        	    0, 2*(getTileSize()+getSpace()),  
                2*(getTileSize()+getSpace()) - getSpace(), dim*(getTileSize()+getSpace()) - getSpace(),  ROUNDING, ROUNDING));					
     	    g2.draw(new RoundRectangle2D.Double(
        	    1, 2*(getTileSize()+getSpace()) + 1,  
                2*(getTileSize()+getSpace()) - getSpace() - 2, dim*(getTileSize()+getSpace()) - getSpace() - 2,  ROUNDING, ROUNDING));					
        	g2.setColor(iter.next().getColor());
        	g2.draw(new RoundRectangle2D.Double(
        	    (dim+2)*(getTileSize()+getSpace()), 2*(getTileSize()+getSpace()),   
                2*(getTileSize()+getSpace()) - getSpace(), dim*(getTileSize()+getSpace()) - getSpace(),  ROUNDING, ROUNDING));					
        	g2.draw(new RoundRectangle2D.Double(
        	    (dim+2)*(getTileSize()+getSpace()) + 1, 2*(getTileSize()+getSpace()) + 1, 
                2*(getTileSize()+getSpace()) - getSpace()-2, dim*(getTileSize()+getSpace()) - getSpace()-2,  ROUNDING, ROUNDING));				                	     		           	
        }
	}

	private void paintPlayerTurnLine(Graphics2D g2) {
		int dim = getBoard().getGame().getOptions().getDimension();
		Color color = getBoard().getGame().getActivePlayer().getColor();
		int nb = getBoard().getGame().getActivePlayerNb();
        g2.setPaint(color);
        switch (nb) {
        	case 0:
        	g2.fill(new RoundRectangle2D.Double(
        	    2*(getTileSize()+getSpace()), 0,  
                dim*(getTileSize()+getSpace()) - getSpace(), 2*(getTileSize()+getSpace()) - getSpace(), ROUNDING, ROUNDING));					        	
        	break;
        	case 1:
        	g2.fill(new RoundRectangle2D.Double(
        	    2*(getTileSize()+getSpace()), (dim+2)*(getTileSize()+getSpace()),  
                dim*(getTileSize()+getSpace()) - getSpace(), 2*(getTileSize()+getSpace()) - getSpace(), ROUNDING, ROUNDING));					
        	
        	break;
        	case 2:
        	g2.fill(new RoundRectangle2D.Double(
        	    0, 2*(getTileSize()+getSpace()),  
                2*(getTileSize()+getSpace()) - getSpace(), dim*(getTileSize()+getSpace()) - getSpace(),  ROUNDING, ROUNDING));					
     	    
        	break;
        	default:
        	g2.fill(new RoundRectangle2D.Double(
        	    (dim+2)*(getTileSize()+getSpace()), 2*(getTileSize()+getSpace()),   
                2*(getTileSize()+getSpace()) - getSpace(), dim*(getTileSize()+getSpace()) - getSpace(),  ROUNDING, ROUNDING));					   	
        	break;
        }
        	
	}

    private void paintFencesLeft(Graphics2D g2) {
    	int no = 0;
     	for (Player p: board.getGame().getPlayers()) {
     		int left = board.getNbAllowedFences(p);
     		int dim = board.getGame().getOptions().getDimension();
     		int start = 1 + (dim - left) / 2;  
     		g2.setColor(Color.black);
     		switch (no) {
     			case 0:
     				for (int i = 1 ; i <= left ; i++) {
				    	g2.fill(
        					new RoundRectangle2D.Double((getTileSize() + getSpace())*(i+start) - getSpace(), 0, 
        					SPACE, getTileSize() * 2 + SPACE, 
        					ROUNDING, ROUNDING));		
     				}
     				break;
     			case 1:
     				for (int i = 1 ; i <= left ; i++) {
				    	g2.fill(
        					new RoundRectangle2D.Double((getTileSize() + getSpace())*(i+start) - getSpace(), (getTileSize() + getSpace())*(getBoard().getGame().getOptions().getDimension()+2), 
        					SPACE, getTileSize() * 2 + SPACE, 
        					ROUNDING, ROUNDING));		
     				}     
     				break;			
     			case 2:
     				for (int i = 1 ; i <= left ; i++) {
				    	g2.fill(
        					new RoundRectangle2D.Double(0, (getTileSize() + getSpace())*(i+start) - getSpace(), 
        					getTileSize() * 2 + SPACE, SPACE,  
        					ROUNDING, ROUNDING));		
     				}  
     				break;   			
     			default:
     				for (int i = 1 ; i <= left ; i++) {
				    
				    	g2.fill(
        					new RoundRectangle2D.Double((getTileSize() + getSpace())*(getBoard().getGame().getOptions().getDimension()+2), (getTileSize() + getSpace())*(i+start) - getSpace(),
        					getTileSize() * 2 + SPACE, SPACE, 
        					ROUNDING, ROUNDING));		
     				}   
     				break;  			     			
     		}
     		++no;
     	}
     		
    }

    private void paintFences(Graphics2D g2) {
    	if (highLightedFence != null)
    		drawFenceBound(g2, highLightedFence);
        for (FencePosition fp : getBoard().getFences())
            drawFence(g2, fp);
    }
    
    private void drawFenceBound(Graphics2D g2, FencePosition fp) {
        g2.setColor(Color.black);
        if (fp.getDirection() == FencePosition.VERTICAL) {
            g2.draw(new RoundRectangle2D.Double(getFencePositionX(fp), getFencePositionY(fp), 
                    SPACE, getTileSize() * 2 + SPACE, ROUNDING, ROUNDING));
            g2.draw(new RoundRectangle2D.Double(getFencePositionX(fp) + 1, getFencePositionY(fp) + 1, 
                    SPACE-2, getTileSize() * 2 + SPACE - 2, ROUNDING, ROUNDING));
        }
        else {
            g2.draw(new RoundRectangle2D.Double(getFencePositionX(fp), getFencePositionY(fp), getTileSize() * 2 + SPACE, 
                    SPACE, ROUNDING, ROUNDING));
            g2.draw(new RoundRectangle2D.Double(getFencePositionX(fp) + 1, getFencePositionY(fp) + 1, getTileSize() * 2 + SPACE - 2, 
                    SPACE-2, ROUNDING, ROUNDING));
       }
    }    
    
    private void drawFence(Graphics2D g2, FencePosition fp) {
        g2.setColor(Color.black);
        if (fp.getDirection() == FencePosition.VERTICAL)
            g2.fill(new RoundRectangle2D.Double(getFencePositionX(fp), getFencePositionY(fp), SPACE, 
                    getTileSize() * 2 + SPACE, ROUNDING, ROUNDING));
        else
            g2.fill(new RoundRectangle2D.Double(getFencePositionX(fp), getFencePositionY(fp), getTileSize() * 2 + SPACE, 
                    SPACE, ROUNDING, ROUNDING));
    }

    private double getFencePositionY(FencePosition fp) {
        if (fp.getDirection() == FencePosition.VERTICAL)
            return getTilePositionY(fp);
        else
            return getTilePositionY(fp) + getTileSize();
    }

    private double getFencePositionX(FencePosition fp) {
        if (fp.getDirection() == FencePosition.VERTICAL)
            return getTilePositionX(fp) + getTileSize();
        else
            return getTilePositionX(fp);
    }

    private void paintPieces(Graphics2D g2) {
        for (Player p : getBoard().getGame().getPlayers())
            drawPiece(g2, p);
        if (highLightedPlayer != null && highLightedMove != null) {      			
       			g2.setPaint(highLightedPlayer.getColor());
        		g2.fill(
        			new RoundRectangle2D.Double(
        				getTilePositionX(highLightedMove.getX()) + getTileSize()/8, 
        			    getTilePositionY(highLightedMove.getY()) + getTileSize()/8,  
                        3*getTileSize()/4, 3*getTileSize()/4, ROUNDING, ROUNDING));
        		g2.setPaint(Color.white);
        		g2.setFont(g2.getFont().deriveFont((float)3*getTileSize()/4));
        		g2.drawString("" + highLightedPlayer.getName().charAt(0), 
                getTilePositionX(highLightedMove.getX()) + getTileSize() /  4, 
                getTilePositionY(highLightedMove.getY()) + 3 * getTileSize() /  4);               	
        }
    }

    private void paintTileStructure(Graphics2D g2) {
        for (int x = 0; x < getBoard().getNbColumns(); x++)
            for (int y = 0; y < getBoard().getNbRows(); y++)
                drawTile(g2, x, y);
    }

    private void drawPiece(Graphics2D g2, Player p) {
        g2.setPaint(p.getColor());
        g2.fill(new RoundRectangle2D.Double(getTilePositionX(getBoard().getPosition(p).getX()) + getTileSize()/8, getTilePositionY(getBoard().getPosition(p).getY()) + getTileSize()/8,  
                3*getTileSize()/4, 3*getTileSize()/4, ROUNDING, ROUNDING));
        g2.setPaint(Color.black);
        g2.setFont(g2.getFont().deriveFont((float)3*getTileSize()/4));
        g2.drawString("" + p.getName().charAt(0), 
                getTilePositionX(getBoard().getPosition(p).getX()) + getTileSize() /  4, 
                getTilePositionY(getBoard().getPosition(p).getY()) + 3 * getTileSize() /  4);
    }

    private void drawTile(Graphics2D g2, int x, int y) {
        g2.setPaint(Color.gray);
        g2.fill(new RoundRectangle2D.Double(getTilePositionX(x), getTilePositionY(y), getTileSize(), 
                getTileSize(), ROUNDING, ROUNDING));
    }
    
    private BoardPosition getBoardPosition(int x, int y) throws MouseOutOfTileException {
        for (BoardPosition bp : getBoard().getAllPositions())
            if (isCursorInPosition (x, y, bp))
                return bp;
        throw new MouseOutOfTileException();
    }
    
    private FencePosition getFencePosition(int x, int y) throws MouseOutOfFenceZoneException {
        for (FencePosition fp : getBoard().getAllFencePositions())
            if (isCursorInFencePosition (x, y, fp))
                return fp;
        throw new MouseOutOfFenceZoneException();        
    }

    private boolean isCursorInFencePosition(int x, int y, FencePosition fp) {
        if (fp.getDirection() == FencePosition.VERTICAL)
            return (x > (getTilePositionX(fp) + getTileSize())) && 
                   (x < (getTilePositionX(fp) + getTileSize() + getSpace())) &&
                   (  
                   ((y > getTilePositionY(fp) + getTileSize() / 2) && 
                   (y < (getTilePositionY(fp) + getTileSize())))
                   ||
                   ((y > getTilePositionY(fp) + getTileSize() + getSpace()) && 
                   (y < (getTilePositionY(fp) + getTileSize() + getSpace() + getTileSize() / 2)))
                   )
                   ;
        else 
            return (y > (getTilePositionY(fp) + getTileSize())) && 
                   (y < (getTilePositionY(fp) + getTileSize() + getSpace())) && 
                   (
                   ((x > getTilePositionX(fp) + getTileSize() / 2) && 
                   (x < (getTilePositionX(fp) + getTileSize())))
                   ||
                   (
                   ((x > getTilePositionX(fp) + getTileSize() + getSpace()) && 
                   (x < (getTilePositionX(fp) + getTileSize() + getSpace() + getTileSize() / 2)))                   	
                   )
                   );
    }

    private boolean isCursorInPosition (int x, int y, BoardPosition bp) {
        return (x  > getTilePositionX(bp)) && (x < (getTilePositionX(bp) + getTileSize()))
            && (y > getTilePositionY(bp)) && (y < (getTilePositionY(bp) + getTileSize()));
    }
    
    private float getTilePositionY(BoardPosition bp) {
        return getTilePositionY(bp.getY());
    }

    private float getTilePositionX(BoardPosition bp) {
        return getTilePositionX(bp.getX());
    }

    private float getTilePositionY(int y) {
    	return (y + 2) * (getTileSize() + getSpace());
    }

    private float getTilePositionX(int x) {
        return (x + 2) * (getTileSize() + getSpace());
    }

    private int getTileSize() {
        return TILESIZE;
    }

    private int getPreferredWidth() {
        return (getTileSize() + getSpace()) * (getBoard().getNbColumns() + 4) - getSpace() + 1;
    }

    private int getSpace() {
        return SPACE;
    }

    private Board getBoard() {
        return board;
    }

    private int getPreferredHeight() {
        return (getTileSize() + getSpace())  * (getBoard().getNbRows() + 4) - getSpace() + 1;
    }

    private Board board;
    private BoardFrame frame;
    private Player highLightedPlayer = null;
	private BoardPosition highLightedMove = null;
	private FencePosition highLightedFence = null;
}
