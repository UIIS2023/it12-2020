package mvc;

import javax.swing.JPanel;

import shapes.Shape;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Iterator;


public class DrawingView extends JPanel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingModel model = new DrawingModel();

	/**
	 * Create the panel.
	 */
	public DrawingView() {
		setBackground(Color.WHITE);
	}


	public void setModel(DrawingModel model) {
		this.model = model;
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> iterator =model.getShapes().iterator();
		while(iterator.hasNext())
			iterator.next().draw(g);
	}
	

	
	
}
