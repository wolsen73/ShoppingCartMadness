package shoppingcartmadness;

import javax.swing.ImageIcon;
import java.awt.Point;

//------------------------------------------- ParkingLot Class -------------------------------------------
public class ParkingLot {

    public static String ICON = "ParkingLot.png";

    public final int[] collumns = {400, 500, 600, 700, 800, 900, 1000,
        1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800};

    private final ImageIcon map = new ImageIcon(ICON);
    private final Point mapSize = new Point(1700, 800);
    //------------------------------------------- Member Fields ----------------------------------------------

//------------------------------------------- Member Functions -------------------------------------------
    public ImageIcon getMapImage() {
        return map;
    }

    public Point getMapSize() {
        return mapSize;
    }
}