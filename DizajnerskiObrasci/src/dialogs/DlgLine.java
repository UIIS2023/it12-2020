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

import shapes.Line;
import shapes.Point;

public class DlgLine extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtFirstX;
	private JTextField txtFirstY;
	private JTextField txtSecondX;
	private JTextField txtSecondY;
	
	private Line line = null;
	private Color edgeColor = null;
	
	private JLabel lblFirstX;
	private JLabel lblFirstY;
	private JLabel lblSecondX;
	private JLabel lblSecondY;
	
	private JButton btnColor;
	private JButton btnConfirm;
	private JButton btnCancel;
	private JLabel lblChooseColor;
	
	private DrawingController controller;

	public DlgLine() {
		setResizable(false);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 420, 236);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel panelUp = new JPanel();
			panelUp.setBackground(Color.decode("#c6edc2"));
			getContentPane().add(panelUp, BorderLayout.CENTER);
			{
				lblFirstX = new JLabel("First point - X coordinate:", SwingConstants.CENTER);
			}
			{
				txtFirstX = new JTextField();
				txtFirstX.setColumns(10);
			}
			{
				lblFirstY = new JLabel("First point - Y coordinate:");
				lblFirstY.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtFirstY = new JTextField();
				txtFirstY.setColumns(10);
			}
			{
				lblSecondX = new JLabel("Second point - X coordinate:");
				lblSecondX.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtSecondX = new JTextField();
				txtSecondX.setColumns(10);
			}
			{
				lblSecondY = new JLabel("Second point - Y coordinate:");
				lblSecondY.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtSecondY = new JTextField();
				txtSecondY.setColumns(10);
			}
			{
				btnConfirm = new JButton("CONFIRM");
				btnConfirm.setBackground(Color.decode("#f5e7ff"));
				btnConfirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if(Integer.parseInt(txtFirstX.getText()) < 0 || Integer.parseInt(txtFirstY.getText()) < 0 || Integer.parseInt(txtSecondX.getText()) < 0 || Integer.parseInt(txtSecondY.getText()) < 0) {
								JOptionPane.showMessageDialog(null, "Data is not correct!", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}
							line = new Line(new Point(Integer.parseInt(txtFirstX.getText()), Integer.parseInt(txtFirstY.getText())), new Point(Integer.parseInt(txtSecondX.getText()), Integer.parseInt(txtSecondY.getText())),false, edgeColor);
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Data is not correct!", "Error", JOptionPane.ERROR_MESSAGE);
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
			GroupLayout gl_panelUp = new GroupLayout(panelUp);
			gl_panelUp.setHorizontalGroup(
				gl_panelUp.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panelUp.createSequentialGroup()
						.addGap(18)
						.addGroup(gl_panelUp.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelUp.createSequentialGroup()
								.addGap(18)
								.addGroup(gl_panelUp.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblFirstX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblFirstY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_panelUp.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panelUp.createParallelGroup(Alignment.LEADING, false)
									.addComponent(lblSecondY, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
									.addComponent(lblSecondX, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
									.addComponent(btnConfirm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelUp.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelUp.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(txtSecondX, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
								.addComponent(txtFirstX, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
								.addComponent(txtFirstY, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
								.addComponent(txtSecondY, Alignment.LEADING))
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
						.addGap(106))
			);
			gl_panelUp.setVerticalGroup(
				gl_panelUp.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelUp.createSequentialGroup()
						.addGap(1)
						.addGroup(gl_panelUp.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtFirstX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblFirstX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelUp.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtFirstY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblFirstY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelUp.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtSecondX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblSecondX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelUp.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtSecondY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblSecondY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGap(12)
						.addGroup(gl_panelUp.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGap(36))
			);
			panelUp.setLayout(gl_panelUp);
		}
		{
			JPanel panelNorth = new JPanel();
			panelNorth.setBackground(Color.decode("#c6edc2"));
			getContentPane().add(panelNorth, BorderLayout.NORTH);
			panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				lblChooseColor = new JLabel("Choose color:");
				panelNorth.add(lblChooseColor);
			}
			{
				btnColor = new JButton("COLOR");
				panelNorth.add(btnColor);
				btnColor.setBackground(Color.decode("#f5e7ff"));
				btnColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnColor.addActionListener(new ActionListener() {
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
	

	public Line getLine() {
		return line;
	}
	
	public void setLine(Line line) {
		txtFirstX.setText("" + line.getStartPoint().getX());
		txtFirstY.setText("" + line.getStartPoint().getY());
		txtSecondX.setText("" + line.getEndPoint().getX());
		txtSecondY.setText("" + line.getEndPoint().getY());
		
		edgeColor = line.getEdgeColor();
	}
	
	public void setFirstPoint(Point point) {
		txtFirstX.setText("" + point.getX());
		txtFirstY.setText("" + point.getY());
	}
	
	public void setSecondPoint (Point point) {
		txtSecondX.setText("" + point.getX());
		txtSecondY.setText("" + point.getY());
	}
	
	public void setColors(Color edgeColor) {
		this.edgeColor = edgeColor;
		
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
	}
}
