package command;



import java.io.Serializable;

import adapter.HexagonAdapter;
import mvc.DrawingModel;
import shapes.Shape;

public class AddShapeCmd implements Command,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingModel model;
	private Shape shape;
	
	
	public AddShapeCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		model.addShape(shape);

	}

	@Override
	public void unexecute() {
		
		for(Shape s : model.getShapes()) {
			if(s instanceof HexagonAdapter) {
				if(shape instanceof HexagonAdapter) {
					HexagonAdapter ha = (HexagonAdapter) s;
					HexagonAdapter ha2 = (HexagonAdapter) shape;
					if(ha.equals(ha2)) {
						model.removeHexagon(ha);
						break;
					}
				}
			}
			
			if(s.equals(shape)) {
				model.removeShape(s);
				break;
			}
			
		}
		

	}

	@Override
	public String toString() {
		return "AddShape; " + shape.toString();
	}
	
	

	

	
	
	

}
