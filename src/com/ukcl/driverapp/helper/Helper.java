package com.ukcl.driverapp.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Helper {
	public static Helper instance = null;
	
	public static Helper getInstance() {
		if(instance == null) {
			instance = new Helper();
		}
			return instance;
	}
	
	public Helper() {
	
	}
	public void hideKeyboard(View view, Context contex) {
        InputMethodManager inputMethodManager =(InputMethodManager)contex.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
	
	public void setFocusListener(EditText edtField, final Context context) {
		edtField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	        @Override
	        public void onFocusChange(View v, boolean hasFocus) {
	            if (!hasFocus) {
	                hideKeyboard(v,context);
	            }
	        }
	    });
	}
}
