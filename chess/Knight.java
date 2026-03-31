/* Class that creates and establishes logic for the Knight
 * @author Dayanandh Saravanan
 * 5/11/2024
 */
import java.awt.image.BufferedImage;

public class Knight extends Piece {
/** image field */
    private BufferedImage image;
    /** creates the Knight piece */
    public Knight(int color, int row, int col) {
        super(color, row, col);
        
        if(color == GamePanel.BLACK){
            image = getImage("/images/Chess_ndt60");
        } else{
            image = getImage("/images/Chess_nlt60");
        }
    }
/** Checks if the knight can move
 * @param newRow            takes the row at which the knight is moving
 * @param newCol            takes the column at which the knight is moving
 * 
 */
    public boolean isValidMove(int newRow, int newCol){
        if(isOnBoard(newRow, newCol)){
            if(Math.abs(newCol - this.getPrevCol()) != 0 && Math.abs(newRow - this.getPrevRow())  != 0){
                if((Math.abs(newRow - this.getPrevRow()) / Math.abs(newCol - this.getPrevCol()) == 2) && ((Math.abs(newRow - this.getPrevRow()) + Math.abs(newCol - this.getPrevCol()) == 3))){
                    if(this.capturedPiece(newRow, newCol) != null){
                        if(this.capturedPiece(newRow, newCol).getColor() != this.getColor()){
                            return true;
                        } else {
                            return false; 
                        }
                    } 
                    return true;
                } else if((Math.abs(newCol - this.getPrevCol()) / Math.abs(newRow - this.getPrevRow()) == 2) && (Math.abs(newCol - this.getPrevCol()) + Math.abs(newRow - this.getPrevRow()) == 3)){
                    if(this.capturedPiece(newRow, newCol) != null){
                        if(this.capturedPiece(newRow, newCol).getColor() != this.getColor()){
                            return true;
                        } else {
                            return false;
                        }
                    
                    } 
                    return true;
                }
        }
        } else{
            return false;
        }
        return false;
        
    }
    /*returns the knight image */
    public BufferedImage getImage(){
        return image;
    }

    
    }
    

