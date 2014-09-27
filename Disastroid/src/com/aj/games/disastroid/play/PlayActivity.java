package com.aj.games.disastroid.play;

import android.os.Bundle;
import android.view.MotionEvent;

import com.aj.games.disastroid.BaseActivity;

public class PlayActivity extends BaseActivity {

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

	int action = event.getAction();
	if (action == MotionEvent.ACTION_DOWN) {
	    this.playPresenter.onTap();
	    return true;
	}

	return super.onTouchEvent(event);
    }
}
