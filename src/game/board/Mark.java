package game.board;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;


public class Mark {
	private Color color = Color.white;
	private MarkType mark;
	private ArrayList<Shape> shapesToDraw;
	
	private float minX, maxX;
	private float minY, maxY;
	private float offset;
	
	public Mark(float minX, float maxX, float minY, float maxY, float offset) {
		shapesToDraw = new ArrayList<Shape>();
		this.minX = minX; this.maxX = maxX;
		this.minY = minY; this.maxY = maxY;
		this.offset = offset;
		
		setMark(MarkType.NONE);
	}
	
	public void setMark(MarkType mark) {
		this.mark = mark;
		
		switch (mark) {
		case X:
			Line line1 = new Line(minX + offset, minY + offset, maxX - offset, maxY - offset);
			Line line2 = new Line(minX + offset, maxY - offset, maxX - offset, minY + offset);
			shapesToDraw.clear();
			shapesToDraw.add(line1);
			shapesToDraw.add(line2);
			break;
		case O:
			float centerX = (minX + maxX) / 2;
			float centerY = (minY + maxY) / 2;
			float radius;
			if ((maxX - minX) <= (maxY - minY)) {
				radius = ((maxX - minX) / 2) - offset;
			} else {
				radius = ((maxY - minY) / 2) - offset;
			}
			
			Circle circle = new Circle(centerX, centerY, radius);
			shapesToDraw.clear();
			shapesToDraw.add(circle);
			break;
		case NONE:
			shapesToDraw.clear();
			break;
		default:
			break;
		}
	}
	
	public MarkType getMark() {
		return mark;
	}
	
	public void init() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		for (Shape s : shapesToDraw) {
			g.draw(s);
		}
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
