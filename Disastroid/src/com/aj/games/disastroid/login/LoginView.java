package com.aj.games.disastroid.login;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.aj.games.disastroid.R.id;

public class LoginView {

    private LoginPresenter presenter;
    private Activity activity;
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;

    public LoginView(LoginPresenter presenter, Activity activity) {
	this.presenter = presenter;
	this.activity = activity;

	this.btnLogin = (Button) this.activity.findViewById(id.btnLogin);
	this.etUsername = (EditText) this.activity.findViewById(id.etUsername);
	this.etPassword = (EditText) this.activity.findViewById(id.etPassword);

	this.btnLogin.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		login();
	    }

	});
    }

    private void login() {
	this.presenter.login(getUsername(), getPassword());
    }

    private String getUsername() {
	return this.etUsername.getText().toString();
    }

    private String getPassword() {
	return this.etPassword.getText().toString();
    }

}
