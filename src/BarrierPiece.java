import java.awt.Color;
import java.awt.Graphics;


public class BarrierPiece extends GameObj {
	
	public static final int SIZE = 13;
	public static final int VEL_X = 0;
	public static final int VEL_Y = 0;
	public int lives = 4;
	
	public BarrierPiece(int pos_x, int pos_y, int courtWidth, 
			int courtHeight) {
		super(VEL_X, VEL_Y, pos_x, pos_y, SIZE, SIZE, courtWidth, courtHeight);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(pos_x, pos_y, width, height);
	}

}
