package com.hnair.iot.dataserver.model;

public class DataRecordModel {

	private String userSchema;
	private String systemSchema;
	private byte[] userData;
	private byte[] systemData;

	public DataRecordModel() {

	}

	public DataRecordModel(String userSchema, String systemSchema, byte[] userData, byte[] systemData) {
		super();
		this.userSchema = userSchema;
		this.systemSchema = systemSchema;
		this.userData = userData;
		this.systemData = systemData;
	}

	public String getUserSchema() {
		return userSchema;
	}

	public void setUserSchema(String userSchema) {
		this.userSchema = userSchema;
	}

	public String getSystemSchema() {
		return systemSchema;
	}

	public void setSystemSchema(String systemSchema) {
		this.systemSchema = systemSchema;
	}

	public byte[] getUserData() {
		return userData;
	}

	public void setUserData(byte[] userData) {
		this.userData = userData;
	}

	public byte[] getSystemData() {
		return systemData;
	}

	public void setSystemData(byte[] systemData) {
		this.systemData = systemData;
	}
}
