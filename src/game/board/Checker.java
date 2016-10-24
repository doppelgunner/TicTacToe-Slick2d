package game.board;


public class Checker {
	
	/**
	 * 1st dimension -> line
	 * 2nd dimension -> each box
	 * 3rd dimension -> each vertex
	 * for traditional tic tac toe 
	 * checking 3x3 - I want more dynamic 
	 * so that the checking can
	 * expand when the grid expands
	 */
	public static final int[][][] CHECKER = {
			{
				{-1,-1}, { 1, 1} // left diagonal
			}, {
				{ 1,-1}, { -1, 1} //right diagonal
			}, {
				{-1, 0}, { 1, 0} // horizontal
			}, {
				{0 ,-1}, { 0, 1} // vertical
			}
	};

	
}
