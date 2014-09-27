package com.aj.games.disastroid.login;

import android.app.Activity;
import android.os.Bundle;

import com.aj.games.disastroid.R.layout;

public class LoginActivity extends Activity {

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(layout.login_activity);

	loginPresenter = new LoginPresenter(this);
    }
}
