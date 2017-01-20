package com.example.teszt1;

import java.util.HashMap;
import java.util.Map;
import static com.example.teszt1.Solver.SAMELINE;

public class GameBoard {
    private Map<CellLocation, CellState> State = new HashMap<CellLocation, CellState>();
    
    private CellState originalPlayer;
    
    public GameBoard() {
        for( CellLocation loc : CellLocation.values() ) {
            this.State.put(loc, CellState.EMPTY);
        }
        this.originalPlayer = nextPlayer();
    }
    
    public CellState getCellState(CellLocation loc) {
        return this.State.get(loc);
    }
    
    public CellState getOriginalPlayer() {
        return this.originalPlayer;
    }
    
    public void setCellState(CellLocation loc, CellState st) {
        if ( this.getCellState(loc) == CellState.EMPTY ) {
            this.State.put(loc, st);
        }
        else {
            throw new IllegalStateException("Tried to modify a non-empty cell.");
        }
    }
    
    public void clearCellState(CellLocation loc) {
        this.State.put(loc, CellState.EMPTY);
    }
    
    public CellState whoIsWinner() {
        for (CellLocation[] line : SAMELINE) {
            CellState[] l = { this.getCellState(line[0]), this.getCellState(line[1]), this.getCellState(line[2]) };
            if (l[0] != CellState.EMPTY && l[0] == l[1] && l[0] == l[2]) {
                return l[0];
            }
        }
        return CellState.EMPTY;
    }
    
    public CellState nextPlayer() {
        int numX = 0, numO = 0, numEmpty = 0;
        for(CellLocation l : CellLocation.values()) {
            switch(this.getCellState(l)) {
                case OCCUPIED_BY_X:
                    numX++;
                    break;
                case OCCUPIED_BY_O:
                    numO++;
                    break;
                default:
                    numEmpty++;
                    break;
            }
        }
        if ( numEmpty == 0 ) throw new IllegalStateException("There is no empty place");
        else if ( numX <= numO ) return CellState.OCCUPIED_BY_X;
        else return CellState.OCCUPIED_BY_O;
     }
    
}
