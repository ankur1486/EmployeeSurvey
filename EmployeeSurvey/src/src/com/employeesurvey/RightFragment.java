package src.com.employeesurvey;

import java.util.ArrayList;
import java.util.List;

import src.com.employeesurvey.adapter.GenderListAdapter;
import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.model.GenderAgeModel;
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
	private String mGroupType;
	private ArrayList<GenderAgeModel> mGenderAgeArrayList = new ArrayList<GenderAgeModel>();
	private int mRowID;
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
		genderListAdapter = new GenderListAdapter(getActivity(),
				mGenderAgeArrayList);
		mGenderList.setAdapter(genderListAdapter);

		mSaveButton = (Button) fragment.findViewById(R.id.save_button);
	}

	public void updateList(ArrayList<GenderAgeModel> arrayList, int rowId) {
		mRowID = rowId;
		if (genderListAdapter != null) {
			genderListAdapter.setNumberOfCounts(arrayList);
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

//		grpTypeSpinner.setSelection(3);
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

		private int mGroupInt;

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {

			Toast.makeText(
					parent.getContext(),
					"On Item Select : \n"
							+ parent.getItemAtPosition(pos).toString() + "view pos " +parent.getPositionForView(view),
					Toast.LENGTH_LONG).show();
			mGroupInt = parent.getPositionForView(view);
			mGroupType = parent.getItemAtPosition(pos).toString();

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save_button:

			if (genderListAdapter != null) {
				List<GenderAgeModel> genderAgeModelsList = genderListAdapter
						.getUpdatedGenderAgeGrp();
				if (genderAgeModelsList != null
						&& genderAgeModelsList.size() > 0) {
					 for (int i = 0; i < genderAgeModelsList.size(); i++) {
					 EmployeeSurveyDb.getInstance().insertGenderRow(mRowID, genderAgeModelsList.get(i).getGender(), genderAgeModelsList.get(i).getAgeGrp(),mGroupType);
					 }
				}
			}

			

			break;

		default:
			break;
		}
	}
}
