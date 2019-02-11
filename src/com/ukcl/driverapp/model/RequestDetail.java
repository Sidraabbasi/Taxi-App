/**
 * 
 */
package com.ukcl.driverapp.model;

import java.io.Serializable;

/**
 * @author Kishan H Dhamat
 * 
 */
public class RequestDetail implements Serializable {
	
	
	private int requestId;
	private int timeLeft;
	private int jobStatus;
	private long startTime;
	private String time, 
	distance, 
	unit;
	
	private String type; // single / double
	private String advanceBDate; // 14-jan-2015
	private String advanceBTime; // 12:00
	private boolean isAdvance; // 0 or 1 = 0 false, 1 for true; 
	
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the jobStatus
	 */
	public int getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus
	 *            the jobStatus to set
	 */
	
	public void setJobStatus(int jobStatus) {
		this.jobStatus = jobStatus;
	}

	private String clientName, clientProfile, clientLatitude, clientLongitude,
			client_d_latitude, client_d_longitude, clientPhoneNumber, e_latitude, e_longitude;
	private float clientRating;

	/**
	 * @return the requestId
	 */
	public int getRequestId() {
		return requestId;
	}

	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clientName
	 *            the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return the clientProfile
	 */
	public String getClientProfile() {
		return clientProfile;
	}

	/**
	 * @param clientProfile
	 *            the clientProfile to set
	 */
	public void setClientProfile(String clientProfile) {
		this.clientProfile = clientProfile;
	}

	/**
	 * @return the clientRating
	 */
	public float getClientRating() {
		return clientRating;
	}

	/**
	 * @param clientRating
	 *            the clientRating to set
	 */
	public void setClientRating(float clientRating) {
		this.clientRating = clientRating;
	}

	/**
	 * @return the clientLatitude
	 */
	public String getClientLatitude() {
		return clientLatitude;
	}

	/**
	 * @param clientLatitude
	 *            the clientLatitude to set
	 */
	public void setClientLatitude(String clientLatitude) {
		this.clientLatitude = clientLatitude;
	}

	/**
	 * @return the clientLongitude
	 */
	public String getClientLongitude() {
		return clientLongitude;
	}

	/**
	 * @param clientLongitude
	 *            the clientLongitude to set
	 */
	public void setClientLongitude(String clientLongitude) {
		this.clientLongitude = clientLongitude;
	}

	/**
	 * @return the clientPhoneNumber
	 */
	public String getClientPhoneNumber() {
		return clientPhoneNumber;
	}

	/**
	 * @param clientPhoneNumber
	 *            the clientPhoneNumber to set
	 */
	public void setClientPhoneNumber(String clientPhoneNumber) {
		this.clientPhoneNumber = clientPhoneNumber;
	}

	/**
	 * @param requestId
	 *            the requestId to set
	 */
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the timeLeft
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * @param timeLeft
	 *            the timeLeft to set
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public String getClient_d_latitude() {
		return client_d_latitude;
	}

	public void setClient_d_latitude(String client_d_latitude) {
		this.client_d_latitude = client_d_latitude;
	}

	public String getClient_d_longitude() {
		return client_d_longitude;
	}

	public void setClient_d_longitude(String client_d_longitude) {
		this.client_d_longitude = client_d_longitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAdvanceBDate() {
		return advanceBDate;
	}

	public void setAdvanceBDate(String advanceBDate) {
		this.advanceBDate = advanceBDate;
	}

	public String getAdvanceBTime() {
		return advanceBTime;
	}

	public void setAdvanceBTime(String advanceBTime) {
		this.advanceBTime = advanceBTime;
	}

	public boolean isAdvance() {
		return isAdvance;
	}

	public void setAdvance(boolean isAdvance) {
		this.isAdvance = isAdvance;
	}

	public String getE_longitude() {
		return e_longitude;
	}

	public void setE_longitude(String e_longitude) {
		this.e_longitude = e_longitude;
	}

	public String getE_latitude() {
		return e_latitude;
	}

	public void setE_latitude(String e_latitude) {
		this.e_latitude = e_latitude;
	}
}
