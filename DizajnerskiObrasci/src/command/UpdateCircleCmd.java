package command;


import java.io.Serializable;



import shapes.Circle;




public class UpdateCircleCmd implements Command , Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Circle newCircle;
	private Circle oldCircle;
	private Circle original;
	
	
	
	public UpdateCircleCmd(Circle oldCircle, Circle newCircle) {
		super();
		this.newCircle = newCircle;
		this.oldCircle = oldCircle;
		
	}
	
	@Override
	public void execute(){

		
		original = oldCircle.clone();
		
		oldCircle.getCenter().setX(newCircle.getCenter().getX());
		oldCircle.getCenter().setY(newCircle.getCenter().getY());
		try {
			oldCircle.setRadius(newCircle.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldCircle.setEdgeColor(newCircle.getEdgeColor());
		oldCircle.setInnerColor(newCircle.getInnerColor());
		

	}
	@Override
	public void unexecute() {
		
		oldCircle.getCenter().setX(original.getCenter().getX());
		oldCircle.getCenter().setY(original.getCenter().getY());
		try {
			oldCircle.setRadius(original.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldCircle.setEdgeColor(original.getEdgeColor());
		oldCircle.setInnerColor(original.getInnerColor());

	}


	@Override
	public String toString() {
		if(original == null) {
			return "ModifyCircle; " + oldCircle.toString() + " ; TO ;" + newCircle.toString();
		} else {
			return "ModifyCircle; " + original.toString() + " ; TO ;" + newCircle.toString();
		}
		
	}

	


	
	

	
	
	
	
	

}
