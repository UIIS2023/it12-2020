package observer;

import mvc.DrawingModel;

public interface Observer {
	
	void changeStateOfButton(int numberOfSelectedShapes, DrawingModel model);
}
