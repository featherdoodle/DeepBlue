/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Piece.Colour;
import chess.Piece.PieceType;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class Board {
    
    public Piece[][] pieces = new Piece[8][8];//am i making twice the number of new Pieces
    boolean turn;
    
    
    public void setupBoard(){
        //black
        pieces[0][0] = new Piece(PieceType.ROOK, Colour.BLACK);
        pieces[0][1] = new Piece(PieceType.KNIGHT, Colour.BLACK);
        pieces[0][2] = new Piece(PieceType.BISHOP, Colour.BLACK);
        pieces[0][3] = new Piece(PieceType.KING, Colour.BLACK);
        pieces[0][4] = new Piece(PieceType.QUEEN, Colour.BLACK);
        pieces[0][5] = new Piece(PieceType.BISHOP, Colour.BLACK);
        pieces[0][6] = new Piece(PieceType.KNIGHT, Colour.BLACK);
        pieces[0][7] = new Piece(PieceType.ROOK, Colour.BLACK);
        
        for(int i = 0; i < 8; i++){
            pieces[1][i] = new Piece(PieceType.PAWN, Colour.BLACK);
        }
        
        pieces[0][0].castling = true;
        //white
        pieces[7][0] = new Piece(PieceType.ROOK, Colour.WHITE);
        pieces[7][1] = new Piece(PieceType.KNIGHT, Colour.WHITE);
        pieces[7][2] = new Piece(PieceType.BISHOP, Colour.WHITE);
        pieces[7][3] = new Piece(PieceType.QUEEN, Colour.WHITE);
        pieces[7][4] = new Piece(PieceType.KING, Colour.WHITE);
        pieces[7][5] = new Piece(PieceType.BISHOP, Colour.WHITE);
        pieces[7][6] = new Piece(PieceType.KNIGHT, Colour.WHITE);
        pieces[7][7] = new Piece(PieceType.ROOK, Colour.WHITE);
        
        for(int i = 0; i < 8; i++){
            pieces[6][i] = new Piece(PieceType.PAWN, Colour.WHITE);
        }
        
        pieces[7][0].castling = true;
        
    }
    
    public void printBoard(){
        
        for(int i = 0; i < 8; i++){
            System.out.print("|");
            for(int j = 0; j < 8; j++){
                
                if(pieces[i][j] == null){
                    System.out.print("00");
                }else{
                    if(pieces[i][j].colour == Colour.BLACK){
                        System.out.print("B");
                    }else if(pieces[i][j].colour == Colour.WHITE){
                        System.out.print("W");
                    }

                    if(pieces[i][j].pieceType == PieceType.PAWN){
                        System.out.print("P");
                    }else if(pieces[i][j].pieceType == PieceType.ROOK){
                        System.out.print("R");
                    }else if(pieces[i][j].pieceType == PieceType.KNIGHT){
                        System.out.print("N");
                    }else if(pieces[i][j].pieceType == PieceType.BISHOP){
                        System.out.print("B");
                    }else if(pieces[i][j].pieceType == PieceType.QUEEN){
                        System.out.print("Q");
                    }else if(pieces[i][j].pieceType == PieceType.KING){
                        System.out.print("K");
                    }
                }
                System.out.print("|");
                
            }
            System.out.println();
        }
        
    }
    
    public int getBoardValue(){
        int value = 0;//not 0 :P
        
        return value;
    }
    
    public ArrayList<Board> getPieceMoves(int x, int y){
        ArrayList<Board> moves = new ArrayList<>();
        
        if(pieces[x][y].pieceType == PieceType.PAWN){
            
        }else if(pieces[x][y].pieceType == PieceType.ROOK){
            //can i loop this somehow
            //i need to check for opposite colour, but it needs to break when it hits a piece of the opp colour.
            int i = 1;
            while(pieces[x+i][y] == null){
                moves.add(0, new Board());
                moves.get(0).pieces[x+i][y] = pieces[x][y];
                moves.get(0).pieces[x][y] = null;
                i += 1;
            }
            i = -1;
            while(pieces[x+i][y] == null){
                moves.add(0, new Board());
                moves.get(0).pieces[x+i][y] = pieces[x][y];
                moves.get(0).pieces[x][y] = null;
                i -= 1;
            }
            i = 1;
            while(pieces[x][y+i] == null){
                moves.add(0, new Board());
                moves.get(0).pieces[x][y+i] = pieces[x][y];
                moves.get(0).pieces[x][y] = null;
                i += 1;
            }
            i = -1;
            while(pieces[x][y+i] == null){
                moves.add(0, new Board());
                moves.get(0).pieces[x][y+i] = pieces[x][y];
                moves.get(0).pieces[x][y] = null;
                i -= 1;
            }
                
        }else if(pieces[x][y].pieceType == PieceType.KNIGHT){
            
        }else if(pieces[x][y].pieceType == PieceType.BISHOP){
            
        }else if(pieces[x][y].pieceType == PieceType.QUEEN){
            //call bishop and rook somehow. split each piece into methods?
        }else if(pieces[x][y].pieceType == PieceType.KING){
            for(int i = -1; i <= 1; i++){
                for(int j = -1; j <= 1; j++){
                    //this will try the current position, but that won't work
                    if(pieces[x+i][y+j] == null){
                        //do i need to make sure it isn't checkmate? new method for that
                        moves.add(0, new Board());
                        moves.get(0).pieces[x+i][y+j] = pieces[x][y];
                        moves.get(0).pieces[x][y] = null;
                    }
                }
            }
        }
        
        return moves;
    }
    
}
