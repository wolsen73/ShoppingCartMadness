package shoppingcartmadness;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.event.KeyEvent;

//--------------------------------------------- Player Class ---------------------------------------------
public class Player {

    public static String PLAYER_DIE_IMG = "blood_splatter.png";
    public static String PLAYER_IMG = "Player.png";

    private static int X_SPAWN = 300;
    private static int Y_SPAWN = 400;

    private Boolean alive = false;
    private int lives;
    private Integer moveCode;
    private String moveDirection = "None";
    private final int moveDistance = 100;
    private ParkingLot parkingLot = new ParkingLot();

    private ImageIcon playerIcon = new ImageIcon(PLAYER_IMG);
    private Point playerPos = new Point(getX_SPAWN(), getY_SPAWN());
    private final Point spawnPos = new Point(getX_SPAWN(), getY_SPAWN());
    //--------------------------------------------- Member Fields --------------------------------------------
    private Boolean spawned = false;

    /**
     * @return the alive
     */
    public Boolean getAlive() {
        return alive;
    }

    /**
     * @param alive the alive to set
     */
    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    /**
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * @param lives the lives to set
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * @return the moveCode
     */
    public Integer getMoveCode() {
        return moveCode;
    }

    /**
     * @param moveCode the moveCode to set
     */
    public void setMoveCode(Integer moveCode) {
        this.moveCode = moveCode;
    }

    public int getMoveDistance() {
        return moveDistance;
    }

    /**
     * @return the playerIcon
     */
    public ImageIcon getPlayerIcon() {
        return playerIcon;
    }

    /**
     * @param playerIcon the playerIcon to set
     */
    public void setPlayerIcon(ImageIcon playerIcon) {
        this.playerIcon = playerIcon;
    }

//--------------------------------------------- Member Functions -----------------------------------------
    public ImageIcon getPlayerImage() {
        return getPlayerIcon();
    }

    public Point getPlayerPos() {
        return playerPos;
    }

    /**
     * @param playerPos the playerPos to set
     */
    public void setPlayerPos(Point playerPos) {
        this.playerPos = playerPos;
    }

    /**
     * @return the spawnPos
     */
    public Point getSpawnPos() {
        return spawnPos;
    }

    /**
     * @return the spawned
     */
    public Boolean getSpawned() {
        return spawned;
    }

    /**
     * @param spawned the spawned to set
     */
    public void setSpawned(Boolean spawned) {
        this.spawned = spawned;
    }

//--------------------------------------------------------------------------------------------------------
// Functions: moveUp, moveDown, moveRight, moveLeft
// Operations: Changes the player's position on the map. Works with the isValidMove function
//             to determine if the move is valid.
//--------------------------------------------------------------------------------------------------------
    public void move() {
        Integer code = this.getMoveCode();
        if (code == null) {
            return;
        }
        if (code == KeyEvent.VK_UP) {
            this.moveUp();
        }
        if (code == KeyEvent.VK_DOWN) {
            this.moveDown();
        }
        if (code == KeyEvent.VK_LEFT) {
            this.moveLeft();
        }
        if (code == KeyEvent.VK_RIGHT) {
            this.moveRight();
        }
        this.setMoveCode(null);
    }

    public void moveDown() {
        moveDirection = "Down";
        if (isValidMove()) {
            playerPos.y += moveDistance;
        }
    }

    public void moveLeft() {
        moveDirection = "Left";
        if (isValidMove()) {
            playerPos.x -= moveDistance;
        }
    }

    public void moveRight() {
        moveDirection = "Right";
        if (isValidMove()) {
            playerPos.x += moveDistance;
        }
    }

    public void moveUp() {
        moveDirection = "Up";
        if (isValidMove()) {
            playerPos.y -= moveDistance;
        }
    }

//--------------------------------------------------------------------------------------------------------
//Function: isValidMove
//Operations: Returns false if the player's move is invalid. Invalid moves include: moving off the map.
//            Otherwise, returns true. Works with nonDangerCollision to determine if the player walked
//            into a parked car.
//--------------------------------------------------------------------------------------------------------
    private boolean isValidMove() {
        if (moveDirection == "Up") {
            if (getPlayerPos().equals(getSpawnPos())) {
                return false;
            }
            if (getPlayerPos().y == 0) {
                return false;
            }
        } else if (moveDirection == "Down") {
            if (getPlayerPos().x == getSpawnPos().x && getPlayerPos().y == getSpawnPos().y + moveDistance) {
                return false;
            }
            if (getPlayerPos().y == parkingLot.getMapSize().y) {
                return false;
            }
        } else if (moveDirection == "Right") {
            if (getPlayerPos().x == parkingLot.getMapSize().x) {
                return false;
            }
        } else if (moveDirection == "Left") {
            if (getPlayerPos().x == getSpawnPos().x) {
                return false;
            }
            if (getPlayerPos().x == (getSpawnPos().x + moveDistance)) {
                if (getPlayerPos().y != getSpawnPos().y && getPlayerPos().y != getSpawnPos().y + moveDistance) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return the X_SPAWN
     */
    public static int getX_SPAWN() {
        return X_SPAWN;
    }

    /**
     * @return the Y_SPAWN
     */
    public static int getY_SPAWN() {
        return Y_SPAWN;
    }
}
