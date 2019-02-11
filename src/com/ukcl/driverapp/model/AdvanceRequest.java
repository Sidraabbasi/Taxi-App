package com.ukcl.driverapp.model;

public class AdvanceRequest {
	
	private String request_id;
	private String time_left_to_respond;
	private String payment_mode;
	private String type;
	private String request_start_time;
	private String first_name;
	private  String last_name;
	private  String is_advance;
	private  String address1;
	private  String address2;
	private  String address3;
	private RequestData request_data;
	
	public class RequestData {
		private Owner owner;

		public Owner getOwner() {
			return owner;
		}

		public void setOwner(Owner owner) {
			this.owner = owner;
		}
	}
	public class Owner {
		private String name;
		private String picture;
		private String address;
		private String latitude;
		private String longitude;
		private String rating;
		private String num_rating;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPicture() {
			return picture;
		}
		public void setPicture(String picture) {
			this.picture = picture;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public String getRating() {
			return rating;
		}
		public void setRating(String rating) {
			this.rating = rating;
		}
		public String getNum_rating() {
			return num_rating;
		}
		public void setNum_rating(String num_rating) {
			this.num_rating = num_rating;
		}
		
	}
	
	
	
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIs_advance() {
		return is_advance;
	}
	public void setIs_advance(String is_advance) {
		this.is_advance = is_advance;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getRequest_start_time() {
		return request_start_time;
	}
	public void setRequest_start_time(String request_start_time) {
		this.request_start_time = request_start_time;
	}
	public String getPayment_mode() {
		return payment_mode;
	}
	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}
	public String getTime_left_to_respond() {
		return time_left_to_respond;
	}
	public void setTime_left_to_respond(String time_left_to_respond) {
		this.time_left_to_respond = time_left_to_respond;
	}
	public RequestData getRequest_data() {
		return request_data;
	}
	public void setRequest_data(RequestData request_data) {
		this.request_data = request_data;
	}
	
	
	
	
	
}
