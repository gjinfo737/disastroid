package com.example.roodootdootdadoo;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BasicView {

	protected BaseActivity activity;
	protected int layoutID;
	protected View mainView;
	private boolean findViewsInMainView = false;

	public BasicView(BaseActivity activity, int layoutID) {
		this.activity = activity;
		this.layoutID = layoutID;
		initViews(this.layoutID);
	}

	public View getMainView() {
		return mainView;
	}

	public void setMainView(View mainView) {
		this.mainView = mainView;
	}

	protected void initViews(int layoutID) {
		activity.setContentView(layoutID);
	}

	protected Button findbutton(int id) {
		return (Button) findView(id);
	}

	protected EditText findEditText(int id) {
		return (EditText) findView(id);
	}

	protected TextView findTextView(int id) {
		return (TextView) findView(id);
	}

	protected ImageButton findImageButton(int id) {
		return (ImageButton) findView(id);
	}

	protected ImageView findImageView(int id) {
		return (ImageView) findView(id);
	}

	protected ListView findListView(int id) {
		return (ListView) findView(id);
	}

	protected GridView findGridView(int id) {
		return (GridView) findView(id);
	}

	protected LinearLayout findLinearLayout(int id) {
		return (LinearLayout) findView(id);
	}

	protected RelativeLayout findRelativeLayout(int id) {
		return (RelativeLayout) findView(id);
	}

	protected ProgressBar findProgressBar(int id) {
		return (ProgressBar) findView(id);
	}

	protected View findView(int id) {
		if (findViewsInMainView)
			return mainView.findViewById(id);
		else
			return activity.findViewById(id);
	}

	public synchronized boolean isFindViewsInMainView() {
		return findViewsInMainView;
	}

	public synchronized void setFindViewsInMainView(boolean findViewsInMainView) {
		this.findViewsInMainView = findViewsInMainView;
	}

}
