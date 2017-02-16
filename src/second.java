import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

public class second extends JPanel implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	public Timer timer = new Timer(5, this);
	private ImageIcon background = new ImageIcon("ParkingLot.png");
	private Player player = new Player();
	
  public second() {
	  timer.start();
	  addKeyListener(this); 
	  setFocusable(true);
	  setFocusTraversalKeysEnabled(false);
  }
 
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    background.paintIcon(this, g, 0, 0);
    player.getPlayerIcon().paintIcon(this, g, player.getPlayerPos().x, player.getPlayerPos().y);
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
