

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
     * Completes one turn. Determines which player is moving based on turn.
     * pre: none
     * post: it is the next turn.
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
