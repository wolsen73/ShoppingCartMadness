package shoppingcartmadness;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.*;

//---------------------------------------------- Gameplay Class ----------------------------------------------

public class Gameplay extends JPanel implements ActionListener, KeyListener, MouseListener {

//---------------------------------------------- Member Functions --------------------------------------------

    public static int FRAME_HEIGHT = 940;
    public static int FRAME_WIDTH = 1800;
    private static int CAR1_DOWN_HOZ = 400;
    private static int CAR1_UP_HOZ = 500;
    private static int CAR2_DOWN_HOZ = 700;
    private static int CAR2_UP_HOZ = 800;
    private static int CAR3_DOWN_HOZ = 1100;
    private static int CAR3_UP_HOZ = 1200;
    private static int CAR4_DOWN_HOZ = 1500;
    private static int CAR4_UP_HOZ = 1600;
    private static int CAR_MOVEMENT_ITERATIONS = 15; // every 1 second (5 loops) move the cars, this gets modified based on level
    private static int CAR_VER_DOWN = 0;
    private static int CAR_VER_UP = 800;
    private static Point COM_POINT1 = new Point(1700, 0);
    private static Point COM_POINT2 = new Point(1700, 100);
    private static Point COM_POINT3 = new Point(1700, 200);
    private static Point COM_POINT4 = new Point(1700, 300);
    private static Point COM_POINT5 = new Point(1700, 400);
    private static Point COM_POINT6 = new Point(1700, 500);
    private static Point COM_POINT7 = new Point(1700, 600);
    private static Point COM_POINT8 = new Point(1700, 700);
    private static Point COM_POINT9 = new Point(1700, 800);
    
    private static int GAME_LOOP_PAUSE = 30; // milliseconds
    private static int NUM_COMPLETION_POINTS = 9;
    private static final long serialVersionUID = 1L;
    public Timer timer = new Timer(5, this);
    private int gameLoopIteration = 0;
    private ParkingLot parkingLot = new ParkingLot();
    private Player player = new Player();
    private Point retryButtonPoint = new Point (500, 500); 
    private Point quitButtonPoint = new Point (1000, 500);
    
    private MovingCar[] carsMovingDown = new MovingCar[4];// change to constant
    private MovingCar[] carsMovingUp = new MovingCar[4];
    private ArrayList<CartCompletion> cartsCompleted = 
    		new ArrayList<CartCompletion>(NUM_COMPLETION_POINTS);
    private Point[] completionPoints = {COM_POINT1, COM_POINT2, COM_POINT3, COM_POINT4,
        COM_POINT5, COM_POINT6, COM_POINT7, COM_POINT8, COM_POINT9};
    
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
  
  
  /**
   * Handles mouse clicking (button clicking)
   */
  public void mouseReleased (MouseEvent e) {
		if (player.getLives() == 0 || cartAreaFull()) {
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
	
  
  /**
   * Handles Keyboard input (for player movement)
   */
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    player.setMoveCode(code);
  }
  
  
  /**
   * Responsible for painting the majority of images on the screen
   */
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
    
    // Paint Game over UI
    if (player.getLives() == 0) {
    	gameOver.paintIcon(this, g, 550, 200);
    	retryButton.paintIcon(this, g, retryButtonPoint.x, retryButtonPoint.y);
    	quitButton.paintIcon(this, g, quitButtonPoint.x, quitButtonPoint.y);
    }
    
    // Paint win game UI
    if (cartAreaFull()) {
    	youWin.paintIcon(this, g, 550, 200);
    	retryButton.paintIcon(this, g, retryButtonPoint.x, retryButtonPoint.y);
    	quitButton.paintIcon(this, g, quitButtonPoint.x, quitButtonPoint.y);
    }
  }

  
  /**
   * The main game loop which keeps running until the game ends.
   */
  private void gameLoop() {
  	
    initCars();
    parkingLot.increaseLevel();

    while (true) {
    	
      try {Thread.sleep(GAME_LOOP_PAUSE);} 
      catch (InterruptedException ex) {
          Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
      }
      gameLoopIteration++;
      //check to see if player is spawned
      if (!player.isAlive()) {
        // if not, check to see if player has a life left
        if (player.getLives() > 0) {
          player.setAlive(true);
          player.setPlayerImage();
        } else {
          // if player has no life, display game over
          break;
        }
      }
      //set player position at spawn point
      if (!player.getSpawned()) {
          player.setPosition(new Point(player.getSpawnPos().x,
              player.getSpawnPos().y));
          player.setSpawned(true);
      }

      Point playerPostion = player.getPosition();
      for (int i = 0; i < carsMovingDown.length; i++) {
        MovingCar carDown = carsMovingDown[i];
        MovingCar carUp = carsMovingUp[i];
        // is player hit?
        if (carDown.getPosition().x == playerPostion.x
      		&& carDown.getPosition().y == playerPostion.y || 
      		carUp.getPosition().x == playerPostion.x
      		&& carUp.getPosition().y == playerPostion.y) {
          // yes- set player spawn to false, continue, set player lives - 1
      	  player.setLives(player.getLives() - 1);
          player.setSpawned(false);
          player.setAlive(false);
          player.setDeathImage();
        }
      }

      if (!player.isAlive()) {continue;}
      completion:
      {
        //check to see if player in shopping cart return area
        Point p = completionPoints[0];
        if (p.x == playerPostion.x) {
        	if (parkingLot.getCurrentLevel() < 9)
        		currentLevelImage = levelImages[parkingLot.getCurrentLevel()];
      		parkingLot.increaseLevel();
          CartCompletion cart = new CartCompletion();
          cart.setPosition(p);
          cartsCompleted.add(cart);
        
          
          // add green cart icon to cart area
          switch (cartsCompleted.size()) {
            case 1:
              cart.setPosition(COM_POINT1);
              player.setSpawned(Boolean.FALSE);
              CAR_MOVEMENT_ITERATIONS = 13;
              break completion;
            case 2:
              cart.setPosition(COM_POINT2);
              player.setSpawned(Boolean.FALSE);
              CAR_MOVEMENT_ITERATIONS = 11;
              break completion;
            case 3:
              cart.setPosition(COM_POINT3);
              player.setSpawned(Boolean.FALSE);
              CAR_MOVEMENT_ITERATIONS = 9;
              break completion;
            case 4:
              cart.setPosition(COM_POINT4);
              player.setSpawned(Boolean.FALSE);
              CAR_MOVEMENT_ITERATIONS = 8;
              break completion;
            case 5:
              cart.setPosition(COM_POINT5);
              player.setSpawned(Boolean.FALSE);
              CAR_MOVEMENT_ITERATIONS = 7;
              break completion;
            case 6:
              cart.setPosition(COM_POINT6);
              player.setSpawned(Boolean.FALSE);
              CAR_MOVEMENT_ITERATIONS =6;
              break completion;
            case 7:
              cart.setPosition(COM_POINT7);
              player.setSpawned(Boolean.FALSE);
              CAR_MOVEMENT_ITERATIONS = 5;
              break completion;
            case 8:
              cart.setPosition(COM_POINT8);
              player.setSpawned(Boolean.FALSE);
              CAR_MOVEMENT_ITERATIONS = 4;
              break completion;
            case 9:
              cart.setPosition(COM_POINT9);
              player.setSpawned(Boolean.FALSE);
              CAR_MOVEMENT_ITERATIONS = 3;
              break completion;
            default:
              break completion;
          }
        }
      }
      
      //if return area is full, win game
      if (cartAreaFull()) {break;}

      // move player
      player.move();
      
      // check to see if cars should move
    
      if (gameLoopIteration % CAR_MOVEMENT_ITERATIONS == 0) {
        // move cars
        for (int i = 0; i < carsMovingDown.length; i++) {
          MovingCar carDown = carsMovingDown[i];
          MovingCar carUp = carsMovingUp[i];
          if (carDown.getPosition().y == carDown.getMAX_VERT()) {
        	  carDown.getPosition().y = 0;
          }
          else {
        	  carDown.getPosition().y += carDown.getMoveDistanceDown();
          }
          if (carUp.getPosition().y == carUp.getMIN_VERT()) {
        	  carUp.getPosition().y = 800;
          }
          else {
        	  carUp.getPosition().y += carUp.getMoveDistancUp();
          }
        }
      }
    }
  }

  
  /**
   * Initializes the cars used in the game.
   * int test = ((int) (Math.random() * 700))%100;
int test1 = test - test%100;
test1
   */
  private void initCars() {
    for (int i = 0; i < carsMovingDown.length; i++) {
      MovingCar carDown = new MovingCar();
      if (i == 0) carDown.setPosition(new Point(CAR1_DOWN_HOZ, CAR_VER_DOWN));
      if (i == 1) carDown.setPosition(new Point(CAR2_DOWN_HOZ, CAR_VER_DOWN + 200));
      if (i == 2) carDown.setPosition(new Point(CAR3_DOWN_HOZ, CAR_VER_DOWN + 400));
      if (i == 3) carDown.setPosition(new Point(CAR4_DOWN_HOZ, CAR_VER_DOWN + 600));
      carsMovingDown[i] = carDown;
    }
      for (int i = 0; i < carsMovingUp.length; i++) {
          MovingCar carUp = new MovingCar();
          if (i == 0) carUp.setPosition(new Point(CAR1_UP_HOZ, CAR_VER_UP));
          if (i == 1) carUp.setPosition(new Point(CAR2_UP_HOZ, CAR_VER_UP + 100));
          if (i == 2) carUp.setPosition(new Point(CAR3_UP_HOZ, CAR_VER_UP + 300));
          if (i == 3) carUp.setPosition(new Point(CAR4_UP_HOZ, CAR_VER_UP + 500));
      carsMovingUp[i] = carUp;
    }
  }
	

  /**
   * Checks to make sure that the cart area is full.
   *
   * @return true if full, false if not
   */
  private Boolean cartAreaFull() {
    if (cartsCompleted.size() == NUM_COMPLETION_POINTS) return true;
    return false;
  }
  
  
  private void resetGame () {
  	player.setLives(player.MAX_LIVES);
  	player.setSpawned(Boolean.FALSE);
  	parkingLot.reset();
  	currentLevelImage = levelImages[0];
  	cartsCompleted.clear();
  }
}
