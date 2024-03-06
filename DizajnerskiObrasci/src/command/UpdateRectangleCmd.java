package command;

import java.io.Serializable;




import shapes.Rectangle;



public class UpdateRectangleCmd implements Command , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle original;
	private Rectangle oldRectangle;
	private Rectangle newRectangle;
	
	
	

	
	public UpdateRectangleCmd(Rectangle oldRectangle, Rectangle newRectangle) {
		super();
		this.oldRectangle = oldRectangle;
		this.newRectangle = newRectangle;
		
	}

	@Override
	public void execute() {
		
		original = oldRectangle.clone();
		
		oldRectangle.getUpperLeftPoint().setX(newRectangle.getUpperLeftPoint().getX());
		oldRectangle.getUpperLeftPoint().setY(newRectangle.getUpperLeftPoint().getY());
		oldRectangle.setHeight(newRectangle.getHeight());
		oldRectangle.setWidth(newRectangle.getWidth());
		oldRectangle.setEdgeColor(newRectangle.getEdgeColor());
		oldRectangle.setInnerColor(newRectangle.getInnerColor());

	}

	@Override
	public void unexecute() {
		oldRectangle.getUpperLeftPoint().setX(original.getUpperLeftPoint().getX());
		oldRectangle.getUpperLeftPoint().setY(original.getUpperLeftPoint().getY());
		oldRectangle.setHeight(original.getHeight());
		oldRectangle.setWidth(original.getWidth());
		oldRectangle.setEdgeColor(original.getEdgeColor());
		oldRectangle.setInnerColor(original.getInnerColor());

	}

	

	@Override
	public String toString() {
		if(original == null) {
			return "ModifyRectangle; " + oldRectangle.toString() + "; TO ;" + newRectangle.toString();
		} else {
			return "ModifyRectangle; " + original.toString() + "; TO ;" + newRectangle.toString();
		}
		
	}


	
	

	
	
	

	

}
