/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Piece.Colour;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Owner
 */
public class Human extends Player{
    
    public Human(Colour _colour){
        super(_colour);
    }
    
    @Override
    public Board move(Board board){
        Scanner scan = new Scanner(System.in);
        
        int x1, y1, x2, y2; 
        
        System.out.print("Piece? (x, y): ");
        
        x1 = scan.nextInt();
        y1 = scan.nextInt();
        
        System.out.print("Destination? (x, y): ");
        
        x2 = scan.nextInt();
        y2 = scan.nextInt();
        
        Board destinationBoard = board;
        destinationBoard.pieces[x2][y2] = destinationBoard.pieces[x1][y1];
        destinationBoard.pieces[x1][y1] = null;
        //um check board as parameter
        
        boolean contains = false;
        ArrayList<Board> allMoves = board.refinePieceMoves(x1, y1);
        for(int i = 0; i < allMoves.size(); i++){
            if(allMoves.get(i).equals(destinationBoard)){
                contains = true;
            }
        }
        if(contains){
            return destinationBoard;
        }else{
            return null;
        }
    }
}
