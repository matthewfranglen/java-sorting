/**
 * 
 */
package com.matthew.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The quick sort picks a pivot point in the list. This pivot is compared to the
 * entire list, and three lists are generated based on values above, equal to,
 * and below the pivot. The lists for values above and below are then
 * recursively sorted. The recursion stops when a list has only a single
 * element.
 * 
 * @author matthew
 * 
 */
public class QuickSort extends Sorter {

	private final PivotSelector chooser;

	public QuickSort() {
		chooser = new DefaultPivotSelector();
	}

	@SuppressWarnings("unchecked")
	// The unchecked warning comes from the inability to create an array of the
	// generic T type. The type cannot be determined at compile time and as such
	// the array cannot be created.
	private <T> T[] recurse(T[] array, Comparator<T> comparator) {
		final int pivot;
		final T[] above, equal, below;

		if (array.length < 2) {
			return array;
		}

		pivot = chooser.choose(array);
		{
			final List<T> a, e, b;

			a = new ArrayList<T>();
			e = new ArrayList<T>();
			b = new ArrayList<T>();
			e.add(array[pivot]);

			for (int i = 0; i < array.length; i++) {
				if (i != pivot) {
					// The comparison is relative from the pivot to the index,
					// so a positive number indicates the pivot is larger than
					// the index.
					int comparison = comparator.compare(array[pivot], array[i]);

					if (comparison > 0) {
						b.add(array[i]);
					} else if (comparison < 0) {
						a.add(array[i]);
					} else {
						e.add(array[i]);
					}
				}
			}

			above = (T[]) a.toArray();
			equal = (T[]) e.toArray();
			below = (T[]) b.toArray();
		}

		System.arraycopy(this.recurse(below, comparator), 0, array, 0,
				below.length);
		System.arraycopy(equal, 0, array, below.length, equal.length);
		System.arraycopy(this.recurse(above, comparator), 0, array,
				below.length + equal.length, above.length);

		return array;
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
		this.recurse(array, comparator);

		return array;
	}
}

/**
 * The choice of pivot determines the efficiency of the algorithm. The closer
 * the pivot is to the middle of the sorted values, the better.
 * 
 * @author matthew
 * 
 */
interface PivotSelector {
	/**
	 * Selects the pivot to use from the array provided.
	 * 
	 * @param array
	 * @return
	 */
	public <T> int choose(T[] array);
}

/**
 * Picks the middle of the unsorted list. Best case performance on already
 * sorted lists.
 * 
 * @author matthew
 * 
 */
class DefaultPivotSelector implements PivotSelector {
	/**
	 * Always picks the middle value.
	 * 
	 * @param array
	 * @return
	 * @see com.matthew.sorting.PivotSelector#choose(java.lang.Object[])
	 */
	public <T> int choose(T[] array) {
		return array.length / 2;
	}
}

/**
 * Picks the first value of the list. Worst case performance on already sorted
 * lists.
 * 
 * @author matthew
 * 
 */
class BadPivotSelector implements PivotSelector {
	/**
	 * Always picks the first value.
	 * 
	 * @param array
	 * @return
	 * @see com.matthew.sorting.PivotSelector#choose(java.lang.Object[])
	 */
	public <T> int choose(T[] array) {
		return 0;
	}
}