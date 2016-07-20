/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A basic game object displayed as a black square, starting in the upper left
 * corner of the game court.
 * 
 */
public class Tank extends GameObj {
	public static final String img_file = "images/ship.PNG";
	public static final String img_file2 = "images/ship2.PNG";
	public static final String img_file3 = "images/ship3.PNG";
	
	public static int SIZE = 30;
	public static final int INIT_X = 250;
	public static final int INIT_Y = 450;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public int lives = 3;
	
	private static BufferedImage img;
	private static BufferedImage img2;
	private static BufferedImage img3;
	/**
	 * Note that, because we don't need to do anything special when constructing
	 * a Square, we simply use the superclass constructor called with the
	 * correct parameters
	 */
	public Tank(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
				courtHeight);
		try {
			if (img == null || img2 == null || img3 == null) {
				img = ImageIO.read(new File(img_file));
				img2 = ImageIO.read(new File(img_file2));
				img3 = ImageIO.read(new File(img_file3));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null);
	}
	
	public void draw2(Graphics g) {
		g.drawImage(img2, pos_x, pos_y, width,height, null);
	}
	
	public void draw3(Graphics g) {
		g.drawImage(img3, pos_x, pos_y, width,height, null);
	}
}
