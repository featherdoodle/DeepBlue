/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Board.WinnerState;
import chess.Piece.Colour;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Owner
 */
public class AI extends Player{
    
    public final long difficulty; //the variable that determines how many moves in the future the ai looks
    
    public AI(Colour _colour, long _difficulty){
        super(_colour);
        difficulty = _difficulty;
    }
    
    @Override
    public Board move(Board board){
        Random random = new Random();
        ArrayList<Board> moves = getAllMoves(board, colour); //list that contains all the possible moves
        int[] values = new int[moves.size()]; 
        //list that contains the board values for each possible move. the indices match up with the previous list
        for(int i = 0; i < moves.size(); i++){
            values[i] = getMoveValue(moves.get(i), /*Colour.swap(colour)*/colour, difficulty);//getting the value of the move to add to the second list
        }
        //finding the best and worst values and indices
        int bestValue = values[0]; //TODO: Array out of bounds when game ends
        int worstValue = values[0];
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
        }//TODO: return empty array?
        if(colour == Colour.WHITE){ //if they are white, they want high values
            if(bestValue == -100000){
                //moves.get(0).winnerState = WinnerState.PLAYER_TWO_WINS;
                return null; //TODO: don't return null! maybe return null
            }
            return moves.get(bestIndices.get(random.nextInt(bestIndices.size()))); 
            //if the values are equal, the move is chosen randomly
        }else{ //if they are black, they want low values
            if(worstValue == 100000){
                //moves.get(0).winnerState = WinnerState.PLAYER_ONE_WINS;
                return null; //TODO: don't return null! maybe return null
            }
            return moves.get(worstIndices.get(random.nextInt(worstIndices.size())));
            //again, choosing randomly if the values are equal
        }
    }
    
    /**
     * Looking through future moves to see what the future boards will look like
     * @param checkColour 
     * @param depth
     * @return 
     */
    private int getMoveValue(Board board, Colour checkColour, long depth){
        
        if(depth <= 1){
            return board.getBoardValue();
        }else{
            checkColour = Colour.swap(checkColour); //check colour
            ArrayList<Board> moves = getAllMoves(board, checkColour);//if moves is empty
            
            int count = moves.size();
            
            if(!moves.isEmpty()){
                int bestValue = getMoveValue(moves.get(0), checkColour, depth/count); 
                //depth is based on count, so when there are less moves, it looks less in the future
                for(int i = 1; i < moves.size(); i++){ 
                    int value = getMoveValue(moves.get(i), checkColour, depth/count);
                    if(getMinMax(checkColour, bestValue, value) == value){
                        bestValue = value;
                    }
                }
                
                return bestValue;
            }else{
                return board.getBoardValue();
            }
        }
    }
    
    private ArrayList<Board> getAllMoves(Board board, Colour checkColour){
        ArrayList<Board> allMoves = new ArrayList<>();
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                //added null check
                if((board.pieces[y][x] != null)&&(board.pieces[y][x].colour == checkColour)){
                    ArrayList<Board> moves = new ArrayList<>();
                    moves = board.getPieceMoves(x, y);
                    for(int m = 0; m < moves.size(); m++){
                        allMoves.add(moves.get(m));
                    }
                }
            }
        }
        return allMoves;
    }
    /**
     * Returns the better value based on the colour of the piece. Black wants lower
     * numbers, white wants higher numbers.
     * @param pieceColour
     * @param a
     * @param b
     * @return 
     */
    private int getMinMax(Colour pieceColour, int a, int b){
        
        if(pieceColour == Colour.WHITE){
            return Math.max(a, b);
        }else{
            return Math.min(a, b);
        }
        
    }
    
}
