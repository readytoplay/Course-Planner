package ca.cmpt213.courseplanner.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ca.cmpt213.courseplanner.model.CourseCollection;

/**
 * Simple observable and Iterable list of CourseCollection.
 */
public class CourseList implements Iterable<CourseCollection> {
	private List<CourseCollection> list = new ArrayList<CourseCollection>();

	public void insert(List<CourseCollection> list) {
		this.list = list;
		notifyObservers();
	}

	@Override
	public Iterator<CourseCollection> iterator() {
		return Collections.unmodifiableList(list).iterator();
	}

	/**
	 * Code to handle being observable
	 */
	private List<CourseListObserver> observers = new ArrayList<CourseListObserver>();

	public void addObserver(CourseListObserver observer) {
		observers.add(observer);
	}

	private void notifyObservers() {
		for (CourseListObserver observer : observers) {
			observer.stateChanged();
		}
	}
}
