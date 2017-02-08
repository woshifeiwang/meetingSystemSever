package com.hnair.iot.dataserver.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String utilities.
 * 
 * @author Bin.Zhang
 */
public final class Strings {
	private static final String NULL = "null";

	// Java RegEx pattern to find Unix env alike vars formatted as either $VAR or ${VAR}
	private static final Pattern VAR_PATTERN_UNIX = Pattern.compile("\\$(?:(\\w+)|\\{(\\w+)\\})");

	// Java RegEx pattern to find vars formatted as ${VAR}
	private static final Pattern VAR_PATTERN = Pattern.compile("\\$\\{(.+?)\\}");

	public static final Pattern VAR_PATTERN_NAME = Pattern.compile("^.*[(/)|(\\\\)|(\\*)|(\\?)|(\")].*$");

	/**
	 * New line.
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * Used to substitute Unix env alike vars formatted as as either $VAR or ${VAR}
	 * 
	 * @param template the template to be substituted
	 * @param variables the variables used in the template
	 * @return the substitute result
	 */
	public static String substituteUnix(String template, Map<String, String> variables) {
		// Naturally, this RegEx pattern means that either matcher.group(1) or matcher.group(2) will be the
		// variable-name the other will be null - so you'll have to check both.
		Matcher matcher = VAR_PATTERN_UNIX.matcher(template);
		// StringBuilder cannot be used here because Matcher expects StringBuffer
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			String varName = matcher.group(1);
			if (varName != null && variables.containsKey(varName)) {
				String replacement = variables.get(varName);
				// quote to work properly with $ and {,} signs
				matcher.appendReplacement(buffer, replacement != null ? Matcher.quoteReplacement(replacement) : NULL);
			}

			varName = matcher.group(2);
			if (varName != null && variables.containsKey(varName)) {
				String replacement = variables.get(varName);
				// quote to work properly with $ and {,} signs
				matcher.appendReplacement(buffer, replacement != null ? Matcher.quoteReplacement(replacement) : NULL);
			}
		}

		return matcher.appendTail(buffer).toString();
	}

	/**
	 * Used to substitute vars formatted as ${VAR}
	 * 
	 * @param template the template to be substituted
	 * @param variables the variables used in the template
	 * @return the substitute result
	 */
	public static String substitute(String template, Map<String, String> variables) {
		Matcher matcher = VAR_PATTERN.matcher(template);
		// StringBuilder cannot be used here because Matcher expects StringBuffer
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			String varName = matcher.group(1);
			if (varName != null && variables.containsKey(varName)) {
				String replacement = variables.get(matcher.group(1));
				// quote to work properly with $ and {,} signs
				matcher.appendReplacement(buffer, replacement != null ? Matcher.quoteReplacement(replacement) : NULL);
			}
		}
		return matcher.appendTail(buffer).toString();
	}

	/**
	 * Test if a string is null or empty.
	 * 
	 * @param s the string to be tested
	 * @return true if null or empty
	 */
	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().equals("");
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal ignoring the case.
	 * </p>
	 *
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code> references are considered equal.
	 * Comparison is case insensitive.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.equalsIgnoreCase(null, null)   = true
	 * StringUtils.equalsIgnoreCase(null, "abc")  = false
	 * StringUtils.equalsIgnoreCase("abc", null)  = false
	 * StringUtils.equalsIgnoreCase("abc", "abc") = true
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 *
	 * @see java.lang.String#equalsIgnoreCase(String)
	 * @param str1 the first String, may be null
	 * @param str2 the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case insensitive, or both <code>null</code>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
	}

	/**
	 * org.apache.commons.lang.StringUtils
	 * <p>
	 * Checks if a String is not empty (""), not null and not whitespace only.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null and not whitespace
	 * @since 2.0
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * org.apache.commons.lang.StringUtils
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks string is empty or not
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		}
		else {
			for (String value : values) {
				result &= !isNullOrEmpty(value);
			}
		}
		return result;
	}

	private Strings() {
	}
}
