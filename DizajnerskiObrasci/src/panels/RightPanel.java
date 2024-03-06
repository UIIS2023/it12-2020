package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


import javax.swing.Box;
import javax.swing.ButtonGroup;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class RightPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine ;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle ;
	private JToggleButton tglbtnDonut ;
	private JToggleButton tglbtnHexagon;
	private JToggleButton btnSelect ;
	
	private ButtonGroup btnsShapes = new ButtonGroup();
	
	public RightPanel() {
		
		
		
		setBackground(Color.decode("#c6edc2"));
		
		
		setSize(150,1000);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 0;
		add(verticalStrut_2, gbc_verticalStrut_2);
		
		
		
		btnSelect = new JToggleButton("Select");
		GridBagConstraints gbc_btnSelect = new GridBagConstraints();
		gbc_btnSelect.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelect.gridx = 1;
		gbc_btnSelect.gridy = 1;
		add(btnSelect, gbc_btnSelect);
		btnSelect.setBackground(Color.decode("#f5e7ff"));
		btnSelect.setFont(new Font("Serif", Font.PLAIN, 17));
		btnSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnSelect);
		
		Component verticalStrut_1 = Box.createVerticalStrut(80);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 2;
		add(verticalStrut_1, gbc_verticalStrut_1);
		
		
		
		tglbtnPoint = new JToggleButton("POINT");
		GridBagConstraints gbc_tglbtnPoint = new GridBagConstraints();
		gbc_tglbtnPoint.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnPoint.gridx = 1;
		gbc_tglbtnPoint.gridy = 3;
		add(tglbtnPoint, gbc_tglbtnPoint);
		tglbtnPoint.setBackground(Color.decode("#f5e7ff"));
		tglbtnPoint.setFont(new Font("Serif", Font.PLAIN, 17));
		tglbtnPoint.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(tglbtnPoint);
		
		
		
	
		
		tglbtnLine= new JToggleButton("LINE");
		GridBagConstraints gbc_tglbtnLine = new GridBagConstraints();
		gbc_tglbtnLine.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnLine.gridx = 1;
		gbc_tglbtnLine.gridy = 4;
		add(tglbtnLine, gbc_tglbtnLine);
		tglbtnLine.setBackground(Color.decode("#f5e7ff"));
		tglbtnLine.setFont(new Font("Serif", Font.PLAIN, 17));
		tglbtnLine.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(tglbtnLine);
		
		
		tglbtnRectangle = new JToggleButton("RECTANGLE");
		GridBagConstraints gbc_tglbtnRectangle = new GridBagConstraints();
		gbc_tglbtnRectangle.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnRectangle.gridx = 1;
		gbc_tglbtnRectangle.gridy = 5;
		add(tglbtnRectangle, gbc_tglbtnRectangle);
		tglbtnRectangle.setBackground(Color.decode("#f5e7ff"));
		tglbtnRectangle.setFont(new Font("Serif", Font.PLAIN, 17));
		tglbtnRectangle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(tglbtnRectangle);
		
		
		tglbtnCircle = new JToggleButton("CIRCLE");
		GridBagConstraints gbc_tglbtnCircle = new GridBagConstraints();
		gbc_tglbtnCircle.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnCircle.gridx = 1;
		gbc_tglbtnCircle.gridy = 6;
		add(tglbtnCircle, gbc_tglbtnCircle);
		tglbtnCircle.setBackground(Color.decode("#f5e7ff"));
		tglbtnCircle.setFont(new Font("Serif", Font.PLAIN, 17));
		tglbtnCircle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(tglbtnCircle);
		
		
		tglbtnDonut = new JToggleButton("DONUT");
		GridBagConstraints gbc_tglbtnDonut = new GridBagConstraints();
		gbc_tglbtnDonut.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnDonut.gridx = 1;
		gbc_tglbtnDonut.gridy = 7;
		add(tglbtnDonut, gbc_tglbtnDonut);
		tglbtnDonut.setBackground(Color.decode("#f5e7ff"));
		tglbtnDonut.setFont(new Font("Serif", Font.PLAIN, 17));
		tglbtnDonut.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(tglbtnDonut);
		
		
	
		
		tglbtnHexagon = new JToggleButton("HEXAGON");
		GridBagConstraints gbc_tglbtnHexagon = new GridBagConstraints();
		gbc_tglbtnHexagon.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnHexagon.gridx = 1;
		gbc_tglbtnHexagon.gridy = 8;
		add(tglbtnHexagon, gbc_tglbtnHexagon);
		tglbtnHexagon.setBackground(Color.decode("#f5e7ff"));
		tglbtnHexagon.setFont(new Font("Serif", Font.PLAIN, 17));
		tglbtnHexagon.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(tglbtnHexagon);
		
		
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public JToggleButton getBtnSelect() {
		return btnSelect;
	}



}
