package com.ukcl.driverapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ukcl.driverapp.base.ActionBarBaseActivitiy;
import com.ukcl.driverapp.db.DBHelper;
import com.ukcl.driverapp.model.User;
import com.ukcl.driverapp.utills.PreferenceHelper;


public class SwapActivity extends ActionBarBaseActivitiy {

	private DBHelper dbHelper;
	private MediaPlayer mp;
	Button btn_white;
	private PreferenceHelper preferenceHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		
		// Remove Alarm logic
		//preferenceHelper = new PreferenceHelper(this);
		/*String id = getIntent().getStringExtra("id");
		try {
			preferenceHelper.removeBooking(id);
		} catch (Exception e) {
		
		}*/
		
		btn_white = (Button)findViewById(R.id.btn_white);
		btn_white.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showActivities();
			}
		});
		mp = MediaPlayer.create(getBaseContext(), R.raw.booking_notification);
		mp.setLooping(true);
		playSound(this, getAlarmUri());
		setActionBarTitle("Booking notification");
		setActionBarIcon(R.drawable.promotion);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mp.stop();
	}
	private void playSound(final Context context, Uri alert) {
		Thread background = new Thread(new Runnable() {
			public void run() {
				try {

					mp.start();
				} catch (Throwable t) {
					Log.i("Animation", "Thread  exception " + t);
				}
			}
		});
		background.start();
	}

	@Override
	public void onClick(View arg0) {

	}
	
	private Uri getAlarmUri() {
        
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
	
	private void showActivities() {
		dbHelper = new DBHelper(getApplicationContext());
		User user = dbHelper.getUser();
		if (user != null) {
			//startActivity(new Intent(this, MapActivity.class));
			Intent i = new Intent(this, MapActivity.class);
			// set the new task and clear flags
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(i);
			
		} else {
			Intent startRegisterActivity = new Intent(SwapActivity.this,
					RegisterActivity.class);
			//startRegisterActivity.putExtra("isSignin", true);
			//startActivity(startRegisterActivity);
			Intent i = new Intent(SwapActivity.this, RegisterActivity.class);
			// set the new task and clear flags
			i.putExtra("isSignin", true);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
			
		}
		finish();
	}
	 

}
