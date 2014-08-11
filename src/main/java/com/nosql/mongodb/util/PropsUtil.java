package com.nosql.mongodb.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置参数工具
 */
public class PropsUtil {

	static String DELIM_START = "${";
	static char DELIM_STOP = '}';
	static int DELIM_START_LEN = 2;
	static int DELIM_STOP_LEN = 1;

	static {
		try {
			System.getProperties().load(ClassLoader.getSystemResourceAsStream("mongodb.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param val The string on which variable substitution is performed.
	 * @throws IllegalArgumentException if <code>val</code> is malformed.
	 */
	public static String substVars(String val, Properties props) throws IllegalArgumentException {
		StringBuilder sbuf = new StringBuilder();
		int i = 0;
		int j, k;

		while (true) {
			j = val.indexOf(DELIM_START, i);
			if (j == -1) {
				if (i == 0) {
					return val;
				} else {
					sbuf.append(val.substring(i, val.length()));
					return sbuf.toString();
				}
			} else {
				sbuf.append(val.substring(i, j));
				k = val.indexOf(DELIM_STOP, j);
				if (k == -1) {
					throw new IllegalArgumentException("[" + val + "] has no closing brace. Opening brace at position " + j);
				} else {
					j += DELIM_START_LEN;
					String key = val.substring(j, k);
					String replacement = System.getProperty(key, null);
					if (replacement == null && props != null)
						replacement = props.getProperty(key);
					if (replacement != null) {
						String recursiveReplacement = substVars(replacement, props);
						sbuf.append(recursiveReplacement);
					}
					i = k + DELIM_STOP_LEN;
				}
			}
		}
	}

	public static String getProperty(String key, String def) {
		String value = System.getProperty(key, def);
		if (value == null) {
			return null;
		}
		return substVars(value, System.getProperties());
	}

	public static String getProperty(String key) {
		return getProperty(key, null);
	}

}