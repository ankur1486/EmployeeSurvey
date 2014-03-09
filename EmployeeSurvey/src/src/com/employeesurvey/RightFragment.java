package src.com.employeesurvey;

import java.util.ArrayList;
import java.util.List;

import src.com.employeesurvey.adapter.GenderListAdapter;
import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.model.EmployeeModel;
import src.com.employeesurvey.model.GenderAgeModel;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

public class RightFragment extends Fragment implements OnClickListener {

	private ListView mGenderList;
	private GenderListAdapter genderListAdapter;
	private Spinner grpTypeSpinner;
	private Button mSaveButton;
	private String mGroupType;
	private ArrayList<GenderAgeModel> mGenderAgeArrayList = new ArrayList<GenderAgeModel>();
	private int mRowID;
	private int grpTypePosition;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.rightpanel_layout, container);
		grpTypeSpinner = (Spinner) view.findViewById(R.id.spinner_group_type);
		initGenderAgeGrpComponents(view);
		initializeSpinnerUiComponents(view);
		return view;
	}

	private void initGenderAgeGrpComponents(View fragment) {
		mGenderList = (ListView) fragment.findViewById(R.id.listView_gender);
		genderListAdapter = new GenderListAdapter(getActivity(),
				mGenderAgeArrayList);
		mGenderList.setAdapter(genderListAdapter);
		ArrayList<EmployeeModel> employeeModel = EmployeeSurveyDb.getInstance()
				.getDataModelForList();
		if (employeeModel.size() > 0) {
			updateList(employeeModel.get(0).getGenderAgeModel(), employeeModel
					.get(0).getRowId());
		}
		mSaveButton = (Button) fragment.findViewById(R.id.save_button);
		mSaveButton.setOnClickListener(this);
	}

	public void updateList(ArrayList<GenderAgeModel> arrayList, int rowId) {
		grpTypePosition = 0;
		if (arrayList != null && arrayList.size() > 0) {
			String grpType = arrayList.get(0).getGroupType();
			// Toast.makeText(getActivity(), " *** " + grpType,
			// Toast.LENGTH_SHORT)
			// .show();
			if (!TextUtils.isEmpty(grpType)) {

				if (grpType.equalsIgnoreCase(getActivity().getString(
						R.string.friends))) {
					grpTypeSpinner.setSelection(0);
					grpTypePosition = 0;
				} else if (grpType.equalsIgnoreCase(getActivity().getString(
						R.string.couple))) {
					grpTypeSpinner.setSelection(1);
					grpTypePosition = 1;
				} else if (grpType.equalsIgnoreCase(getActivity().getString(
						R.string.family))) {
					grpTypeSpinner.setSelection(2);
					grpTypePosition = 2;
				} else if (grpType.equalsIgnoreCase(getActivity().getString(
						R.string.family_with_child))) {
					grpTypeSpinner.setSelection(3);
					grpTypePosition = 3;
				} else if (grpType.equalsIgnoreCase(getActivity().getString(
						R.string.colleagues))) {
					grpTypeSpinner.setSelection(4);
					grpTypePosition = 4;
				} else if (grpType.equalsIgnoreCase(getActivity().getString(
						R.string.others))) {
					grpTypeSpinner.setSelection(5);
					grpTypePosition = 5;
				}
			}
		}
		mRowID = rowId;
		if (genderListAdapter != null) {
			genderListAdapter.setNumberOfCounts(arrayList);
		}

	}

	private void initializeSpinnerUiComponents(View view) {
		List<String> list = new ArrayList<String>();
		list.add(getActivity().getString(R.string.friends));
		list.add(getActivity().getString(R.string.couple));
		list.add(getActivity().getString(R.string.family));
		list.add(getActivity().getString(R.string.family_with_child));
		list.add(getActivity().getString(R.string.colleagues));
		list.add(getActivity().getString(R.string.others));

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		grpTypeSpinner.setAdapter(dataAdapter);

		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		grpTypeSpinner.setSelection(grpTypePosition);
	}

	// Add spinner data

	public void addListenerOnSpinnerItemSelection() {

		grpTypeSpinner
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	protected class CustomOnItemSelectedListener implements
			OnItemSelectedListener {

		// private int mGroupInt;

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {

			// Toast.makeText(
			// parent.getContext(),
			// "On Item Select : \n"
			// + parent.getItemAtPosition(pos).toString()
			// + "view pos " + parent.getPositionForView(view),
			// Toast.LENGTH_LONG).show();
			// mGroupInt = parent.getPositionForView(view);
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
					EmployeeSurveyDb.getInstance().deleteGenderDetailByRowId(
							"" + mRowID);
					for (int i = 0; i < genderAgeModelsList.size(); i++) {
						EmployeeSurveyDb.getInstance().insertGenderRow(mRowID,
								genderAgeModelsList.get(i).getGender(),
								genderAgeModelsList.get(i).getAgeGrp(),
								mGroupType);
					}

					int ageGroupSize = 0;
					for (int i = 0; i < genderAgeModelsList.size(); i++) {
						if (genderAgeModelsList.get(i).getAgeGrp().equals("")) {
							break;
						} else {
							ageGroupSize++;
						}
					}
					if (ageGroupSize == genderAgeModelsList.size()) {
						EmployeeSurveyDb.getInstance().updateFormCompleted(
								"" + mRowID);
						updateLeftFragment();
					}
				}
			}

			break;

		default:
			break;
		}
	}

	private void updateLeftFragment() {
		LeftFragment rightFragment = (LeftFragment) getFragmentManager()
				.findFragmentById(R.id.left_fragment);
		rightFragment.updateLeftList();

	}
}
