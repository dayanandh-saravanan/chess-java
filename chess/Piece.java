/**represents each piece in the game
 * @author dayanandh saravanan
 * 5/11/2024
 */
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Piece {
    
    /**image of piece */
    private BufferedImage image;
    /** row location of piece */
    private int row;
    /**column location of piece */
    private int col;
    /**previous row of piece */
    private int prevRow;
    /**previous column of piece */
    private int prevCol;
    /**x-coordinate of piece */
    private int x;
    /**y-coordinate of piece */
    private int y;
    /** color of piece (white or black) */
    private int color;
    /**piece that can be captured */
    private Piece targetPiece;
    /** sees if a piece has moved at least once */
    private boolean hasMoved = false;
    /** checks if a pawn has moved two squares */
    private boolean jumpedTwo;
   

    public Piece(int color, int row, int col){

        this.color = color;
        this.row = row;
        this.col = col;
        prevCol = col;
        prevRow = row;

        x = getXBasedOnCol(col);
        y = getYBasedOnRow(row);
    }

    /** creates and image for the piece
     * @param imagePath         file name of the piece
     */
    public BufferedImage getImage(String imagePath){
        URL imageUrl = getClass().getResource(imagePath + ".png");

        try {
            image = ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return image;
    }
    /** returns the captured piece or null if a piece has not been captured
     * @param newRow            row at which the current piece has moved
     * @param newCol            column at which the current piece has moved
     * @return p                returns the captured piece
     */
    public Piece capturedPiece(int newRow, int newCol){
        for(Piece p : GamePanel.getPieces()){
            if(p.getRow() == newRow && p.getCol() == newCol && p != this){
                targetPiece = p;
                return p;
            }
        }

        return null;
    }

    /** returns true if there is a piece on the square the current piece is moving to
     * @param newRow            row at which the current piece has moved
     * @param newCol            column at which the current piece has moved
    */
    public boolean pieceOnSquare(int newRow, int newCol){
            for(Piece p : GamePanel.getPieces()){
                    if(p.getRow() == newRow && p.getCol() == newCol && p != this){
                        targetPiece = p;
                        return true;
                    }
            
        }
    return false;
}
    /**determines if a piece is moving on a straight line
     * @param newRow            row at which the current piece has moved
     * @param newCol            column at which the current piece has moved
     */
    public boolean pieceOnStraightLine(int newRow, int newCol){

        for(int i = this.getPrevCol() - 1; i > newCol; i--){
            for(Piece p : GamePanel.getPieces()){
                if(p.getCol() == i && p.getRow() == newRow){
                    targetPiece = p;
                    return true;
                }
            }
        }
        for(int i = this.getPrevCol() + 1; i < newCol; i++){
            for(Piece p : GamePanel.getPieces()){
                if(p.getCol() == i && p.getRow() == newRow){
                    targetPiece = p;
                    return true;
                }
            }
        }

        for(int i = this.getPrevRow() - 1; i > newRow; i--){
            for(Piece p : GamePanel.getPieces()){
                if(p.getRow() == i && p.getCol() == newCol){
                    targetPiece = p;
                    return true;
                }
            }
        }
        for(int i = this.getPrevRow() + 1; i < newRow; i++){
            for(Piece p : GamePanel.getPieces()){
                if(p.getRow() == i && p.getCol() == newCol){
                    targetPiece = p;
                    return true;
                }
            }
        }
    return false;
    }
    /**determines if a piece is moving on a diagonal line
     * @param newRow            row at which the current piece has moved
     * @param newCol            column at which the current piece has moved
     */
    public boolean pieceOnDiagonalLine(int newRow, int newCol) {
    
    if(newRow < this.getPrevRow()){
         for(int j = this.getPrevCol() - 1; j > newCol; j--){
                for(Piece p : GamePanel.getPieces()){
                    if(p.getRow() == this.getPrevRow() - Math.abs((j - this.getPrevCol())) && p.getCol() == j){
                        targetPiece = p;
                        return true;
                    }
                }
            }


            for(int j = this.getPrevCol() + 1; j < newCol; j++){
                for(Piece p : GamePanel.getPieces()){
                    if(p.getRow() == this.getPrevRow() - Math.abs((j - this.getPrevCol())) && p.getCol() == j){
                        targetPiece = p;
                        return true;
                    }
                }
            }
    }

    if(newRow > this.getPrevRow()){

        for(int j = this.getPrevCol() - 1; j > newCol; j--){
            for(Piece p : GamePanel.getPieces()){
                if(p.getRow() == this.getPrevRow() + Math.abs((j - this.getPrevCol())) && p.getCol() == j){
                    targetPiece = p;
                    return true;
                }
            }
        }

        for(int j = this.getPrevCol() + 1; j < newCol; j++){
            for(Piece p : GamePanel.getPieces()){
                if(p.getRow() == this.getPrevRow() + Math.abs((j - this.getPrevCol())) && p.getCol() == j){
                    targetPiece = p;
                    return true;
                }
            }
        }
    }

        return false;
    }
    /**returns x-coordinate of piece */
    public int getX(){
        return x;
    }
    /**returns y-coordinate of piece */
    public int getY(){
        return y;
    }
    /**retirns the x given the column
     * @param col           column number piece is on
     */
    public int getXBasedOnCol(int col){
        return col * 75;
    }
    /**returns the y based on the row
     * @param row           row number piece is on
     */
    public int getYBasedOnRow(int row){
        return row * 75;
    }
    /**returns color of piece */
    public int getColor(){
        return color;
    }
    /**returns row of piece */
    public int getRow(){
        return row;
    }
    /**returns column of piece */
    public int getCol(){
        return col;
    }
    /**returns previous row of piece */
    public int getPrevRow(){
        return prevRow;
    }
    /**returns previous column of piece */
    public int getPrevCol(){
        return prevCol;
    }
    /**determines the row based on x-coordinate
     * @param x             x-coordinate
     */
    public int getRowBasedOnX(int x){
        return ((y + 38)/75);
    }
    /**determines the column based on the y-ccordinate
     * @param y             y-coordinate
     */
    public int getColBasedOnY(int y){
        return ((x + 38)/75);
    }
    /** returns true if a piece has moved at least once */
    public boolean hasMoved(){
        return hasMoved;
    }
    /**returns target piece */
    public Piece getTargetPiece(){
        return targetPiece;
    }
    /** sets target piece */
    public void setTargetPiece(Piece p){
        targetPiece = p;
    }
    /**sets x-coordinate */
    public void setX(int x){
        this.x = x;
    }
    /**sets y-coordinate */
    public void setY(int y){
        this.y = y;
    }
    /**sets the row */
    public void setRow(int row){
        this.row = row;
    }
    /**sets the column */
    public void setCol(int col){
        this.col = col;
    }
    /**draws the piece
     * @param g2        allows the method draw image to be called
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, 75, 75, null);
    }
    /**updates the piece */
    public void update() {

        if(this instanceof Pawn){
            if(Math.abs(row - prevRow) == 2){
                this.setJumpedTwo(true);
            }
        }

        hasMoved = true;
        x = getXBasedOnCol(col);
        y = getYBasedOnRow(row);
        prevRow = getRowBasedOnX(x);
        prevCol = getColBasedOnY(y);
        
    }
    /**logic for is valid move is created in each specific piece's class
     * @param newRow            row at which the current piece has moved
     * @param newCol            column at which the current piece has moved
    */
    public boolean isValidMove(int newRow, int newCol){
        return false;
    }
    /**determines if the piece is still within the board
     * @param newRow            row at which the current piece has moved
     * @param newCol            column at which the current piece has moved
     */
    public boolean isOnBoard(int newRow, int newCol){
        if((newRow <  0 || newRow > 7) || (newCol < 0 || newCol > 7)){
            return false;
        }
        return true;
    }
    /**sets the previous column
     * @param col           previous column
     */
    public void setPrevCol(int col) {
        this.prevCol = col;
    }
    /**determines if a pawn has jumped two squares
     * @param b             true if a pawn has jumped two and false otherwise
     */
    public void setJumpedTwo(boolean b){
        this.jumpedTwo = b;
    }
    /**returns true if a pawn has jumped two and false otherwise */
    public boolean getJumpedTwo(){
        return jumpedTwo;
    }
    /**returns what specific piece it is */
    public String getType() {
        if(this instanceof Bishop){
            return "Bishop";
        }

        if(this instanceof Rook){
            return "Rook";
        }

        if(this instanceof Knight){
            return "Knight";
        }
        if(this instanceof Queen){
            return "Queen";
        }
        if(this instanceof King){
            return "King";
        }
        return "Pawn";
    }






}
