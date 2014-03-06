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
import android.widget.Toast;

public class GenderListAdapter extends BaseAdapter {

	private Context mContext;
	private int mCount = 0;

	private List<GenderAgeModel> mGenderAgeModelList = new ArrayList<GenderAgeModel>();

	public GenderListAdapter(Context context,
			List<GenderAgeModel> genderAgeModelsList) {
		mContext = context;
		mGenderAgeModelList = genderAgeModelsList;
	}

	public void setNumberOfCounts(int count) {
		mCount = count;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mCount;
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

		// GenderAgeModel genderAgeModel = mGenderAgeModelList.get(position);

		Switch maleFemaleSwitch = (Switch) convertView
				.findViewById(R.id.male_female_switch);
		maleFemaleSwitch
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						String state = "OFF";
						if (isChecked) {
							state = "ON";
						}
						Toast.makeText(mContext, "Switch 1 is " + state,
								Toast.LENGTH_LONG).show();

						mGenderAgeModelList.get(position).setGender(state);
					}
				});

		RadioGroup ageGroupRadioGrp = (RadioGroup) convertView
				.findViewById(R.id.gender_radio_group);
		ageGroupRadioGrp
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						String value = ((RadioButton) group.findViewById(group
								.getCheckedRadioButtonId())).getText()
								.toString();
						Toast.makeText(mContext, "checkedId :" + value,
								Toast.LENGTH_SHORT).show();

						mGenderAgeModelList.get(position).setAgeGrp(value);
					}
				});

		return convertView;
	}

	public List<GenderAgeModel> getUpdatedGenderAgeGrp() {
		if (mGenderAgeModelList != null && mGenderAgeModelList.size() > 0) {
			return mGenderAgeModelList;
		}
		return null;
	}

}
