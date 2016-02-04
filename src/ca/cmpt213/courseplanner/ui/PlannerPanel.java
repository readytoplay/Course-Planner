package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ca.cmpt213.courseplanner.model.Model;

/**
 * PlannerPanel Class is derived from JPanel which displays a list of Panel
 * objects to the main GUI. Data includes the Model data derived from the course
 * file.
 */

@SuppressWarnings("serial")
public class PlannerPanel extends JPanel {
	private Model model;

	public PlannerPanel(Model model) {
		this.model = model;

	}

	public void displayGeneralPanel(String title, Component container) {
		setLayout(new BorderLayout());
		add(makeLabel(title, Color.BLUE), BorderLayout.NORTH);
		add(container, BorderLayout.CENTER);

	}

	protected JLabel makeLabel(String title, Color color) {
		JLabel label = new JLabel(title);
		label.setForeground(color);
		return label;
	}

	protected void makeBorder(Component container) {
		((JComponent) container).setBorder(BorderFactory.createBevelBorder(
				BevelBorder.LOWERED, Color.black, Color.gray));
	}

	protected Model getModel() {
		return model;
	}
}
