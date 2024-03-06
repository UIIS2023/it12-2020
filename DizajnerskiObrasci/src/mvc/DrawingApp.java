package mvc;

import java.awt.EventQueue;

public class DrawingApp {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrawingModel model = new DrawingModel();
					DrawingFrame frame = new DrawingFrame();
					frame.getView().setModel(model);
					DrawingController controller = new DrawingController(frame,model);
					frame.setController(controller);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}
}
