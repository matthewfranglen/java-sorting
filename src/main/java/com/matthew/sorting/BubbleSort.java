/**
 * 
 */
package com.matthew.sorting;

import java.util.Comparator;

/**
 * @author matthew
 * 
 */
public class BubbleSort extends Sorter {

	/**
	 * Performs a single iteration over the array. Alters in place and returns
	 * the number of changes.
	 * 
	 * @param array
	 * @param comparator
	 * @return
	 */
	private <T> int iterate(T[] array, Comparator<T> comparator) {
		int count = 0;
		T copy;

		for (int i = 0; i < array.length - 1; i++) {
			if (comparator.compare(array[i], array[i + 1]) > 0) {
				// First argument is greater than second
				copy = array[i];
				array[i] = array[i + 1];
				array[i + 1] = copy;
				count++;
			}
		}

		return count;
	}

	/**
	 * @param array
	 * @param comparator
	 * @return
	 * @see com.matthew.sorting.Sorter#sort(java.lang.Object[],
	 *      java.util.Comparator)
	 */
	@Override
	public <T> T[] sort(T[] array, Comparator<T> comparator) {
		while (iterate(array, comparator) > 0)
			;

		return array;
	}
}
