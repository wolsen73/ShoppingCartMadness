package shoppingcartmadness;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.logging.*;

//---------------------------------------------- Gameplay Class ----------------------------------------------

public class Gameplay extends JPanel implements ActionListener, KeyListener, MouseListener {

//---------------------------------------------- Member Functions --------------------------------------------
	
	private final int NUM_COMPLETION_POINTS = 9;
	private final int GAME_LOOP_PAUSE = 30; 
  private static final long serialVersionUID = 1L;
  public Timer timer = new Timer(5, this);
  private int gameLoopIteration = 0;
	
  private MovingCar[] carsMovingDown = new MovingCar[4];
  private MovingCar[] carsMovingUp = new MovingCar[4];
	private final Point[] DOWN_CAR_POS = {new Point(400, 0), new Point(700, 0),
	  new Point(1100, 0), new Point(1500, 0)};
	private final Point[] UP_CAR_POS = {new Point(500, 800), new Point(800, 800),
		  new Point(1200, 800), new Point(1600, 800)};
  
  private ArrayList<CartCompletion> cartsCompleted = 
  		new ArrayList<CartCompletion>(NUM_COMPLETION_POINTS);

  private Player player = new Player();
  private ParkingLot parkingLot = new ParkingLot();
  
  private ImageIcon[] levelImages = {new ImageIcon("1.png"), new ImageIcon("2.png"),
  		new ImageIcon("3.png"), new ImageIcon("4.png"), new ImageIcon("5.png"),
  		new ImageIcon("6.png"), new ImageIcon("7.png"), new ImageIcon("8.png"),
  		new ImageIcon("9.png")};
  private ImageIcon currentLevelImage = new ImageIcon("1.png");
  private ImageIcon display = new ImageIcon("Display.png");
  private ImageIcon heart = new ImageIcon("Heart.png");
  private ImageIcon gameOver = new ImageIcon("GameOver.png");
  private ImageIcon youWin = new ImageIcon("YouWin.png");
  private ImageIcon retryButton = new ImageIcon("RetryButton.png");
  private ImageIcon quitButton = new ImageIcon("QuitButton.png");
  
  private Point retryButtonPoint = new Point (500, 500); 
  private Point quitButtonPoint = new Point (1000, 500);
    
//---------------------------------------------- Member Functions --------------------------------------------
    
  public Gameplay() {
    timer.start();
    addKeyListener(this);
    addMouseListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);

    // Lambda Runnable for game loop to keep the UI responsive
    Runnable task2 = () -> {gameLoop();};
    new Thread(task2).start();
  }

  public void actionPerformed (ActionEvent e) {repaint();}
  public void keyReleased (KeyEvent e) {}
  public void keyTyped (KeyEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited (MouseEvent e) {}
  public void mousePressed (MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}
  
  
//--------------------------------------------------------------------------------------------------------
//Function: mouseReleased
//Operations: Detects if a GUI button was clicked. If the "retry" button was selected, a new game is 
//            started. If the "quit" button was selected, the program ends.
//-------------------------------------------------------------------------------------------------------
  public void mouseReleased (MouseEvent e) {
		if (player.getLives() == 0 || isCartAreaFull()) {
  		int x = e.getX();
  		int y = e.getY();
  		
		// If player pressed retry button (start new game)
		if (x >= retryButtonPoint.x && x <= retryButtonPoint.x + retryButton.getIconWidth())
			if (y >= retryButtonPoint.y && y <= retryButtonPoint.y + retryButton.getIconHeight()) {
				resetGame();
				Runnable thread = () -> {gameLoop();};
		    new Thread(thread).start();
			}
		
		// If player clicked quit button (end game)
		if (x >= quitButtonPoint.x && x <= quitButtonPoint.x + quitButton.getIconWidth())
			if (y >= quitButtonPoint.y && y <= quitButtonPoint.y + quitButton.getIconHeight()) 
				System.exit(0);
  	}
	}
	
  
//--------------------------------------------------------------------------------------------------------
// Function: keyPressed
// Operations: Translates keyboard input into player movement
//-------------------------------------------------------------------------------------------------------
  public void keyPressed (KeyEvent e) {
    int code = e.getKeyCode();
    if(player.canMove()) player.setMoveCode(code);
  }
  
  
//--------------------------------------------------------------------------------------------------------
//Function: paintComponent
//Operations: Responsible for painting all graphics on the screen
//-------------------------------------------------------------------------------------------------------
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    parkingLot.getMapImage().paintIcon(this, g, 0, 0);
    player.getPlayerImage().paintIcon(this, g, player.getPosition().x, player.getPosition().y);
    display.paintIcon(this, g, -4, 0);
    currentLevelImage.paintIcon(this, g, 310, 11);
    
    // Paint hearts (lives)
    int temp = 10;
    for (int i = 0; i < player.getLives(); i++) {
    	heart.paintIcon(this, g, temp, 117);
    	temp += 120;
    }
    
    // Paint parked cars
    for (int i = 0; i < ParkingLot.parkedCars.size(); i++) {
			ParkingLot.parkedCars.get(i).getImage().paintIcon(this, g, 
			ParkingLot.parkedCars.get(i).getPosition().x,
			ParkingLot.parkedCars.get(i).getPosition().y);
    }
      
    // Paint cars moving Down
    for (int i = 0; i < carsMovingDown.length; i++) {
    	carsMovingDown[i].getDownIcon().paintIcon(this, g, 
    	carsMovingDown[i].getPosition().x, 
    	carsMovingDown[i].getPosition().y);
    }
    
    // Paint cars moving Up
    for (int i = 0; i < carsMovingUp.length; i++) {
    	carsMovingUp[i].getUpIcon().paintIcon(this, g, 
    	carsMovingUp[i].getPosition().x, 
    	carsMovingUp[i].getPosition().y);  
    }
    
    // Paint completion carts
    for (int i = 0; i < cartsCompleted.size(); i++) {
      if (cartsCompleted.get(i) != null) {
        cartsCompleted.get(i).getIcon().paintIcon(this, g,cartsCompleted.get(i).getPosition().x,
      		cartsCompleted.get(i).getPosition().y);
      }
    }
    
    // Paint "Game Over" UI
    if (player.getLives() == 0) {
    	gameOver.paintIcon(this, g, 550, 200);
    	retryButton.paintIcon(this, g, retryButtonPoint.x, retryButtonPoint.y);
    	quitButton.paintIcon(this, g, quitButtonPoint.x, quitButtonPoint.y);
    }
    
    // Paint "You Win" UI
    if (isCartAreaFull()) {
    	youWin.paintIcon(this, g, 550, 200);
    	retryButton.paintIcon(this, g, retryButtonPoint.x, retryButtonPoint.y);
    	quitButton.paintIcon(this, g, quitButtonPoint.x, quitButtonPoint.y);
    }
  }

  
//--------------------------------------------------------------------------------------------------------
//Function: gameLoop
//Operations: The main game loop that controls the majority of gameplay aspects.
//--------------------------------------------------------------------------------------------------------
  private void gameLoop() {
  	
  	// Sets the game up by initiating parked and moving cars
    initCars();
    parkingLot.increaseLevel();

    while (true) {
    	try {Thread.sleep(GAME_LOOP_PAUSE);} 
      catch (InterruptedException ex) {
          Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
      }
    	
    	// Moves player and iterates the game loop
    	player.move();
    	gameLoopIteration++;
    	
    	// Respawns the player if dead and lives are left. Ends game if dead and no lives are left.
      if (!player.isAlive()) {
      	if(player.getLives() > 0) player.deathRespawn();
      	else break;
      }
      
      // Detects if the player has been hit by a car
      Point playerPostion = player.getPosition();
      for (int i = 0; i < carsMovingDown.length; i++) {
      	
        MovingCar carDown = carsMovingDown[i];
        MovingCar carUp = carsMovingUp[i];
        
        // Is player hit?
        if (carDown.getPosition().x == playerPostion.x && carDown.getPosition().y == playerPostion.y || 
      		carUp.getPosition().x == playerPostion.x && carUp.getPosition().y == playerPostion.y) {
        	
          // If yes - kill player, move cars, and wait to spawn player.
        	player.kill();
        	for ( int j = 0; j < 5; j++) {
        		if (carDown.getPosition().y == carDown.getMAX_VERT()) carDown.getPosition().y = 0;
        		else carDown.getPosition().y += 100;

        		if (carUp.getPosition().y <= carUp.getMIN_VERT()) carUp.getPosition().y = 800;
        		else carUp.getPosition().y += -100;
        		
        		try {Thread.sleep(100);} 
        	  catch (InterruptedException e) {e.printStackTrace();}
        	}
        }
      }
      
      // If player is dead, restart the game loop.
      if (!player.isAlive()) continue;
      
      // If player made it to the final lane, increase the level and difficulty.
      if (playerPostion.x == CartCompletion.COMP_X_COORD) increaseLevel();
      
      // If the return area is full, exit the loop and win game.
      if (isCartAreaFull()) break;
      
      /*
       * Controls the motion of the cars, speed of cars is based on
       * the value of the parkingLot.getCarIterations(). The value is changed
       * as the player progresses through levels.
       */
      if (gameLoopIteration % parkingLot.getCarIterations() == 0) {
        for (int i = 0; i < carsMovingDown.length; i++) {
          MovingCar carDown = carsMovingDown[i];
          MovingCar carUp = carsMovingUp[i];
          
          if (carDown.getPosition().y == carDown.getMAX_VERT()) carDown.getPosition().y = 0;
          else carDown.getPosition().y += carDown.getMoveDistanceDown();
          if (carUp.getPosition().y <= carUp.getMIN_VERT()) carUp.getPosition().y = 800;
          else carUp.getPosition().y += carUp.getMoveDistancUp();
        }
      }
    }
  }

  
//--------------------------------------------------------------------------------------------------------
//Function: initCars
//Operations: Initializes moving cars and sets their default positions.
//--------------------------------------------------------------------------------------------------------
  private void initCars() {
  	for (int i = 0; i < DOWN_CAR_POS.length; i++)
      carsMovingDown[i] = new MovingCar(DOWN_CAR_POS[i]);
  	for (int i = 0; i < DOWN_CAR_POS.length; i++)
      carsMovingUp[i] = new MovingCar(UP_CAR_POS[i]);
  }
	
  
//--------------------------------------------------------------------------------------------------------
// Function: isCartAreaFull
// Operations: Checks to see if the CartCompletion area is full (full when equal to 9). This indicates
//             that the game was won.
//--------------------------------------------------------------------------------------------------------
  private Boolean isCartAreaFull() {
    if (cartsCompleted.size() == NUM_COMPLETION_POINTS) return true;
    return false;
  }
  
  
//--------------------------------------------------------------------------------------------------------
// Function: increaseLevel
// Operations: Called when the player has reached the final lane. Function resets the players Position
//             to the spawn position. A new CartCompletion icon is spawned.  The level and difficulty
//             is increased.
//--------------------------------------------------------------------------------------------------------
  private void increaseLevel () {
  	cartsCompleted.add(new CartCompletion());
  	player.setSpawnPos();
    if (parkingLot.getCurrentLevel() < 9)
  		currentLevelImage = levelImages[parkingLot.getCurrentLevel()];
    parkingLot.increaseLevel();
  }
  
  
//--------------------------------------------------------------------------------------------------------
// Function: resetGame
// Operations: Called when the player selects the "retry" button. Resets all game data to their default
//            values.
//--------------------------------------------------------------------------------------------------------
  private void resetGame () {
  	player.reset();
  	parkingLot.reset();
  	CartCompletion.reset();
  	currentLevelImage = levelImages[0];
  	cartsCompleted.clear();
  }
}
