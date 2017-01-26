/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.io.Serializable;

/**
 *
 * @author Owner
 */
public class Game implements Serializable{
    
    //boolean turn = true; //true if it is player one's turn, false if it is player two's
    Board board = new Board();
    Player playerOne, playerTwo;
    
    /**
     * Switches between turns, running the actual game. Continues until the game is finished.
     * pre: none
     * post: the game has been completed
     */
    public void step(){
        
        Player player;
        
        if(board.turn){
            player = playerOne;
        }else{
            player = playerTwo;
        }
        
        board = player.move(board);
        
        
        board.turn = !board.turn;
    }
    
    
}
