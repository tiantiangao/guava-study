package com.gtt.collections;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author tiantiangao
 */
public class ImmutableCollectionsTest {

	@Test
	public void test() {
		testJDKUnmodifiedList();
	}

	private void testJDKUnmodifiedList() {
		List<String> lists = Lists.newArrayList("aa", "bb", "cc");

		List<String> unmodifiedLists = Collections.unmodifiableList(lists);
		assertEquals(3, unmodifiedLists.size());

		lists.add("dd");
		assertEquals(4, unmodifiedLists.size());
	}

}
