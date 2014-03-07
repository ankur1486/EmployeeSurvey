package src.com.employeesurvey;

import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.prefrences.EmployeePrefrence;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 
 * Dashboard class which will hold two fragments left and right .
 * 
 * Left fragment - person count , location etc Right fragmnet - Group typ , age
 * and gender
 * 
 */
public class DashboardActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Window win = getWindow();

		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		EmployeeSurveyDb.getInstance().getRowIdToSet();
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
		case R.id.action_send_mail:

			sendMail();
			Toast.makeText(getBaseContext(), "You selected Logout",
					Toast.LENGTH_SHORT).show();

			break;
		case R.id.action_logout:

			showConfirmLogoutAlert();
			Toast.makeText(getBaseContext(), "You selected Logout",
					Toast.LENGTH_SHORT).show();

			break;

		}
		return true;
	}

	private void sendMail() {
//		Intent sendemai = new Intent(Intent.ACTION_SEND);
//		sendemai.putExtra(Intent.EXTRA_EMAIL,
//				new String[] { "ankur1486@gmail.com" });
//		// sendemai.putExtra(Intent.EXTRA_CC, new String[] { emailadd });
//		sendemai.putExtra(
//				Intent.EXTRA_SUBJECT,
//				"Employee Data for user "
//						+ EmployeePrefrence.getInstance().getStringValue(
//								EmployeePrefrence.SET_USERNAME, ""));
//		sendemai.putExtra(Intent.EXTRA_TEXT, "Testing ");
//		// need this to prompts email client only
//		sendemai.setType("message/rfc822");
//		startActivity(Intent
//				.createChooser(sendemai, "Select email application"));
		
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
