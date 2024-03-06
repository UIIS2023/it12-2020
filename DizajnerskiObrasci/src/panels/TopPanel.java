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


public class TopPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnModify;
	private JButton btnDelete;
	private JButton btnEdgeColor;
	private JButton btnInnerColor;
	private JLabel lblEdgeColor;
	private JLabel lblInnerColor;
	
	private Color edgeColor = Color.BLACK;
	private Color innerColor = Color.WHITE;
	
	public TopPanel() {
		
		setBackground(Color.decode("#c6edc2"));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component verticalStrut_2 = Box.createVerticalStrut(5);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 0; 
		add(verticalStrut_2, gbc_verticalStrut_2);
		
		Component horizontalStrut1 = Box.createHorizontalStrut(150);
		GridBagConstraints gbc_horizontalStrut1 = new GridBagConstraints();
		gbc_horizontalStrut1.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut1.gridx = 0;
		gbc_horizontalStrut1.gridy = 1;
		add(horizontalStrut1, gbc_horizontalStrut1);
		
		
		
		btnDelete = new JButton("Delete");
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridx = 1;
		gbc_btnDelete.gridy = 1;
		add(btnDelete, gbc_btnDelete);
		btnDelete.setEnabled(false);
		btnDelete.setBackground(Color.decode("#f5e7ff"));
		btnDelete.setFont(new Font("Serif", Font.PLAIN, 17));
		btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		btnModify = new JButton("Modify");
		GridBagConstraints gbc_btnModify = new GridBagConstraints();
		gbc_btnModify.insets = new Insets(0, 0, 5, 0);
		gbc_btnModify.gridx = 2;
		gbc_btnModify.gridy = 1;
		add(btnModify, gbc_btnModify);
		btnModify.setEnabled(false);
		btnModify.setBackground(Color.decode("#f5e7ff"));
		btnModify.setFont(new Font("Serif", Font.PLAIN, 17));
		btnModify.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		lblInnerColor = new JLabel("Inner color:");
		GridBagConstraints gbc_lblInnerColor = new GridBagConstraints();
		gbc_lblInnerColor.insets = new Insets(0, 0, 5, 0);
		gbc_lblInnerColor.gridx = 3;
		gbc_lblInnerColor.gridy = 1;
		add(lblInnerColor, gbc_lblInnerColor);
		lblInnerColor.setFont(new Font("Serif", Font.BOLD,13));
		
	    
		
		Component horizontalStrut4 = Box.createHorizontalStrut(5);
		GridBagConstraints gbc_horizontalStrut4 = new GridBagConstraints();
		gbc_horizontalStrut4.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut4.gridx = 4;
		gbc_horizontalStrut4.gridy = 1;
		add(horizontalStrut4, gbc_horizontalStrut4);
		
		btnInnerColor = new JButton("");
		GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
		gbc_btnInnerColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnInnerColor.gridx = 5;
		gbc_btnInnerColor.gridy = 1;
		add(btnInnerColor, gbc_btnInnerColor);
		btnInnerColor.setBackground(innerColor);
		
		
		
		Component horizontalStrut2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut2 = new GridBagConstraints();
		gbc_horizontalStrut2.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut2.gridx = 6;
		gbc_horizontalStrut2.gridy = 1;
		add(horizontalStrut2, gbc_horizontalStrut2);
		
		lblEdgeColor = new JLabel("Edge color:");
		GridBagConstraints gbc_lblEdgeColor = new GridBagConstraints();
		gbc_lblEdgeColor.insets = new Insets(0, 0, 5, 0);
		gbc_lblEdgeColor.gridx = 7;
		gbc_lblEdgeColor.gridy = 1;
		add(lblEdgeColor, gbc_lblEdgeColor);
		lblEdgeColor.setFont(new Font("Serif", Font.BOLD,13));
		
		Component horizontalStrut5 = Box.createHorizontalStrut(5);
		GridBagConstraints gbc_horizontalStrut5 = new GridBagConstraints();
		gbc_horizontalStrut5.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut5.gridx = 8;
		gbc_horizontalStrut5.gridy = 1;
		add(horizontalStrut5, gbc_horizontalStrut5);
		
		
		btnEdgeColor = new JButton("");
		GridBagConstraints gbc_btnEdgeColor = new GridBagConstraints();
		gbc_btnEdgeColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnEdgeColor.gridx = 9;
		gbc_btnEdgeColor.gridy = 1;
		add(btnEdgeColor, gbc_btnEdgeColor);
		btnEdgeColor.setBackground(edgeColor);
		
		Component horizontalStrut3 = Box.createHorizontalStrut(135);
		GridBagConstraints gbc_horizontalStrut3 = new GridBagConstraints();
		gbc_horizontalStrut3.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut3.gridx = 10;
		gbc_horizontalStrut3.gridy = 1;
		add(horizontalStrut3, gbc_horizontalStrut3);
		
		
		
	}

	public Color getEdgeColor() {
		return edgeColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

}
