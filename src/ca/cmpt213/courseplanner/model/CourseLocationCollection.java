package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseLocationCollection class collects a course's offering locations and
 * instructors of this location based on the components of this course.
 * 
 * Data includes list of CourseTypeCollection to get the course's components,
 * course instructors, and offering location.
 */

public class CourseLocationCollection {
	private List<CourseTypeCollection> types = new ArrayList<CourseTypeCollection>();
	private List<String> instructors = new ArrayList<String>();
	private String location;
	private int semester;
	private String courseName;

	public CourseLocationCollection(String location, List<String> instructors) {
		this.location = location;
	}

	private void addType(Course course) {
		addInformation(course);
		String componentCode = course.getComponentcode();
		boolean hasCourse = false;
		for (CourseTypeCollection t : types) {
			if (t.getComponentCode().equals(componentCode)) {
				hasCourse = true;
			}
		}

		if (!hasCourse) {
			CourseTypeCollection component = new CourseTypeCollection(
					componentCode, courseName, semester, getInstructorAll(),
					location);
			types.add(component);
		}

	}

	private void addInformation(Course course) {
		ArrayList<String> courseInstructors = course.getInstructors();
		for (int i = 0; i < courseInstructors.size(); i++) {
			boolean has = false;
			for (int j = 0; j < this.instructors.size(); j++) {
				if (this.instructors.get(j).equals(courseInstructors.get(i))) {
					has = true;
				}
			}
			if (!has) {
				this.instructors.add(courseInstructors.get(i));
			}
		}
	}

	public void collectCourseByLocation(Course course) {
		addType(course);
		String componentCode = course.getComponentcode();
		for (CourseTypeCollection t : types) {
			if (t.getComponentCode().equals(componentCode)) {
				t.addInformation(course);
			}

		}
	}

	public List<CourseTypeCollection> getTypesList() {
		return types;
	}

	public List<String> getInstructors() {
		return instructors;
	}

	public String getLocation() {

		return location;
	}

	public String getInstructorAll() {
		String result = "";
		for (int i = 0; i < getInstructors().size() - 1; i++) {
			result += getInstructors().get(i) + ",";
		}
		result += getInstructors().get(getInstructors().size() - 1);
		return result;
	}

	public String toString() {
		String result = getLocation() + " by ";
		result += getInstructorAll();

		result += "\n";
		for (CourseTypeCollection t : types) {
			result += t;
		}
		return result;
	}

	public void setSemester(int semester) {
		this.semester = semester;

	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseName() {
		return courseName;
	}

}
