package com.ukcl.driverapp.model;

public class RequestInfoModel {
	private Response response;
	private boolean success;
	
	
	public class Response {
		private Request request;
		private UserM owner;
		private UserM walker;
		
		
		public Request getRequest() {
			return request;
		}

		public void setRequest(Request request) {
			this.request = request;
		}

		public UserM getOwner() {
			return owner;
		}

		public void setOwner(UserM owner) {
			this.owner = owner;
		}

		public UserM getWalker() {
			return walker;
		}

		public void setWalker(UserM walker) {
			this.walker = walker;
		}
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
