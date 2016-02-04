package ca.cmpt213.courseplanner.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.cmpt213.courseplanner.model.CourseCollection;
import ca.cmpt213.courseplanner.model.CourseSemesterCollection;
import ca.cmpt213.courseplanner.model.Model;

/**
 * CourseListUI Class displays a list of courses which match the filter criteria
 * the user has entered.
 */

@SuppressWarnings("serial")
public class CourseListUI extends PlannerPanel {
	private final int CELL_WIDTH = 90;
	private final String title = "Course List";
	private String selectedCourseNum = "";
	private JList<String> list;
	private Model model;
	private CourseList listModel;
	private OfferingList semesterList = new OfferingList();
	private DefaultListModel modelDefault = new DefaultListModel();
	private String selectedCourse = "";

	public CourseListUI(final Model model) {
		super(model);
		this.model = model;
		listModel = model.getCourseList();
		model.setSemesterList(semesterList);

		// /// get model data ////
		list = new JList(modelDefault);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFixedCellWidth(CELL_WIDTH);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);

		list.setVisibleRowCount(-1);

		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					if (modelDefault.size() != 0) {
						JList source = (JList) event.getSource();
						selectedCourseNum = (String) source.getSelectedValue();
						if (selectedCourseNum != null) {
							selectedCourseNum = selectedCourseNum
									.substring(selectedCourseNum
											.lastIndexOf(' ') + 1);
						} else {
							// Clear the text since there's no selection
							selectedCourseNum = null;
						}

						for (CourseCollection course : listModel) {
							if (course.getCourseNumber().equals(
									selectedCourseNum)) {
								selectedCourse = course.getDepartment() + " "
										+ selectedCourseNum;
								addSemesterList(selectedCourseNum);
								break;
							}
						}
					}
				}
			}

		});
		JScrollPane listScroller = new JScrollPane(list);

		makeBorder(listScroller);
		displayGeneralPanel(title, listScroller);
		registerAsObserver();
	}

	private void addSemesterList(String selectedCourseNum) {
		List<CourseSemesterCollection> courseSemesters = new ArrayList<CourseSemesterCollection>();
		for (CourseCollection course : listModel) {
			if (course.getCourseNumber().equals(selectedCourseNum)) {
				courseSemesters = course.getCourseYears();
			}
		}
		sortByCourseSemester(courseSemesters);
		semesterList.insert(courseSemesters);
	}

	private void sortByCourseSemester(List<CourseSemesterCollection> courses) {
		Collections.sort(courses, new Comparator<CourseSemesterCollection>() {
			@Override
			public int compare(final CourseSemesterCollection object1,
					final CourseSemesterCollection object2) {
				return Integer.valueOf(object1.getSemester()).compareTo(
						object2.getSemester());
			}
		});
	}

	private void registerAsObserver() {
		listModel.addObserver(new CourseListObserver() {
			@Override
			public void stateChanged() {
				updateCourseList();
			}
		});
		semesterList.addObserver(new OfferingListObserver() {
			@Override
			public void stateChanged() {
				updateSemesterList();
			}
		});
	}

	protected void updateSemesterList() {
		model.setSemesterList(semesterList);
	}

	private void updateCourseList() {
		modelDefault.removeAllElements();
		for (CourseCollection course : listModel) {
			modelDefault.addElement(course.getDepartment() + " "
					+ course.getCourseNumber());
		}
	}

}
