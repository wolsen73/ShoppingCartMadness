/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingcartmadness;

import java.awt.Point;
import javax.swing.ImageIcon;

/**
 *
 * @author Lunelune
 */
public class CartCompletion {
    
     private ImageIcon icon = new ImageIcon("cart_completed.png");
    //   private final Point spawnPos = new Point(300, 400);// TODO: remove
    private Point position; // = new Point(300, 400);

    /**
     * @return the icon
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Point position) {
        this.position = position;
    }
    
}
