package shoppingcartmadness;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

/**
 *
 * @author Lunelune
 * @update will.olsen
 * @update Travis Kerns
 */

public class MovingCar {

//---------------------------------------------- Member Fields -----------------------------------------------
	
	private final int MIN_VERT = 0;
  private final int MAX_VERT = 800;
  private int moveDistanceDown = 100;
  private int moveDistanceUp = -100;
  private Point position;
     
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
  
//---------------------------------------------- Member Functions --------------------------------------------
  
  public MovingCar (Point position) {
  	this.position = position;
		int rand = ThreadLocalRandom.current().nextInt(0, carMovingDownImages.length);
		movingCarDownImage = carMovingDownImages[rand];
		rand = ThreadLocalRandom.current().nextInt(0, carMovingDownImages.length);
		movingCarUpImage = carMovingUpImages[rand];
	}
	
  public int getMAX_VERT() {return MAX_VERT;}
  public int getMIN_VERT() {return MIN_VERT;}
  public ImageIcon getDownIcon() {return movingCarDownImage;}
  public ImageIcon getUpIcon() {return movingCarUpImage;}
  public Point getPosition() {return position;}
  public void setPosition(Point position) {this.position = position;}

  /**
   * @return the move down Distance
   */
  public int getMoveDistanceDown() {
  	moveDistanceDown = ThreadLocalRandom.current().nextInt(50, 200);
  	int randomizer = moveDistanceDown % 100;
  	moveDistanceDown = moveDistanceDown - randomizer;
    return moveDistanceDown;
  }
  
  /**
   * @return the move up Distance
   */
  public int getMoveDistancUp() {
		moveDistanceUp = ThreadLocalRandom.current().nextInt(-200, -50);
		int randomizer = moveDistanceUp % 100;
		moveDistanceUp = moveDistanceUp - randomizer;
	  return moveDistanceUp;
  }
}
