package src.com.employeesurvey.adapter;

import java.util.ArrayList;
import java.util.List;

import src.com.employeesurvey.R;
import src.com.employeesurvey.model.GenderAgeModel;
import src.com.employeesurvey.util.ScalingLayout;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
		final GenderListViewHolder genderListViewHolder;
		if (convertView == null) {

			LayoutInflater layoutInflater = LayoutInflater.from(mContext);
			convertView = layoutInflater.inflate(R.layout.gender_row, null);
			genderListViewHolder = new GenderListViewHolder();
			genderListViewHolder.mGenderSwitch = (Switch) convertView
					.findViewById(R.id.male_female_switch);
			genderListViewHolder.mAgeGrpButton = (Button) convertView
					.findViewById(R.id.ageGrp_button);
			// genderListViewHolder.mAgeGroupRadioGroup = (RadioGroup)
			// convertView
			// .findViewById(R.id.gender_radio_group);
			//
			// genderListViewHolder.radioButton1 = (RadioButton) convertView
			// .findViewById(R.id.radioButton_age_group1);
			// genderListViewHolder.radioButton1
			// .setOnClickListener(new onRadioButtonClick(position));
			// genderListViewHolder.radioButton2 = (RadioButton) convertView
			// .findViewById(R.id.radioButton_age_group2);
			// genderListViewHolder.radioButton2
			// .setOnClickListener(new onRadioButtonClick(position));
			// genderListViewHolder.radioButton3 = (RadioButton) convertView
			// .findViewById(R.id.radioButton_age_group3);
			// genderListViewHolder.radioButton3
			// .setOnClickListener(new onRadioButtonClick(position));
			// genderListViewHolder.radioButton4 = (RadioButton) convertView
			// .findViewById(R.id.radioButton_age_group4);
			// genderListViewHolder.radioButton4
			// .setOnClickListener(new onRadioButtonClick(position));
			// genderListViewHolder.radioButton5 = (RadioButton) convertView
			// .findViewById(R.id.radioButton_age_group5);
			// genderListViewHolder.radioButton5
			// .setOnClickListener(new onRadioButtonClick(position));
			// genderListViewHolder.radioButton6 = (RadioButton) convertView
			// .findViewById(R.id.radioButton_age_group6);
			// genderListViewHolder.radioButton6
			// .setOnClickListener(new onRadioButtonClick(position));
			// genderListViewHolder.radioButton7 = (RadioButton) convertView
			// .findViewById(R.id.radioButton_age_group7);
			// genderListViewHolder.radioButton7
			// .setOnClickListener(new onRadioButtonClick(position));
			convertView.setTag(genderListViewHolder);
		} else {
			genderListViewHolder = (GenderListViewHolder) convertView.getTag();
		}

		// genderListViewHolder.radioButton1
		// .setOnClickListener(new onRadioButtonClick(position));
		//
		// genderListViewHolder.radioButton2
		// .setOnClickListener(new onRadioButtonClick(position));
		//
		// genderListViewHolder.radioButton3
		// .setOnClickListener(new onRadioButtonClick(position));
		//
		// genderListViewHolder.radioButton4
		// .setOnClickListener(new onRadioButtonClick(position));
		//
		// genderListViewHolder.radioButton5
		// .setOnClickListener(new onRadioButtonClick(position));
		//
		// genderListViewHolder.radioButton6
		// .setOnClickListener(new onRadioButtonClick(position));
		//
		// genderListViewHolder.radioButton7
		// .setOnClickListener(new onRadioButtonClick(position));

		if (mGenderAgeModel.get(position).getGender().equals("MALE")) {
			genderListViewHolder.mGenderSwitch.setChecked(true); // female
		} else {
			genderListViewHolder.mGenderSwitch.setChecked(false); // male
		}

		String ageGrp = mGenderAgeModel.get(position).getAgeGrp();
		if (!TextUtils.isEmpty(ageGrp)) {
			genderListViewHolder.mAgeGrpButton.setText(ageGrp);
		} else {
			genderListViewHolder.mAgeGrpButton.setText("Age Group");
		}

		genderListViewHolder.mGenderSwitch
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						genderListViewHolder.mGenderSwitch = (Switch) v
								.findViewById(R.id.male_female_switch);
						String state = "MALE"; // male selected
						if (!genderListViewHolder.mGenderSwitch.isChecked()) {
							state = "FEMALE"; // female selected
						}
						mGenderAgeModel.get(position).setGender(state);
					}
				});

		genderListViewHolder.mAgeGrpButton
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						final Dialog dialog = new Dialog(mContext);
						dialog.setTitle("Set Age Group ");
						dialog.setContentView(R.layout.number_picker_dialog_layout);

						dialog.show();

						ScalingLayout mScalingLayoutView = (ScalingLayout) dialog
								.findViewById(R.id.emma_category_button_layout);

						String[] ageGrpLength = mContext.getResources()
								.getStringArray(R.array.malefemaleage_array);
						for (int i = 0; i < ageGrpLength.length; i++) {
							final Button button = new Button(mContext);
							button.setText(ageGrpLength[i]);
							button.setId(i);
							button.setGravity(Gravity.CENTER);

							mScalingLayoutView.addView(button);

							button.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									mGenderAgeModel.get(position).setAgeGrp(
											button.getText().toString());
									genderListViewHolder.mAgeGrpButton.setText(button.getText().toString());
									dialog.dismiss();
								}
							});
						}
					}
				});

		// if (genderListViewHolder.radioButton1.getText().toString()
		// .equalsIgnoreCase(ageGrp)) {
		// genderListViewHolder.radioButton1.setChecked(true);
		//
		// } else if (genderListViewHolder.radioButton2.getText().toString()
		// .equalsIgnoreCase(ageGrp)) {
		// genderListViewHolder.radioButton2.setChecked(true);
		// } else if (genderListViewHolder.radioButton3.getText().toString()
		// .equalsIgnoreCase(ageGrp)) {
		// genderListViewHolder.radioButton3.setChecked(true);
		// } else if (genderListViewHolder.radioButton4.getText().toString()
		// .equalsIgnoreCase(ageGrp)) {
		// genderListViewHolder.radioButton4.setChecked(true);
		// } else if (genderListViewHolder.radioButton5.getText().toString()
		// .equalsIgnoreCase(ageGrp)) {
		// genderListViewHolder.radioButton5.setChecked(true);
		// } else if (genderListViewHolder.radioButton6.getText().toString()
		// .equalsIgnoreCase(ageGrp)) {
		// genderListViewHolder.radioButton6.setChecked(true);
		// } else if (genderListViewHolder.radioButton7.getText().toString()
		// .equalsIgnoreCase(ageGrp)) {
		// genderListViewHolder.radioButton7.setChecked(true);
		//
		// }

		return convertView;
	}

	// class onRadioButtonClick implements OnClickListener {
	// private int mPosition;
	//
	// onRadioButtonClick(int position) {
	// mPosition = position;
	// }
	//
	// public void onClick(View v) {
	// RadioButton mRadioButton = null;
	// switch (v.getId()) {
	// case R.id.radioButton_age_group1:
	// mRadioButton = (RadioButton) v
	// .findViewById(R.id.radioButton_age_group1);
	// mGenderAgeModel.get(mPosition).setAgeGrp(
	// mRadioButton.getText().toString());
	// break;
	// case R.id.radioButton_age_group2:
	// mRadioButton = (RadioButton) v
	// .findViewById(R.id.radioButton_age_group2);
	// mGenderAgeModel.get(mPosition).setAgeGrp(
	// mRadioButton.getText().toString());
	// break;
	// case R.id.radioButton_age_group3:
	// mRadioButton = (RadioButton) v
	// .findViewById(R.id.radioButton_age_group3);
	// mGenderAgeModel.get(mPosition).setAgeGrp(
	// mRadioButton.getText().toString());
	// break;
	// case R.id.radioButton_age_group4:
	// mRadioButton = (RadioButton) v
	// .findViewById(R.id.radioButton_age_group4);
	// mGenderAgeModel.get(mPosition).setAgeGrp(
	// mRadioButton.getText().toString());
	// break;
	// case R.id.radioButton_age_group5:
	// mRadioButton = (RadioButton) v
	// .findViewById(R.id.radioButton_age_group5);
	// mGenderAgeModel.get(mPosition).setAgeGrp(
	// mRadioButton.getText().toString());
	// break;
	// case R.id.radioButton_age_group6:
	// mRadioButton = (RadioButton) v
	// .findViewById(R.id.radioButton_age_group6);
	// mGenderAgeModel.get(mPosition).setAgeGrp(
	// mRadioButton.getText().toString());
	// break;
	// case R.id.radioButton_age_group7:
	// mRadioButton = (RadioButton) v
	// .findViewById(R.id.radioButton_age_group7);
	// mGenderAgeModel.get(mPosition).setAgeGrp(
	// mRadioButton.getText().toString());
	// break;
	//
	// default:
	// break;
	// }
	//
	// }
	// }

	public List<GenderAgeModel> getUpdatedGenderAgeGrp() {
		if (mGenderAgeModel != null && mGenderAgeModel.size() > 0) {
			return mGenderAgeModel;
		}
		return null;
	}

	static class GenderListViewHolder {
		Switch mGenderSwitch;
		Button mAgeGrpButton;
		// RadioGroup mAgeGroupRadioGroup;
		// RadioButton radioButton1;
		// RadioButton radioButton2;
		// RadioButton radioButton3;
		// RadioButton radioButton4;
		// RadioButton radioButton5;
		// RadioButton radioButton6;
		// RadioButton radioButton7;
	}

}
