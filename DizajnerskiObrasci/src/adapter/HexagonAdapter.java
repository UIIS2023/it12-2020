package adapter;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;
import shapes.Point;
import shapes.ShapeWithInnerColor;

public class HexagonAdapter extends ShapeWithInnerColor implements Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hexagon hexagon;

	public HexagonAdapter () {
		
	}
	
	public HexagonAdapter(int x, int y, int radius, Color innerColor, Color edgeColor) {
		this.hexagon = new Hexagon(x, y, radius);
		this.setEdgeColor(edgeColor);
		this.setInnerColor(innerColor);
	}
	
	public HexagonAdapter(Point p, int radius) {
		hexagon = new Hexagon(p.getX(),  p.getY(), radius);
	}
	
	public HexagonAdapter(Point p, int radius, Color color) {
		this(p, radius);
		this.setEdgeColor(color);
	}
	
	public HexagonAdapter(Point p, int radius, Color color, Color innerColor)
	{
		this(p, radius, color);
		this.setInnerColor(innerColor);
	}
	
	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBy(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
		
	}

	@Override
	public void draw(Graphics g) {
		this.hexagon.setBorderColor(getEdgeColor());
	    this.hexagon.setAreaColor(getInnerColor());
		//this.hexagon.paint(g);
		
		hexagon.paint(g);
		if(selected) {
			g.setColor(Color.BLUE);
			g.drawRect(getX() - 2, getY() - 2, 4, 4);
			g.drawRect(getX() - getRadius() - 2, getY() - 2, 4, 4);
			g.drawRect(getX() + getRadius() - 2, getY() - 2, 4, 4);
			
			g.drawRect((int)(getX() - getRadius() * Math.cos(Math.PI/3)*1.1), (int)(getY() - getRadius() * Math.cos(Math.PI/3)*1.78), 4, 4);
			g.drawRect((int)(getX() + getRadius() * Math.cos(Math.PI/3)), (int)(getY() - getRadius() * Math.cos(Math.PI/3)*1.78), 4, 4);
			
			g.drawRect((int)(getX() - getRadius() * Math.cos(Math.PI/3)*1.05), (int)(getY() + getRadius() * Math.cos(Math.PI/3)*1.70), 4, 4);
			g.drawRect((int)(getX() + getRadius() * Math.cos(Math.PI/3)), (int)(getY() + getRadius() * Math.cos(Math.PI/3)*1.70), 4, 4);
			
		}
	}
	
	

	public int getX() {
		return hexagon.getX();
	}
	
	public int getY() {
		return hexagon.getY();
	}

	public int getRadius() {
		return hexagon.getR();
	}
	
	public void setX(int x) {
		this.hexagon.setX(x);
	}
	
	public void setY(int y) {
		this.hexagon.setY(y);
	}
	
	public void setRadius(int radius) {
		this.hexagon.setR(radius);
	}
	
	public Color getColor() {
		return hexagon.getBorderColor();
	}
	
	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return "Hexagon; " + "center: (" + getX() + ", " + getY() + "), " + "radius: " + getRadius() + " ; edge color: "
				+ getEdgeColor().toString() + " ; inner color: " + getInnerColor().toString();
	}
	
	@Override
	public HexagonAdapter clone() {
		Point point = new Point(this.getX(), this.getY());
		
		HexagonAdapter hexagon = new HexagonAdapter(point, getRadius(), this.getEdgeColor(), this.getInnerColor());
		
		return hexagon;
	}

	public Hexagon getHexagon() {
		return hexagon;
	}
	
	public boolean equals(HexagonAdapter adapter) {
        if (this == adapter) {
            return true;
        }
        
        if(this.getX() == adapter.getX() && 
           this.getY() == adapter.getY() && 
           this.getRadius() == adapter.getRadius()) {
        	return true;
        }
        
        if (adapter == null || getClass() != adapter.getClass()) {
            return false;
        }
        return false;

    }

	/**
	 * @param hexagon the hexagon to set
	 */
	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
}
