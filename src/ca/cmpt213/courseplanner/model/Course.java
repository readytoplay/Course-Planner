package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Course class is the information about each course retrieved from the source
 * file.
 * 
 * Data includes semesters of offering, course department, course number,
 * offering locations, enrolement capacity, actual enrolement total, course
 * instructors, and course components.
 * 
 * It supports setting locations other than Burnaby, Surrey, and Hrbrcntr to
 * "OTHER" and returning its data to the calling method.
 */

public class Course {
	private int semester;
	private String subject;
	private String catalogNumber;
	private String location;
	private int enrolementCapacity;
	private int enrolementTotal;
	private ArrayList<String> instructors;
	private String componentcode;
	private String[] campuses = { "BURNABY", "SURREY", "HRBRCNTR", "OTHER" };

	public Course(int semester, String subject, String catalogNumber,
			String location, int enrolementCapacity, int enrolementTotal,
			ArrayList<String> instructorsList, String componentcode) {

		this.semester = semester;
		this.subject = subject;
		this.catalogNumber = catalogNumber;
		setLocation(location);
		this.enrolementCapacity = enrolementCapacity;
		this.enrolementTotal = enrolementTotal;
		this.instructors = setInstructors(instructorsList);
		this.componentcode = componentcode;
	}

	private ArrayList<String> setInstructors(ArrayList<String> instructorsList) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < instructorsList.size(); i++) {

			String instructor = instructorsList.get(i);
			if (instructor.contains("\"")) {
				int index = instructor.indexOf("\"");
				if (index == 0) {
					instructor = instructor.substring(1);
				} else if (index == instructor.length() - 1) {
					instructor = instructor.substring(0,
							instructor.length() - 1);
				} else {
					String a = instructor.substring(0, index);
					a += instructor.substring(index + 1);
					instructor = a;
				}
				if (instructor.indexOf(" ") == 0) {
					instructor = instructor.substring(1);
				}
			}
			result.add(instructor);
		}
		return result;
	}

	private void setLocation(String location) {
		if (!Arrays.asList(campuses).contains(location)) {
			this.location = "OTHER";
		} else {
			this.location = location;
		}
	}

	public String getInformationSemester() {
		if (semester % 10 == 1) {
			return "SPRING";
		} else if (semester % 10 == 4) {
			return "SUMMER";
		} else {
			return "FALL";
		}
	}

	public int getSemester() {
		return semester;
	}

	public String getSubject() {
		return subject;
	}

	public String getCatalogNumber() {
		return catalogNumber;
	}

	public String getLocation() {
		return location;
	}

	public int getEnrolementCapacity() {
		return enrolementCapacity;
	}

	public int getEnrolementTotal() {
		return enrolementTotal;
	}

	public ArrayList<String> getInstructors() {
		return instructors;
	}

	public String getComponentcode() {
		return componentcode;
	}

}
