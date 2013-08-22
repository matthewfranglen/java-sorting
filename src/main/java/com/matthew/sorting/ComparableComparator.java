/**
 * 
 */
package com.matthew.sorting;

import java.util.Comparator;

/**
 * Comparable to Comparator adapter.
 * 
 * @author matthew
 * 
 * @param <T>
 */
class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {

	/**
	 * Compares the objects using the compareTo method.
	 * 
	 * @param one
	 * @param two
	 * @return
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(T one, T two) {
		return one.compareTo(two);
	}
}