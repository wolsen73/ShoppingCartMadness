package shoppingcartmadness;

import java.awt.Point;
import javax.swing.ImageIcon;

/**
 *
 * @author Lunelune
 */

public class CartCompletion {
    
    private ImageIcon icon = new ImageIcon("cart_completed.png");
    private Point position; 

    public ImageIcon getIcon() {return icon;}
    public void setIcon(ImageIcon icon) {this.icon = icon;}
    public Point getPosition() {return position;}
    public void setPosition(Point position) {this.position = position;}
}
