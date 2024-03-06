package command;

import java.io.Serializable;
import java.util.List;


import shapes.Shape;

public class BringToBackCmd implements Command, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private List<Shape> list;
	private int position; 
	

	
	public BringToBackCmd(Shape shape, List<Shape> list) {
		this.shape = shape;
		this.list = list;
		this.position = list.indexOf(shape);
	}

	@Override
	public void execute() {
		//uklanjanje oblika sa trenutne pozicije
		position = list.indexOf(shape); // ubaceno zbog loga
		shape = list.remove(position);
		//dodavanje oblika na pocetak liste
		list.add(0, shape);
	}

	@Override
	public void unexecute() {
		//uklanjanje oblika sa pocetka liste
		shape = list.remove(0);
		//postavljanje oblika na prethodnu poziciju
		list.add(position, shape);
	}

	@Override
	public String toString() {
		return "BringToBack; "+ shape.toString();
	}

	

	
	
	

}
