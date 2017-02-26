package shoppingcartmadness;

import javax.swing.JFrame;

public class ShoppingCartMadness {

//--------------------------------------------- Main Method --------------------------------------------	
	
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        Gameplay gameplay = new Gameplay();

        frame.add(gameplay);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 940);
    }
}
