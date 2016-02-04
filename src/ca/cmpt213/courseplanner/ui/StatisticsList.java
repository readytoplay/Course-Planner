package ca.cmpt213.courseplanner.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Simple observable and Iterable list of Integer.
 */
public class StatisticsList implements Iterable<Integer> {
	private List<Integer> list = new ArrayList<Integer>();
	private String courseName = "";
	public void insert(List<Integer> list) {
		this.list = list;
		notifyObservers();
	}
	public void setTitle(String courseName) {
		this.courseName = "Course: " + courseName;
		notifyObservers();
	}
	
	public String getTitle() {
		return courseName;
	}
	@Override
	public Iterator<Integer> iterator() {
		return Collections.unmodifiableList(list).iterator();
	}

	/**
	 * Code to handle being observable
	 */
	// (Should put this list at top with other fields!)
	private List<StatisticsListObserver> observers = new ArrayList<StatisticsListObserver>();

	public void addObserver(StatisticsListObserver observer) {
		observers.add(observer);
	}

	private void notifyObservers() {
		for (StatisticsListObserver observer : observers) {
			observer.stateChanged();
		}
	}
}
