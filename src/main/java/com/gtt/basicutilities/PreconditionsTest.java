package com.gtt.basicutilities;

import static com.google.common.base.Preconditions.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * 
 * @author tiantiangao
 */
public class PreconditionsTest {

	@Test
	public void test() {
		testCheckArgument();
		testCheckNotNull();
		testCheckState();
		testCheckElementIndex();
		testCheckPositionIndex();
		testCheckPositionIndexs();
	}

	private void testCheckArgument() {
		int i = 1;
		checkArgument(i > 0, "参数是%s, 参数必须为正整数", i);

		try {
			i = -1;
			checkArgument(-1 > 0, "参数是%s, 参数必须为正整数", -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	private void testCheckNotNull() {
		Object value = new Object();

		checkNotNull(value, "参数是null");

		try {
			value = null;
			checkNotNull(value, "参数是null");
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	private void testCheckState() {
		ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
		checkState(list.size() < 6, "集体长度应该小于5");

		list.add(6);
		try {
			checkState(list.size() < 6, "集体长度应该小于5");
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	private void testCheckElementIndex() {
		ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);
		// [0, size)
		checkElementIndex(list.size(), 4);

		try {
			checkElementIndex(list.size(), 3);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	private void testCheckPositionIndex() {
		ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);
		// [0, size]
		checkPositionIndex(list.size(), 3);

		try {
			checkPositionIndex(list.size(), 2);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	private void testCheckPositionIndexs() {
		ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
		checkPositionIndexes(4, 5, list.size());

		try {
			checkPositionIndexes(5, 6, list.size());
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
