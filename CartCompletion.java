package shoppingcartmadness;

import java.awt.Point;
import javax.swing.ImageIcon;

/**
 *
 * @author Lunelune
 * @update Travis Kerns
 */

public class CartCompletion {
	
//--------------------------------------------- Member Fields --------------------------------------------
	
	public static final int COMP_X_COORD = 1700;
	private static int cartsCreated = 0;
  private ImageIcon icon = new ImageIcon("cart_completed.png");
  private Point position;
  
//--------------------------------------------- Member Functions -----------------------------------------
  
  public CartCompletion () {
  	position = new Point(COMP_X_COORD, cartsCreated * 100);
  	cartsCreated++;
  }
  
  public ImageIcon getIcon () {return icon;}
  public void setIcon (ImageIcon icon) {this.icon = icon;}
  
  public Point getPosition () {return position;}
  public void setPosition (Point position) {this.position = position;}
  
  public static void reset() {cartsCreated = 0;}
}
