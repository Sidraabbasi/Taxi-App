package com.ukcl.driverapp.base;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;

import com.ukcl.driverapp.MapActivity;
import com.ukcl.driverapp.R;
import com.ukcl.driverapp.parse.AsyncTaskCompleteListener;
import com.ukcl.driverapp.parse.HttpRequester;
import com.ukcl.driverapp.parse.ParseContent;
import com.ukcl.driverapp.utills.AndyConstants;
import com.ukcl.driverapp.utills.AndyUtils;
import com.ukcl.driverapp.utills.PreferenceHelper;

/**
 * @author Kishan H Dhamat
 * 
 */

public abstract class BaseMapFragment extends Fragment implements
		OnClickListener, AsyncTaskCompleteListener {
	protected MapActivity mapActivity;
	protected PreferenceHelper preferenceHelper;
	protected ParseContent parseContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapActivity = (MapActivity) getActivity();
		preferenceHelper = new PreferenceHelper(mapActivity);
		parseContent = new ParseContent(mapActivity);
	}

	public void startActivityForResult(Intent intent, int requestCode,
			String fragmentTag) {
		mapActivity.startActivityForResult(intent, requestCode, fragmentTag);
	}

	@Override
	@Deprecated
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
	}

	private void login() {
		if (!AndyUtils.isNetworkAvailable(mapActivity)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet),
					mapActivity);
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL, AndyConstants.ServiceType.LOGIN);
		map.put(AndyConstants.Params.EMAIL, preferenceHelper.getEmail());
		map.put(AndyConstants.Params.PASSWORD, preferenceHelper.getPassword());
		map.put(AndyConstants.Params.DEVICE_TYPE,
				AndyConstants.DEVICE_TYPE_ANDROID);
		map.put(AndyConstants.Params.DEVICE_TOKEN,
				preferenceHelper.getDeviceToken());
		map.put(AndyConstants.Params.LOGIN_BY, AndyConstants.MANUAL);
		new HttpRequester(mapActivity, map, AndyConstants.ServiceCode.LOGIN,
				this);

	}

	private void loginSocial(String id, String loginType) {
		if (!AndyUtils.isNetworkAvailable(mapActivity)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet),
					mapActivity);
			return;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL, AndyConstants.ServiceType.LOGIN);
		map.put(AndyConstants.Params.SOCIAL_UNIQUE_ID, id);
		map.put(AndyConstants.Params.DEVICE_TYPE,
				AndyConstants.DEVICE_TYPE_ANDROID);
		map.put(AndyConstants.Params.DEVICE_TOKEN,
				preferenceHelper.getDeviceToken());
		map.put(AndyConstants.Params.LOGIN_BY, loginType);
		new HttpRequester(mapActivity, map, AndyConstants.ServiceCode.LOGIN,
				this);

	}

}
