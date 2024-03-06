package strategy;


import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectOutputStream;

import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import command.Command;


public class Drawing implements SaveStrategy{


	private Stack<Command> undoStack;
	private Stack<Command> redoStack;
	
	public Drawing(Stack<Command> undoStack, Stack<Command> redoStack) {
		
		this.undoStack = undoStack;
		this.redoStack = redoStack;
	}

	@Override
	public void save() {
		//SERIJALIZACIJA CRTEZA
		JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView());
		jFileChooser.setDialogTitle("Select a folder");
		jFileChooser.setCurrentDirectory(new File("C:\\Users\\Natasa\\Desktop\\drawings"));
		String defaultFileName = "drawing";
		jFileChooser.setSelectedFile(new File(defaultFileName));

		if (jFileChooser.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getPath();

			File file = new File(path + ".ser");
			ObjectOutputStream objectOutputStream = null;
			try {
				objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

				if(undoStack.size() == 0) {
	            	objectOutputStream.writeObject(redoStack);
	            }else {
	                objectOutputStream.writeObject(undoStack);
	            }

	            objectOutputStream.close();
	            
	            JOptionPane.showMessageDialog(null, "Drawing saved successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
	}

	
}
