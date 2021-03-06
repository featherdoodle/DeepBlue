

package chess;

import chess.Piece.Colour;
import chess.Piece.PawnMoveState;
import chess.Piece.PieceType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

/**
 *
 * @author Owner
 */
public class Board implements Serializable{

    //variables used to change text colours
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public Piece[][] pieces = new Piece[8][8];

    boolean turn; //true if it is white's turn, false if it is black's turn
    
    public static enum WinnerState {
        UNFINISHED, PLAYER_ONE_WINS, PLAYER_TWO_WINS, STALEMATE
    }

    /**
     * Does the initial setup of the board. Initializes all the pieces and puts
     * them in the array.
     * beginning values.
     * pre: none
     * post: pieces[][] is filled with the starting values
     */
    public void setupBoard() {
        //initializing black pieces
        pieces[0][0] = new Piece(PieceType.ROOK, Colour.BLACK);
        pieces[0][1] = new Piece(PieceType.KNIGHT, Colour.BLACK);
        pieces[0][2] = new Piece(PieceType.BISHOP, Colour.BLACK);
        pieces[0][3] = new Piece(PieceType.QUEEN, Colour.BLACK);
        pieces[0][4] = new Piece(PieceType.KING, Colour.BLACK);
        pieces[0][5] = new Piece(PieceType.BISHOP, Colour.BLACK);
        pieces[0][6] = new Piece(PieceType.KNIGHT, Colour.BLACK);
        pieces[0][7] = new Piece(PieceType.ROOK, Colour.BLACK);
        
        for (int i = 0; i < 8; i++) {
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

        for (int i = 0; i < 8; i++) {
            pieces[6][i] = new Piece(PieceType.PAWN, Colour.WHITE);
        }
        
        turn = true; //it starts as white's turn
    }

    /**
     * Iterates through the board and prints out the pieces. 
     * pre: none 
     * post: board is printed to the console.
     */
    public void printBoard() {

        System.out.println(ANSI_RESET + " \u30000\u30001\u30002\u30003\u30004\u30005\u30006\u30007");
        for (int i = 0; i < 8; i++) {
            System.out.print(i);
            System.out.print("|");
            for (int j = 0; j < 8; j++) {

                if (pieces[i][j] == null) {
                    System.out.print("\u3000");
                } else {

                    PieceType pieceType = pieces[i][j].pieceType;
                    Colour colour = pieces[i][j].colour;

                    if ((pieceType == PieceType.PAWN) && (colour == Colour.WHITE)) {
                        System.out.print(ANSI_PURPLE + "♙");
                    } else if ((pieceType == PieceType.ROOK) && (colour == Colour.WHITE)) {
                        System.out.print(ANSI_PURPLE + "♖");
                    } else if ((pieceType == PieceType.KNIGHT) && (colour == Colour.WHITE)) {
                        System.out.print(ANSI_PURPLE + "♘");
                    } else if ((pieceType == PieceType.BISHOP) && (colour == Colour.WHITE)) {
                        System.out.print(ANSI_PURPLE + "♗");
                    } else if ((pieceType == PieceType.QUEEN) && (colour == Colour.WHITE)) {
                        System.out.print(ANSI_PURPLE + "♕");
                    } else if ((pieceType == PieceType.KING) && (colour == Colour.WHITE)) {
                        System.out.print(ANSI_PURPLE + "♔");
                    } else if ((pieceType == PieceType.PAWN) && (colour == Colour.BLACK)) {
                        System.out.print(ANSI_GREEN + "♟");
                    } else if ((pieceType == PieceType.ROOK) && (colour == Colour.BLACK)) {
                        System.out.print(ANSI_GREEN + "♜");
                    } else if ((pieceType == PieceType.KNIGHT) && (colour == Colour.BLACK)) {
                        System.out.print(ANSI_GREEN + "♞");
                    } else if ((pieceType == PieceType.BISHOP) && (colour == Colour.BLACK)) {
                        System.out.print(ANSI_GREEN + "♝");
                    } else if ((pieceType == PieceType.QUEEN) && (colour == Colour.BLACK)) {
                        System.out.print(ANSI_GREEN + "♛");
                    } else if ((pieceType == PieceType.KING) && (colour == Colour.BLACK)) {
                        System.out.print(ANSI_GREEN + "♚");
                    }
                }
                System.out.print(ANSI_RESET + "|");
            }
            System.out.println();
        }

    }
    
    //for printing in the jar :)
    /*public void printBoard(){
        System.out.println("  0  1  2  3  4  5  6  7");
        for(int i = 0; i < 8; i++){
            System.out.print(i);
            System.out.print("|");
            for(int j = 0; j < 8; j++){
                if(pieces[i][j] == null){
                    System.out.print("  ");
                }else{
                    
                    PieceType pieceType = pieces[i][j].pieceType;
                    
                    if(pieces[i][j].colour == Colour.BLACK){
                        System.out.print("B");
                    }else{
                        System.out.print("W");
                    }
                    
                    if(pieceType == PieceType.PAWN){
                        System.out.print("P");
                    }else if(pieceType == PieceType.KNIGHT){
                        System.out.print("N");
                    }else if(pieceType == PieceType.BISHOP){
                        System.out.print("B");
                    }else if(pieceType == PieceType.ROOK){
                        System.out.print("R");
                    }else if(pieceType == PieceType.QUEEN){
                        System.out.print("Q");
                    }else if(pieceType == PieceType.KING){
                        System.out.print("K");
                    }
                    
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }*/

    /**
     * Finding all the possible moves that a specific piece can make. 
     * pre: requires the x and y coordinates of the piece to be moved 
     * post: returns a list of boards with a different move applied to each.
     */
    public ArrayList<Board> getPieceMoves(int x, int y) {

        boolean kingMoved = false; //variable if the king has been moved
        
        ArrayList<Board> moves = new ArrayList<>();
        //loops through all piece types to find possible moves
        if (pieces[y][x].pieceType == PieceType.PAWN) {
            moves = getPawnMoves(moves, x, y);
        } else if (pieces[y][x].pieceType == PieceType.ROOK) {
            moves = getRookMoves(moves, x, y);
        } else if (pieces[y][x].pieceType == PieceType.KNIGHT) {
            for (int i = -2; i <= 2; i += 4) {
                for (int j = -1; j <= 1; j += 2) {
                    if (checkBounds(x + i, y + j)) {
                        if ((pieces[y + j][x + i] == null) || (pieces[y + j][x + i].colour != pieces[y][x].colour)) {
                            moves.add(makeMove(cloneBoard(this), x, y, x + i, y + j));
                        }
                    }
                    if (checkBounds(x + j, y + i)) {
                        if ((pieces[y + i][x + j] == null) || (pieces[y + i][x + j].colour != pieces[y][x].colour)) {
                            moves.add(makeMove(cloneBoard(this), x, y, x + j, y + i));
                        }
                    }
                }
            }
        } else if (pieces[y][x].pieceType == PieceType.BISHOP) {
            moves = getBishopMoves(moves, x, y);
        } else if (pieces[y][x].pieceType == PieceType.QUEEN) {
            moves = getRookMoves(moves, x, y);
            moves = getBishopMoves(moves, x, y);
        } else if (pieces[y][x].pieceType == PieceType.KING) {
            kingMoved = true;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (checkBounds(x + i, y + j)) {
                        if ((i != 0) || (j != 0)) {
                            if ((pieces[y + j][x + i] == null) || (pieces[y][x].colour != pieces[y + j][x + i].colour)) {
                                moves.add(makeMove(cloneBoard(this), x, y, x + i, y + j));
                                moves.get(moves.size() - 1).pieces[y + j][x + i].castling = false;
                            }
                        }
                    }
                }
            }//castling code. can't castle if under attack
            if ((pieces[y][x].castling)&&(!underAttack(x, y))) {
                if (pieces[y][x].colour == Colour.BLACK) {
                    if ((pieces[0][0] != null) && (pieces[0][0].castling) && (pieces[0][1] == null) && (pieces[0][2] == null) && (pieces[0][3] == null)) {
                        moves.add(makeMove(cloneBoard(this), x, y, 2, 0));
                        moves.set(moves.size() - 1, makeMove(moves.get(moves.size() - 1), 0, 0, 3, 0));
                        moves.get(moves.size()-1).pieces[0][2].castling = false;
                        moves.get(moves.size()-1).pieces[0][3].castling = false; 
                    } else if ((pieces[0][7] != null) && (pieces[0][7].castling) && (pieces[0][5] == null) && (pieces[0][6] == null)) {
                        moves.add(makeMove(cloneBoard(this), x, y, 6, 0)); 
                        moves.set(moves.size() - 1, makeMove(moves.get(moves.size() - 1), 7, 0, 5, 0));
                        moves.get(moves.size()-1).pieces[0][6].castling = false;
                        moves.get(moves.size()-1).pieces[0][5].castling = false;
                    }
                } else {
                    if ((pieces[7][0] != null) && (pieces[7][0].castling) && (pieces[7][1] == null) && (pieces[7][2] == null) && (pieces[7][3] == null)) {
                        moves.add(makeMove(cloneBoard(this), x, y, 2, 7));
                        moves.set(moves.size() - 1, makeMove(moves.get(moves.size() - 1), 0, 7, 3, 7));
                        moves.get(moves.size()-1).pieces[7][2].castling = false;
                        moves.get(moves.size()-1).pieces[7][3].castling = false;
                    } else if ((pieces[7][7] != null) && (pieces[7][7].castling) && (pieces[7][5] == null) && (pieces[7][6] == null)) {
                        moves.add(makeMove(cloneBoard(this), x, y, 6, 7)); 
                        moves.set(moves.size() - 1, makeMove(moves.get(moves.size() - 1), 7, 7, 5, 7));
                        moves.get(moves.size()-1).pieces[7][6].castling = false;
                        moves.get(moves.size()-1).pieces[7][5].castling = false;
                    }
                }
            }
        }
        
        //removing any king moves that put it under attack
        outer:
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(!kingMoved){
                    if((pieces[j][i] != null)&&(pieces[j][i].pieceType == PieceType.KING)&&(pieces[j][i].colour == pieces[y][x].colour)){
                        for(int m = 0; m < moves.size(); m++){
                            if(moves.get(m).underAttack(i, j)){
                                moves.remove(m);
                                m-=1;
                            }
                        }
                        break outer;
                    }
                }else{
                    for(int m = 0; m < moves.size(); m++){
                        if((moves.get(m).pieces[j][i] != null)&&(moves.get(m).pieces[j][i].pieceType == PieceType.KING)&&(moves.get(m).pieces[j][i].colour == pieces[y][x].colour)){
                            if(moves.get(m).underAttack(i, j)){
                                moves.remove(m);
                                m-=1;
                            }
                        }
                    }
                }
            }
        }
        
        return moves;
    }

    /**
     * Finds all possible moves for a pawn. pre: requires the moves that are
     * known to this point, and the x and y of the pawn. post: returns the list
     * that was taken in, with pawn moves added
     * @param moves the complete list of possible moves
     * @return the updated list of possible moves
     */
    public ArrayList<Board> getPawnMoves(ArrayList<Board> moves, int x, int y) {
        Colour colour = pieces[y][x].colour;
        int direction; //the direction the pawn moves on the board
        //if the colour is white, the direction is -1, otherwise the direction is 1
        direction = colour == Colour.WHITE ? -1 : 1;

        for (int i = -1; i <= 1; i++) {
            boolean capture;
            if (i == 0) {
                capture = false;
            } else {
                capture = true;
            }
            //checking for a regular capture or for a regular move
            if (squareAvailable(x+i, y+direction, 0, capture, false, colour)) {
                //if the pawn is at the end of the board, there are different transformations
                if (((y+direction) == 0) || (y+direction == 7)) {
                    for (PieceType j : PieceType.values()) {
                        if ((j != PieceType.PAWN) && (j != PieceType.KING)) {
                            moves.add(makeMove(cloneBoard(this), x, y, x + i, y + direction));
                            moves.get(moves.size()-1).pieces[y+direction][x+i].pieceType = j;
                            moves.get(moves.size()-1).pieces[y+direction][x+i].pawnMoveState = null;
                        }
                    }
                } else {
                    moves.add(makeMove(cloneBoard(this), x, y, x+i, y+direction));
                    moves.get(moves.size()-1).pieces[y+direction][x+i].pawnMoveState = PawnMoveState.MOVE_ONE;
                }//if the pawn is moving two squares forward
                if (squareAvailable(x+i, y+(2*direction), 0, capture, false, colour)&&(pieces[y][x].pawnMoveState == PawnMoveState.MOVE_TWO)) {
                    moves.add(moves.size(), makeMove(cloneBoard(this), x, y, x, y+(direction*2)));
                    moves.get(moves.size()-1).pieces[y+(direction*2)][x].pawnMoveState = PawnMoveState.LAST_MOVE_TWO;
                }
            }//checking for enpassant
            if ((capture) && (squareAvailable(x+i, y+direction, direction, capture, true, colour))) {
                moves.add(makeMove(cloneBoard(this), x, y, x+i, y+direction));
                moves.get(moves.size()-1).pieces[y][x + i] = null;
                moves.get(moves.size()-1).pieces[y+direction][x+i].pawnMoveState = PawnMoveState.MOVE_ONE;
            }
        }
        return moves;
    }

    /**
     * Checks if the square is available to be moved to by pawns. pre: requires
     * the x and y of the square to be checked, the direction the pawn moves,
     * whether the square is being checked for a capture or for an empty square,
     * if the type of capture is enpassant, and the colour of the piece. post:
     * returns a boolean indicating if the piece is free to move to or not
     *
     * @param direction -1 or 1 depending on the colour of the piece (only used for enpassant)
     * @param capture boolean indicating whether the check is for a null square
     * or for a piece of opposite colour
     * @param enpassant boolean indicating whether the capture is the fancy
     * enpassant type or just regular
     */
    public boolean squareAvailable(int x, int y, int direction, boolean capture, boolean enpassant, Colour colour) {
        boolean available = false;

        if (!capture) {
            available = ((checkBounds(x, y)) && (pieces[y][x] == null));
        } else if (!enpassant) {
            available = ((checkBounds(x, y)) && (pieces[y][x] != null) && (pieces[y][x].colour != colour));
        } else {
            if ((checkBounds(x, y)) && (pieces[y][x] == null)) {
                if ((checkBounds(x, y-direction)) && (pieces[y-direction][x] != null) && (pieces[y-direction][x].pawnMoveState == PawnMoveState.LAST_MOVE_TWO) && (colour != pieces[y-direction][x].colour)) {
                    available = true;
                }
            }
        }
        return available;
    }

    /**
     * Finds all the moves that a specific rook can make.
     * pre: requires the x and y coordinates of the rook. Requires the current list
     * of possible moves.
     * post: adds the rook moves to the complete list of moves.
     */
    public ArrayList<Board> getRookMoves(ArrayList<Board> moves, int x, int y) {

        int[] i = {-1, 1, 0, 0};
        int[] j = {0, 0, -1, 1};

        for (int index = 0; index < 4; index++) {
            int n = 1;
            boolean empty = true;
            while (empty) {

                if ((checkBounds(x+i[index]*n, y+j[index]*n)) && (pieces[y+j[index]*n][x+i[index]*n] == null)) {
                    moves.add(makeMove(cloneBoard(this), x, y, x+i[index]*n, y+j[index]*n));
                    moves.get(moves.size()-1).pieces[y+j[index]*n][x+i[index]*n].castling = false;
                } else if ((checkBounds(x+i[index]*n, y+j[index]*n))&&(pieces[y][x].colour != pieces[y+j[index]*n][x+i[index]*n].colour)) {
                    moves.add(makeMove(cloneBoard(this), x, y, x+i[index]*n, y+j[index]*n));
                    moves.get(moves.size()-1).pieces[y+j[index]*n][x+i[index]*n].castling = false;
                    empty = false;
                } else {
                    empty = false;
                }
                n++;
            }
        }
        return moves;
    }

    /**
     * Finds all the moves that a specific bishop can make.
     * pre: requires the x and y coordinates of the bishop. Requires the current list
     * of possible moves.
     * post: adds the bishop moves to the complete list of moves.
     */
    public ArrayList<Board> getBishopMoves(ArrayList<Board> moves, int x, int y) {

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int n = 1;
                boolean empty = true;
                while (empty) {

                    if ((checkBounds(x+i*n, y+j*n)) && (pieces[y+j*n][x+i*n] == null)) {
                        moves.add(makeMove(cloneBoard(this), x, y, x+i*n, y+j*n));
                    } else if ((checkBounds(x+i*n, y+j*n)) && (pieces[y][x].colour != pieces[y+j*n][x+i*n].colour)) {
                        moves.add(makeMove(cloneBoard(this), x, y, x+i*n, y+j*n));
                        empty = false;
                    } else {
                        empty = false;
                    }
                    n++;
                }
            }
        }
        return moves;
    }

    /**
     * Check to see if a given piece is under attack
     * pre: require x and y of the piece
     * post: returns a boolean true if the piece is under attack, false otherwise
     */
    public boolean underAttack(int x, int y){
        Colour colour = pieces[y][x].colour;
        
        //KNIGHT attacks
        for (int i = -2; i <= 2; i += 4) {
            for (int j = -1; j <= 1; j += 2) {
                if (checkBounds(x+i, y+j)) {
                    if ((pieces[y+j][x+i] != null)&&(pieces[y+j][x+i].pieceType == PieceType.KNIGHT)&&(pieces[y+j][x+i].colour != colour)){
                        return true;
                    }
                }
                if (checkBounds(x+j, y+i)) {
                    if((pieces[y+i][x+j] != null)&&(pieces[y+i][x+j].pieceType == PieceType.KNIGHT)&&(pieces[y+i][x+j].colour != colour)){
                        return true;
                    }
                }
            }
        }
        //KINGS attack
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if((checkBounds(x+i, y+j))&&(pieces[y+j][x+i] != null)&&(pieces[y+j][x+i].colour != colour)){
                    if(pieces[y+j][x+i].pieceType == PieceType.KING){
                        return true;
                    }
                }
            }
        }
        //PAWNS attack
        for(int i = -1; i <= 1; i+=2){
            int j = colour == Colour.WHITE ? -1 : 1;
            if((checkBounds(x+i, y+j))&&(pieces[y+j][x+i] != null)&&(pieces[y+j][x+i].colour != colour)){
                if(pieces[y+j][x+i].pieceType == PieceType.PAWN){
                    return true;
                }
            }
            
        }
        //BISHOP attack
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int n = 1;
                boolean empty = true;
                while (empty) {

                    if ((checkBounds(x + i * n, y + j * n)) && (pieces[y + j * n][x + i * n] == null)) {
                        
                    } else if ((checkBounds(x + i * n, y + j * n)) && (colour != pieces[y + j * n][x + i * n].colour)) {
                        if((pieces[y + j * n][x + i * n].pieceType == PieceType.BISHOP)||(pieces[y + j * n][x + i * n].pieceType == PieceType.QUEEN)){
                            return true;
                        }
                        empty = false;
                    } else {
                        empty = false;
                    }
                    n++;
                }
            }
        }
        //ROOK attack
        int[] i = {-1, 1, 0, 0};
        int[] j = {0, 0, -1, 1};

        for (int index = 0; index < 4; index++) {
            int n = 1;
            boolean empty = true;
            while (empty) {

                if ((checkBounds(x + i[index] * n, y + j[index] * n)) && (pieces[y + j[index] * n][x + i[index] * n] == null)) {
                    
                } else if ((checkBounds(x + i[index] * n, y + j[index] * n)) && (colour != pieces[y + j[index] * n][x + i[index] * n].colour)) {
                    if((pieces[y + j[index] * n][x + i[index] * n].pieceType == PieceType.ROOK)||(pieces[y + j[index] * n][x + i[index] * n].pieceType == PieceType.QUEEN)){
                        return true;
                    }
                    empty = false;
                } else {
                    empty = false;
                }
                n++;
            }
        }
        
        return false;
    }
    
    /**
     * Performs a move and returns a board with said move applied. Also updates
     * the pawn states of the board.
     * pre: Requires the board that will be updated, the x and y of the original
     * piece, and the x and y of the square it will be moved to.
     * post: Returns a board with the piece moved.
     */
    public Board makeMove(Board board, int x1, int y1, int x2, int y2) {
        Board returnBoard = cloneBoard(board);
        returnBoard.pieces[y2][x2] = returnBoard.pieces[y1][x1];
        returnBoard.pieces[y1][x1] = null;

        for (int i = 0; i < 8; i++) {
            if ((returnBoard.pieces[3][i] != null) && (returnBoard.pieces[3][i].pieceType == PieceType.PAWN)) {
                returnBoard.pieces[3][i].pawnMoveState = PawnMoveState.MOVE_ONE;
            }
            if ((returnBoard.pieces[4][i] != null) && (returnBoard.pieces[4][i].pieceType == PieceType.PAWN)) {
                returnBoard.pieces[4][i].pawnMoveState = PawnMoveState.MOVE_ONE;
            }
        }
        return returnBoard;
    }

    /**
     * Iterates through the entire board and finds all possible moves for a specific colour.
     * pre: requires the colour to check for.
     * post: returns an ArrayList of boards containing all the possible moves.
     */
    public ArrayList<Board> getAllMoves(Colour checkColour) {
        ArrayList<Board> allMoves = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if ((pieces[y][x] != null) && (pieces[y][x].colour == checkColour)) {
                    ArrayList<Board> moves;
                    moves = getPieceMoves(x, y);
                    for (int m = 0; m < moves.size(); m++) {
                        allMoves.add(moves.get(m));
                    }
                }
            }
        }
        
        return allMoves;
    }

    /**
     * Copies all the variables in board from a board, and returns it.
     * pre: requires the board to copy.
     * post: returns a copy of the board.
     */
    public static Board cloneBoard(Board board) {
        if (board == null) {
            return null;
        }
        Board returnBoard = new Board();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                returnBoard.pieces[i][j] = clonePiece(board.pieces[i][j]);
            }
        }

        returnBoard.turn = board.turn;

        return returnBoard;

    }

    /**
     * Makes a copy of a piece. Copies all the variables in piece.
     * pre: requires the piece to copy
     * post: returns the copied piece
     */
    public static Piece clonePiece(Piece piece) {
        if (piece == null) {
            return null;
        } else {
            Piece returnPiece = new Piece(piece.pieceType, piece.colour);
            returnPiece.castling = piece.castling;
            returnPiece.pawnMoveState = piece.pawnMoveState;
            return returnPiece;
        }
    }

    /**
     * Check to make sure the square is inside the board.
     * pre: takes in the coordinates of the square
     * post: returns a boolean of whether the piece is in bounds
     */
    public boolean checkBounds(int x, int y) {
        if ((x < 0) || (x >= 8)) {
            return false;
        }
        if ((y < 0) || (y >= 8)) {
            return false;
        }
        return true;
    }

    /**
     * Determining the number value of the board.
     * pre: none
     * post: returns a double.
     */
    public double getBoardValue() {
        double value = 0;
        int numberPieces;
        int numberBlackPieces = 0;
        int numberWhitePieces = 0;
        EnumMap<Colour, EnumMap<PieceType, Integer>> pieceCount = getPieceCount();

        //if the king is missing, the values are very high on the extremes
        if (pieceCount.get(Colour.WHITE).get(PieceType.KING) == 0) {
            return -100000;
        }
        if (pieceCount.get(Colour.BLACK).get(PieceType.KING) == 0) {
            return 100000;
        }
        
        //iterating through the board to find 
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if((pieces[i][j] != null)&&(pieces[i][j].pieceType != PieceType.KING)){
                    value += getPieceValue(pieces[i][j]);
                    if(pieces[i][j].colour == Colour.BLACK){
                        numberBlackPieces += 1;
                    }else if(pieces[i][j].colour == Colour.WHITE){
                        numberWhitePieces += 1;
                    }
                }
            }
        }

        numberPieces = numberBlackPieces + numberWhitePieces;
        if (numberPieces >= 26) { // 6 pieces taken
            value += getMidSquaresValue();
        } else if (numberPieces >= 6) {//between 6 pieces taken, and 6 pieces left
            
        } else{
            double endgame = getEndgameValue(numberPieces, pieceCount, value);
            if(endgame == 0){
                return 0;
            }else{
                if((numberBlackPieces == 0)||(numberWhitePieces == 0)){
                    value += endgame;
                }
            }
            
        }
        return value;
    }

    /**
     * Determining adjustments to the value of the board in endgame.
     * pre: takes in the total number of pieces, and the count of the piece types on the board
     * post: returns a double
     */
    public double getEndgameValue(int numberPieces, EnumMap<Colour, EnumMap<PieceType, Integer>> pieceCount, double originalValue) {
        //the number of pieces does not include the kings
        //i know two of them are kings already
        double value = 0;

        if (numberPieces == 0) {
            return 0; //its gotta be a draw
        } else if (numberPieces == 1) {
            for (Colour i : Colour.values()) {
                if (pieceCount.get(i).get(PieceType.BISHOP) > 0) {
                    return 0;
                } else if (pieceCount.get(i).get(PieceType.KNIGHT) > 0) {
                    return 0;
                } else{
                    value += getQuadrantValue(i);
                    value += getKingValue(i, originalValue);
                }
            }
        }else{
            for (Colour i : Colour.values()) {
                value += getQuadrantValue(i);
                value += getKingValue(i, originalValue);
            }
        }
        
        return value;
    }
    
    /**
     * If the only enemy piece is a king, the friendly king moves towards
     * the enemy king. The value of the board is impacted by the distance
     * between kings.
     * pre: requires the colour of the friendly king, and the value of the board
     * post: returns a double of the value
     */
    public double getKingValue(Colour colour, double originalValue){
        double value = 0;
        
        if((colour == Colour.BLACK)&&(originalValue >= 0)){
            return value;
        }if((colour == Colour.WHITE)&&(originalValue <= 0)){
            return value;
        }
        
        int whiteX = 0;
        int whiteY = 0;
        int blackX = 0;
        int blackY = 0;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if((pieces[j][i] != null)&&(pieces[j][i].pieceType == PieceType.KING)){
                    if(pieces[j][i].colour == Colour.BLACK){
                        blackX = i+1;
                        blackY = j+1;
                    }else if(pieces[j][i].colour == Colour.WHITE){
                        whiteX = i+1;
                        whiteY = j+1;
                    }
                }
            }
        }
        
        value = 100 - (Math.abs(whiteX-blackX))-(Math.abs(whiteY-blackY));
        
        value = value/20000;
        
        return value;
    }
    
    /**
     * Determines a value based on the number of squares blocked off to
     * a king by rooks or queens.
     * pre: requires the colour of the friendly side
     * post: returns the value
     */
    public double getQuadrantValue(Colour colour){
        double value = 0;
        
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        
        x.add(1);
        y.add(1);
        x.add(8);
        y.add(8);
        
        int width, height;
        
        int kingX = 0;
        int kingY = 0;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(pieces[j][i] != null){
                    if(pieces[j][i].colour == colour){
                        if((pieces[j][i].pieceType == PieceType.QUEEN)||(pieces[j][i].pieceType == PieceType.ROOK)){
                            if(!x.contains(i+1)){
                                x.add(i+1);
                            }if(!y.contains(j+1)){
                                y.add(j+1);
                            }
                        }
                    }else if((pieces[j][i].colour != colour)&&(pieces[j][i].pieceType == PieceType.KING)){
                        kingX = i+1;
                        kingY = j+1;
                    }
                }
            }
        }
        
        Collections.sort(x);
        Collections.sort(y);
        
        int minX = x.get(0);
        int maxX = x.get(1);
        for(int i = 1; i < x.size(); i++){
            if(kingX > x.get(i)){
                minX = x.get(i-1);
                maxX = x.get(i);
            }
        }
        width = maxX-minX;
        
        int minY = y.get(0);
        int maxY = y.get(1);
        for(int i = 1; i < y.size(); i++){
            if(kingY > y.get(i)){
                minY = y.get(i-1);
                maxY = y.get(i);
            }
        }
        height = maxY-minY;
        
        value = width*height;
        
        value = 64-value;
        value = value/100;
        
        return value;
    }
    
    /**
     * Determining adjustments to the value of the board in the begin game.
     * pre: none
     * post: returns double
     */
    public double getMidSquaresValue() {
        double value = 0;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if ((pieces[2+i][2+j] != null)&&(pieces[2+i][2+j].pieceType == PieceType.PAWN)&&(pieces[2+i][2+j].colour == Colour.BLACK)) {
                    value -= 0.07;
                }
                if ((pieces[4+i][2+j] != null)&&(pieces[4+i][2+j].pieceType == PieceType.PAWN)&&(pieces[4+i][2+j].colour == Colour.WHITE)) {
                    value += 0.07;
                }
            }
        }
        for (int x = 3; x <= 4; x++) {
            for (int y = 3; y <= 4; y++) {
                for (int i = -2; i <= 2; i += 4) {
                    for (int j = -1; j <= 1; j += 2) {
                        if ((pieces[y+j][x+i] != null)&&(pieces[y+j][x+i].pieceType == PieceType.KNIGHT)&&(pieces[y+j][x+i].colour == Colour.BLACK)) {
                            value -= 0.07;
                        }
                        if ((pieces[y+j][x+i] != null)&&(pieces[y+j][x+i].pieceType == PieceType.KNIGHT)&&(pieces[y+j][x+i].colour == Colour.WHITE)) {
                            value += 0.07;
                        }
                    }
                }
            }
        }
        return value;
    }

    /**
     * Determines the number of each type of piece on the board.
     * pre: none
     * post: a 2D enum map of colour and pieceType (and integers)
     */
    public EnumMap<Colour, EnumMap<PieceType, Integer>> getPieceCount() {
        EnumMap<Colour, EnumMap<PieceType, Integer>> pieceCount = new EnumMap<>(Colour.class);

        for (Colour i : Colour.values()) {
            pieceCount.put(i, new EnumMap<>(PieceType.class));
            for (PieceType j : PieceType.values()) {
                pieceCount.get(i).put(j, 0);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    int currentValue = pieceCount.get(pieces[i][j].colour).get(pieces[i][j].pieceType);
                    pieceCount.get(pieces[i][j].colour).put(pieces[i][j].pieceType, currentValue + 1);
                }
            }
        }
        return pieceCount;
    }

    /**
     * Gets the value of an individual piece based on pieceType
     * pre: takes in a piece
     * post: returns an int of the value
     */
    public static int getPieceValue(Piece piece) {
        int value = 0;

        if (piece.pieceType == PieceType.PAWN) {
            value = 1;
        } else if (piece.pieceType == PieceType.ROOK) {
            value = 5;
        } else if (piece.pieceType == PieceType.BISHOP) {
            value = 3;
        } else if (piece.pieceType == PieceType.KNIGHT) {
            value = 3;
        } else if (piece.pieceType == PieceType.QUEEN) {
            value = 9;
        }

        if (piece.colour == Colour.BLACK) {
            value *= -1;
        }
        return value;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof Board) {
            
            if(turn != ((Board)object).turn){
                return false;
            }
            
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((pieces[i][j] == null) && (((Board) object).pieces[i][j] != null)) {
                        return false;
                    } else if ((pieces[i][j] != null) && (((Board) object).pieces[i][j] == null)) {
                        return false;
                    } else if ((pieces[i][j] == null) && (((Board) object).pieces[i][j] == null)) {

                    } else if (!pieces[i][j].equals(((Board) object).pieces[i][j])) {
                        return false;
                    }
                }
            }
            
            return true;
        } else {
            return false;
        }
    }

    /**
     * Finds the winnerState of the board.
     * pre: none
     * post: returns the winnerState
     */
    public WinnerState getWinnerState(Colour checkColour) {
        WinnerState winnerState = WinnerState.UNFINISHED;
        if (getBoardValue() >= 100000) {
            winnerState = WinnerState.PLAYER_ONE_WINS;
        } else if (getBoardValue() <= -100000) {
            winnerState = WinnerState.PLAYER_TWO_WINS;
        } else{
            
            if(getAllMoves(Colour.swap(checkColour)).isEmpty()){
                
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if((pieces[j][i] != null)&&(pieces[j][i].pieceType == PieceType.KING)&&(pieces[j][i].colour != checkColour)){
                            if(underAttack(i, j)){
                                winnerState = checkColour == Colour.WHITE ? WinnerState.PLAYER_ONE_WINS : WinnerState.PLAYER_TWO_WINS;
                            }else{
                                winnerState = WinnerState.STALEMATE;
                            }
                        }
                    }
                }
            }
        }
        return winnerState;
    }
    
}
