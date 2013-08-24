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
public class MergeSortTest {

	@RunWith(JUnit4.class)
	public static class BottomUp extends SorterTest {
		/**
		 * @return
		 * @see com.matthew.sorting.SorterTest#make()
		 */
		@Override
		protected Sorter make() {
			return MergeSort.bottomUp();
		}
	}

	@RunWith(JUnit4.class)
	public static class TopDown extends SorterTest {
		/**
		 * @return
		 * @see com.matthew.sorting.SorterTest#make()
		 */
		@Override
		protected Sorter make() {
			return MergeSort.topDown();
		}
	}

}
