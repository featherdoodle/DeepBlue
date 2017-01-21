/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Owner
 */
public class Piece {
    
    Colour colour; 
    PawnMove pawnMoveState = PawnMove.MOVE_TWO;
    boolean castling = false;
    PieceType pieceType;
    
    public Piece(PieceType _pieceType, Colour _colour){
        colour = _colour;
        pieceType = _pieceType;
        if(pieceType == PieceType.PAWN){
            pawnMoveState = PawnMove.MOVE_ONE;
        }
        if((pieceType == PieceType.KING)||(pieceType == PieceType.ROOK)){
            castling = true;
        }
        
    }
    
    public static enum PieceType{
        PAWN, ROOK, BISHOP, KNIGHT, QUEEN, KING;
    }
    
    public static enum PawnMove{
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
                if((((Piece)object).pawnMoveState == pawnMoveState)&&(((Piece)object).castling == castling)){
                    return true;
                }
            }
        }
        return false;
    }
    
}
