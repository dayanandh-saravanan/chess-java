/** Represents the board, an 8x8 grid with 2 different colored squares
 * 
 * @author Dayanidi Saravanan
 * 5/11/2024
 */
import java.awt.Color;
import java.awt.Graphics2D;

/* 
 * draws the 8x8 board with alternating colors going across a row and alternating colors going down a column
 * 
 * @param g2        provides methods to draw board
 */
public class Board {
    
    

    public void draw(Graphics2D g2){
        
        boolean color = true;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){

            if(color){
                g2.setColor(new Color(79, 121, 66));
                color = false;
            } else{
                g2.setColor(Color.WHITE);
                color  = true;
            }
                g2.fillRect(j*75, i*75, 75, 75);
            }
        color = !color;  
        }
    }
}
