package com.aj.games.disastroid.play;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
    private Button btnPause;

    public PlayView(PlayPresenter presenter, Activity activity) {
	this.presenter = presenter;
	this.activity = activity;
	this.activity.setContentView(layout.play_activity);
	this.shipView = (ShipView) this.activity.findViewById(id.shipview);
	this.tvLevel = (TextView) this.activity.findViewById(id.tv_level);
	this.btnPause = (Button) this.activity.findViewById(id.btn_pause);
	bind();
    }

    private void bind() {
	this.btnPause.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View arg0) {
		presenter.togglePause();
	    }
	});
    }

    public void showPaused(boolean isPaused) {
	btnPause.setText(isPaused ? ">" : "||");
    }

    public void update(Ship ship, List<Obstacle> obstacles, List<Explosion> explosions, int level) {
	this.shipView.render(ship, obstacles, explosions);

	this.tvLevel.setText("Level: " + level);
    }

    public void gameOver() {
	btnPause.setEnabled(false);
	this.tvLevel.setText(tvLevel.getText() + ". You have to restart the app as punishment.");

    }

}
