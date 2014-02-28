package com.example.sampleproject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class DashboardActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Window win = getWindow();

		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// No Titlebar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// LinearLayout fragContainer = (LinearLayout)
		// findViewById(R.id.main_id);
		//
		// LinearLayout ll = new LinearLayout(this);
		// ll.setOrientation(LinearLayout.HORIZONTAL);
		//
		// ll.setId(12345);

//		getSupportFragmentManager().beginTransaction()
//				.add(new LeftFragment(), "leftFragmentTag").commit();
//		getSupportFragmentManager().beginTransaction()
//				.add(new RightFragment(), "rightFragmentTag").commit();

		// fragContainer.addView(ll);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
