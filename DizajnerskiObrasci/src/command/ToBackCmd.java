package command;

import java.io.Serializable;
import java.util.List;


import shapes.Shape;

public class ToBackCmd implements Command, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private List<Shape> list;
	private int position;
	
	
	
	public ToBackCmd(Shape shape, List<Shape> list) {
		super();
		this.shape = shape;
		this.list = list;
	}

	@Override
	public void execute() {
		position = list.indexOf(shape);
		if (position > 0) {
			Shape shape = list.remove(position);
			list.add(position - 1, shape);
		}
		
	}

	@Override
	public void unexecute() {
		position = list.indexOf(shape);
		if (position < list.size() - 1) {
			Shape shape = list.remove(position);
			list.add(position + 1, shape);
		}
		
	}

	@Override
	public String toString() {
		return "ToBack; "+ shape.toString();
	}

	

	
	
	

}
