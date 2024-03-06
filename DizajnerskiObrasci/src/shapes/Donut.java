package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int innerRadius;
	
	public Donut() {
		
	}
	public Donut (Point center) {
		super(center);
	}
	public Donut(Point center, int radius, int innerRadius) {
		super(center,radius);
		this.innerRadius=innerRadius;	
	}
	
	public Donut(Point center, int radius, int innerRadius, Color edgeColor) {
		this(center, radius, innerRadius);
		this.setEdgeColor(edgeColor);
	}
	
    public Donut(Point center, int radius, int innerRadius,boolean selected) {
    	this(center,radius,innerRadius);
    	this.setSelected(selected);	
	} 
    
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color edgeColor) {
		this(center, radius, innerRadius, selected);
		this.setEdgeColor(edgeColor);
	}
	
	public Donut(Point center, int radius, int innerRadius, Color edgeColor, Color innerColor) {
		this(center, radius, innerRadius,edgeColor);
		this.setInnerColor(innerColor);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color edgeColor, Color innerColor) {
		this(center, radius, innerRadius, selected, edgeColor);
		this.setInnerColor(innerColor);
	}
	
    public double area() {
		return super.area()-innerRadius*innerRadius*Math.PI;
	}
    
	public boolean equals (Object obj)
	{
		if(obj instanceof Donut)
		{
			Donut temporary=(Donut) obj;
			if(this.getCenter().equals(temporary.getCenter()) && this.getRadius()==temporary.getRadius() && this.innerRadius==temporary.innerRadius)
				return true;
			else
				return false;
			
		}
		else
			return false;
	}
	public boolean contains(int x, int y) {
		double distanceFromCenter = getCenter().distance(x, y);
		return super.contains(x,y) && distanceFromCenter>innerRadius;
	}
	public boolean contains(Point p) {
		double distanceFromCenter = getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(),p.getY()) && distanceFromCenter>innerRadius;
	}
	
	
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        java.awt.Shape outer = new Ellipse2D.Double(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(), 2*getRadius(),2*getRadius());
        java.awt.Shape inner = new Ellipse2D.Double(getCenter().getX() - getInnerRadius(), getCenter().getY() - getInnerRadius(), 2*getInnerRadius(),2*getInnerRadius());

        Area cwc = new Area(outer);
        cwc.subtract(new Area(inner));

        g2d.setColor(getInnerColor());
        g2d.fill(cwc);
        g2d.setColor(getEdgeColor());
        g2d.draw(cwc);

        g2d.dispose();
        
        if(isSelected()) {
        	g.setColor(Color.blue);
        	// za unustrasnji krug
        	g.drawRect(getCenter().getX() - innerRadius - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + innerRadius - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - innerRadius - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + innerRadius - 2, 4, 4);
			
			//za spoljasnji krug
			g.drawRect(getCenter().getX() - radius - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + radius - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - radius - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + radius - 2, 4, 4);
        }
	}
	
	public void fill(Graphics g) {
		
		super.fill(g); 
		g.setColor(Color.white);
		g.fillOval(getCenter().getX() - this.innerRadius,
					getCenter().getY() - this.innerRadius,
					this.innerRadius * 2 - 2,
					this.innerRadius * 2 - 2);
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Donut) {
			return (int)(this.area()-((Donut)o).area());
	
		}
		return 0;
	}
	
	public String toString() {
		return "Donut; " + "center: " + "(" + center.getX() + ", " + center.getY() + ")" + ", " + "radius: " + radius  + 
				", innerRadius: " + innerRadius
				+ " ; edge color: " + getEdgeColor().toString() + " ; inner color: " + getInnerColor().toString();
	   }

	public int getInnerRadius() {
		return innerRadius;
	}
	
	public void setInnerRadius(int innerRadius) throws Exception {
		if(innerRadius > 0) {
			this.innerRadius = innerRadius;
		}
		else {
			throw new NumberFormatException("Inner radius has to be a value greater than 0!");
		}
			
	}
	
	
	

	
	@Override
	public Donut clone() {
		Point point = new Point(this.getCenter().getX(), this.getCenter().getY());
		
			Donut donut = new Donut(point, getRadius(), getInnerRadius(), getEdgeColor(), getInnerColor());
		
		return donut;
	
	}
	



}
