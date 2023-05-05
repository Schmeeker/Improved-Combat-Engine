package code;

public class Util {
	
	public static int random(int MIN, int MAX) {
		return (int) (Math.random() * (MAX - MIN + 1)) + MIN;
	}
	
	public static int dice(int number, int sides) {
		int value = 0;
		for(int i = 1; i <= number; i++) {
			value += (int) (Math.random() * (sides)) + 1;
		}
		return value;
	}
	
}
