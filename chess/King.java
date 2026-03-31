/* Class that creates and establishes logic for the king
 * @author Dayanandh Saravanan
 * 5/28/2024
 */
import java.awt.image.BufferedImage;

public class King extends Piece {
/** image field */
    private BufferedImage image;
    /** creates the King piece */
    public King(int color, int row, int col) {
        super(color, row, col);
        
        if(color == GamePanel.BLACK){
            image = getImage("/images/Chess_kdt60");
        } else{
            image = getImage("/images/Chess_klt60");
        }
    }
/** Checks if the king can move
 * @param newRow            takes the row at which the king is moving
 * @param newCol            takes the column at which the king is moving
 * 
 */
    public boolean isValidMove(int newRow, int newCol) {
        if(isOnBoard(newRow, newCol)){

            if((Math.abs(newRow - this.getPrevRow()) + Math.abs(newCol - this.getPrevCol()) == 1) || Math.abs(newRow - this.getPrevRow()) * Math.abs(newCol - this.getPrevCol()) == 1){

                if(this.capturedPiece(newRow, newCol) != null){
                    if(this.capturedPiece(newRow, newCol).getColor() != this.getColor()){
                        return true;
                    } else {
                        return false;
                    }
                
                } 
                return true;
            }
        if(newRow == this.getPrevRow() && newCol == this.getPrevCol() + 2 && !pieceOnStraightLine(newRow, newCol) && this.capturedPiece(newRow, newCol) == null && !this.hasMoved()){
            for(Piece p : GamePanel.getPieces()){
                if(p.getRow() == this.getPrevRow() && p.getCol() == this.getPrevCol() + 3 && !p.hasMoved() && p instanceof Rook){
                    return true;
                }
            }
        }

        if(newRow == this.getPrevRow() && newCol == this.getPrevCol() - 2 && !this.hasMoved() && !pieceOnStraightLine(newRow, newCol) && this.capturedPiece(newRow, newCol) == null){
            Piece p1 = null;
            Piece p2 = null;
            for(Piece p : GamePanel.getPieces()){
                if(p.getRow() == this.getPrevRow() && p.getCol() == this.getPrevCol() - 3){
                    p1 = p;
                }
                if(p.getRow() == this.getPrevRow() && p.getCol() == this.getPrevCol() - 4 && !p.hasMoved() && p instanceof Rook){
                    p2 = p;
                }
                if(p1 == null && p2 != null){
                    return true;
                }
            }
        }
            
        } else {
            return false;
        }

        return false;
    }
    /*returns the rook of which the king is castling with
     * @param newRow            new row of the king
     * @param newCol            new column of the king
     * @return p                returns the castling piece
     */
    public Piece getCastlingPiece(int newRow, int newCol){
        if(this.isValidMove(newRow, newCol)){
            if(newCol == this.getPrevCol() + 2){
                for(Piece p : GamePanel.getPieces()){
                    if(p.getRow() == this.getPrevRow() && p.getCol() == this.getPrevCol() + 3 && !p.hasMoved()){
                        return p;
                    }
                }
            }
            if(newCol == this.getPrevCol() - 2){
                for(Piece p : GamePanel.getPieces()){
                    if(p.getRow() == this.getPrevRow() && p.getCol() == this.getPrevCol() - 4 && !p.hasMoved()){
                        return p;
                    }
                }
            }
        }
    return null;
    }
    

}
