package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ca.cmpt213.courseplanner.model.CourseCollection;
import ca.cmpt213.courseplanner.model.Department;
import ca.cmpt213.courseplanner.model.Model;

;

/**
 * CourseListFilterUI Class allows the user to select which courses should be
 * shown in the Course List panel
 */

@SuppressWarnings("serial")
public class CourseListFilterUI extends PlannerPanel {
	private List<Department> departments = new ArrayList<Department>();
	private final String title = "Course List Filter";
	private JComboBox<String> myComboBox;
	private JCheckBox includeUndergrad;
	private JCheckBox includeGrad;
	private CourseList courseList = new CourseList();
	private Model model;
	private String selectedDepartment = "";

	@SuppressWarnings("static-access")
	public CourseListFilterUI(Model model) {
		super(model);
		model.setCourseList(courseList);
		this.model = model;

		departments = model.getDepartments();
		sortByDepartmentName();
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		makeBorder(container);
		container.add(makeComboBox(), BorderLayout.NORTH);
		container.add(makeCheckBox(), BorderLayout.CENTER);
		container.add(makeUpdateButton(), BorderLayout.SOUTH);

		displayGeneralPanel(title, container);
		registerAsObserver();

	}

	private void sortByDepartmentName() {
		Collections.sort(departments, new Comparator<Department>() {
			@Override
			public int compare(final Department object1,
					final Department object2) {
				return object1.getDepartmentName().compareTo(
						object2.getDepartmentName());
			}
		});
	}

	private JPanel makeComboBox() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		Vector<String> options = new Vector<String>();
		for (Department department : departments) {
			options.add(department.getDepartmentName());
		}

		myComboBox = new JComboBox<String>(options);
		panel.add(makeLabel("Department ", Color.BLACK));
		panel.add(myComboBox);
		return panel;
	}

	private JPanel makeCheckBox() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		includeUndergrad = new JCheckBox("Include undergrad courses");
		includeGrad = new JCheckBox("Include grad courses");
		panel.add(includeUndergrad);
		panel.add(includeGrad);
		return panel;
	}

	private JPanel makeUpdateButton() {
		// push button to the right
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(Box.createHorizontalGlue());

		JButton updateBtn = new JButton("Update Course List");
		panel.add(updateBtn);
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedDepartment = String.valueOf(myComboBox
						.getSelectedItem());
				boolean hasUndergrad = includeUndergrad.isSelected();
				boolean hasGrad = includeGrad.isSelected();

				addCourseList(selectedDepartment, hasUndergrad, hasGrad);
			}
		});
		return panel;
	}

	private void registerAsObserver() {
		courseList.addObserver(new CourseListObserver() {
			@Override
			public void stateChanged() {
				updateCourseList();
			}
		});
	}

	private void addCourseList(String selectedDepartment, boolean hasUndergrad,
			boolean hasGrad) {
		List<CourseCollection> courses = new ArrayList<CourseCollection>();
		for (int i = 0; i < departments.size(); i++) {
			boolean matchDepartmentName = departments.get(i)
					.getDepartmentName().equals(selectedDepartment);
			if (matchDepartmentName && hasUndergrad && hasGrad) {
				for (CourseCollection course : departments.get(i)
						.getCourseNumber()) {
					courses.add(course);
				}
			} else if (matchDepartmentName && !hasUndergrad && hasGrad) {
				for (CourseCollection course : departments.get(i)
						.getCourseNumber()) {
					char initial = course.getCourseNumber().charAt(0);
					if (initial != 'X') {
						if (Character.getNumericValue(initial) >= 5) {
							courses.add(course);
						}
					}
				}
			} else if (matchDepartmentName && hasUndergrad && !hasGrad) {
				for (CourseCollection course : departments.get(i)
						.getCourseNumber()) {
					char initial = course.getCourseNumber().charAt(0);
					if (initial == 'X'
							|| Character.getNumericValue(initial) <= 4) {
						courses.add(course);
					}
				}
			}
		}
		sortByCourseName(courses);
		courseList.insert(courses);
	}

	private void sortByCourseName(List<CourseCollection> courses) {
		Collections.sort(courses, new Comparator<CourseCollection>() {
			@Override
			public int compare(final CourseCollection object1,
					final CourseCollection object2) {
				return object1.getCourseNumber().compareTo(
						object2.getCourseNumber());
			}
		});
	}

	private void updateCourseList() {
		model.setCourseList(courseList);
	}
}
