package observer;

import java.io.Serializable;

import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class ButtonState implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingFrame frame;
	private DrawingController controller;
	
	public ButtonState (DrawingFrame frame, DrawingController controller) {
		this.frame = frame;
		this.controller = controller;
	}
	
	@Override
	public void changeStateOfButton(int numberOfSelectedShapes, DrawingModel model) {
		
			if(model.getShapes().size() == 0) {
				frame.getLeftPanel().getBtnSaveDrawing().setEnabled(false);
			} else if(model.getShapes().size() > 0){
				frame.getLeftPanel().getBtnSaveDrawing().setEnabled(true);
			}
			
			if(frame.getBottomPanel().getDefaultListModel().size() > 0) {
				frame.getLeftPanel().getBtnSaveLog().setEnabled(true);
				frame.getLeftPanel().getBtnLoadLogAndDrawing().setEnabled(false);//disejbluje se kad se ucita neki log i kad se iscrta nesto
			} else {
				frame.getLeftPanel().getBtnSaveLog().setEnabled(false);
			}
			
		
			if(numberOfSelectedShapes == 0) {
				frame.getTopPanel().getBtnModify().setEnabled(false);
				frame.getTopPanel().getBtnDelete().setEnabled(false);
				frame.getLeftPanel().getBtnToFront().setEnabled(false);
				frame.getLeftPanel().getBtnToBack().setEnabled(false);
				frame.getLeftPanel().getBtnBringToFront().setEnabled(false);
				frame.getLeftPanel().getBtnBringToBack().setEnabled(false);
			} 
			
			else if(numberOfSelectedShapes == 1) {
				frame.getTopPanel().getBtnModify().setEnabled(true);
				frame.getTopPanel().getBtnDelete().setEnabled(true);
				
				if(model.getIndexOfSelectedShape() != -1) {
					//ako je u sredini negde
					if(model.getIndexOfSelectedShape() > 0 && model.getIndexOfSelectedShape() < model.getShapes().size() - 1) {
						frame.getLeftPanel().getBtnBringToFront().setEnabled(true);
						frame.getLeftPanel().getBtnBringToFront().setEnabled(true);
					} 					
					//ako je prvi
					if(model.getIndexOfSelectedShape() == 0) {
						frame.getLeftPanel().getBtnBringToBack().setEnabled(false);
						frame.getLeftPanel().getBtnToBack().setEnabled(false);
					} else {
						frame.getLeftPanel().getBtnBringToBack().setEnabled(true);
						frame.getLeftPanel().getBtnToBack().setEnabled(true);
					}
					//ako je poslednji
					if(model.getIndexOfSelectedShape() == model.getShapes().size() - 1) {
						frame.getLeftPanel().getBtnBringToFront().setEnabled(false);
						frame.getLeftPanel().getBtnToFront().setEnabled(false);
					} else {
						frame.getLeftPanel().getBtnBringToFront().setEnabled(true);
						frame.getLeftPanel().getBtnToFront().setEnabled(true);
					}
					
					
				} 
				
			}
			else if(numberOfSelectedShapes > 1) {
				frame.getTopPanel().getBtnModify().setEnabled(false);
				frame.getLeftPanel().getBtnBringToFront().setEnabled(false);
				frame.getLeftPanel().getBtnBringToBack().setEnabled(false);
				frame.getLeftPanel().getBtnToFront().setEnabled(false);
				frame.getLeftPanel().getBtnToBack().setEnabled(false);
				frame.getTopPanel().getBtnDelete().setEnabled(true);
			}
			
			if(controller.getUndoStack().size()==0) {
				frame.getLeftPanel().getBtnUndo().setEnabled(false);
			}
			else {
				frame.getLeftPanel().getBtnUndo().setEnabled(true);
			}
			
			if(controller.getRedoStack().size()==0) {
				frame.getLeftPanel().getBtnRedo().setEnabled(false);
			}else{
				frame.getLeftPanel().getBtnRedo().setEnabled(true);
			}
			
		
		
	}

}
