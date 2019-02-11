/**
 * 
 */
package com.ukcl.driverapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeSet;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.ukcl.driverapp.adapter.ArrayListAdapter;
import com.ukcl.driverapp.base.ActionBarBaseActivitiy;
import com.ukcl.driverapp.helper.DialogFactory;
import com.ukcl.driverapp.interfaces.CommentCallback;
import com.ukcl.driverapp.model.AlarmInfo;
import com.ukcl.driverapp.model.BookingDetailsModel;
import com.ukcl.driverapp.model.Request;
import com.ukcl.driverapp.model.RequestDetail;
import com.ukcl.driverapp.model.User;
import com.ukcl.driverapp.parse.HttpRequester;
import com.ukcl.driverapp.parse.ParseContent;
import com.ukcl.driverapp.utills.AndyConstants;
import com.ukcl.driverapp.utills.AndyUtils;
import com.ukcl.driverapp.utills.PreferenceHelper;
import com.ukcl.driverapp.viewbinders.BookingsBinder;
import com.ukcl.driverapp.widget.MyFontButton;

/**
 * 
 * @author Faraz Ahmed
 *
 */
public class MyBookingsActivity extends ActionBarBaseActivitiy implements
		OnItemClickListener, OnClickListener, TimePickerDialog.OnTimeSetListener{
	private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();
	private ListView listViewBookings;
	private ArrayListAdapter<Request> bookingsAdapter;
	private PreferenceHelper preferenceHelper;
	private ParseContent parseContent;
	private ImageView tvNoHistory;
	private ArrayList<User> listBookings = new ArrayList<User>();
	private Request requestDetials;
	public static final String TIMEPICKER_TAG = "timepicker";
	TimePickerDialog timePickerDialog;
	private Calendar calendar;
	private Request request;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mybookings);
		setTitle("My Bookings");
		getActionBar().setIcon(R.drawable.ub__nav_history);
		
		listViewBookings = (ListView) findViewById(R.id.listBookings);
		listViewBookings.setOnItemClickListener(this);
		preferenceHelper = new PreferenceHelper(this);
		parseContent = new ParseContent(this);
		listBookings = new ArrayList<User>();
		bookingsAdapter = new ArrayListAdapter<Request>(this, new BookingsBinder(this, preferenceHelper));
		listViewBookings.setAdapter(bookingsAdapter);
		listViewBookings.setOnItemClickListener(this);
		calendar = Calendar.getInstance();
		timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
		TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
        if (tpd != null) {
            tpd.setOnTimeSetListener(this);
        }
        initImageLoader();
		
		getBookingDetails();
	}

	
	private void showTimePicker(Request request){
		this.request = request;
		timePickerDialog.setVibrate(true);
        timePickerDialog.setCloseOnSingleTapMinute(false);
        timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);

	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		
		//2015-09-02 19:21:00
		String[] splittedTime = request.getRequest_start_timeString().split(" ");
		String date = splittedTime[0]; // date
		String time = splittedTime[1]; // time;
		
		String[] splittedMinutes = time.split(":");
		
		int hours = Integer.parseInt(splittedMinutes[0]); // hours 
		int minutes = 	Integer.parseInt(splittedMinutes[1]); // minuts 
		
		
		String[] splittedDate = date.split("-");
		int year = Integer.parseInt(splittedDate[0]);
		int month = Integer.parseInt(splittedDate[1]);
		int day = Integer.parseInt(splittedDate[2]);
		
		if(hourOfDay < (hours - 3)) {
			showDateError(1);
		}else if(hourOfDay > hours){
			showDateError(2);
		}else if(hourOfDay > (hours - 3) && minute > minutes){
			showDateError(3);
		}else {
			//showDateError(4);
			preferenceHelper.putBooking(new AlarmInfo(request.getId(),year +"-" + month +"-" +day +" " + splittedMinutes[0] + " : " + splittedMinutes[1])); 
			Calendar cal = Calendar.getInstance();
		     cal.add(Calendar.YEAR, year);
		     cal.add(Calendar.DATE, day);
		     cal.add(Calendar.MONTH, month);
		     cal.add(Calendar.HOUR, hourOfDay);
		     cal.add(Calendar.MINUTE, minute);
		     
			 //cal.add(Calendar.SECOND, 15);
			 Intent intent = new Intent(this, SwapActivity.class);
			 intent.putExtra("id", request.getId());
		        PendingIntent pendingIntent = PendingIntent.getActivity(this,
		            12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		        AlarmManager am = 
		            (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
		        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
		                pendingIntent);
		        AndyUtils.showToast("Alarm added for this booking", this);
			}
		 
	/*	 Calendar cal = Calendar.getInstance();
	     cal.add(Calendar.YEAR, year);
	     cal.add(Calendar.DATE, day);
	     cal.add(Calendar.MONTH, month);
	     cal.add(Calendar.HOUR, hourOfDay);
	     cal.add(Calendar.MINUTE, minute);
	     
		 cal.add(Calendar.SECOND, 15);
		 Intent intent = new Intent(this, SwapActivity.class);
		 intent.putExtra("id", request.getId());
		    PendingIntent pendingIntent = PendingIntent.getActivity(this,
	            12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	        AlarmManager am = 
	            (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
	        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
	                pendingIntent);
	        AndyUtils.showToast("Alarm added for this booking", this);*/
		
		
		
}
	
private void initImageLoader() {
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).build();
		ImageLoader.getInstance().init(config);
		
		
	}
	
	private void showDateError(int errorType) {
		
		switch (errorType) {
		case 1:
			AndyUtils.showToast("You can only set alarm 3 hours prior the appointment. Please select time within specified range", this);
			break;
		case 2:
			AndyUtils.showToast("Please select time within specified range", this);
			break;
		case 3:
			AndyUtils.showToast("Please select time within specified range", this);
			break;
		case 4:
			AndyUtils.showToast("Success", this);
			break;
		default:
			break;
		}
		
		
	}
	
	
	
	/**
	 * 
	 */
/*	private void getBookings() {
		// TODO Auto-generated method stub
		if (!AndyUtils.isNetworkAvailable(this)) {
			AndyUtils.showToast(
					getResources().getString(R.string.dialog_no_inter_message),
					this);
			return;
		}
		AndyUtils.showCustomProgressDialog(this,
				getResources().getString(R.string.progress_getting_history),
				false, null);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Const.URL, Const.ServiceType.HISTORY + Const.Params.ID + "="
				+ preferenceHelper.getUserId() + "&" + Const.Params.TOKEN + "="
				+ preferenceHelper.getSessionToken());
		AppLog.Log("History", Const.ServiceType.HISTORY + Const.Params.ID + "="
				+ preferenceHelper.getUserId() + "&" + Const.Params.TOKEN + "="
				+ preferenceHelper.getSessionToken());
		new HttpRequester(this, map, Const.ServiceCode.HISTORY, true, this);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
			requestDetials = (Request) arg0.getItemAtPosition(position);
			showDetails(requestDetials);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uberdriverforx.parse.AsyncTaskCompleteListener#onTaskCompleted(java
	 * .lang.String, int)
	 */
	@Override
	public void onTaskCompleted(String response, int serviceCode) {
		AndyUtils.removeCustomProgressDialog();
		switch (serviceCode) {
		case AndyConstants.ServiceCode.BOOKING_DETAILS:
			handleData(response);
			break;
		case AndyConstants.ServiceCode.BOOKING_STARTED:
			//handleData(response);
			String responseA = response;
			handleAdvanceBooking();
			break;
		case AndyConstants.ServiceCode.CANCEL_BOOKING:
			AndyUtils.showToast("Booking Canceled", this);
			onBackPressed();
			break;
		default:
			break;
		}
	}

	private void handleAdvanceBooking() {
		
		RequestDetail requestDetails  = new RequestDetail();
		requestDetails.setRequestId(Integer.valueOf(this.requestDetials.getId()));
		requestDetails.setAdvance(true);
		requestDetails.setAdvanceBDate(this.requestDetials.getRequest_start_timeString());
		//requestDetails.setClient_d_latitude(advanceRequest.getAddress2());
		requestDetails.setClientName(this.requestDetials.getOwner().getFirst_name());
		requestDetails.setClientPhoneNumber("123456"); // dummy
		requestDetails.setClientLongitude(this.requestDetials.getOwner().getLongitude());
		requestDetails.setClientLatitude(this.requestDetials.getOwner().getLatitude());
		//requestDetails.setClientRating(Integer.valueOf(advanceRequest.getRequest_data().getOwner().getRating()));
		requestDetails.setClientRating(4);
		requestDetails.setClientProfile(this.requestDetials.getOwner().getPicture());
		requestDetails.setType(this.requestDetials.getAdvance_type());
		preferenceHelper.putRequstDetails(requestDetails);
		preferenceHelper.putRequestId(Integer.valueOf(this.requestDetials.getId()));
		finish();
	/*	JobFragment jobFragment = new JobFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(AndyConstants.JOB_STATUS,
				AndyConstants.IS_WALKER_STARTED);
		bundle.putSerializable(AndyConstants.REQUEST_DETAIL,
				requestDetails);
		jobFragment.setArguments(bundle);
		mapActivity.addFragment(jobFragment, false,
				AndyConstants.JOB_FRGAMENT_TAG, true);
	*/
		
	}

	private void handleData(String response) {
		Gson gson = new Gson();
		//Type listType = new TypeToken<ArrayList<BookingDetailsModel>>(){}.getType();
		//ArrayList<BookingDetailsModel> bookingDetails = (ArrayList<BookingDetailsModel>) gson.fromJson(response, listType);
		BookingDetailsModel data = gson.fromJson(response, BookingDetailsModel.class);
		loadData(data.getResponse());
	}

	private void loadData(ArrayList<Request> data) {
		if(data != null && data.size() > 0) {
			bookingsAdapter.clearList();
			for(Request request : data) {
				if(request.getOwner() !=null && request.getWalker() != null){
					bookingsAdapter.add(request);
				}
			}
			bookingsAdapter.notifyDataSetChanged();
		}
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnActionNotification:
			onBackPressed();
			break;
		case R.id.btnsetalarm:
//			AndyUtils.showToast("Set Bookings", this);
			showTimePicker((Request)v.getTag());
			break;
			
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uberorg.ActionBarBaseActivitiy#isValidate()
	 */
	

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	private void showDetails(final Request requestDetails) {
		
		Log.d("amal", "in show fare");
		final Dialog mDialog = new Dialog(this, R.style.MyFareestimateDialog);
		mDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		mDialog.setContentView(R.layout.bookingdetails);
		TextView fromaddress, toaddress, customerName, toaddresstwo, time;
		MyFontButton btnstart, btncancel, fareestimatetitle;
		fromaddress = (TextView) mDialog.findViewById(R.id.fromaddress);
		toaddress = (TextView) mDialog.findViewById(R.id.toaddress);
		//estimatedfare = (TextView) mDialog.findViewById(R.id.estimatedfare);
		customerName = (TextView) mDialog.findViewById(R.id.customerName);
		toaddresstwo = (TextView) mDialog.findViewById(R.id.toaddresstwo);
		time = (TextView) mDialog.findViewById(R.id.time);
		btnstart = (MyFontButton) mDialog.findViewById(R.id.btnstart);
		btncancel = (MyFontButton) mDialog.findViewById(R.id.btncancel);
		fareestimatetitle= (MyFontButton) mDialog.findViewById(R.id.fareestimatetitle);
		fareestimatetitle.setText("Booking Details");
		fromaddress.setText(requestDetails.getStarting_address());
		toaddress.setText(requestDetails.getD_address());
		customerName.setText(requestDetails.getOwner().getFirst_name());
		time .setText(requestDetails.getRequest_start_timeString());
		if(requestDetails.getAdvance_type().equals("double")){
			toaddresstwo.setVisibility(View.VISIBLE);
			toaddresstwo.setText(requestDetails.getE_address());
		}else {
			toaddresstwo.setVisibility(View.INVISIBLE);
		}
		// Double destination logic
		/*
		 * if(isDoubleDestination()) { toaddresstwo.setVisibility(View.VISIBLE);
		 * toaddresstwo.setText(edtdestination2.getText().toString().trim());
		 * 
		 * }else { toaddresstwo.setVisibility(View.INVISIBLE); }
		 */
		btnstart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mDialog.dismiss();
				startBooking(requestDetails);
			}
		});
		mDialog.show();
		btncancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				handleCancelModule(requestDetails);
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	protected void handleCancelModule(final Request requestDetails) {
		DialogFactory.createInputDialog(this, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}, "Cancel booking", "Reason for cancel", new CommentCallback() {
			
			@Override
			public void comment(String comment) {
				cancelBooking(requestDetails, comment);
			}
		}).show();
		
	}

	

	

	// if status = 1 then accept if 0 then reject
	private void getBookingDetails() {
		if (!AndyUtils.isNetworkAvailable(this)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet),
					this);
			return;
		}
		AndyUtils.showCustomProgressDialog(this, "", getResources()
				.getString(R.string.progress_loading), false);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL, AndyConstants.ServiceType.GET_DRIVER_BOOKINGS);
		map.put(AndyConstants.Params.DRIVER_ID, preferenceHelper.getUserId());
		map.put(AndyConstants.Params.ADVANCE_TYPE,"all");
		map.put(AndyConstants.Params.IS_ADVANCE,"1");
		new HttpRequester(this, map,
				AndyConstants.ServiceCode.BOOKING_DETAILS, this);
	}
	
	
	private void startBooking(Request requestDetails) {
		if (!AndyUtils.isNetworkAvailable(this)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet),
					this);
			return;
		}
		AndyUtils.showCustomProgressDialog(this, "", getResources()
				.getString(R.string.progress_loading), false);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL, AndyConstants.ServiceType.BOOKING_STARTED);
		map.put(AndyConstants.Params.DRIVER_ID, preferenceHelper.getUserId());
		map.put(AndyConstants.Params.OWNER_ID,requestDetails.getOwner().getId());
		map.put(AndyConstants.Params.REQUEST_ID,requestDetails.getId());
		new HttpRequester(this, map,
				AndyConstants.ServiceCode.BOOKING_STARTED, this);
	}
	private void cancelBooking(Request requestDetails, String comment) {
		if (!AndyUtils.isNetworkAvailable(this)) {
			AndyUtils.showToast(
					getResources().getString(R.string.dialog_no_inter_message),
					this);
			return;
		}
		AndyUtils.showToast(
				getResources().getString(R.string.progress_loading),
				this);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL, AndyConstants.ServiceType.CANCEL_BOOKING);
		map.put(AndyConstants.Params.OWNER_ID, requestDetails.getOwner().getId());
		map.put(AndyConstants.Params.DRIVER_ID, requestDetails.getWalker().getId());
		map.put(AndyConstants.Params.COMMENT,comment);
		map.put(AndyConstants.Params.REQUEST_ID,requestDetails.getId());
		new HttpRequester(this, map,
				AndyConstants.ServiceCode.CANCEL_BOOKING, this);
	}

	
	
	
}
