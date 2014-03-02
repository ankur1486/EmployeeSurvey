package src.com.employeesurvey;

import src.com.employeesurvey.database.EmployeeSurveyDb;
import android.app.Application;

public class EmployeeSurveryApplicaiton extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		EmployeeSurveyDb.init(this);
	}
}
