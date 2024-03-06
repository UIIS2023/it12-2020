package mvc;

import java.awt.BorderLayout;
import javax.swing.JFrame;


import panels.BottomPanel;
import panels.LeftPanel;
import panels.RightPanel;
import panels.TopPanel;


import javax.swing.JColorChooser;

import java.awt.Color;
import java.awt.Dimension;



import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;








public class DrawingFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingController controller; 
    private DrawingView view = new DrawingView();	
    private BottomPanel bottomPanel;
	private RightPanel rightPanel;
	private LeftPanel leftPanel;
	private TopPanel topPanel;
    
   
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public DrawingFrame() {
		setTitle("Rad Natasa IT 12/2020");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 964, 655);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(1100, 700));
	
		
		bottomPanel = new BottomPanel();
		rightPanel = new RightPanel();
		leftPanel = new LeftPanel();
		topPanel = new TopPanel();
		
		getContentPane().add(view, BorderLayout.CENTER);
		getContentPane().add(rightPanel, BorderLayout.EAST);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				controller.mouseClicked(e); 
			}
		});
		
	 
		
		
		
		rightPanel.getBtnSelect().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedSelect(e);
			}
		});
		
		rightPanel.getTglbtnCircle().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedDrawing(e);
			}
		});
		
		rightPanel.getTglbtnDonut().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedDrawing(e);
			}
		});
		
		rightPanel.getTglbtnHexagon().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedDrawing(e);
			}
		});
		
		rightPanel.getTglbtnLine().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedDrawing(e);
			}
		});
		
		rightPanel.getTglbtnPoint().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedDrawing(e);
			}
		});
		
		rightPanel.getTglbtnRectangle().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedDrawing(e);
			}
		});
		
	
		
		topPanel.getBtnModify().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedModify(e);
			}
		});
	
		
		topPanel.getBtnDelete().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedDelete(e);
			}
		});
	
		
	
		
	
		leftPanel.getBtnSaveLog().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedSaveLog(e);
			}
		});
		
	
		leftPanel.getBtnSaveDrawing().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedSaveDrawing(e);
			}
		});
		
		
		leftPanel.getBtnLoadLogAndDrawing().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedLoadLogAndDrawingStepByStep(e);
			}
		});
		
	
	
		
		
	
		leftPanel.getBtnToFront().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedToFront(e);
			}
		});
		
	
		leftPanel.getBtnToBack().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedToBack(e);
			}
		});
		
	
		leftPanel.getBtnBringToFront().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedBringToFront(e);
			}
		});
		
	
		leftPanel.getBtnBringToBack().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedBringToBack(e);
			}
		});
		
		
		
	
		leftPanel.getBtnUndo().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedUndo(e);
			}
		});
		
	
		leftPanel.getBtnRedo().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedRedo(e);
			}
		});
	

	
		
		topPanel.getBtnEdgeColor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedChooseEdgeColor(e);
			}
		});
		
		
		
		
		
	
		
		topPanel.getBtnInnerColor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformedChooseInnerColor(e);
			}
		});
	}
		
		
	
	
	

	

	public DrawingView getView() {
		return view;
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public Color chooseColor(Color oldColor) {
		Color newColor = JColorChooser.showDialog(null, "Choose a color", oldColor);
		if(newColor!=null)
			return newColor;
		else {
			return oldColor;
		}
	}



	

	public BottomPanel getBottomPanel() {
		return bottomPanel;
	}


	public RightPanel getRightPanel() {
		return rightPanel;
	}


	public LeftPanel getLeftPanel() {
		return leftPanel;
	}


	public TopPanel getTopPanel() {
		return topPanel;
	}
	
}
			
	
	
		
	
