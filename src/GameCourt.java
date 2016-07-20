/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	
	// Bullets
	private ArrayList<Bullet> bullets;
	private ArrayList<EnemyBullet> enemyBullets;
	
	// Aliens - ArrayList of ArrayLists
	private ArrayList<ArrayList<GameObj>> aliens = 
			new ArrayList<ArrayList<GameObj>>();
	private ArrayList<GameObj> one;
	private ArrayList<GameObj> two;
	private ArrayList<GameObj> three;
	private ArrayList<GameObj> four;
	private ArrayList<GameObj> five;
	private ArrayList<GameObj> six;
	private ArrayList<GameObj> seven;
	private ArrayList<GameObj> eight;
	private ArrayList<GameObj> nine;
	private ArrayList<GameObj> ten;
	private ArrayList<GameObj> eleven;
	
	// Barriers
	private ArrayList<ArrayList<BarrierPiece>> barriers;
	private ArrayList<BarrierPiece> b1;
	private ArrayList<BarrierPiece> b2;
	private ArrayList<BarrierPiece> b3;
	private ArrayList<BarrierPiece> b4;

	// The tanks
	private Tank tank;
	
	// PowerUps
	private ArrayList<PowerUp> powerups;
	private int bulletsLimit;
	private boolean stopAliens = false;
	
	// Game score
	private int gameScore;

	public boolean playing = false; // whether the game is running
	private JLabel score; // Current status text (i.e. Running...)
	private JLabel lives;

	// Tick counter
	private int animationTick;
	
	// PowerUp counter
	private int powerUpScore;
	private int powerUpNumber;
	private boolean powerUpStatus;
	private int powerUpTick;
	
	// Game constants
	public static final int COURT_WIDTH = 500;
	public static final int COURT_HEIGHT = 500;
	public static final int tank_VELOCITY = 4;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;

	public GameCourt(JLabel score, JLabel lives) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the tank to move as long
		// as an arrow key is pressed, by changing the tank's
		// velocity accordingly. (The tick method below actually
		// moves the tank.)
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					tank.v_x = -tank_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					tank.v_x = tank_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_SPACE && 
						bullets.size() < bulletsLimit)
					bullets.add(new Bullet(tank.pos_x + (Tank.SIZE / 2) - 
							(Bullet.WIDTH / 2), tank.pos_y, 
							COURT_WIDTH, COURT_HEIGHT));
			}

			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT : tank.v_x = 0;
											tank.v_y = 0; break;
					case KeyEvent.VK_RIGHT : tank.v_x = 0;
											 tank.v_y = 0; break;
				}
			}
		});

		this.score = score;
		this.lives = lives;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		
		//initializing tank
		tank = new Tank(COURT_WIDTH, COURT_HEIGHT);
		
		//initializing aliens
		resetAliens();
		
		//initialize bullets
		bullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<EnemyBullet>();
		
		//initialize barriers
		resetBarriers();
		
		//initialize powerups
		powerups = new ArrayList<PowerUp>();
		
		//initialize bulletsLimit
		bulletsLimit = 1;
		
		//initialize score and powerUpScore
		gameScore = 0;
		powerUpScore = gameScore;
		
		//initialize firing rate
		RandomNumber.FIRE_RATE = 50;
		
		//initialize animation
		animationTick = 0;
		
		playing = false;
		lives.setText(Integer.toString(tank.lives));
		score.setText(Integer.toString(gameScore));

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	public void start() {
		playing = true;
	}
	
	/***********************************
	 * Method for Barriers
	 ***********************************/
	public void resetBarriers() {
		
		barriers = new ArrayList<ArrayList<BarrierPiece>>();
		b1 = new ArrayList<BarrierPiece>();
		b2 = new ArrayList<BarrierPiece>();
		b3 = new ArrayList<BarrierPiece>();
		b4 = new ArrayList<BarrierPiece>();
		
		createBarrier(37, 412, b1);
		createBarrier(162, 412, b2);
		createBarrier(287, 412, b3);
		createBarrier(412, 412, b4);
		
		// Add barriers to the list of all barriers
		barriers.add(b1);
		barriers.add(b2);
		barriers.add(b3);
		barriers.add(b4);

		
	}
	public void createBarrier(int pos_x, int pos_y, ArrayList<BarrierPiece> b) {
		int x = pos_x;
		int y = pos_y;
		
		// Add barrier pieces to each barrier in the specified position
		for (int i = 1; i < 9; i++) {
			if(i < 4) {
				b.add(new BarrierPiece(x, y, COURT_WIDTH, COURT_HEIGHT));
				if (i == 3) x +=13;
				else y -= 13;
			}
			
			if (i > 3 && i < 7) {
				b.add(new BarrierPiece(x, y, COURT_WIDTH, COURT_HEIGHT));
				if (i == 6) y += 13;
				else {
					x += 13;
				}
			}
			
			if (i > 6 && i < 9) {
				b.add(new BarrierPiece(x, y, COURT_WIDTH, COURT_HEIGHT));
				y += 13;
			}
		}
	}
	public void destroyBarriers() {
		
		// Barriers hit by enemy bullets
		outerloop:
		for (ArrayList<BarrierPiece> barrier : barriers) {
			for (BarrierPiece piece : barrier) {
				for (EnemyBullet bullet : enemyBullets) {
					if (bullet.intersects(piece)) {
						piece.lives--;
						enemyBullets.remove(bullet);
						if (piece.lives == 0) barrier.remove(piece);
						break outerloop;
					}
				}
			}
		}
		
		// Barriers hit by player bullets
		outerloop:
		for (ArrayList<BarrierPiece> barrier : barriers) {
			for (BarrierPiece piece : barrier) {
				for (Bullet bullet : bullets) {
					if (bullet.intersects(piece)) {
						piece.lives--;
						bullets.remove(bullet);
						if (piece.lives == 0) barrier.remove(piece);
						break outerloop;
					}
				}
			}
		}
	}

	/************************************
	 * Methods for dealing with aliens
	 ************************************/
	public void resetAliens() {
		int i = 125;
		aliens = new ArrayList<ArrayList<GameObj>>();
		one = new ArrayList<GameObj>();
		two = new ArrayList<GameObj>();
		three = new ArrayList<GameObj>();
		four = new ArrayList<GameObj>();
		five = new ArrayList<GameObj>();
		six = new ArrayList<GameObj>();
		seven = new ArrayList<GameObj>();
		eight = new ArrayList<GameObj>();
		nine = new ArrayList<GameObj>();
		ten = new ArrayList<GameObj>();
		eleven = new ArrayList<GameObj>();
		
		// Re-add all the alienlists
		aliens.add(one);
		aliens.add(two);
		aliens.add(three);
		aliens.add(four);
		aliens.add(five);
		aliens.add(six);
		aliens.add(seven);
		aliens.add(eight);
		aliens.add(nine);
		aliens.add(ten);
		aliens.add(eleven);
		
		//Re-add the all aliens
		for (ArrayList<GameObj> alienList : aliens) {
			alienList.add(new AlienA(i, COURT_WIDTH, COURT_HEIGHT));
			alienList.add(new AlienB(i, AlienA.INIT_Y + 30, COURT_WIDTH, 
					COURT_HEIGHT));
			alienList.add(new AlienB(i, AlienA.INIT_Y + 60, COURT_WIDTH, 
					COURT_HEIGHT));
			alienList.add(new AlienC(i, AlienA.INIT_Y + 90, COURT_WIDTH, 
					COURT_HEIGHT));
			alienList.add(new AlienC(i, AlienA.INIT_Y + 120, COURT_WIDTH, 
					COURT_HEIGHT));
			i += 30;
		}
	}
	public void moveAliens() {
		for (ArrayList<GameObj> alienList : aliens) {
			for (GameObj alien : alienList) {
				alien.move();
			}
		}
	}
	public void bounceAliens() {
		// Make the firstRow bounce off walls and move down
		ArrayList<GameObj> leftColumn = aliens.get(0);
		ArrayList<GameObj> rightColumn = aliens.get(aliens.size() - 1);
		
		GameObj leftAlien = leftColumn.get(leftColumn.size() - 1);
		GameObj rightAlien = rightColumn.get(rightColumn.size() - 1);
		
		// Bounce off the left
		if (leftAlien.checkWall()) {
			for (ArrayList<GameObj> alienList : aliens) {
				for (GameObj alien : alienList) {
					alien.bounce(Direction.LEFT);
				}
			}
		}
		
		//Bounce off the right
		if (rightAlien.checkWall()) {
			for (ArrayList<GameObj> alienList : aliens) {
				for (GameObj alien : alienList) {
					alien.bounce(Direction.RIGHT);
				}
			}
		}
	}
	public void killAliens() {
		outerloop:
		for (Bullet bullet : bullets){
			for (ArrayList<GameObj> alienList : aliens) {
				for (GameObj alien : alienList) {
					if (bullet.intersects(alien)) {
						if (alien instanceof AlienA) {
							gameScore += AlienA.SCORE;
							powerUpScore += AlienA.SCORE;
						}
						
						if (alien instanceof AlienB) {
							gameScore += AlienB.SCORE;
							powerUpScore += AlienB.SCORE;
						}
						
						if (alien instanceof AlienC) {
							gameScore += AlienC.SCORE;
							powerUpScore += AlienC.SCORE;
						}
						
						score.setText(Integer.toString(gameScore));
						
						bullets.remove(bullet);
						alienList.remove(alien);
						break outerloop;
					}
				}
			}
		}
	// If column is empty, remove it from the overall arrayList
	for (ArrayList<GameObj> alienList : aliens) {
		if (alienList.size() == 0) {
			aliens.remove(alienList);
			break;
		}
	}
	
	}
	public void alienFire() {
		int index = 0;
		// Generate a random index from 0 - 10
		if (RandomNumber.fire()) {
			index = RandomNumber.whichIndex();
			if (index > aliens.size() - 1) return;
		
			ArrayList<GameObj> fireColumn = aliens.get(index);
		
			if (fireColumn.size() == 0) return;
			else {
				GameObj alien = fireColumn.get(fireColumn.size() - 1);
				enemyBullets.add(new EnemyBullet(alien.pos_x + 
						(alien.width / 2) - (Bullet.WIDTH / 2), 
						alien.pos_y, COURT_WIDTH, COURT_HEIGHT));
			}
		}
	}

	/************************************
	 * Methods for dealing with powerups
	 ************************************/
	public void checkPowerUp() {
	// Add powerup if powerup score > 500
		if (powerUpScore >= 500) {
			powerUpScore = powerUpScore - 500 ;
			powerups.add(new PowerUp(COURT_WIDTH, COURT_HEIGHT));
			powerUpNumber = RandomNumber.powerUpNumber();
		}
	}
	public void killPowerUp() {
		// Delete powerUp if hit by bullet
		if (powerups.size() > 0) {
			outerloop:
				for (Bullet bullet : bullets) {
					for (PowerUp powerup : powerups) {
						if (bullet.intersects(powerup)) {
							powerups.remove(powerup);
							bullets.remove(bullet);
							if (powerUpNumber == 0) {
								bulletsLimit = 5;
								powerUpStatus = true;
							}
							if (powerUpNumber == 1) {
								tank.lives++;
								lives.setText(Integer.toString(tank.lives));
							}
							if (powerUpNumber == 2) {
								stopAliens = true;
								powerUpStatus = true;
							}
							break outerloop;
						}	
					}
				}
		}
	}
	public void powerUpTimer() {
		powerUpTick++;
		if (powerUpTick == 200) {
			if (powerUpNumber == 0) bulletsLimit = 1;
			if (powerUpNumber == 2) stopAliens = false;
			powerUpStatus = false;
			powerUpTick = 0;
		}
	}
	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// Reset condition - all aliens dead, increase fire rate
			if (aliens.size() == 0) {
				resetAliens();
				RandomNumber.FIRE_RATE += 100;
			}
			
			// Lives
			for (EnemyBullet bullet : enemyBullets) {
				if (bullet.intersects(tank)) {
					enemyBullets.remove(bullet);
					tank.lives--;
					lives.setText(Integer.toString(tank.lives));
					break;
				}
			}
						
			// Lose condition - 0 lives or aliens reach the bottom level
			if (tank.lives == 0) {
				playing = false;
				lives.setText("0             You Lose!");
			}
			
			for (ArrayList<GameObj> alienList : aliens) {
				for (GameObj alien : alienList) {
					if (alien.pos_y >= Tank.INIT_Y - Tank.SIZE) {
						playing = false;
						lives.setText("You Lose!");
					}
				}
			}
			
			// Move tank
			tank.move();
			
			// Move every alien
			if (!stopAliens) moveAliens();
			
			// Move PowerUp
			for (PowerUp powerup : powerups) {
				powerup.move();
			}
			
			//Move bullets
			for(Bullet bullet : bullets) {
				bullet.move();
			}
			
			// Move enemy bullets
			for (EnemyBullet bullet : enemyBullets) {
				bullet.move();
			}
			
			// Bounce aliens at walls
			bounceAliens();
			
			// Aliens fire at random
			if (!stopAliens) alienFire();
			
			// Remove the bullet if it goes out of bounds
			for (Bullet bullet : bullets) {
				if (bullet.checkWall()) {
					bullets.remove(bullet);
					break;
				}
			}
			
			// Remove powerup if it hits the wall
			for (PowerUp powerup : powerups) {
				if (powerup.checkWall()) {
					powerups.remove(powerup);
					break;
				}
			}
					
			// Delete alien if bullet hits for all aliens
			killAliens();
			
			// Check if powerup should appear
			checkPowerUp();
			
			// Method for killing powerup
			killPowerUp();
			
			// Method for dealing with duration of powerup
			if (powerUpStatus) powerUpTimer();
			
			// Destroy barriers
			destroyBarriers();
			
			// Change animation tick
			if (animationTick == 15) animationTick = 0;
			else animationTick++;
			
			// update the display
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw Tank
		if (tank.lives >= 3) tank.draw(g);
		if (tank.lives == 2) tank.draw2(g);
		if (tank.lives == 1) tank.draw3(g);
		
		
		// Draw Aliens
		for (ArrayList<GameObj> alienList : aliens) {
			for (GameObj alien : alienList) {
				if (animationTick < 9) alien.draw(g);
				else alien.draw2(g);
			}
		}
		
		// Draw barriers
		for (ArrayList<BarrierPiece> barrier : barriers) {
			for (BarrierPiece piece : barrier) {
				piece.draw(g);
			}
		}
		
		// Draw bullets
		if (bullets != null) {
			for (Bullet bullet : bullets) {
				bullet.draw(g);
			}
		}
		
		if (enemyBullets != null) {
			for (EnemyBullet bullet : enemyBullets){
				bullet.draw(g);
			}
		}
		
		// Draw powerups
		if (powerups != null) {
			if (powerUpNumber == 0) {
				for (PowerUp powerup : powerups) {
					powerup.draw(g);
				}
			}
			
			if (powerUpNumber == 1) {
				for (PowerUp powerup : powerups) {
					powerup.draw2(g);
				}
			}
			
			if (powerUpNumber == 2) {
				for (PowerUp powerup : powerups) {
					powerup.draw3(g);
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
