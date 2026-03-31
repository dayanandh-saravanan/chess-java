/* Class that creates and establishes logic for the Queen
 * @author Dayanandh Saravanan
 * 5/13/2024
 */
import java.awt.image.BufferedImage;

public class Queen extends Piece {
/** image field */
    private BufferedImage image;
    /** creates the queen piece */
    public Queen(int color, int row, int col) {
        super(color, row, col);
        
        if(color == GamePanel.BLACK){
            image = getImage("/images/Chess_qdt60");
        } else{
            image = getImage("/images/Chess_qlt60");
        }
    }
/** Checks if the queen can move
 * @param newRow            takes the row at which the queen is moving
 * @param newCol            takes the column at which the queen is moving
 * 
 */
    public boolean isValidMove(int newRow, int newCol){
        if(isOnBoard(newRow, newCol)){
            if(newRow == this.getPrevRow() && newCol  == this.getPrevCol()){
                return false;
            }
        
                if(newCol == this.getPrevCol() && !pieceOnStraightLine(newRow, newCol)){
                    if(this.capturedPiece(newRow, newCol) != null){
                        if(this.capturedPiece(newRow, newCol).getColor() != this.getColor()){
                            return true;
                        } else {
                            return false;
                        }
                    } 
                    return true;
            } 
                if(newRow == this.getPrevRow() && !pieceOnStraightLine(newRow, newCol)){
                    if(this.capturedPiece(newRow, newCol) != null){
                        if(this.capturedPiece(newRow, newCol).getColor() != this.getColor()){
                            return true;
                        } else {
                            return false;
                        }
                    
                    } 
                    return true;
            } 
        
        if(Math.abs(newCol - this.getPrevCol()) != 0 && Math.abs(newRow - this.getPrevRow()) != 0){
                if(Math.abs(newRow - this.getPrevRow()) == Math.abs(newCol - this.getPrevCol()) && !pieceOnDiagonalLine(newRow,newCol)){
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

        }
        return false;
    }
    /*returns the image of the queen */
    public BufferedImage getImage(){
        return image;
    }
    }
    
    

    
