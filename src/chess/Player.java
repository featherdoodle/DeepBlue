

package chess;

import chess.Piece.Colour;
import java.io.Serializable;

/**
 *
 * @author Owner
 */
//abstract class that extends over Human and AI
public abstract class Player implements Serializable{
    
    final Colour colour;
    
    public Player(Colour _colour){
        colour = _colour;
    }
    
    public abstract Board move(Board board);
    
    @Override
    public abstract String toString();
}
