/**
 * 
 */
package com.matthew.sorting;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author matthew
 * 
 */
public class QuickSortTest {

	@RunWith(JUnit4.class)
	public static class Simple extends SorterTest {
		/**
		 * @return
		 * @see com.matthew.sorting.SorterTest#make()
		 */
		@Override
		protected Sorter make() {
			return QuickSort.simple();
		}
	}

	@RunWith(JUnit4.class)
	public static class InPlace extends SorterTest {
		/**
		 * @return
		 * @see com.matthew.sorting.SorterTest#make()
		 */
		@Override
		protected Sorter make() {
			return QuickSort.inPlace();
		}
	}

}
