package src.com.employeesurvey;

import src.com.employeesurvey.adapter.LeftPanelListAdapter;
import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.prefrences.EmployeePrefrence;
import src.com.employeesurvey.util.LocationUtils;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.location.LocationListener;

/**
 * Left fragment class which will hold the layout 
 * 
 *
 */
public class LeftFragment extends Fragment implements LocationListener {

	private ListView mLeftPanelListView;
	private LocationUtils locationUtils;
	private Location mLocation;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		locationUtils = new LocationUtils(getActivity(), this);
		locationUtils.connectLocationService();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragment = inflater.inflate(R.layout.leftpanel_layout, container);
		try {
			init(fragment);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fragment;
	}

	private void init(View fragment) {
		
		int listCount = EmployeeSurveyDb.getInstance().getLeftListCount();
		mLeftPanelListView = (ListView) fragment
				.findViewById(R.id.left_panel_listView);
		LeftPanelListAdapter genderListAdapter = new LeftPanelListAdapter(
				getActivity(), listCount , LeftFragment.this);
		mLeftPanelListView.setAdapter(genderListAdapter);
	}

	@Override
	public void onLocationChanged(Location location) {
		mLocation = location;
		if (location != null) {
			System.out.println("Latitude :" + location.getLatitude());
			System.out.println("Longitude :" + location.getLongitude());

			EmployeePrefrence.getInstance()
					.setStringValue(EmployeePrefrence.SET_LATITUDE,
							"" + location.getLatitude());
			EmployeePrefrence.getInstance().setStringValue(
					EmployeePrefrence.SET_LONGITUDE,
					"" + location.getLongitude());
		}
	}

}
