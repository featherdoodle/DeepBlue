/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Piece.Colour;
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
    public void move(Board board){
        Scanner scan = new Scanner(System.in);
        
        int x1, y1, x2, y2; 
        
        System.out.print("Piece? (x, y): ");
        
        x1 = scan.nextInt();
        y1 = scan.nextInt();
        
        System.out.print("Destination? (x, y): ");
        
        x2 = scan.nextInt();
        y2 = scan.nextInt();
        
        if(board.getPieceMoves(x1, y1).contains(/*destination*/)){ //do the coordinates need to be in arrays?
            board.pieces[x2][y2] = board.pieces[x1][y1];
            board.pieces[x1][y1] = null;
        }
    }
}
