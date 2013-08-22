/**
 * 
 */
package com.matthew.sorting;

/**
 * @author matthew
 * 
 */
public class BubbleSortTest extends SorterTest {

	/**
	 * @param testName
	 */
	public BubbleSortTest(String testName) {
		super(testName);
	}

	/**
	 * @return
	 * @see com.matthew.sorting.SorterTest#make()
	 */
	@Override
	protected Sorter make() {
		return new BubbleSort();
	}

}
