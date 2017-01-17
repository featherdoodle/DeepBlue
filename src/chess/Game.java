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
        System.out.println("1. P v P\n2. P v AI\n3. AI v AI");
        int choice = scan.nextInt();
        
        if(choice == 1){
            playerOne = new Human(Colour.WHITE);
            playerTwo = new Human(Colour.BLACK);
        }else if(choice == 2){
            System.out.println("Human playing as black or white?");
            scan.nextLine();
            String colourChoice = scan.nextLine();
            System.out.println("Select the difficulty of the AI (1, 2 or 3)");
            int difficulty = scan.nextInt();
            if(colourChoice.equalsIgnoreCase("white")){
                playerOne = new Human(Colour.WHITE);
                playerTwo = new AI(Colour.BLACK, difficulty);
            }else{//i guess error checking here
                playerOne = new AI(Colour.WHITE, difficulty);
                playerTwo = new Human(Colour.BLACK);
            }
        }else if(choice == 3){
            System.out.println("Select the difficulty of the first AI (1, 2 or 3)");
            long difficulty1 = scan.nextInt();
            System.out.println("Select the difficulty of the second AI (1, 2 or 3)");
            long difficulty2 = scan.nextInt();
            playerOne = new AI(Colour.WHITE, difficulty1);
            playerTwo = new AI(Colour.BLACK, difficulty2);
            
        }
    }
    
    public void step(){
        board.setupBoard();
        board.printBoard();
        System.out.println();
        
        while(board.winnerState == WinnerState.UNFINISHED){
            while(turn){
                if(playerOne.move(board) == null){
                    System.out.println("Invalid");
                }else{
                    board = board.cloneBoard(playerOne.move(board));
                    board.printBoard();
                    System.out.println();
                    turn = false;
                }
            }while(!turn){
                board = board.cloneBoard(playerTwo.move(board));
                board.printBoard();
                System.out.println();
                turn = true;
            }
            board.updateWinnerState();
        }
        
        if(board.winnerState == WinnerState.PLAYER_ONE_WINS){
            System.out.println("Player 1 Wins");
        }else if(board.winnerState == WinnerState.PLAYER_TWO_WINS){
            System.out.println("Player 2 Wins");
        }else if(board.winnerState == WinnerState.TIE){
            System.out.println("Tie");
        }
    }
    
    
}
