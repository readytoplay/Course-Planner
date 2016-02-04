package ca.cmpt213.courseplanner.model;

import ca.cmpt213.courseplanner.ui.MainGUI;

/**
 * Main Class models the behavior of course planner application which shows
 * users when a course has been offered in the past (based on the source file)
 * by calling FileIO class and MainGUI class . It supports reading and turning
 * source file content to more readable format and showing graphical interface.
 * 
 * Data includes the file paths of source file and target file.
 */

public class Main {
	private final static String SOURCEFILE = "data/course_data_2015.csv";
	private final static String TARGETFILE = "docs/output_dump.txt";

	public static void main(String[] args) {
		dumpModel();
		MainGUI.displayUI();
	}

	private static void dumpModel() {
		FileIO.ReadFromFile(SOURCEFILE);
		FileIO.WriteToFile(TARGETFILE);
	}
}
