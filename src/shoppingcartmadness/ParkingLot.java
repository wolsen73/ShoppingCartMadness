package shoppingcartmadness;

import javax.swing.ImageIcon;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//------------------------------------------- ParkingLot Class -------------------------------------------

public class ParkingLot {
	
//------------------------------------------- Member Fields ----------------------------------------------

	private final int PARKED_CARS_PER_LVL = 2;
  private final ImageIcon MAP = new ImageIcon("ParkingLot.png");
  private final Point MAP_SIZE = new Point(1700, 800);
  private static int currentLevel = 0;
  
  public static ArrayList<ParkedCar> parkedCars = new ArrayList<ParkedCar>();
  
  // Not used yet (may use in future)
  //public final int[] columns = {400, 500, 600, 700, 800, 900, 1000,
      //1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800};
  
  public final int[] safeColumns = {600, 900, 1000, 1300, 1400};
  public final int[] rows = {0, 100, 200, 300, 400, 500, 600, 700, 800};

//------------------------------------------- Member Functions -------------------------------------------
    
  public ImageIcon getMapImage() {return MAP;}
  public Point getMapSize() {return MAP_SIZE;}
  
  private int getRowIndex () {
		return ThreadLocalRandom.current().nextInt(0, rows.length);
	}
	private int getSafeColumnIndex () {
		return ThreadLocalRandom.current().nextInt(0, safeColumns.length);
	}
  
//--------------------------------------------------------------------------------------------------------
// Function: increaseLevel
// Operations: Increases the level (difficulty) by adding more parked cars which act as obstacles. Works
//            with the isValidParkingSpot function to determine if the spawn is not already taken.
//--------------------------------------------------------------------------------------------------------
  public void increaseLevel () { 
  	currentLevel++;
  	Point spawn;
  	for (int i = 0; i < PARKED_CARS_PER_LVL; i++) {
  		do {
  			spawn = new Point (safeColumns[getSafeColumnIndex()], rows[getRowIndex()]);
  		} while (isValidParkingSpot(spawn));
  		parkedCars.add(new ParkedCar(spawn));
  	}
  }

//--------------------------------------------------------------------------------------------------------
// Function: isValidParkingSpot
// Operations: Returns true if the spot is already taken by a parked car (false otherwise).
//--------------------------------------------------------------------------------------------------------
	private boolean isValidParkingSpot (Point spawn) {
		for (int i = 0; i < parkedCars.size(); i++)
			if (spawn.equals(parkedCars.get(i).getPosition())) return true;
		return false;
	}
}
	
	
	
