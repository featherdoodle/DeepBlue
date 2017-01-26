/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Piece.Colour;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Owner
 */
public class AI extends Player{
    
    public final double difficulty; //the variable that determines how many moves in the future the ai looks
    
    public AI(Colour _colour, double _difficulty){
        super(_colour);
        difficulty = _difficulty;
    }
    
    @Override
    public Board move(Board board){
        Random random = new Random();
        ArrayList<Board> moves = board.getAllMoves(colour); //list that contains all the possible moves
        double[] values = new double[moves.size()]; 
        //list that contains the board values for each possible move. the indices match up with the previous list
        for(int i = 0; i < moves.size(); i++){
            values[i] = getMoveValue(moves.get(i), /*Colour.swap(colour)*/colour, difficulty);//getting the value of the move to add to the second list
        }
        //finding the best and worst values and indices
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
            if(bestValue == -100000){
                //moves.get(0).winnerState = WinnerState.PLAYER_TWO_WINS;
                return board;
            }
            return moves.get(bestIndices.get(random.nextInt(bestIndices.size()))); 
            //if the values are equal, the move is chosen randomly
        }else{ //if they are black, they want low values
            if(worstValue == 100000){
                //moves.get(0).winnerState = WinnerState.PLAYER_ONE_WINS;
                return board;
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
    //TODO: castling in check, game ending too early for bots
    private double getMoveValue(Board board, Colour checkColour, double depth){
        
        if(depth <= 1){
            return board.getBoardValue();
        }else{
            checkColour = Colour.swap(checkColour); //check colour
            ArrayList<Board> moves = board.getAllMoves(checkColour);//if moves is empty
            
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
                return board.getBoardValue();
            }
        }
    }
    
    /**
     * Returns the better value based on the colour of the piece. Black wants lower
     * numbers, white wants higher numbers.
     * @param pieceColour
     * @param a
     * @param b
     * @return 
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
        output += colour + " " + difficulty;
        return output;
    }
    
}
