package com.ukcl.driverapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.splunk.mint.Mint;
import com.ukcl.driverapp.gcm.CommonUtilities;
import com.ukcl.driverapp.gcm.GCMRegisterHendler;
import com.ukcl.driverapp.utills.AndyUtils;
import com.ukcl.driverapp.utills.AppLog;
import com.ukcl.driverapp.utills.PreferenceHelper;


public class MainActivity extends Activity implements OnClickListener {

	private boolean isRecieverRegister = false;
	private static final String TAG = "FirstFragment";
	private PreferenceHelper preferenceHelper;
	// private Animation topToBottomAnimation, bottomToTopAnimation;
	private RelativeLayout rlLoginRegisterLayout;

	// private MyFontTextView tvMainBottomView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*BugSenseHandler.initAndStartSession(MainActivity.this,
				"5861938a");
		*/
		Mint.initAndStartSession(MainActivity.this, "f1732acb");
		preferenceHelper = new PreferenceHelper(this);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).build();
		ImageLoader.getInstance().init(config);

		if (preferenceHelper.getUserId() != null) {
			startActivity(new Intent(this, MapActivity.class));
			this.finish();
			return;
		}
		setContentView(R.layout.fragment_main);
		rlLoginRegisterLayout = (RelativeLayout) findViewById(R.id.rlLoginRegisterLayout);
		// tvMainBottomView = (MyFontTextView) mainFragmentView
		// .findViewById(R.id.tvMainBottomView);

		findViewById(R.id.btnFirstSignIn).setOnClickListener(this);
		findViewById(R.id.btnFirstRegister).setOnClickListener(this);

		if (TextUtils.isEmpty(new PreferenceHelper(MainActivity.this)
				.getDeviceToken())) {
			isRecieverRegister = true;
			registerGcmReceiver(mHandleMessageReceiver);
		} else {

			AppLog.Log(TAG, "device already registerd with :"
					+ new PreferenceHelper(MainActivity.this).getDeviceToken());
		}
		// TODO Auto-generated method stub
	}

	private BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			AndyUtils.removeCustomProgressDialog();
			if (intent.getAction().equals(
					CommonUtilities.DISPLAY_MESSAGE_REGISTER)) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					int resultCode = bundle.getInt(CommonUtilities.RESULT);
					AppLog.Log(TAG, "Result code-----> " + resultCode);
					if (resultCode == Activity.RESULT_OK) {

					} else {
						Toast.makeText(MainActivity.this,
								getString(R.string.register_gcm_failed),
								Toast.LENGTH_SHORT).show();
						finish();
					}

				}
			}
		}
	};

	public void registerGcmReceiver(BroadcastReceiver mHandleMessageReceiver) {
		if (mHandleMessageReceiver != null) {
			AndyUtils.showCustomProgressDialog(this, "", getResources()
					.getString(R.string.progress_loading), false);
			new GCMRegisterHendler(MainActivity.this, mHandleMessageReceiver);

		}
	}

	public void unregisterGcmReceiver(BroadcastReceiver mHandleMessageReceiver) {
		if (mHandleMessageReceiver != null) {

			if (mHandleMessageReceiver != null) {
				unregisterReceiver(mHandleMessageReceiver);
			}

		}

	}

	@Override
	public void onClick(View v) {

		Intent startRegisterActivity = new Intent(MainActivity.this,
				RegisterActivity.class);
		switch (v.getId()) {

		case R.id.btnFirstRegister:
			if (!AndyUtils.isNetworkAvailable(MainActivity.this)) {
				AndyUtils.showToast(
						getResources().getString(R.string.toast_no_internet),
						MainActivity.this);
				return;
			}
			startRegisterActivity.putExtra("isSignin", false);

			break;

		case R.id.btnFirstSignIn:

			startRegisterActivity.putExtra("isSignin", true);

			break;

		default:
			break;
		}
		startActivity(startRegisterActivity);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		finish();

	}

	@Override
	public void onDestroy() {
		if (isRecieverRegister) {
			unregisterGcmReceiver(mHandleMessageReceiver);
			isRecieverRegister = false;
		}
		super.onDestroy();
	}

}
