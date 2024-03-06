package dialogs;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mvc.DrawingController;
import shapes.Donut;
import shapes.Point;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgDonut extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	private JTextField txtInnerRadius;
	
	private Donut donut = null;
	private Color edgeColor = null;
	private Color innerColor=null;
	
	private DrawingController controller;
	
	public DlgDonut() {
		
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 358, 222);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel pnlCenter = new JPanel();
			pnlCenter.setBackground(Color.decode("#c6edc2"));
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setLayout(new GridLayout(5, 2, 0, 0));
			{
				JLabel lblX = new JLabel("X coordinate (of the center):", SwingConstants.CENTER);
				pnlCenter.add(lblX);
			}
			{
				txtX = new JTextField();
				pnlCenter.add(txtX);
				txtX.setColumns(10);
			}
			{
				JLabel lblY = new JLabel("Y coordinate (of the center):");
				lblY.setHorizontalAlignment(SwingConstants.CENTER);
				pnlCenter.add(lblY);
			}
			{
				txtY = new JTextField();
				pnlCenter.add(txtY);
				txtY.setColumns(10);
			}
			{
				JLabel lblRadius = new JLabel("Radius:");
				lblRadius.setHorizontalAlignment(SwingConstants.CENTER);
				pnlCenter.add(lblRadius);
			}
			{
				txtRadius = new JTextField();
				pnlCenter.add(txtRadius);
				txtRadius.setColumns(10);
			}
			{
				JLabel lblInnerRadius = new JLabel("Inner radius:");
				lblInnerRadius.setHorizontalAlignment(SwingConstants.CENTER);
				pnlCenter.add(lblInnerRadius);
			}
			{
				txtInnerRadius = new JTextField();
				pnlCenter.add(txtInnerRadius);
				txtInnerRadius.setColumns(10);
			}
			JButton btnConfirm = new JButton("CONFIRM");
			pnlCenter.add(btnConfirm);
			btnConfirm.setBackground(Color.decode("#f5e7ff"));
			btnConfirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if(Integer.parseInt(txtX.getText()) < 0 || Integer.parseInt(txtY.getText()) < 0 || Integer.parseInt(txtRadius.getText()) < 1 || Integer.parseInt(txtInnerRadius.getText()) < 1 || Integer.parseInt(txtInnerRadius.getText()) >= Integer.parseInt(txtRadius.getText())) {
							JOptionPane.showMessageDialog(null, "Data is not correct!", "Error!", JOptionPane.ERROR_MESSAGE);
							return;
						}
						donut = new Donut(new Point(Integer.parseInt(txtX.getText()), Integer.parseInt(txtY.getText())), Integer.parseInt(txtRadius.getText()), Integer.parseInt(txtInnerRadius.getText()),false, edgeColor, innerColor);
						dispose();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Data is not correct!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			{
				JButton btnCancel = new JButton("CANCEL");
				pnlCenter.add(btnCancel);
				btnCancel.setBackground(Color.decode("#f5e7ff"));
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
		{
			JPanel pnlNorth = new JPanel();
			pnlNorth.setBackground(Color.decode("#c6edc2"));
			getContentPane().add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JLabel lblChooseColors = new JLabel("Choose colors:");
				pnlNorth.add(lblChooseColors);
			}
			{
				{
					JButton btnEdgeColor = new JButton("EDGE color");
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
			{
				JButton btnInnerColor = new JButton("INNER color");
				pnlNorth.add(btnInnerColor);
				btnInnerColor.setBackground(Color.decode("#f5e7ff"));
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

	public Donut getDonut() {
		return donut;
	}
	
	public void setPoint(Point point) {
		txtX.setText("" + point.getX());
		txtY.setText("" + point.getY());
    }
	
	public void setColors(Color edgeColor, Color innerColor) {
		this.edgeColor = edgeColor;
		this.innerColor = innerColor;
	}
	
	public void setDonut(Donut donut) {
		txtX.setText("" + donut.getCenter().getX());
		txtY.setText("" + donut.getCenter().getY());
		txtRadius.setText("" + donut.getRadius());
		txtInnerRadius.setText("" + donut.getInnerRadius());
		
		edgeColor = donut.getEdgeColor();
		innerColor = donut.getInnerColor();
	}
	
	

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
}
