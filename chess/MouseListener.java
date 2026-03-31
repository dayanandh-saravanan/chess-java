/**creates a class that allows the user to interact using the mouse
 * @author dayanidi saravanan
 * 5/12/2024
 */

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter{
    
        /** if mouse is pressed or not */
        private boolean pressed;
        /** x-coordinate of the mouse */
        private int x;
        /** y-coordinate of the mouse */
        private int y;

        /** set pressed to true if mouse has been clicked
         * @param e             triggered when the mouse is pressed
         */
        @Override
        public void mousePressed(MouseEvent e){
            pressed = true;
        }

        /** set pressed to false on release of the mouse
         * @param e         triggers when the mouse is released
         */
        @Override
        public void mouseReleased(MouseEvent e){
            pressed = false;
        }

        /** set pressed to false on release of the mouse
         * @param e         triggers when the mouse is dragged
         */
        @Override 
        public void mouseDragged(MouseEvent e){ 
            x = e.getX();
            y = e.getY();
        }

        /** set pressed to false on release of the mouse
         * @param e         triggers when the mouse has moved
         */
        @Override
        public void mouseMoved(MouseEvent e){
            x = e.getX();
            y = e.getY();
        }

        /** returns the x-coordinate of the mouse cursor */
        public int getX(){
            return x;
        }

        /**returns the y-coordinate of the mouse cursor */
        public int getY(){
            return y;
        }

        /** true if mouse is pressed and false if otherwise */
        public boolean isPressed(){
            return pressed;
        }
}
