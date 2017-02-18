import javax.swing.ImageIcon;
import java.awt.Point;

//--------------------------------------------- Player Class ---------------------------------------------

public class Player {

//--------------------------------------------- Member Fields --------------------------------------------
	
	private final ImageIcon playerIcon = new ImageIcon("Player.png");
	private final Point spawnPos = new Point(300, 400);
	private Point playerPos = new Point(300, 400);
	private final int moveDistance = 100;
	private String moveDirection = "None";
	private ParkingLot parkingLot = new ParkingLot();
	
	
//--------------------------------------------- Member Functions -----------------------------------------

	public ImageIcon getPlayerImage () {return playerIcon;}
	public Point getPlayerPos () {return playerPos;}
	public int getMoveDistance () {return moveDistance;}
	
	
//--------------------------------------------------------------------------------------------------------
// Functions: moveUp, moveDown, moveRight, moveLeft
// Operations: Changes the player's position on the map. Works with the isValidMove function
//             to determine if the move is valid.
//--------------------------------------------------------------------------------------------------------
	public void moveUp () {
		moveDirection = "Up";
		if (isValidMove()) playerPos.y -= moveDistance;
	}
	public void moveDown () {
		moveDirection = "Down";
		if (isValidMove()) playerPos.y += moveDistance;
	}
	public void moveRight () {
		moveDirection = "Right";
		if (isValidMove()) playerPos.x += moveDistance;
	}
	public void moveLeft () {
		moveDirection = "Left";
		if (isValidMove()) playerPos.x -= moveDistance;
	}
	
	
//--------------------------------------------------------------------------------------------------------
//Function: isValidMove
//Operations: Returns false if the player's move is invalid. Invalid moves include: moving off the map.
//            Otherwise, returns true. Works with nonDangerCollision to determine if the player walked
//            into a parked car.
//--------------------------------------------------------------------------------------------------------
	private boolean isValidMove () {
		if (moveDirection == "Up") {
			if (playerPos.equals(spawnPos)) return false;
			if (playerPos.y == 0) return false;
		}
		else if (moveDirection == "Down") {
			if (playerPos.x == spawnPos.x && playerPos.y == spawnPos.y + moveDistance) return false; 
			if (playerPos.y == parkingLot.getMapSize().y) return false;
		}
		else if (moveDirection == "Right") {
			if (playerPos.x == parkingLot.getMapSize().x) return false; 
		}
		else if (moveDirection == "Left") {
			if (playerPos.x == spawnPos.x) return false;
			if (playerPos.x == (spawnPos.x + moveDistance)) 
				if (playerPos.y != spawnPos.y && playerPos.y != spawnPos.y + moveDistance)
					return false;
		} 
		return true;
	}
	

//--------------------------------------------- Haven't Written Yet --------------------------------------
	
	private boolean nonDangerCollision () {return false;}
	private boolean dangerCollision () {return false;}
	private boolean wonLevel () {return false;}
}
