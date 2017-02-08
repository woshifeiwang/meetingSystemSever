package com.hnair.iot.dataserver.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "time")
public class TimeUnit {

	private String timeSpan;
	private Long amountToAdd;
	private String zoneId;

	public String getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(String timeSpan) {
		this.timeSpan = timeSpan;
	}

	public Long getAmountToAdd() {
		return amountToAdd;
	}

	public void setAmountToAdd(Long amountToAdd) {
		this.amountToAdd = amountToAdd;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

}
