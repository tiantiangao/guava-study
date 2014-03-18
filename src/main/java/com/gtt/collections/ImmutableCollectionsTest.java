package com.gtt.collections;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.*;

/**
 * @author tiantiangao
 */
public class ImmutableCollectionsTest {

	@Test
	public void test() {
		testJDKUnmodifiedList();
		testCreate();
		testAsList();
	}

	private void testJDKUnmodifiedList() {
		List<String> lists = Lists.newArrayList("aa", "bb", "cc");

		List<String> unmodifiedLists = Collections.unmodifiableList(lists);
		assertEquals(3, unmodifiedLists.size());

		lists.add("dd");
		assertEquals(4, unmodifiedLists.size());
	}

	private void testCreate() {
		testCopyOf();
		testOf();
		testBuilder();
	}

	private void testCopyOf() {
		ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);
		ImmutableList<Integer> unmodifiedList = ImmutableList.copyOf(list);
		assertEquals(3, unmodifiedList.size());

		list.add(4);
		assertEquals(3, unmodifiedList.size());
	}

	private void testOf() {
		assertEquals(4, ImmutableList.of(1, 2, 3, 4).size());
		assertEquals(4, ImmutableSet.of(1, 2, 3, 4).size());
		assertEquals(4, ImmutableMap.of("aa", 1, "bb", 2, "cc", 3, "dd", 4).entrySet().size());
		assertEquals(4, (Object) ImmutableMap.of("aa", 1, "bb", 2, "cc", 3, "dd", 4).get("dd"));
	}

	private void testBuilder() {
	}

	private void testAsList() {
		ImmutableSortedSet<Integer> iset = ImmutableSortedSet.of(5, 2, 3, 4, 1);
		ImmutableList<Integer> ilist = iset.asList();

		List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
		assertEquals(list, ilist);
	}
}
