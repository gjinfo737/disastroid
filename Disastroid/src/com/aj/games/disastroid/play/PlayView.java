package com.aj.games.disastroid.play;

import java.util.List;

import android.app.Activity;
import android.widget.TextView;

import com.aj.games.disastroid.R.id;
import com.aj.games.disastroid.R.layout;
import com.aj.games.disastroid.obstacle.Obstacle;
import com.aj.games.disastroid.obstacles.Explosion;
import com.aj.games.disastroid.ship.Ship;
import com.aj.games.disastroid.views.ShipView;

public class PlayView {

    private PlayPresenter presenter;
    private Activity activity;
    private ShipView shipView;
    private TextView tvLevel;

    public PlayView(PlayPresenter presenter, Activity activity) {
	this.presenter = presenter;
	this.activity = activity;
	this.activity.setContentView(layout.play_activity);
	this.shipView = (ShipView) this.activity.findViewById(id.shipview);
	this.tvLevel = (TextView) this.activity.findViewById(id.tv_level);
    }

    public void update(Ship ship, List<Obstacle> obstacles, List<Explosion> explosions, int level) {
	this.shipView.render(ship, obstacles, explosions);

	this.tvLevel.setText("Level: " + level);
    }

}
