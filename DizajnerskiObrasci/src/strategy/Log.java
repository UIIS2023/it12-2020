package strategy;


import java.awt.Color;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

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
import mvc.DrawingFrame;
import mvc.DrawingModel;
import shapes.Circle;
import shapes.Donut;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;

public class Log implements SaveStrategy{


	private DrawingFrame frame;
	private DrawingModel model;
	
	private Stack<Command> redoStack;
	
	
	private List<Shape> tempListOfShapes;
	private List<Command> tempListOfCommands;
	
	public Log(DrawingFrame frame, DrawingModel model, Stack<Command> redoStack) {
		this.frame = frame;
		this.model = model;
		this.redoStack = redoStack;
		tempListOfShapes = new ArrayList<Shape>();
		tempListOfCommands = new ArrayList<Command>();
	}

	@Override
	public void save() {
		
		//CUVANJE LOGA U PLAIN TEXT FORMATU
		
		JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView());
		jFileChooser.setDialogTitle("Select a folder");
		jFileChooser.setCurrentDirectory(new File("C:\\Users\\Natasa\\Desktop\\logs"));
		String defaultFileName = "log";
		jFileChooser.setSelectedFile(new File(defaultFileName));

		if (jFileChooser.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getPath();

			File file = new File(path + ".txt"); 

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

				for (int index = 0; index < frame.getBottomPanel().getDefaultListModel().getSize(); index++) {
					bw.write(frame.getBottomPanel().getDefaultListModel().getElementAt(index) + "\n");
				}
				
				JOptionPane.showMessageDialog(null, "Log saved successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	//UCITAVANJE LOGA I KOMANDU PO KOMANDU ISCRTAVANJE CRTEZA
	public void load() {
		
		Stack<Command> loadStack = new Stack<>();
		Stack<Command> helper = new Stack<>();
		JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView());
		jFileChooser.setDialogTitle("Select a folder");
		jFileChooser.setCurrentDirectory(new File("C:\\Users\\Natasa\\Desktop\\logs"));
		String path = "";

		if (jFileChooser.showDialog(null, "Load") == JFileChooser.APPROVE_OPTION) {
			path = jFileChooser.getSelectedFile().getPath();
			
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
	            String fileLine;
	            while ((fileLine = br.readLine()) != null) {
	               // System.out.println(fileLine); // Process the row as needed
	                Command cmd = logParser(fileLine);
	                if(!(cmd==null)) {
	                	 helper.push(cmd);
	                }
	               
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			// Skidanje sa loadStacka i punjenje redo-a
			while(helper.size() != 0) {
	        	Command c = helper.pop();
	        	loadStack.push(c);
			}
			
			for(Command command : loadStack) {
	            redoStack.push(command);
	        }
		}
		
		
		
	}
	
	public Command logParser(String fileLine) {	 
											
		String[] parts = fileLine.split(";");
		
		// Ovde pocinje parsiranje kada je UNDO/REDO!!!
		if(parts[0].contains("Undo") || parts[0].contains("Redo")) {
			
			String commandUndoRedo = parts[0];
			String command = parts[1];
			String shapeString = parts[2];
			String positionAndSize = parts[3];
			String edgeColorString = parts[4];
			String innerColorString;
			
			if(shapeString.contains("Point") || shapeString.contains("Line")) {
				innerColorString = "";
			} else {
				innerColorString = parts[5]; 
			}
			
			// if-ologija za shape
			Shape shape = null;
			Shape modifiedShape = null;
			
			if(shapeString.contains("Point")) {
				 
				 positionAndSize = positionAndSize.replaceAll("[()]", "");
				 String[] coordinates = positionAndSize.split(",");
				 int x = Integer.parseInt(coordinates[0].trim());
				 int y = Integer.parseInt(coordinates[1].trim());
				 
				 String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 shape = new Point(x,y,edgeColor);
			}
			else if(shapeString.contains("Line")) {
				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String[] startPointEndPoint = positionAndSize.split("-->");
				String startPoint = startPointEndPoint[0];
				String endPoint = startPointEndPoint[1];
				String[] startPointSplit = startPoint.split(",");
				String[] endPointSplit = endPoint.split(",");
				int startPointX = Integer.parseInt(startPointSplit[0].trim());
				int startPointY = Integer.parseInt(startPointSplit[1].trim());
				int endPointX = Integer.parseInt(endPointSplit[0].trim());
				int endPointY = Integer.parseInt(endPointSplit[1].trim());
				Point point1 = new Point(startPointX, startPointY);
				Point point2 = new Point(endPointX, endPointY);
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
			
			     shape = new Line(point1,point2, edgeColor);
				
			}
			else if(shapeString.contains("Rectangle")) {
				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String [] positionAndSizeSplit = positionAndSize.split(",");
				String firstPart = positionAndSizeSplit[0];
				int upperLeftPointY = Integer.parseInt(positionAndSizeSplit[1].trim()); //to je zapravo secondPart jer samo taj broj ostane
				String thirdPart = positionAndSizeSplit[2];
				String fourthPart = positionAndSizeSplit[3];
				String[] firstPartSplit = firstPart.split(":");
				int upperLeftPointX = Integer.parseInt(firstPartSplit[1].trim());
				String[] thirdPartSplit = thirdPart.split(":");
				int width = Integer.parseInt(thirdPartSplit[1].trim());
				String[] fourthPartSplit = fourthPart.split(":");
				int height = Integer.parseInt(fourthPartSplit[1].trim());
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 String[] innerColorSplit = innerColorString.split("\\[");
				 String rgbRightSideInner = innerColorSplit[1];
				 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
				 String [] rgbSplitInner = rgbRightSideInner.split(",");
				 String rInner = rgbSplitInner[0];
				 String gInner = rgbSplitInner[1];
				 String bInner = rgbSplitInner[2];
				 String[] rEqualsSplitInner = rInner.split("=");
				 String[] gEqualsSplitInner = gInner.split("=");
				 String[] bEqualsSplitInner = bInner.split("=");
				 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
				 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
				 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
				 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
			
				 Point p = new Point(upperLeftPointX,upperLeftPointY);
			     shape = new Rectangle(p,width, height,edgeColor,innerColor);
				
			}
			else if(shapeString.contains("Circle")) {
				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String[] positionAndSizeSplit = positionAndSize.split(",");
				String firstPart = positionAndSizeSplit[0];
				int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
				String thirdPart = positionAndSizeSplit[2];
				String[] firstPartSplit = firstPart.split(":");
				int centerX = Integer.parseInt(firstPartSplit[1].trim());
				String[] thirdPartSplit = thirdPart.split(":");
				int radius = Integer.parseInt(thirdPartSplit[1].trim());
				
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 String[] innerColorSplit = innerColorString.split("\\[");
				 String rgbRightSideInner = innerColorSplit[1];
				 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
				 String [] rgbSplitInner = rgbRightSideInner.split(",");
				 String rInner = rgbSplitInner[0];
				 String gInner = rgbSplitInner[1];
				 String bInner = rgbSplitInner[2];
				 String[] rEqualsSplitInner = rInner.split("=");
				 String[] gEqualsSplitInner = gInner.split("=");
				 String[] bEqualsSplitInner = bInner.split("=");
				 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
				 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
				 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
				 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
			
				 Point p = new Point(centerX,centerY);
				 shape = new Circle(p,radius,edgeColor,innerColor);
				
			}
			else if(shapeString.contains("Donut")) {
				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String[] positionAndSizeSplit = positionAndSize.split(",");
				String firstPart = positionAndSizeSplit[0];
				int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
				String thirdPart = positionAndSizeSplit[2];
				String fourthPart = positionAndSizeSplit[3];
				String[] firstPartSplit = firstPart.split(":");
				int centerX = Integer.parseInt(firstPartSplit[1].trim());
				String[] thirdPartSplit = thirdPart.split(":");
				int radius =Integer.parseInt(thirdPartSplit[1].trim());
				String[] fourthPartSplit = fourthPart.split(":");
				int innerRadius = Integer.parseInt(fourthPartSplit[1].trim());
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 String[] innerColorSplit = innerColorString.split("\\[");
				 String rgbRightSideInner = innerColorSplit[1];
				 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
				 String [] rgbSplitInner = rgbRightSideInner.split(",");
				 String rInner = rgbSplitInner[0];
				 String gInner = rgbSplitInner[1];
				 String bInner = rgbSplitInner[2];
				 String[] rEqualsSplitInner = rInner.split("=");
				 String[] gEqualsSplitInner = gInner.split("=");
				 String[] bEqualsSplitInner = bInner.split("=");
				 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
				 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
				 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
				 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
			
				
				Point p = new Point(centerX,centerY);
				shape = new Donut(p,radius,innerRadius,edgeColor,innerColor);
				
			}
			else if(shapeString.contains("Hexagon")) {
				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String[] positionAndSizeSplit = positionAndSize.split(",");
				String firstPart = positionAndSizeSplit[0];
				int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
				String thirdPart = positionAndSizeSplit[2];
				String[] firstPartSplit = firstPart.split(":");
				int centerX = Integer.parseInt(firstPartSplit[1].trim());
				String[] thirdPartSplit = thirdPart.split(":");
				int radius = Integer.parseInt(thirdPartSplit[1].trim());
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 String[] innerColorSplit = innerColorString.split("\\[");
				 String rgbRightSideInner = innerColorSplit[1];
				 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
				 String [] rgbSplitInner = rgbRightSideInner.split(",");
				 String rInner = rgbSplitInner[0];
				 String gInner = rgbSplitInner[1];
				 String bInner = rgbSplitInner[2];
				 String[] rEqualsSplitInner = rInner.split("=");
				 String[] gEqualsSplitInner = gInner.split("=");
				 String[] bEqualsSplitInner = bInner.split("=");
				 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
				 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
				 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
				 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
			
				 
				 Point p = new Point(centerX,centerY);
				 shape = new HexagonAdapter(p,radius,edgeColor,innerColor);
				
				
				
			}
			
			
			// if-ologija za komandu 
			
			Command cmd = null;
			// TODO Ukoliko radimo ponovno dodavanje istog shape-a, ne treba ga dodati u listu
			if(command.contains("AddShape")) {
				boolean exist = false;
				for(Shape s : tempListOfShapes) {
					if(s.equals(shape)) { 
						exist = true;
						break;
					}
				}
				
				if(!exist) {
					tempListOfShapes.add(shape);
				}

				cmd = new AddShapeCmd(model,shape);
				
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
				
			
			} else if(command.contains("DeleteShape")) {
				for(Shape s : tempListOfShapes) {
					 if(s instanceof HexagonAdapter) {
						 if(shape instanceof HexagonAdapter) {
							 HexagonAdapter ha = (HexagonAdapter)s;
							 HexagonAdapter ha2 = (HexagonAdapter) shape;
							 if(ha.equals(ha2)) {
								 shape = s;
								 break;
							 }
						 }
					 } else {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
				 }
				cmd = new RemoveShapeCmd(model,shape);
				
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
			
			} else if(command.contains("Modify")) {
				//parts[5] ce biti TO
				
				// Odavde krece parsiranje drugog oblika kod MODIFY funkcionalnosti
				
				if(shapeString.contains("Point")) {
					shapeString = parts[6];
					positionAndSize = parts[7];
					edgeColorString = parts[8];
					 
					 positionAndSize = positionAndSize.replaceAll("[()]", "");
					 String[] coordinates = positionAndSize.split(",");
					 int x = Integer.parseInt(coordinates[0].trim());
					 int y = Integer.parseInt(coordinates[1].trim());
					 
					 String[] edgeColorSplit = edgeColorString.split("\\[");
					 String rgbRightSide = edgeColorSplit[1];
					 rgbRightSide = rgbRightSide.replaceAll("]", "");
					 String [] rgbSplit = rgbRightSide.split(",");
					 String r = rgbSplit[0];
					 String g = rgbSplit[1];
					 String b = rgbSplit[2];
					 String[] rEqualsSplit = r.split("=");
					 String[] gEqualsSplit = g.split("=");
					 String[] bEqualsSplit = b.split("=");
					 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
					 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
					 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
					 Color edgeColor = new Color(rValue,gValue,bValue);
					 
					 modifiedShape = new Point(x,y,edgeColor);
					 
					 for(Shape s : tempListOfShapes) {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
					 cmd = new UpdatePointCmd((Point)shape, (Point)modifiedShape);
					
					// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
						boolean commandExists = false;
						for(Command c : tempListOfCommands) {
							if(c.toString().equals(cmd.toString())) {
								commandExists = true;
								cmd = c;
								break;
							}
						}
						
						if(!commandExists) {
							tempListOfCommands.add(cmd);
						}
					 
				}
				else if(shapeString.contains("Line")) {
					shapeString = parts[6];
					positionAndSize = parts[7];
					edgeColorString = parts[8];

					positionAndSize = positionAndSize.replaceAll("[()]", "");
					String[] startPointEndPoint = positionAndSize.split("-->");
					String startPoint = startPointEndPoint[0];
					String endPoint = startPointEndPoint[1];
					String[] startPointSplit = startPoint.split(",");
					String[] endPointSplit = endPoint.split(",");
					int startPointX = Integer.parseInt(startPointSplit[0].trim());
					int startPointY = Integer.parseInt(startPointSplit[1].trim());
					int endPointX = Integer.parseInt(endPointSplit[0].trim());
					int endPointY = Integer.parseInt(endPointSplit[1].trim());
					Point point1 = new Point(startPointX, startPointY);
					Point point2 = new Point(endPointX, endPointY);
					
					String[] edgeColorSplit = edgeColorString.split("\\[");
					 String rgbRightSide = edgeColorSplit[1];
					 rgbRightSide = rgbRightSide.replaceAll("]", "");
					 String [] rgbSplit = rgbRightSide.split(",");
					 String r = rgbSplit[0];
					 String g = rgbSplit[1];
					 String b = rgbSplit[2];
					 String[] rEqualsSplit = r.split("=");
					 String[] gEqualsSplit = g.split("=");
					 String[] bEqualsSplit = b.split("=");
					 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
					 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
					 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
					 Color edgeColor = new Color(rValue,gValue,bValue);
				
					 modifiedShape = new Line(point1,point2, edgeColor);
					 
					 for(Shape s : tempListOfShapes) {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
					 cmd = new UpdateLineCmd((Line)shape, (Line)modifiedShape);
					
					// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
						boolean commandExists = false;
						for(Command c : tempListOfCommands) {
							if(c.toString().equals(cmd.toString())) {
								commandExists = true;
								cmd = c;
								break;
							}
						}
						
						if(!commandExists) {
							tempListOfCommands.add(cmd);
						}
					
				}
				else if(shapeString.contains("Rectangle")) {
					shapeString = parts[7];
					positionAndSize = parts[8];
					edgeColorString = parts[9];
					innerColorString = parts[10];
					
					
					positionAndSize = positionAndSize.replaceAll("[()]", "");
					String [] positionAndSizeSplit = positionAndSize.split(",");
					String firstPart = positionAndSizeSplit[0];
					int upperLeftPointY = Integer.parseInt(positionAndSizeSplit[1].trim()); //to je zapravo secondPart jer samo taj broj ostane
					String thirdPart = positionAndSizeSplit[2];
					String fourthPart = positionAndSizeSplit[3];
					String[] firstPartSplit = firstPart.split(":");
					int upperLeftPointX = Integer.parseInt(firstPartSplit[1].trim());
					String[] thirdPartSplit = thirdPart.split(":");
					int width = Integer.parseInt(thirdPartSplit[1].trim());
					String[] fourthPartSplit = fourthPart.split(":");
					int height = Integer.parseInt(fourthPartSplit[1].trim());
					
					String[] edgeColorSplit = edgeColorString.split("\\[");
					 String rgbRightSide = edgeColorSplit[1];
					 rgbRightSide = rgbRightSide.replaceAll("]", "");
					 String [] rgbSplit = rgbRightSide.split(",");
					 String r = rgbSplit[0];
					 String g = rgbSplit[1];
					 String b = rgbSplit[2];
					 String[] rEqualsSplit = r.split("=");
					 String[] gEqualsSplit = g.split("=");
					 String[] bEqualsSplit = b.split("=");
					 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
					 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
					 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
					 Color edgeColor = new Color(rValue,gValue,bValue);
					 
					 String[] innerColorSplit = innerColorString.split("\\[");
					 String rgbRightSideInner = innerColorSplit[1];
					 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
					 String [] rgbSplitInner = rgbRightSideInner.split(",");
					 String rInner = rgbSplitInner[0];
					 String gInner = rgbSplitInner[1];
					 String bInner = rgbSplitInner[2];
					 String[] rEqualsSplitInner = rInner.split("=");
					 String[] gEqualsSplitInner = gInner.split("=");
					 String[] bEqualsSplitInner = bInner.split("=");
					 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
					 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
					 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
					 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
				
					 Point p = new Point(upperLeftPointX,upperLeftPointY);
					 modifiedShape = new Rectangle(p,width, height,edgeColor,innerColor);
					 
					 for(Shape s : tempListOfShapes) {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
					 cmd = new UpdateRectangleCmd((Rectangle)shape, (Rectangle)modifiedShape);
				
					// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
						boolean commandExists = false;
						for(Command c : tempListOfCommands) {
							if(c.toString().equals(cmd.toString())) {
								commandExists = true;
								cmd = c;
								break;
							}
						}
						
						if(!commandExists) {
							tempListOfCommands.add(cmd);
						}
					
				}
				else if(shapeString.contains("Circle")) {
					shapeString = parts[7];
					positionAndSize = parts[8];
					edgeColorString = parts[9];
					innerColorString = parts[10];
					
					positionAndSize = positionAndSize.replaceAll("[()]", "");
					String[] positionAndSizeSplit = positionAndSize.split(",");
					String firstPart = positionAndSizeSplit[0];
					int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
					String thirdPart = positionAndSizeSplit[2];
					String[] firstPartSplit = firstPart.split(":");
					int centerX = Integer.parseInt(firstPartSplit[1].trim());
					String[] thirdPartSplit = thirdPart.split(":");
					int radius = Integer.parseInt(thirdPartSplit[1].trim());
					
					
					String[] edgeColorSplit = edgeColorString.split("\\[");
					 String rgbRightSide = edgeColorSplit[1];
					 rgbRightSide = rgbRightSide.replaceAll("]", "");
					 String [] rgbSplit = rgbRightSide.split(",");
					 String r = rgbSplit[0];
					 String g = rgbSplit[1];
					 String b = rgbSplit[2];
					 String[] rEqualsSplit = r.split("=");
					 String[] gEqualsSplit = g.split("=");
					 String[] bEqualsSplit = b.split("=");
					 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
					 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
					 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
					 Color edgeColor = new Color(rValue,gValue,bValue);
					 
					 String[] innerColorSplit = innerColorString.split("\\[");
					 String rgbRightSideInner = innerColorSplit[1];
					 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
					 String [] rgbSplitInner = rgbRightSideInner.split(",");
					 String rInner = rgbSplitInner[0];
					 String gInner = rgbSplitInner[1];
					 String bInner = rgbSplitInner[2];
					 String[] rEqualsSplitInner = rInner.split("=");
					 String[] gEqualsSplitInner = gInner.split("=");
					 String[] bEqualsSplitInner = bInner.split("=");
					 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
					 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
					 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
					 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
				
					 Point p = new Point(centerX,centerY);
					 modifiedShape = new Circle(p,radius,edgeColor,innerColor);
					 
					 for(Shape s : tempListOfShapes) {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
					 cmd = new UpdateCircleCmd((Circle)shape, (Circle)modifiedShape);
					
					// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
						boolean commandExists = false;
						for(Command c : tempListOfCommands) {
							if(c.toString().equals(cmd.toString())) {
								commandExists = true;
								cmd = c;
								break;
							}
						}
						
						if(!commandExists) {
							tempListOfCommands.add(cmd);
						}
					 
					
				}
				else if(shapeString.contains("Donut")) {
					shapeString = parts[7];
					positionAndSize = parts[8];
					edgeColorString = parts[9];
					innerColorString = parts[10];
					
					
					positionAndSize = positionAndSize.replaceAll("[()]", "");
					String[] positionAndSizeSplit = positionAndSize.split(",");
					String firstPart = positionAndSizeSplit[0];
					int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
					String thirdPart = positionAndSizeSplit[2];
					String fourthPart = positionAndSizeSplit[3];
					String[] firstPartSplit = firstPart.split(":");
					int centerX = Integer.parseInt(firstPartSplit[1].trim());
					String[] thirdPartSplit = thirdPart.split(":");
					int radius =Integer.parseInt(thirdPartSplit[1].trim());
					String[] fourthPartSplit = fourthPart.split(":");
					int innerRadius = Integer.parseInt(fourthPartSplit[1].trim());
					
					String[] edgeColorSplit = edgeColorString.split("\\[");
					 String rgbRightSide = edgeColorSplit[1];
					 rgbRightSide = rgbRightSide.replaceAll("]", "");
					 String [] rgbSplit = rgbRightSide.split(",");
					 String r = rgbSplit[0];
					 String g = rgbSplit[1];
					 String b = rgbSplit[2];
					 String[] rEqualsSplit = r.split("=");
					 String[] gEqualsSplit = g.split("=");
					 String[] bEqualsSplit = b.split("=");
					 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
					 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
					 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
					 Color edgeColor = new Color(rValue,gValue,bValue);
					 
					 String[] innerColorSplit = innerColorString.split("\\[");
					 String rgbRightSideInner = innerColorSplit[1];
					 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
					 String [] rgbSplitInner = rgbRightSideInner.split(",");
					 String rInner = rgbSplitInner[0];
					 String gInner = rgbSplitInner[1];
					 String bInner = rgbSplitInner[2];
					 String[] rEqualsSplitInner = rInner.split("=");
					 String[] gEqualsSplitInner = gInner.split("=");
					 String[] bEqualsSplitInner = bInner.split("=");
					 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
					 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
					 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
					 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
				
					
					Point p = new Point(centerX,centerY);
					modifiedShape = new Donut(p,radius,innerRadius,edgeColor,innerColor);
					
					 for(Shape s : tempListOfShapes) {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
					cmd = new UpdateDonutCmd((Donut)shape, (Donut)modifiedShape);
					boolean commandExists = false;
					for(Command c : tempListOfCommands) {
						if(c.toString().equals(cmd.toString())) {
							commandExists = true;
							cmd = c;
							break;
						}
					}
					
					if(!commandExists) {
						tempListOfCommands.add(cmd);
					}
					
				}
				else if(shapeString.contains("Hexagon")) {
					shapeString = parts[7];
					positionAndSize = parts[8];
					edgeColorString = parts[9];
					innerColorString = parts[10];
					
					
					positionAndSize = positionAndSize.replaceAll("[()]", "");
					String[] positionAndSizeSplit = positionAndSize.split(",");
					String firstPart = positionAndSizeSplit[0];
					int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
					String thirdPart = positionAndSizeSplit[2];
					String[] firstPartSplit = firstPart.split(":");
					int centerX = Integer.parseInt(firstPartSplit[1].trim());
					String[] thirdPartSplit = thirdPart.split(":");
					int radius = Integer.parseInt(thirdPartSplit[1].trim());
					
					String[] edgeColorSplit = edgeColorString.split("\\[");
					 String rgbRightSide = edgeColorSplit[1];
					 rgbRightSide = rgbRightSide.replaceAll("]", "");
					 String [] rgbSplit = rgbRightSide.split(",");
					 String r = rgbSplit[0];
					 String g = rgbSplit[1];
					 String b = rgbSplit[2];
					 String[] rEqualsSplit = r.split("=");
					 String[] gEqualsSplit = g.split("=");
					 String[] bEqualsSplit = b.split("=");
					 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
					 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
					 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
					 Color edgeColor = new Color(rValue,gValue,bValue);
					 
					 String[] innerColorSplit = innerColorString.split("\\[");
					 String rgbRightSideInner = innerColorSplit[1];
					 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
					 String [] rgbSplitInner = rgbRightSideInner.split(",");
					 String rInner = rgbSplitInner[0];
					 String gInner = rgbSplitInner[1];
					 String bInner = rgbSplitInner[2];
					 String[] rEqualsSplitInner = rInner.split("=");
					 String[] gEqualsSplitInner = gInner.split("=");
					 String[] bEqualsSplitInner = bInner.split("=");
					 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
					 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
					 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
					 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
				
					 
					 Point p = new Point(centerX,centerY);
					 modifiedShape = new HexagonAdapter(p,radius,edgeColor,innerColor);
					 
					 for(Shape s : tempListOfShapes) {
						 if(s instanceof HexagonAdapter) {
							 HexagonAdapter ha = (HexagonAdapter)s;
							 HexagonAdapter ha2 = (HexagonAdapter) shape;
							 if(ha.equals(ha2)) {
								 shape = s;
								 break;
							 }
						 }
						 
					 }
					 
					 cmd = new UpdateHexagonCmd((HexagonAdapter)shape, (HexagonAdapter)modifiedShape);
				
					// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
						boolean commandExists = false;
						for(Command c : tempListOfCommands) {
							if(c.toString().equals(cmd.toString())) {
								commandExists = true;
								cmd = c;
								break;
							}
						}
						
						if(!commandExists) {
							tempListOfCommands.add(cmd);
						}
						
					
				}
				
			
				
			} else if(command.contains("SelectShape")) {
				for(Shape s : tempListOfShapes) {
					 if(s instanceof HexagonAdapter) {
						 if(shape instanceof HexagonAdapter) {
							 HexagonAdapter ha = (HexagonAdapter)s;
							 HexagonAdapter ha2 = (HexagonAdapter) shape;
							 if(ha.equals(ha2)) {
								 shape = s;
								 break;
							 }
						 }
					 } else {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
				 }
				cmd = new SelectShapeCmd(model,shape); 
				
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
			
			} else if(command.contains("DeselectShape")) {
				for(Shape s : tempListOfShapes) {
					 if(s instanceof HexagonAdapter) {
						 if(shape instanceof HexagonAdapter) {
							 HexagonAdapter ha = (HexagonAdapter)s;
							 HexagonAdapter ha2 = (HexagonAdapter) shape;
							 if(ha.equals(ha2)) {
								 shape = s;
								 break;
							 }
						 }
					 } else {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
				 }
				cmd = new DeselectShapeCmd(model,shape);
				
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
				
			} else if(command.contains("DeselectAllShapes")) {
				for(Shape s : tempListOfShapes) {
					 if(s instanceof HexagonAdapter) {
						 if(shape instanceof HexagonAdapter) {
							 HexagonAdapter ha = (HexagonAdapter)s;
							 HexagonAdapter ha2 = (HexagonAdapter) shape;
							 if(ha.equals(ha2)) {
								 shape = s;
								 break;
							 }
						 }
					 } else {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
				 }
				cmd = new DeselectAllShapesCmd(model,shape); 
				
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
				
			} else if(command.contains("BringToFront")) {
				for(Shape s : tempListOfShapes) {
					 if(s instanceof HexagonAdapter) {
						 if(shape instanceof HexagonAdapter) {
							 HexagonAdapter ha = (HexagonAdapter)s;
							 HexagonAdapter ha2 = (HexagonAdapter) shape;
							 if(ha.equals(ha2)) {
								 shape = s;
								 break;
							 }
						 }
					 } else {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
				 }
				cmd = new BringToFrontCmd(shape, model.getShapes()); 
				
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
				
				
			} else if(command.contains("BringToBack")) {
				for(Shape s : tempListOfShapes) {
					 if(s instanceof HexagonAdapter) {
						 if(shape instanceof HexagonAdapter) {
							 HexagonAdapter ha = (HexagonAdapter)s;
							 HexagonAdapter ha2 = (HexagonAdapter) shape;
							 if(ha.equals(ha2)) {
								 shape = s;
								 break;
							 }
						 }
					 } else {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
				 }
				cmd = new BringToBackCmd(shape, model.getShapes()); 
				
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
				
			} else if(command.contains("ToFront")) {
				for(Shape s : tempListOfShapes) {
					 if(s instanceof HexagonAdapter) {
						 if(shape instanceof HexagonAdapter) {
							 HexagonAdapter ha = (HexagonAdapter)s;
							 HexagonAdapter ha2 = (HexagonAdapter) shape;
							 if(ha.equals(ha2)) {
								 shape = s;
								 break;
							 }
						 }
					 } else {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
				 }
				cmd = new ToFrontCmd(shape, model.getShapes()); 
				
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
			
			} else if(command.contains("ToBack")) {
				for(Shape s : tempListOfShapes) {
					 if(s instanceof HexagonAdapter) {
						 if(shape instanceof HexagonAdapter) {
							 HexagonAdapter ha = (HexagonAdapter)s;
							 HexagonAdapter ha2 = (HexagonAdapter) shape;
							 if(ha.equals(ha2)) {
								 shape = s;
								 break;
							 }
						 }
					 } else {
						 if(s.equals(shape)) {
							 shape = s;
							 break;
						 }
					 }
					 
				 }
				cmd = new ToBackCmd(shape, model.getShapes()); 
				
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
				
			} 
			
			if(commandUndoRedo.contains("Undo")) {
				Command undoCommand = new UndoCmd(cmd);
				frame.getBottomPanel().getDefaultListModel().addElement("Undo; " + cmd.toString());
				return undoCommand;
			} else {
				Command redoCommand = new RedoCmd(cmd);
				frame.getBottomPanel().getDefaultListModel().addElement("Redo; " + cmd.toString());
				return redoCommand;
			}
			
			
		} else {
			
		// Ovde pocinje parsiranje kada nije UNDO/REDO!!!
		
		// Uvek ce biti 5 clanova iz loga
		// Osim ukoliko je tacka ili linija. Kod njih nece biti parts[4]
		String command = parts[0];
		String shapeString = parts[1];
		String positionAndSize = parts[2];
		String edgeColorString = parts[3];
		String innerColorString;
		
		if(shapeString.contains("Point") || shapeString.contains("Line")) {
			innerColorString = "";
		} else {
			innerColorString = parts[4]; 
		}
		

		
		
		
		// if-ologija za shape
		Shape shape = null;
		Shape modifiedShape = null;
		if(shapeString.contains("Point")) {
			 
			 positionAndSize = positionAndSize.replaceAll("[()]", "");
			 String[] coordinates = positionAndSize.split(",");
			 int x = Integer.parseInt(coordinates[0].trim());
			 int y = Integer.parseInt(coordinates[1].trim());
			 
			 String[] edgeColorSplit = edgeColorString.split("\\[");
			 String rgbRightSide = edgeColorSplit[1];
			 rgbRightSide = rgbRightSide.replaceAll("]", "");
			 String [] rgbSplit = rgbRightSide.split(",");
			 String r = rgbSplit[0];
			 String g = rgbSplit[1];
			 String b = rgbSplit[2];
			 String[] rEqualsSplit = r.split("=");
			 String[] gEqualsSplit = g.split("=");
			 String[] bEqualsSplit = b.split("=");
			 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
			 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
			 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
			 Color edgeColor = new Color(rValue,gValue,bValue);
			 
			 shape = new Point(x,y,edgeColor);
		}
		else if(shapeString.contains("Line")) {
			positionAndSize = positionAndSize.replaceAll("[()]", "");
			String[] startPointEndPoint = positionAndSize.split("-->");
			String startPoint = startPointEndPoint[0];
			String endPoint = startPointEndPoint[1];
			String[] startPointSplit = startPoint.split(",");
			String[] endPointSplit = endPoint.split(",");
			int startPointX = Integer.parseInt(startPointSplit[0].trim());
			int startPointY = Integer.parseInt(startPointSplit[1].trim());
			int endPointX = Integer.parseInt(endPointSplit[0].trim());
			int endPointY = Integer.parseInt(endPointSplit[1].trim());
			Point point1 = new Point(startPointX, startPointY);
			Point point2 = new Point(endPointX, endPointY);
			
			String[] edgeColorSplit = edgeColorString.split("\\[");
			 String rgbRightSide = edgeColorSplit[1];
			 rgbRightSide = rgbRightSide.replaceAll("]", "");
			 String [] rgbSplit = rgbRightSide.split(",");
			 String r = rgbSplit[0];
			 String g = rgbSplit[1];
			 String b = rgbSplit[2];
			 String[] rEqualsSplit = r.split("=");
			 String[] gEqualsSplit = g.split("=");
			 String[] bEqualsSplit = b.split("=");
			 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
			 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
			 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
			 Color edgeColor = new Color(rValue,gValue,bValue);
		
		     shape = new Line(point1,point2, edgeColor);
			
		}
		else if(shapeString.contains("Rectangle")) {
			positionAndSize = positionAndSize.replaceAll("[()]", "");
			String [] positionAndSizeSplit = positionAndSize.split(",");
			String firstPart = positionAndSizeSplit[0];
			int upperLeftPointY = Integer.parseInt(positionAndSizeSplit[1].trim()); //to je zapravo secondPart jer samo taj broj ostane
			String thirdPart = positionAndSizeSplit[2];
			String fourthPart = positionAndSizeSplit[3];
			String[] firstPartSplit = firstPart.split(":");
			int upperLeftPointX = Integer.parseInt(firstPartSplit[1].trim());
			String[] thirdPartSplit = thirdPart.split(":");
			int width = Integer.parseInt(thirdPartSplit[1].trim());
			String[] fourthPartSplit = fourthPart.split(":");
			int height = Integer.parseInt(fourthPartSplit[1].trim());
			
			String[] edgeColorSplit = edgeColorString.split("\\[");
			 String rgbRightSide = edgeColorSplit[1];
			 rgbRightSide = rgbRightSide.replaceAll("]", "");
			 String [] rgbSplit = rgbRightSide.split(",");
			 String r = rgbSplit[0];
			 String g = rgbSplit[1];
			 String b = rgbSplit[2];
			 String[] rEqualsSplit = r.split("=");
			 String[] gEqualsSplit = g.split("=");
			 String[] bEqualsSplit = b.split("=");
			 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
			 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
			 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
			 Color edgeColor = new Color(rValue,gValue,bValue);
			 
			 String[] innerColorSplit = innerColorString.split("\\[");
			 String rgbRightSideInner = innerColorSplit[1];
			 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
			 String [] rgbSplitInner = rgbRightSideInner.split(",");
			 String rInner = rgbSplitInner[0];
			 String gInner = rgbSplitInner[1];
			 String bInner = rgbSplitInner[2];
			 String[] rEqualsSplitInner = rInner.split("=");
			 String[] gEqualsSplitInner = gInner.split("=");
			 String[] bEqualsSplitInner = bInner.split("=");
			 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
			 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
			 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
			 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
		
			 Point p = new Point(upperLeftPointX,upperLeftPointY);
		     shape = new Rectangle(p,width, height,edgeColor,innerColor);
			
		}
		else if(shapeString.contains("Circle")) {
			positionAndSize = positionAndSize.replaceAll("[()]", "");
			String[] positionAndSizeSplit = positionAndSize.split(",");
			String firstPart = positionAndSizeSplit[0];
			int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
			String thirdPart = positionAndSizeSplit[2];
			String[] firstPartSplit = firstPart.split(":");
			int centerX = Integer.parseInt(firstPartSplit[1].trim());
			String[] thirdPartSplit = thirdPart.split(":");
			int radius = Integer.parseInt(thirdPartSplit[1].trim());
			
			
			String[] edgeColorSplit = edgeColorString.split("\\[");
			 String rgbRightSide = edgeColorSplit[1];
			 rgbRightSide = rgbRightSide.replaceAll("]", "");
			 String [] rgbSplit = rgbRightSide.split(",");
			 String r = rgbSplit[0];
			 String g = rgbSplit[1];
			 String b = rgbSplit[2];
			 String[] rEqualsSplit = r.split("=");
			 String[] gEqualsSplit = g.split("=");
			 String[] bEqualsSplit = b.split("=");
			 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
			 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
			 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
			 Color edgeColor = new Color(rValue,gValue,bValue);
			 
			 String[] innerColorSplit = innerColorString.split("\\[");
			 String rgbRightSideInner = innerColorSplit[1];
			 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
			 String [] rgbSplitInner = rgbRightSideInner.split(",");
			 String rInner = rgbSplitInner[0];
			 String gInner = rgbSplitInner[1];
			 String bInner = rgbSplitInner[2];
			 String[] rEqualsSplitInner = rInner.split("=");
			 String[] gEqualsSplitInner = gInner.split("=");
			 String[] bEqualsSplitInner = bInner.split("=");
			 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
			 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
			 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
			 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
		
			 Point p = new Point(centerX,centerY);
			 shape = new Circle(p,radius,edgeColor,innerColor);
			
		}
		else if(shapeString.contains("Donut")) {
			positionAndSize = positionAndSize.replaceAll("[()]", "");
			String[] positionAndSizeSplit = positionAndSize.split(",");
			String firstPart = positionAndSizeSplit[0];
			int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
			String thirdPart = positionAndSizeSplit[2];
			String fourthPart = positionAndSizeSplit[3];
			String[] firstPartSplit = firstPart.split(":");
			int centerX = Integer.parseInt(firstPartSplit[1].trim());
			String[] thirdPartSplit = thirdPart.split(":");
			int radius =Integer.parseInt(thirdPartSplit[1].trim());
			String[] fourthPartSplit = fourthPart.split(":");
			int innerRadius = Integer.parseInt(fourthPartSplit[1].trim());
			
			String[] edgeColorSplit = edgeColorString.split("\\[");
			 String rgbRightSide = edgeColorSplit[1];
			 rgbRightSide = rgbRightSide.replaceAll("]", "");
			 String [] rgbSplit = rgbRightSide.split(",");
			 String r = rgbSplit[0];
			 String g = rgbSplit[1];
			 String b = rgbSplit[2];
			 String[] rEqualsSplit = r.split("=");
			 String[] gEqualsSplit = g.split("=");
			 String[] bEqualsSplit = b.split("=");
			 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
			 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
			 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
			 Color edgeColor = new Color(rValue,gValue,bValue);
			 
			 String[] innerColorSplit = innerColorString.split("\\[");
			 String rgbRightSideInner = innerColorSplit[1];
			 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
			 String [] rgbSplitInner = rgbRightSideInner.split(",");
			 String rInner = rgbSplitInner[0];
			 String gInner = rgbSplitInner[1];
			 String bInner = rgbSplitInner[2];
			 String[] rEqualsSplitInner = rInner.split("=");
			 String[] gEqualsSplitInner = gInner.split("=");
			 String[] bEqualsSplitInner = bInner.split("=");
			 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
			 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
			 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
			 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
		
			
			Point p = new Point(centerX,centerY);
			shape = new Donut(p,radius,innerRadius,edgeColor,innerColor);
			
		}
		else if(shapeString.contains("Hexagon")) {
			positionAndSize = positionAndSize.replaceAll("[()]", "");
			String[] positionAndSizeSplit = positionAndSize.split(",");
			String firstPart = positionAndSizeSplit[0];
			int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
			String thirdPart = positionAndSizeSplit[2];
			String[] firstPartSplit = firstPart.split(":");
			int centerX = Integer.parseInt(firstPartSplit[1].trim());
			String[] thirdPartSplit = thirdPart.split(":");
			int radius = Integer.parseInt(thirdPartSplit[1].trim());
			
			String[] edgeColorSplit = edgeColorString.split("\\[");
			 String rgbRightSide = edgeColorSplit[1];
			 rgbRightSide = rgbRightSide.replaceAll("]", "");
			 String [] rgbSplit = rgbRightSide.split(",");
			 String r = rgbSplit[0];
			 String g = rgbSplit[1];
			 String b = rgbSplit[2];
			 String[] rEqualsSplit = r.split("=");
			 String[] gEqualsSplit = g.split("=");
			 String[] bEqualsSplit = b.split("=");
			 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
			 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
			 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
			 Color edgeColor = new Color(rValue,gValue,bValue);
			 
			 String[] innerColorSplit = innerColorString.split("\\[");
			 String rgbRightSideInner = innerColorSplit[1];
			 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
			 String [] rgbSplitInner = rgbRightSideInner.split(",");
			 String rInner = rgbSplitInner[0];
			 String gInner = rgbSplitInner[1];
			 String bInner = rgbSplitInner[2];
			 String[] rEqualsSplitInner = rInner.split("=");
			 String[] gEqualsSplitInner = gInner.split("=");
			 String[] bEqualsSplitInner = bInner.split("=");
			 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
			 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
			 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
			 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
		
			 
			 Point p = new Point(centerX,centerY);
			 shape = new HexagonAdapter(p,radius,edgeColor,innerColor);
			
			
			
		}
		
		
		// if-ologija za komandu 
		
		Command cmd = null;
		
		if(command.contains("AddShape")) {
			boolean exist = false;
			for(Shape s : tempListOfShapes) {
				if(s.equals(shape)) { 
					exist = true;
					break;
				}
			}
			
			if(!exist) {
				tempListOfShapes.add(shape);
			}
			cmd = new AddShapeCmd(model,shape);
			
			// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
			boolean commandExists = false;
			for(Command c : tempListOfCommands) {
				if(c.toString().equals(cmd.toString())) {
					commandExists = true;
					cmd = c;
					break;
				}
			}
			
			if(!commandExists) {
				tempListOfCommands.add(cmd);
			}
			
			//cmd.execute();
			frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
		    return cmd;
		} else if(command.contains("DeleteShape")) {
			for(Shape s : tempListOfShapes) {
				 if(s instanceof HexagonAdapter) {
					 if(shape instanceof HexagonAdapter) {
						 HexagonAdapter ha = (HexagonAdapter)s;
						 HexagonAdapter ha2 = (HexagonAdapter) shape;
						 if(ha.equals(ha2)) {
							 shape = s;
							 break;
						 }
					 }
				 } else {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
			 }
			cmd = new RemoveShapeCmd(model,shape);
			
			// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
			boolean commandExists = false;
			for(Command c : tempListOfCommands) {
				if(c.toString().equals(cmd.toString())) {
					commandExists = true;
					cmd = c;
					break;
				}
			}
			
			if(!commandExists) {
				tempListOfCommands.add(cmd);
			}
			
			frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
			return cmd;
		} else if(command.contains("Modify")) {
			//parts[5] ce biti TO
			
		
			
			// Odavde krece parsiranje drugog oblika kod MODIFY funkcionalnosti
			
			if(shapeString.contains("Point")) {
				shapeString = parts[5];
				positionAndSize = parts[6];
				edgeColorString = parts[7];
				 
				 positionAndSize = positionAndSize.replaceAll("[()]", "");
				 String[] coordinates = positionAndSize.split(",");
				 int x = Integer.parseInt(coordinates[0].trim());
				 int y = Integer.parseInt(coordinates[1].trim());
				 
				 String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 modifiedShape = new Point(x,y,edgeColor);
				 
				 for(Shape s : tempListOfShapes) {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
				 
				 cmd = new UpdatePointCmd((Point)shape, (Point)modifiedShape);
				 
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}
				 
				 frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
				 return cmd;
				 
				 
			}
			else if(shapeString.contains("Line")) {
				shapeString = parts[5];
				positionAndSize = parts[6];
				edgeColorString = parts[7];

				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String[] startPointEndPoint = positionAndSize.split("-->");
				String startPoint = startPointEndPoint[0];
				String endPoint = startPointEndPoint[1];
				String[] startPointSplit = startPoint.split(",");
				String[] endPointSplit = endPoint.split(",");
				int startPointX = Integer.parseInt(startPointSplit[0].trim());
				int startPointY = Integer.parseInt(startPointSplit[1].trim());
				int endPointX = Integer.parseInt(endPointSplit[0].trim());
				int endPointY = Integer.parseInt(endPointSplit[1].trim());
				Point point1 = new Point(startPointX, startPointY);
				Point point2 = new Point(endPointX, endPointY);
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
			
				 modifiedShape = new Line(point1,point2, edgeColor);
				 
				 for(Shape s : tempListOfShapes) {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
				 
				 cmd = new UpdateLineCmd((Line)shape, (Line)modifiedShape);
				 
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
					boolean commandExists = false;
					for(Command c : tempListOfCommands) {
						if(c.toString().equals(cmd.toString())) {
							commandExists = true;
							cmd = c;
							break;
						}
					}
					
					if(!commandExists) {
						tempListOfCommands.add(cmd);
					}
				 
				 frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
				 return cmd;
				
			}
			else if(shapeString.contains("Rectangle")) {
				shapeString = parts[6];
				positionAndSize = parts[7];
				edgeColorString = parts[8];
				innerColorString = parts[9];
				
				
				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String [] positionAndSizeSplit = positionAndSize.split(",");
				String firstPart = positionAndSizeSplit[0];
				int upperLeftPointY = Integer.parseInt(positionAndSizeSplit[1].trim()); //to je zapravo secondPart jer samo taj broj ostane
				String thirdPart = positionAndSizeSplit[2];
				String fourthPart = positionAndSizeSplit[3];
				String[] firstPartSplit = firstPart.split(":");
				int upperLeftPointX = Integer.parseInt(firstPartSplit[1].trim());
				String[] thirdPartSplit = thirdPart.split(":");
				int width = Integer.parseInt(thirdPartSplit[1].trim());
				String[] fourthPartSplit = fourthPart.split(":");
				int height = Integer.parseInt(fourthPartSplit[1].trim());
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 String[] innerColorSplit = innerColorString.split("\\[");
				 String rgbRightSideInner = innerColorSplit[1];
				 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
				 String [] rgbSplitInner = rgbRightSideInner.split(",");
				 String rInner = rgbSplitInner[0];
				 String gInner = rgbSplitInner[1];
				 String bInner = rgbSplitInner[2];
				 String[] rEqualsSplitInner = rInner.split("=");
				 String[] gEqualsSplitInner = gInner.split("=");
				 String[] bEqualsSplitInner = bInner.split("=");
				 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
				 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
				 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
				 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
			
				 Point p = new Point(upperLeftPointX,upperLeftPointY);
				 modifiedShape = new Rectangle(p,width, height,edgeColor,innerColor);
				 
				 for(Shape s : tempListOfShapes) {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
				 
				 cmd = new UpdateRectangleCmd((Rectangle)shape, (Rectangle)modifiedShape);
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
					boolean commandExists = false;
					for(Command c : tempListOfCommands) {
						if(c.toString().equals(cmd.toString())) {
							commandExists = true;
							cmd = c;
							break;
						}
					}
					
					if(!commandExists) {
						tempListOfCommands.add(cmd);
					}
				 
				 frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
				 return cmd;
				
			}
			else if(shapeString.contains("Circle")) {
				shapeString = parts[6];
				positionAndSize = parts[7];
				edgeColorString = parts[8];
				innerColorString = parts[9];
				
				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String[] positionAndSizeSplit = positionAndSize.split(",");
				String firstPart = positionAndSizeSplit[0];
				int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
				String thirdPart = positionAndSizeSplit[2];
				String[] firstPartSplit = firstPart.split(":");
				int centerX = Integer.parseInt(firstPartSplit[1].trim());
				String[] thirdPartSplit = thirdPart.split(":");
				int radius = Integer.parseInt(thirdPartSplit[1].trim());
				
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 String[] innerColorSplit = innerColorString.split("\\[");
				 String rgbRightSideInner = innerColorSplit[1];
				 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
				 String [] rgbSplitInner = rgbRightSideInner.split(",");
				 String rInner = rgbSplitInner[0];
				 String gInner = rgbSplitInner[1];
				 String bInner = rgbSplitInner[2];
				 String[] rEqualsSplitInner = rInner.split("=");
				 String[] gEqualsSplitInner = gInner.split("=");
				 String[] bEqualsSplitInner = bInner.split("=");
				 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
				 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
				 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
				 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
			
				 Point p = new Point(centerX,centerY);
				 modifiedShape = new Circle(p,radius,edgeColor,innerColor);
				 
				 for(Shape s : tempListOfShapes) {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
				 
				 cmd = new UpdateCircleCmd((Circle)shape, (Circle)modifiedShape);
				 
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
					boolean commandExists = false;
					for(Command c : tempListOfCommands) {
						if(c.toString().equals(cmd.toString())) {
							commandExists = true;
							cmd = c;
							break;
						}
					}
					
					if(!commandExists) {
						tempListOfCommands.add(cmd);
					}
				 
				 frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
				 return cmd;
				 
				 
				
			}
			else if(shapeString.contains("Donut")) {
				shapeString = parts[6];
				positionAndSize = parts[7];
				edgeColorString = parts[8];
				innerColorString = parts[9];
				
				
				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String[] positionAndSizeSplit = positionAndSize.split(",");
				String firstPart = positionAndSizeSplit[0];
				int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
				String thirdPart = positionAndSizeSplit[2];
				String fourthPart = positionAndSizeSplit[3];
				String[] firstPartSplit = firstPart.split(":");
				int centerX = Integer.parseInt(firstPartSplit[1].trim());
				String[] thirdPartSplit = thirdPart.split(":");
				int radius =Integer.parseInt(thirdPartSplit[1].trim());
				String[] fourthPartSplit = fourthPart.split(":");
				int innerRadius = Integer.parseInt(fourthPartSplit[1].trim());
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 String[] innerColorSplit = innerColorString.split("\\[");
				 String rgbRightSideInner = innerColorSplit[1];
				 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
				 String [] rgbSplitInner = rgbRightSideInner.split(",");
				 String rInner = rgbSplitInner[0];
				 String gInner = rgbSplitInner[1];
				 String bInner = rgbSplitInner[2];
				 String[] rEqualsSplitInner = rInner.split("=");
				 String[] gEqualsSplitInner = gInner.split("=");
				 String[] bEqualsSplitInner = bInner.split("=");
				 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
				 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
				 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
				 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
			
				
				Point p = new Point(centerX,centerY);
				modifiedShape = new Donut(p,radius,innerRadius,edgeColor,innerColor);
				
				 for(Shape s : tempListOfShapes) {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
				
				
				cmd = new UpdateDonutCmd((Donut)shape, (Donut)modifiedShape);
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
				boolean commandExists = false;
				for(Command c : tempListOfCommands) {
					if(c.toString().equals(cmd.toString())) {
						commandExists = true;
						cmd = c;
						break;
					}
				}
				
				if(!commandExists) {
					tempListOfCommands.add(cmd);
				}

				frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
				return cmd;
				
			}
			else if(shapeString.contains("Hexagon")) {
				shapeString = parts[6];
				positionAndSize = parts[7];
				edgeColorString = parts[8];
				innerColorString = parts[9];
				
				
				positionAndSize = positionAndSize.replaceAll("[()]", "");
				String[] positionAndSizeSplit = positionAndSize.split(",");
				String firstPart = positionAndSizeSplit[0];
				int centerY = Integer.parseInt(positionAndSizeSplit[1].trim());
				String thirdPart = positionAndSizeSplit[2];
				String[] firstPartSplit = firstPart.split(":");
				int centerX = Integer.parseInt(firstPartSplit[1].trim());
				String[] thirdPartSplit = thirdPart.split(":");
				int radius = Integer.parseInt(thirdPartSplit[1].trim());
				
				String[] edgeColorSplit = edgeColorString.split("\\[");
				 String rgbRightSide = edgeColorSplit[1];
				 rgbRightSide = rgbRightSide.replaceAll("]", "");
				 String [] rgbSplit = rgbRightSide.split(",");
				 String r = rgbSplit[0];
				 String g = rgbSplit[1];
				 String b = rgbSplit[2];
				 String[] rEqualsSplit = r.split("=");
				 String[] gEqualsSplit = g.split("=");
				 String[] bEqualsSplit = b.split("=");
				 int rValue = Integer.parseInt(rEqualsSplit[1].trim());
				 int gValue = Integer.parseInt(gEqualsSplit[1].trim());
				 int bValue = Integer.parseInt(bEqualsSplit[1].trim());
				 Color edgeColor = new Color(rValue,gValue,bValue);
				 
				 String[] innerColorSplit = innerColorString.split("\\[");
				 String rgbRightSideInner = innerColorSplit[1];
				 rgbRightSideInner = rgbRightSideInner.replaceAll("]", "");
				 String [] rgbSplitInner = rgbRightSideInner.split(",");
				 String rInner = rgbSplitInner[0];
				 String gInner = rgbSplitInner[1];
				 String bInner = rgbSplitInner[2];
				 String[] rEqualsSplitInner = rInner.split("=");
				 String[] gEqualsSplitInner = gInner.split("=");
				 String[] bEqualsSplitInner = bInner.split("=");
				 int rValueInner = Integer.parseInt(rEqualsSplitInner[1].trim());
				 int gValueInner = Integer.parseInt(gEqualsSplitInner[1].trim());
				 int bValueInner = Integer.parseInt(bEqualsSplitInner[1].trim());
				 Color innerColor = new Color(rValueInner,gValueInner,bValueInner);
			
				 
				 Point p = new Point(centerX,centerY);
				 modifiedShape = new HexagonAdapter(p,radius,edgeColor,innerColor);
				 
				 for(Shape s : tempListOfShapes) {
					 if(s instanceof HexagonAdapter) {
						 HexagonAdapter ha = (HexagonAdapter)s;
						 HexagonAdapter ha2 = (HexagonAdapter) shape;
						 if(ha.equals(ha2)) {
							 shape = s;
							 break;
						 }
					 }
					 
				 }
				 
				 cmd = new UpdateHexagonCmd((HexagonAdapter)shape, (HexagonAdapter)modifiedShape);
				 
				// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
					boolean commandExists = false;
					for(Command c : tempListOfCommands) {
						if(c.toString().equals(cmd.toString())) {
							commandExists = true;
							cmd = c;
							break;
						}
					}
					
					if(!commandExists) {
						tempListOfCommands.add(cmd);
					}
				 
				 frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
				 return cmd;
					
				
			}
			
		
			
		} else if(command.contains("SelectShape")) {
			 for(Shape s : tempListOfShapes) {
				 if(s instanceof HexagonAdapter) {
					 if(shape instanceof HexagonAdapter) {
						 HexagonAdapter ha = (HexagonAdapter)s;
						 HexagonAdapter ha2 = (HexagonAdapter) shape;
						 if(ha.equals(ha2)) {
							 shape = s;
							 break;
						 }
					 }
				 } else {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
				 
			 }
			cmd = new SelectShapeCmd(model,shape); 
			
			// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
			boolean commandExists = false;
			for(Command c : tempListOfCommands) {
				if(c.toString().equals(cmd.toString())) {
					commandExists = true;
					cmd = c;
					break;
				}
			}
			
			if(!commandExists) {
				tempListOfCommands.add(cmd);
			}
			
			frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
			return cmd;
		} else if(command.contains("DeselectShape")) {
			for(Shape s : tempListOfShapes) {
				 if(s instanceof HexagonAdapter) {
					 if(shape instanceof HexagonAdapter) {
						 HexagonAdapter ha = (HexagonAdapter)s;
						 HexagonAdapter ha2 = (HexagonAdapter) shape;
						 if(ha.equals(ha2)) {
							 shape = s;
							 break;
						 }
					 }
				 } else {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
				 
			 }
			cmd = new DeselectShapeCmd(model,shape); 
			
			// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
			boolean commandExists = false;
			for(Command c : tempListOfCommands) {
				if(c.toString().equals(cmd.toString())) {
					commandExists = true;
					cmd = c;
					break;
				}
			}
			
			if(!commandExists) {
				tempListOfCommands.add(cmd);
			}
			
			frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
			return cmd;
		} else if(command.contains("DeselectAllShapes")) {
			for(Shape s : tempListOfShapes) {
				 if(s instanceof HexagonAdapter) {
					 if(shape instanceof HexagonAdapter) {
						 HexagonAdapter ha = (HexagonAdapter)s;
						 HexagonAdapter ha2 = (HexagonAdapter) shape;
						 if(ha.equals(ha2)) {
							 shape = s;
							 break;
						 }
					 }
				 } else {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
			 }
			cmd = new DeselectAllShapesCmd(model,shape); 
			
			// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
			boolean commandExists = false;
			for(Command c : tempListOfCommands) {
				if(c.toString().equals(cmd.toString())) {
					commandExists = true;
					cmd = c;
					break;
				}
			}
			
			if(!commandExists) {
				tempListOfCommands.add(cmd);
			}
			
			frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
			return cmd;
		} else if(command.contains("BringToFront")) {
			for(Shape s : tempListOfShapes) {
				 if(s instanceof HexagonAdapter) {
					 if(shape instanceof HexagonAdapter) {
						 HexagonAdapter ha = (HexagonAdapter)s;
						 HexagonAdapter ha2 = (HexagonAdapter) shape;
						 if(ha.equals(ha2)) {
							 shape = s;
							 break;
						 }
					 }
				 } else {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
				 
			 }
			cmd = new BringToFrontCmd(shape, model.getShapes()); 
			
			// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
			boolean commandExists = false;
			for(Command c : tempListOfCommands) {
				if(c.toString().equals(cmd.toString())) {
					commandExists = true;
					cmd = c;
					break;
				}
			}
			
			if(!commandExists) {
				tempListOfCommands.add(cmd);
			}
			
			frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
			return cmd;
		} else if(command.contains("BringToBack")) {
			for(Shape s : tempListOfShapes) {
				 if(s instanceof HexagonAdapter) {
					 if(shape instanceof HexagonAdapter) {
						 HexagonAdapter ha = (HexagonAdapter)s;
						 HexagonAdapter ha2 = (HexagonAdapter) shape;
						 if(ha.equals(ha2)) {
							 shape = s;
							 break;
						 }
					 }
				 } else {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
			 }
			cmd = new BringToBackCmd(shape, model.getShapes()); 
			
			// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
			boolean commandExists = false;
			for(Command c : tempListOfCommands) {
				if(c.toString().equals(cmd.toString())) {
					commandExists = true;
					cmd = c;
					break;
				}
			}
			
			if(!commandExists) {
				tempListOfCommands.add(cmd);
			}
			
			frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
			return cmd;
		} else if(command.contains("ToFront")) {
			for(Shape s : tempListOfShapes) {
				 if(s instanceof HexagonAdapter) {
					 if(shape instanceof HexagonAdapter) {
						 HexagonAdapter ha = (HexagonAdapter)s;
						 HexagonAdapter ha2 = (HexagonAdapter) shape;
						 if(ha.equals(ha2)) {
							 shape = s;
							 break;
						 }
					 }
				 } else {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
			 }
			cmd = new ToFrontCmd(shape, model.getShapes()); 
			
			// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
			boolean commandExists = false;
			for(Command c : tempListOfCommands) {
				if(c.toString().equals(cmd.toString())) {
					commandExists = true;
					cmd = c;
					break;
				}
			}
			
			if(!commandExists) {
				tempListOfCommands.add(cmd);
			}
			
			frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
			return cmd;
		} else if(command.contains("ToBack")) {
			for(Shape s : tempListOfShapes) {
				 if(s instanceof HexagonAdapter) {
					 if(shape instanceof HexagonAdapter) {
						 HexagonAdapter ha = (HexagonAdapter)s;
						 HexagonAdapter ha2 = (HexagonAdapter) shape;
						 if(ha.equals(ha2)) {
							 shape = s;
							 break;
						 }
					 }
				 } else {
					 if(s.equals(shape)) {
						 shape = s;
						 break;
					 }
				 }
			 }
			cmd = new ToBackCmd(shape, model.getShapes()); 
			
			// TODO: Dodato zbog UNDO kad zavrsimo sa REDO iz loga
			boolean commandExists = false;
			for(Command c : tempListOfCommands) {
				if(c.toString().equals(cmd.toString())) {
					commandExists = true;
					cmd = c;
					break;
				}
			}
			
			if(!commandExists) {
				tempListOfCommands.add(cmd);
			}
			
			frame.getBottomPanel().getDefaultListModel().addElement(cmd.toString());
			return cmd;
		} 
		
		}
		
		return null;
		

		
	}

}
