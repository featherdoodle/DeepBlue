/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chess.Piece.Colour;
import chess.Piece.PawnMoveState;
import chess.Piece.PieceType;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
        //TODO: method for user input?
        int x1, y1, x2, y2; 
        
        Scanner scan = new Scanner(System.in);
        
        while(true){
            System.out.print("Piece? (x y): ");
            String input = scan.nextLine();
            Scanner inputScanner = new Scanner(input);
        
            if(input.equalsIgnoreCase("q")){
                Main.quitGame();
            }else{
                try{
                    x1 = inputScanner.nextInt();
                    y1 = inputScanner.nextInt();
                    if((x1 >= 0)&&(x1 < 8)){
                        if((y1 >= 0)&&(y1 < 8)){
                            break;
                        }
                    }
                }catch(Exception e){
                    System.out.println("Invalid input, you silly.");
                }
            }
        }
        
        while(true){
            System.out.print("Destination? (x y): ");
            String input = scan.nextLine();
            Scanner inputScanner = new Scanner(input);
        
            if(input.equalsIgnoreCase("q")){
                Main.quitGame();
            }else{
                try{
                    x2 = inputScanner.nextInt();
                    y2 = inputScanner.nextInt();
                    if((x2 >= 0)&&(x2 < 8)){
                        if((y2 >= 0)&&(y2 < 8)){
                            break;
                        }
                    }
                }catch(Exception e){
                    System.out.println("Invalid input, you silly.");
                }
            }
        }
        
        Board destinationBoard = Board.cloneBoard(board);
        
        boolean enpassant = false;
        
        if((destinationBoard.pieces[y2][x2] == null)&&(destinationBoard.pieces[y1][x2] != null)&&(destinationBoard.pieces[y1][x2].colour != destinationBoard.pieces[y1][x1].colour)){
            enpassant = true;
        }
        
        destinationBoard.pieces[y2][x2] = destinationBoard.pieces[y1][x1];
        destinationBoard.pieces[y1][x1] = null;
        
        for(int i = 0; i < 8; i++){
            if((destinationBoard.pieces[3][i] != null)&&(destinationBoard.pieces[3][i].pieceType == PieceType.PAWN)){
                destinationBoard.pieces[3][i].pawnMoveState = PawnMoveState.MOVE_ONE;
            }if((destinationBoard.pieces[4][i] != null)&&(destinationBoard.pieces[4][i].pieceType == PieceType.PAWN)){
                destinationBoard.pieces[4][i].pawnMoveState = PawnMoveState.MOVE_ONE;
            }
        }
        
        if(destinationBoard.pieces[y2][x2] != null){
            if(destinationBoard.pieces[y2][x2].pieceType == PieceType.PAWN){
                if(Math.abs(y2-y1) == 2){
                    destinationBoard.pieces[y2][x2].pawnMoveState = PawnMoveState.LAST_MOVE_TWO;
                }else{
                    destinationBoard.pieces[y2][x2].pawnMoveState = PawnMoveState.MOVE_ONE;
                }
                if(enpassant){
                    destinationBoard.pieces[y1][x2] = null;
                }if((y2 == 0)||(y2 == 7)){
                    System.out.println("What type of piece would you like the pawn to become?");
        
                    for(;;){ //infinate loop
                        //Scanner scan = new Scanner(System.in);
                        try{
                            String choice = scan.nextLine();
                            if(choice.equalsIgnoreCase("knight")){
                                destinationBoard.pieces[y2][x2].pieceType = PieceType.KNIGHT;
                                break;
                            }else if(choice.equalsIgnoreCase("bishop")){
                                destinationBoard.pieces[y2][x2].pieceType = PieceType.BISHOP;
                                break;
                            }else if(choice.equalsIgnoreCase("rook")){
                                destinationBoard.pieces[y2][x2].pieceType = PieceType.ROOK;
                                break;
                            }else if(choice.equalsIgnoreCase("queen")){
                                destinationBoard.pieces[y2][x2].pieceType = PieceType.QUEEN;
                                break;
                            }else{
                                System.out.println("Please enter a valid choice (knight, bishop, rook, queen)");
                            }
                        }catch(InputMismatchException e){
                            System.out.println("Please enter a valid choice (knight, bishop, rook, queen)");
                        }
                    }
                }
            }if(destinationBoard.pieces[y2][x2].pieceType == PieceType.KING){
                if(Math.abs(x2-x1) == 2){
                    if((x2 == 2)&&(y2 == 0)){
                        destinationBoard.pieces[0][3] = destinationBoard.pieces[0][0];
                        destinationBoard.pieces[0][0] = null;
                        destinationBoard.pieces[0][3].castling = false;
                    }else if((x2 == 6)&&(y2 == 0)){
                        destinationBoard.pieces[0][5] = destinationBoard.pieces[0][7];
                        destinationBoard.pieces[0][7] = null;
                        destinationBoard.pieces[0][5].castling = false;
                    }else if((x2 == 1)&&(y2 == 7)){
                        destinationBoard.pieces[7][2] = destinationBoard.pieces[7][0];
                        destinationBoard.pieces[7][0] = null;
                        destinationBoard.pieces[7][2].castling = false;
                    }else if((x2 == 5)&&(y2 == 7)){
                        destinationBoard.pieces[7][4] = destinationBoard.pieces[7][7];
                        destinationBoard.pieces[7][7] = null;
                        destinationBoard.pieces[7][4].castling = false;
                    }
                }
                destinationBoard.pieces[y2][x2].castling = false;
            }
        }
        boolean contains = false;
        
        ArrayList<Board> allMoves = board.getAllMoves(colour);
        
        for(int i = 0; i < allMoves.size(); i++){
            if(allMoves.get(i).equals(destinationBoard)){
                contains = true;
            }
        }
        
        if(contains){
            return destinationBoard;
        }else{
            System.out.println("Enter a valid move");
            return move(board);
        }
    }
    
    public int[] getInput(){
        int[] coordinates = new int[2];
        Scanner scan = new Scanner(System.in);
        coordinates[0] = scan.nextInt();
            if (scan.hasNextInt()){
                coordinates[1] = scan.nextInt();
            }else{
                scan.nextLine();   
            }
        return coordinates;
    }
    
    @Override
    public String toString(){
        String output = "";
        output += "Human " + colour;
        return output;
    }
    
}
