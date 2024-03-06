package panels;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.DefaultListModel;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BottomPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane = new JScrollPane();
	private JList<String> logList;
	private DefaultListModel<String> defaultListModel = new DefaultListModel<String>();

	public BottomPanel() {

		setLayout(new CardLayout());

		logList = new JList<String>(defaultListModel);
		scrollPane.setViewportView(logList);
		add(scrollPane, "log");
		logList.setBackground(Color.white);
		logList.setLayoutOrientation(JList.VERTICAL);
		
	}

	public DefaultListModel<String> getDefaultListModel() {
		return defaultListModel;
	}

}
