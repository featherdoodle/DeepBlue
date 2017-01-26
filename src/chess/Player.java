/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Piece.Colour;
import java.io.Serializable;

/**
 *
 * @author Owner
 */
public abstract class Player implements Serializable{
    
    final Colour colour;
    
    public Player(Colour _colour){
        colour = _colour;
    }
    
    public abstract Board move(Board board);
    @Override
    public abstract String toString();
}
