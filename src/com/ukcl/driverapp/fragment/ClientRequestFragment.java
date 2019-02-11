package com.ukcl.driverapp.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.jraf.android.backport.switchwidget.Switch;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.ukcl.driverapp.GCMIntentService;
import com.ukcl.driverapp.R;
import com.ukcl.driverapp.base.BaseMapFragment;
import com.ukcl.driverapp.locationupdate.LocationHelper;
import com.ukcl.driverapp.locationupdate.LocationHelper.OnLocationReceived;
import com.ukcl.driverapp.model.AdvanceRequest;
import com.ukcl.driverapp.model.RequestDetail;
import com.ukcl.driverapp.parse.AsyncTaskCompleteListener;
import com.ukcl.driverapp.parse.HttpRequester;
import com.ukcl.driverapp.utills.AndyConstants;
import com.ukcl.driverapp.utills.AndyUtils;
import com.ukcl.driverapp.utills.AppLog;
import com.ukcl.driverapp.widget.MyFontButton;
import com.ukcl.driverapp.widget.MyFontTextView;

/**
 * @author Kishan H Dhamat
 * 
 */

public class ClientRequestFragment extends BaseMapFragment implements
		AsyncTaskCompleteListener, OnLocationReceived, OnCheckedChangeListener {
	private GoogleMap mMap;
	private final String TAG = "ClientRequestFragment";
	private LinearLayout llAcceptReject;
	private View llUserDetailView;
	// private ProgressBar pbTimeLeft;
	private MyFontButton btnClientAccept, btnClientReject,
			btnClientReqRemainTime;
	// private RelativeLayout rlTimeLeft;
	private boolean isContinueRequest, isAccepted = false;
	private Timer timer;
	private SeekbarTimer seekbarTimer;
	private RequestDetail requestDetail;
	private Marker markerDriverLocation, markerClientLocation,
			markerClient_d_location, markerClient_e_location;
	private LocationClient locationClient;
	private Location location;
	private LocationHelper locationHelper;
	private MyFontTextView tvClientName;// , tvClientPhoneNumber;
	private RatingBar tvClientRating;
	private ImageView ivClientProfilePicture;
	private AQuery aQuery;
	private newRequestReciever requestReciever;
	private boolean selector = false;
	private Switch switchSetting;
	private static View clientRequestView;
	private ArrayList<SeekbarTimer> seekBar = new ArrayList<SeekbarTimer>();
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*clientRequestView = inflater.inflate(
				R.layout.fragment_client_requests, container, false);
		*/
		
		if (clientRequestView  != null) {
	        ViewGroup parent = (ViewGroup) clientRequestView .getParent();
	        if (parent != null)
	            parent.removeView(clientRequestView );
	    }
	    try {
	    	clientRequestView  = inflater.inflate(R.layout.fragment_client_requests, container, false);
	    } catch (InflateException e) {
	        /* map is already there, just return view as it is */
	    }
		
	    switchSetting = (Switch)clientRequestView.findViewById(R.id.switchAvaibility);
	    
		llAcceptReject = (LinearLayout) clientRequestView.findViewById(R.id.llAcceptReject);
		llUserDetailView = (View) clientRequestView.findViewById(R.id.clientDetailView);
		btnClientAccept = (MyFontButton) clientRequestView
				.findViewById(R.id.btnClientAccept);
		btnClientReject = (MyFontButton) clientRequestView
				.findViewById(R.id.btnClientReject);
		// pbTimeLeft = (ProgressBar) clientRequestView
		// .findViewById(R.id.pbClientReqTime);
		// rlTimeLeft = (RelativeLayout) clientRequestView
		// .findViewById(R.id.rlClientReqTimeLeft);
		btnClientReqRemainTime = (MyFontButton) clientRequestView
				.findViewById(R.id.btnClientReqRemainTime);
		tvClientName = (MyFontTextView) clientRequestView
				.findViewById(R.id.tvClientName);
		// tvClientPhoneNumber = (MyFontTextView) clientRequestView
		// .findViewById(R.id.tvClientNumber);

		tvClientRating = (RatingBar) clientRequestView
				.findViewById(R.id.tvClientRating);

		ivClientProfilePicture = (ImageView) clientRequestView
				.findViewById(R.id.ivClientImage);

		btnClientAccept.setOnClickListener(this);
		btnClientReject.setOnClickListener(this);

		clientRequestView.findViewById(R.id.btnMyLocation).setOnClickListener(
				this);

		return clientRequestView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		aQuery = new AQuery(mapActivity);
		
		setUpMap();
		locationHelper = new LocationHelper(getActivity());
		locationHelper.setLocationReceivedLister(this);
		locationHelper.onStart();
		checkState();
		

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter(AndyConstants.NEW_REQUEST);
		
		requestReciever = new newRequestReciever();
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
				requestReciever, filter);
		
	}

	private void addMarker() {
		if (mMap == null) {
			setUpMap();
			return;
		}
		locationClient = new LocationClient(mapActivity,
				new ConnectionCallbacks() {

					@Override
					public void onDisconnected() {

					}

					@Override
					public void onConnected(Bundle arg0) {
						location = locationClient.getLastLocation();
						if (location != null) {
							if (mMap != null) {
								if (markerDriverLocation == null) {
									markerDriverLocation = mMap
											.addMarker(new MarkerOptions()
													.position(
															new LatLng(
																	location.getLatitude(),
																	location.getLongitude()))
													.icon(BitmapDescriptorFactory
															.fromResource(R.drawable.pin_driver))
													.title(getResources()
															.getString(
																	R.string.my_location)));
									mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
											new LatLng(location.getLatitude(),
													location.getLongitude()),
											12));
								} else {
									markerDriverLocation
											.setPosition(new LatLng(location
													.getLatitude(), location
													.getLongitude()));
								}
							}
						} else {
							showLocationOffDialog();
						}
					}
				}, new OnConnectionFailedListener() {

					@Override
					public void onConnectionFailed(ConnectionResult arg0) {

					}
				});
		locationClient.connect();
	}

	public void showLocationOffDialog() {

		AlertDialog.Builder gpsBuilder = new AlertDialog.Builder(mapActivity);
		gpsBuilder.setCancelable(false);
		gpsBuilder
				.setTitle(getString(R.string.dialog_no_location_service_title))
				.setMessage(getString(R.string.dialog_no_location_service))
				.setPositiveButton(
						getString(R.string.dialog_enable_location_service),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// continue with delete
								dialog.dismiss();
								Intent viewIntent = new Intent(
										Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(viewIntent);

							}
						})

				.setNegativeButton(getString(R.string.dialog_exit),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// do nothing
								dialog.dismiss();
								mapActivity.finish();
							}
						});
		gpsBuilder.create();
		gpsBuilder.show();
	}

	private void setUpMap() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			mMap = ((SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(
							R.id.clientReqMap)).getMap();
			mMap.getUiSettings().setZoomControlsEnabled(false);
			mMap.setMyLocationEnabled(false);
			mMap.getUiSettings().setMyLocationButtonEnabled(false);

			mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

				// Use default InfoWindow frame

				@Override
				public View getInfoWindow(Marker marker) {
					View v = mapActivity.getLayoutInflater().inflate(
							R.layout.info_window_layout, null);
					MyFontTextView title = (MyFontTextView) v
							.findViewById(R.id.markerBubblePickMeUp);
					MyFontTextView content = (MyFontTextView) v
							.findViewById(R.id.infoaddress);
					title.setText(marker.getTitle());

					getAddressFromLocation(marker.getPosition(), content);

					// ((TextView) v).setText(marker.getTitle());
					return v;
				}

				// Defines the contents of the InfoWindow

				@Override
				public View getInfoContents(Marker marker) {

					// Getting view from the layout file info_window_layout View

					// Getting reference to the TextView to set title TextView

					// Returning the view containing InfoWindow contents return
					return null;

				}

			});

			mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(Marker marker) {
					marker.showInfoWindow();
					return true;
				}
			});
			addMarker();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnClientAccept:
			isAccepted = true;
			cancelSeekbarTimer();
			respondRequest(1, String.valueOf(AndyUtils.requestDetails.getRequestId()));
			break;
		case R.id.btnClientReject:
			isAccepted = false;
			cancelSeekbarTimer();
			respondRequest(0, String.valueOf(AndyUtils.requestDetails.getRequestId()));
			selector = false;
			break;
		case R.id.btnMyLocation:

			/*
			 * Location loc = mMap.getMyLocation(); if (loc != null) { LatLng
			 * latLang = new LatLng(loc.getLatitude(), loc.getLongitude());
			 * mMap.animateCamera(CameraUpdateFactory.newLatLng(latLang)); }
			 */
			
			///
			if (markerDriverLocation.getPosition() != null)
				mMap.animateCamera(CameraUpdateFactory
						.newLatLng(markerDriverLocation.getPosition()));
			
			
			/*RequestDetail requestDetails  = new RequestDetail();
			requestDetails.setAdvance(true);
			requestDetails.setAdvanceBDate(advanceRequest.getRequest_start_time());
			//requestDetails.setClient_d_latitude(advanceRequest.getAddress2());
			requestDetails.setClientName(advanceRequest.getRequest_data().getOwner().getName());
			requestDetails.setClientPhoneNumber("123456"); // dummy
			requestDetails.setClientLongitude(advanceRequest.getRequest_data().getOwner().getLongitude());
			requestDetails.setClientLatitude(advanceRequest.getRequest_data().getOwner().getLatitude());
			//requestDetails.setClientRating(Integer.valueOf(advanceRequest.getRequest_data().getOwner().getRating()));
			requestDetails.setClientRating(4);
			requestDetails.setClientProfile(advanceRequest.getRequest_data().getOwner().getPicture());
			requestDetails.setType(advanceRequest.getType());
			
			loadRequest(requestDetails);
			
			*/
			break;
		default:
			break;
		}
	}

	private void loadRequest(RequestDetail requestDetails) {
		JobFragment jobFragment = new JobFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(AndyConstants.JOB_STATUS,
				AndyConstants.IS_WALKER_STARTED);
		bundle.putSerializable(AndyConstants.REQUEST_DETAIL,
				requestDetails);
		jobFragment.setArguments(bundle);
		mapActivity.addFragment(jobFragment, false,
				AndyConstants.JOB_FRGAMENT_TAG, true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(preferenceHelper.getRequestDetails() != null) {
			loadRequest(preferenceHelper.getRequestDetails());
		}
		else if (preferenceHelper.getRequestId() == AndyConstants.NO_REQUEST) {
			startCheckingUpcomingRequests();
		}
		mapActivity.setActionBarTitle(getString(R.string.app_name));
	}
	@Override
	public void onPause() {
		super.onPause();
		if (preferenceHelper.getRequestId() == AndyConstants.NO_REQUEST) {
			stopCheckingUpcomingRequests();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopCheckingUpcomingRequests();
		cancelSeekbarTimer();
		AndyUtils.removeCustomProgressDialog();
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
				requestReciever);

	}

	@Override
	public void onTaskCompleted(String response, int serviceCode) {
		Log.d("mahi", "response" + response);
		
		switch (serviceCode) {
		case AndyConstants.ServiceCode.GET_ALL_REQUEST:
			AndyUtils.removeCustomProgressDialog();
			AppLog.Log(TAG, "getAllRequests Response :" + response);
			if (!parseContent.isSuccess(response)) {
				return;
			}

			requestDetail = parseContent.parseAllRequests(response);
			if (requestDetail != null) {
				AndyUtils.requestDetails = requestDetail;
				if (selector == false) {
					selector = true;
					GCMIntentService.generateNotification(getActivity(),
							"New Request");
					stopCheckingUpcomingRequests();
					// startTimerForRespondRequest(requestDetail.getTimeLeft());
					setComponentVisible();
					// pbTimeLeft.setMax(requestDetail.getTimeLeft());
					btnClientReqRemainTime.setText(""
							+ requestDetail.getTimeLeft());
					// pbTimeLeft.setProgress(requestDetail.getTimeLeft());
					tvClientName.setText(requestDetail.getClientName());
					// tvClientPhoneNumber.setText(requestDetail
					// .getClientPhoneNumber());
					if (requestDetail.getClientRating() != 0) {
						tvClientRating.setRating(requestDetail
								.getClientRating());
						Log.i("Rating", "" + requestDetail.getClientRating());
					}

					if (requestDetail.getClientProfile() != null)
						if (!requestDetail.getClientProfile().equals(""))
							aQuery.id(ivClientProfilePicture)
									.progress(R.id.pBar)
									.image(requestDetail.getClientProfile());
					
					markerClientLocation = null;
					markerClientLocation = mMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(Double.parseDouble(requestDetail
											.getClientLatitude()), Double
											.parseDouble(requestDetail
													.getClientLongitude())))
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.pin_client_org))
							.title(mapActivity.getResources().getString(
									R.string.client_location)));
					
					markerClient_d_location = null;
					if (requestDetail.getClient_d_latitude() != null
							&& requestDetail.getClient_d_longitude() != null) {
						markerClient_d_location = mMap
								.addMarker(new MarkerOptions()
										.position(
												new LatLng(
														Double.parseDouble(requestDetail
																.getClient_d_latitude()),
														Double.parseDouble(requestDetail
																.getClient_d_longitude())))
										.icon(BitmapDescriptorFactory
												.fromResource(R.drawable.pin_client))
										.title(mapActivity
												.getResources()
												.getString(
														R.string.client_d_location)));
					}

					markerClient_e_location= null;
					if (requestDetail.getE_latitude() != null
							&& requestDetail.getE_longitude() != null) {
						markerClient_e_location = mMap
								.addMarker(new MarkerOptions()
										.position(
												new LatLng(
														Double.parseDouble(requestDetail
																.getE_latitude()),
														Double.parseDouble(requestDetail
																.getE_longitude())))
										.icon(BitmapDescriptorFactory
												.fromResource(R.drawable.pin_client))
										.title(mapActivity
												.getResources()
												.getString(
														R.string.client_e_location)));
					}
					
					startSeekBar();
				}
			}
			break;
			
		case AndyConstants.ServiceCode.CHECK_STATE:
		case AndyConstants.ServiceCode.TOGGLE_STATE:
			AndyUtils.removeCustomProgressDialog();
			if (!parseContent.isSuccess(response)) {
				return;
			}
			AppLog.Log("TAG", "toggle state:" + response);
			if (parseContent.parseAvaibilty(response)) {
				switchSetting.setOnCheckedChangeListener(null);
				switchSetting.setChecked(true);

			} else {
				switchSetting.setOnCheckedChangeListener(null);
				switchSetting.setChecked(false);
			}
			switchSetting.setOnCheckedChangeListener(this);
			break;	
			
		case AndyConstants.ServiceCode.RESPOND_REQUEST:
			AppLog.Log(TAG, "respond Request Response :" + response);
			removeNotification();
			AndyUtils.removeCustomProgressDialog();
			if (parseContent.isSuccess(response)) {
				if (isAccepted) {
					preferenceHelper.putRequestId(requestDetail.getRequestId());
					JobFragment jobFragment = new JobFragment();
					
					Bundle bundle = new Bundle();
					bundle.putInt(AndyConstants.JOB_STATUS,
							AndyConstants.IS_WALKER_STARTED);
					bundle.putSerializable(AndyConstants.REQUEST_DETAIL,
							requestDetail);
					jobFragment.setArguments(bundle);
					mapActivity.addFragment(jobFragment, false,
							AndyConstants.JOB_FRGAMENT_TAG, true);
				} else {
					cancelSeekbarTimer();
					setComponentInvisible();
					if (markerClientLocation != null
							&& markerClientLocation.isVisible()) {
						markerClientLocation.remove();
					}
					if (markerClient_d_location != null
							&& markerClient_d_location.isVisible()) {
						markerClient_d_location.remove();
					}
					
					if (markerClient_e_location != null
							&& markerClient_e_location.isVisible()) {
						markerClient_e_location.remove();
					}
					
					
					preferenceHelper.putRequestId(AndyConstants.NO_REQUEST);
					startCheckingUpcomingRequests();
				}
			}
			break;
		case AndyConstants.ServiceCode.RESPOND_REQUEST_ADVANCE:
			AppLog.Log(TAG, "respond Request Response :" + response);
			removeNotification();
			AndyUtils.removeCustomProgressDialog();
			
			if (parseContent.isSuccess(response)) {
				if (isAccepted) {
					AndyUtils.showToast("Request Accepted", mapActivity);
					//preferenceHelper.putRequestId(Integer.valueOf(advanceRequest.getRequest_id()));
					/*JobFragment jobFragment = new JobFragment();
					Bundle bundle = new Bundle();
					bundle.putInt(AndyConstants.JOB_STATUS,
							AndyConstants.IS_WALKER_STARTED);
					bundle.putSerializable(AndyConstants.REQUEST_DETAIL,
							requestDetail);
					jobFragment.setArguments(bundle);
					mapActivity.addFragment(jobFragment, false,
							AndyConstants.JOB_FRGAMENT_TAG, true);*/
				} else {
					cancelSeekbarTimer();
					setComponentInvisible();
					if (markerClientLocation != null
							&& markerClientLocation.isVisible()) {
						markerClientLocation.remove();
					}
					if (markerClient_d_location != null
							&& markerClient_d_location.isVisible()) {
						markerClient_d_location.remove();
					}
					
					if (markerClient_e_location != null
							&& markerClient_e_location.isVisible()) {
						markerClient_e_location.remove();
					}
					
					
					
					preferenceHelper.putRequestId(AndyConstants.NO_REQUEST);
					startCheckingUpcomingRequests();
				}
			}
			break;
	
			
		default:
			Toast.makeText(getActivity(), "Request timeout, please try again", Toast.LENGTH_SHORT).show();
			break;

		}
	}

	private void startSeekBar() {
		seekbarTimer = new SeekbarTimer(
				requestDetail.getTimeLeft() * 1000, 1000);
		seekbarTimer.start();
		seekBar.add(seekbarTimer);
	}

	private class SeekbarTimer extends CountDownTimer {

		public SeekbarTimer(long startTime, long interval) {
			super(startTime, interval);
			// pbTimeLeft.setProgressDrawable(getResources().getDrawable(
			// R.drawable.customprogress));
		}

		@Override
		public void onFinish() {
			if (seekbarTimer == null) {
				return;
			}
			AndyUtils.showToast(
					mapActivity.getResources().getString(
							R.string.toast_time_over), mapActivity);
			setComponentInvisible();
			preferenceHelper.clearRequestData();
			if (markerClientLocation != null
					&& markerClientLocation.isVisible()) {
				markerClientLocation.remove();
			}
			if (markerClient_d_location != null
					&& markerClient_d_location.isVisible()) {
				markerClient_d_location.remove();
			}
			
			if (markerClient_e_location != null
					&& markerClient_e_location.isVisible()) {
				markerClient_e_location.remove();
			}
			
			
			removeNotification();
			startCheckingUpcomingRequests();
			this.cancel();
			seekbarTimer = null;
			selector = false;

		}

		@Override
		public void onTick(long millisUntilFinished) {
			int time = (int) (millisUntilFinished / 1000);
			if (!isVisible()) {
				return;
			}
			btnClientReqRemainTime.setText("" + time);
			try {

				MediaPlayer r = MediaPlayer.create(getActivity(), R.raw.beep);
				if (time <= 8 && time >= 0) {
					r.start();

				}
				if (time == 0)
					r.stop();

			} catch (Exception e) {
				e.printStackTrace();
			}

			// pbTimeLeft.setProgress(time);
			// if (time <= 5) {
			// pbTimeLeft.setProgressDrawable(getResources().getDrawable(
			// R.drawable.customprogressred));
			// }

		}
	}

	// if status = 1 then accept if 0 then reject
	private void respondRequest(int status, String requestId) {
		if (!AndyUtils.isNetworkAvailable(mapActivity)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet),
					mapActivity);
			return;
		}

		AndyUtils.showCustomProgressDialog(mapActivity, "", getResources()
				.getString(R.string.progress_respond_request), false);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL, AndyConstants.ServiceType.RESPOND_REQUESTS);
		map.put(AndyConstants.Params.ID, preferenceHelper.getUserId());
		map.put(AndyConstants.Params.REQUEST_ID,requestId);
		map.put(AndyConstants.Params.TOKEN, preferenceHelper.getSessionToken());
		map.put(AndyConstants.Params.ACCEPTED, String.valueOf(status));
		map.put(AndyConstants.Params.DATE_TIME, "2015-07-24 10:27:36");
		new HttpRequester(mapActivity, map,
				AndyConstants.ServiceCode.RESPOND_REQUEST, this);
	}
	
	private void respondRequestAdvance(int status, String requestId, String dateTime) {
		if (!AndyUtils.isNetworkAvailable(mapActivity)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet),
					mapActivity);
			return;
		}

		AndyUtils.showCustomProgressDialog(mapActivity, "", getResources()
				.getString(R.string.progress_respond_request), false);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL, AndyConstants.ServiceType.RESPOND_REQUESTS_LATER);
		map.put(AndyConstants.Params.ID, preferenceHelper.getUserId());
		map.put(AndyConstants.Params.REQUEST_ID,requestId);
		map.put(AndyConstants.Params.TOKEN, preferenceHelper.getSessionToken());
		map.put(AndyConstants.Params.ACCEPTED, String.valueOf(status));
		map.put(AndyConstants.Params.DATE_TIME, String.valueOf(dateTime));
		new HttpRequester(mapActivity, map,
				AndyConstants.ServiceCode.RESPOND_REQUEST_ADVANCE, this);

	}
	
	public void checkRequestStatus() {
		if (!AndyUtils.isNetworkAvailable(mapActivity)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet),
					mapActivity);
			return;
		}
		AndyUtils.showCustomProgressDialog(mapActivity, "", getResources()
				.getString(R.string.progress_dialog_request), false);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL,
				AndyConstants.ServiceType.CHECK_REQUEST_STATUS
						+ AndyConstants.Params.ID + "="
						+ preferenceHelper.getUserId() + "&"
						+ AndyConstants.Params.TOKEN + "="
						+ preferenceHelper.getSessionToken() + "&"
						+ AndyConstants.Params.REQUEST_ID + "="
						+ preferenceHelper.getRequestId());
		new HttpRequester(mapActivity, map,
				AndyConstants.ServiceCode.CHECK_REQUEST_STATUS, true, this);
	}

	public void getAllRequests() {
		if (!AndyUtils.isNetworkAvailable(mapActivity)) {
			return;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL,
				AndyConstants.ServiceType.GET_ALL_REQUESTS
						+ AndyConstants.Params.ID + "="
						+ preferenceHelper.getUserId() + "&"
						+ AndyConstants.Params.TOKEN + "="
						+ preferenceHelper.getSessionToken());

		new HttpRequester(mapActivity, map,
				AndyConstants.ServiceCode.GET_ALL_REQUEST, true, this);
	}

	private class TimerRequestStatus extends TimerTask {
		@Override
		public void run() {
			if (isContinueRequest) {
				// isContinueRequest = false;
				getAllRequests();
			}
		}

	}

	private void startCheckingUpcomingRequests() {
		AppLog.Log(TAG, "start checking upcomingRequests");
		isContinueRequest = true;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerRequestStatus(),
				AndyConstants.DELAY, AndyConstants.TIME_SCHEDULE);
	}

	private void stopCheckingUpcomingRequests() {
		AppLog.Log(TAG, "stop checking upcomingRequests");
		isContinueRequest = false;
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void removeNotification() {
		try {
			NotificationManager manager = (NotificationManager) mapActivity
					.getSystemService(mapActivity.NOTIFICATION_SERVICE);
			manager.cancel(AndyConstants.NOTIFICATION_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLocationReceived(LatLng latlong) {
		if (latlong != null) {
			if (mMap != null) {
				if (markerDriverLocation == null) {
					markerDriverLocation = mMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(latlong.latitude,
											latlong.longitude))
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.pin_driver))
							.title(mapActivity.getResources().getString(
									R.string.my_location)));
					mMap.animateCamera(CameraUpdateFactory
							.newLatLngZoom(new LatLng(latlong.latitude,
									latlong.longitude), 16));
				} else {
					markerDriverLocation.setPosition(new LatLng(
							latlong.latitude, latlong.longitude));
				}
			}
		}
	}

	public void setComponentVisible() {
		llAcceptReject.setVisibility(View.VISIBLE);
		btnClientReqRemainTime.setVisibility(View.VISIBLE);
		// rlTimeLeft.setVisibility(View.VISIBLE);
		llUserDetailView.setVisibility(View.VISIBLE);
	}

	public void setComponentInvisible() {
		llAcceptReject.setVisibility(View.GONE);
		btnClientReqRemainTime.setVisibility(View.GONE);
		// rlTimeLeft.setVisibility(View.GONE);
		llUserDetailView.setVisibility(View.GONE);
	}

	public void cancelSeekbarTimer() {
		if(seekBar != null && seekBar.size() > 0){
			for(SeekbarTimer seekBarTimer : seekBar){
				if (seekBarTimer != null) {
					seekBarTimer.cancel();
					seekBarTimer = null;
				}
			}
		}
	}
	
	public void cancelSeekbarTimerLogic() {
		if (seekbarTimer != null) {
			seekbarTimer.cancel();
			seekbarTimer = null;
		}
	}
	
	

	public void onDestroyView() {
		SupportMapFragment f = (SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.clientReqMap);
		if (f != null) {
			try {
				
				getFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		mMap = null;
		super.onDestroyView();
	}

	/* added by amal */
	private String strAddress = null;

	private void getAddressFromLocation(final LatLng latlng,
			final MyFontTextView et) {

		Geocoder gCoder = new Geocoder(getActivity());
		Log.d("hey", String.valueOf(strAddress));
		try {
			final List<Address> list = gCoder.getFromLocation(latlng.latitude,
					latlng.longitude, 1);
			if (list != null && list.size() > 0) {
				Address address = list.get(0);
				StringBuilder sb = new StringBuilder();
				if (address.getAddressLine(0) != null) {

					sb.append(address.getAddressLine(0)).append(", ");
				}
				sb.append(address.getLocality()).append(", ");
				// sb.append(address.getPostalCode()).append(",");
				sb.append(address.getCountryName());
				strAddress = sb.toString();

				strAddress = strAddress.replace(",null", "");
				strAddress = strAddress.replace("null", "");
				strAddress = strAddress.replace("Unnamed", "");
				if (!TextUtils.isEmpty(strAddress)) {

					et.setText(strAddress);

				}
			}
			Log.d("hey", strAddress);

		} catch (IOException exc) {
			exc.printStackTrace();
		}

	}

	private class newRequestReciever extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Gson gson = new Gson();
			String response = intent.getStringExtra(AndyConstants.NEW_REQUEST);
			AppLog.Log(TAG, "FROM BROAD CAST-->" + response);
			try {
				AdvanceRequest advanceRequest = gson.fromJson(response, AdvanceRequest.class);
				if(advanceRequest != null && advanceRequest.getIs_advance() != null && advanceRequest.getIs_advance().equalsIgnoreCase("1")) {
					
					// selector == false 
					// stop check 
					//show accept or reject dialog
					//if(selector  == false ) {
						//selector = true;
						stopCheckingUpcomingRequests();
						showAdvanceBookingDialog(advanceRequest);
					//}
					
				}
				else {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getInt(AndyConstants.Params.UNIQUE_ID) == 1) {
						requestDetail = parseContent.parseNotification(response);
						if (requestDetail != null) {
							AndyUtils.requestDetails = requestDetail;
							if (selector == false) {
								selector = true;
								stopCheckingUpcomingRequests();
								// startTimerForRespondRequest(requestDetail.getTimeLeft());
								
								setComponentVisible();
								// pbTimeLeft.setMax(requestDetail.getTimeLeft());
								btnClientReqRemainTime.setText(""
										+ requestDetail.getTimeLeft());
								// pbTimeLeft.setProgress(requestDetail.getTimeLeft());
								tvClientName.setText(requestDetail.getClientName());
								// tvClientPhoneNumber.setText(requestDetail
								// .getClientPhoneNumber());
								if (requestDetail.getClientRating() != 0) {
									tvClientRating.setRating(requestDetail
											.getClientRating());
									Log.i("Rating",
											"" + requestDetail.getClientRating());
								}
								
								if (requestDetail.getClientProfile() != null)
									if (!requestDetail.getClientProfile()
											.equals(""))
										aQuery.id(ivClientProfilePicture)
										.progress(R.id.pBar)
										.image(requestDetail
												.getClientProfile());
								markerClientLocation = null;
								markerClientLocation = mMap
										.addMarker(new MarkerOptions()
										.position(
												new LatLng(
														Double.parseDouble(requestDetail
																.getClientLatitude()),
																Double.parseDouble(requestDetail
																		.getClientLongitude())))
																		.icon(BitmapDescriptorFactory
																				.fromResource(R.drawable.pin_client_org))
																				.title(mapActivity
																						.getResources()
																						.getString(
																								R.string.client_location)));
								
								markerClient_d_location = null;
								Log.d("xxx",
										"he" + requestDetail.getClient_d_latitude());
								Log.d("xxx",
										"she"
												+ requestDetail
												.getClient_d_longitude());
								if (requestDetail.getClient_d_latitude() != null
										&& requestDetail.getClient_d_longitude() != null) {
									Log.d("xxx", "inside");
									markerClient_d_location = mMap
											.addMarker(new MarkerOptions()
											.position(
													new LatLng(
															Double.parseDouble(requestDetail
																	.getClient_d_latitude()),
																	Double.parseDouble(requestDetail
																			.getClient_d_longitude())))
																			.icon(BitmapDescriptorFactory
																					.fromResource(R.drawable.pin_client))
																					.title(mapActivity
																							.getResources()
																							.getString(
																									R.string.client_d_location)));
								}
								
								markerClient_d_location = null;
								Log.d("xxx",
										"he" + requestDetail.getClient_d_latitude());
								Log.d("xxx",
										"she"
												+ requestDetail
												.getClient_d_longitude());
								if (requestDetail.getClient_d_latitude() != null
										&& requestDetail.getClient_d_longitude() != null) {
									Log.d("xxx", "inside");
									markerClient_d_location = mMap
											.addMarker(new MarkerOptions()
											.position(
													new LatLng(
															Double.parseDouble(requestDetail
																	.getClient_d_latitude()),
																	Double.parseDouble(requestDetail
																			.getClient_d_longitude())))
																			.icon(BitmapDescriptorFactory
																					.fromResource(R.drawable.pin_client))
																					.title(mapActivity
																							.getResources()
																							.getString(
																									R.string.client_d_location)));
								}
								
								markerClient_e_location= null;
								if (requestDetail.getE_latitude() != null
										&& requestDetail.getE_longitude() != null) {
									Log.d("xxx", "inside");
									markerClient_e_location = mMap
											.addMarker(new MarkerOptions()
											.position(
													new LatLng(
															Double.parseDouble(requestDetail
																	.getE_latitude()),
																	Double.parseDouble(requestDetail
																			.getE_longitude())))
																			.icon(BitmapDescriptorFactory
																					.fromResource(R.drawable.pin_client))
																					.title(mapActivity
																							.getResources()
																							.getString(
																									R.string.client_e_location)));
								}
								
								/*seekbarTimer = new SeekbarTimer(
										requestDetail.getTimeLeft() * 1000, 1000);
								seekbarTimer.start();*/
								startSeekBar();
								AppLog.Log(TAG, "From broad cast recieved request");
							}
						}
					} else {
						setComponentInvisible();
						selector = false;
						preferenceHelper.clearRequestData();
						if (markerClientLocation != null
								&& markerClientLocation.isVisible()) {
							markerClientLocation.remove();
						}
						if (markerClient_d_location != null
								&& markerClient_d_location.isVisible()) {
							markerClient_d_location.remove();
						}
						
						if (markerClient_e_location != null
								&& markerClient_e_location.isVisible()) {
							markerClient_e_location.remove();
						}
						cancelSeekbarTimer();
						removeNotification();
						startCheckingUpcomingRequests();
					}
				}
				
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
	AdvanceRequest advanceRequest;

	private void showAdvanceBookingDialog(final AdvanceRequest advanceRequest) {
		Log.d("ClientRequestFragment", "in Advance Request details");
		this.advanceRequest = advanceRequest;
		final Dialog mDialog = new Dialog(mapActivity, R.style.MyFareestimateDialog);
		mDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		mDialog.setContentView(R.layout.advance_request_details);
		mDialog.setCancelable(false);
		TextView fromaddress, toaddress, customerName, toaddresstwo, time;
		MyFontButton btnAccept, btnReject, fareestimatetitle;
		fromaddress = (TextView) mDialog.findViewById(R.id.fromaddress);
		toaddress = (TextView) mDialog.findViewById(R.id.toaddress);
		
		//estimatedfare = (TextView) mDialog.findViewById(R.id.estimatedfare);
		customerName = (TextView) mDialog.findViewById(R.id.customerName);
		toaddresstwo = (TextView) mDialog.findViewById(R.id.toaddresstwo);
		time = (TextView) mDialog.findViewById(R.id.time);
		btnAccept = (MyFontButton) mDialog.findViewById(R.id.btnaccept);
		btnReject = (MyFontButton) mDialog.findViewById(R.id.btnreject);
		fareestimatetitle= (MyFontButton) mDialog.findViewById(R.id.fareestimatetitle);
		fareestimatetitle.setText("Advance Booking");
		fromaddress.setText(advanceRequest.getAddress1());
		toaddress.setText(advanceRequest.getAddress2());
		if(advanceRequest.getType().equalsIgnoreCase("double")){
			toaddresstwo.setVisibility(View.VISIBLE);
			toaddresstwo.setText(advanceRequest.getAddress3());
		}
		customerName.setText(advanceRequest.getRequest_data().getOwner().getName());
		time .setText(advanceRequest.getRequest_start_time()); // Currently dummy not coming from server
		
		// Double destination logic
		/*
		 * if(isDoubleDestination()) { toaddresstwo.setVisibility(View.VISIBLE);
		 * toaddresstwo.setText(edtdestination2.getText().toString().trim());
		 * 
		 * }else { toaddresstwo.setVisibility(View.INVISIBLE); }
		 */
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				isAccepted = true;
				respondRequestAdvance(1, advanceRequest.getRequest_id(), advanceRequest.getRequest_start_time());
				mDialog.dismiss();
			}
		});
		mDialog.show();
		btnReject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				isAccepted = false;
				respondRequestAdvance(0, advanceRequest.getRequest_id(), advanceRequest.getRequest_start_time());
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		AppLog.Log("TAG", "On checked change listener");
		switch (buttonView.getId()) {
		case R.id.switchAvaibility:
			changeState();
			break;

		default:
			break;
		}
	}
	
	private void checkState() {
		if (!AndyUtils.isNetworkAvailable(mapActivity)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet), mapActivity);
			return;
		}
		/*AndyUtils.showCustomProgressDialog(mapActivity, "",
				getResources().getString(R.string.progress_getting_avaibility),
				false);*/
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL,
				AndyConstants.ServiceType.CHECK_STATE + AndyConstants.Params.ID
						+ "=" + preferenceHelper.getUserId() + "&"
						+ AndyConstants.Params.TOKEN + "="
						+ preferenceHelper.getSessionToken());
		new HttpRequester(mapActivity, map, AndyConstants.ServiceCode.CHECK_STATE,
				true, this);
	}
	
	private void changeState() {
		if (!AndyUtils.isNetworkAvailable(mapActivity)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet), mapActivity);
			return;
		}

		AndyUtils.showCustomProgressDialog(mapActivity, "",
				getResources().getString(R.string.progress_changing_avaibilty),
				false);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL, AndyConstants.ServiceType.TOGGLE_STATE);
		map.put(AndyConstants.Params.ID, preferenceHelper.getUserId());
		map.put(AndyConstants.Params.TOKEN, preferenceHelper.getSessionToken());
		map.put(AndyConstants.Params.IS_LOGGING_OUT, "0");
		new HttpRequester(mapActivity, map, AndyConstants.ServiceCode.TOGGLE_STATE,
				this);

	}
	
}
