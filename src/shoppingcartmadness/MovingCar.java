package shoppingcartmadness;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

/**
 *
 * @author Lunelune
 * @update will.olsen
 */

public class MovingCar {

    private static int MAX_VERT = 800;
    private static int MIN_VERT = 0;

    private final ImageIcon[] carMovingDownImages = {
    		new ImageIcon("MovingDownCar1.png"), new ImageIcon("MovingDownCar2.png"), 
    		new ImageIcon("MovingDownCar3.png"), new ImageIcon("MovingDownCar4.png"),
    		new ImageIcon("MovingDownCar5.png")};
    
    private final ImageIcon[] carMovingUpImages = {
    		new ImageIcon("MovingUpCar1.png"), new ImageIcon("MovingUpCar2.png"), 
    		new ImageIcon("MovingUpCar3.png"), new ImageIcon("MovingUpCar4.png"),
    		new ImageIcon("MovingUpCar5.png")};
    
    private ImageIcon movingCarDownImage = new ImageIcon();
    private ImageIcon movingCarUpImage = new ImageIcon();
    
    public MovingCar () {
		int rand = ThreadLocalRandom.current().nextInt(0, carMovingDownImages.length);
		movingCarDownImage = carMovingDownImages[rand];
		
		rand = ThreadLocalRandom.current().nextInt(0, carMovingDownImages.length);
		movingCarUpImage = carMovingUpImages[rand];
	}
	
    
    private int moveDistanceDown = 100;
    private int moveDistanceUp = -100;
    private Point position;

    /**
     * @return the MAX_VERT
     */
    public static int getMAX_VERT() {
        return MAX_VERT;
    }
    
    /**
     * @return the MAX_VERT
     */
    public static int getMIN_VERT() {
        return MIN_VERT;
    }

    /**
     * @return the icon
     */
    public ImageIcon getDownIcon() {
        return movingCarDownImage;
    }
    
    public ImageIcon getUpIcon() {
        return movingCarUpImage;
    }

    /**
     * @return the move down Distance
     */
    public int getMoveDistanceDown() {
    	moveDistanceDown = ThreadLocalRandom.current().nextInt(50, 200);
    	int randomizer = moveDistanceDown % 100;
    	moveDistanceDown = moveDistanceDown - randomizer;
    	System.out.println("DOWN " + moveDistanceDown);
        return moveDistanceDown;
    }
    
    /**
     * @return the move up Distance
     */
    public int getMoveDistancUp() {
    	moveDistanceUp = ThreadLocalRandom.current().nextInt(-200, -50);
    	int randomizer = moveDistanceUp % 100;
    	moveDistanceUp = moveDistanceUp - randomizer;
    	System.out.println("UP " + moveDistanceUp);
        return moveDistanceUp;
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
