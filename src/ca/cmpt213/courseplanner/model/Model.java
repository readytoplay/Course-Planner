package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.List;
import ca.cmpt213.courseplanner.ui.CourseList;
import ca.cmpt213.courseplanner.ui.DetailList;
import ca.cmpt213.courseplanner.ui.OfferingList;
import ca.cmpt213.courseplanner.ui.StatisticsList;

/**
 * Model Class is an object class collecting all information of each model
 * classes.
 * 
 * Data includes the list of department, list of courses of chosen department,
 * and the statistics of a chosen course
 */

public class Model {

	private static List<String> coursesList = new ArrayList<String>();
	private static List<Department> departmentList = new ArrayList<Department>();
	private String courseStat;
	private static Model model;
	private CourseList courseListModel;
	private OfferingList semesterListModel;
	private StatisticsList statListModel;
	private DetailList typeListModel;

	public Model() {
	}

	public static void setCollection(List<CourseCollection> collections) {
	}

	public String getCourseStat() {
		return courseStat;
	}

	public static List<Department> getDepartments() {
		return departmentList;
	}

	public static List<String> getCourses() {
		return coursesList;
	}

	public static void setDepartments(List<Department> departments) {
		departmentList = departments;
	}

	@SuppressWarnings("static-access")
	public void setModel(Model model) {
		this.model = model;
	}

	public static Model getModel() {
		return model;
	}

	public void setCourseList(CourseList courseListModel) {
		this.courseListModel = courseListModel;
	}

	public CourseList getCourseList() {
		return courseListModel;
	}

	public void setSemesterList(OfferingList semesterListModel) {
		this.semesterListModel = semesterListModel;
	}

	public OfferingList getSemesterList() {
		return semesterListModel;
	}

	public void setStatList(StatisticsList statListModel) {
		this.statListModel = statListModel;
	}

	public StatisticsList getStatList() {
		return statListModel;
	}

	public void setTypeList(DetailList typeListModel) {
		this.typeListModel = typeListModel;
	}

	public DetailList getTypeList() {
		return typeListModel;
	}

}
