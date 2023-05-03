package code;

public class Util {
	
	public static int random(int MIN, int MAX) {
		return (int) (Math.random() * (MAX - MIN + 1)) + MIN;
	}
	
}
