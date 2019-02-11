package com.ukcl.driverapp.model;

import java.util.ArrayList;

public class SurgeAreaModel {
	private boolean success;
	private ArrayList<Surge> response;
		
		public class Surge{
			
			private String id;
			private String latitude;
			private String longitude;
			private String fare;
			private String radius;
			private String start_time;
			private String end_time;
			
			
			public String getLongitude() {
				return longitude;
			}
			public void setLongitude(String longitude) {
				this.longitude = longitude;
			}
			public String getLatitude() {
				return latitude;
			}
			public void setLatitude(String latitude) {
				this.latitude = latitude;
			}
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getFare() {
				return fare;
			}
			public void setFare(String fare) {
				this.fare = fare;
			}
			public String getRadius() {
				return radius;
			}
			public void setRadius(String radius) {
				this.radius = radius;
			}
			public String getStart_time() {
				return start_time;
			}
			public void setStart_time(String start_time) {
				this.start_time = start_time;
			}
			public String getEnd_time() {
				return end_time;
			}
			public void setEnd_time(String end_time) {
				this.end_time = end_time;
			}
			
		}
		public ArrayList<Surge> getResponse() {
			return response;
		}
		public void setResponse(ArrayList<Surge> response) {
			this.response = response;
		}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
