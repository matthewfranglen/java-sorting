/**
 * 
 */
package com.matthew.sorting;

import java.util.Comparator;

/**
 * @author matthew
 * 
 */
public abstract class Sorter {

	/**
	 * Sort the provided array using the results of the comparator.
	 * 
	 * @param array
	 * @param comparator
	 * @return
	 */
	public abstract <T> T[] sort(T[] array, Comparator<T> comparator);

	public <T extends Comparable<T>> T[] sort(T[] array) {
		return this.sort(array, new ComparableComparator<T>());
	}
}