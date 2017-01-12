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
    
    public final long difficulty;
    
    public AI(Colour _colour, long _difficulty){
        super(_colour);
        difficulty = _difficulty;
    }
    
    @Override
    public Board move(Board board){
        Random random = new Random();
        ArrayList<Board> moves = getAllMoves(board, colour);
        int[] values = new int[moves.size()];
        for(int i = 0; i < moves.size(); i++){//size()
            values[i] = getMoveValue(moves.get(i), colour, difficulty);//checkColour
        }
        //board = getBestMove(getAllMoves(), colour);
        int bestValue = values[0]; // values are all super small
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
        }
        if(colour == Colour.WHITE){
            return moves.get(bestIndices.get(random.nextInt(bestIndices.size())));
        }else{
            return moves.get(worstIndices.get(random.nextInt(worstIndices.size())));
        }
    }
    
    private int getMoveValue(Board board, Colour checkColour, long depth){
        
        if(depth <= 1){
            return board.getBoardValue();
        }else{
            checkColour = Colour.swap(checkColour); //check colour
            ArrayList<Board> moves = getAllMoves(board, checkColour);//if movrd is empty
            
            int count = moves.size(); //erm
            
            if(!moves.isEmpty()){
                int bestValue = getMoveValue(moves.get(0), checkColour, depth /= count);
                for(int i = 1; i < moves.size(); i++){ //size()
                    int value = getMoveValue(moves.get(i), checkColour, depth /= count);
                    /*if(checkColour == Colour.BLACK){
                        if(value == -100000){
                            value *= 100-depth;
                        }
                    }else if(checkColour == Colour.WHITE){
                        if(value == 100000){
                            value *= 100-depth;
                        }
                    }*/
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
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                //added null check
                if((board.pieces[i][j] != null)&&(board.pieces[i][j].colour == checkColour)){
                    ArrayList<Board> moves = new ArrayList<>();
                    moves = board.getPieceMoves(j, i);//swapped cause x y thing
                    for(int m = 0; m < moves.size(); m++){ //size();
                        allMoves.add(moves.get(m));
                    }
                }
            }
        }
        return allMoves;
    }
    
    private int getMinMax(Colour pieceColour, int a, int b){
        
        if(pieceColour == Colour.WHITE){
            return Math.max(a, b);
        }else{
            return Math.min(a, b);
        }
        
    }
    
}
