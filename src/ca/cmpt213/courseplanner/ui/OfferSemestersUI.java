package ca.cmpt213.courseplanner.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.cmpt213.courseplanner.model.CourseLocationCollection;
import ca.cmpt213.courseplanner.model.CourseSemesterCollection;
import ca.cmpt213.courseplanner.model.CourseTypeCollection;
import ca.cmpt213.courseplanner.model.Model;

/**
 * CourseOfferSemestersUI Class displays the semesters of a course offering.
 */

@SuppressWarnings("serial")
public class OfferSemestersUI extends PlannerPanel {
	private final String title = "Course Offering by Semester";
	private String[] semesters = { "Spring", "Summer", "Fall" };
	private JPanel btnsPanel = new JPanel();
	private OfferingList semesterListModel;
	private StatisticsList statListModel = new StatisticsList();
	private DetailList typeListModel = new DetailList();
	private List<String> yearList = new ArrayList<String>();
	private List<String> semesterList = new ArrayList<String>();
	private List<Integer> statList = new ArrayList<Integer>(Collections.nCopies(7, 0));
	JPanel container = new JPanel();
	GridBagConstraints c = new GridBagConstraints();
	GridBagConstraints label_c = new GridBagConstraints();
	private Model model;

	public OfferSemestersUI(Model model) {
		super(model);
		this.model = model;
		semesterListModel = model.getSemesterList();
		model.setStatList(statListModel);
		model.setTypeList(typeListModel);

		addYearList(semesterListModel);
		container.setLayout(new GridBagLayout());
		container.setBackground(Color.WHITE);
		container.setPreferredSize(new Dimension(50, 400));

		makeBorder(container);
		displayGeneralPanel(title, container);
		JPanel btnsPanel = new JPanel();
		btnsPanel.setLayout(new BoxLayout(btnsPanel, BoxLayout.PAGE_AXIS));

		makeGrid(yearList.size());
		registerAsObserver();
	}

	private void addYearList(OfferingList semesterListModel) {
		yearList.clear();
		int i = 0;
		int firstYear = 0;
		String year = "";
		for (CourseSemesterCollection course : semesterListModel) {
			semesterList.add(Integer.toString(course.getSemester())
					.substring(0));
			year = Integer.toString(course.getSemester()).substring(1, 3);
			if (i == 0) {
				firstYear = Integer.valueOf(year);
			}
			i++;
		}
		if (!year.isEmpty()) {
			int lastYear = Integer.valueOf(year);
			for (int index = firstYear; index <= lastYear; index++) {
				DecimalFormat formatter = new DecimalFormat("00");
				yearList.add(formatter.format(index));
			}
		}
	}

	private void makeGrid(int rows) {
		statList = new ArrayList<Integer>(Collections.nCopies(7, 0));
		container.removeAll();
		for (int row = 0; row < rows + 1; row++) { // rows
			c.gridy = row;
			for (int col = 0; col < 4; col++) { // cols
				c.gridx = col;
				if (c.gridy == 0 && c.gridx != 0) { // label semester
					label_c.gridx = c.gridx;
					label_c.gridy = c.gridy;
					label_c.fill = GridBagConstraints.BOTH;
					JLabel semester;
					if (rows == 0) {
						break;
					} else {
						// /// semester data ////
						semester = new JLabel(semesters[col - 1]);
					}
					semester.setMinimumSize(new Dimension(Integer.MIN_VALUE,
							semester.getMinimumSize().height));
					semester.setBackground(Color.WHITE);
					semester.setOpaque(true);
					container.add(semester, label_c);
				} else if (c.gridx == 0) { // label year
					label_c.gridx = c.gridx;
					label_c.gridy = c.gridy;
					label_c.fill = GridBagConstraints.BOTH;
					JLabel year = new JLabel();
					if (c.gridy != 0) {
						// /// year data ////
						String yearString = "20" + yearList.get(c.gridy - 1)
								+ "  ";
						year = new JLabel(yearString);
					}
					year.setBackground(Color.WHITE);
					year.setOpaque(true);
					container.add(year, label_c);
				} else if (c.gridx != 0 && c.gridy != 0) { // distribute courses
															// buttons
					c.fill = GridBagConstraints.BOTH;
					c.weightx = 1.0;
					c.weighty = 1.0;
					btnsPanel = new JPanel();
					btnsPanel.setLayout(new BoxLayout(btnsPanel,
							BoxLayout.PAGE_AXIS));
					makeBorder(btnsPanel);
					// /// data ////
					String semester = getSemesterCell(yearList.get(c.gridy - 1));
					List<CourseLocationCollection> courseLocations = new ArrayList<CourseLocationCollection>();
					for (CourseSemesterCollection course : semesterListModel) {
						if (course.getSemester() == Integer.parseInt(semester)) {
							String courseSemester = semester.substring(
									semester.length() - 1, semester.length());
							courseLocations = course.getLocationList();
							addStatList(courseSemester, courseLocations.size());
						}
					}
					for (final CourseLocationCollection location : courseLocations) {
						addStatList(location.getLocation(), 0);
						statListModel.setTitle(location.getCourseName());
						JButton button = new JButton(location.getCourseName()
								+ " - " + location.getLocation());
						button.setMaximumSize(new Dimension(Integer.MAX_VALUE,
								button.getMinimumSize().height));
						button.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								addTypeList(location.getTypesList());
							}

						});
						btnsPanel.add(button);
					}
					container.add(btnsPanel, c);
				}
				repaint();
				add(container, BorderLayout.CENTER);
				container.revalidate();
			}

		}
	}

	private String getSemesterCell(String year) {
		String semester = "";
		if (c.gridx - 1 == 0) { // for spring
			semester = "1" + year + "1";
		} else if (c.gridx - 1 == 1) { // for summer
			semester = "1" + year + "4";
		} else if (c.gridx - 1 == 2) { // for fall
			semester = "1" + year + "7";
		}

		return semester;
	}

	private void addStatList(String stat, int count) {
		if (stat.equals("1")) { // for spring
			int spring = statList.get(0);
			spring += count;
			statList.set(0, spring);
		} else if (stat.equals("4")) { // for summer
			int summer = statList.get(1);
			summer += count;
			statList.set(1, summer);
		} else if (stat.equals("7")) { // for fall
			int fall = statList.get(2);
			fall += count;
			statList.set(2, fall);
		} else if (stat.equals("BURNABY")) { // for BURNABY
			int campuas = statList.get(3);
			campuas += 1;
			statList.set(3, campuas);
		} else if (stat.equals("SURREY")) { // for SURREY
			int campuas = statList.get(4);
			campuas += 1;
			statList.set(4, campuas);
		} else if (stat.equals("HRBRCNTR")) { // for HRBRCNTR
			int campuas = statList.get(5);
			campuas += 1;
			statList.set(5, campuas);
		} else { // for OTHER
			int campuas = statList.get(6);
			campuas += 1;
			statList.set(6, campuas);
		}
		statListModel.insert(statList);
	}

	private void addTypeList(List<CourseTypeCollection> typeList) {
		typeListModel.insert(typeList);
	}

	private void registerAsObserver() {
		semesterListModel.addObserver(new OfferingListObserver() {
			@Override
			public void stateChanged() {
				updateSemesterList();
			}
		});
		statListModel.addObserver(new StatisticsListObserver() {
			@Override
			public void stateChanged() {
				updateStatList();
			}
		});
		typeListModel.addObserver(new DetailListObserver() {
			@Override
			public void stateChanged() {
				updateTypeList();
			}
		});
	}

	protected void updateTypeList() {
		model.setTypeList(typeListModel);
	}

	protected void updateStatList() {
		model.setStatList(statListModel);
	}

	protected void updateSemesterList() {
		model.setSemesterList(semesterListModel);
		addYearList(semesterListModel);
		makeGrid(yearList.size());
	}
}
