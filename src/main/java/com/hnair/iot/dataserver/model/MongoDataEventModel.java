package com.hnair.iot.dataserver.model;

import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Document
public final class MongoDataEventModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private DBObject header;
	private DBObject event;

	public MongoDataEventModel() {

	}

	public MongoDataEventModel(DataRecordEvent dto) {
		this.event = (DBObject) JSON.parse(dto.getMeta());
		this.header = (DBObject) JSON.parse(dto.getHeader());
	}

	public DBObject getEvent() {
		return event;
	}

	public void setEvent(DBObject event) {
		this.event = event;
	}

	public DBObject getHeader() {
		return header;
	}

	public void setHeader(DBObject header) {
		this.header = header;
	}

}
