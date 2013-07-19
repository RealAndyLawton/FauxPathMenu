package com.realandylawton.pathmenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	PathMenu pathMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pathMenu = (PathMenu)findViewById(R.id.path_menu);
		
		int[] icons = { R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3 };
		List<PathMenuItem> menuItems = new ArrayList<PathMenuItem>();
		for(int icon : icons) {
			menuItems.add(new PathMenuItem(icon));
		}
		
		pathMenu.addMenuItems(menuItems);
		
	}

}
