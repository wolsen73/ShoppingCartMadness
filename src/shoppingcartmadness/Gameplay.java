package shoppingcartmadness;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.logging.*;

//---------------------------------------------- Gameplay Class ----------------------------------------------

public class Gameplay extends JPanel implements ActionListener, KeyListener {

//---------------------------------------------- Member Functions --------------------------------------------

    public static int FRAME_HEIGHT = 940;
    public static int FRAME_WIDTH = 1800;
    private static int CAR1_HOZ = 400;
    private static int CAR2_HOZ = 700;
    private static int CAR3_HOZ = 1100;
    private static int CAR4_HOZ = 1500;
    private static int CAR_MOVEMENT_ITERATIONS = 20; // every 1 second (5 loops) move the cars
    private static int CAR_VER = 0;
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
    private Car[] cars = new Car[4];// change to constant
    private CartCompletion[] cartsCompleted = new CartCompletion[NUM_COMPLETION_POINTS];
    private Point[] completionPoints = {COM_POINT1, COM_POINT2, COM_POINT3, COM_POINT4,
        COM_POINT5, COM_POINT6, COM_POINT7, COM_POINT8, COM_POINT9};
    private int gameLoopIteration = 0;
    private ParkingLot parkingLot = new ParkingLot();
    private Player player = new Player();

//---------------------------------------------- Member Functions --------------------------------------------
    
    public Gameplay() {
      timer.start();
      addKeyListener(this);
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);

      // Lambda Runnable for game loop to keep the UI responsive
      Runnable task2 = () -> {gameLoop();};
      new Thread(task2).start();
    }

    public void actionPerformed(ActionEvent e) {repaint();}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        player.setMoveCode(code);
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      parkingLot.getMapImage().paintIcon(this, g, 0, 0);
      player.getPlayerImage().paintIcon(this, g, player.getPosition().x, player.getPosition().y);
      
      // Paint parked cars
      for (int i = 0; i < ParkingLot.parkedCars.size(); i++) {
      	ParkingLot.parkedCars.get(i).getImage().paintIcon(this, g, 
      			ParkingLot.parkedCars.get(i).getPosition().x,
      			ParkingLot.parkedCars.get(i).getPosition().y);
      }
      
      // Set cars
      for (int i = 0; i < cars.length; i++) {
        cars[i].getIcon().paintIcon(this, g, cars[i].getPosition().x, cars[i].getPosition().y);
      }
      
      for (int i = 0; i < cartsCompleted.length; i++) {
        CartCompletion cart = cartsCompleted[i];
        if (cart != null) {
          cartsCompleted[i].getIcon().paintIcon(this, g,
        	cartsCompleted[i].getPosition().x,
          cartsCompleted[i].getPosition().y);
        }
      }
    }

    /**
     * Checks to make sure that the cart area is full.
     *
     * @return true if full, false if not
     */
    private Boolean cartAreaFull() {

        for (int i = 0; i < cartsCompleted.length; i++) {
            CartCompletion cart = cartsCompleted[i];
            if (cart == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * The main game loop which keeps running until the game ends.
     */
    private void gameLoop() {

        initCars();
        parkingLot.increaseLevel();

        // ask player if they want to start a game(for now, assume they do)
        //if no, exit program
        // if yes, set life to 3
        while (true) {

            try {Thread.sleep(GAME_LOOP_PAUSE);} 
            catch (InterruptedException ex) {
                Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
            }
            gameLoopIteration++;
            //check to see if player is spawned
            if (!player.isAlive()) {
                // if no, check to see if player has a life left
                int lives = player.getLives();
                if (lives > 0) {
                    // if player has a life - 1
                    player.setLives(lives - 1);
                    player.setAlive(true);
                    player.setPlayerImage();
                } else {
                    //if player has no life, display game over( ask to replay?)
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
            for (int i = 0; i < cars.length; i++) {
                Car car = cars[i];
                // is player hit?
                if (car.getPosition().x == playerPostion.x
                        && car.getPosition().y == playerPostion.y) {
                    // yes- set player spawn to false, continue
                    player.setSpawned(false);
                    player.setAlive(false);
                    // TODO: bad player sound
                    player.setDeathImage();
                }

            }

            //if player dead, continue
            if (!player.isAlive()) {
                continue;
            }
            completion:
            {
                //check to see if player in shopping cart return area
                Point p = completionPoints[0];
                if (p.x == playerPostion.x) {
                    // TODO: make good sound
                    // set a green
                		parkingLot.increaseLevel();
                    CartCompletion cart = new CartCompletion();
                    cart.setPosition(p);
                    insertCompletedCart(cart);
                    // add green cart icon to cart area
                    switch (getCartsCompleted()) {
                        case 1:
                            cart.setPosition(COM_POINT1);
                            player.setSpawned(Boolean.FALSE);
                            break completion;
                        case 2:
                            cart.setPosition(COM_POINT2);
                            player.setSpawned(Boolean.FALSE);
                            break completion;
                        case 3:
                            cart.setPosition(COM_POINT3);
                            player.setSpawned(Boolean.FALSE);
                            break completion;
                        case 4:
                            cart.setPosition(COM_POINT4);
                            player.setSpawned(Boolean.FALSE);
                            break completion;
                        case 5:
                            cart.setPosition(COM_POINT5);
                            player.setSpawned(Boolean.FALSE);
                            break completion;
                        case 6:
                            cart.setPosition(COM_POINT6);
                            player.setSpawned(Boolean.FALSE);
                            break completion;
                        case 7:
                            cart.setPosition(COM_POINT7);
                            player.setSpawned(Boolean.FALSE);
                            break completion;
                        case 8:
                            cart.setPosition(COM_POINT8);
                            player.setSpawned(Boolean.FALSE);
                            break completion;
                        case 9:
                            cart.setPosition(COM_POINT9);
                            player.setSpawned(Boolean.FALSE);
                            break completion;
                        default:
                            break completion;

                    }
                }
            }
            // if in return area, check to see if return area is full
            //if return area full, win game
            if (cartAreaFull()) {
                // TODO: show game win to user
                // possibly ask to play again
                break;
            }

            // move player
            player.move();

            // check to see if cars should move
            if (gameLoopIteration % CAR_MOVEMENT_ITERATIONS == 0) {
                // move cars
                for (int i = 0; i < cars.length; i++) {
                    Car car = cars[i];
                    if (car.getPosition().y == car.getMAX_VERT()) { // TODO: replace 800 with constant for max
                        car.getPosition().y = 0;
                    } else {
                        car.getPosition().y += car.getMoveDistance();
                    }
                }
            }
        }
    }

    /**
     * Gets the completed carts size;
     *
     * @return
     */
    private int getCartsCompleted() {
        int size = 0;
        for (int i = 0; i < cartsCompleted.length; i++) {
            CartCompletion cart = cartsCompleted[i];
            if (cart != null) {
                size++;
            }
        }
        return size;
    }

    /**
     * Initializes the cars used in the game.
     */
    private void initCars() {
        //set cars
        for (int i = 0; i < cars.length; i++) {
            //instatiate new car
            Car car = new Car();
            switch (i) {
                case 0:
                    car.setPosition(new Point(CAR1_HOZ, CAR_VER));
                    break;
                case 1:
                    car.setPosition(new Point(CAR2_HOZ, CAR_VER + 200));
                    break;
                case 2:
                    car.setPosition(new Point(CAR3_HOZ, CAR_VER + 400));
                    break;
                case 3:
                    car.setPosition(new Point(CAR4_HOZ, CAR_VER + 600));
                    break;
                default:
                    break;

            }
            cars[i] = car;
        }
    }

    /**
     * Inserts a cart at the first null element of the list. Doesnt insert if
     * list is full.
     *
     * @param cart cart to insert
     */
    private void insertCompletedCart(CartCompletion cart) {

        for (int i = 0; i < cartsCompleted.length; i++) {
            CartCompletion c = cartsCompleted[i];
            if (c == null) {
                cartsCompleted[i] = cart;
                return;
            }
        }
    }
}
