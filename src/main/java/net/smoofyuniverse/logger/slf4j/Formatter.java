/*
 * Copyright (c) 2017-2021 Hugo Dupanloup (Yeregorix)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.smoofyuniverse.logger.slf4j;

import net.smoofyuniverse.logger.core.Logger;

import java.util.HashMap;
import java.util.Map;

// Modified version of org.slf4j.helpers.MessageFormatter
public class Formatter {
	private static final Logger logger = Logger.get("Formatter");

	private static final String DELIM_STR = "{}";
	private static final char DELIM_START = '{', DELIM_STOP = '}', ESCAPE_CHAR = '\\';

	public static String format(String pattern, Object[] args) {
		if (args == null || args.length == 0)
			return pattern;

		Object lastObj = args[args.length - 1];
		if (lastObj instanceof Throwable)
			return format(pattern, args, args.length - 1, (Throwable) lastObj);

		return format(pattern, args, args.length, null);
	}

	public static String format(String pattern, Object[] args, Throwable throwable) {
		if (args == null && throwable == null)
			return pattern;

		if (args == null)
			return format(pattern, new Object[0], 0, throwable);

		return format(pattern, args, args.length, throwable);
	}

	private static String format(String pattern, Object[] args, int argsLen, Throwable throwable) {
		if (pattern == null)
			return null;

		if (argsLen == 0 && throwable == null)
			return pattern;

		int i = 0;
		int j;
		StringBuilder b = new StringBuilder(pattern.length() + 50);

		int l;
		for (l = 0; l < argsLen; l++) {
			j = pattern.indexOf(DELIM_STR, i);

			if (j == -1) {
				// no more variables
				if (i == 0) { // this is a simple string
					return pattern;
				} else { // add the tail string which contains no variables and return
					// the result.
					b.append(pattern, i, pattern.length());
					return b.toString();
				}
			} else {
				if (isEscapedDelimeter(pattern, j)) {
					if (!isDoubleEscaped(pattern, j)) {
						l--; // DELIM_START was escaped, thus should not be incremented
						b.append(pattern, i, j - 1);
						b.append(DELIM_START);
						i = j + 1;
					} else {
						// The escape character preceding the delimiter start is
						// itself escaped: "abc x:\\{}"
						// we have to consume one backward slash
						b.append(pattern, i, j - 1);
						deeplyAppendParameter(b, args[l], new HashMap<>());
						i = j + 2;
					}
				} else {
					// normal case
					b.append(pattern, i, j);
					deeplyAppendParameter(b, args[l], new HashMap<>());
					i = j + 2;
				}
			}
		}
		// append the characters following the last {} pair.
		b.append(pattern, i, pattern.length());
		return b.toString();
	}

	private static boolean isEscapedDelimeter(String pattern, int delimeterStartIndex) {
		if (delimeterStartIndex == 0)
			return false;

		char potentialEscape = pattern.charAt(delimeterStartIndex - 1);
		return potentialEscape == ESCAPE_CHAR;
	}

	private static boolean isDoubleEscaped(String pattern, int delimeterStartIndex) {
		return delimeterStartIndex >= 2 && pattern.charAt(delimeterStartIndex - 2) == ESCAPE_CHAR;
	}

	private static void deeplyAppendParameter(StringBuilder b, Object o, Map<Object[], Object> seenMap) {
		if (o == null) {
			b.append("null");
			return;
		}

		if (!o.getClass().isArray()) {
			safeObjectAppend(b, o);
		} else {
			if (o instanceof boolean[])
				booleanArrayAppend(b, (boolean[]) o);
			else if (o instanceof byte[])
				byteArrayAppend(b, (byte[]) o);
			else if (o instanceof char[])
				charArrayAppend(b, (char[]) o);
			else if (o instanceof short[])
				shortArrayAppend(b, (short[]) o);
			else if (o instanceof int[])
				intArrayAppend(b, (int[]) o);
			else if (o instanceof long[])
				longArrayAppend(b, (long[]) o);
			else if (o instanceof float[])
				floatArrayAppend(b, (float[]) o);
			else if (o instanceof double[])
				doubleArrayAppend(b, (double[]) o);
			else
				objectArrayAppend(b, (Object[]) o, seenMap);
		}
	}

	private static void safeObjectAppend(StringBuilder b, Object obj) {
		try {
			b.append(obj.toString());
		} catch (Throwable t) {
			logger.warn("Failed toString() invocation on an object of type [" + obj.getClass().getName() + "]");
			b.append("[FAILED toString()]");
		}
	}

	private static void objectArrayAppend(StringBuilder b, Object[] a, Map<Object[], Object> seenMap) {
		b.append('[');
		if (!seenMap.containsKey(a)) {
			seenMap.put(a, null);
			final int len = a.length;
			for (int i = 0; i < len; i++) {
				deeplyAppendParameter(b, a[i], seenMap);
				if (i != len - 1)
					b.append(", ");
			}
			// allow repeats in siblings
			seenMap.remove(a);
		} else {
			b.append("...");
		}
		b.append(']');
	}

	private static void booleanArrayAppend(StringBuilder b, boolean[] a) {
		b.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			b.append(a[i]);
			if (i != len - 1)
				b.append(", ");
		}
		b.append(']');
	}

	private static void byteArrayAppend(StringBuilder b, byte[] a) {
		b.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			b.append(a[i]);
			if (i != len - 1)
				b.append(", ");
		}
		b.append(']');
	}

	private static void charArrayAppend(StringBuilder b, char[] a) {
		b.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			b.append(a[i]);
			if (i != len - 1)
				b.append(", ");
		}
		b.append(']');
	}

	private static void shortArrayAppend(StringBuilder b, short[] a) {
		b.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			b.append(a[i]);
			if (i != len - 1)
				b.append(", ");
		}
		b.append(']');
	}

	private static void intArrayAppend(StringBuilder b, int[] a) {
		b.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			b.append(a[i]);
			if (i != len - 1)
				b.append(", ");
		}
		b.append(']');
	}

	private static void longArrayAppend(StringBuilder b, long[] a) {
		b.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			b.append(a[i]);
			if (i != len - 1)
				b.append(", ");
		}
		b.append(']');
	}

	private static void floatArrayAppend(StringBuilder b, float[] a) {
		b.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			b.append(a[i]);
			if (i != len - 1)
				b.append(", ");
		}
		b.append(']');
	}

	private static void doubleArrayAppend(StringBuilder b, double[] a) {
		b.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			b.append(a[i]);
			if (i != len - 1)
				b.append(", ");
		}
		b.append(']');
	}
}
