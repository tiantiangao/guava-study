package com.gtt.basicutilities;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import org.junit.Test;

import com.google.common.base.Throwables;

/**
 * @author tiantiangao
 */
public class ThrowablesTest {

	@Test
	public void test() {
		testPropagate();
		testPropagateIfInstanceOf();
		testPropagateIfPossible();
		testGetRootCause();
		testGetStackTraceAsString();
		testGetCausalChain();
	}

	private void testPropagate() {
		try {
			URL url = new URL("http://www.dianping.com");
			InputStream in = url.openStream();
			in.close();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	private void testPropagateIfInstanceOf() {
		try {
			throw new NumberFormatException("a");
		} catch (Throwable t) {
			try {
				Throwables.propagateIfInstanceOf(t, NumberFormatException.class);
				fail();
			} catch (Throwable t2) {
				assertTrue(true);
			}
		}
	}

	private void testPropagateIfPossible() {
		try {
			throw new NumberFormatException();
		} catch (Throwable t) {
			try {
				Throwables.propagateIfPossible(t, Exception.class);
				fail();
			} catch (Throwable t1) {
				assertTrue(true);
			}
		}
	}

	private void testGetRootCause() {
		Exception e = new NumberFormatException("a");
		assertEquals(e, Throwables.getRootCause(e));

		IllegalArgumentException e2 = new IllegalArgumentException(e);
		assertEquals(e, Throwables.getRootCause(e2));
	}

	private void testGetStackTraceAsString() {
		try {
			Integer.parseInt("a");
			fail();
		} catch (Exception e) {
			assertTrue(Throwables.getStackTraceAsString(e).startsWith(
					"java.lang.NumberFormatException: For input string: \"a\""));
		}
	}

	private void testGetCausalChain() {
		FileNotFoundException fnfe = new FileNotFoundException();
		IllegalArgumentException iae = new IllegalArgumentException(fnfe);
		RuntimeException re = new RuntimeException(iae);
		IllegalStateException ex = new IllegalStateException(re);

		assertEquals(Arrays.asList(ex, re, iae, fnfe), Throwables.getCausalChain(ex));
		try {
			Throwables.getCausalChain(null);
			fail("Should have throw NPE");
		} catch (NullPointerException expected) {
		}
	}
}
