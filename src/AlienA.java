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
 * An Alien game object in the topmost row - gives the most points
 */
public class AlienA extends GameObj {
	public static final String img_file = "images/alienA.PNG";
	public static final String img_file2 = "images/alienA2.PNG";
	public static final int SIZE = 30;
	public static final int INIT_Y = 25;
	public static final int INIT_VEL_X = 1;
	public static final int INIT_VEL_Y = 0;
	public static final int SCORE = 50;
	
	private static BufferedImage img;
	private static BufferedImage img2;

	public AlienA(int pos_x, int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, pos_x, INIT_Y, SIZE, SIZE,
				courtWidth, courtHeight);
		
		try {
			if (img == null || img2 == null) {
				img = ImageIO.read(new File(img_file));
				img2 = ImageIO.read(new File(img_file2));
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

}