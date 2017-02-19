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
public class Car {

    private static int MAX_VERT = 800;

    private final ImageIcon icon = new ImageIcon("Car.png");
    private final int moveDistance = 100;
    private Point position;

    /**
     * @return the MAX_VERT
     */
    public static int getMAX_VERT() {
        return MAX_VERT;
    }

    /**
     * @return the icon
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * @return the moveDistance
     */
    public int getMoveDistance() {
        return moveDistance;
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