package shoppingcartmadness;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.event.KeyEvent;

//--------------------------------------------- Player Class ---------------------------------------------

public class Player {
	
//--------------------------------------------- Member Fields --------------------------------------------

	private final String PLAYER_DIE_IMG = "blood_splatter.png";
	private final String PLAYER_IMG = "Player.png";
	private final int X_SPAWN = 300;
	private final int Y_SPAWN = 400;
	private final int MOVE_DISTANCE = 100;
	public final int MAX_LIVES = 3;
	
	private int lives = MAX_LIVES;
	private Boolean alive = true;
	private Boolean spawned = false;
	private boolean canMove = true;
	private Integer moveCode;
	private String moveDirection = "None";
	private ParkingLot parkingLot = new ParkingLot();
	
	private ImageIcon defaultPlayerImage = new ImageIcon(PLAYER_IMG);
	private ImageIcon playerImage = new ImageIcon(PLAYER_IMG);
	private ImageIcon deathImage = new ImageIcon(PLAYER_DIE_IMG);
	private Point playerPos = new Point(X_SPAWN, Y_SPAWN);
	private final Point spawnPos = new Point(X_SPAWN, Y_SPAWN);
	private Point movePosition = new Point();

//----------------------------------------------- Member Functions ---------------------------------------
    
  public Boolean isAlive () {return alive;}
  public void setAlive (Boolean alive) {this.alive = alive;}
  public Boolean getSpawned() {return spawned;}
  public void setSpawned(Boolean spawned) {this.spawned = spawned;}
  public void setCanMove (boolean canMove) {this.canMove = canMove;}
  public boolean canMove () {return canMove;}
  
  public int getLives () {return lives;}
  public void setLives(int lives) {this.lives = lives;}
  
  public Integer getMoveCode () {return moveCode;}
  public void setMoveCode (Integer moveCode) {this.moveCode = moveCode;}
  
  public ImageIcon getPlayerImage () {return playerImage;}
  public void setPlayerImage () {playerImage = defaultPlayerImage;}
  public void setDeathImage () {playerImage = deathImage;}
  
  public Point getPosition() {return playerPos;}
  public void setSpawnPos () {playerPos.setLocation(spawnPos);}


//--------------------------------------------------------------------------------------------------------
// Functions: move, moveUp, moveDown, moveRight, moveLeft
// Operations: Changes the player's position on the map. Works with the isValidMove function
//             to determine if the move is valid.
//--------------------------------------------------------------------------------------------------------
  public void move() {
			movePosition.setLocation(playerPos);
		  Integer code = this.getMoveCode();
		  if (code == null) return;
		  if (code == KeyEvent.VK_UP) this.moveUp();
		  if (code == KeyEvent.VK_DOWN) this.moveDown();
		  if (code == KeyEvent.VK_LEFT) this.moveLeft();
		  if (code == KeyEvent.VK_RIGHT) this.moveRight();
		  this.setMoveCode(null);
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
      if (playerPos.equals(spawnPos)) return false;
      if (playerPos.y == 0) return false;
    } 
    else if (moveDirection == "Down") {
      if (playerPos.x == spawnPos.x && playerPos.y == spawnPos.y + MOVE_DISTANCE) return false;
      if (playerPos.y == parkingLot.getMapSize().y) return false;
    } 
    else if (moveDirection == "Right") {
      if (playerPos.x == parkingLot.getMapSize().x) return false;
    }
    else if (moveDirection == "Left") {
        if (playerPos.x == spawnPos.x) return false;
        if (playerPos.x == (spawnPos.x + MOVE_DISTANCE)) 
          if (playerPos.y != spawnPos.y && playerPos.y != spawnPos.y + MOVE_DISTANCE) 
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
