package com.aj.games.disastroid.login;

import android.app.Activity;
import android.content.Intent;

import com.aj.games.disastroid.play.PlayActivity;

public class LoginPresenter {

    private Activity activity;
    private LoginView view;

    public LoginPresenter(Activity activity) {
		this.activity = activity;
		this.view = new LoginView(this, activity);
		onLoginSuccess();
    }

    public void login(String username, String password) {
    	this.onLoginSuccess();
    }

    private void onLoginSuccess() {
		Intent intent = new Intent(this.activity, PlayActivity.class);
		this.activity.startActivity(intent);
    }

}
