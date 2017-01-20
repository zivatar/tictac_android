package com.example.teszt1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
    public static final CellLocation[][] SAMELINE = {
        { CellLocation.BOTTOM_CENTRE, CellLocation.BOTTOM_LEFT, CellLocation.BOTTOM_RIGHT },
        { CellLocation.TOP_CENTRE, CellLocation.TOP_LEFT, CellLocation.TOP_RIGHT },
        { CellLocation.CENTRE_CENTRE, CellLocation.CENTRE_LEFT, CellLocation.CENTRE_RIGHT },
        { CellLocation.TOP_LEFT, CellLocation.CENTRE_CENTRE, CellLocation.BOTTOM_RIGHT },
        { CellLocation.TOP_RIGHT, CellLocation.CENTRE_CENTRE, CellLocation.BOTTOM_LEFT },
        { CellLocation.TOP_LEFT, CellLocation.CENTRE_LEFT, CellLocation.BOTTOM_LEFT },
        { CellLocation.TOP_CENTRE, CellLocation.CENTRE_CENTRE, CellLocation.BOTTOM_CENTRE },
        { CellLocation.TOP_RIGHT, CellLocation.CENTRE_RIGHT, CellLocation.BOTTOM_RIGHT }
    }; 
        
    public Map<CellLocation, Integer> cellScores;

    public CellLocation suggest(GameBoard gb) {
        if ( gb.whoIsWinner() == CellState.OCCUPIED_BY_O ) throw new IllegalStateException("Winner: O");
        else if ( gb.whoIsWinner() == CellState.OCCUPIED_BY_X ) throw new IllegalStateException("Winner: X");
        else return findTheBestStep(gb);
    }

    public CellState otherPlayer(CellState np) {
        if (np == CellState.OCCUPIED_BY_X) return CellState.OCCUPIED_BY_O;
        else if (np == CellState.OCCUPIED_BY_O) return CellState.OCCUPIED_BY_X;
        return CellState.EMPTY;
    }
    
    public CellLocation findTheBestStep(GameBoard mgb) {
        cellScores = new HashMap<CellLocation, Integer>();

        miniMax(mgb, 0, 1);
            
        // find the location of the largest score
        int maxVal = Integer.MIN_VALUE;
        CellLocation maxLoc = CellLocation.CENTRE_CENTRE;
        for ( CellLocation loc : CellLocation.values() ) {
            if ( mgb.getCellState(loc) == CellState.EMPTY && cellScores.containsKey(loc) ) {
                int val = cellScores.get(loc);
                if ( val > maxVal ) {
                    maxVal = val;
                    maxLoc = loc;
                }
            }
        }
            
        return maxLoc;
    }
        
    public int miniMax(GameBoard mgb, int depth, int turn) {
        CellState winner = mgb.whoIsWinner();
        if ( winner == mgb.getOriginalPlayer() ) return 1;
        else if( winner == otherPlayer(mgb.getOriginalPlayer()) ) return -1;
           
        List<Integer> localScores = new ArrayList<Integer>(); 
        for ( CellLocation l : CellLocation.values()) {
            if ( mgb.getCellState(l) == CellState.EMPTY ) { // try empty cells only
                CellState actualPlayer = mgb.nextPlayer();
                mgb.setCellState(l, actualPlayer); // try the empty cell
                
                int sc = miniMax(mgb, depth + 1, 3-turn); // turn: 2->1 1->2
                localScores.add(sc);
                if ( actualPlayer == mgb.getOriginalPlayer() && depth == 0 ) cellScores.put(l, sc);
       
                mgb.clearCellState(l); // make the cell empty again
            }
        }
            
        if ( localScores.isEmpty() ) return 0;
        else if ( turn == 1 ) return Collections.max(localScores); // original player
        else return Collections.min(localScores); // other player
    }
}
