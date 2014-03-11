package src.com.employeesurvey;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.model.EmployeeModel;
import src.com.employeesurvey.model.GenderAgeModel;
import src.com.employeesurvey.prefrences.EmployeePrefrence;
import src.com.employeesurvey.util.Constants;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
//import com.google.android.gms.common.api.ResultCallback;

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
		case R.id.action_send_report:
			wrietDataInCsvFormat(
					"Username,Storename,Date,GroupID,Location,Group_Type,Gender,Age",
					true);
			sendMail();

			break;
		case R.id.action_logout:

			showConfirmLogoutAlert();

			break;

		}
		return true;
	}

	private void sendMail() {

		HashMap<String, String> hashMap = EmployeeSurveyDb.getInstance()
				.getUserNameStoreName();
		String username = hashMap.get(Constants.USER_NAME_KEY);
		String storename = hashMap.get(Constants.STORE_NAME_KEY);
		//

		ArrayList<EmployeeModel> employeeModelsList = EmployeeSurveyDb
				.getInstance().getDataModelForList();
		for (int i = 0; i < employeeModelsList.size(); i++) {
			String date = setTimeFormat(employeeModelsList.get(i).getTime());
			String location = employeeModelsList.get(i).getLatitude() + "/"
					+ employeeModelsList.get(i).getLongitude();
			ArrayList<GenderAgeModel> genderAgeModelsList = employeeModelsList
					.get(i).getGenderAgeModel();
			for (int temp = 0; temp < genderAgeModelsList.size(); temp++) {
				wrietDataInCsvFormat(username, false);
				wrietDataInCsvFormat(storename, false);
				wrietDataInCsvFormat(date, false);
				wrietDataInCsvFormat(employeeModelsList.get(i).getRowId() + "",
						false);
				wrietDataInCsvFormat(location, false);
				wrietDataInCsvFormat(genderAgeModelsList.get(temp)
						.getGroupType(), false);
				wrietDataInCsvFormat(genderAgeModelsList.get(temp).getGender(),
						false);
				wrietDataInCsvFormat(genderAgeModelsList.get(temp).getAgeGrp(),
						false);
				wrietDataInCsvFormat("", true);
			}
		}

//		Intent sendIntent = new Intent(Intent.ACTION_SEND);
//		sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//		sendIntent.setType("text/csv");
//		sendIntent.putExtra(Intent.EXTRA_EMAIL,
//				new String[] { "kuldeeprajput025@gmail.com" });
//		// sendemai.putExtra(Intent.EXTRA_CC, new String[] { emailadd });
//		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Report ");
////		sendIntent.putExtra(
////				Intent.EXTRA_STREAM,
////				Uri.parse(Environment.getExternalStorageDirectory()
////						+ "/Csv_EmployeeSurvey.csv"));
//		sendIntent.putExtra(Intent.EXTRA_TEXT, "Enjoy the report");
//		startActivity(Intent.createChooser(sendIntent, "Email:"));

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
		EmployeeSurveyDb.getInstance().deleteUserStoreTable();
		EmployeeSurveyDb.getInstance().deleteLeftListTable();
		EmployeeSurveyDb.getInstance().deleteGenderListTable();
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

	private void wrietDataToFile(String data) {
		// File myFile = new File(Environment.getExternalStorageDirectory()
		// + "/Export_EmployeeSurvey.txt");
		// try {
		// // myFile.
		//
		// myFile.createNewFile();
		//
		// FileOutputStream fOut = new FileOutputStream(myFile, true);
		// OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		// myOutWriter.append('\n');
		// myOutWriter.append(data);
		// myOutWriter.flush();
		// myOutWriter.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void wrietDataInCsvFormat(String data, boolean newLineRequired) {
		File myFile = new File(Environment.getExternalStorageDirectory()
				+ "/Csv_EmployeeSurvey.csv");
		try {
			// myFile.

			myFile.createNewFile();

			FileOutputStream fOut = new FileOutputStream(myFile, true);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(',');
			myOutWriter.append(data);
			if (newLineRequired) {
				myOutWriter.append('\n');
			}
			myOutWriter.flush();
			myOutWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String setTimeFormat(String milliSeconds) {
		// Create a DateFormatter object for displaying date in specified
		// format.

		long msecLong = Long.parseLong(milliSeconds);
		DateFormat formatter = new SimpleDateFormat("dd:mm:yyy EEEE HH:mm:ss ");

		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(msecLong);
		return formatter.format(calendar.getTime());
	}
}
