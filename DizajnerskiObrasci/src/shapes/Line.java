package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point startPoint;
	private Point endPoint;
	
	
	public Line() {

	}

	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public Line(Point startPoint, Point endPoint, Color edgeColor) {
		this(startPoint,endPoint);
		this.setEdgeColor(edgeColor);
}

	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		this.setSelected(selected);
		
	}

	public Line(Point startPoint, Point endPoint, boolean selected, Color edgeColor) {
		this(startPoint, endPoint, selected);
		this.setEdgeColor(edgeColor);
	}

	public double length() {
		return this.startPoint.distance(this.endPoint.getX(), this.endPoint.getY());
		
	}

	public boolean equals(Object obj) {
	 if (obj instanceof Line)
	 {
		 Line temporary= (Line) obj;
		 if(this.startPoint.equals(temporary.startPoint)&& this.endPoint.equals(temporary.endPoint)) 
			
		    return true;
		 else
			 return false;	 
	 } 
	 else 
		 return false;
	}
	
	public boolean contains (int  x, int y) {
		return (this.startPoint.distance(x, y) +this.endPoint.distance(x, y))-this.length() <=2;
	}
	
	

	@Override
	public void draw(Graphics g) {
		g.setColor(getEdgeColor());
        g.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(),endPoint.getY());
        
        if (selected == true) {
			g.setColor(Color.BLUE);
			g.drawRect(startPoint.getX()-2, startPoint.getY()-2,4,4);	
			g.drawRect(endPoint.getX()-2, endPoint.getY()-2,4,4);	
		}
	}
	

	
	@Override
	public void moveTo(int x, int y) {
	
		//ne radi nista	
	}

	@Override
	public void moveBy(int x, int y) {
		startPoint.moveBy(x,y);
		endPoint.moveBy(x,y);		
	}
	

	@Override
	public int compareTo(Object o) {
		if(o instanceof Line) {
			return (int)(this.length()-((Line)o).length());
		}
		return 0;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getStartPoint() {
		return this.startPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public Point getEndPoint() {
		return this.endPoint;
	}
	
	
	
	public String toString() {
		return "Line; " + ("("+startPoint.getX()+", "+startPoint.getY()+")") + "-->" + ("("+endPoint.getX()+", "+endPoint.getY()+");") + " Color: " + getEdgeColor().toString();
	}
	


	@Override
	public Line clone() {
		Point startPoint = new Point(this.getStartPoint().getX(), this.getStartPoint().getY());
		Point endPoint = new Point(this.getEndPoint().getX(), this.getEndPoint().getY());
		
		Line line = new Line(startPoint, endPoint, getEdgeColor());
		
		return line;
	}

	
	

}
