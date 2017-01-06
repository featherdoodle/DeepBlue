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
    //CHECK ABOUT PARAMETERS
    //MAKE BOARD EQUALS AND CONTAIN METHODS
    public Piece[][] pieces = new Piece[8][8];
    //if im flipping the board, i need a boolean turn, or access to game's turn
    
    public WinnerState winnerState = WinnerState.UNFINISHED;
    
    public static enum WinnerState{
        UNFINISHED, PLAYER_ONE_WINS, PLAYER_TWO_WINS, TIE
    }
    
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
    
    public ArrayList<Board> getPieceMoves(int x, int y){
        //CASTLING FOR WHITE AND EN PASSANT
        //need method for which pieces can attack
        //check situations
        //lots of error checking == if out of bounds!
        ArrayList<Board> moves = new ArrayList<>();
        
        if(pieces[y][x].pieceType == PieceType.PAWN){
            if(pieces[y][x].colour == Colour.WHITE){
                if((checkBounds(x, y-1)&&(pieces[y-1][x] == null))){
                    moves.add(0, new Board()); //is this fixing one issue?
                    moves.set(0, makeMove(pieces, x, y, x, y-1));
                    if((y-1) == 0){
                        moves.get(0).pieces[y-1][x].pieceType = PieceType.QUEEN;
                    }
                }if((checkBounds(x, y-2)&&(pieces[y-2][x] == null)&&(pieces[y][x].moveTwo))){
                    moves.add(0, new Board()); //i did it here too 
                    moves.set(0, makeMove(pieces, x, y, x, y-2));
                    moves.get(0).pieces[y][x].moveTwo = false;
                }if((checkBounds(x-1, y-1))&&(pieces[y-1][x-1] != null)&&(pieces[y-1][x-1].colour == Colour.BLACK)){ //this is a capture
                    moves.add(0, makeMove(pieces, x, y, x-1, y-1));
                    if((y-1) == 0){
                        moves.get(0).pieces[y-1][x-1].pieceType = PieceType.QUEEN;
                    }
                }if((checkBounds(x+1, y-1))&&(pieces[y-1][x+1].colour == Colour.BLACK)){ //this is a capture
                    moves.add(0, makeMove(pieces, x, y, x+1, y-1));
                    if((y-1) == 0){
                        moves.get(0).pieces[y-1][x+1].pieceType = PieceType.QUEEN;
                    }
                }
                
            }else if(pieces[y][x].colour == Colour.BLACK){
                
                if((checkBounds(x, y+1))&&(pieces[y+1][x] == null)){
                    moves.add(0, makeMove(pieces, x, y, x, y+1));
                    if((y+1) == 0){
                        moves.get(0).pieces[y+1][x].pieceType = PieceType.QUEEN;
                    }
                }if((checkBounds(x, y+2))&&(pieces[y+2][x] == null)&&(pieces[y][x].moveTwo)){
                    moves.add(0, makeMove(pieces, x, y, x, y+2));
                    moves.get(0).pieces[y][x].moveTwo = false;
                }if((checkBounds(x, y+1))&&(pieces[y+1][x-1] != null)&&(pieces[y+1][x-1].colour == Colour.BLACK)){ //this is a capture
                    moves.add(0, makeMove(pieces, x, y, x-1, y+1));
                    if((y+1) == 0){
                        moves.get(0).pieces[y+1][x-1].pieceType = PieceType.QUEEN;
                    }
                }if((checkBounds(x+1, y+1))&&(pieces[y+1][x+1] != null)&&(pieces[y+1][x+1].colour == Colour.BLACK)){ //this is a capture
                    moves.add(0, makeMove(pieces, x, y, x+1, y+1));
                    if((y+1) == 0){
                        moves.get(0).pieces[y+1][x+1].pieceType = PieceType.QUEEN;
                    }
                }
                
            }
        }else if(pieces[y][x].pieceType == PieceType.ROOK){
            moves = getRookMoves(moves, x, y);
        }else if(pieces[y][x].pieceType == PieceType.KNIGHT){
            //knight is simple, but those if statements are long :o simplify?
            for(int i = -2; i <= 2; i+=4){
                for(int j = -1; j <= 1; j+=2){
                    if(checkBounds(x+i, y+j)){
                        if((pieces[y+j][x+i] == null)||(pieces[y+j][x+i].colour != pieces[y][x].colour)){
                            moves.add(0, makeMove(pieces, x, y, x+i, y+j));
                        }
                    }if(checkBounds(x+j, y+i)){
                        if((pieces[y+i][x+j] == null)||(pieces[y+j][x+i].colour != pieces[y][x].colour)){
                            moves.add(0, makeMove(pieces, x, y, x+j, y+i));
                        }
                    }
                }
            }
        }else if(pieces[y][x].pieceType == PieceType.BISHOP){
            moves = getBishopMoves(moves, x, y);
        }else if(pieces[y][x].pieceType == PieceType.QUEEN){
            moves = getRookMoves(moves, x, y);
            moves = getBishopMoves(moves, x, y);
        }else if(pieces[y][x].pieceType == PieceType.KING){
            for(int i = -1; i <= 1; i++){
                for(int j = -1; j <= 1; j++){
                    //this will try the current position, but that won't work
                    if(checkBounds(x+i, y+j)){
                        if((pieces[y+j][x+i] == null)||(pieces[y][x].colour != pieces[y+j][x+i].colour)){
                            moves.add(0, makeMove(pieces, x, y, x+i, y+j));
                            moves.get(0).pieces[y][x].castling = false;
                        }
                    }
                }
            }
            if((pieces[y][x].castling)&&(pieces[y][x].castling)){
                if(pieces[y][x].colour == Colour.BLACK){
                    if((pieces[1][0] == null)&&(pieces[2][0] == null)){
                        moves.add(0, makeMove(pieces, x, y, 1, 0));
                        moves.set(0, makeMove(moves.get(0).pieces, 0, 0, 2, 0));
                    }
                }else{
                    
                }
            }
        }
        //return null if the list is empty
        return moves;
    }
    
    public ArrayList<Board> getRookMoves(ArrayList<Board> moves, int x, int y){
        //maybe take in the current array so it can be replaced instead for loops above
        
        boolean empty = true;
        
        int[] i = {-1, 1, 0, 0};
        int[] j = {0, 0, -1, 1};
        
        
        for(int index = 0; index < 4; index++){
            while(empty){

                if((checkBounds(x+i[index], y+j[index]))&&(pieces[y+j[index]][x+i[index]] == null)){
                    moves.add(0, makeMove(pieces, x, y, x+i[index], y+j[index]));
                    moves.get(0).pieces[y][x].castling = false;
                }else if(pieces[y][x].colour != pieces[y+j[index]][x+i[index]].colour){
                    moves.add(0, makeMove(pieces, x, y, x+i[index], y+j[index]));
                    moves.get(0).pieces[y][x].castling = false;
                    empty = false;
                }else{
                    empty = false;
                }

            }
        }
        return moves;
    }
    
    public ArrayList<Board> getBishopMoves(ArrayList<Board> moves, int x, int y){
        
        boolean empty = true;
        
        for(int i = -1; i <= 1; i+=2){
            for(int j = -1; j <= 1; j += 2){
                while(empty){

                    if((checkBounds(x+i, y+j))&&(pieces[y+j][x+i] == null)){
                        moves.add(0, makeMove(pieces, x, y, x+i, y+j));
                    }else if(pieces[y][x].colour != pieces[y+j][x+i].colour){
                        moves.add(0, makeMove(pieces, x, y, x+i, y+j));
                        empty = false;
                    }else{
                        empty = false;
                    }

                }
            }
        }
        return moves;
    }
    
    public Board makeMove(Piece[][] currentPieces, int x1, int y1, int x2, int y2){
        Board returnBoard = new Board();
        returnBoard.pieces = currentPieces;
        returnBoard.pieces[y2][x2] = currentPieces[y1][x1];
        returnBoard.pieces[y1][x1] = null;
        
        return returnBoard;
        //check for checkmate here
    }
    
    public ArrayList<Board> refinePieceMoves(int x, int y){
        Colour colour = pieces[y][x].colour;
        
        ArrayList<Board> moves = getPieceMoves(x, y);
        ArrayList<Board> refinedMoves = new ArrayList<>();
        
        for(int i = 0; i < moves.size(); i++){
            if(colour == Colour.BLACK){
                if(getBoardValue() < 100000){
                    refinedMoves.add(0, moves.get(i));
                }
            }else{
                if(getBoardValue() > -100000){
                    refinedMoves.add(0, moves.get(i));
                }
            }
        }
        return refinedMoves;
    }
    
    public boolean checkBounds(int x, int y){
        if((x < 0)||(x >= 8)){
            return false;
        }if((y < 0)||(y >= 8)){
            return false;
        }
        return true;
    }
    
    public int getBoardValue(){
        int value = 0;//not 0 :P
        boolean whiteKing = false;
        boolean blackKing = false;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(pieces[i][j].pieceType != null){
                    if(pieces[i][j].pieceType == PieceType.KING){
                        if(pieces[i][j].colour == Colour.WHITE){
                            whiteKing = true;
                        }else{
                            blackKing = true;
                        }
                    }else{
                        value += getPieceValue(pieces[i][j]);
                    }
                }
            }
            
            if(!whiteKing){
                value = -100000;
            }else if(!blackKing){
                value = +100000;
            }
            
        }
        return value;
    }
    
    public static int getPieceValue(Piece piece){
        
        int value = 0;
        
        if(piece.pieceType == PieceType.PAWN){
            value = 1;
        }else if(piece.pieceType == PieceType.ROOK){
            value = 5;
        }else if(piece.pieceType == PieceType.BISHOP){
            value = 3;
        }else if(piece.pieceType == PieceType.KNIGHT){
            value = 3;
        }else if(piece.pieceType == PieceType.QUEEN){
            value = 9;
        }
        
        if(piece.colour == Colour.BLACK){
            value *= -1;
        }
        return value;
    }
    
    @Override
    public boolean equals(Object object){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(pieces[i][j] != this.pieces[i][j]){//is that proper use of this
                    return false;
                }
            }
        }
        return true;
    }
    
    public void updateWinnerState(){
        if(getBoardValue() >= 100000){
            winnerState = WinnerState.PLAYER_ONE_WINS;
        }else if(getBoardValue() <= -100000){
            winnerState = WinnerState.PLAYER_TWO_WINS;
        }else{
            //check tie, and then unfinished
        }
    }
    
    
    /*Individual pieces:

Pawn - 1 point
Knight - 3 points
Bishop - 3 points
Rook - 5 points
Queen - 9 points
Piece combinations:
Rook and Knight - 7.5 points
Rook and Bishop - 8 points
Pair of Rooks - 10 points
Three light pieces - 10 points
Rook and two light pieces - 11 points
http://chess.stackexchange.com/questions/2409/how-many-points-is-each-chess-piece-worth
*/
}
