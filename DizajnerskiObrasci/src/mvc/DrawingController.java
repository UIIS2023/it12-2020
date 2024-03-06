package mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;


import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.DeselectAllShapesCmd;
import command.DeselectShapeCmd;
import command.RedoCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UndoCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import dialogs.DlgCircle;
import dialogs.DlgDonut;
import dialogs.DlgHexagon;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import observer.ButtonState;
import shapes.Circle;
import shapes.Donut;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import strategy.Drawing;
import strategy.Log;
import strategy.SaveStrategy;
import strategy.StrategyManager;

public class DrawingController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingFrame frame;
	private DrawingModel model;
	
	private ButtonState enDisBtn;
	
		
	private boolean lineSecondPoint = false;
	private final int selectOperation = 0;
	private final int drawingOperation = 1;
	private int activeOperation;
	private Point lineFirstPoint;
	
	
	
	
	private Stack<Command> undoStack, redoStack;
	
	
	
	
	public DrawingController(DrawingFrame frmDrawing, DrawingModel model) {
		super();
		this.frame = frmDrawing;
		this.model = model;
		activeOperation=drawingOperation;
		this.enDisBtn = new ButtonState(this.frame,this);
		model.addObserver(enDisBtn);
		
		
		
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
		
		
		
		
	}

	public void mouseClicked(MouseEvent e) {

		Point mouseClick = new Point(e.getX(), e.getY());
		Shape s = null;
		
		boolean executedDeselectOfShape = false;
		List<Shape> allShapes = model.getShapes();
		
		if (activeOperation == selectOperation) {
			for (Shape shape  : allShapes) {
				if(shape.contains(mouseClick.getX(), mouseClick.getY())) {
					executedDeselectOfShape = true;
					s=shape;					
				}
			}

			if(s != null) {
				if(s.isSelected()) {
					DeselectShapeCmd deselectShapeCmd = new DeselectShapeCmd(model,s);
					deselectShapeCmd.execute();
					frame.getBottomPanel().getDefaultListModel().addElement(deselectShapeCmd.toString());
					undoStack.push(deselectShapeCmd);
					
					redoStack.clear();
				}
				else {
					SelectShapeCmd selectShapeCmd = new SelectShapeCmd(model,s);
					selectShapeCmd.execute();
					frame.getBottomPanel().getDefaultListModel().addElement(selectShapeCmd.toString());
					undoStack.push(selectShapeCmd);
					
					redoStack.clear();
				}
			}			
			
			if(executedDeselectOfShape == false) {
				deselectAllSelectedShapes();				
			}
		}
		
		if (!frame.getRightPanel().getTglbtnLine().isSelected()) 
			lineSecondPoint = false;
		
	
		if (frame.getRightPanel().getTglbtnPoint().isSelected()) {
			
			deselectAllSelectedShapes();	
			DlgPoint dlgPoint = new DlgPoint();
		    dlgPoint.setPoint(mouseClick);
		    dlgPoint.setController(this);
			dlgPoint.setColors(frame.getTopPanel().getEdgeColor());
			dlgPoint.setVisible(true);
			
			if(dlgPoint.getPoint() != null) {
				AddShapeCmd addShapeCmd = new AddShapeCmd(model,dlgPoint.getPoint());
				addShapeCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(addShapeCmd.toString());
				undoStack.push(addShapeCmd);
			
				redoStack.clear();
				
			}
			
		} else if (frame.getRightPanel().getTglbtnLine().isSelected()) {
			
			deselectAllSelectedShapes();	
			if (lineSecondPoint==true) 
			{
				DlgLine dlgLine = new DlgLine();
				dlgLine.setFirstPoint(lineFirstPoint);
				dlgLine.setSecondPoint(mouseClick);
				dlgLine.setController(this);
				dlgLine.setColors(frame.getTopPanel().getEdgeColor());
				dlgLine.setVisible(true);
				if(dlgLine.getLine() != null) {
					AddShapeCmd addShapeCmd = new AddShapeCmd(model,dlgLine.getLine());
					addShapeCmd.execute();
					frame.getBottomPanel().getDefaultListModel().addElement(addShapeCmd.toString());
					undoStack.push(addShapeCmd);
					
				    redoStack.clear();
				    frame.repaint();
				}
				lineSecondPoint = false;
				model.notifyObservers();
				return;
				
			}					
			lineFirstPoint = mouseClick;
			lineSecondPoint = true;
			
		
		} else if (frame.getRightPanel().getTglbtnRectangle().isSelected()) {
			
			deselectAllSelectedShapes();	
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setPoint(mouseClick);
			dlgRectangle.setController(this);
			dlgRectangle.setColors(frame.getTopPanel().getEdgeColor(),frame.getTopPanel().getInnerColor());
			dlgRectangle.setVisible(true);
			
			if(dlgRectangle.getRectangle() != null) {
				AddShapeCmd addShapeCmd = new AddShapeCmd(model,dlgRectangle.getRectangle());
				addShapeCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(addShapeCmd.toString());
				undoStack.push(addShapeCmd);
				
				redoStack.clear();
			
			}
			
		} else if (frame.getRightPanel().getTglbtnCircle().isSelected()) {
			
			deselectAllSelectedShapes();	
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setPoint(mouseClick);
			dlgCircle.setController(this);
			dlgCircle.setColors(frame.getTopPanel().getEdgeColor(),frame.getTopPanel().getInnerColor());
			dlgCircle.setVisible(true);
			
			if(dlgCircle.getCircle() != null) {
				AddShapeCmd addShapeCmd = new AddShapeCmd(model,dlgCircle.getCircle());
				addShapeCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(addShapeCmd.toString());
				undoStack.push(addShapeCmd);
				
				redoStack.clear();
				
			}
			
		} else if (frame.getRightPanel().getTglbtnDonut().isSelected()) {
			
			deselectAllSelectedShapes();	
			DlgDonut dlgDonut = new DlgDonut();
			dlgDonut.setPoint(mouseClick);
			dlgDonut.setController(this);
			dlgDonut.setColors(frame.getTopPanel().getEdgeColor(),frame.getTopPanel().getInnerColor());
			dlgDonut.setVisible(true);
			
			if(dlgDonut.getDonut() != null) {
				AddShapeCmd addShapeCmd = new AddShapeCmd(model,dlgDonut.getDonut());
				addShapeCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(addShapeCmd.toString());
				undoStack.push(addShapeCmd);
				
				redoStack.clear();
				
			}
			
		}
		 else if (frame.getRightPanel().getTglbtnHexagon().isSelected()) {
			
			deselectAllSelectedShapes();	
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.setPoint(mouseClick);
			dlgHexagon.setController(this);
			dlgHexagon.setColors(frame.getTopPanel().getEdgeColor(),frame.getTopPanel().getInnerColor());
			dlgHexagon.setVisible(true);
			
			if(dlgHexagon.getHexagon() != null) {
				AddShapeCmd addShapeCmd = new AddShapeCmd(model,dlgHexagon.getHexagon());
				addShapeCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(addShapeCmd.toString());
				undoStack.push(addShapeCmd);
				
				redoStack.clear();
			
			}
			
		}
		frame.repaint();
		model.notifyObservers();
	}
	
	
	
	private void deselectAllSelectedShapes() {
		for (Shape shape  : model.getShapes()) {
			if(shape.isSelected()) {
				DeselectAllShapesCmd deselectAllShapesCmd = new DeselectAllShapesCmd(model,shape);
				deselectAllShapesCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(deselectAllShapesCmd.toString());
				undoStack.push(deselectAllShapesCmd);				
				redoStack.clear();
			}
		}
		
	}

	public void actionPerformedSelect(ActionEvent e) {
		activeOperation = selectOperation;
	}
	
	public void actionPerformedModify(ActionEvent e) {
		int index = model.getSelected();
		if (index == -1) 
			return;
		
		Shape shape = model.getShape(index);
		
		if (shape instanceof Point) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setPoint((Point)shape);
			dlgPoint.setController(this);
			dlgPoint.setVisible(true);
			
			if(dlgPoint.getPoint() != null) {
				UpdatePointCmd updatePointCmd = new UpdatePointCmd((Point)shape,dlgPoint.getPoint());
				updatePointCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(updatePointCmd.toString());
				undoStack.push(updatePointCmd);
				
				redoStack.clear();	
			}
		} else if (shape instanceof Line) {
			DlgLine dlgLine = new DlgLine();
			dlgLine.setLine((Line)shape);
			dlgLine.setController(this);
			dlgLine.setVisible(true);
			
			if(dlgLine.getLine() != null) {
				UpdateLineCmd updateLineCmd = new UpdateLineCmd ((Line)shape,dlgLine.getLine());
				updateLineCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(updateLineCmd.toString());
				undoStack.push(updateLineCmd);
				
				redoStack.clear();
			}
		} else if (shape instanceof Rectangle) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setRectangle((Rectangle)shape);
			dlgRectangle.setController(this);
			dlgRectangle.setVisible(true);
			
			if(dlgRectangle.getRectangle() != null) {
				UpdateRectangleCmd updateRectangleCmd = new UpdateRectangleCmd((Rectangle)shape,dlgRectangle.getRectangle());
				updateRectangleCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(updateRectangleCmd.toString());
				undoStack.push(updateRectangleCmd);
				
				redoStack.clear();
			}
		
		}else if (shape instanceof Donut) {
				DlgDonut dlgDonut = new DlgDonut();
				dlgDonut.setDonut((Donut)shape);
				dlgDonut.setController(this);
				dlgDonut.setVisible(true);
				
			if(dlgDonut.getDonut() != null) {
				UpdateDonutCmd updateDonutCmd = new UpdateDonutCmd((Donut)shape,dlgDonut.getDonut());
				updateDonutCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(updateDonutCmd.toString());
				undoStack.push(updateDonutCmd);
			
				redoStack.clear();
			}
		} else if (shape instanceof Circle) {
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setCircle((Circle)shape);
			dlgCircle.setController(this);
			dlgCircle.setVisible(true);
			
			if(dlgCircle.getCircle() != null) {
				UpdateCircleCmd updateCircleCmd = new UpdateCircleCmd((Circle)shape,dlgCircle.getCircle());
				updateCircleCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(updateCircleCmd.toString());
				undoStack.push(updateCircleCmd);
				
				redoStack.clear();
			}
		} else if (shape instanceof HexagonAdapter) {
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.setHexagon((HexagonAdapter)shape);
			dlgHexagon.setController(this);
			dlgHexagon.setVisible(true);
			
			if(dlgHexagon.getHexagon() != null) {
				UpdateHexagonCmd updateHexagonCmd = new UpdateHexagonCmd((HexagonAdapter)shape,dlgHexagon.getHexagon());
				updateHexagonCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(updateHexagonCmd.toString());
				undoStack.push(updateHexagonCmd);
				
				redoStack.clear();
			}
		}
		frame.repaint();	
	}
	
	
	
	public void actionPerformedChooseInnerColor(ActionEvent e) {
		Color color  = frame.chooseColor(frame.getTopPanel().getBtnInnerColor().getBackground());
		frame.getTopPanel().getBtnInnerColor().setBackground(color);	
		frame.getTopPanel().setInnerColor(color);
	}
	
	

	public void actionPerformedChooseEdgeColor(ActionEvent e) {
		Color color  = frame.chooseColor(frame.getTopPanel().getBtnEdgeColor().getBackground());
		frame.getTopPanel().getBtnEdgeColor().setBackground(color);	
		frame.getTopPanel().setEdgeColor(color);
	}
	
	
	
	public void actionPerformedDelete(ActionEvent e) {
		if (model.isEmpty())
			return;
		
		if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected shape/shapes?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			List<Shape> nonDeletedShapes = new ArrayList<Shape>();
			List<Shape> deletedShapes = new ArrayList<Shape>();
			for (Shape shape  : model.getShapes()) {
				if(shape.isSelected()) { 
					deletedShapes.add(shape);		
				} else {
					nonDeletedShapes.add(shape);
				}
			}
			
			for (Shape shape : deletedShapes) {
				RemoveShapeCmd removeShapeCmd = new RemoveShapeCmd(model,shape);
				removeShapeCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(removeShapeCmd.toString());
				undoStack.push(removeShapeCmd);
			
				redoStack.clear();
			}
			
			model.notifyObservers();
			frame.repaint();
		}
	}

	public void actionPerformedSaveLog(ActionEvent e) {
		SaveStrategy log = new Log(frame,model,redoStack);
		SaveStrategy strategyManager = new StrategyManager(log);
		strategyManager.save();
		
	}

	public void actionPerformedSaveDrawing(ActionEvent e) {
		SaveStrategy drawing = new Drawing(undoStack, redoStack);
		SaveStrategy strategyManager = new StrategyManager(drawing);
		strategyManager.save();
	
		
	}

	public void actionPerformedLoadLogAndDrawingStepByStep(ActionEvent e) {
		Log log = new Log(frame,model,redoStack);
		log.load();
		model.notifyObservers();//da bi enable-ovao redo dugme kad se ucita log i disable-ovao load log and drawing dugme
		
	}

	

	public void actionPerformedToFront(ActionEvent e) {
		for(Shape shape : model.getShapes()) {
			if(shape.isSelected()== true) {
				ToFrontCmd toFrontCmd = new ToFrontCmd (shape, model.getShapes());
				toFrontCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(toFrontCmd.toString());
				undoStack.push(toFrontCmd);
				
				redoStack.clear();
				break;
			}
		}
		model.notifyObservers();
		frame.repaint();
		
	}

	public void actionPerformedToBack(ActionEvent e) {
		for(Shape shape : model.getShapes()) {
			if(shape.isSelected()==true) {
				ToBackCmd toBackCmd = new ToBackCmd(shape, model.getShapes());
				toBackCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(toBackCmd.toString());
				undoStack.push(toBackCmd);
				
				redoStack.clear();
				break;
			}
		}
		model.notifyObservers();
		frame.repaint();
		
	}

	public void actionPerformedBringToFront(ActionEvent e) {
		for(Shape shape : model.getShapes()) {
			if(shape.isSelected()== true) {
				BringToFrontCmd bringToFrontCmd = new BringToFrontCmd(shape, model.getShapes());
				bringToFrontCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(bringToFrontCmd.toString());
				undoStack.push(bringToFrontCmd);
				
				redoStack.clear();
				break;
			}
		}
		model.notifyObservers();
		frame.repaint();
		
	}

	public void actionPerformedBringToBack(ActionEvent e) {
		for(Shape shape : model.getShapes()) {
			if(shape.isSelected()== true) {
				BringToBackCmd bringToBackCmd = new BringToBackCmd(shape, model.getShapes());
				bringToBackCmd.execute();
				frame.getBottomPanel().getDefaultListModel().addElement(bringToBackCmd.toString());
				undoStack.push(bringToBackCmd);
			
				redoStack.clear();
				break;
			}
		}
		model.notifyObservers();
		frame.repaint();
		
	}
	
	public void actionPerformedUndo(ActionEvent e) {
		if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            boolean deselectFromBlank = false;
            boolean removeMoreThanOne = false;
            
            if(command instanceof UndoCmd) {
            	if(((UndoCmd) command).getCmd() instanceof DeselectAllShapesCmd) {
            		deselectFromBlank = doDeselectAllShapesUndoCmd(command);
            	} else if(((UndoCmd) command).getCmd() instanceof RemoveShapeCmd) {
            		removeMoreThanOne = doRemoveAllSelectedShapesUndoCmd(command);
            	}
            } else if(command instanceof RedoCmd) {
            	if(((RedoCmd) command).getCmd() instanceof DeselectAllShapesCmd) {
            		deselectFromBlank = doDeselectAllShapesUndoCmd(command);
            	} else if(((RedoCmd) command).getCmd() instanceof RemoveShapeCmd) {
            		removeMoreThanOne = doRemoveAllSelectedShapesUndoCmd(command);
            	}
            }
            
            while(command instanceof DeselectAllShapesCmd) {
            	command.unexecute();     
                frame.getBottomPanel().getDefaultListModel().addElement("Undo; " + command.toString());
                redoStack.push(command);
               
                command = undoStack.peek();
                deselectFromBlank = true;
            
                if(command instanceof DeselectAllShapesCmd) {
                	command = undoStack.pop();
                	continue;
                } else {
                	break;
                }
            }
            
            while(command instanceof RemoveShapeCmd) {
            	command.unexecute();        
                frame.getBottomPanel().getDefaultListModel().addElement("Undo; " + command.toString());
                redoStack.push(command);
               
                command = undoStack.peek();
                removeMoreThanOne = true;
                
                if(command instanceof RemoveShapeCmd) {
                	command = undoStack.pop();
                	continue;
                } else {
                	break;
                }
            }
            
            if(!deselectFromBlank && !removeMoreThanOne) {
            	command.unexecute();      
            	frame.getBottomPanel().getDefaultListModel().addElement("Undo; " + command.toString());
                redoStack.push(command);
               
            }
          
            model.notifyObservers();
            frame.repaint();
            
        }
	}



	public void actionPerformedRedo(ActionEvent e) {
		if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            boolean deselectFromBlank = false;
            boolean removeMoreThanOne = false;
            
            if(command instanceof UndoCmd) {
            	if(((UndoCmd) command).getCmd() instanceof DeselectAllShapesCmd) {
            		deselectFromBlank = doDeselectAllShapesRedoCmd(command);
            	} else if(((UndoCmd) command).getCmd() instanceof RemoveShapeCmd) {
            		removeMoreThanOne = doRemoveAllSelectedShapesRedoCmd(command);
            	}
            } else if(command instanceof RedoCmd){
            	// RedoCmd
            	if(((RedoCmd) command).getCmd() instanceof DeselectAllShapesCmd) {
            		deselectFromBlank = doDeselectAllShapesRedoCmd(command);
            	} else if(((RedoCmd) command).getCmd() instanceof RemoveShapeCmd) {
            		removeMoreThanOne = doRemoveAllSelectedShapesRedoCmd(command);
            	}
            }
            
            while(command instanceof DeselectAllShapesCmd) {
            	command.execute();         
                frame.getBottomPanel().getDefaultListModel().addElement("Redo; " + command.toString());
                undoStack.push(command);
              
                
            	if(redoStack.isEmpty()) {
            		break;
            	}
                
                command = redoStack.peek();
                deselectFromBlank = true;
                
                if(command instanceof DeselectAllShapesCmd) {
                	command = redoStack.pop();
                	continue;
                } else {
                	break;
                }
            }
            
            while(command instanceof RemoveShapeCmd) {
            	command.execute();     
                frame.getBottomPanel().getDefaultListModel().addElement("Redo; " + command.toString());
                undoStack.push(command);
                
                removeMoreThanOne = true;
                
                if(redoStack.isEmpty()) {
            		break;
            	}
                
                command = redoStack.peek();
               
                if(command instanceof RemoveShapeCmd) {
                	command = redoStack.pop();
                	continue;
                } else {
                	break;
                }
            }
            
            if(!deselectFromBlank && !removeMoreThanOne) {
            	command.execute();           
                frame.getBottomPanel().getDefaultListModel().addElement("Redo; " + command.toString());
            	undoStack.push(command);
            	
            }
            
            model.notifyObservers();
            frame.repaint();
            
        }
	}

	

	private boolean doDeselectAllShapesRedoCmd(Command command) {

		boolean deselectFromBlank = false;
		Command cmd = null;
		Command inputCommand = command;
		
		if(command instanceof UndoCmd ) { 
			cmd = ((UndoCmd) command).getCmd();
		} else {
			cmd = ((RedoCmd) command).getCmd();
		}
		
		while(cmd instanceof DeselectAllShapesCmd) {
        	command.execute();         
            frame.getBottomPanel().getDefaultListModel().addElement("Redo; " + command.toString());
            undoStack.push(command);
          
            
        	if(redoStack.isEmpty()) {
        		break;
        	}
            
            command = redoStack.peek();
            deselectFromBlank = true;
            
            if(!inputCommand.getClass().equals(command.getClass())) {
            	break;
            }
            
            Command isCmdDeselected = isDeselectAllSelectedCommand(command);
            
            if(isCmdDeselected == null) {
            	break;
            }
            
            if(isCmdDeselected instanceof DeselectAllShapesCmd) {
            	command = redoStack.pop();
            	continue;
            } else {
            	break;
            }
        }
		
		return deselectFromBlank;
	}
	
	private Command isDeselectAllSelectedCommand(Command command) {
		
		Command cmd = null;
		
		if(command instanceof UndoCmd ) { 
			cmd = ((UndoCmd) command).getCmd();
			if(cmd instanceof DeselectAllShapesCmd) {
				return cmd;
			}
		} else if(command instanceof RedoCmd ){
			cmd = ((RedoCmd) command).getCmd();
			if(cmd instanceof DeselectAllShapesCmd) {
				return cmd;
			}
		}
		
		return null;
	}
	
	private boolean doDeselectAllShapesUndoCmd(Command command) {
		
		boolean deselectFromBlank = false;
		Command cmd = null;
		Command inputCommand = command;
		
		if(command instanceof UndoCmd ) { 
			cmd = ((UndoCmd) command).getCmd();
		} else {
			cmd = ((RedoCmd) command).getCmd();
		}
		
		while(cmd instanceof DeselectAllShapesCmd) {
        	command.unexecute();     
            frame.getBottomPanel().getDefaultListModel().addElement("Undo; " + command.toString());
            redoStack.push(command);
           
            command = undoStack.peek();
            deselectFromBlank = true;
            
            if(!inputCommand.getClass().equals(command.getClass())) {
            	break;
            }
            
            Command isCmdDeselected = isDeselectAllSelectedCommand(command);
        
            if(isCmdDeselected == null) {
            	break;
            }
            
            if(isCmdDeselected instanceof DeselectAllShapesCmd) {
            	command = undoStack.pop();
            	continue;
            } else {
            	break;
            }
        }
		
		return deselectFromBlank;
	}


	private Command isRemoveShapeCommand(Command command) {
		Command cmd = null;
		
		if(command instanceof UndoCmd ) { 
			cmd = ((UndoCmd) command).getCmd();
			if(cmd instanceof RemoveShapeCmd) {
				return cmd;
			}
		} else if(command instanceof RedoCmd ){
			cmd = ((RedoCmd) command).getCmd();
			if(cmd instanceof RemoveShapeCmd) {
				return cmd;
			}
		}
		
		return null;
	}

	private boolean doRemoveAllSelectedShapesUndoCmd(Command command) {
		
		boolean removeMoreThanOne = false;
		Command cmd = null;
		Command inputCommand = command;
		
		if(command instanceof UndoCmd ) { 
			cmd = ((UndoCmd) command).getCmd();
		} else {
			cmd = ((RedoCmd) command).getCmd();
		}
		
		while(cmd instanceof RemoveShapeCmd) {
        	command.unexecute();     
            frame.getBottomPanel().getDefaultListModel().addElement("Undo; " + command.toString());
            redoStack.push(command);
           
            command = undoStack.peek();
            removeMoreThanOne = true;
            
            if(!inputCommand.getClass().equals(command.getClass())) {
            	break;
            }
            
            Command isCmdRemove = isRemoveShapeCommand(command);
        
            if(isCmdRemove == null) {
            	break;
            }
            
            if(isCmdRemove instanceof RemoveShapeCmd) {
            	command = undoStack.pop();
            	continue;
            } else {
            	break;
            }
        }
		return removeMoreThanOne;
	}
	
	private boolean doRemoveAllSelectedShapesRedoCmd(Command command) {
		boolean removeMoreThanOne = false;
		Command cmd = null;
		Command inputCommand = command;
		
		if(command instanceof UndoCmd ) { 
			cmd = ((UndoCmd) command).getCmd();
		} else {
			cmd = ((RedoCmd) command).getCmd();
		}
		
		while(cmd instanceof RemoveShapeCmd) {
        	command.execute();         
            frame.getBottomPanel().getDefaultListModel().addElement("Redo; " + command.toString());
            undoStack.push(command);
          
            
        	if(redoStack.isEmpty()) {
        		break;
        	}
            
            command = redoStack.peek();
            removeMoreThanOne = true;
            
            if(!inputCommand.getClass().equals(command.getClass())) {
            	break;
            }
            
            Command isCmdRemove = isRemoveShapeCommand(command);
            
            if(isCmdRemove == null) {
            	break;
            }
            
            if(isCmdRemove instanceof RemoveShapeCmd) {
            	command = redoStack.pop();
            	continue;
            } else {
            	break;
            }
        }
		
		return removeMoreThanOne;
	}
	
	public Stack<Command> getUndoStack() {
		return undoStack;
	}

	public Stack<Command> getRedoStack() {
		return redoStack;
	}
	
	public DrawingFrame getFrame() {
		return frame;
	}

	public void actionPerformedDrawing(ActionEvent e) {
		activeOperation=drawingOperation;
		
	}
	
	
}


