package ca.cmpt213.courseplanner.ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.cmpt213.bargraph.BarGraphIcon;
import ca.cmpt213.bargraph.BarGraphModel;
import ca.cmpt213.courseplanner.model.Model;

/**
 * StatisticsUI Class displays the statistics of a course offering semesters and
 * locations using bargraph class.
 */

@SuppressWarnings("serial")
public class StatisticsUI extends PlannerPanel {

	private final String title = "Statistics";
	private String[] semesters = { "Spring", "Summer", "Fall" };
	private String[] campuses = { "Bby", "Sry", "Van", "Other" };
	private StatisticsList statListModel = new StatisticsList();
	private int[] statData = new int[7];
	int[] semestersStat = new int[3];
	int[] campusesStat = new int[4];
	private JPanel container;

	public StatisticsUI(Model model) {
		super(model);
		statListModel = model.getStatList();

		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		Dimension newSize = new Dimension(0, 0);
		container.setMaximumSize(newSize);
		// // sample data//
		for (int i = 0; i < 3; i++) {
			semestersStat[i] = statData[i];
		}
		for (int i = 3; i < 7; i++) {
			campusesStat[i - 3] = statData[i];
		}

		makeBarGraph(semestersStat, campusesStat);
		// should trim the bottom space
		makeBorder(container);
		displayGeneralPanel(title, container);

		registerAsObserver();

	}

	private void makeBarGraph(int[] semestersStat, int[] campusesStat) {
		container.removeAll();
		BarGraphModel semestersGraph = new BarGraphModel(semestersStat,semesters);
		BarGraphModel campusesGraph = new BarGraphModel(campusesStat, campuses);
		container.add(new JLabel(statListModel.getTitle()));
		container.add(new JLabel(" "));
		container.add(new JLabel("Semester Offerings:"));
		container.add(new JLabel(new BarGraphIcon(semestersGraph, 220, 150)));
		container.add(new JLabel("Campus Offerings:"));
		container.add(new JLabel(new BarGraphIcon(campusesGraph, 220, 150)));
		container.setMaximumSize(getMinimumSize());
		container.revalidate();
	}

	private void registerAsObserver() {
		statListModel.addObserver(new StatisticsListObserver() {
			@Override
			public void stateChanged() {
				updateStatList();
			}
		});
	}

	protected void updateStatList() {
		int[] semestersStat = new int[3];
		int[] campusesStat = new int[4];
		int index = 0;
		for (Integer stat : statListModel) {
			statData[index] = stat;
			index++;
		}
		for (int i = 0; i < 3; i++) {
			semestersStat[i] = statData[i];
		}
		for (int i = 3; i < 7; i++) {
			campusesStat[i - 3] = statData[i];
		}

		makeBarGraph(semestersStat, campusesStat);
	}

}
