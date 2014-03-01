package src.com.employeesurvey;

import java.io.IOException;

import src.com.employeesurvey.util.GPSTracker;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class LeftFragment extends Fragment {

	private EditText mgenderSizeInput;
	private ListView mLeftPanelListView;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragment = inflater.inflate(R.layout.leftpanel_layout, container);
		try {
			init(fragment);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fragment;
	}

	private void init(View fragment) {
		mLeftPanelListView = (ListView)fragment.findViewById(R.id.left_panel_listView);
		LeftPanelListAdapter genderListAdapter = new LeftPanelListAdapter(getActivity(),LeftFragment.this);
		mLeftPanelListView.setAdapter(genderListAdapter);
	}

	public String getLocation() throws NumberFormatException, IOException{
		String postalCode = "";
		// check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(getActivity());

        if (gpsTracker.canGetLocation())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
           
            String stringLongitude = String.valueOf(gpsTracker.longitude);
            postalCode = gpsTracker.getPostalCode(getActivity());
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
//            gpsTracker.showSettingsAlert();
        }
        return postalCode;
	}
}
