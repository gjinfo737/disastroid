package com.aj.games.disastroid.login;

import android.app.Activity;

public class LoginPresenter {

    private Activity activity;
    private LoginView view;

    public LoginPresenter(Activity activity) {
	this.activity = activity;
	this.view = new LoginView(this, activity);

    }

    public void login(String username, String password) {
	// TODO Auto-generated method stub

    }

}
