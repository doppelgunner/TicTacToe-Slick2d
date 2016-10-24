package game.board.drawer;

import game.board.Cell;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import boboy.color.CColor;

public class Drawer {
	
	private static Color color = CColor.alphaDarkBlack;
	private static float lineWidth = 15f;
	
	private static ArrayList<ToDraw> toDrawLater = new ArrayList<>();
	
	public static void setColor(Color c) {
		color = c;
	}
	
	public static void setLineWidth(float lw) {
		lineWidth = lw;
	}
	
	public static Color getColor() {
		return color;
	}
	
	public static float getLineWidth() {
		return lineWidth;
	}
	
	public static void draw(Graphics g) {
		for (int i = 0; i < toDrawLater.size(); i++) {
			ToDraw toDraw = toDrawLater.get(i);
			draw(g, toDraw);
		}
	}
	
	private static void draw(Graphics g, ToDraw toDraw) {
		float origLineWidth = g.getLineWidth();
		Color origColor = g.getColor();
		
		g.setColor(color);
		g.setLineWidth(lineWidth);
		
		switch (toDraw.getDrawType()) {
		case RECT_HORIZONTAL:
			drawDiagLeft(g, toDraw.getStart(), toDraw.getFinish());
			break;
			
		case RECT_VERTICAL:
			drawVertical(g, toDraw.getStart(), toDraw.getFinish());
			break;
			
		case RECT_DIAG_RIGHT:
			drawDiagRight(g, toDraw.getStart(), toDraw.getFinish());
			break;
			
		case RECT_DIAG_LEFT:
			drawHorizontal(g, toDraw.getStart(), toDraw.getFinish());
			break;
		case LINE:
			drawLine(g, toDraw.getStart(), toDraw.getFinish());
			break;
		default:	
		}
		
		g.setColor(origColor);
		g.setLineWidth(origLineWidth);
	}
	
	public static void drawLater(Cell start, Cell finish, DrawType drawType) {
		toDrawLater.add(new ToDraw(start, finish, drawType));
	}
	
	private static void drawLine(Graphics g, Cell start, Cell finish) {
		g.drawLine(start.getCenterX(), start.getCenterY(), finish.getCenterX(), finish.getCenterY());
	}
	
	private static void drawDiagRight(Graphics g, Cell start, Cell finish) {
		g.drawLine(start.getMinX(), start.getMinY(), start.getMaxX(), start.getMaxY()); 
		g.drawLine(start.getMinX(), start.getMinY(), finish.getMinX(), finish.getMinY());
		g.drawLine(start.getMaxX(), start.getMaxY(), finish.getMaxX(), finish.getMaxY());
		g.drawLine(finish.getMinX(), finish.getMinY(), finish.getMaxX(), finish.getMaxY()); 
	}
	
	private static void drawDiagLeft(Graphics g, Cell start, Cell finish) {
		g.drawLine(start.getMinX(), start.getMaxY(), start.getMaxX(), start.getMinY());
		g.drawLine(start.getMinX(), start.getMaxY(), finish.getMinX(), finish.getMaxY());
		g.drawLine(start.getMaxX(), start.getMinY(), finish.getMaxX(), finish.getMinY());
		g.drawLine(finish.getMinX(), finish.getMaxY(), finish.getMaxX(), finish.getMinY());
	}
	
	private static void drawHorizontal(Graphics g, Cell start, Cell finish) {
		g.drawLine(start.getMinX(), start.getMinY(), start.getMinX(), start.getMaxY());
		g.drawLine(start.getMinX(), start.getMinY(), finish.getMaxX(), finish.getMinY());
		g.drawLine(start.getMinX(), start.getMaxY(), finish.getMaxX(), finish.getMaxY());
		g.drawLine(finish.getMaxX(), finish.getMinY(), finish.getMaxX(), start.getMaxY());
	}
	
	private static void drawVertical(Graphics g, Cell start, Cell finish) {
		g.drawLine(start.getMinX(), start.getMinY(), start.getMaxX(), start.getMinY());
		g.drawLine(start.getMinX(), start.getMinY(), finish.getMinX(), finish.getMaxY());
		g.drawLine(start.getMaxX(), start.getMinY(), finish.getMaxX(), finish.getMaxY());
		g.drawLine(finish.getMinX(), finish.getMaxY(), finish.getMaxX(), finish.getMaxY());
	}
	
	public static void clearAll() {
		toDrawLater.clear();
	}
}

