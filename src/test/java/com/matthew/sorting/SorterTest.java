/**
 * 
 */
package com.matthew.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author matthew
 * 
 */
public abstract class SorterTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public SorterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(BubbleSortTest.class);
	}

	protected abstract Sorter make();

	public void testSort() {
		final Sorter sorter = make();
		final TestData<?>[] data = new TestData[] {
				new TestData<Integer>(1, 2, 3, 4, 5),
				new TestData<String>("apple", "cherry", "banana"),
				new TestData<Double>(1.0, 2.0, 2.5, 3.5) };

		for (TestData<?> test : data) {
			assertEquals(test.sorted(), test.random(sorter));
		}
	}
}

/**
 * This holds different sets of data that can be sorted. This works using Lists
 * to allow assertEquals to work, and for better printing.
 * 
 * @author matthew
 * 
 * @param <T>
 */
class TestData<T extends Comparable<T>> {
	public final T[] sorted;

	public TestData(T... raw) {
		T[] data = Arrays.copyOf(raw, raw.length);
		Arrays.sort(data, new ComparableComparator<T>());
		this.sorted = data;
	}

	/**
	 * This will pass the data in random order through the sorter.
	 * 
	 * @param sorter
	 * @return
	 */
	public List<T> random(Sorter sorter) {
		T[] data = Arrays.copyOf(this.sorted, this.sorted.length);
		Arrays.sort(data, new RandomComparator());
		return Arrays.asList(sorter.sort(data));
	}

	public List<T> sorted() {
		return Arrays.asList(this.sorted);
	}
}

class RandomComparator implements Comparator<Object> {
	private final static Random generator = new Random(
			System.currentTimeMillis());

	/**
	 * @param one
	 * @param two
	 * @return
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object one, Object two) {
		return generator.nextInt(3) - 1;
	}
}