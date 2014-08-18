package com.example.roodootdootdadoo.play;

import com.example.roodootdootdadoo.BaseActivity;
import com.example.roodootdootdadoo.BasicView;
import com.example.roodootdootdadoo.R.id;
import com.example.roodootdootdadoo.R.layout;

public class PlayView extends BasicView {

	private PlayViewControl pvc;

	public PlayView(BaseActivity activity) {
		super(activity, layout.activity_main);
	}

	@Override
	protected void initViews(int layoutID) {
		super.initViews(layoutID);
		pvc = (PlayViewControl) findView(id.play_view_control);
	}

	public PlayViewControl getPlayViewControl() {
		return pvc;
	}

}
