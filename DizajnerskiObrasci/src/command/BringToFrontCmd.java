package command;

import java.io.Serializable;
import java.util.List;


import shapes.Shape;

public class BringToFrontCmd implements Command, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private List<Shape> list;
	private int position;
	
	
	
	public BringToFrontCmd(Shape shape, List<Shape> list) {
		this.shape = shape;
		this.list = list;
		position = list.indexOf(shape);
	}

	
	
	@Override
	public void execute() {
		//remove vraca oblik koji se uklanja sa prosledjene pozicije
		position = list.indexOf(shape); // ubaceno zbog loga
		shape = list.remove(position);
		//dodavanje tog oblika u listu ali na kraj
		list.add(list.size(),shape);
		
	}

	@Override
	public void unexecute() {
		//uklanjanje oblika sa kraja liste
		shape = list.remove(list.size() - 1);
		//postavljanje oblika na prethodnu poziciju
		list.add(position, shape);
	}



	@Override
	public String toString() {
		return "BringToFront; " + shape.toString();
	}



	



	
	
	

}
