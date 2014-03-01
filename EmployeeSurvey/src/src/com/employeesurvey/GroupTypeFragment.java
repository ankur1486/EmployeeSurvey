package src.com.employeesurvey;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class GroupTypeFragment extends Fragment {

	private Spinner grpTypeSpinner;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.group_type_layout, container);
		initializeUiComponents(view);
		return view;
	}

	private void initializeUiComponents(View view) {

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
}
