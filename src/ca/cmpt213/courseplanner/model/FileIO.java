package ca.cmpt213.courseplanner.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * FileIO class supports writing into a text file, reading from a text file, and
 * storing the contents read to be used by the caller method.
 */

public class FileIO {
	private static Model model = new Model();
	private static List<Department> departments = new ArrayList<Department>();
	private static List<Course> courses = new ArrayList<Course>();

	@SuppressWarnings("static-access")
	public static void WriteToFile(String target) {
		File targetFile = new File(target);
		try {
			PrintWriter writer = new PrintWriter(targetFile);
			for (Course course : courses) {
				String subject = course.getSubject();
				boolean hasDepartment = false;
				for (Department department : departments) {
					if (department.getDepartmentName().equals(subject)) {
						hasDepartment = true;
					}
				}
				if (!hasDepartment) {
					departments.add(new Department(subject));
				}
				for (Department department : departments) {
					if (department.getDepartmentName().equals(subject)) {
						department.collectCourse(course);
					}
				}
			}
			for (Department department : departments) {
				writer.println(department);
				System.out.println(department);
			}

			writer.close();
			model.setDepartments(departments);
			model.setModel(model);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void ReadFromFile(String sourcePath) {
		File sourceFile = new File(sourcePath);
		try {
			Scanner scanner = new Scanner(sourceFile);
			scanner.nextLine();
			// Read from the csv file:
			while (scanner.hasNextLine()) {

				String info = scanner.nextLine();

				String[] courseInfo = info.split(",");
				ArrayList<String> instructors = new ArrayList<String>();
				for (int i = 6; i < courseInfo.length - 1; i++) {
					instructors.add(courseInfo[i]);
				}
				Course course = new Course(Integer.parseInt(courseInfo[0]),
						courseInfo[1], courseInfo[2], courseInfo[3],
						Integer.parseInt(courseInfo[4]),
						Integer.parseInt(courseInfo[5]), instructors,
						courseInfo[courseInfo.length - 1]);
				courses.add(course);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"Data file (" + sourceFile.getAbsolutePath()
							+ ") not found.");
			System.exit(0);
			e.printStackTrace();
		}

	}
}
