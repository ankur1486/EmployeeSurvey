package src.com.employeesurvey;

import src.com.employeesurvey.prefrences.EmployeePrefrence;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

public class SplashActivity extends Activity {

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
		// TODO Auto-generated method stub
		super.onResume();
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

}
