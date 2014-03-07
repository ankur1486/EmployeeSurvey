package src.com.employeesurvey.adapter;

import java.util.ArrayList;
import java.util.List;

import src.com.employeesurvey.R;
import src.com.employeesurvey.model.GenderAgeModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class GenderListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<GenderAgeModel> mGenderAgeModel;

	public GenderListAdapter(Context context,
			ArrayList<GenderAgeModel> genderAgeModelsList) {
		mContext = context;
		mGenderAgeModel = genderAgeModelsList;
	}

	public void setNumberOfCounts(ArrayList<GenderAgeModel> genderAgeModel) {
		mGenderAgeModel = genderAgeModel;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mGenderAgeModel.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(mContext);
			convertView = layoutInflater.inflate(R.layout.gender_row, null);
		}

		Switch maleFemaleSwitch = (Switch) convertView
				.findViewById(R.id.male_female_switch);
		if (mGenderAgeModel.get(position).getGender().equals("ON")) {
			maleFemaleSwitch.setChecked(false); // female
		} else {
			maleFemaleSwitch.setChecked(true); // male
		}
		maleFemaleSwitch
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						String state = "ON"; // male selected
						if (isChecked) {
							state = "OFF"; // female selected
						}
						// Toast.makeText(mContext, "Switch 1 is " + state,
						// Toast.LENGTH_LONG).show();

						mGenderAgeModel.get(position).setGender(state);
					}
				});

		RadioGroup ageGroupRadioGrp = (RadioGroup) convertView
				.findViewById(R.id.gender_radio_group);

		RadioButton radioButton1 = (RadioButton) convertView
				.findViewById(R.id.radioButton_age_group1);
		RadioButton radioButton2 = (RadioButton) convertView
				.findViewById(R.id.radioButton_age_group2);
		RadioButton radioButton3 = (RadioButton) convertView
				.findViewById(R.id.radioButton_age_group3);
		RadioButton radioButton4 = (RadioButton) convertView
				.findViewById(R.id.radioButton_age_group4);
		RadioButton radioButton5 = (RadioButton) convertView
				.findViewById(R.id.radioButton_age_group5);
		RadioButton radioButton6 = (RadioButton) convertView
				.findViewById(R.id.radioButton_age_group6);
		RadioButton radioButton7 = (RadioButton) convertView
				.findViewById(R.id.radioButton_age_group7);

		String ageGrp = mGenderAgeModel.get(position).getAgeGrp();

		if (radioButton1.getText().toString().equalsIgnoreCase(ageGrp)) {
			radioButton1.setChecked(true);
		} else if (radioButton2.getText().toString().equalsIgnoreCase(ageGrp)) {
			radioButton2.setChecked(true);
		} else if (radioButton3.getText().toString().equalsIgnoreCase(ageGrp)) {
			radioButton3.setChecked(true);
		} else if (radioButton4.getText().toString().equalsIgnoreCase(ageGrp)) {
			radioButton4.setChecked(true);
		} else if (radioButton5.getText().toString().equalsIgnoreCase(ageGrp)) {
			radioButton5.setChecked(true);
		} else if (radioButton6.getText().toString().equalsIgnoreCase(ageGrp)) {
			radioButton6.setChecked(true);
		} else if (radioButton7.getText().toString().equalsIgnoreCase(ageGrp)) {
			radioButton7.setChecked(true);
		}

		// Toast.makeText(mContext, "value selected :" + ageGrp,
		// Toast.LENGTH_SHORT).show();

		ageGroupRadioGrp
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						String value = ((RadioButton) group.findViewById(group
								.getCheckedRadioButtonId())).getText()
								.toString();
						// Toast.makeText(mContext, "checkedId :" + value,
						// Toast.LENGTH_SHORT).show();

						mGenderAgeModel.get(position).setAgeGrp(value);
					}
				});

		return convertView;
	}

	public List<GenderAgeModel> getUpdatedGenderAgeGrp() {
		if (mGenderAgeModel != null && mGenderAgeModel.size() > 0) {
			return mGenderAgeModel;
		}
		return null;
	}

}
