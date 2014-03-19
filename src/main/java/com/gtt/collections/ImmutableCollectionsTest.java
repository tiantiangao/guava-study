package com.gtt.collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author tiantiangao
 */
public class ImmutableCollectionsTest {

	@Test
	public void test() {
		testJDKUnmodifiedList();
		testCreate();
		testAsList();
		testImmutableXXX();
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
		ImmutableMap<Object, Object> map = ImmutableMap.builder().put("aaa", 1).put("bbb", 2).put("ccc", 3).build();
		assertEquals(3, map.size());
		assertEquals(1, map.get("aaa"));
		assertEquals(2, map.get("bbb"));
		assertEquals(3, map.get("ccc"));
	}

	private void testAsList() {
		ImmutableSortedSet<Integer> iset = ImmutableSortedSet.of(5, 2, 3, 4, 1);
		ImmutableList<Integer> ilist = iset.asList();

		List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
		assertEquals(list, ilist);
	}

	private void testImmutableXXX() {
		assertEquals(5, ImmutableList.of(1, 2, 3, 4, 5).size());
		assertEquals(5, ImmutableSet.of(1, 2, 3, 4, 5).size());
		assertEquals(5, ImmutableSortedSet.of(1, 2, 3, 4, 5).size());
		assertEquals(3, ImmutableMap.of(1, 2, 3, 4, 5, 6).size());
		assertEquals(3, ImmutableSortedMap.of(1, 2, 3, 4, 5, 6).size());
		assertEquals(9, ImmutableMultiset.of(1, 1, 2, 2, 3, 3, 4, 5, 6).size());
		assertEquals(6, ImmutableMultiset.of(1, 1, 2, 2, 3, 3, 4, 5, 6).elementSet().size());
		assertEquals(2, ImmutableMultiset.of(1, 1, 2, 2, 3, 3, 4, 5, 6).count(1));
	}
}
