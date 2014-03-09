package src.com.employeesurvey.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import src.com.employeesurvey.R;
import src.com.employeesurvey.RightFragment;
import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.model.EmployeeModel;
import src.com.employeesurvey.model.GenderAgeModel;
import src.com.employeesurvey.prefrences.EmployeePrefrence;
import src.com.employeesurvey.util.Constants;
import src.com.employeesurvey.util.ScalingLayout;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Left adapter class which will hold the id , person count , time , Location ,
 * Add delete button and status is form completed or not.
 * 
 * 
 */
public class LeftPanelListAdapter extends BaseAdapter {

	protected static final String TAG = LeftPanelListAdapter.class
			.getSimpleName();
	private Context mContext;
	private Fragment mFragment;
	private String currentTime;
	private String mLatitude;
	private String mLongitude;
	private int mSize = 0;

	private int mSelectedPosition = 0;
	private ArrayList<EmployeeModel> mEmployeeModel;;

	public LeftPanelListAdapter(Context context,
			ArrayList<EmployeeModel> employeeModels, Fragment fragment) {
		mContext = context;
		mFragment = fragment;
		mEmployeeModel = employeeModels;
		mSize = (mEmployeeModel.size() - 1);
		mLatitude = EmployeePrefrence.getInstance().getStringValue(
				EmployeePrefrence.SET_LATITUDE, Constants.DEFAULT_LATITUDE);
		mLongitude = EmployeePrefrence.getInstance().getStringValue(
				EmployeePrefrence.SET_LONGITUDE, Constants.DEFAULT_LATITUDE);

	}

	private String getcurrentTime() {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return currentTime = df.format(now.getTime());
	}

	private String setTimeFormat(String milliSeconds) {
		// Create a DateFormatter object for displaying date in specified
		// format.

		long msecLong = Long.parseLong(milliSeconds);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(msecLong);
		return formatter.format(calendar.getTime());
	}

	@Override
	public int getCount() {
		return mEmployeeModel.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private boolean toggleIsChecked;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final EmployeeLeftListMemberViewHolder holder;
		if (view == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(mContext);
			view = layoutInflater.inflate(R.layout.left_fragment_row, null);

			holder = new EmployeeLeftListMemberViewHolder();

			holder.addDeleteButton = (ToggleButton) view
					.findViewById(R.id.button_add_delete);

			holder.countButton = (Button) view
					.findViewById(R.id.numberPicker_button);

			holder.groupIdTextView = (TextView) view
					.findViewById(R.id.textView_groupId);

			holder.timeTextView = (TextView) view
					.findViewById(R.id.textView_time);
			holder.locationTextView = (TextView) view
					.findViewById(R.id.textView_location);
			holder.completedCheckBox = (CheckBox) view
					.findViewById(R.id.checkBox_form_completed);

			view.setTag(holder);

		} else {

			holder = (EmployeeLeftListMemberViewHolder) view.getTag();
		}

		EmployeeModel employeeModel = mEmployeeModel.get(position);

		if (mSize == position) {
			holder.addDeleteButton.setChecked(true);
		} else {
			holder.addDeleteButton.setChecked(false);
		}
		if (employeeModel.isFormCompleted() == 1) {
			holder.completedCheckBox.setChecked(true);
		} else {
			holder.completedCheckBox.setChecked(false);
		}
		holder.completedCheckBox.setEnabled(false);
		holder.countButton.setText("" + employeeModel.getPersonCount());
		holder.addDeleteButton.setOnClickListener(new onClickAddDeleteButton(
				position, toggleIsChecked, mContext));

		holder.countButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// showAlert(mContext , position );
				// AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				// mContext);
				final Dialog dialog = new Dialog(mContext);
				dialog.setTitle("Set Person Count");
				dialog.setContentView(R.layout.number_picker_dialog_layout);

				dialog.show();

				ScalingLayout mScalingLayoutView = (ScalingLayout) dialog
						.findViewById(R.id.emma_category_button_layout);

				for (int i = 1; i <= 15; i++) {
					Button button = new Button(mContext);
					button.setText("" + i);
					button.setId(i);
					button.setGravity(Gravity.CENTER);
					button.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Button button = (Button) v.findViewById(v.getId());
							EmployeeSurveyDb.getInstance()
									.updatePersonCount(
											""
													+ mEmployeeModel.get(
															position)
															.getRowId(),
											button.getId());

							EmployeeSurveyDb.getInstance()
									.deleteGenderDetailByRowId(
											""
													+ mEmployeeModel.get(
															position)
															.getRowId());
							for (int j = 0; j < button.getId(); j++) {
								GenderAgeModel genderAgeModel = new GenderAgeModel();
								EmployeeSurveyDb.getInstance()
										.insertGenderRow(
												mEmployeeModel.get(position)
														.getRowId(),
												genderAgeModel.getGender(),
												genderAgeModel.getAgeGrp(),
												genderAgeModel.getGroupType());
							}
							mEmployeeModel = EmployeeSurveyDb.getInstance()
									.getDataModelForList();
							mSize = (mEmployeeModel.size() - 1);
							Log.w(TAG, "Total left row" + mEmployeeModel.size());
							notifyDataSetChanged();
							holder.countButton.setText(""
									+ button.getText().toString());
							updateRightFragment(0, position);
							dialog.dismiss();
						}
					});
					mScalingLayoutView.addView(button);
				}

				// LayoutInflater inflater = (LayoutInflater) mContext
				// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// View npView = inflater.inflate(
				// R.layout.number_picker_dialog_layout, null);

			}
		});

		// holder.countButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// LayoutInflater inflater = (LayoutInflater) mContext
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View npView = inflater.inflate(
		// R.layout.number_picker_dialog_layout, null);
		//
		// final NumberPicker numberPicker = (NumberPicker) npView
		// .findViewById(R.id.numberPicker1);
		// numberPicker.setMinValue(1);
		// numberPicker.setMaxValue(15);
		//
		// AlertDialog alertDialog = new AlertDialog.Builder(mContext)
		// .setTitle(R.string.number_of_persons_)
		// .setView(npView)
		// .setPositiveButton(R.string.ok,
		// new DialogInterface.OnClickListener() {
		//
		// public void onClick(DialogInterface dialog,
		// int whichButton) {
		//
		// int index = numberPicker.getValue();
		// EmployeeSurveyDb
		// .getInstance()
		// .updatePersonCount(
		// ""
		// + mEmployeeModel
		// .get(position)
		// .getRowId(),
		// index);
		//
		// EmployeeSurveyDb
		// .getInstance()
		// .deleteGenderDetailByRowId(
		// ""
		// + mEmployeeModel
		// .get(position)
		// .getRowId());
		// for (int i = 0; i < index; i++) {
		// GenderAgeModel genderAgeModel = new GenderAgeModel();
		// EmployeeSurveyDb
		// .getInstance()
		// .insertGenderRow(
		// mEmployeeModel.get(
		// position)
		// .getRowId(),
		// genderAgeModel
		// .getGender(),
		// genderAgeModel
		// .getAgeGrp(),
		// genderAgeModel
		// .getGroupType());
		// }
		// mEmployeeModel = EmployeeSurveyDb
		// .getInstance()
		// .getDataModelForList();
		// mSize = (mEmployeeModel.size() - 1);
		// Log.w(TAG, "Total left row"
		// + mEmployeeModel.size());
		// notifyDataSetChanged();
		// holder.countButton.setText("" + index);
		// updateRightFragment(0, position);
		// }
		//
		// })
		// .setNegativeButton(R.string.cancel,
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog,
		// int whichButton) {
		// }
		// }).create();
		// alertDialog
		// .getWindow()
		// .setSoftInputMode(
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// alertDialog.show();
		// }
		// });

		holder.groupIdTextView.setText("" + (1 + position));

		holder.timeTextView.setText(setTimeFormat(employeeModel.getTime()));

		holder.locationTextView.setText(employeeModel.getLatitude() + " , "
				+ employeeModel.getLongitude());
		holder.locationTextView
				.setOnClickListener(new OnLocationItemClickListener(position,
						mContext));
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSelectedPosition = position;
				selectItem(v, position);
				updateRightFragment(0, position);

				notifyDataSetChanged();
			}

			private void selectItem(View v, int position) {
				v.setBackgroundResource(android.R.color.holo_blue_light);
			}
		});

		if (mSelectedPosition == position) {
			view.setBackgroundResource(android.R.color.holo_blue_light);
		} else {
			view.setBackgroundResource(android.R.color.white);
		}

		return view;
	}

	/**
	 * mAlert dialog to confirm whether user want to delete the particular row
	 */
	private void showDeleteRowConfirmAlert(final int position) {
		new AlertDialog.Builder(mContext)
				.setTitle(R.string.app_name)
				.setMessage(R.string.are_you_sure_you_want_to_delete_this_row)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								mSelectedPosition = position;
								EmployeeSurveyDb.getInstance().deleteLeftRow(
										""
												+ mEmployeeModel.get(position)
														.getRowId());
								mEmployeeModel = EmployeeSurveyDb.getInstance()
										.getDataModelForList();
								mSize = (mEmployeeModel.size() - 1);
								Log.w(TAG,
										"Total left row"
												+ mEmployeeModel.size());
								notifyDataSetChanged();

							}
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								notifyDataSetChanged();
								dialog.cancel();

							}
						}).setIcon(R.drawable.ic_launcher).show();
	}

	static class EmployeeLeftListMemberViewHolder {
		TextView locationTextView;
		TextView timeTextView;
		TextView groupIdTextView;
		Button countButton;
		ToggleButton addDeleteButton;
		CheckBox completedCheckBox;
	}

	private void updateRightFragment(int position, int rowId) {
		RightFragment rightFragment = (RightFragment) mFragment
				.getFragmentManager().findFragmentById(R.id.right_fragmment);
		if (position != -1) {
			rightFragment.updateList(mEmployeeModel.get(rowId)
					.getGenderAgeModel(), mEmployeeModel.get(rowId).getRowId());
		} else {
			ArrayList<GenderAgeModel> genderAgeModelList = new ArrayList<GenderAgeModel>();

			rightFragment.updateList(genderAgeModelList, rowId);
		}
	}

	/**
	 * 
	 * on click Add/Delete
	 * 
	 */
	class onClickAddDeleteButton implements OnClickListener {
		private int mPosition;
		private Context mViewClickContext;
		private boolean mToggleIsChecked;

		onClickAddDeleteButton(int position, boolean toggleIsChecked,
				Context context) {
			mPosition = position;
			mViewClickContext = context;
			mToggleIsChecked = toggleIsChecked;
		}

		public void onClick(View v) {

			ToggleButton addDeleteButton = (ToggleButton) v
					.findViewById(R.id.button_add_delete);

			if (!addDeleteButton.isChecked()) {
				mSelectedPosition = mPosition + 1;
				// Toast.makeText(mViewClickContext, "Toggle is add  ",
				// Toast.LENGTH_SHORT).show();
				int leftListCount = EmployeeSurveyDb.getInstance()
						.getLeftListCount();
				String latitude = EmployeePrefrence.getInstance()
						.getStringValue(EmployeePrefrence.SET_LATITUDE, "");
				String longitude = EmployeePrefrence.getInstance()
						.getStringValue(EmployeePrefrence.SET_LONGITUDE, "");

				EmployeeSurveyDb.getInstance().insertLeftRow(leftListCount, 0,
						"" + System.currentTimeMillis(), latitude, longitude,
						0, 0);
				mEmployeeModel = EmployeeSurveyDb.getInstance()
						.getDataModelForList();
				Log.w(LeftPanelListAdapter.TAG, "Total left row"
						+ mEmployeeModel.size());
				mSize = (mEmployeeModel.size() - 1);

				notifyDataSetChanged();
				updateRightFragment(-1, mPosition);

			} else {
				// Toast.makeText(mViewClickContext, "Toggle is delete ",
				// Toast.LENGTH_SHORT).show();
				showDeleteRowConfirmAlert(mPosition);
			}

		}
	}

	public void updateData() {
		mEmployeeModel = EmployeeSurveyDb.getInstance().getDataModelForList();
		notifyDataSetChanged();
	}
}

class OnLocationItemClickListener implements OnClickListener {
	private int mPosition;
	private Context mViewClickContext;

	OnLocationItemClickListener(int position, Context context) {
		mPosition = position;
		mViewClickContext = context;
	}

	public void onClick(View v) {

		String latitude = EmployeePrefrence.getInstance().getStringValue(
				EmployeePrefrence.SET_LATITUDE, "12.77");
		String longitude = EmployeePrefrence.getInstance().getStringValue(
				EmployeePrefrence.SET_LONGITUDE, "33.77");

		String label = "Your Store Location";
		String uriBegin = "geo:" + latitude + "," + longitude;
		String query = latitude + "," + longitude + "(" + label + ")";
		String encodedQuery = Uri.encode(query);
		String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
		Uri uri = Uri.parse(uriString);
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
		mViewClickContext.startActivity(intent);
	}
}
