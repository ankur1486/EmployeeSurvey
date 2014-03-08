package src.com.employeesurvey;

import src.com.employeesurvey.prefrences.EmployeePrefrence;
import src.com.employeesurvey.util.LocationUtils;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.google.android.gms.location.LocationListener;

public class SplashActivity extends Activity implements LocationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash_layout);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				String username = EmployeePrefrence.getInstance()
						.getStringValue(EmployeePrefrence.SET_USERNAME, "");
				String storename = EmployeePrefrence.getInstance()
						.getStringValue(EmployeePrefrence.SET_STORENAME, "");
				if (!TextUtils.isEmpty(username)
						&& !TextUtils.isEmpty(storename)) {
					launchDashboardActivity();
				} else {
					launchLoginActivity();
				}

			}
		}, 1500);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		LocationUtils locationUtils = new LocationUtils(this, this);
		locationUtils.connectLocationService();
	}

	private void launchDashboardActivity() {
		Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
		startActivity(intent);
		finish();
	}

	private void launchLoginActivity() {
		Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			System.out.println("Spalsh Latitude :" + location.getLatitude());
			System.out.println("Splash Longitude :" + location.getLongitude());

			EmployeePrefrence.getInstance()
					.setStringValue(EmployeePrefrence.SET_LATITUDE,
							"" + location.getLatitude());
			EmployeePrefrence.getInstance().setStringValue(
					EmployeePrefrence.SET_LONGITUDE,
					"" + location.getLongitude());
		}
	}

}
