package game.board;


import game.Game;
import game.board.drawer.DrawType;
import game.board.drawer.Drawer;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;

import boboy.color.CColor;
import boboy.game.gui.GUIRectangle;

public class Grid {

	private float x, y;
	private int width, height;
	private int checks;
	private int gridSize;
	private float cellSize;
	
	private int rows, cols;
	private Cell[][] cells;
	
	private ArrayList<Cell> placedCells;
	
	private boolean isX;
	private boolean winner;
	
	private String winnerText;
	private int xScore;
	private int oScore;
	
	public Grid (float x, float y, int width, int height, int gridSize, int checks) {
		this.x = x; this.y = y;
		this.width = width; this.height = height;
		this.checks = checks;
		this.gridSize = gridSize;
		
		this.cellSize = width / gridSize;
		
		cells = new Cell[gridSize][gridSize];
		for (int row = 0; row < gridSize; row++) {
			for (int col = 0; col < gridSize; col++) {
				cells[row][col] = new Cell((col * cellSize) + x, (row * cellSize) + y, cellSize, cellSize, row, col);
			}
		}
		
		isX = true;
		winner = false;
		xScore = 0;
		oScore = 0;
		
		placedCells = new ArrayList<Cell>();
		winnerText = "";
	}
	
	//init outside
	public void init(Graphics g) {
		
	}
	
	public void render(Graphics g) {
		//draw cells
		for (int row = 0; row < cells.length; row ++) {
			for (int col = 0; col < cells[row].length; col++) {
				cells[row][col].draw(g);
			}
		}
	}
	
	public void update(GameContainer container) {
		Input input = container.getInput();
		Point mouse = new Point(input.getMouseX(), input.getMouseY());
		
		//update cells for check events
		for (int row = 0; row < cells.length; row ++) {
			for (int col = 0; col < cells[row].length; col++) {
				Cell cell = cells[row][col];
				
				if (cell.isInBounds(mouse)) {
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						if (!winner  && !cell.isMarked()) {
							//sound.play(0.4f, 0.1f);
							
							if (isX) {
								cell.putMark(MarkType.X);
							}
							else {
								cell.putMark(MarkType.O);
							}
							
							isX = !isX;
							
								
							
							if (checkGrid(cell.getMarkType())) {
								winner = true;
								switch (cell.getMarkType()) {
									case X: xScore++;
										winnerText = "X WON";
										break;
									case O: oScore++;
										winnerText = "O WON";
										break;
									default:
											break;
								}
							}
							 
							
							placedCells.add(cell);
							Game.playPlaceSound();
						}
					}
				}
				
			}
		}
		
		//check if there are more available cells
		if (placedCells.size() == getNumberOfCells() && winner != true) {
			winner = true;
			winnerText = "TIE!";
		}
		
	}
	
	public int getNumberOfCells() {
		return gridSize * gridSize;
	}
	
	public int getChecks() {
		return checks;
	}
	
	public int getSize() {
		return gridSize;
	}
	
	public void reset() {
		for (int row = 0; row < cells.length; row ++) {
			for (int col = 0; col < cells[row].length; col++) {
				cells[row][col].reset();
			}
		}
		placedCells.clear();
		
		isX = true;
		winner = false;
		Drawer.clearAll();
	}
	
	public boolean someoneWon() {
		return winner;
	}
	
	public void updateScoreBoard(GUIRectangle scoreBoard) {
		String text = String.format("X:%02d | 0:%02d", xScore, oScore);
		scoreBoard.setText(text);
	}
	
	public String getWinnerTotal() {
		if (xScore == oScore) return "TIE!";
		else if (xScore > oScore) return "X WON";
		else if (xScore < oScore) return "O WON";
		
		return null;
	}
	
	public String getWinner() {
		return winnerText;
	}
	
	private boolean checkGrid(MarkType markType) {
		
		for (int row = 0; row < cells.length; row ++) {
			for (int col = 0; col < cells[row].length; col++) {
				Cell cell = cells[row][col];
				if (cell.getMarkType() == markType) {
					if (checkCell(cell)) return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean checkCell(Cell cell) {
		
		//check if it streaks on horizontal, vertical, diagLeft and diagRight
		if (checkCellHor(cell) || checkCellVer(cell) 
				|| checkCellDiagLeft(cell) || checkCellDiagRight(cell)) return true;
		
		return false;
	}
	
	private boolean checkCellHor(Cell cell) {
		int row = cell.getRow();
		int col = cell.getCol();
		
		Cell start = cell;
		Cell finish = getCellAt(row, col + checks - 1);
		
		for (int hor = 1; hor < checks; hor++) {
			int checkCol = col + hor;
			Cell checkCell = getCellAt(row, checkCol);
			if (checkCell != null && checkCell.isMarked()) {
				if (checkCell.getMarkType() != cell.getMarkType()) return false;
			} else return false;
		}
		
		if (start != null && finish != null) {
			Drawer.drawLater(start, finish, DrawType.LINE);
		}
		
		return true;
	}
	
	private boolean checkCellVer(Cell cell) {
		int row = cell.getRow();
		int col = cell.getCol();
		
		Cell start = cell;
		Cell finish = getCellAt(row + checks - 1, col);
		
		for (int ver = 1; ver < checks; ver++) {
			int checkRow = row + ver;
			Cell checkCell = getCellAt(checkRow, col);
			if (checkCell != null && checkCell.isMarked()) {
				if (checkCell.getMarkType() != cell.getMarkType()) return false;
			} else return false;
		}
		
		if (start != null && finish != null) {
			Drawer.drawLater(start, finish, DrawType.LINE);
		}
		
		return true;
	}
	
	private boolean checkCellDiagLeft(Cell cell) {
		int row = cell.getRow();
		int col = cell.getCol();
		
		Cell start = cell;
		Cell finish = getCellAt(row + checks - 1, col + checks - 1);
		
		for (int ver = 1, hor = 1; ver < checks && hor < checks; ver++, hor++) {
			int checkRow = row + ver;
			int checkCol = col + hor;
			Cell checkCell = getCellAt(checkRow, checkCol);
			if (checkCell != null && checkCell.isMarked()) {
				if (checkCell.getMarkType() != cell.getMarkType()) return false;
			} else return false;
		}
		
		if (start != null && finish != null) {
			Drawer.drawLater(start, finish, DrawType.LINE);
		}
		
		return true;
	}
	
	private boolean checkCellDiagRight(Cell cell) {
		int row = cell.getRow();
		int col = cell.getCol();
		
		Cell start = cell;
		Cell finish = getCellAt((row + checks - 1),(col - (checks - 1)));
		
		for (int ver = 1, hor = 1; ver < checks && hor < checks; ver++, hor++) {
			int checkRow = row + ver;
			int checkCol = col - hor;
			Cell checkCell = getCellAt(checkRow, checkCol);
			if (checkCell != null && checkCell.isMarked()) {
				if (checkCell.getMarkType() != cell.getMarkType()) return false;
			} else return false;
			
		}
		
		if (start != null && finish != null) {
			Drawer.drawLater(start, finish, DrawType.LINE);
		}
			
		return true;
	}
	
	public Cell getCellAt(int row, int col) {
		if (row >= gridSize || col >= gridSize || row < 0 || col < 0) return null;
		
		return cells[row][col];
	}

}
