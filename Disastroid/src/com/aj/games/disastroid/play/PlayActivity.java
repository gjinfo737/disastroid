package com.aj.games.disastroid.play;

import android.app.Activity;
import android.os.Bundle;

public class PlayActivity extends Activity {

    private PlayPresenter playPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	playPresenter = new PlayPresenter(this);
    }

    @Override
    protected void onResume() {
	super.onResume();

	playPresenter.onResume();
    }

    @Override
    protected void onPause() {
	super.onPause();
	playPresenter.onPause();
    }
}
