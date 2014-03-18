package com.gtt.collections;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

/**
 * @author tiantiangao
 */
public class ImmutableCollectionsTest {

	@Test
	public void test() {
		testJDKUnmodifiedList();
		testAsList();
	}

	private void testJDKUnmodifiedList() {
		List<String> lists = Lists.newArrayList("aa", "bb", "cc");

		List<String> unmodifiedLists = Collections.unmodifiableList(lists);
		assertEquals(3, unmodifiedLists.size());

		lists.add("dd");
		assertEquals(4, unmodifiedLists.size());
	}

	private void testAsList() {
		ImmutableSet<Integer> iset= ImmutableSet.of(1, 2, 3, 4, 5);
		ImmutableList<Integer> ilist= iset.asList();
		System.out.println(iset);
		System.out.println(ilist.get(2));
	}
}
