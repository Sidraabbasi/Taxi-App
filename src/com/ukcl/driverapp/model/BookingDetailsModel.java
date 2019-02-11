package com.ukcl.driverapp.model;

import java.util.ArrayList;

public class BookingDetailsModel {
	private ArrayList<Request> response;
	private String success;
	public ArrayList<Request> getResponse() {
		return response;
	}

	public void setResponse(ArrayList<Request> response) {
		this.response = response;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
}
