package src.com.employeesurvey;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.model.EmployeeModel;
import src.com.employeesurvey.prefrences.EmployeePrefrence;
import src.com.employeesurvey.util.Constants;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
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

		HashMap<String, String> hashMap = EmployeeSurveyDb.getInstance()
				.getUserNameStoreName();
		String username = hashMap.get(Constants.USER_NAME_KEY);
		String storename = hashMap.get(Constants.STORE_NAME_KEY);
		wrietDataToFile("USERNAME :" + username + " STROEANME :" + storename);
		System.out
				.println("USERNAME :" + username + " STROEANME :" + storename);

		int personCount = EmployeeSurveyDb.getInstance().getPersonCount();
		System.out.println("PERSON COUNT :" + personCount);
		wrietDataToFile("PERSON COUNT :" + personCount);
		
		int maleCount = EmployeeSurveyDb.getInstance().getNumberOfMalesFemales(
				"OFF");
		System.out.println("MALE COUNT :" + maleCount);
		wrietDataToFile("MALE COUNT :" + maleCount);

		int femaleCount = EmployeeSurveyDb.getInstance()
				.getNumberOfMalesFemales("ON");
		System.out.println("FEMALE CONUT :" + femaleCount);
		wrietDataToFile("FEMALE CONUT :" + femaleCount);

		/**
		 * male
		 */

		String[] ageGrpString = getResources().getStringArray(
				R.array.malefemaleage_array);
		for (int i = 0; i < ageGrpString.length; i++) {
			// male
			int male_0_10_count = EmployeeSurveyDb.getInstance()
					.getMaleFemaleByAgeGroup(ageGrpString[i].toString(), "OFF");
			System.out.println("AGE GROUP :" + ageGrpString[i].toString()
					+ " MALE : " + male_0_10_count);
			wrietDataToFile("AGE GROUP :" + ageGrpString[i].toString()
					+ " MALE : " + male_0_10_count);

			// female
			int female_0_10_count = EmployeeSurveyDb.getInstance()
					.getMaleFemaleByAgeGroup(ageGrpString[i].toString(), "ON");
			System.out.println("AGE GROUP :" + ageGrpString[i].toString()
					+ " FEMALE : " + female_0_10_count);
			wrietDataToFile("AGE GROUP :" + ageGrpString[i].toString()
					+ " FEMALE : " + male_0_10_count);

		}
		String[] groupType = getResources().getStringArray(R.array.group_type);

		for (int temp = 0; temp < groupType.length; temp++) {
			for (int i = 0; i < ageGrpString.length; i++) {
				// male
				int male_0_10_count = EmployeeSurveyDb.getInstance()
						.getGroupTypeDetail(groupType[temp].toString(),
								ageGrpString[i].toString(), "OFF");
				System.out.println("GROUP TYPE :" + groupType[temp].toString()
						+ "AGE GROUP :" + ageGrpString[i].toString()
						+ " MALE : " + male_0_10_count);
				wrietDataToFile("GROUP TYPE :" + groupType[temp].toString()
						+ "AGE GROUP :" + ageGrpString[i].toString()
						+ " MALE : " + male_0_10_count);

				// female
				int female_0_10_count = EmployeeSurveyDb.getInstance()
						.getGroupTypeDetail(groupType[temp].toString(),
								ageGrpString[i].toString(), "ON");
				System.out.println("GROUP TYPE :" + groupType[temp].toString()
						+ "AGE GROUP :" + ageGrpString[i].toString()
						+ " FEMALE : " + female_0_10_count);
				wrietDataToFile("GROUP TYPE :" + groupType[temp].toString()
						+ "AGE GROUP :" + ageGrpString[i].toString()
						+ " FEMALE : " + female_0_10_count);

			}
		}

		

		ArrayList<EmployeeModel> employeeModelsArray = EmployeeSurveyDb
				.getInstance().getTimeLocationPersonCount();
		for (int i = 0; i < employeeModelsArray.size(); i++) {
			EmployeeModel employeeModel = employeeModelsArray.get(i);
			System.out.println("TIme :" + employeeModel.getTime()
					+ " Latitude :" + employeeModel.getLatitude()
					+ " Longitude :" + employeeModel.getLongitude()
					+ " Person Count :" + employeeModel.getPersonCount()
					+ " \n");
			wrietDataToFile("TIme :" + employeeModel.getTime() + " Latitude :"
					+ employeeModel.getLatitude() + " Longitude :"
					+ employeeModel.getLongitude() + " Person Count :"
					+ employeeModel.getPersonCount() + " \n");

		}

		// Intent sendemai = new Intent(Intent.ACTION_SEND);
		// sendemai.putExtra(Intent.EXTRA_EMAIL,
		// new String[] { "ankur1486@gmail.com" });
		// // sendemai.putExtra(Intent.EXTRA_CC, new String[] { emailadd });
		// sendemai.putExtra(
		// Intent.EXTRA_SUBJECT,
		// "Employee Data for user "
		// + EmployeePrefrence.getInstance().getStringValue(
		// EmployeePrefrence.SET_USERNAME, ""));
		// sendemai.putExtra(Intent.EXTRA_TEXT, "Testing ");
		// // need this to prompts email client only
		// sendemai.setType("message/rfc822");
		// startActivity(Intent
		// .createChooser(sendemai, "Select email application"));
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

	private void wrietDataToFile(String data) {
		File myFile = new File(Environment.getExternalStorageDirectory()
				+ "/Export_EmployeeSurvey.txt");
		try {
//			myFile.

			myFile.createNewFile();

			FileOutputStream fOut = new FileOutputStream(myFile, true);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append('\n');
			myOutWriter.append(data);
			myOutWriter.flush();
			myOutWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
