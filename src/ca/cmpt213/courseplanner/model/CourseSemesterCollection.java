package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseSemesterCollection class collects a course's offering semesters. It
 * supporting distributing the course based on location.
 * 
 * Data includes list of CourseLocationCollection for each offering to see where
 * the course is offered in this semester.
 */

public class CourseSemesterCollection {
	private List<CourseLocationCollection> courses = new ArrayList<CourseLocationCollection>();
	private int semester;
	private String courseName;

	public CourseSemesterCollection(int semester) {
		this.semester = semester;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void addCourseLocation(Course course) {
		String location = course.getLocation();
		List<String> insturctorsList = course.getInstructors();
		boolean hasCourse = false;
		for (CourseLocationCollection c : courses) {
			if (c.getLocation().equals(location)) {
				hasCourse = true;
			}
		}
		if (!hasCourse) {
			CourseLocationCollection collection = new CourseLocationCollection(
					location, insturctorsList);
			collection.setSemester(semester);
			collection.setCourseName(courseName);
			courses.add(collection);
		}

	}

	public int getInformationSemester() {
		return getSemester() % 10;
	}

	public void addCourses(Course course) {
		addCourseLocation(course);
		String location = course.getLocation();
		for (CourseLocationCollection c : courses) {
			if (c.getLocation().equals(location)) {
				c.collectCourseByLocation(course);
			}
		}

	}

	public List<CourseLocationCollection> getLocationList() {
		return courses;
	}

	public int getSemester() {
		return semester;
	}

	public String toString() {
		String result = "";
		for (CourseLocationCollection c : courses) {
			result += "\t" + getSemester() + " in ";
			result += c;
		}
		return result;
	}

}
