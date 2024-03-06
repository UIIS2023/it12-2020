package command;

import java.io.Serializable;



import shapes.Point;



public class UpdatePointCmd implements Command, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point original;
	private Point newPoint;
	private Point oldPoint;
	
	
	
	

	public UpdatePointCmd(Point oldPoint, Point newPoint) {
		super();
		this.newPoint = newPoint;
		this.oldPoint = oldPoint;
	}

	@Override
	public void execute() {

		original = oldPoint.clone();
		
		oldPoint.setX(newPoint.getX());
		oldPoint.setY(newPoint.getY());
		oldPoint.setEdgeColor(newPoint.getEdgeColor());
		
		
	}

	@Override
	public void unexecute() {
		
		oldPoint.setX(original.getX());
		oldPoint.setY(original.getY());
		oldPoint.setEdgeColor(original.getEdgeColor());
		
	}


	@Override
	public String toString() {
		if(original == null) {
			return "ModifyPoint; " + oldPoint.toString() + "; TO ;" + newPoint.toString();
		} else {
			return "ModifyPoint; " + original.toString() + "; TO ;" + newPoint.toString();
		}
		
	}

	


	
	

	
	
	

}
