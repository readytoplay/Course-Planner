package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.cmpt213.courseplanner.model.Model;

/**
 * MainGUI Class displays the main UI of Course Planner Program by calling the
 * Model for data information
 */

public class MainGUI {
	// Default size of the panels
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 700;
	private static final int PANEL_HEIGHT = (int) (Math.round(HEIGHT * 0.30));
	private static final int PANEL_WIDTH = (int) (Math.round(WIDTH * 0.22));

	public static void displayUI() {
		PlannerPanel plannerPanel = new PlannerPanel(Model.getModel());
		Model courseModel = plannerPanel.getModel();
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout(6, 6));
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JPanel westPanel = combinePanels(fixSize(new CourseListFilterUI(
				courseModel)), new CourseListUI(courseModel));
		JPanel centerPanel = new OfferSemestersUI(courseModel);
		JPanel eastPanel = combinePanels(new StatisticsUI(courseModel),
				new DetailsUI(courseModel));

		frame.add(westPanel, BorderLayout.WEST);
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(eastPanel, BorderLayout.EAST);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static JPanel combinePanels(JPanel top, JPanel bottom) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(top);
		panel.add(bottom);
		panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		return panel;
	}

	private static PlannerPanel fixSize(PlannerPanel panel) {
		Dimension prefSize = panel.getPreferredSize();
		Dimension newSize = new Dimension(Integer.MAX_VALUE,
				(int) prefSize.getHeight());
		panel.setMaximumSize(newSize);
		return panel;
	}
}
