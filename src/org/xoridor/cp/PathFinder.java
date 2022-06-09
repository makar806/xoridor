package org.xoridor.cp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xoridor.core.Board;
import org.xoridor.core.BoardPosition;
import org.xoridor.core.mc.NoFenceMoveChecker;

/** finds a path on a 2 dimensional grid filled with obstacles
 *
 * Use:
 *
 * PathFinder star = new PathFinder(grid); 
 *
 *
 * in loop for  computer player:
 *
 * Path p = star.findShortest(startRow,startCol,goalRow,goalCol);
 *
 */
public final class PathFinder {
    
    private final Board board;
    
    /** keep distances to each position here */
    private final int [][] distances;
    
    /** we only care about fences in the way */
    private final NoFenceMoveChecker noFenceMoveChecker;
    
    public PathFinder(Board board) {
        this.board = board;
        this.noFenceMoveChecker = new NoFenceMoveChecker(board);
        this.distances = new int [board.getNbRows()][board.getNbColumns()]; 
    }
    
    private void clear() {
        for (int i = 0 ; i < distances.length ; i++)
            for (int j = 0 ; j < distances[i].length ; j++)
                distances[i][j] = Integer.MAX_VALUE;
    }
    
    /** position on the grid */
    public final static class Position {
        public int row, col;
        public Position(int row, int col) {
            this.row = row; this.col = col;
        }
        public BoardPosition bp(Board b) {
            try {
                return new BoardPosition(b, col, row);
            } catch (Exception e) {
                return null;
            }
        }
        public String toString() {
            return  "(" + row + "," + col + ")";
        }
    }
    
    /** path from start to a certain position */
    public final static class Path {
        
        public int giveMove() {
            if (size() < 2)
                return -1;
            Position last = positions.get(size() - 1);
            Position forlast = positions.get(size() - 2);
            if (forlast.col - last.col == 1)
                return 2; //down
            else if (forlast.col - last.col == -1)
                return 0; //up
            else if (forlast.row - last.row == 1)
                return 1;//right
            else if (forlast.row - last.row == -1)
                return 3; //left
            return -1;
        }
        
        /** list of positions */
        public List<Position> positions;
        
        public int getCost() { return positions.size() - 1; }
        
        public int size() { return positions.size(); }
        
        public void add(Position o) { positions.add(o); }
        
        public void add(int r, int c) {
            positions.add(new Position(r,c));
        }
        
        public Position head() { return positions.get(0); }
        
        public Path(ArrayList<Position> positions) { this.positions = positions; }
        
        public Path(Position initial) {
            positions = new ArrayList<Position>(1);
            positions.add(initial);
        }
        
        public Path(int cap) { positions = new ArrayList<Position>(cap); }
        
        public void set(Position front, Path old) {
            positions.add(front);
            Iterator<Position> i = old.positions.iterator();
            while (i.hasNext())
                positions.add(i.next());
        }
        
        public String toString() {
            String str = "";
            for (Position pos: positions)
            	str += pos.toString() + " ";
            return str;   
        }
        public void print() {
            System.out.println(toString());
        }
    }
    
    
    public final Path findShortestPath(int startRow, int startCol, int goalRow) {
    	Path bestPath = null;
    	for (int goalCol = 0 ; goalCol < board.getNbColumns() ; goalCol++) {
            Path p = findShortestPath(startRow, startCol, goalRow, goalCol);
            if (p != null && (bestPath == null || bestPath.size() > p.size())) {
                bestPath = p;
            }
    	}
    	return bestPath;
    }
    
    public final int findShortest(int startRow, int startCol, int goalRow) {
    	return findShortestPath(startRow, startCol, goalRow).giveMove();
    }
  
    public final int findShortest(int startRow, int startCol, int goalRow, int goalCol) {
    	return findShortestPath(startRow, startCol, goalRow,goalCol).giveMove();
    }
    
    /** returns shortest path from startRow to startCol 0 up, 1 right, 2 down, 3 left*/
    public final Path findShortestPath
    (int startRow, int startCol, int goalRow, int goalCol) {
        clear();
        Position initial = new Position(startRow, startCol);
        Position goal = new Position(goalRow, goalCol);
        BinaireHoop list = new BinaireHoop(200, new Path(goal), goal);
        list.add(new Path(initial));
        Position [] successors = new Position[4];
        int trie = 0;
        for ( ; ! list.isEmpty() && trie < 20000  ; trie++) {
            Path first = (Path) list.remove();
            Position firstPos = first.head();
            int r = firstPos.row; int c = firstPos.col;
            if (r == goalRow && c == goalCol) {
                return first;
            } else {
                int successorsCount = 0;
                Position secondPos = new Position(r-1,c);            
                if (secondPos.bp(board) != null && noFenceMoveChecker.validMove(firstPos.bp(board), secondPos.bp(board))) {
                    successors[successorsCount++]
                    = secondPos;
                }
                secondPos = new Position(r+1,c);
                if (secondPos.bp(board) != null && noFenceMoveChecker.validMove(firstPos.bp(board), secondPos.bp(board))) {
                    successors[successorsCount++]
                    = secondPos;
                }
                secondPos = new Position(r,c-1);
                if (secondPos.bp(board) != null && noFenceMoveChecker.validMove(firstPos.bp(board), secondPos.bp(board))) {
                    successors[successorsCount++]
                    = secondPos;
                }
                secondPos = new Position(r,c+1);
                if (secondPos.bp(board) != null && noFenceMoveChecker.validMove(firstPos.bp(board), secondPos.bp(board))) {
                    successors[successorsCount++]
                    = secondPos;
                }
                
                while (successorsCount-- > 0) {
                    Position succ = successors[successorsCount];
                    int newPathCost = first.getCost() + 1;
                    if (distances[succ.row][succ.col] > newPathCost) { //shorter
                        distances[succ.row][succ.col] = newPathCost;
                        int pathSize = first.size() + 1;
                        Path newPath = new Path(pathSize);
                        newPath.set(succ, first);
                        list.add(newPath);
                    }
                }
                
            }
        }
        return null;
    }

}


