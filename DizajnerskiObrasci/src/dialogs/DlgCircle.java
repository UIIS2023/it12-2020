
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

import shapes.Circle;
import shapes.Point;

public class DlgCircle extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	
	private Circle circle = null;
	private Color  innerColor = null;
	private Color edgeColor = null;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblRadius;
	private JButton btnInnerColor;
	private JButton btnEdgeColor;
	private JButton btnCancel;
	private JLabel lblInputData;
	
	private DrawingController controller;

	public DlgCircle() {
		
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 354, 266);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel pnlCenter = new JPanel();
			pnlCenter.setBackground(Color.decode("#c6edc2"));
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
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
				lblRadius = new JLabel("Radius:");
				lblRadius.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtRadius = new JTextField();
				txtRadius.setColumns(10);
			}
			JButton btnConfirm = new JButton("CONFIRM");
			btnConfirm.setBackground(Color.decode("#f5e7ff"));
			btnConfirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if(Integer.parseInt(txtX.getText()) < 0 || Integer.parseInt(txtY.getText()) < 0 || Integer.parseInt(txtRadius.getText()) < 1) {
							JOptionPane.showMessageDialog(null, "Data is not correct!", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}
						circle = new Circle(new Point(Integer.parseInt(txtX.getText()), Integer.parseInt(txtY.getText())), Integer.parseInt(txtRadius.getText()),false, edgeColor,innerColor);
						dispose();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Data is not correct!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			});
			{
				btnCancel = new JButton("CANCEL");
				btnCancel.setBackground(Color.decode("#f5e7ff"));
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
			lblInputData = new JLabel("Input coordinates of the center and radius:");
			GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
			gl_pnlCenter.setHorizontalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_pnlCenter.createSequentialGroup()
										.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_pnlCenter.createSequentialGroup()
										.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_pnlCenter.createSequentialGroup()
										.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGap(27)
								.addComponent(lblInputData, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addContainerGap()
								.addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
						.addContainerGap())
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblInputData)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addGap(45)
								.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnConfirm, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
						.addGap(12))
			);
			pnlCenter.setLayout(gl_pnlCenter);
		}
		{
			JPanel pnlNorth = new JPanel();
			pnlNorth.setBackground(Color.decode("#c6edc2"));
			getContentPane().add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JLabel lblChooseColors = new JLabel("Choose colors:");
			pnlNorth.add(lblChooseColors);
			{
				
				{
					btnEdgeColor = new JButton("EDGE color");
					pnlNorth.add(btnEdgeColor);
					btnEdgeColor.setBackground(Color.decode("#f5e7ff"));
					btnEdgeColor.addActionListener(new ActionListener() {
						public void actionPerformed (ActionEvent e) {
							edgeColor = JColorChooser.showDialog(null, "Pick the edge color", edgeColor);
							if(edgeColor == null) {
								edgeColor = controller.getFrame().getTopPanel().getBtnEdgeColor().getBackground();
							}
						
						}
					});
					
				}
			}
			{
				btnInnerColor = new JButton("INNER color");
				pnlNorth.add(btnInnerColor);
				btnInnerColor.setBackground(Color.decode("#f5e7ff"));
				btnInnerColor.setVerticalAlignment(SwingConstants.TOP);
				btnInnerColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnInnerColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						innerColor=JColorChooser.showDialog(null, "Pick the inner color", innerColor);
						if(innerColor == null) {
							innerColor = controller.getFrame().getTopPanel().getBtnInnerColor().getBackground();
						}	
						
					}
				});
			}
		}
	}

	public Circle getCircle() {
		return circle;
	}
	
	public void setPoint(Point point) {
		txtX.setText("" + point.getX());
		txtY.setText("" + point.getY());
	}
	
	public void setColors(Color edgeColor, Color innerColor) {
		this.edgeColor = edgeColor;
		this.innerColor = innerColor;
	}
	
	public void setCircle(Circle circle) {
		txtX.setText("" + circle.getCenter().getX());
		txtY.setText("" + circle.getCenter().getY());
		txtRadius.setText("" + circle.getRadius());
		
		innerColor = circle.getInnerColor();
		edgeColor=circle.getEdgeColor();
	}
	
	

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
}
