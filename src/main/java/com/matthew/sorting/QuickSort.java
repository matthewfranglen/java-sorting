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

	private final Recurser recurser;

	private QuickSort(Recurser recurser) {
		this.recurser = recurser;
	}

	public static QuickSort simple() {
		return new QuickSort(new SimpleRecurser(new DefaultPivotSelector()));
	}

	public static QuickSort inPlace() {
		return new QuickSort(new InPlaceRecurser(new DefaultPivotSelector()));
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
		this.recurser.recurse(array, 0, array.length, comparator);

		return array;
	}
}

/**
 * The method that implements quick sort has been split into a class because
 * there are two separate implementations available.
 * 
 * @author matthew
 * 
 */
abstract class Recurser {
	protected final PivotSelector chooser;

	public Recurser(PivotSelector chooser) {
		this.chooser = chooser;
	}

	public abstract <T> void recurse(T[] array, int start, int length,
			Comparator<T> comparator);
}

/**
 * The SimpleRecurser just creates new arrays to handle the three separate lists
 * that need to be handled. This uses more memory but has simpler code.
 * 
 * @author matthew
 * 
 */
class SimpleRecurser extends Recurser {

	/**
	 * @param chooser
	 */
	public SimpleRecurser(PivotSelector chooser) {
		super(chooser);
	}

	@Override
	@SuppressWarnings("unchecked")
	// The unchecked warning comes from the inability to create an array of the
	// generic T type. The type cannot be determined at compile time and as such
	// the array cannot be created.
	public <T> void recurse(T[] array, int start, int length,
			Comparator<T> comparator) {
		final int pivot;
		final T[] above, equal, below;

		if (array.length < 2) {
			return;
		}

		pivot = this.chooser.choose(array, 0, array.length);
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

		this.recurse(below, 0, below.length, comparator);
		this.recurse(above, 0, above.length, comparator);

		System.arraycopy(below, 0, array, 0, below.length);
		System.arraycopy(equal, 0, array, below.length, equal.length);
		System.arraycopy(above, 0, array, below.length + equal.length,
				above.length);
	}
}

/**
 * The InPlaceRecurser uses swaps to update the original array in place. This
 * does not duplicate the array at all, leading to a constant memory use cost
 * (there are variables related to the algorithm).
 * 
 * @author matthew
 * 
 */
class InPlaceRecurser extends Recurser {

	/**
	 * @param chooser
	 */
	public InPlaceRecurser(PivotSelector chooser) {
		super(chooser);
	}

	@Override
	public <T> void recurse(T[] array, int start, int length,
			Comparator<T> comparator) {
		T pivot;
		int equalStart, equalEnd;

		if (length < 2) {
			return;
		}

		// Move the pivot to the start of the array.
		{
			int p = chooser.choose(array, start, length);
			pivot = array[p];
			if (p != start) {
				array[p] = array[start];
				array[start] = pivot;
			}
		}

		// Sort the values into three lists by maintaining a block of equal
		// values in the start/middle and shifting it as the values are
		// evaluated.
		equalEnd = start + 1;
		equalStart = start;
		for (int end = start + length - 1; equalEnd <= end;) {
			int comparison = comparator.compare(pivot, array[end]);

			if (comparison > 0) {
				// Pivot greater than end of array. Need to shift the equal
				// block one over and move the end to the new space.
				T copy = array[equalStart];
				array[equalStart] = array[end];
				array[end] = array[equalEnd];
				array[equalEnd] = copy;
				equalStart++;
				equalEnd++;
			} else if (comparison == 0) {
				// Pivot equal. Swap end with first unevaluated value after
				// equal block to expand the equal block.
				T copy = array[equalEnd];
				array[equalEnd] = array[end];
				array[end] = copy;
				equalEnd++;
			} else {
				// End of array greater than pivot, already in right place but
				// need to consider the next position down next time.
				end--;
			}
		}

		// equalStart indicates the first value that is equal to the pivot. That
		// index should not move, as everything before it is less than the
		// pivot.
		this.recurse(array, start, equalStart - start, comparator);

		// equalEnd indicates the first value that is greater than the pivot.
		// That index is the start of the values that are higher than the pivot.
		this.recurse(array, equalEnd, start + length - equalEnd, comparator);
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
	public <T> int choose(T[] array, int start, int length);
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
	public <T> int choose(T[] array, int start, int length) {
		return start + (length / 2);
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
	public <T> int choose(T[] array, int start, int length) {
		return start;
	}
}