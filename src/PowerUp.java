import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PowerUp extends GameObj{
	public static final String img_file = "images/powerup1.PNG";
	public static final String img_file2 = "images/powerup2.PNG";
	public static final String img_file3 = "images/powerup3.PNG";
	
	public static final int WIDTH = 60;
	public static final int HEIGHT = 40;
	public static final int INIT_X = 0;
	public static final int INIT_Y = 0;
	public static final int INIT_VEL_X = 2;
	public static final int INIT_VEL_Y = 0;
	
	private static BufferedImage img;
	private static BufferedImage img2;
	private static BufferedImage img3;
	
	public PowerUp(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, WIDTH, HEIGHT, 
				courtWidth, courtHeight);
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
