package src.com.employeesurvey;

import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.prefrences.EmployeePrefrence;
import android.app.Application;

public class EmployeeSurveryApplicaiton extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		
		EmployeePrefrence.init(this);
		EmployeeSurveyDb.init(this);
		
	}
}
