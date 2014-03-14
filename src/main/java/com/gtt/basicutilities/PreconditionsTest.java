package com.gtt.basicutilities;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * 
 * @author tiantiangao
 */
public class PreconditionsTest {

	@Test
	public void test() {
		testCheckArgument();
		testCheckNotNull();

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
}
