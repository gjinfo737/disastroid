package com.aj.games.disastroid.login;

import android.app.Activity;
import android.os.Bundle;

public class LoginActivity extends Activity {

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	this.loginPresenter = new LoginPresenter(this);
    }
}
