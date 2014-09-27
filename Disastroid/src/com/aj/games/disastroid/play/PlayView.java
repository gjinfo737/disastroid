package com.aj.games.disastroid.play;

import java.util.List;

import android.app.Activity;

import com.aj.games.disastroid.R.id;
import com.aj.games.disastroid.R.layout;
import com.aj.games.disastroid.obstacle.Obstacle;
import com.aj.games.disastroid.ship.Ship;
import com.aj.games.disastroid.views.ShipView;

public class PlayView {

    private PlayPresenter presenter;
    private Activity activity;
    private ShipView shipView;

    public PlayView(PlayPresenter presenter, Activity activity) {
	this.presenter = presenter;
	this.activity = activity;
	this.activity.setContentView(layout.play_activity);
	this.shipView = (ShipView) this.activity.findViewById(id.shipview);
    }

    public void update(Ship ship, List<Obstacle> obstacles) {
	this.shipView.render(ship, obstacles);
    }

}
