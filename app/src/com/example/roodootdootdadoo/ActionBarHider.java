package com.example.roodootdootdadoo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;

public class ActionBarHider {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ActionBarHider(Activity activity) {
		if (Build.VERSION.SDK_INT < 11)
			return;
		try {
			ActionBar actionBar = activity.getActionBar();
			if (actionBar != null)
				actionBar.hide();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
