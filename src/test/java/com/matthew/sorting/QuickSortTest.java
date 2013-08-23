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
@RunWith(JUnit4.class)
public class QuickSortTest extends SorterTest {

	/**
	 * @return
	 * @see com.matthew.sorting.SorterTest#make()
	 */
	@Override
	protected Sorter make() {
		return new QuickSort();
	}

}
