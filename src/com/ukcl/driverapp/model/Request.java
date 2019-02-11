package com.ukcl.driverapp.model;

@SuppressWarnings("unused")
public class Request {
	
	private String id;
	private String owner_id;
	private String confirmed_walker;
	private String current_walker;
	private String request_start_time;
	private String is_walker_started;
	private String is_walker_arrived;
	private String is_started;
	private String is_completed;
	private String is_dog_rated;
	private String is_walker_rated;
	private String distance;
	private String time;
	private String total;
	private String is_paid;
	private String card_payment;
	private String ledger_payment;
	private String is_cancelled;
	private String refund;
	private String transfer_amount;
	private String later;
	private String latitude;
	private String longitude;
	private String starting_name;
	
	private String starting_address;
	private String D_latitude;
	private String D_longitude;
	private String D_name;
	private String D_address;
	private String E_latitude;
	private String E_longitude;
	private String E_address;
	private String E_name;
	private String advance_type;
	
	
	private UserM walker;
	private UserM owner;
	
	
	public String getStarting_address() {
		return starting_address;
	}
	public void setStarting_address(String starting_address) {
		this.starting_address = starting_address;
	}
	public String getD_latitude() {
		return D_latitude;
	}
	public void setD_latitude(String d_latitude) {
		D_latitude = d_latitude;
	}
	public String getD_longitude() {
		return D_longitude;
	}
	public void setD_longitude(String d_longitude) {
		D_longitude = d_longitude;
	}
	public String getD_name() {
		return D_name;
	}
	public void setD_name(String d_name) {
		D_name = d_name;
	}
	public String getD_address() {
		return D_address;
	}
	public void setD_address(String d_address) {
		D_address = d_address;
	}
	public String getE_latitude() {
		return E_latitude;
	}
	public void setE_latitude(String e_latitude) {
		E_latitude = e_latitude;
	}
	public String getE_longitude() {
		return E_longitude;
	}
	public void setE_longitude(String e_longitude) {
		E_longitude = e_longitude;
	}
	public String getE_address() {
		return E_address;
	}
	public void setE_address(String e_address) {
		E_address = e_address;
	}
	public String getE_name() {
		return E_name;
	}
	public void setE_name(String e_name) {
		E_name = e_name;
	}
	public String getAdvance_type() {
		return advance_type;
	}
	public void setAdvance_type(String advance_type) {
		this.advance_type = advance_type;
	}
	public String getPayment_mode() {
		return payment_mode;
	}
	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public String getPromo_code() {
		return promo_code;
	}
	public void setPromo_code(String promo_code) {
		this.promo_code = promo_code;
	}
	private String payment_mode;
	private String payment_id;
	private String promo_code;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getConfirmed_walker() {
		return confirmed_walker;
	}
	public void setConfirmed_walker(String confirmed_walker) {
		this.confirmed_walker = confirmed_walker;
	}
	public String getCurrent_walker() {
		return current_walker;
	}
	public void setCurrent_walker(String current_walker) {
		this.current_walker = current_walker;
	}
	public String getRequest_start_timeString() {
		return request_start_time;
	}
	public void setRequest_start_timeString(String request_start_timeString) {
		this.request_start_time = request_start_timeString;
	}
	public String getIs_walker_started() {
		return is_walker_started;
	}
	public void setIs_walker_started(String is_walker_started) {
		this.is_walker_started = is_walker_started;
	}
	public String getIs_walker_arrived() {
		return is_walker_arrived;
	}
	public void setIs_walker_arrived(String is_walker_arrived) {
		this.is_walker_arrived = is_walker_arrived;
	}
	public String getIs_started() {
		return is_started;
	}
	public void setIs_started(String is_started) {
		this.is_started = is_started;
	}
	public String getIs_completed() {
		return is_completed;
	}
	public void setIs_completed(String is_completed) {
		this.is_completed = is_completed;
	}
	public String getIs_dog_rated() {
		return is_dog_rated;
	}
	public void setIs_dog_rated(String is_dog_rated) {
		this.is_dog_rated = is_dog_rated;
	}
	public String getIs_walker_rated() {
		return is_walker_rated;
	}
	public void setIs_walker_rated(String is_walker_rated) {
		this.is_walker_rated = is_walker_rated;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getIs_paid() {
		return is_paid;
	}
	public void setIs_paid(String is_paid) {
		this.is_paid = is_paid;
	}
	public String getCard_payment() {
		return card_payment;
	}
	public void setCard_payment(String card_payment) {
		this.card_payment = card_payment;
	}
	public String getLedger_payment() {
		return ledger_payment;
	}
	public void setLedger_payment(String ledger_payment) {
		this.ledger_payment = ledger_payment;
	}
	public String getIs_cancelled() {
		return is_cancelled;
	}
	public void setIs_cancelled(String is_cancelled) {
		this.is_cancelled = is_cancelled;
	}
	public String getRefund() {
		return refund;
	}
	public void setRefund(String refund) {
		this.refund = refund;
	}
	public String getTransfer_amount() {
		return transfer_amount;
	}
	public void setTransfer_amount(String transfer_amount) {
		this.transfer_amount = transfer_amount;
	}
	public String getLater() {
		return later;
	}
	public void setLater(String later) {
		this.later = later;
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
	public String getStarting_name() {
		return starting_name;
	}
	public void setStarting_name(String starting_name) {
		this.starting_name = starting_name;
	}
	public UserM getWalker() {
		return walker;
	}
	public void setWalker(UserM walker) {
		this.walker = walker;
	}
	public UserM getOwner() {
		return owner;
	}
	public void setOwner(UserM owner) {
		this.owner = owner;
	}	
	
	
	
	
}
