/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Board.WinnerState;
import chess.Piece.Colour;
import java.util.Scanner;

/**
 *
 * @author Owner
 */
public class Game {
    boolean turn = true;
    Board board = new Board();
    Player playerOne, playerTwo;
    
    
    public void menu(){
        
        Scanner scan = new Scanner(System.in);
        System.out.println("1. P v P\n2. P v AI\n3.AI v AI");
        int choice = scan.nextInt();
        
        if(choice == 1){
            playerOne = new Human(Colour.WHITE);
            playerTwo = new Human(Colour.BLACK);
        }else if(choice == 2){
            System.out.println("Human playing as black or white?");
            if(scan.nextLine().equalsIgnoreCase("white")){
                playerOne = new Human(Colour.WHITE);
                playerTwo = new AI(Colour.BLACK);
            }else{//i guess error checking here
                playerOne = new AI(Colour.WHITE);
                playerTwo = new Human(Colour.BLACK);
            }
            
            System.out.println("Select the difficulty of the AI (1, 2 or 3)");
            playerTwo.difficulty = scan.nextInt();
        }else if(choice == 3){
            playerOne = new AI(Colour.WHITE);
            playerTwo = new AI(Colour.BLACK);
            
            System.out.println("Select the difficulty of the first AI (1, 2 or 3)");
            playerOne.difficulty = scan.nextInt();
            System.out.println("Select the difficulty of the first AI (1, 2 or 3)");
            playerOne.difficulty = scan.nextInt();
        }
    }
    
    public void step(){
        while(board.winnerState == WinnerState.UNFINISHED){
            if(turn){
                playerOne.move(board);
                turn = false;
            }else{
                playerTwo.move(board);
                turn = true;
            }
        }
    }
    
}
