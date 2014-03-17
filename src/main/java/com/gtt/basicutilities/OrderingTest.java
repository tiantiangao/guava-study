package com.gtt.basicutilities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

/**
 * @author tiantiangao
 */
public class OrderingTest {

	@Test
	public void test() {
		testNatural();
		testFrom();
		testReverse();
		testNullFirst();
		testNullLast();
		testCompound();
		testOnResultOf();
		testGreatestOf();
		testLeastOf();
		testIsOrdered();
		testIsStrictlyOrdered();
		testSortedCopy();
	}

	private void testNatural() {
		// test int order
		List<Integer> unorderedIntList = Lists.newArrayList(5, 3, 2, 4, 1);
		List<Integer> orderedIntList = Lists.newArrayList(1, 2, 3, 4, 5);
		Collections.sort(unorderedIntList, Ordering.natural());
		assertTrue(orderedIntList.equals(unorderedIntList));

		// test string order
		List<String> unorderedStringList = Lists.newArrayList("Test", "Jerry", "Rock", "Ohaha", "Yeah");
		List<String> orderedStringList = Lists.newArrayList("Jerry", "Ohaha", "Rock", "Test", "Yeah");
		Collections.sort(unorderedStringList, Ordering.natural());
		assertTrue(orderedStringList.equals(unorderedStringList));
	}

	private void testFrom() {
		List<Integer> unorderedIntList = Lists.newArrayList(5, 3, 2, 4, 1);
		List<Integer> orderedIntList = Lists.newArrayList(1, 2, 3, 4, 5);
		Collections.sort(unorderedIntList, Ordering.from(new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				return i1.compareTo(i2);
			}
		}));
		assertTrue(orderedIntList.equals(unorderedIntList));
	}

	private void testReverse() {
		List<Integer> unorderedIntList = Lists.newArrayList(5, 3, 2, 4, 1);
		List<Integer> orderedIntList = Lists.newArrayList(5, 4, 3, 2, 1);
		Collections.sort(unorderedIntList, Ordering.natural().reverse());
		assertTrue(orderedIntList.equals(unorderedIntList));
	}

	private void testNullFirst() {
		List<Integer> unorderedIntList = Lists.newArrayList(5, 3, null, 4, 1);
		List<Integer> orderedIntList = Lists.newArrayList(null, 1, 3, 4, 5);
		Collections.sort(unorderedIntList, Ordering.natural().nullsFirst());
		assertTrue(orderedIntList.equals(unorderedIntList));
	}

	private void testNullLast() {
		List<Integer> unorderedIntList = Lists.newArrayList(5, 3, null, 4, 1);
		List<Integer> orderedIntList = Lists.newArrayList(1, 3, 4, 5, null);
		Collections.sort(unorderedIntList, Ordering.natural().nullsLast());
		assertTrue(orderedIntList.equals(unorderedIntList));
	}

	private void testCompound() {
		List<String> unorderedStringList = Lists.newArrayList("Oest", "Jerry", "Jock", "Ohaha", "Yeah");
		List<String> orderedStringList = Lists.newArrayList("Jock", "Jerry", "Ohaha", "Oest", "Yeah");

		Ordering<String> firstLetterOrdering = Ordering.from(new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.substring(0, 1).compareTo(s2.substring(0, 1));
			}
		});
		Collections.sort(unorderedStringList, firstLetterOrdering.compound(new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s2.substring(1, s2.length()).compareTo(s1.substring(1, s1.length()));
			}
		}));
		assertTrue(orderedStringList.equals(unorderedStringList));
	}

	private void testOnResultOf() {
		List<String> unorderedStringList = Lists.newArrayList("Oest", "Jarry", "Jock", "Ohaha", "Ybah");
		List<String> orderedStringList = Lists.newArrayList("Jarry", "Ybah", "Oest", "Ohaha", "Jock");

		Ordering<String> secondLetterOrdering = Ordering.natural().onResultOf(new Function<String, String>() {
			@Override
			public String apply(String input) {
				// 去除首字母
				return input.substring(1, input.length());
			}
		});

		Collections.sort(unorderedStringList, secondLetterOrdering);
		assertTrue(orderedStringList.equals(unorderedStringList));
	}

	private void testGreatestOf() {
		List<Integer> unorderList = Lists.newArrayList(5, 3, 2, 4, 1);
		List<Integer> orderList = Lists.newArrayList(5, 4);
		assertTrue(orderList.equals(Ordering.natural().greatestOf(unorderList, 2)));

		orderList = Lists.newArrayList(5, 4, 3, 2, 1);
		assertTrue(orderList.equals(Ordering.natural().greatestOf(unorderList, 8)));
	}

	private void testLeastOf() {
		List<Integer> unorderList = Lists.newArrayList(5, 3, 2, 4, 1);
		List<Integer> orderList = Lists.newArrayList(1, 2);
		assertTrue(orderList.equals(Ordering.natural().leastOf(unorderList, 2)));

		orderList = Lists.newArrayList(1, 2, 3, 4, 5);
		assertTrue(orderList.equals(Ordering.natural().leastOf(unorderList, 8)));
	}

	private void testIsOrdered() {
		// 大于可通过
		List<Integer> orderList = Lists.newArrayList(1, 2, 3, 4, 5);
		assertTrue(Ordering.natural().isOrdered(orderList));

		// 大于或等于也可通过
		orderList = Lists.newArrayList(1, 2, 2, 4, 5);
		assertTrue(Ordering.natural().isOrdered(orderList));
	}

	private void testIsStrictlyOrdered() {
		// 大于可通过
		List<Integer> orderList = Lists.newArrayList(1, 2, 3, 4, 5);
		assertTrue(Ordering.natural().isStrictlyOrdered(orderList));

		// 大于或等于不可通过
		orderList = Lists.newArrayList(1, 2, 2, 4, 5);
		assertFalse(Ordering.natural().isStrictlyOrdered(orderList));
	}

	private void testSortedCopy() {
		List<Integer> unorderList = Lists.newArrayList(5, 3, 2, 4, 1);
		List<Integer> orderList = Lists.newArrayList(1, 2, 3, 4, 5);
		assertTrue(orderList.equals(Ordering.natural().sortedCopy(unorderList)));
	}
}
