package com.aj.games.disastroid.login;

import android.os.Bundle;

import com.aj.games.disastroid.BaseActivity;

public class LoginActivity extends BaseActivity {

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	this.loginPresenter = new LoginPresenter(this);
    }
}
