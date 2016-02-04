package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ca.cmpt213.courseplanner.model.CourseTypeCollection;
import ca.cmpt213.courseplanner.model.Model;

/**
 * CourseDetailsUI Class displays the detail location, instructors, enrolement
 * status, and course components of a course in a certain semester at a certain
 * location.
 */

@SuppressWarnings("serial")
public class DetailsUI extends PlannerPanel {

	private static final int NUMBER_ROWS = 10;
	private static final int NUMBER_COLS = 2;
	private final String title = "Details of Course Offering";
	private final String section = "Section Type";
	private final String enrolment = "Enrollment(filled/cap)";
	private DetailList typeListModel = new DetailList();
	private JTextArea listDisplay;
	private JPanel typeDisplay;
	private List<String> courseDetails = new ArrayList<String>();

	private String infoDetails;

	public DetailsUI(Model model) {
		super(model);
		typeListModel = model.getTypeList();
		addDetailList();

		JPanel container = new JPanel();
		JPanel subPanel = new JPanel();
		typeDisplay = new JPanel();
		typeDisplay.setLayout(new GridLayout(NUMBER_ROWS, NUMBER_COLS));

		subPanel.setLayout(new BorderLayout(6, 6));
		subPanel.add(makeTitleList(), BorderLayout.WEST);
		subPanel.add(makeTextArea(), BorderLayout.CENTER);

		container.setLayout(new BorderLayout());
		container.add(subPanel, BorderLayout.NORTH);
		container.add(typeDisplay, BorderLayout.CENTER);
		makeBorder(container);
		displayGeneralPanel(title, container);
		registerAsObserver();
	}

	private void addDetailList() {
		courseDetails.clear();
		courseDetails.add(section);
		courseDetails.add(enrolment);
		infoDetails = "";
		for (CourseTypeCollection collection : typeListModel) {
			String[] subString = collection.printTypes().split(",");
			infoDetails = collection.printInfo();
			for (String str : subString) {
				courseDetails.add(str);
			}
		}

	}

	private Component makeTextArea() {
		listDisplay = new JTextArea();
		listDisplay.setWrapStyleWord(true);
		listDisplay.setColumns(10);
		listDisplay.setRows(4);
		listDisplay.setLineWrap(true);
		return listDisplay;
	}

	private JLabel makeTitleList() {
		String headers = "<html>" + "Course Name:  " + "<br>"
				+ "Semester:  <br> " + "Location:  <br> "
				+ "Instructors:  <br> " + "</html>";
		JLabel label = new JLabel(headers);

		label.setMinimumSize(new Dimension(Integer.MIN_VALUE, label
				.getMinimumSize().height));
		return label;
	}

	private void makeTypeList() {
		typeDisplay.removeAll();
		typeDisplay.setLayout(new GridLayout(NUMBER_ROWS, NUMBER_COLS));
		int i = 0;
		for (int row = 0; row < NUMBER_ROWS; row++) {
			for (int col = 0; col < NUMBER_COLS; col++) {
				final JLabel icon = new JLabel();
				if (i < courseDetails.size()) {
					icon.setText(courseDetails.get(i));
				}
				i += 1;
				typeDisplay.add(icon);
			}
		}
		typeDisplay.revalidate();
	}

	private void registerAsObserver() {
		typeListModel.addObserver(new DetailListObserver() {
			@Override
			public void stateChanged() {
				updateTypeList();
			}
		});
	}

	protected void updateTypeList() {
		addDetailList();
		makeTypeList();
		listDisplay.setText(infoDetails);
	}
}
