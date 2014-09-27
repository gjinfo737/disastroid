package com.aj.games.disastroid.play;

import android.app.Activity;

import com.aj.games.disastroid.R.layout;

public class PlayView {

    private PlayPresenter presenter;
    private Activity activity;

    public PlayView(PlayPresenter presenter, Activity activity) {
	this.presenter = presenter;
	this.activity = activity;
	this.activity.setContentView(layout.play_activity);

    }

}
