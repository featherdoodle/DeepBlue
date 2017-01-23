/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Board.WinnerState;
import chess.Piece.Colour;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Owner
 */
public class Game {
    boolean turn = true; //true if it is player one's turn, false if it is player two's
    Board board = new Board();
    Player playerOne, playerTwo;
    
    public void menu(){
        
        Scanner scan = new Scanner(System.in); 
        
        System.out.println("1. Play Game\n2. Instructions\n3. Exit");
        
        int firstChoice = getChoice(1, 4); //valid input checking
        
        if(firstChoice == 1){
            System.out.println("1. P v P\n2. P v AI\n3. AI v AI");
            int secondChoice = getChoice(1, 3);

            if(secondChoice == 1){
                playerOne = new Human(Colour.WHITE); //both players are human, opposite colours
                playerTwo = new Human(Colour.BLACK);
            }else if(secondChoice == 2){ //when one player is human, one ai
                System.out.println("Human playing as black or white?");
                scan.nextLine();
                String colourChoice = scan.nextLine();
                System.out.println("Select the difficulty of the AI (1, 2 or 3)");
                long difficulty = getChoice(1, 3); 
                if(colourChoice.equalsIgnoreCase("white")){
                    playerOne = new Human(Colour.WHITE);
                    playerTwo = new AI(Colour.BLACK, difficulty*1000); //adjusting the difficulty based on input
                }else{
                    playerOne = new AI(Colour.WHITE, difficulty*1000);
                    playerTwo = new Human(Colour.BLACK);
                }
            }else if(secondChoice == 3){//two ais
                System.out.println("Select the difficulty of the first AI (1, 2 or 3)");
                long difficulty1 = getChoice(1, 3);
                System.out.println("Select the difficulty of the second AI (1, 2 or 3)");
                long difficulty2 = getChoice(1, 3);
                playerOne = new AI(Colour.WHITE, difficulty1*1000);
                playerTwo = new AI(Colour.BLACK, difficulty2*1000);

            }
        }else if(firstChoice == 2){
            printInstructions();
        }else if(firstChoice == 4){
            playerOne = new AI(Colour.WHITE, 1000);
            playerTwo = new AI(Colour.BLACK, 1000);
        }else{ //quitting
            System.exit(0);
        }
        
    }
    /**
     * Method that continues until the user inputs a number within the desired range
     * @param min
     * @param max
     * @return 
     */
    public static int getChoice(int min, int max){
        for(;;){ //infinate loop
            Scanner scan = new Scanner(System.in);
            try{
                int choice = scan.nextInt();
                if((choice >= min)&&(choice <= max)){
                    return choice;
                }else{
                    System.out.println("Please enter a valid number");
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter a valid number");
            }
        }
    }
    
    public void printInstructions(){ //method to print instructions
        System.out.print("Instructions\nChess is a two player game with 6 different"
                + "types of pieces. The goal of the game is to put the other player"
                + "in checkmate. This is when their king is current being threatened"
                + "and is unable to move without being under attack by an enemy piece."
                + "Each piece has different capabilities. PAWN: Can only move forward"
                + "(towards the enemy's side of the board) by one square at a time. "
                + "On the first turn, pawns are able to move two squares forward. "
                + "Pawns capture pieces by moving diagonally one square. KNIGHT: "
                + "The only piece that can jump overtop of other pieces. Moves in an"
                + "L shape (moves ");
    }
    /**
     * Switches between turns, running the actual game. Continues until the game is finished.
     */
    public void step(){
        board.setupBoard();
        board.printBoard();
        System.out.println();
        
        while(board.winnerState == WinnerState.UNFINISHED){
            while(turn){
                //TODO: make tempboard and check if it is null-- keep board same, diff winnerstate
                if(board == playerOne.move(board)){
                    board.winnerState = WinnerState.PLAYER_TWO_WINS;
                    break;
                }else{
                    board = board.cloneBoard(playerOne.move(board));
                }
                /*if(board == null){
                    board.winnerState = WinnerState.PLAYER_TWO_WINS;
                    break;
                }*/
                board.printBoard();
                System.out.println();
                turn = false;
            }while(!turn){
                if(board == playerTwo.move(board)){
                    board.winnerState = WinnerState.PLAYER_ONE_WINS;
                    break;
                }else{
                    board = board.cloneBoard(playerTwo.move(board));
                }
                /*if(board == null){
                    board.winnerState = WinnerState.PLAYER_ONE_WINS;
                    break;
                }*/
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
