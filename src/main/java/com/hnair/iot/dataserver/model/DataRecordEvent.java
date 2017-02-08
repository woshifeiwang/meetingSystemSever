package com.hnair.iot.dataserver.model;

import java.io.Serializable;

public class DataRecordEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String header;
	private String meta;
	
	public DataRecordEvent(){
		
	}
	public DataRecordEvent(String header, String meta) {
		this.header = header;
		this.meta = meta;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}

	

}
