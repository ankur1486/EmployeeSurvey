package src.com.employeesurvey.prefrences;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class EmployeePrefrence {
	// Constant key for Preference
	private static EmployeePrefrence instance;

	private static final String KEY_EMPLOYEE_SURVEY_PREFS = "employee_survey_preference";
	public static final String SET_USERNAME = "USERNAME";
	public static final String SET_STORENAME = "STORENAME";

	public static final String SET_LATITUDE = "latitude";
	public static final String SET_LONGITUDE = "longitude";

	private SharedPreferences sharedPrefs;
	private Editor prefsEditor;

	private EmployeePrefrence(Context context) {
		sharedPrefs = context.getSharedPreferences(KEY_EMPLOYEE_SURVEY_PREFS,
				Activity.MODE_PRIVATE);
		prefsEditor = sharedPrefs.edit();
	}

	public static void init(Application application) {
		if (instance == null) {
			instance = new EmployeePrefrence(
					application.getApplicationContext());
		}
	}

	public static EmployeePrefrence getInstance() {
		if (instance == null) {
			throw new RuntimeException(
					"Must run init(Application application) before an instance can be obtained");
		}
		return instance;
	}

	public String getStringValue(String key, String defaultvalue) {
		return sharedPrefs.getString(key, defaultvalue);
	}

	public void setStringValue(String key, String value) {
		prefsEditor.putString(key, value);
		prefsEditor.commit();
	}

	public int getIntValue(String key, int defaultvalue) {
		return sharedPrefs.getInt(key, defaultvalue);
	}

	public void setIntValue(String key, int value) {
		prefsEditor.putInt(key, value);
		prefsEditor.commit();
	}

	public boolean getBooleanValue(String key, Boolean defaultvalue) {
		return sharedPrefs.getBoolean(key, defaultvalue);
	}

	public void setBooleanValue(String key, boolean value) {
		prefsEditor.putBoolean(key, value);
		prefsEditor.commit();
	}
}
