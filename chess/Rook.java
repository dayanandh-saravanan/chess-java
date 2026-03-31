/* Class that creates and establishes logic for the rook
 * @author Dayanandh Saravanan
 * 5/12/2024
 */
import java.awt.image.BufferedImage;
public class Rook extends Piece {
/** image field */
    private BufferedImage image;

    /** creates the Rook piece */
    public Rook(int color, int row, int col) {
        super(color, row, col);
        if(color == GamePanel.BLACK){
         image = getImage("/images/Chess_rdt60");
        } else{
            image = getImage("/images/Chess_rlt60");
        }
        
    }
/** Checks if the rook can move
 * @param newRow            takes the row at which the rook is moving
 * @param newCol            takes the column at which the rook is moving
 * 
 */
    public boolean isValidMove(int newRow, int newCol){
        if(isOnBoard(newRow, newCol)){
            if(newCol == this.getPrevCol()  && newRow != this.getPrevRow()){
                if(Math.abs(newCol - this.getPrevCol()) / Math.abs(newRow - this.getPrevRow()) == 0 && !pieceOnStraightLine(newRow, newCol)){
                    if(this.capturedPiece(newRow, newCol) != null){
                        if(this.capturedPiece(newRow, newCol).getColor() != this.getColor()){
                            return true;
                        } else {
                            return false;
                        }
                    } 
                    return true;
                } else{
                    return false;
                }
            } else if(newRow == this.getPrevRow() && newCol != this.getPrevCol()){
                if(Math.abs(newRow - this.getPrevRow()) / Math.abs(newCol - this.getPrevCol()) == 0 && !pieceOnStraightLine(newRow, newCol)){
                    if(this.capturedPiece(newRow, newCol) != null){
                        if(this.capturedPiece(newRow, newCol).getColor() != this.getColor()){
                            return true;
                        } else {
                            return false;
                        }
                    
                    } 
                    return true;
                } else {
                    return false;
                }
            } else{
                return false;
            }
        } else{
            return false;
        }
    }
    /*returns the image of the rook */
    public BufferedImage getImage(){
        return image;
    }

}
