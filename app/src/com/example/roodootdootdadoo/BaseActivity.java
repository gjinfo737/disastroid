package com.example.roodootdootdadoo;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;

public class BaseActivity extends Activity {
	public static final String LOG_FOLDER = Environment.getExternalStorageDirectory() + File.separator + "vitalilogs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new ActionBarHider(this);
	}

	public Context getContext() {
		return this;
	}

	public View inflate(int resource, ViewGroup root) {
		return getLayoutInflater().inflate(resource, root);
	}
}
