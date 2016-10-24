package game.board.drawer;

import game.board.Cell;


public class ToDraw {
	private Cell start;
	private Cell finish;
	private DrawType drawType;
	
	public ToDraw(Cell start, Cell finish, DrawType drawType) {
		this.start = start;
		this.finish = finish;
		this.drawType = drawType;
	}
	
	public Cell getStart() {
		return start;
	}
	
	public Cell getFinish() {
		return finish;
	}
	
	public DrawType getDrawType() {
		return drawType;
	}
}
