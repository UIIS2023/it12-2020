package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends ShapeWithInnerColor implements Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point upperLeftPoint;
	private int width;
	private int height;
	
	
	
	public Rectangle() {

	}
	public Rectangle (Point upperLeftPoint) {
		this.upperLeftPoint=upperLeftPoint;
	}
	

	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color edgeColor) {
		this(upperLeftPoint, width, height, selected);
		setEdgeColor(edgeColor);
	}
	public Rectangle(Point upperLeftPoint, int width, int height, Color edgeColor) {
		this(upperLeftPoint, width, height);
		this.setEdgeColor(edgeColor);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, Color edgeColor, Color innerColor) {
		this(upperLeftPoint,width,height,edgeColor);
		this.setInnerColor(innerColor);
	}
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color edgeColor, Color innerColor) {
		this(upperLeftPoint, width, height, selected, edgeColor);
		setInnerColor(innerColor);
	}

	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected) {
		this(upperLeftPoint, width, height);
		setSelected(selected);
	
	}
	
 
	public int area() {
		return this.width * this.height;
	}

	public int circumference() {
		return this.width *2 +this.height * 2;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Rectangle)
		{
			Rectangle temporary = (Rectangle) obj;
			if(this.upperLeftPoint.equals(temporary.upperLeftPoint)&& this.width==temporary.width &&this.height==temporary.height)
				return true;
			else 
				return false;
		}else 
			return false;
	}
	public boolean contains (int x, int y) {
		return (upperLeftPoint.getX()<=x && x<= upperLeftPoint.getX()+width && upperLeftPoint.getY()<=y && y<=upperLeftPoint.getY()+height);
			
		
	}
	public boolean contains (Point p) {
		if(upperLeftPoint.getX()<=p.getX() && p.getX()<= upperLeftPoint.getX()+width && upperLeftPoint.getY()<=p.getY() && p.getY()<=upperLeftPoint.getY()+height)
			return true;
		return false;
	}
	
	

	@Override
	public void draw(Graphics g) {
		g.setColor(getEdgeColor());
		g.drawRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);
		this.fill(g);
		if (selected) {
			g.setColor(Color.blue);
			g.drawRect(upperLeftPoint.getX() - 2, upperLeftPoint.getY() - 2, 4, 4);
			g.drawRect(upperLeftPoint.getX() + width - 2, upperLeftPoint.getY() - 2, 4, 4);
			g.drawRect(upperLeftPoint.getX() - 2, upperLeftPoint.getY() + height - 2, 4, 4);
			g.drawRect(upperLeftPoint.getX() + width  - 2, upperLeftPoint.getY() + height - 2, 4, 4);
		}
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillRect(this.upperLeftPoint.getX() + 1, this.upperLeftPoint.getY() + 1, this.width-1, this.height-1);
		
	}
		
    
	@Override
	public void moveTo(int x, int y) {
		upperLeftPoint.moveTo(x, y);
	
	}

	@Override
	public void moveBy(int x, int y) {
		upperLeftPoint.moveBy(x, y);
		
	}
	

	@Override
	public int compareTo(Object o) {
		if(o instanceof Rectangle) {
			return (this.area()-((Rectangle)o).area());
		}
		return 0;
	}
	
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	

	
	
	 public String toString() {
		 return "Rectangle; " + "Upper left point: " + "(" + upperLeftPoint.getX() + ", " + upperLeftPoint.getY() + ")"+ ", "
	 + "width: " + width + ", " + "height: " + height + " ; edge color: " + getEdgeColor().toString() + " ; inner color: " + getInnerColor().toString();
	 }
	 

	 
	@Override
	public Rectangle clone() {
		Point upperLeftPoint = new Point(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY());
		Rectangle rectangle = new Rectangle(upperLeftPoint, getHeight(), getWidth(), getEdgeColor(), getInnerColor());
		
		return rectangle;
	}
	 
	 

	

}
