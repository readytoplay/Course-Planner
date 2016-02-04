package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Department class collects courses that belongs to this department.
 * 
 * Data includes list of CourseCollection for each course and this department
 * name.
 */

public class Department {
	private List<CourseCollection> collections = new ArrayList<CourseCollection>();
	private String departmentName;

	public Department(String departmentName) {
		this.departmentName = departmentName;
	}

	public void addCourseNumber(Course course) {
		String catalogNumber = course.getCatalogNumber();
		boolean hasCourse = false;
		for (CourseCollection collection : collections) {
			if (collection.getCourseNumber().equals(catalogNumber)) {
				hasCourse = true;
			}
		}
		if (!hasCourse) {
			CourseCollection collection = new CourseCollection(catalogNumber);
			collection.setDepartment(departmentName);
			collections.add(collection);
		}

	}

	public void collectCourse(Course course) {
		addCourseNumber(course);
		String catalogNumber = course.getCatalogNumber();
		for (CourseCollection c : collections) {
			if (c.getCourseNumber().equals(catalogNumber)) {
				c.collectCourseByYear(course);
			}
		}
	}

	public List<CourseCollection> getCourseNumber() {
		return collections;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public String toString() {
		String result = "";
		for (CourseCollection c : collections) {
			result += getDepartmentName() + " " + c;
		}
		return result;
	}

}
