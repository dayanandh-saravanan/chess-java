/* Class that creates and establishes logic for the pawn
 * @author Dayanandh Saravanan
 * 5/20/2024
 */
import java.awt.image.BufferedImage;

public class Pawn extends Piece{
/** image field */
    private BufferedImage image;
    /** creates the pawn piece */
    public Pawn(int color, int row, int col) {
        super(color, row, col);
        
        if(color == GamePanel.BLACK){
            image = getImage("/images/Chess_pdt60");
        } else{
            image = getImage("/images/Chess_plt60");
        }
    }
/** Checks if the pawn can move
 * @param newRow            takes the row at which the pawn is moving
 * @param newCol            takes the column at which the pawn is moving
 * 
 */
    public boolean isValidMove(int newRow, int newCol){
        if(isOnBoard(newRow, newCol)){
            if(this.hasMoved()){
                if(this.getColor() == GamePanel.WHITE){
                    if(newRow == this.getPrevRow() - 1 && newCol == this.getPrevCol() && !pieceOnSquare(newRow, newCol)){
                        return true;
                    } else if(newRow == this.getPrevRow() - 1 && (newCol == this.getPrevCol() + 1 || newCol == this.getPrevCol() - 1) && pieceOnSquare(newRow, newCol)){
                        if(this.getTargetPiece().getColor() != this.getColor()){
                            return true;
                        }

                    }
                    if((newCol == this.getPrevCol() + 1 || newCol == this.getPrevCol() - 1) && newRow == this.getPrevRow() - 1){
                        for(Piece p : GamePanel.getPieces()){
                            if(p.getCol() == newCol && p.getRow() == this.getPrevRow() && p.getJumpedTwo()){
                                this.setTargetPiece(p);
                                return true;
                            }
                        }
                    }
                } else {
                    if(newRow == this.getPrevRow() + 1 && newCol == this.getPrevCol() && !pieceOnSquare(newRow, newCol)){
                        return true;
                    } else if(newRow == this.getPrevRow() + 1 && (newCol == this.getPrevCol() - 1 || newCol == this.getPrevCol() + 1) && pieceOnSquare(newRow, newCol)){
                        if(this.getTargetPiece().getColor() != this.getColor()){
                            return true;
                        }
                    }
                    if((newCol == this.getPrevCol() + 1 || newCol == this.getPrevCol() - 1) && newRow == this.getPrevRow() + 1){
                        for(Piece p : GamePanel.getPieces()){
                            if(p.getCol() == newCol && p.getRow() == this.getPrevRow() && p.getJumpedTwo()){
                                this.setTargetPiece(p);
                                return true;
                            }
                        }
                    }
                }
            } else {
                if(this.getColor() == GamePanel.WHITE){
                    if((newRow == this.getPrevRow() - 2 && this.getPrevCol() == newCol && (!pieceOnSquare(newRow + 1, newCol) && !pieceOnSquare(newRow, newCol))) || (newRow == this.getPrevRow() - 1 && newCol == this.getPrevCol() && !pieceOnSquare(newRow, newCol))){
                        return true;
                    } else if(newRow == this.getPrevRow() - 1 && (newCol == this.getPrevCol() + 1 || newCol == this.getPrevCol() - 1) && pieceOnSquare(newRow, newCol)){
                            if(this.getTargetPiece().getColor() != this.getColor()){
                                return true;
                             }
                    }
                } else {
                    if((newRow == this.getPrevRow() + 2 && this.getPrevCol() == newCol && (!pieceOnSquare(newRow - 1, newCol) && !pieceOnSquare(newRow, newCol))) || (newRow == this.getPrevRow() + 1 && newCol == this.getPrevCol() && !pieceOnSquare(newRow, newCol))){
                        return true;
                    } else if(newRow == this.getPrevRow() + 1 && (newCol == this.getPrevCol() + 1 || newCol == this.getPrevCol() - 1) && pieceOnSquare(newRow, newCol)){
                            if(this.getTargetPiece().getColor() != this.getColor()){
                                return true;
                             }
                    }
                }
            }
            
            

        }
        return false;
    }


}
