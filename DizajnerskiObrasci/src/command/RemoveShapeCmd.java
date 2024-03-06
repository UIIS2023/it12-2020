package command;

import java.io.Serializable;


import mvc.DrawingModel;
import shapes.Shape;

public class RemoveShapeCmd implements Command, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingModel model;
    private Shape shape;
    private int index;
    
   
    
	public RemoveShapeCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
		this.index = model.getShapes().indexOf(shape);
		if(index==-1) {
			index=model.getShapes().size(); // stavlja ga na poslednje mesto
		}
	}
	
	@Override
	public void execute() {
		model.removeSelected();
		
		
	}

	@Override
	public void unexecute() {
		shape.setSelected(true); // Dodato zbog load-ovanja

		model.addShapeOnIndex(index, shape);
	}

	@Override
	public String toString() {
		return "DeleteShape; " + shape.toString();
	}

	

	
	
	

}
