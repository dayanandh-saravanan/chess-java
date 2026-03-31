/** the gamepanel of the chess game
 * @author Dayanidi Saravanan, Dayanandh Saravanan
 * 5/11/2024
 * */ 
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener{
    
    /** the chess board */
    private Board board;
    /** all the pieces that move on the board */
    private static ArrayList<Piece> pieces = new ArrayList<Piece>();
    /** the pieces that appear when simulating a move */
    private static ArrayList<Piece> simPieces = new ArrayList<Piece>();
    /** the pieces that pawns can promote to */
    private static ArrayList<Piece> promotionPieces = new ArrayList<Piece>();
    /** the piece the user is moving */
    private Piece currentPiece;
    /** allows the user to click and drag on pieces */
    private MouseListener mouse;
    /** the rook piece that is getting castled */
    private Piece castlingPiece;
    /** checks if a piece can move */
    private boolean canMove;
    /** checks if a square is valid */
    private boolean validSquare;
    /** checks if a pawn can promote */
    private boolean promotion;
    /** checks if the king is in checkmate */
    private boolean checkmate;
    /** checks if the game ended in a draw */
    private boolean stalemate;
    /** the piece that is checking the king */
    private Piece checkingPiece;
    

    //piece color
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    static int currentColor = 0;

    //sfx
    SoundEffect soundEffect = new SoundEffect();

    
    
    /**Constructs the GamePanel for chess */
    public GamePanel(){
        
    
        this.setSize(600, 600);
        this.setBackground(Color.DARK_GRAY);
        board = new Board();
        this.setBoard();
        this.copyPieces(pieces, simPieces);
        mouse = new MouseListener();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
            
        

        
        Timer timer = new Timer(17, this);
        timer.start();

    }
/** adds pieces to board */
    public void setBoard(){
        //white pawns
        for(int i = 0; i < 8; i++){
            pieces.add(new Pawn(WHITE, 6, i));
        }
        //white pieces
        pieces.add(new Rook(WHITE, 7, 0));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new Bishop(WHITE, 7, 2));
        pieces.add(new Bishop(WHITE, 7, 5));
        pieces.add(new Knight(WHITE, 7, 1));
        pieces.add(new Knight(WHITE, 7, 6));
        pieces.add(new Queen(WHITE, 7, 3));
        pieces.add(new King(WHITE, 7, 4));
        //black pawns
        for(int i =0; i < 8; i++){
            pieces.add(new Pawn(BLACK, 1, i));
        }
        //black pieces
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 0, 7));
        pieces.add(new Bishop(BLACK, 0, 2));
        pieces.add(new Bishop(BLACK, 0, 5));
        pieces.add(new Knight(BLACK, 0, 1));
        pieces.add(new Knight(BLACK, 0, 6));
        pieces.add(new Queen(BLACK, 0, 3));
        pieces.add(new King(BLACK, 0, 4));
    }

 

    /**copies pieces from the first argument array to the second argument array.
     * @param pieceArr                  pieces that are getting copied
     * @param swappedArr                pieces that are getting removed
     */
    public void copyPieces(ArrayList<Piece> pieceArr, ArrayList<Piece> swappedArr){
        for(int i = swappedArr.size() - 1; i >= 0; i--){
            swappedArr.remove(i);
        }

        for(int i = 0; i < pieceArr.size(); i++){
            swappedArr.add(pieceArr.get(i));
        }
    }
    /**the code that runs in the game loop. Allows mouse input and runs the game */
    public void update(){

        if(promotion){
            this.promotingPiece();
        } else if(!checkmate && !stalemate){
        if(mouse.isPressed()){
            if(currentPiece == null){

                for(Piece p : pieces){

                    if(p.getColor() == currentColor && p.getRow() == mouse.getY() / 75 && p.getCol() == mouse.getX() / 75){
                        currentPiece = p;
                    }
                    
                }
            } else{
                movePiece();
            }
        } 

        if(!mouse.isPressed()){
            if(currentPiece != null){
                if(validSquare){
                this.copyPieces(simPieces, pieces);
                currentPiece.update();
                if(currentPiece instanceof King){
                    if(castlingPiece != null){
                        castlingPiece.setPrevCol(5);
                        castlingPiece.update();
                        
                    }
                }
                if(isKingInCheck() && isCheckmate()){
                    checkmate = true;
                    soundEffect.setFile(".//sfx/game-end.wav");

                    soundEffect.play();
                } else if(isStalemate() && !isKingInCheck()){
                    stalemate = true;
                    soundEffect.setFile(".//sfx/game-end.wav");

                    soundEffect.play();
                } else {
                    Piece oppKing = this.getKing(true);
                    for(Piece p : simPieces){
                        if(p.getColor() != oppKing.getColor()){
                            if(p.isValidMove(oppKing.getRow(), oppKing.getCol())){
                                soundEffect.setFile(".//sfx//move-check.wav");
                            }
                        }
                    }

                    soundEffect.play();

                    if(canPromote()){
                        promotion = true;
                        
                    } else{
                    currentPiece = null;
                    if(currentColor == WHITE){
                        currentColor = BLACK;
                        for(Piece p : pieces){
                            if(p.getColor() == BLACK){
                                p.setJumpedTwo(false);
                            }
                        }
                    } else{
                        currentColor = WHITE;
                        for(Piece p : pieces){
                            if(p.getColor() == WHITE){
                                p.setJumpedTwo(false);
                            }
                        }
                    }
                }
            }



            } else{
                copyPieces(pieces, simPieces);
                
                    soundEffect.setFile(".//sfx//illegal.wav");
                if(!(currentPiece.getRowBasedOnX(mouse.getX()) == currentPiece.getPrevRow() && currentPiece.getColBasedOnY(mouse.getY()) == currentPiece.getPrevCol())){
                    soundEffect.play();
                }
                if(currentPiece instanceof King){
                    if(castlingPiece != null){
                        castlingPiece.setCol(castlingPiece.getPrevCol());
                        castlingPiece.setX(castlingPiece.getXBasedOnCol(castlingPiece.getCol()));
                        castlingPiece = null;               
                    }
                }

                currentPiece.setX(currentPiece.getXBasedOnCol(currentPiece.getPrevCol()));
                currentPiece.setY(currentPiece.getYBasedOnRow(currentPiece.getPrevRow()));
                currentPiece.setRow(currentPiece.getPrevRow());
                currentPiece.setCol(currentPiece.getPrevCol());
                currentPiece = null;
            }
            }
        }
    }
    }
    /** checks to see if the king is in check */
    private boolean isKingInCheck() {
        
        Piece king = this.getKing(true);
        for(Piece p : simPieces){
            if(p.getColor() != king.getColor()){
                if(p.isValidMove(king.getRow(), king.getCol())){
                    checkingPiece = currentPiece;
                    return true;
                }  
            }
        }


        return false;
    }
    /** checks to see if the king can get captured */
    private boolean kingCanGetCaptured(){
       Piece king = this.getKing(false);

        for(Piece p : simPieces){
            if(p.getColor() != king.getColor() && p.isValidMove(king.getRow(), king.getCol())){
                return true;
            }
        }
        return false;
    }
    /**returns either the current king or opponent king depending on the argument
     * @param opponent          if true, gets opponent king, if false, gets current king
     */
    private Piece getKing(boolean opponent) {
        Piece king = null;
        for(Piece p : simPieces){
            if(opponent){
                if(p instanceof King && p.getColor() != currentColor){
                    king = p;
                }
        } else{
          
            if(p instanceof King && p.getColor() == currentColor){
                king = p;
            }
            
        }
    }
    return king;
    }
    /** changes pawn to promoting piece and plays sound accordingly */
    private void promotingPiece() {
        if(mouse.isPressed()){
            for(Piece p : promotionPieces){
                if((p.getCol() == ((mouse.getX() / 75))) && (p.getRow() == (mouse.getY() / 75))){
                    switch(p.getType()){
                        case "Queen": simPieces.add(new Queen(currentColor, currentPiece.getRow(), currentPiece.getCol()));
                                soundEffect.setFile(".//sfx//promote.wav");    
                            break;
                        case "Rook": simPieces.add(new Rook(currentColor, currentPiece.getRow(), currentPiece.getCol()));
                                soundEffect.setFile(".//sfx//promote.wav");    
                            break; 
                        case "Bishop": simPieces.add(new Bishop(currentColor, currentPiece.getRow(), currentPiece.getCol()));
                                soundEffect.setFile(".//sfx//promote.wav");    
                            break;
                        case "Knight": simPieces.add(new Knight(currentColor, currentPiece.getRow(), currentPiece.getCol()));
                                soundEffect.setFile(".//sfx//promote.wav");
                            break;
                        default: 
                            break;
                    }
                    simPieces.remove(currentPiece);
                    soundEffect.play();
                    copyPieces(simPieces, pieces);
                    currentPiece = null;
                    promotion = false;
                    if(currentColor == WHITE){
                        currentColor = BLACK;
                        
                            if(p.getColor() == BLACK){
                                p.setJumpedTwo(false);
                            }
                        
                    } else{
                        currentColor = WHITE;
                       
                            if(p.getColor() == WHITE){
                                p.setJumpedTwo(false);
                            }
            
                    }
                }
            }
        }
    }
    /**checks if the king is tring to move to a square that would put it in check
     * @param p         the king piece
     */
    private boolean checkedSquare(Piece p){
        if(p instanceof King){
            for(Piece piece : simPieces){
                if(piece != p && piece.getColor() != p.getColor() && piece.isValidMove(p.getRow(), p.getCol())){
                    return true;
                }
            }
        }
        return false;
    }
    /** checks to see if the game resulted in stalemate */
    private boolean isStalemate(){

        int numPieces = 0;
        for(Piece p : simPieces){
            if(p.getColor() != currentColor){
                numPieces++;
            }
        }

        if(numPieces == 1){
            if(!this.canKingMove(this.getKing(true))){
                return true;
            }
        }

        return false;
    }
    /** checks if the king is in checkmate */
    private boolean isCheckmate(){

        Piece king = this.getKing(true);

        if(canKingMove(king)){
            return false;
        } else{
            int rowDiff = Math.abs(checkingPiece.getRow() - king.getRow());
            int colDiff = Math.abs(checkingPiece.getCol() - king.getCol());

            if(rowDiff == 0){
                if(checkingPiece.getCol() < king.getCol()){
                    for(int i = checkingPiece.getCol(); i < king.getCol(); i++){
                        for(Piece p : simPieces){
                            if(p != king && p.isValidMove(checkingPiece.getRow(), i)){
                                return false;
                            }
                        }
                    }
                } else{
                    for(int i = checkingPiece.getCol(); i > king.getCol(); i--){
                        for(Piece p : simPieces){
                            if(p != king && p.isValidMove(checkingPiece.getRow(), i)){
                                return false;
                            }
                        }
                    }
                }
            } else if(colDiff == 0){
                if(checkingPiece.getRow() < king.getRow()){
                    for(int i = checkingPiece.getRow(); i < king.getRow(); i++){
                        for(Piece p : simPieces){
                            if(p != king && p.isValidMove(i, checkingPiece.getCol())){
                                return false;
                            }
                        }
                    }
                } else{
                    for(int i = checkingPiece.getRow(); i > king.getRow(); i--){
                        for(Piece p : simPieces){
                            if(p != king && p.isValidMove(i, checkingPiece.getCol())){
                                return false;
                            }
                        }
                    }
                }
            } else if(rowDiff == colDiff){
                if(checkingPiece.getRow() < king.getRow()){
                    if(checkingPiece.getCol() < king.getCol()){
                        int row = checkingPiece.getRow();
                        for(int i = checkingPiece.getCol(); i < king.getCol(); i++){
                            
                            for(Piece p : simPieces){
                                if(p != king && p.getColor() != currentColor && p.isValidMove(row, i)){
                                    return false;
                                }
                            }
                            row++;
                        }
                    }else{
                        int row = checkingPiece.getRow();
                        for(int i = checkingPiece.getCol(); i > king.getCol(); i--){
                            
                            for(Piece p : simPieces){
                                if(p != king && p.getColor() != currentColor && p.isValidMove(row, i)){
                                    return false;
                                }
                            }
                            row++;
                        }
                    }
                } else{
                    if(checkingPiece.getCol() < king.getCol()){
                        int row = checkingPiece.getRow();
                        for(int i = checkingPiece.getCol(); i < king.getCol(); i++){
                            
                            for(Piece p : simPieces){
                                if(p != king && p.getColor() != currentColor && p.isValidMove(row, i)){
                                    return false;
                                }
                            }
                            row--;
                        }
                    } else {
                        int row = checkingPiece.getRow();
                        for(int i = checkingPiece.getCol(); i > king.getCol(); i--){
                            
                            for(Piece p : simPieces){
                                if(p != king && p.getColor() != currentColor && p.isValidMove(row, i)){
                                    return false;
                                }
                            }
                            row--;
                        }
                    }
                }
            } else {
                for(Piece p : simPieces){
                    if(p != king && p.getColor() != currentColor && p.isValidMove(checkingPiece.getRow(), checkingPiece.getCol())){
                        return false;
                    }
                }
            }
        }

        return true;

    }
    /** checks if the king can move
     * @param king          the king piece
     */
    private boolean canKingMove(Piece king){

            if(isValidMove(king, -1, -1)){
                return true;
            }
            if(isValidMove(king, -1, 0)){
                return true;
            }
            if(isValidMove(king, 1, -1)){
                return true;
            }
            if(isValidMove(king, 0, -1)){
                return true;
            }
            if(isValidMove(king, 0, 1)){
                return true;
            }
            if(isValidMove(king, 1, -1)){
                return true;
            }
            if(isValidMove(king, 1, 0)){
                return true;
            }
            if(isValidMove(king, 1, 1)){
                return true;
            }

        return false;
    }
    /**checks if any of the 8 possible moves a king can make are valid
     * @param king          the king piece
     * @param row           this value is added to the original row to account for every possible move a king can make
     * @param col           this value is added to the orignal col to account for every possible move a king can make
     */
    private boolean isValidMove(Piece king, int row, int col){
        boolean valid = false;
        
        king.setRow(king.getRow() + row);
        king.setCol(king.getCol() + col);

        if(king.isValidMove(king.getRow(), king.getCol())){
            if(king.getTargetPiece() != null){
                simPieces.remove(king.getTargetPiece());
            }
            if(checkedSquare(king) == false){
                valid = true;
            }
        }
        king.setX(king.getXBasedOnCol(king.getPrevCol()));
        king.setY(king.getYBasedOnRow(king.getPrevRow()));
        king.setRow(king.getPrevRow());
        king.setCol(king.getPrevCol());
        copyPieces(pieces, simPieces);

        return valid;
    }

    /** checks if the king can castle, if it can, updates the board accordingly  */
    private void castlingCheck(){
        if(currentPiece instanceof King){
            castlingPiece = ((King)(currentPiece)).getCastlingPiece(currentPiece.getRowBasedOnX(mouse.getX()), currentPiece.getColBasedOnY(mouse.getY()));
            
            if(castlingPiece != null){
                if(castlingPiece.getCol() == 0){
                    castlingPiece.setCol(3);
                    
                } else{
                    castlingPiece.setCol(5);
                    
                }
            castlingPiece.setX(castlingPiece.getXBasedOnCol(castlingPiece.getCol()));
            soundEffect.setFile(".//sfx//castle.wav");
            }

            
        }
    }
    /** moves the piece, simulates what the board will look like before the move is confirmed */
    private void movePiece(){
        
        soundEffect.setFile(".//sfx//move-self.wav");
    
        canMove = false;
        validSquare = false;

        this.copyPieces(pieces, simPieces);

        if(currentPiece instanceof King){
           
            if(castlingPiece != null){
                castlingPiece.setCol(castlingPiece.getPrevCol());
                castlingPiece.setX(castlingPiece.getXBasedOnCol(castlingPiece.getCol()));
                castlingPiece = null;
            }
        }
        currentPiece.setX(mouse.getX() - 50);
        currentPiece.setY(mouse.getY() - 50);
        currentPiece.setRow(currentPiece.getRowBasedOnX(mouse.getX() - 50));
        currentPiece.setCol(currentPiece.getColBasedOnY(mouse.getY() - 50));
        if(currentPiece.isValidMove(currentPiece.getRow(), currentPiece.getCol())){
            canMove = true;

            if(currentPiece instanceof Pawn){
                if(currentPiece.getColor() == WHITE){
                    if((currentPiece.getCol() == currentPiece.getPrevCol() + 1 || currentPiece.getCol() == currentPiece.getPrevCol() - 1) && currentPiece.getRow() == currentPiece.getPrevRow() - 1 && !currentPiece.pieceOnSquare(currentPiece.getRow(), currentPiece.getCol())){
                        simPieces.remove(currentPiece.getTargetPiece());
                        soundEffect.setFile(".//sfx//capture.wav");       
                    } 
            } 
            if(currentPiece.getColor() == BLACK){
                if((currentPiece.getCol() == currentPiece.getPrevCol() + 1 || currentPiece.getCol() == currentPiece.getPrevCol() - 1) && currentPiece.getRow() == currentPiece.getPrevRow() + 1 && !currentPiece.pieceOnSquare(currentPiece.getRow(), currentPiece.getCol())){
                    simPieces.remove(currentPiece.getTargetPiece());
                    soundEffect.setFile(".//sfx//capture.wav"); 
                }
            }
            }

            if(currentPiece.capturedPiece(currentPiece.getRow(), currentPiece.getCol()) != null){
                if(currentPiece.capturedPiece(currentPiece.getRow(), currentPiece.getCol()).getColor() != currentPiece.getColor()){
                    simPieces.remove(currentPiece.capturedPiece(currentPiece.getRow(), currentPiece.getCol()));
                        soundEffect.setFile(".//sfx//capture.wav");
                }
            }
            castlingCheck();
            if(!this.checkedSquare(currentPiece) && !this.kingCanGetCaptured()){
                validSquare = true;
            }
            
        } 


    }
        /** checks if the pawn can promote based on its color */
        private boolean canPromote(){

            if(currentPiece instanceof Pawn){
                if((currentPiece.getColor() == WHITE && currentPiece.getRow() == 0) || (currentPiece.getColor() == BLACK && currentPiece.getRow() == 7)){
                    promotionPieces.clear();
                    promotionPieces.add(new Queen(currentColor, 2, 9));
                    promotionPieces.add(new Rook(currentColor, 3, 9));
                    promotionPieces.add(new Bishop(currentColor, 4, 9));
                    promotionPieces.add(new Knight(currentColor, 5, 9));
                    return true;
                }
            }

            return false;
        }
        /**renders all the images on the screen
         * @param g         provides methods to draw 
         */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        board.draw(g2);

        //pieces
        for(Piece p : simPieces){
            p.draw(g2);
        }
        //paints the square piece is hovered over if its a valid move
        if(currentPiece != null) {
            if(currentPiece.isValidMove(currentPiece.getRow(), currentPiece.getCol()) && !this.checkedSquare(currentPiece) && !kingCanGetCaptured()){
            g2.setColor(Color.GRAY);
            //if the pawn can promote, paint the promotion square yellow
            if(canPromote())
            {
                g2.setColor(Color.YELLOW);
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.fillRect(currentPiece.getCol()*75, currentPiece.getRow()*75, 75, 75);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        currentPiece.draw(g2);
            }


        if(promotion){
           for(Piece p : promotionPieces){
           
                if(p instanceof Bishop){
                    p.draw(g2);
                }
                if(p instanceof Queen){
                    p.draw(g2);
                    
                }
                if(p instanceof Rook){
                    p.draw(g2);
                }
                if(p instanceof Knight){
                    p.draw(g2);
                }
            
        }
    }
}
    }
    /** game loop */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.update();
        this.repaint();


    }
    /** return the pieces */
    public static ArrayList<Piece> getPieces(){
        return simPieces;
    }
    public static int getCurrentColor() {
        return currentColor;
    }
}

