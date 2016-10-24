package game.board;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import boboy.color.CColor;

public class Cell extends Rectangle {

	private Mark mark;
	private Color color;
	
	private boolean inside;
	private boolean marked;
	
	private int row,col;
	
	public Cell(float x, float y, float width, float height, int row,int col) {
		super(x, y, width, height);
		
		
		mark = new Mark(minX, maxX, minY, maxY, 10);
		marked = false;
		color = new Color(CColor.normalWhite);
		
		this.row = row; this.col = col;
	}
	
	public void setMark(MarkType markType) {
		mark.setMark(markType);
	}
	
	public MarkType getMarkType() {
		return mark.getMark();
	}
	
	public void draw(Graphics g) {
		if (! marked && isInside()) {
			g.setColor(color);
			g.fill(this);
		} else {
			g.draw(this);
			drawMark(g);
		}
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	public void setMarkColor(Color color) {
		mark.setColor(color);
	}

	public boolean isInBounds(Point p) {
		if (p.getX()  <= minX || p.getX() >= maxX) {
			setInside(false);
			return false;
		}
		if (p.getY() <= minY || p.getY() >= maxY) {
			setInside(false);
			return false;
		}
		
		setInside(true);
		return true;
	}
	
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	
	public boolean isInside() {
		return inside;
	}
	

	private void drawMark(Graphics g) {
		mark.render(g);
	}
	
	public void putMark(MarkType type) {
		mark.setMark(type);
		setMarked(true);
	}
	
	public void setMarked(boolean b) {
		this.marked = b;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public void reset() {
		marked = false;
		mark.setMark(MarkType.NONE);
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public String toString() {
		return "Cell: (" + row + ", " + col +")";
	}
	
	
}
