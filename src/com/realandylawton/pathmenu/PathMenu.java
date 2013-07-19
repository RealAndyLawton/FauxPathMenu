package com.realandylawton.pathmenu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class PathMenu extends FrameLayout {
	
	public final static float DEGREES = 90.0f;
	public final static int DISTANCE = 200;
	
	List<PathMenuItem> mMenuItems = new ArrayList<PathMenuItem>();
	
	private ImageView mAnchorView;

	public PathMenu(Context context) {
		super(context);
		setup();
	}

	public PathMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray attrTypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PathMenu, 0, 0);
		
		try {
			setAnchorView(attrTypedArray.getResourceId(R.styleable.PathMenu_anchorView, 0));
		}
		finally {
			attrTypedArray.recycle();
		}
		
		setup();

	}

	private void setup() {
		
		// Position the anchor view
		ImageView anchorView = getAnchorView();
		if(anchorView != null) {
			anchorView.setLayoutParams(new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT, 
					FrameLayout.LayoutParams.WRAP_CONTENT));
			addView(anchorView);
			
			anchorView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					expandMenuItems();
				}
				
			});
			
		}
		
	}
	
	public void addMenuItems(List<PathMenuItem> menuItems) {
		
		if(menuItems != null) {
			if(menuItems.size() == 0) {
				// TODO Handle empty menu more gracefully
				return;
			}
		}
		
		mMenuItems.addAll(menuItems);		
		float[] degrees = getMenuItemDegrees();

		int i = 0;
		for(PathMenuItem menuItem : mMenuItems) {
			
			// Create the image view with the appropriate image resource
			ImageView menuItemView = new ImageView(getContext());
			menuItemView.setLayoutParams(new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT));
			menuItemView.setVisibility(View.INVISIBLE);
			menuItemView.setImageResource(menuItem.getResourceId());
			
			// Find the (x,y) coords
			float itemDegree = degrees[i];
			// TODO Handle DP conversion
			int x = (int) (DISTANCE * Math.cos(Math.toRadians(itemDegree))); // x = dcos(theta);
			int y = (int) (DISTANCE * Math.sin(Math.toRadians(itemDegree))); // y = dsin(theta);
			
			int translateDuration = 5000;
			
			TranslateAnimation translate = new TranslateAnimation(0, x, 0, y);
			translate.setStartOffset(0);
			translate.setDuration(translateDuration);
			translate.setInterpolator(getContext(), android.R.anim.anticipate_overshoot_interpolator);
	
			// TODO Use PropertyAnimation instead of old school view animation,
			AlphaAnimation alpha = new AlphaAnimation(0, 1.0f);
			alpha.setDuration(translateDuration / 2);
			
			AnimationSet animation = new AnimationSet(true);
			animation.setInterpolator(getContext(), android.R.anim.linear_interpolator);
			animation.setFillAfter(true);
			animation.setFillBefore(false);
			animation.setFillEnabled(true);

			animation.addAnimation(translate);
			animation.addAnimation(alpha);
			
			menuItem.setView(menuItemView);
			menuItem.setInAnimation(animation);
			
			this.addView(menuItemView);
			
			i++;
			
		}
		
	}
	
	public void expandMenuItems() {
		
		for(PathMenuItem menuItem : mMenuItems) {
			menuItem.getView().startAnimation(menuItem.getInAnimation());
		}
		
	}
	
	private float[] getMenuItemDegrees() {
		
		// TODO Make this configurable and lazily create
		float[] degrees = new float[3];
		for(int i = 0; i < 3; i++) {
			degrees[i] = 22.5f * (i + 1);
		}
		
		return degrees;
		
	}
	
	public ImageView getAnchorView() {
		return mAnchorView;
	}

	public void setAnchorView(ImageView anchorView) {
		this.mAnchorView = anchorView;
	}
	
	public void setAnchorView(int resId) {
		// TODO Throw some Exception on invalid resource ID
		if(resId > 0) {
			mAnchorView = new ImageView(getContext());
			mAnchorView.setImageResource(resId);
		}
	}
	

}
