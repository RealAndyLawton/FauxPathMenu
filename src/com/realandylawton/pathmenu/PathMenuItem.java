package com.realandylawton.pathmenu;

import android.view.animation.Animation;
import android.widget.ImageView;

public class PathMenuItem {
	
	private int mResourceId;
	private Animation mInAnimation;
	private ImageView mView;

	public Animation getInAnimation() {
		return mInAnimation;
	}

	public void setInAnimation(Animation mInAnimation) {
		this.mInAnimation = mInAnimation;
	}

	public PathMenuItem() {
		// TODO Auto-generated constructor stub
	}
	
	public PathMenuItem(int resourceId) {
		mResourceId = resourceId;
	}

	public int getResourceId() {
		return mResourceId;
	}

	public void setResourceId(int mResourceId) {
		this.mResourceId = mResourceId;
	}

	public ImageView getView() {
		return mView;
	}

	public void setView(ImageView mView) {
		this.mView = mView;
	}
	
	
	
}
