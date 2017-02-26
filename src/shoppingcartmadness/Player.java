package shoppingcartmadness;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.event.KeyEvent;

//--------------------------------------------- Player Class ---------------------------------------------

public class Player {
	
//--------------------------------------------- Member Fields --------------------------------------------

	private final String PLAYER_DIE_IMG = "blood_splatter.png";
	private final String PLAYER_IMG = "Player.png";
	private final int MOVE_DISTANCE = 100;
	public final int MAX_LIVES = 3;
	
	private int lives = MAX_LIVES;
	private boolean alive = true;
	private boolean canMove = true;
	private int moveCode = 0;
	private String moveDirection = "None";
	private ParkingLot parkingLot = new ParkingLot();
	
	private ImageIcon defaultPlayerImage = new ImageIcon(PLAYER_IMG);
	private ImageIcon playerImage = new ImageIcon(PLAYER_IMG);
	private ImageIcon deathImage = new ImageIcon(PLAYER_DIE_IMG);
	private Point playerPos = new Point(300, 400);
	private final Point SPAWN_POS = new Point(300, 400);
	private Point movePosition = new Point();

//----------------------------------------------- Member Functions ---------------------------------------
    
  public boolean isAlive () {return alive;}
  public boolean canMove () {return canMove;}
  
  public int getLives () {return lives;}
  public void setLives(int lives) {this.lives = lives;}
  
  public void setMoveCode (int moveCode) {this.moveCode = moveCode;}
  public ImageIcon getPlayerImage () {return playerImage;}
  
  public Point getPosition () {return playerPos;}
  public void setSpawnPos () {playerPos.setLocation(SPAWN_POS);}
  
//--------------------------------------------------------------------------------------------------------
// Functions: kill, respawn, and reset
// Operations: Sets player attributes according to whether or not the player was killed or respawned.
//             Reset should only be called when a new game is created.
//--------------------------------------------------------------------------------------------------------
  public void kill () {
  	alive = false;
  	canMove = false;
  	lives -= 1;
  	playerImage = deathImage;
  }
  
  public void deathRespawn () {
  	alive = true;
  	canMove = true;
  	playerImage = defaultPlayerImage;
  	playerPos.setLocation(SPAWN_POS);
  }
  
  public void reset () {
  	alive = true;
  	canMove = true;
  	lives = MAX_LIVES;
  	playerImage = defaultPlayerImage;
  	playerPos.setLocation(SPAWN_POS);
  }

//--------------------------------------------------------------------------------------------------------
// Functions: move, moveUp, moveDown, moveRight, moveLeft
// Operations: Changes the player's position on the map. Works with the isValidMove function
//             to determine if the move is valid.
//--------------------------------------------------------------------------------------------------------
  public void move() {
			movePosition.setLocation(playerPos);
		  if (moveCode == KeyEvent.VK_UP) this.moveUp();
		  if (moveCode == KeyEvent.VK_DOWN) this.moveDown();
		  if (moveCode == KeyEvent.VK_LEFT) this.moveLeft();
		  if (moveCode == KeyEvent.VK_RIGHT) this.moveRight();
		  this.setMoveCode(0);
  }
  public void moveDown() {
    moveDirection = "Down";
    movePosition.y += MOVE_DISTANCE;
    if (isValidMove()) playerPos.setLocation(movePosition);
  }
  public void moveLeft() {
    moveDirection = "Left";
    movePosition.x -= MOVE_DISTANCE;
    if (isValidMove()) playerPos.setLocation(movePosition);
  }
  public void moveRight() {
    moveDirection = "Right";
    movePosition.x += MOVE_DISTANCE;
    if (isValidMove()) playerPos.setLocation(movePosition);
  }
  public void moveUp() {
    moveDirection = "Up";
    movePosition.y -= MOVE_DISTANCE;
    if (isValidMove()) playerPos.setLocation(movePosition);
  }

//--------------------------------------------------------------------------------------------------------
// Function: isValidMove
// Operations: Returns false if the player's move is invalid. Invalid moves include: moving off the map.
//            Otherwise, returns true. Works with isCollisionDetected to determine if the player walked
//            into a parked car.
//--------------------------------------------------------------------------------------------------------
  private boolean isValidMove() {
    if (moveDirection == "Up") {
      if (playerPos.equals(SPAWN_POS)) return false;
      if (playerPos.y == 0) return false;
    } 
    else if (moveDirection == "Down") {
      if (playerPos.x == SPAWN_POS.x && playerPos.y == SPAWN_POS.y + MOVE_DISTANCE) return false;
      if (playerPos.y == parkingLot.getMapSize().y) return false;
    } 
    else if (moveDirection == "Right") {
      if (playerPos.x == parkingLot.getMapSize().x) return false;
    }
    else if (moveDirection == "Left") {
        if (playerPos.x == SPAWN_POS.x) return false;
        if (playerPos.x == (SPAWN_POS.x + MOVE_DISTANCE)) 
          if (playerPos.y != SPAWN_POS.y && playerPos.y != SPAWN_POS.y + MOVE_DISTANCE) 
            return false;
    }
    if(isCollisionDetected()) return false;
    else return true;
  }
  
//--------------------------------------------------------------------------------------------------------
// Function: isCollisionDetected
// Operations: Returns true if collision is detected between a player and a parked car (false otherwise)
//--------------------------------------------------------------------------------------------------------
  private boolean isCollisionDetected () {
  	for (int i = 0; i < ParkingLot.parkedCars.size(); i++) {
  		if (movePosition.equals(ParkingLot.parkedCars.get(i).getPosition())) return true;
  		else continue;
  	}
  	return false;
  }
}
