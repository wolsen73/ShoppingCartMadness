import javax.swing.JFrame;

public class TestGraphics {

  public static void main(String[] args) {
  
	  JFrame frame = new JFrame();
	  second s = new second();
	  
	  frame.add(s);
	  frame.setVisible(true);
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setSize(800, 800);
  }
}