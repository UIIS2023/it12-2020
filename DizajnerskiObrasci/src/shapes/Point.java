package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape implements Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	


	public Point() {

	} 

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Point(int x, int y,Color edgeColor) {
		this(x,y);
		this.setEdgeColor(edgeColor);
	}

	public Point(int x, int y, boolean selected) {
		this(x, y);
		this.setSelected(selected);
	}
	
	public Point(int x, int y, boolean selected, Color edgeColor) {
		this(x, y, selected);
		this.setEdgeColor(edgeColor);
	}

	public double distance(int x2, int y2) { 
		double dx = this.x - x2; 
									
		double dy = this.y - y2;
		double d = Math.sqrt(dx * dx + dy * dy);
		return d;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point temporary = (Point) obj;
			if (this.x == temporary.x && this.y == temporary.y)
				return true;
			else
				return false;
		} 
		else
			return false;
	}

	public boolean contains(int x, int y) {
		
		return this.distance(x, y) <= 2;
	}
	

	@Override
	public void draw(Graphics g) {
		g.setColor(getEdgeColor());
		g.drawLine(x-2, y, x+2, y);
		g.drawLine(x, y-2, x, y+2);	
		
		if (selected == true) {
			g.setColor(Color.BLUE);
			g.drawRect(x-2,y-2,4,4);	
		}

	}
	
	

	@Override
	public void moveTo(int x, int y) {
		this.x=x;
		this.y=y;
	}

	@Override
	public void moveBy(int x, int y) {
		this.x+=x;
	    this.y+=y;
	}
	

	@Override
	public int compareTo(Object o) {
		if(o instanceof Point) {
			return (int)( this.distance(0, 0)-((Point)o).distance(0,0));
		
		}
		return 0;
		
	}

	
	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return this.x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return this.y;
	}
	
	

	

	public String toString() {
								
		return "Point; (" + x + "," + y + ")" + "; Color: " + getEdgeColor().toString();
	}
	


	@Override
	public Point clone() {
		Point point = new Point();
		point.setX(this.getX());
		point.setY(this.getY());
		point.setEdgeColor(getEdgeColor());
		
		return point;
	}
	
	
	
	
	
	

}
