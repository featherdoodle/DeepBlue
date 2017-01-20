/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Piece.Colour;
import chess.Piece.PawnMove;
import chess.Piece.PieceType;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 *
 * @author Owner
 */
public class Board {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public String P1Colour = ANSI_PURPLE;
    public String P2Colour = ANSI_GREEN;
    
    public Piece[][] pieces = new Piece[8][8];
    
    public WinnerState winnerState = WinnerState.UNFINISHED;
    
    public static enum WinnerState{
        UNFINISHED, PLAYER_ONE_WINS, PLAYER_TWO_WINS, TIE
    }
    
    public void setupBoard(){
        //initializing black pieces
        pieces[0][0] = new Piece(PieceType.ROOK, Colour.BLACK);
        pieces[0][1] = new Piece(PieceType.KNIGHT, Colour.BLACK);
        pieces[0][2] = new Piece(PieceType.BISHOP, Colour.BLACK);
        pieces[0][3] = new Piece(PieceType.QUEEN, Colour.BLACK);
        pieces[0][4] = new Piece(PieceType.KING, Colour.BLACK);
        pieces[0][5] = new Piece(PieceType.BISHOP, Colour.BLACK);
        pieces[0][6] = new Piece(PieceType.KNIGHT, Colour.BLACK);
        pieces[0][7] = new Piece(PieceType.ROOK, Colour.BLACK);
        
        for(int i = 0; i < 8; i++){
            pieces[1][i] = new Piece(PieceType.PAWN, Colour.BLACK);
        }
        //initializing white pieces
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
        
    }
    
    public void printBoard(){
        
        System.out.println(ANSI_RESET + " |0|1|2|3|4|5|6|7");
        for(int i = 0; i < 8; i++){
            System.out.print(i);
            System.out.print("|");
            for(int j = 0; j < 8; j++){
                
                if(pieces[i][j] == null){
                    System.out.print("\u3000");
                }else{
                    
                    PieceType pieceType = pieces[i][j].pieceType;
                    Colour colour = pieces[i][j].colour;
                    
                    if((pieceType == PieceType.PAWN)&&(colour == Colour.WHITE)){
                        System.out.print(P1Colour + "♙");
                    }else if((pieceType == PieceType.ROOK)&&(colour == Colour.WHITE)){
                        System.out.print(P1Colour + "♖");
                    }else if((pieceType == PieceType.KNIGHT)&&(colour == Colour.WHITE)){
                        System.out.print(P1Colour + "♘");
                    }else if((pieceType == PieceType.BISHOP)&&(colour == Colour.WHITE)){
                        System.out.print(P1Colour + "♗");
                    }else if((pieceType == PieceType.QUEEN)&&(colour == Colour.WHITE)){
                        System.out.print(P1Colour + "♕");
                    }else if((pieceType == PieceType.KING)&&(colour == Colour.WHITE)){
                        System.out.print(P1Colour + "♔");
                    }else if((pieceType == PieceType.PAWN)&&(colour == Colour.BLACK)){
                        System.out.print(P2Colour + "♟");
                    }else if((pieceType == PieceType.ROOK)&&(colour == Colour.BLACK)){
                        System.out.print(P2Colour + "♜");
                    }else if((pieceType == PieceType.KNIGHT)&&(colour == Colour.BLACK)){
                        System.out.print(P2Colour + "♞");
                    }else if((pieceType == PieceType.BISHOP)&&(colour == Colour.BLACK)){
                        System.out.print(P2Colour + "♝");
                    }else if((pieceType == PieceType.QUEEN)&&(colour == Colour.BLACK)){
                        System.out.print(P2Colour + "♛");
                    }else if((pieceType == PieceType.KING)&&(colour == Colour.BLACK)){
                        System.out.print(P2Colour + "♚");
                    }
                }
                System.out.print(ANSI_RESET + "|");
                
            }
            System.out.println();
        }
        
    }
    
    /**
     * Finding all the possible moves that a specific piece can make.
     * @param x
     * @param y
     * @return 
     */
    public ArrayList<Board> getPieceMoves(int x, int y){
        //CASTLING FOR WHITE AND EN PASSANT
        ArrayList<Board> moves = new ArrayList<>();
        
        if(pieces[y][x].pieceType == PieceType.PAWN){
            moves = getPawnMoves(moves, x, y);
        }else if(pieces[y][x].pieceType == PieceType.ROOK){
            moves = getRookMoves(moves, x, y);
            
            //clone the boards in the array list and add them
        }else if(pieces[y][x].pieceType == PieceType.KNIGHT){
            for(int i = -2; i <= 2; i+=4){
                for(int j = -1; j <= 1; j+=2){
                    if(checkBounds(x+i, y+j)){
                        if((pieces[y+j][x+i] == null)||(pieces[y+j][x+i].colour != pieces[y][x].colour)){
                            moves.add(makeMove(cloneBoard(this), x, y, x+i, y+j));
                        }
                    }if(checkBounds(x+j, y+i)){
                        if((pieces[y+i][x+j] == null)||(pieces[y+i][x+j].colour != pieces[y][x].colour)){
                            moves.add(makeMove(cloneBoard(this), x, y, x+j, y+i));
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
                            moves.add(makeMove(cloneBoard(this), x, y, x+i, y+j));
                            moves.get(moves.size()-1).pieces[y+j][x+i].castling = false;
                        }
                    }
                }
            }
            if(pieces[y][x].castling){//rook castling
                if(pieces[y][x].colour == Colour.BLACK){
                    if((pieces[0][0] != null)&&(pieces[0][0].castling)&&(pieces[0][1] == null)&&(pieces[0][2] == null)){
                        moves.add(makeMove(cloneBoard(this), x, y, 1, 0));
                        moves.set(moves.size()-1, makeMove(cloneBoard(moves.get(moves.size()-1)), 0, 0, 2, 0));
                        //set castling to false for that move.
                    }else if((pieces[0][7] != null)&&(pieces[0][7].castling)&&(pieces[0][4] == null)&&(pieces[0][5] == null)&&(pieces[0][6] == null)){
                        moves.add(makeMove(cloneBoard(this), x, y, 5, 0)); //check the numbers
                        moves.set(moves.size()-1, makeMove(cloneBoard(moves.get(moves.size()-1)), 7, 0, 4, 0));
                    }
                }else{
                    if((pieces[7][0] != null)&&(pieces[7][0].castling)&&(pieces[7][1] == null)&&(pieces[7][2] == null)){
                        moves.add(makeMove(cloneBoard(this), x, y, 1, 7));
                        moves.set(moves.size()-1, makeMove(cloneBoard(moves.get(moves.size()-1)), 0, 7, 2, 7));
                        //set castling to false for that move.
                    }else if((pieces[7][7] != null)&&(pieces[7][7].castling)&&(pieces[7][4] == null)&&(pieces[7][5] == null)&&(pieces[7][6] == null)){
                        moves.add(makeMove(cloneBoard(this), x, y, 5, 7)); //check the numbers
                        moves.set(moves.size()-1, makeMove(cloneBoard(moves.get(moves.size()-1)), 7, 7, 4, 7));
                    }
                }
            }
        }
        
        //returns null if the list is empty
        return moves;
    }
    
    public ArrayList<Board> getPawnMoves(ArrayList<Board> moves, int x, int y){
        int direction;
        if(pieces[y][x].colour == Colour.WHITE){
            direction = -1;
        }else{
            direction = 1;
        }
        
        if((checkBounds(x, y+direction)&&(pieces[y+direction][x] == null))){
                moves.add(moves.size(), makeMove(cloneBoard(this), x, y, x, y+direction));//i kinda feel like this is wrong
                moves.get(moves.size()-1).pieces[y+direction][x].pawnMove = PawnMove.TRUE;
                if((y+direction) == 0){
                    //moves.get(moves.size()-1).pieces[y-1][x].pieceType = PieceType.QUEEN;
                    for(PieceType i : PieceType.values()){
                        moves.get(moves.size()-1).pieces[y+direction][x].pieceType = i;
                        //when this happens, the user needs to be asked what they want to change the pawn to
                    }
                }
            }if((checkBounds(x, y+(direction*2))&&(pieces[y+(direction*2)][x] == null)&&(pieces[y][x].pawnMove == PawnMove.TRUE))){
                moves.add(moves.size(), makeMove(cloneBoard(this), x, y, x, y+(direction*2)));
                moves.get(moves.size()-1).pieces[y+(direction*2)][x].pawnMove = PawnMove.LAST_MOVE_TWO;
            }
            
            for(int i = -1; i <= 1; i+=2){//HOW DID A FOR LOOP GIVE A NULL POINTER
                 //this is a capture
                if((checkBounds(x+i, y+direction))&&(pieces[y+direction][x+i] != null)&&(pieces[x][y] != null)&&(pieces[y+direction][x+i].colour != pieces[x][y].colour)){
                    moves.add(makeMove(cloneBoard(this), x, y, x+i, y+direction));
                    if(((y+direction) == 0)||(y+direction == 7)){
                        for(PieceType j : PieceType.values()){ //small repetition
                            moves.get(moves.size()-1).pieces[y+direction][x].pieceType = j;
                            //need to add multiple moves, one for each piece type... oops
                            //when this happens, the user needs to be asked what they want to change the pawn to
                        }
                    }
                }else if((checkBounds(x+i, y+(2*direction)))&&(pieces[y+(2*direction)][x+i] != null)&&(pieces[y+(2*direction)][x+i].pawnMove == PawnMove.LAST_MOVE_TWO)&&(pieces[x][y] != null)&&(pieces[y+(2*direction)][x+i].colour != pieces[x][y].colour)){
                    moves.add(makeMove(cloneBoard(this), x, y, x+i, y+direction));
                    moves.get(moves.size()-1).pieces[y+(2*direction)][x+i] = null;
                    if(((y+direction) == 0)||(y+direction == 7)){
                        for(PieceType j : PieceType.values()){ //small repetition
                            moves.get(moves.size()-1).pieces[y+direction][x].pieceType = j;
                            //when this happens, the user needs to be asked what they want to change the pawn to
                        }
                    }
                }
                
            }
            
         
        return moves;
    }
    
    public ArrayList<Board> getRookMoves(ArrayList<Board> moves, int x, int y){
        //maybe take in the current array so it can be replaced instead for loops above
        
        int[] i = {-1, 1, 0, 0};
        int[] j = {0, 0, -1, 1};
        
        
        for(int index = 0; index < 4; index++){
            int n = 1;
            boolean empty = true;
            while(empty){

                if((checkBounds(x+i[index]*n, y+j[index]*n))&&(pieces[y+j[index]*n][x+i[index]*n] == null)){
                    moves.add(makeMove(cloneBoard(this), x, y, x+i[index]*n, y+j[index]*n));
                    moves.get(moves.size()-1).pieces[y+j[index]*n][x+i[index]*n].castling = false;
                }else if((checkBounds(x+i[index]*n, y+j[index]*n))&&(pieces[y][x].colour != pieces[y+j[index]*n][x+i[index]*n].colour)){
                    moves.add(makeMove(cloneBoard(this), x, y, x+i[index]*n, y+j[index]*n));
                    moves.get(moves.size()-1).pieces[y+j[index]*n][x+i[index]*n].castling = false;
                    empty = false;
                }else{
                    empty = false;
                }
                n++;
            }
        }
        return moves;
    }
    
    public ArrayList<Board> getBishopMoves(ArrayList<Board> moves, int x, int y){
        
        for(int i = -1; i <= 1; i+=2){
            for(int j = -1; j <= 1; j += 2){
                int n = 1;
                boolean empty = true;
                while(empty){

                    if((checkBounds(x+i*n, y+j*n))&&(pieces[y+j*n][x+i*n] == null)){
                        moves.add(makeMove(cloneBoard(this), x, y, x+i*n, y+j*n));
                    }else if((checkBounds(x+i*n, y+j*n))&&(pieces[y][x].colour != pieces[y+j*n][x+i*n].colour)){
                        moves.add(makeMove(cloneBoard(this), x, y, x+i*n, y+j*n));
                        empty = false;
                    }else{
                        empty = false;
                    }

                    n++;
                }
            }
        }
        return moves;
    }
    
    public Board makeMove(Board board, int x1, int y1, int x2, int y2){
        Board returnBoard = cloneBoard(board);
        returnBoard.pieces[y2][x2] = returnBoard.pieces[y1][x1];
        returnBoard.pieces[y1][x1] = null;

        return returnBoard;
    }
    
    public Board cloneBoard(Board board){
        if(board == null){
            return null;
        }
        Board returnBoard = new Board();
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                returnBoard.pieces[i][j] = clonePiece(board.pieces[i][j]);
            }
        }
        
        returnBoard.winnerState = board.winnerState;
        
        return returnBoard;
        
    }
    
    public Piece clonePiece(Piece piece){
        if(piece == null){
            return null;
        }else{
            Piece returnPiece = new Piece(piece.pieceType, piece.colour);
            returnPiece.castling = piece.castling;
            returnPiece.pawnMove = piece.pawnMove;
            return returnPiece;
        }
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
        int value = 0;
        int numberPieces = 64;
        EnumMap<Colour, EnumMap<PieceType, Integer>> pieceCount = getPieceCount();//watch references
        
        if(pieceCount.get(Colour.WHITE).get(PieceType.KING) == 0){
            return -100000;
        }if(pieceCount.get(Colour.BLACK).get(PieceType.KING) == 0){
            return 100000;
        }
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if((pieces[i][j] != null)&&(pieces[i][j].pieceType != PieceType.KING)){
                    value += getPieceValue(pieces[i][j]);
                }else{
                    numberPieces--; //subracting 1 for every null value
                }
            }
        }
        
        if(numberPieces <= 26){ // 6 pieces taken?
            value += getMidSquaresValue();
        }else if(numberPieces >= 6){//between 6 pieces taken, and 6 pieces left
            
        }else{
            value += getEndgameValue(numberPieces, pieceCount);
        }
        
        
        return value;
        
    }
    
    public int getEndgameValue(int numberPieces, EnumMap<Colour, EnumMap<PieceType, Integer>> pieceCount){
        //i know two of them are kings already
        int value = 0;
        
        if(numberPieces == 2){
            return 0; //its gotta be a draw
        }else if(numberPieces == 3){
            for(Colour i : Colour.values()){
                if(pieceCount.get(i).get(PieceType.BISHOP) > 0){
                    return 0;
                }else if(pieceCount.get(i).get(PieceType.KNIGHT) > 0){
                    return 0;
                }else if(pieceCount.get(i).get(PieceType.QUEEN) > 0){
                    return 3;//idk
                }else if(pieceCount.get(i).get(PieceType.ROOK) > 0){
                    return 1;
                }
            }
        }else if(numberPieces == 4){
            
        }
        return value;
    }
    
    public double getMidSquaresValue(){
        double value = 0;
        
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 4; j++){
                if((pieces[2+i][2+j] != null)&&(pieces[2+i][2+j].pieceType == PieceType.PAWN)&&(pieces[2+i][2+j].colour == Colour.BLACK)){
                    value -= 0.07;
                }if((pieces[4+i][2+j] != null)&&(pieces[4+i][2+j].pieceType == PieceType.PAWN)&&(pieces[4+i][2+j].colour == Colour.WHITE)){
                    value += 0.07;
                }
            }
        }
        for(int x = 3; x <= 4; x++){
            for(int y = 3; y <= 4; y++){
                for(int i = -2; i <= 2; i+=4){
                    for(int j = -1; j <= 1; j+=2){
                        if((pieces[y+j][x+i] != null)&&(pieces[y+j][x+i].pieceType == PieceType.KNIGHT)&&(pieces[y+j][x+i].colour == Colour.BLACK)){
                            value -= 0.07;
                        }
                        if((pieces[y+j][x+i] != null)&&(pieces[y+j][x+i].pieceType == PieceType.KNIGHT)&&(pieces[y+j][x+i].colour == Colour.WHITE)){
                            value += 0.07;
                        }
                    }
                }
            }
        }
        
        return value; // :(
    }
    
    public EnumMap<Colour, EnumMap<PieceType, Integer>> getPieceCount(){
        EnumMap<Colour, EnumMap<PieceType, Integer>> pieceCount = new EnumMap<>(Colour.class);
        
        for(Colour i : Colour.values()){
            pieceCount.put(i, new EnumMap<>(PieceType.class));
            for(PieceType j : PieceType.values()){
                pieceCount.get(i).put(j, 0);
            }
        }
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(pieces[i][j] != null){
                    int currentValue = pieceCount.get(pieces[i][j].colour).get(pieces[i][j].pieceType);
                    pieceCount.get(pieces[i][j].colour).put(pieces[i][j].pieceType, currentValue+1);
                }
            }
        }
        
        return pieceCount;
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
        if(object instanceof Board){
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    //super bad null checking. maybe throw the exception instead
                    if((pieces[i][j] == null)&&(((Board)object).pieces[i][j] != null)){
                        return false;
                    }else if((pieces[i][j] != null)&&(((Board)object).pieces[i][j] == null)){
                        return false;
                    }else if((pieces[i][j] == null)&&(((Board)object).pieces[i][j] == null)){
                        
                    }else if(!pieces[i][j].equals(((Board)object).pieces[i][j])){
                        return false;
                    }
                }
            }
            return true;
        }else{
            return false;
        }
    }
    
    public void updateWinnerState(){
        if(getBoardValue() >= 100000){ //should this check if the next move it will be this value? seems expensive
            winnerState = WinnerState.PLAYER_ONE_WINS;
        }else if(getBoardValue() <= -100000){
            winnerState = WinnerState.PLAYER_TWO_WINS;
        }else{
            //should do piece count and then determine if the pieces left are able to checkmate?
            //check tie, and then unfinished
            //that will be in the endgameValue method
        }
    }
    

}
