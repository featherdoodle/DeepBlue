

package chess;

import chess.Board.WinnerState;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owner
 */
public class Main {

    static Game game = new Game();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        menu(game);
    }
    
    /**
     * Prints out a menu and responds to the user input.
     * pre: none
     * post: game is set up
     */
    public static void menu(Game game){
        
        System.out.println("1. Play Game\n2. Instructions\n3. Load Game\n4. Exit");
        
        int firstChoice = getChoice(1, 5); //valid input checking
        
        if(firstChoice == 1){
            System.out.println("1. P v P\n2. P v AI\n3. AI v AI");
            int secondChoice = getChoice(1, 3);

            if(secondChoice == 1){
                game.playerOne = new Human(Piece.Colour.WHITE); //both players are human, opposite colours
                game.playerTwo = new Human(Piece.Colour.BLACK);
            }else if(secondChoice == 2){ //when one player is human, one ai
                System.out.println("Human playing as black or white?");
                boolean colourChoice = getColour();
                System.out.println("Select the difficulty of the AI (1-3)");
                long difficulty = getChoice(1, 3); 
                if(colourChoice){
                    game.playerOne = new Human(Piece.Colour.WHITE);
                    game.playerTwo = new AI(Piece.Colour.BLACK, Math.pow(10, difficulty+2));
                }else{
                    game.playerOne = new AI(Piece.Colour.WHITE, Math.pow(10, difficulty+2));
                    game.playerTwo = new Human(Piece.Colour.BLACK);
                }
            }else if(secondChoice == 3){//two ais
                System.out.println("Select the difficulty of the first AI (1-3)");
                long difficulty1 = getChoice(1, 3);
                System.out.println("Select the difficulty of the second AI (1-3)");
                long difficulty2 = getChoice(1, 3);
                game.playerOne = new AI(Piece.Colour.WHITE, Math.pow(10, difficulty1+3));
                game.playerTwo = new AI(Piece.Colour.BLACK, Math.pow(10, difficulty2+3));
            }
            game.board.setupBoard();
            runGame(game);
        }else if(firstChoice == 2){
            printInstructions(game);
        }else if(firstChoice == 3){
            System.out.println("What game would you like to load?");
            while(true){
                String gameNumber = Integer.toString(getChoice(1, 5));
                gameNumber += ".ser";//extention
                if(loadGame(gameNumber) != null){
                    game = loadGame(gameNumber);
                    runGame(game);
                }else{
                    menu(new Game());
                }
            }
        }else{ //quitting
            System.exit(0);
        }   
    }
    
    /**
     * Runs the game. Continues until the game is over.
     * pre: none
     * post: game has been completed
     */
    public static void runGame(Game game){
        game.board.printBoard();
        System.out.println();
        
        //continues to run while the game is unfinished
        while(game.board.getWinnerState() == WinnerState.UNFINISHED){
            game.step();
            game.board.printBoard();
            System.out.println();
        }
        gameOver(game.board.getWinnerState());
    }
    
    /**
     * Checks the winnerState and responds accordingly.
     * pre: requires a winnerState to check. (game must be finished)
     * post: appropriate text is printed out, user is able to return to menu
     */
    public static void gameOver(WinnerState winnerState){
        if(winnerState != WinnerState.UNFINISHED){
            if(winnerState == WinnerState.PLAYER_ONE_WINS){
                System.out.println("Player 1 Wins");
            }else if(winnerState == WinnerState.PLAYER_TWO_WINS){
                System.out.println("Player 2 Wins");
            }else if(winnerState == WinnerState.STALEMATE){
                System.out.println("Stalemate");
            }
            Scanner scan = new Scanner(System.in);
            System.out.println("Press enter to return to the menu");
            if(scan.nextLine() != null){
                menu(new Game());
            }
        }
    }
    
    /**
     * Method that loops until the user inputs a number within the desired range
     * pre: takes in an int for the minimum value the user has to enter, and the max value
     * post: returns the valid int that the user inputs
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
    
    /**
     * Similar to the above method, but for the user inputing a colour.
     * pre: none
     * post: returns a boolean that is true if the colour is white, false if black.
     */
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
    
    /**
     * Method to print out instructions to the screen.
     * pre: none
     * post: instructions are printed to the screen.
     */
    public static void printInstructions(Game game){
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
                + "If you would like to quit the game at any point, type 'q'.\n\n");
        
        menu(game); //returns to menu
    }
    
    /**
     * Saves the game to a file that the user indicates.
     * pre: none
     * post: game is serialized and stored in a file. Exits automatically
     */
    public static void saveGame(){

        System.out.println("Which data slot would you like to store it under? (1-5)");
        String filename = getChoice(1, 5) + ".ser";
        
        try{
            // Serialize data object to a file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(game);
            out.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.exit(0);
    }
    
    /**
     * Loads a game indicated by the user.
     * pre: requires the filename, which is found through the user input
     * post: a game is returned that was previously saved
     */
    public static Game loadGame(String filename){
   
        Game game = new Game();
        
        try{
            // Deserialize data object from a file
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            game = (Game)(in.readObject());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return game;
    }
    
    /**
     * Determines whether or not the user wants to save, quits.
     * pre: none
     * post: the game is saved if wanted, game is exited
     */
    public static void quitGame(){
        
        System.out.println("Would you like to save?");
        
        for(;;){ //checking user input
            Scanner scan = new Scanner(System.in);
            try{
                String choice = scan.nextLine();
                if(choice.equalsIgnoreCase("yes")){
                    saveGame(); //saves the game
                }else if(choice.equalsIgnoreCase("no")){
                    System.exit(0); //exits
                }else{
                    System.out.println("Please enter a valid choice (yes or no)");
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter a valid choice (yes or no)");
            }
        }
        
    }
    
    
}
