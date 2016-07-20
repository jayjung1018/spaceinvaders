import java.awt.Color;
import java.awt.Graphics;


public class Bullet extends GameObj{
	
	public static final int VEL_X = 0;
	public static final int VEL_Y = -15;
	public static final int WIDTH = 5;
	public static final int HEIGHT = 10;
	
	// Initial pos depends on where the tank is
	public Bullet (int pos_x, int pos_y, int courtWidth, int courtHeight) {
		super(VEL_X, VEL_Y, pos_x, pos_y, WIDTH, HEIGHT, courtWidth, 
				courtHeight);
	}
	
	// Simple rectangle
	@Override
	public void draw (Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(pos_x, pos_y, WIDTH, HEIGHT);
	}
	
	@Override
	public void move() {
		pos_x += v_x;	
		pos_y += v_y;
	}

}
