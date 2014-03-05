package src.com.employeesurvey;

import src.com.employeesurvey.prefrences.EmployeePrefrence;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class DashboardActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Window win = getWindow();

		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.dashboard_activity_layout);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.action_logout:

			showConfirmLogoutAlert();
			Toast.makeText(getBaseContext(), "You selected Logout",
					Toast.LENGTH_SHORT).show();

			break;

		}
		return true;
	}

	private void showConfirmLogoutAlert() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_name)
				.setMessage(
						R.string.are_you_sure_you_want_to_logout_from_application_)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								deletePrefrences();
								deleteDatabase();
								finish();
							}
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}).setIcon(R.drawable.ic_launcher).show();
	}

	/**
	 * method to delete Database for user
	 */
	private void deleteDatabase() {
		// TODO Auto-generated method stub

	}

	/**
	 * method to delete Prefrences values
	 */
	private void deletePrefrences() {
		EmployeePrefrence.getInstance().setStringValue(
				EmployeePrefrence.SET_STORENAME, "");
		EmployeePrefrence.getInstance().setStringValue(
				EmployeePrefrence.SET_USERNAME, "");

	}

}
