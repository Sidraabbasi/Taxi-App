package com.ukcl.driverapp;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ukcl.driverapp.base.ActionBarBaseActivitiy;
import com.ukcl.driverapp.fragment.LoginFragment;
import com.ukcl.driverapp.fragment.RegisterFragment;
import com.ukcl.driverapp.gcm.GCMRegisterHendler;
import com.ukcl.driverapp.utills.AndyConstants;
import com.ukcl.driverapp.utills.AndyUtils;

/**
 * @author Kishan H Dhamat
 * 
 */
public class RegisterActivity extends ActionBarBaseActivitiy {
	public ActionBar actionBar;
	

	private ProgressBar pbar;
	

	private String filePath;

	private int chooserType;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		//logTokens();
		
		
		actionBar = getSupportActionBar();
		
		// addFragment(new UberMainFragment(), false,
		// AndyConstants.MAIN_FRAGMENT_TAG);
		if (getIntent().getBooleanExtra("isSignin", false)) {

			addFragment(new LoginFragment(), true,
					AndyConstants.LOGIN_FRAGMENT_TAG, false);
		} else {
			addFragment(new RegisterFragment(), true,
					AndyConstants.REGISTER_FRAGMENT_TAG, false);
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btnActionNotification:
			onBackPressed();
			break;

		default:
			break;
		}

	}

	public void registerGcmReceiver(BroadcastReceiver mHandleMessageReceiver) {
		if (mHandleMessageReceiver != null) {
			AndyUtils.showCustomProgressDialog(this, "", getResources()
					.getString(R.string.progress_loading), false);
			new GCMRegisterHendler(RegisterActivity.this,
					mHandleMessageReceiver);

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
	public void onBackPressed() {
		// TODO Auto-generated method stub

		Fragment signinFragment = getSupportFragmentManager()
				.findFragmentByTag(AndyConstants.LOGIN_FRAGMENT_TAG);
		Fragment fragment = getSupportFragmentManager().findFragmentByTag(
				AndyConstants.REGISTER_FRAGMENT_TAG);
		if (fragment != null && fragment.isVisible()) {

			goToMainActivity();
		} else if (signinFragment != null && signinFragment.isVisible()) {
			goToMainActivity();
		} else {
			super.onBackPressed();
		}

	}

	
	
	
	
	
/*	private void chooseImage() {
		chooserType = ChooserType.REQUEST_PICK_PICTURE;
		imageChooserManager = new ImageChooserManager(this,
				ChooserType.REQUEST_PICK_PICTURE, "myfolder", true);
		imageChooserManager.setImageChooserListener(this);
		try {
			pbar.setVisibility(View.VISIBLE);
			filePath = imageChooserManager.choose();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void takePicture() {
		chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
		imageChooserManager = new ImageChooserManager(this,
				ChooserType.REQUEST_CAPTURE_PICTURE, "myfolder", true);
		imageChooserManager.setImageChooserListener(this);
		try {
			pbar.setVisibility(View.VISIBLE);
			filePath = imageChooserManager.choose();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK
				&& (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
			if (imageChooserManager == null) {
				reinitializeImageChooser();
			}
			imageChooserManager.submit(requestCode, data);
		} else {
			pbar.setVisibility(View.GONE);
		}
	}

	@Override
	public void onImageChosen(final ChosenImage image) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				pbar.setVisibility(View.GONE);
				if (image != null) {
					//textViewFile.setText(image.getFilePathOriginal());
					//imageViewThumbnail.setImageURI(Uri.parse(new File(image
							//.getFileThumbnail()).toString()));
					//imageViewThumbSmall.setImageURI(Uri.parse(new File(image
							//.getFileThumbnailSmall()).toString()));
				}
			}
		});
	}

	@Override
	public void onError(final String reason) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				pbar.setVisibility(View.GONE);
				Toast.makeText(RegisterActivity.this, reason,
						Toast.LENGTH_LONG).show();
			}
		});
	}

*/	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}
	
	
	
	/*private void reinitializeImageChooser() {
		imageChooserManager = new ImageChooserManager(this, chooserType,
				"myfolder", true);
		imageChooserManager.setImageChooserListener(this);
		imageChooserManager.reinitialize(filePath);
	}
*/
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("chooser_type", chooserType);
		outState.putString("media_path", filePath);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("chooser_type")) {
				chooserType = savedInstanceState.getInt("chooser_type");
			}

			if (savedInstanceState.containsKey("media_path")) {
				filePath = savedInstanceState.getString("media_path");
			}
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	

}
