package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeftPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnRedo ;
	private JButton btnUndo ;
	private JButton btnToBack ;
	private JButton btnToFront ;
	private JButton btnBringToBack;
	private JButton btnBringToFront ;
	
	private JButton btnSaveLog ;
	private JButton btnSaveDrawing ;
	private JButton btnLoadLogAndDrawing ;
	
	private JLabel label;
	
	public LeftPanel() {
		
		setBackground(Color.decode("#c6edc2"));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		add(verticalStrut, gbc_verticalStrut);
		
		
		
		btnUndo= new JButton("UNDO");
		GridBagConstraints gbc_btnUndo = new GridBagConstraints();
		gbc_btnUndo.insets = new Insets(0, 0, 5, 0);
		gbc_btnUndo.gridx = 1;
		gbc_btnUndo.gridy = 1;
		add(btnUndo, gbc_btnUndo);
		btnUndo.setEnabled(false);
		btnUndo.setBackground(Color.decode("#e7f7e5"));
		btnUndo.setFont(new Font("Serif", Font.BOLD,13));
		
		
		btnRedo = new JButton("REDO");
		GridBagConstraints gbc_btnRedo = new GridBagConstraints();
		gbc_btnRedo.insets = new Insets(0, 0, 5, 0);
		gbc_btnRedo.gridx = 1;
		gbc_btnRedo.gridy = 2;
		add(btnRedo, gbc_btnRedo);
		btnRedo.setEnabled(false);
		btnRedo.setBackground(Color.decode("#e7f7e5"));
		btnRedo.setFont(new Font("Serif", Font.BOLD,13));
	
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 3;
		add(verticalStrut_1, gbc_verticalStrut_1);
		
		btnToFront = new JButton("To front");
		GridBagConstraints gbc_btnToFront = new GridBagConstraints();
		gbc_btnToFront.insets = new Insets(0, 0, 5, 0);
		gbc_btnToFront.gridx = 1;
		gbc_btnToFront.gridy = 4;
		add(btnToFront, gbc_btnToFront);
		btnToFront.setEnabled(false);
		btnToFront.setBackground(Color.decode("#e7f7e5"));
		btnToFront.setFont(new Font("Serif", Font.BOLD,13));
		
		
		btnToBack = new JButton("To back");
		GridBagConstraints gbc_btnToBack = new GridBagConstraints();
		gbc_btnToBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnToBack.gridx = 1;
		gbc_btnToBack.gridy = 5;
		add(btnToBack, gbc_btnToBack);
		btnToBack.setEnabled(false);
		btnToBack.setBackground(Color.decode("#e7f7e5"));
		btnToBack.setFont(new Font("Serif", Font.BOLD,13));
	
		
		btnBringToFront = new JButton("Bring to front");
		GridBagConstraints gbc_btnBringToFront = new GridBagConstraints();
		gbc_btnBringToFront.insets = new Insets(0, 0, 5, 0);
		gbc_btnBringToFront.gridx = 1;
		gbc_btnBringToFront.gridy = 6;
		add(btnBringToFront, gbc_btnBringToFront);
		btnBringToFront.setEnabled(false);
		btnBringToFront.setBackground(Color.decode("#e7f7e5"));
		btnBringToFront.setFont(new Font("Serif", Font.BOLD,13));
		
		
		btnBringToBack = new JButton("Bring to back");
		GridBagConstraints gbc_btnBringToBack = new GridBagConstraints();
		gbc_btnBringToBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnBringToBack.gridx = 1;
		gbc_btnBringToBack.gridy = 7;
		add(btnBringToBack, gbc_btnBringToBack);
		btnBringToBack.setEnabled(false);
		btnBringToBack.setBackground(Color.decode("#e7f7e5"));
		btnBringToBack.setFont(new Font("Serif", Font.BOLD,13));
	
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 8;
		add(verticalStrut_2, gbc_verticalStrut_2);
		
	
		
		btnSaveLog = new JButton("Save log");
		GridBagConstraints gbc_btnSaveLog = new GridBagConstraints();
		gbc_btnSaveLog.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveLog.gridx = 1;
		gbc_btnSaveLog.gridy = 9;
		add(btnSaveLog, gbc_btnSaveLog);
		btnSaveLog.setBackground(Color.decode("#e7f7e5"));
		btnSaveLog.setFont(new Font("Serif", Font.BOLD,13));
		btnSaveLog.setEnabled(false);
		
		
		btnSaveDrawing = new JButton("Save drawing");
		GridBagConstraints gbc_btnSaveDrawing = new GridBagConstraints();
		gbc_btnSaveDrawing.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveDrawing.gridx = 1;
		gbc_btnSaveDrawing.gridy = 10;
		add(btnSaveDrawing, gbc_btnSaveDrawing);
		btnSaveDrawing.setBackground(Color.decode("#e7f7e5"));
		btnSaveDrawing.setFont(new Font("Serif", Font.BOLD,13));
		btnSaveDrawing.setEnabled(false);
	
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_3.gridx = 1;
		gbc_verticalStrut_3.gridy = 11;
		add(verticalStrut_3, gbc_verticalStrut_3);
		
	
		
		label = new JLabel(" step by step:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 14;
		add(label, gbc_label);
		label.setFont(new Font("Serif", Font.BOLD,13));
		
		btnLoadLogAndDrawing = new JButton("Load log and drawing");
		GridBagConstraints gbc_btnLoadLogAndDrawing = new GridBagConstraints();
		gbc_btnLoadLogAndDrawing.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoadLogAndDrawing.gridx = 1;
		gbc_btnLoadLogAndDrawing.gridy = 15;
		add(btnLoadLogAndDrawing, gbc_btnLoadLogAndDrawing);
		btnLoadLogAndDrawing.setBackground(Color.decode("#e7f7e5"));
		btnLoadLogAndDrawing.setFont(new Font("Serif", Font.BOLD,13));
		
		
	
	}

	public JButton getBtnLoadLogAndDrawing() {
		return btnLoadLogAndDrawing;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	

	public JButton getBtnSaveLog() {
		return btnSaveLog;
	}

	public JButton getBtnSaveDrawing() {
		return btnSaveDrawing;
	}

}
