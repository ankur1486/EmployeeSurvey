package src.com.employeesurvey.adapter;

import java.util.ArrayList;
import java.util.List;

import src.com.employeesurvey.R;
import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.model.GenderAgeModel;
import src.com.employeesurvey.util.ScalingLayout;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;

public class GenderListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<GenderAgeModel> mGenderAgeModel;
	private int mLeftListRowId;

	public GenderListAdapter(Context context,
			ArrayList<GenderAgeModel> genderAgeModelsList) {
		mContext = context;
		mGenderAgeModel = genderAgeModelsList;
	}

	public void setNumberOfCounts(ArrayList<GenderAgeModel> genderAgeModel,
			int leftListRowID) {
		mGenderAgeModel = genderAgeModel;
		mLeftListRowId = leftListRowID;
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
			convertView.setTag(genderListViewHolder);
		} else {
			genderListViewHolder = (GenderListViewHolder) convertView.getTag();
		}

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
				.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						return event.getActionMasked() == MotionEvent.ACTION_MOVE;
					}
				});

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
						EmployeeSurveyDb.getInstance().updateGenderRow(
								position, mLeftListRowId,
								mGenderAgeModel.get(position).getGender(),
								mGenderAgeModel.get(position).getAgeGrp(),
								mGenderAgeModel.get(position).getGroupType());
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
									genderListViewHolder.mAgeGrpButton
											.setText(button.getText()
													.toString());
									dialog.dismiss();

									EmployeeSurveyDb.getInstance()
											.updateGenderRow(
													position,
													mLeftListRowId,
													mGenderAgeModel.get(
															position)
															.getGender(),
													mGenderAgeModel.get(
															position)
															.getAgeGrp(),
													mGenderAgeModel.get(
															position)
															.getGroupType());
								}
							});
						}
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

	static class GenderListViewHolder {
		Switch mGenderSwitch;
		Button mAgeGrpButton;
	}

}
