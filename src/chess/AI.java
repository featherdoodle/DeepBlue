/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Piece.Colour;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class AI extends Player{
    
    public AI(Colour _colour){
        super(_colour);
    }
    
    @Override
    public void move(Board board){
        //board = getBestMove(getAllMoves(), colour);
        
    }
    
    private int getMoveValue(Board board, Colour checkColour, int depth){
        if(depth == 0){
            return board.getBoardValue();
        }else{
            checkColour = Colour.swap(checkColour); //check colour
            ArrayList<Board> moves = getAllMoves(board, checkColour);//if movrd is empty
            
            if(!moves.isEmpty()){
                int bestValue = getMoveValue(moves.get(0), checkColour, depth--);
                for(int i = 1; i < moves.size(); i++){ //size()
                    int value = getMoveValue(moves.get(i), checkColour, depth--);
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
                if(board.pieces[i][j].colour == checkColour){
                    ArrayList<Board> moves = new ArrayList<>();
                    moves = board.getPieceMoves(i, j);
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
