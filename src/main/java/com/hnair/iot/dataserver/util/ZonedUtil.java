package com.hnair.iot.dataserver.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class ZonedUtil {
	
	public static LocalDateTime zonedTime(ZonedDateTime zonedDateTime,String zoneID){
		ZoneId zoneId = ZoneId.of(zoneID);
		ZonedDateTime withZoneSameInstant = zonedDateTime.withZoneSameInstant(zoneId);
		LocalDateTime localDateTime = withZoneSameInstant.toLocalDateTime();
		return localDateTime;
	}
}
