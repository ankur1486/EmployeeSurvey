package src.com.employeesurvey;

import java.util.ArrayList;
import java.util.List;

import src.com.employeesurvey.adapter.GenderListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class RightFragment extends Fragment implements OnClickListener {

	private ListView mGenderList;
	private GenderListAdapter genderListAdapter;
	private Spinner grpTypeSpinner;
	private Button mSaveButton;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.rightpanel_layout, container);
		initGenderAgeGrpComponents(view);
		initializeSpinnerUiComponents(view);
		return view;
	}

	private void initGenderAgeGrpComponents(View fragment) {
		mGenderList = (ListView) fragment.findViewById(R.id.listView_gender);
		genderListAdapter = new GenderListAdapter(getActivity());
		mGenderList.setAdapter(genderListAdapter);

		mSaveButton = (Button) fragment.findViewById(R.id.save_button);
	}

	public void updateList(int number) {
		if (genderListAdapter != null) {
			genderListAdapter.setNumberOfCounts(number);
		}
	}

	private void initializeSpinnerUiComponents(View view) {

		grpTypeSpinner = (Spinner) view.findViewById(R.id.spinner_group_type);
		List<String> list = new ArrayList<String>();
		list.add("Friends");
		list.add("Couple");
		list.add("Family");
		list.add("Family with child");
		list.add("Colleagues");
		list.add("others");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		grpTypeSpinner.setAdapter(dataAdapter);

		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();
	}

	// Add spinner data

	public void addListenerOnSpinnerItemSelection() {

		grpTypeSpinner
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	protected class CustomOnItemSelectedListener implements
			OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {

			Toast.makeText(
					parent.getContext(),
					"On Item Select : \n"
							+ parent.getItemAtPosition(pos).toString(),
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save_button:

			break;

		default:
			break;
		}
	}
}
