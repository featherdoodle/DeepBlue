/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Board.WinnerState;
import chess.Piece.Colour;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Owner
 */
public class Game {
    
    //boolean turn = true; //true if it is player one's turn, false if it is player two's
    Board board = new Board();
    Player playerOne, playerTwo;
    
    public void menu(){//TODO: doesn't loop properly //where is step called
        
        System.out.println("1. Play Game\n2. Instructions\n3. Load Game\n4. Exit");
        
        int firstChoice = getChoice(1, 5); //valid input checking
        
        if(firstChoice == 1){
            System.out.println("1. P v P\n2. P v AI\n3. AI v AI");
            int secondChoice = getChoice(1, 3);

            if(secondChoice == 1){
                playerOne = new Human(Colour.WHITE); //both players are human, opposite colours
                playerTwo = new Human(Colour.BLACK);
            }else if(secondChoice == 2){ //when one player is human, one ai
                System.out.println("Human playing as black or white?");
                boolean colourChoice = getColour();
                System.out.println("Select the difficulty of the AI (1-3)");
                long difficulty = getChoice(1, 3); 
                if(colourChoice){
                    playerOne = new Human(Colour.WHITE);
                    playerTwo = new AI(Colour.BLACK, Math.pow(10, difficulty+2));
                }else{
                    playerOne = new AI(Colour.WHITE, Math.pow(10, difficulty+2));
                    playerTwo = new Human(Colour.BLACK);
                }
            }else if(secondChoice == 3){//two ais
                System.out.println("Select the difficulty of the first AI (1-3)");
                long difficulty1 = getChoice(1, 3);
                System.out.println("Select the difficulty of the second AI (1-3)");
                long difficulty2 = getChoice(1, 3);
                playerOne = new AI(Colour.WHITE, Math.pow(10, difficulty1+2));
                playerTwo = new AI(Colour.BLACK, Math.pow(10, difficulty2+2));
            }
        }else if(firstChoice == 2){
            printInstructions();
        }else if(firstChoice == 3){
            //TODO:save&load
        }else if(firstChoice == 5){
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
    
    public static boolean getColour(){
        for(;;){ //infinate loop
            Scanner scan = new Scanner(System.in);
            try{
                String choice = scan.nextLine();
                if(choice.equalsIgnoreCase("white")){
                    return true;
                }else if(choice.equalsIgnoreCase("black")){
                    return false;
                }else{
                    System.out.println("Please enter a valid choice (black or white)");
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter a valid choice (black or white)");
            }
        }
    }
    
    public void printInstructions(){ //method to print instructions
        System.out.print("Instructions\nChess is a two player game with 6 different "
                + "types of pieces. The goal of the game is to put the other player \n"
                + "in checkmate. This is when their king is current being threatened "
                + "and is unable to move without being\n under attack by an enemy piece. "
                + "Each piece has different capabilities. PAWN: Can only move forward \n"
                + "(towards the enemy's side of the board) by one square at a time. "
                + "On the first turn, pawns are able\n to move two squares forward. "
                + "Pawns capture pieces by moving diagonally one square. KNIGHT: \n"
                + "The only piece that can jump over other pieces. Moves in an "
                + "L shape (moves 2 squares in one\n direction, and then 1 square in a "
                + "perpendicular direction. BISHOP: Moves diagonally any number of \n"
                + "squares (until blocked by a piece). ROOK: Moves up, down, left, "
                + "or right any number of squares\n (until blocked by a piece). QUEEN: "
                + "Can move diagonally, up down, left, or right any number of squares \n"
                + "(until blocked by a piece). KING: Can move one square in any direction. \n"
                + "If you would like to save the game at any point, type 's'.\n\n");
        
        menu();
    }
    /**
     * Switches between turns, running the actual game. Continues until the game is finished.
     */
    public void step(){
        board.setupBoard();
        board.printBoard();
        System.out.println();
        
        saveGame(3);
        
        Board tempBoard;
        
        while(board.winnerState == WinnerState.UNFINISHED){
            while(board.turn){
                tempBoard = board.cloneBoard(playerOne.move(board));
                if(board.equals(tempBoard)){
                    board.winnerState = WinnerState.PLAYER_TWO_WINS;
                    break;
                }else{
                    board = board.cloneBoard(tempBoard);
                }
                board.printBoard();
                System.out.println();
                board.turn = false;
            }while(!board.turn){
                tempBoard = board.cloneBoard(playerTwo.move(board));
                if(board.equals(tempBoard)){
                    board.winnerState = WinnerState.PLAYER_ONE_WINS;
                    break;
                }else{
                    board = board.cloneBoard(tempBoard);
                }
                board.printBoard();
                System.out.println();
                board.turn = true;
            }
            board.updateWinnerState();
        }
        
        if(board.winnerState != WinnerState.UNFINISHED){
            if(board.winnerState == WinnerState.PLAYER_ONE_WINS){
                System.out.println("Player 1 Wins");
            }else if(board.winnerState == WinnerState.PLAYER_TWO_WINS){
                System.out.println("Player 2 Wins");
            }else if(board.winnerState == WinnerState.STALEMATE){
                System.out.println("Stalemate");
            }
            Scanner scan = new Scanner(System.in);
            System.out.println("Press enter to return to the menu");
            if(scan.nextLine() != null){
                menu();
            }
        }
    }
    
    public void saveGame(File file, int gameNumber){

        int increments = 5;
        
        int line;
        line = (gameNumber-1)*increments;
        
        //String line32 = Files.readAllLines(Paths.get("file.txt")).get(32);
        
        //File file = new File("savedgames.txt");
        BufferedWriter writer;
        BufferedReader reader;
        
        String gameText = (playerOne + "\n" + playerTwo + "\n" + board.toString());
        
        try{
            
            writer = new BufferedWriter(new FileWriter(file));
            reader = new BufferedReader(new FileReader(file));
            
            for(int i = 0; i <= line; i++){
                writer.write(reader.readLine());
            }
            writer.write(gameText);
            writer.close();
            reader.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
    
    public Game loadGame(File file, int gameNumber){
        
        Game game = new Game();
        
        int increments = 5;
        
        int line;
        line = (gameNumber-1)*increments;
        
        //File file = new File("savedgames.txt");
        BufferedReader reader;
        
        try{
            
            reader = new BufferedReader(new FileReader(file));
            
            for(int i = 0; i <= line; i++){
                reader.readLine();
            }
            
            for(int i = 0; i < 2; i++){
                String[] lineOne = (reader.readLine()).split(" ");
                if(lineOne[0].equals("Human")){
                    if(lineOne[1].equals("WHITE")){
                        playerOne = new Human(Colour.WHITE);
                    }else{
                        playerOne = new Human(Colour.BLACK);
                    }
                }else{
                    if(lineOne[1].equals("WHITE")){
                        playerOne = new AI(Colour.WHITE, Integer.parseInt(lineOne[2]));
                    }else{
                        playerOne = new AI(Colour.BLACK, Integer.parseInt(lineOne[2]));
                    }
                }
            }
            
            
            
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
    
    //TODO: add save & load
    
}
