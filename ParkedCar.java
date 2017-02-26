package shoppingcartmadness;

import java.awt.Point;
import javax.swing.ImageIcon;
import java.util.concurrent.ThreadLocalRandom;

//------------------------------------------- ParkedCar Class -------------------------------------------

public class ParkedCar {
	
//------------------------------------------- Member Fields ---------------------------------------------
	
	private ImageIcon[] carImages = {
		new ImageIcon("ParkedCar1.png"), new ImageIcon("ParkedCar2.png"),
		new ImageIcon("ParkedCar3.png"), new ImageIcon("ParkedCar4.png"),
		new ImageIcon("ParkedCar5.png")};
	
	private Point position;
	private ImageIcon image = new ImageIcon();
	
//------------------------------------------- Member Functions ------------------------------------------
	
	public ParkedCar (Point position) {
		this.position = position;
		int rand = ThreadLocalRandom.current().nextInt(0, carImages.length);
		image = carImages[rand];
	}
	
	public Point getPosition () {return position;}
	public ImageIcon getImage () {return image;}
}
