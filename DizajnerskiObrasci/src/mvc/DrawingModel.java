package mvc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adapter.HexagonAdapter;
import observer.Observable;
import observer.Observer;

import shapes.Shape;

public class DrawingModel implements Observable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Shape> shapes = new ArrayList<>();
	private int i;
	
	private List<Observer> observers = new ArrayList<>();

	public void addShape(Shape shape) {

			shapes.add(shape);
	
	}
	
	
	public void removeShape(Shape shape) {

			shapes.remove(shape);

   }
	
	public void removeHexagon(HexagonAdapter hexagon) {
		shapes.remove(hexagon);
	}
	
	
	
	public Shape getShape(int index) {
		return shapes.get(index);
	}
	
	public void setShape(int index, Shape shape) {
		shapes.set(index, shape);
	}
	
	
	public void removeSelected() {
		shapes.removeIf(shape -> shape.isSelected());
		
	}
	
	public void selectShape(Shape shape) {

		shape.setSelected(true);
	}
	
	public void deselectShape(Shape shape) {

		shape.setSelected(false);
	}
	
	
	
	
	
	
	
	public int getSelected() {
		for (i = shapes.size()-1; i >= 0; i--) {
			if (shapes.get(i).isSelected()==true) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isEmpty() {
		return shapes.isEmpty();
	}
	
	public List<Shape> getShapes() {
		return shapes;
	}
	
	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}

	public void addShapeOnIndex(int index, Shape shape) {
		shapes.add(index, shape);
	}
	
	public int getIndexOfSelectedShape() {
		for(Shape s : shapes) {
			if(s.isSelected()) { 
				return shapes.indexOf(s);
			}
		}
		
		return -1;
	}


	@Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);
		
	}



	@Override
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
		
	}



	@Override
	public void notifyObservers() {
		int numberOfSelectedShapes = 0;
		for(int k=0; k < shapes.size(); k++) {
			if (shapes.get(k).isSelected()) 
				numberOfSelectedShapes++;
		}
		for (Observer obs: observers) {
			obs.changeStateOfButton(numberOfSelectedShapes, this);
		}
		
	}

}
