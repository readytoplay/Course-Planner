package ca.cmpt213.courseplanner.model;

/**
 * CourseTypeCollection class collects a course's components and compute the
 * overall enrolement status.
 * 
 * Data includes enrolement capacity for storing the expecting enrolememt
 * number, enrolement total for storing the actual enrolememt number, and
 * components name for storing the types of curricular components.
 */

public class CourseTypeCollection {
	private String componentcode;

	private int enrolementCapacity = 0;
	private int enrolementTotal = 0;
	private String courseName;
	private int semester;
	private String instructors;
	private String location;

	public CourseTypeCollection(String componentcode, String courseName,
			int semester, String instructors, String location) {
		this.componentcode = componentcode;
		this.courseName = courseName;
		this.semester = semester;
		this.instructors = instructors;
		this.location = location;
	}

	public void addInformation(Course course) {
		this.enrolementCapacity += course.getEnrolementCapacity();
		this.enrolementTotal += course.getEnrolementTotal();

	}

	public int getEnrolementCapacity() {
		return enrolementCapacity;
	}

	public int getEnrolementTotal() {
		return enrolementTotal;
	}

	public String getComponentCode() {
		return componentcode;
	}

	public String toString() {

		String result = "\t\t" + "Type=" + getComponentCode() + ","
				+ "Enrollment=" + getEnrolementTotal() + "/"
				+ getEnrolementCapacity() + "\n";
		return result;

	}

	public String printTypes() {
		String result = getComponentCode() + "," + getEnrolementTotal() + "/" + getEnrolementCapacity();
		return result;
	}

	public String printInfo() {
		if (instructors.equals("(null)")) {
			instructors = "";
		}
		String result = courseName + "\n" + semester + "\n" + location + "\n"
				+ instructors;
		return result;
	}

}
