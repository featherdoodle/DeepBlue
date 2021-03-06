

package chess;

import java.io.Serializable;

/**
 *
 * @author Owner
 */
public class Piece implements Serializable{
    
    Colour colour; 
    PawnMoveState pawnMoveState = null;
    boolean castling = false;
    PieceType pieceType;
    
    /**
     * Initializes the variables in piece.
     * pre: none
     * post: new Piece created
     */
    public Piece(PieceType _pieceType, Colour _colour){
        colour = _colour;
        pieceType = _pieceType;
        if(pieceType == PieceType.PAWN){
            pawnMoveState = PawnMoveState.MOVE_TWO;
        }
        if((pieceType == PieceType.KING)||(pieceType == PieceType.ROOK)){
            castling = true;
        }
        
    }
    
    public static enum PieceType{
        PAWN, ROOK, BISHOP, KNIGHT, QUEEN, KING;
    }
    
    /*if a pawn can move two squares forward, one square, or if its last move was
    two squares forward (used for enpassant)*/
    public static enum PawnMoveState{
        MOVE_ONE, MOVE_TWO, LAST_MOVE_TWO;
    }
    
    public static enum Colour{
        WHITE, BLACK;
        public static Colour swap(Colour colour){
            if(colour == Colour.BLACK){
                return Colour.WHITE;
            }else{
                return Colour.BLACK;
            }
        }
    }
    
    @Override
    public boolean equals(Object object){
        if(object instanceof Piece){
            if((((Piece)object).pieceType == pieceType)&&(((Piece)object).colour == colour)){
                if(pieceType == PieceType.PAWN){
                    return ((Piece)object).pawnMoveState == pawnMoveState; 
                }if((pieceType == PieceType.KING)||(pieceType == PieceType.ROOK)){
                    return ((Piece)object).castling == castling;
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        String output = "";
        output += colour + " " + pawnMoveState + " " + castling + " " + pieceType;
        return output;
    }
}
