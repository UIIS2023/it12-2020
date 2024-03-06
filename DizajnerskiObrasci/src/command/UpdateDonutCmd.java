package command;


import java.io.Serializable;




import shapes.Donut;



public class UpdateDonutCmd implements Command, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Donut original = new Donut();
	private Donut newDonut;
	private Donut oldDonut;
	
	
	
	public UpdateDonutCmd(Donut oldDonut, Donut newDonut) {
		super();
		this.newDonut = newDonut;
		this.oldDonut = oldDonut;
	
	}

	
	
	@Override
	public void execute() {
		
		original = oldDonut.clone();
		
		oldDonut.getCenter().setX(newDonut.getCenter().getX());
		oldDonut.getCenter().setY(newDonut.getCenter().getY());
		try {
			oldDonut.setRadius(newDonut.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			oldDonut.setInnerRadius(newDonut.getInnerRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldDonut.setEdgeColor(newDonut.getEdgeColor());
		oldDonut.setInnerColor(newDonut.getInnerColor());
		
		
		
		
	}

	@Override
	public void unexecute() {

		oldDonut.getCenter().setX(original.getCenter().getX());
		oldDonut.getCenter().setY(original.getCenter().getY());
		try {
			oldDonut.setRadius(original.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			oldDonut.setInnerRadius(original.getInnerRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		oldDonut.setEdgeColor(original.getEdgeColor());
		oldDonut.setInnerColor(original.getInnerColor());
		
		
	}



	


	@Override
	public String toString() {
		if(original.getCenter() == null) {
			return "ModifyDonut; " + oldDonut.toString()+ "; TO ;" + newDonut.toString();
		} else {
			return "ModifyDonut; " + original.toString()+ "; TO ;" + newDonut.toString();
		}
		
	}



	



	

	
	
	

}
