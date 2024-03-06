package dialogs;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mvc.DrawingController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import shapes.Point;

public class DlgPoint extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtX;
	private JTextField txtY;
	
	private Point point = null;
	private Color edgeColor = null;
	
	private JLabel lblX;
	private JLabel lblY;
	
	private JButton btnEdgeColor;
	private JButton btnConfirm;
	private JButton btnCancel;
	private JLabel lblChooseColor;
	
	private DrawingController controller;
	
	public DlgPoint() {
		
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 332, 171);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel pnlTop = new JPanel();
			pnlTop.setBackground(Color.decode("#c6edc2"));
			getContentPane().add(pnlTop, BorderLayout.CENTER);
			{
				lblX = new JLabel("X coordinate:", SwingConstants.CENTER);
			}
			{
				txtX = new JTextField();
				txtX.setColumns(10);
			}
			{
				lblY = new JLabel("Y coordinate:");
				lblY.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtY = new JTextField();
				txtY.setColumns(10);
			}
			{
				btnConfirm = new JButton("CONFIRM");
				btnConfirm.setBackground(Color.decode("#f5e7ff"));
				btnConfirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if(Integer.parseInt(txtX.getText()) < 0 || Integer.parseInt(txtY.getText()) < 0) {
								JOptionPane.showMessageDialog(null, "Data is not correct!", "Error!", JOptionPane.ERROR_MESSAGE);
								return;
							}
							point = new Point(Integer.parseInt(txtX.getText()), Integer.parseInt(txtY.getText()),false, edgeColor);
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Data is not correct!", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
			{
				btnCancel = new JButton("CANCEL");
				btnCancel.setBackground(Color.decode("#f5e7ff"));
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
			GroupLayout gl_pnlTop = new GroupLayout(pnlTop);
			gl_pnlTop.setHorizontalGroup(
				gl_pnlTop.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnlTop.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_pnlTop.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlTop.createSequentialGroup()
								.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_pnlTop.createSequentialGroup()
								.addGroup(gl_pnlTop.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_pnlTop.createSequentialGroup()
										.addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
										.addGap(14)))
								.addGroup(gl_pnlTop.createParallelGroup(Alignment.LEADING)
									.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(gl_pnlTop.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap())))
			);
			gl_pnlTop.setVerticalGroup(
				gl_pnlTop.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlTop.createSequentialGroup()
						.addGroup(gl_pnlTop.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlTop.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_pnlTop.createParallelGroup(Alignment.LEADING)
							.addComponent(btnConfirm, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
							.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
						.addContainerGap())
			);
			pnlTop.setLayout(gl_pnlTop);
		}
		{
			JPanel pnlNorth = new JPanel();
			pnlNorth.setBackground(Color.decode("#c6edc2"));
			getContentPane().add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				lblChooseColor = new JLabel("Choose color:");
				pnlNorth.add(lblChooseColor);
			}
			{
				btnEdgeColor = new JButton("COLOR");
				pnlNorth.add(btnEdgeColor);
				btnEdgeColor.setBackground(Color.decode("#f5e7ff"));
				btnEdgeColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnEdgeColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						edgeColor = JColorChooser.showDialog(null, "Pick the edge color", edgeColor);
						if(edgeColor == null) {
							edgeColor = controller.getFrame().getTopPanel().getBtnEdgeColor().getBackground();
						}
						
					}
				});
			}
		}
	}

	public Point getPoint() {
		return point;
	}
	public void setColors(Color color) {
		this.edgeColor = color;
	}
	
	public void setPoint(Point point) {
		txtX.setText("" + point.getX());
		txtY.setText("" + point.getY());
		
		edgeColor = point.getEdgeColor();
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
	}
}
