package game;

public class Settings {
	
	public static final int _3x3 = 3;
	public static final int _4x4to6x6 = 4;
	public static final int _7x7 = 5;
	public static final int _8x8to10x10 = 6;
	
	private static int GRID_SIZE = 3;
	private static int STREAK = 3;
	
	public static void setGridSize(int n) {
		if (n < 0) {
			GRID_SIZE = 3;
			STREAK = 3;
		} else if (n > 10) {
			GRID_SIZE = 10;
			STREAK = 6;
		} else {
			GRID_SIZE = n;
		}
		
		if (GRID_SIZE == 3) {
			STREAK = 3;
		} else if (GRID_SIZE > 3 && GRID_SIZE < 7){
			STREAK = 4;
		} else if (GRID_SIZE == 7) {
			STREAK = 5;
		} else if (GRID_SIZE > 7 && GRID_SIZE <= 10) {
			STREAK = 6;
		}
	}
	
	public static int getGridSize() {
		return GRID_SIZE;
	}
	
	public static int getStreak() {
		return STREAK;
	}
	
}
