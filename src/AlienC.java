import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * An Alien game object in the lowest rows - gives the least amount of points
 */
public class AlienC extends GameObj {
	public static final String img_file = "images/alienC.PNG";
	public static final String img_file2 = "images/alienC2.PNG";

	public static final int SIZE = 30;
	public static final int INIT_VEL_X = 1;
	public static final int INIT_VEL_Y = 0;
	public static final int SCORE = 10;
	
	private static BufferedImage img;
	private static BufferedImage img2;

	public AlienC(int pos_x, int pos_y, int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, pos_x, pos_y, SIZE, SIZE,
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
		g.drawImage(img2, pos_x, pos_y, width, height, null);
	}

}