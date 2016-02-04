package ca.cmpt213.courseplanner.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ca.cmpt213.courseplanner.model.CourseTypeCollection;

/**
 * Simple observable and Iterable list of CourseTypeCollection.
 */
public class DetailList implements Iterable<CourseTypeCollection> {
	private List<CourseTypeCollection> list = new ArrayList<CourseTypeCollection>();

	public void insert(List<CourseTypeCollection> list) {
		this.list = list;
		notifyObservers();
	}
	
	@Override
	public Iterator<CourseTypeCollection> iterator() {
		return Collections.unmodifiableList(list).iterator();
	}

	/**
	 * Code to handle being observable
	 */
	// (Should put this list at top with other fields!)
	private List<DetailListObserver> observers = new ArrayList<DetailListObserver>();

	public void addObserver(DetailListObserver observer) {
		observers.add(observer);
	}

	private void notifyObservers() {
		for (DetailListObserver observer : observers) {
			observer.stateChanged();
		}
	}
}