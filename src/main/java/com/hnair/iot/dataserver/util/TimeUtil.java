package com.hnair.iot.dataserver.util;

import java.time.temporal.ChronoUnit;

/*
 * create  chronoUnit
 */
public class TimeUtil {
	
	public static int findIndexOfNonDigit(CharSequence text) {
		for (int i = 0; i < text.length(); i++) {
			if (Character.isDigit(text.charAt(i))) {
				continue;
			}
			return i;
		}
		return -1;
	}

	public static ChronoUnit parseDuration(String datePattern) {
		final int index = findIndexOfNonDigit(datePattern);
		if (index == -1) {
			throw new IllegalStateException("Incorrect time format given: " + datePattern);
		}
		try {
			final ChronoUnit unit;
			switch (datePattern) {
			case "sec":
			case "secs": {
				unit = ChronoUnit.SECONDS;
				return unit;
			}
			case "min":
			case "mins": {
				unit = ChronoUnit.MINUTES;
				return unit;
			}
			case "hour":
			case "hours": {
				unit = ChronoUnit.HOURS;
				return unit;
			}
			case "day":
			case "days": {
				unit = ChronoUnit.DAYS;
				return unit;
			}
			case "week":
			case "weeks": {
				unit = ChronoUnit.WEEKS;
				return unit;
			}
			case "month":
			case "months": {
				unit = ChronoUnit.MONTHS;
				return unit;
			}
			case "year":
			case "years": {
				unit = ChronoUnit.YEARS;
				return unit;
			}

			}
			return ChronoUnit.WEEKS;

		}
		catch (Exception e) {
			throw new IllegalStateException(
					"Incorrect time format given: " + datePattern + " val: " + datePattern.substring(0, index));
		}

	}
}