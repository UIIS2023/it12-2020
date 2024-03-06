package command;


import java.io.Serializable;

import adapter.HexagonAdapter;
import mvc.DrawingModel;
import shapes.Shape;

public class DeselectShapeCmd implements Command, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private DrawingModel model;
	
	
	
	public DeselectShapeCmd(DrawingModel model,Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	@Override
	public void execute() {
		for (Shape s  : model.getShapes()) {
			if(s instanceof HexagonAdapter) {
				if(shape instanceof HexagonAdapter) {
					HexagonAdapter ha = (HexagonAdapter) s;
					HexagonAdapter ha2 = (HexagonAdapter) shape;
					if(ha.equals(ha2)) {
						model.deselectShape(s);
						break;
					}
				}
			}
			if(s.equals(shape)) { 
				model.deselectShape(s);
				break;
			}
		}
	
		
	}

	@Override
	public void unexecute() {
		for (Shape s  : model.getShapes()) {
			if(s instanceof HexagonAdapter) {
				if(shape instanceof HexagonAdapter) {
					HexagonAdapter ha = (HexagonAdapter) s;
					HexagonAdapter ha2 = (HexagonAdapter) shape;
					if(ha.equals(ha2)) {
						model.selectShape(s);
						break;
					}
				}
			}
			if(s.equals(shape)) { 
				model.selectShape(s);
				break;
			}
		}
		
	}
	@Override
	public String toString() {
		return "DeselectShape; "+ shape.toString();
	}
	

	
	

	

}
