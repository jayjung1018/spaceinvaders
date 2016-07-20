
public class RandomNumber {

	public static int FIRE_RATE = 50;
	
	public static boolean fire() {
		double scale = Math.random();
		int random = (int) (scale * 1000);
		if (random <= FIRE_RATE) return true;
		else return false;
	}
	
	public static int whichIndex() {
		double scale = Math.random();
		int index = (int) (scale * 11);
		return index;
	}
	
	public static int powerUpNumber() {
		double scale = Math.random();
		int number = (int) (scale * 3);
		return number;
	}
}
