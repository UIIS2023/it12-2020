package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends ShapeWithInnerColor implements Cloneable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Point center;
	protected int radius;

	

	public Circle() {

	}
	public Circle(Point center) {
		this.center = center;
	}
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	public Circle(Point center, int radius,Color edgeColor) {
		this.center = center;
		this.radius = radius;
		this.setEdgeColor(edgeColor); 
	}

	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		this.setSelected(selected);	
	}
	
	public Circle(Point center, int radius, boolean selected, Color edgeColor) {
		this(center, radius, selected);
		this.setEdgeColor(edgeColor);
	}
	
	public Circle(Point center, int radius,  Color edgeColor, Color innerColor) {
		this(center, radius, edgeColor);
		this.setInnerColor(innerColor);
	}
	public Circle(Point center, int radius, boolean selected, Color edgeColor, Color innerColor) {
		this(center, radius, selected, edgeColor);
		this.setInnerColor(innerColor);
	}

	public double area() {
		return radius * radius * Math.PI;
	}

	public double circumference() {
		return 2 * radius * Math.PI;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle temporary = (Circle) obj;
			if (this.center.equals(temporary.center) && this.radius == temporary.radius)
				return true;
			else
				return false;

		} 
		else
			return false;
	}

	public boolean contains(int x, int y) {
		return center.distance(x, y) <= radius;
	}

	public boolean contains(Point p) {
		return center.distance(p.getX(), p.getY()) <= radius;
	}

	@Override
	public void draw(Graphics g) {
		  g.setColor(getEdgeColor());
	      g.drawOval(center.getX()-radius, center.getY()-radius, 2*radius, 2*radius);	
	     this.fill(g);
	      if(selected == true) {
				g.setColor(Color.BLUE);
				g.drawRect(center.getX() - 2, center.getY() - 2, 4, 4);
				g.drawRect(center.getX() - radius - 2, center.getY() - 2, 4, 4);
				g.drawRect(center.getX() + radius - 2, center.getY() - 2, 4, 4);
				g.drawRect(center.getX() - 2, center.getY() - radius - 2, 4, 4);
				g.drawRect(center.getX() - 2, center.getY() + radius - 2, 4, 4);
			}
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
	   g.fillOval(this.center.getX() - this.radius + 1, this.center.getY() - this.radius + 1,
				this.radius*2 - 2, this.radius*2 - 2);
		
	}


	@Override
	public int compareTo(Object o) {
		if(o instanceof Circle) {
			return (int)(this.area()-((Circle)o).area());	
		}
		return 0;
	}
	

	@Override
	public void moveTo(int x, int y) {
	   center.moveTo(x, y);	
	}

	@Override
	public void moveBy(int x, int y) {
		   center.moveBy(x, y);	
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return this.radius;
	}

	public void setRadius(int radius) throws Exception {
		if (radius > 0) {
			this.radius = radius;
		} else {
			throw new NumberFormatException("Radius has to be a value greater than 0!");
		}
	}

	

	public String toString() {
		
		return "Circle; " + "center: " + "(" +center.getX() + ", " + center.getY() + ")" + ", " + "radius: " + radius 
				+ " ; edge color: " + getEdgeColor().toString() + " ; inner color: " + getInnerColor().toString();
		
	}
	
	
	

	
	@Override
	public Circle clone() {
		Point point = new Point(this.getCenter().getX(), this.getCenter().getY());
		Circle circle = new Circle(point, getRadius(), this.getEdgeColor(), this.getInnerColor());
		
		return circle;
	}
	
	
	
	
}  
