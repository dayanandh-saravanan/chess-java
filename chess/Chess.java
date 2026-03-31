/** the main class
 * @author Dayanidi Saravanan
 * 5/11/2024
 * */ 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Chess{

    public static void main(String[] args) {
        
        JFrame frame =  new JFrame("Chess Board");
        frame.setBounds(200, 0, 816, 639);
        frame.setResizable(false);

        GamePanel board = new GamePanel();
        frame.add(board);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

