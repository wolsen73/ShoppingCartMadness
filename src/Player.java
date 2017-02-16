import javax.swing.ImageIcon;
import java.awt.Point;

public class Player {

	private ImageIcon playerIcon;
	private Point playerPos;
	private int moveDistance = 40;
	
	
	public Player () {
		playerIcon = new ImageIcon("ShoppingCartDude.png");
		playerPos = new Point(260, 460);
	}

	public ImageIcon getPlayerIcon () {return playerIcon;}
	public Point getPlayerPos () {return playerPos;}
	public int getMoveDistance () {return moveDistance;}
	
	public void moveUp () {playerPos.y -= moveDistance;}
	public void moveDown () {playerPos.y += moveDistance;}
	public void moveRight () {playerPos.x += moveDistance;}
	public void moveLeft () {playerPos.x -= moveDistance;}
}
