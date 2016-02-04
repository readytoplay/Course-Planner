package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * CourseCollection is a collection of a course with course number (i.e. CMPT
 * 213) and the semester of which the course is offered.
 * 
 * Data includes list of CourseSemesterCollection to get the course's offering
 * semesters and course number
 */

public class CourseCollection {
	private String courseNumber;
	private List<CourseSemesterCollection> semester_course = new ArrayList<CourseSemesterCollection>();
	private String department;

	public CourseCollection(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public void addYearCourse(Course course) {
		int semester = course.getSemester();
		boolean hasCourse = false;
		for (CourseSemesterCollection y : semester_course) {
			if (y.getSemester() == semester) {
				hasCourse = true;
			}
		}
		if (!hasCourse) {
			CourseSemesterCollection collection = new CourseSemesterCollection(
					semester);
			collection.setCourseName(department + " " + courseNumber);
			semester_course.add(collection);
		}

	}

	public void collectCourseByYear(Course course) {
		addYearCourse(course);
		int semester = course.getSemester();
		for (CourseSemesterCollection y : semester_course) {
			if (y.getSemester() == semester) {
				y.addCourses(course);
			}
		}
	}

	public List<CourseSemesterCollection> getCourseYears() {
		return semester_course;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	private void sortBySemester() {
		Comparator<CourseSemesterCollection> comparator = createSemesterComparator();
		java.util.Collections.sort(this.semester_course, comparator);

	}

	private Comparator<CourseSemesterCollection> createSemesterComparator() {
		return new Comparator<CourseSemesterCollection>() {
			@Override
			public int compare(CourseSemesterCollection course1,
					CourseSemesterCollection course2) {

				int diff = course1.getSemester() - course2.getSemester();
				return diff;
			}
		};
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartment() {
		return department;
	}

	public String toString() {
		sortBySemester();
		String result = getCourseNumber() + "\n";
		for (CourseSemesterCollection c : semester_course) {
			result += c;
		}
		return result;
	}
}
