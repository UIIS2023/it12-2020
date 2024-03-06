package shapes;

import java.awt.Graphics;
import java.io.Serializable;
import java.awt.Color;

public abstract class Shape implements Moveable,Comparable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean selected;
	private Color edgeColor;
	public Shape() {

	}
	
	public Shape(Color color) {
		this.edgeColor = color;
	}
	
	public Shape(Color color, boolean selected) {
		this(color);
		this.selected = selected;
	}

	public Shape(boolean selected) {
		this.selected = selected;
	}
	 
	public abstract boolean contains (int x, int y);
    public abstract void draw (Graphics g);//Graphics g omogucava metode za iscrtavanje oblika
    
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;

	}
	public Color getEdgeColor() {
		return edgeColor;
	}
	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}
}
 