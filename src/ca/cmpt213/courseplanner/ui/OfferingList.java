package ca.cmpt213.courseplanner.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ca.cmpt213.courseplanner.model.CourseSemesterCollection;

/**
 * Simple observable and Iterable list of CourseSemesterCollection.
 */
public class OfferingList implements Iterable<CourseSemesterCollection> {
	private List<CourseSemesterCollection> list = new ArrayList<CourseSemesterCollection>();

	public void insert(List<CourseSemesterCollection> list) {
		this.list = list;
		notifyObservers();
	}
	
	@Override
	public Iterator<CourseSemesterCollection> iterator() {
		return Collections.unmodifiableList(list).iterator();
	}

	/**
	 * Code to handle being observable
	 */
	// (Should put this list at top with other fields!)
	private List<OfferingListObserver> observers = new ArrayList<OfferingListObserver>();

	public void addObserver(OfferingListObserver observer) {
		observers.add(observer);
	}

	private void notifyObservers() {
		for (OfferingListObserver observer : observers) {
			observer.stateChanged();
		}
	}
}
