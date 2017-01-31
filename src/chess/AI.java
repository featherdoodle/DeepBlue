

package chess;

import chess.Board.WinnerState;
import chess.Piece.Colour;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Owner
 */
public class AI extends Player {
    
    public double difficulty; //the variable that determines how many moves in the future the ai looks
    
    public AI(Colour _colour, double _difficulty){
        super(_colour);
        difficulty = _difficulty;
    }
    
    /**
     * The computer makes a move. It looks through all possible moves and future moves
     * to determine the move with the best value.
     * pre: takes in the current board
     * post: a move is made by the computer
     */
    @Override
    public Board move(Board board){
        
        Random random = new Random();
        
        board = Board.cloneBoard(board);
        
        ArrayList<Board> moves = board.getAllMoves(colour); //list that contains all the possible moves
        double[] values = new double[moves.size()]; 
        //list that contains the board values for each possible move. the indices match up with the previous list
        for(int i = 0; i < moves.size(); i++){
            values[i] = getMoveValue(moves.get(i), colour, difficulty);//getting the value of the move to add to the second list
        }
        //finding the best and worst values and indices (worst = lowest, best = highest)
        double bestValue = values[0];
        double worstValue = values[0];
        ArrayList<Integer> bestIndices = new ArrayList<>();
        bestIndices.add(0); 
        ArrayList<Integer> worstIndices = new ArrayList<>();
        worstIndices.add(0);
        for(int i = 1; i < moves.size(); i++){ 
            if(values[i] > bestValue){
                bestValue = values[i];
                bestIndices.clear();
                bestIndices.add(i); 
            }else if(values[i] == bestValue){
                bestIndices.add(i); 
            }
            if(values[i] < worstValue){ 
                worstValue = values[i];
                worstIndices.clear();
                worstIndices.add(i);
            }else if(values[i] == worstValue){
                worstIndices.add(i);
            }
        }
        if(colour == Colour.WHITE){ //if they are white, they want high values
            //if the values are equal, the move is chosen randomly
            return moves.get(bestIndices.get(random.nextInt(bestIndices.size()))); 
        }else{ //if they are black, they want low values
            return moves.get(worstIndices.get(random.nextInt(worstIndices.size())));
        }
    }
    
    /**
     * Looking through future moves to see what the future boards will look like
     * pre: takes in the board, the colour to find moves for, how far in the future to look
     * post: returns a double of the best value of the board.
     */
    //TODO: castling in check, game ending too early for bots
    private double getMoveValue(Board board, Colour checkColour, double depth){
        
        if(depth <= 1){
            return board.getBoardValue(); //base case, get the value of the board.
        }else{
            checkColour = Colour.swap(checkColour); //changing colour to check for the next turn
            ArrayList<Board> moves = board.getAllMoves(checkColour); //getting all moves for that colour
            
            int count = moves.size();
            
            if(!moves.isEmpty()){ 
                double bestValue = getMoveValue(moves.get(0), checkColour, depth/count); 
                //depth is based on count, so when there are less moves, it looks less in the future
                for(int i = 1; i < moves.size(); i++){ 
                    double value = getMoveValue(moves.get(i), checkColour, depth/count);
                    if(getMinMax(checkColour, bestValue, value) == value){
                        bestValue = value;
                    }
                }
                return bestValue;
            }else{
                WinnerState winnerState = board.getWinnerState(Colour.swap(checkColour));
                if(winnerState == WinnerState.PLAYER_ONE_WINS){
                    return 100000*(depth+1); //super high value if P1 wins
                }else if(winnerState == WinnerState.PLAYER_TWO_WINS){
                    return -100000*(depth+1); //super low value if P2 wins
                }else{
                    return 0;
                }
            }
        }
    }
    
    /**
     * Returns the better value based on the colour of the piece. Black wants lower
     * numbers, white wants higher numbers.
     * pre: takes in the colour and the two numbers to compare
     * post: returns the best number
     */
    private double getMinMax(Colour pieceColour, double a, double b){
        
        if(pieceColour == Colour.WHITE){
            return Math.max(a, b);
        }else{
            return Math.min(a, b);
        }
        
    }
    
    @Override
    public String toString(){
        String output = "";
        output += "AI " + colour + " " + difficulty;
        return output;
    }
    
}
