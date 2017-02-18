import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//---------------------------------------------- Gameplay Class ----------------------------------------------

public class Gameplay extends JPanel implements ActionListener, KeyListener {
	
//---------------------------------------------- Member Fields -----------------------------------------------
	
	private static final long serialVersionUID = 1L;
	public Timer timer = new Timer(5, this);
	private Player player = new Player();
	private ParkingLot parkingLot = new ParkingLot();
	
	
//---------------------------------------------- Member Functions --------------------------------------------
	
  public Gameplay () {
	  timer.start();
	  addKeyListener(this); 
	  setFocusable(true);
	  setFocusTraversalKeysEnabled(false);
  }
 
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    parkingLot.getMapImage().paintIcon(this, g, 0, 0);
    player.getPlayerImage().paintIcon(this, g, player.getPlayerPos().x, player.getPlayerPos().y);
  }
 
  public void keyPressed(KeyEvent e) {
	  int code = e.getKeyCode();
	  if (code == KeyEvent.VK_UP) {player.moveUp();}
    if (code == KeyEvent.VK_DOWN) {player.moveDown();}
    if (code == KeyEvent.VK_LEFT) {player.moveLeft();}
    if (code == KeyEvent.VK_RIGHT) {player.moveRight();}
  }
  
  public void actionPerformed (ActionEvent e) {repaint();}
 
  public void keyTyped(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
}
