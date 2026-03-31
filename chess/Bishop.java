/* Class that creates and establishes logic for the Bishop
 * @author Dayanandh Saravanan
 * 5/15/2024
 */
import java.awt.Shape;
import java.awt.image.BufferedImage;

public class Bishop extends Piece{

    /** image field */
    private BufferedImage image;
    /** creates the bishop piece */
    public Bishop(int color, int row, int col) {
        super(color, row, col); 
        
        if(color == GamePanel.BLACK){
            image = getImage("/images/Chess_bdt60");
        } else{
            image = getImage("/images/Chess_blt60");
        }
    }
/** Checks if the bishop can move
 * @param newRow            takes the row at which the bishop is moving
 * @param newCol            takes the column at which the bishop is moving
 * 
 */
    public boolean isValidMove(int newRow, int newCol){
        if(isOnBoard(newRow, newCol) ){
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
    /*returns the bishop image */
    public BufferedImage getImage(){
        return image;
    }

    
}
