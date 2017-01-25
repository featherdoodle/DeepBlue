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
        
        int x1, y1, x2, y2; 
        
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Piece? (x, y): ");

        x1 = scan.nextInt();
        y1 = scan.nextInt();
        
        //x1 = Game.getChoice(0, 8);
        //y1 = Game.getChoice(0, 8);
        
        System.out.print("Destination? (x, y): ");
        
        x2 = scan.nextInt();
        y2 = scan.nextInt();
        //x2 = Game.getChoice(0, 8);
        //y2 = Game.getChoice(0, 8);
        
        Board destinationBoard = new Board();
        destinationBoard = destinationBoard.cloneBoard(board);
        
        destinationBoard.pieces[y2][x2] = destinationBoard.pieces[y1][x1];
        destinationBoard.pieces[y1][x1] = null;
        
        for(int i = 0; i < 8; i++){
            if((destinationBoard.pieces[3][i] != null)&&(destinationBoard.pieces[3][i].pieceType == PieceType.PAWN)){
                destinationBoard.pieces[3][i].pawnMoveState = PawnMoveState.MOVE_ONE;
            }if((destinationBoard.pieces[4][i] != null)&&(destinationBoard.pieces[4][i].pieceType == PieceType.PAWN)){
                destinationBoard.pieces[4][i].pawnMoveState = PawnMoveState.MOVE_ONE;
            }
        }
        
        //TODO: seperate into methods. if they move the king two squares, move 
        //the rook too. also, Humans can move enemy pieces
        if(destinationBoard.pieces[y2][x2] != null){
            if(destinationBoard.pieces[y2][x2].pieceType == PieceType.PAWN){
                if(Math.abs(y2-y1) == 2){
                    destinationBoard.pieces[y2][x2].pawnMoveState = PawnMoveState.LAST_MOVE_TWO;
                }else{
                    destinationBoard.pieces[y2][x2].pawnMoveState = PawnMoveState.MOVE_ONE;
                }
            }if(destinationBoard.pieces[y2][x2].castling){
                destinationBoard.pieces[y2][x2].castling = false;
            }
        }
        boolean contains = false;
        
        ArrayList<Board> allMoves = board.refinePieceMoves(colour);
        
        if(allMoves.isEmpty()){
            return board;
        }
        
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
}
