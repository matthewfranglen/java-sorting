/**
 * 
 */
package com.matthew.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * The merge sort operates by merging ever larger pairs of sorted lists until
 * the entire list is sorted. The process can start from the top down, where
 * recursive calls are made to sort each half of the current list, or bottom up,
 * where the list is split into a list of parts which are merged to form another
 * list of bigger parts (and so on).
 * 
 * @author matthew
 * 
 */
public class MergeSort extends Sorter {

	private final Implementation implementation;

	private MergeSort(Implementation implementation) {
		this.implementation = implementation;
	}

	public static MergeSort bottomUp() {
		return new MergeSort(new BottomUpImplementation());
	}

	public static MergeSort topDown() {
		return new MergeSort(new TopDownImplementation());
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
		return this.implementation.sort(Arrays.asList(array), comparator)
				.toArray(array);
	}
}

/**
 * Since there are two different implementations of merge sort, the actual
 * implementation code needs to be encapsulated.
 * 
 * @author matthew
 * 
 */
abstract class Implementation {
	public abstract <T> List<T> sort(List<T> array, Comparator<T> comparator);

	protected <T> List<T> merge(List<T> one, List<T> two,
			Comparator<T> comparator) {
		List<T> result = new ArrayList<T>();
		int iOne, iTwo;

		for (iOne = 0, iTwo = 0; iOne < one.size() && iTwo < two.size();) {
			if (comparator.compare(one.get(iOne), two.get(iTwo)) > 0) {
				result.add(two.get(iTwo));
				iTwo++;
			} else {
				result.add(one.get(iOne));
				iOne++;
			}
		}

		if (iOne < one.size()) {
			for (; iOne < one.size(); iOne++) {
				result.add(one.get(iOne));
			}
		} else if (iTwo < two.size()) {
			for (; iTwo < two.size(); iTwo++) {
				result.add(two.get(iTwo));
			}
		}

		return result;
	}
}

/**
 * This performs the recursive process of splitting the list, sorting the split
 * lists, and then merging the split lists. The recursion ends when a list with
 * a single element is reached, as that is already sorted.
 * 
 * @author matthew
 * 
 */
class TopDownImplementation extends Implementation {

	/**
	 * @param array
	 * @param comparator
	 * @return
	 * @see com.matthew.sorting.Implementation#sort(java.lang.Object[],
	 *      java.util.Comparator)
	 */
	@Override
	public <T> List<T> sort(List<T> array, Comparator<T> comparator) {
		List<T> one, two;
		int middle;

		if (array.size() < 2) {
			return array;
		}

		middle = array.size() / 2;
		one = this.sort(array.subList(0, middle), comparator);
		two = this.sort(array.subList(middle, array.size()), comparator);

		return this.merge(one, two, comparator);
	}
}

/**
 * This performs the bottom up process of splitting the list into a list of
 * lists containing the smallest parts and then iteratively merging consecutive
 * lists of the current list to form bigger lists of a new list, repeating until
 * there is only a single list in the list. (terrible English)
 * 
 * @author matthew
 * 
 */
class BottomUpImplementation extends Implementation {

	/**
	 * @param array
	 * @param comparator
	 * @return
	 * @see com.matthew.sorting.Implementation#sort(java.lang.Object[],
	 *      java.util.Comparator)
	 */
	@Override
	@SuppressWarnings("unchecked")
	// Passing in a single manual argument to a varargs constructor turns it
	// into a generic array, which gets a warning.
	public <T> List<T> sort(List<T> array, Comparator<T> comparator) {
		List<List<T>> one, two;

		one = new ArrayList<List<T>>();
		two = new ArrayList<List<T>>();
		for (T current : array) {
			one.add(Arrays.asList(current));
		}

		while (one.size() > 1) {
			List<T> first = null;

			for (List<T> current : one) {
				if (first == null) {
					first = current;
				} else {
					two.add(this.merge(first, current, comparator));
					first = null;
				}
			}
			if (first != null) {
				two.add(first);
			}

			{
				List<List<T>> copy = one;

				one.clear();
				one = two;
				two = copy;
			}
		}

		return one.get(0);
	}
}