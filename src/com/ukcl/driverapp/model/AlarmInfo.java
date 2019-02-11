package com.ukcl.driverapp.model;

public class AlarmInfo {
	private String id;
	private String time;
	private boolean isAlarmSet = false;
	
	public AlarmInfo(String id, String time) {
		this.id = id;
		this.time = time;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public boolean isAlarmSet() {
		return isAlarmSet;
	}

	public void setAlarmSet(boolean isAlarmSet) {
		this.isAlarmSet = isAlarmSet;
	}
}
