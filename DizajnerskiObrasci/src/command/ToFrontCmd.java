package command;

import java.io.Serializable;
import java.util.List;


import shapes.Shape;

public class ToFrontCmd implements Command, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private List<Shape> list;
	private int position;
	
	
	
	public ToFrontCmd(Shape shape, List<Shape> list) {
		this.shape = shape;
		this.list = list;
	}

	
	
	@Override
	public void execute() { //pomeranje za jednu poziciju
		position = list.indexOf(shape);
		if (position < list.size() - 1) {
			Shape shape = list.remove(position);
			list.add(position + 1, shape);
		}
		
	}

	@Override
	public void unexecute() {
		position = list.indexOf(shape);
		if (position > 0) {
			Shape shape = list.remove(position);
			list.add(position - 1, shape);
		}
		
	}



	@Override
	public String toString() {
		return "ToFront; "+shape.toString();
	}



	



	
	
	

}
