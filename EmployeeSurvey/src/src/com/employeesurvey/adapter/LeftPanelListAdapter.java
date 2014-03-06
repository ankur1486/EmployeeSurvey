package src.com.employeesurvey.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import src.com.employeesurvey.R;
import src.com.employeesurvey.RightFragment;
import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.prefrences.EmployeePrefrence;
import src.com.employeesurvey.util.Constants;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Left adapter class which will hold the id , person count , time , Location ,
 * Add delete button and status is form completed or not.
 * 
 * 
 */
public class LeftPanelListAdapter extends BaseAdapter {

	private Context mContext;
	private int mCount;
	private Fragment mFragment;
	private String currentTime;
	private String mLatitude;
	private String mLongitude;
	EmployeeLeftListMemberViewHolder holder = new EmployeeLeftListMemberViewHolder();;

	public LeftPanelListAdapter(Context context, int listCount,
			Fragment fragment) {
		mContext = context;
		mFragment = fragment;
		mCount = listCount;
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

	private boolean toggleIsChecked;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;

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

			view.setTag(holder);

		} else {

			holder = (EmployeeLeftListMemberViewHolder) view.getTag();
		}

		holder.addDeleteButton
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							EmployeeSurveyDb.getInstance().insertLeftRow(
									position, 0, currentTime, mLatitude,
									mLongitude, 1, 1);
							mCount = EmployeeSurveyDb.getInstance()
									.getLeftListCount();
							notifyDataSetChanged();
							toggleIsChecked = isChecked;

						} else {
							showDeleteRowConfirmAlert();
						}
					}
				});

		// holder.addDeleteButton.setOnClickListener(new onClickAddDeleteButton(
		// position, toggleIsChecked, mContext));

		holder.countButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View npView = inflater.inflate(
						R.layout.number_picker_dialog_layout, null);

				final NumberPicker numberPicker = (NumberPicker) npView
						.findViewById(R.id.numberPicker1);
				numberPicker.setMinValue(1);
				numberPicker.setMaxValue(20);

				AlertDialog alertDialog = new AlertDialog.Builder(mContext)
						.setTitle(R.string.number_of_persons_)
						.setView(npView)
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int whichButton) {

										int index = numberPicker.getValue();
										RightFragment rightFragment = (RightFragment) mFragment
												.getFragmentManager()
												.findFragmentById(
														R.id.right_fragmment);
										rightFragment.updateList(index);

										holder.countButton.setText("" + index);
									}
								})
						.setNegativeButton(R.string.cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).create();
				alertDialog
						.getWindow()
						.setSoftInputMode(
								WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				alertDialog.show();
			}
		});

		holder.groupIdTextView.setText("" + (1 + position));

		holder.timeTextView.setText(getcurrentTime());

		holder.locationTextView.setText(mLatitude + " , " + mLongitude);
		holder.locationTextView
				.setOnClickListener(new OnLocationItemClickListener(position,
						mContext));
		view.setOnClickListener(new OnItemClickListener(position, mContext));
		return view;
	}

	/**
	 * mAlert dialog to confirm whether user want to delete the particular row
	 */
	private void showDeleteRowConfirmAlert() {
		new AlertDialog.Builder(mContext)
				.setTitle(R.string.app_name)
				.setMessage(R.string.are_you_sure_you_want_to_delete_this_row)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								mCount = mCount - 1;
								notifyDataSetChanged();

								EmployeeSurveyDb.getInstance()
										.getLeftRowEntries(0);
							}
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
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

		if (mToggleIsChecked) {
			Toast.makeText(mViewClickContext, "Toggle is add  ",
					Toast.LENGTH_SHORT).show();

		} else {
			Toast.makeText(mViewClickContext, "Toggle is delete ",
					Toast.LENGTH_SHORT).show();
		}

	}
}

class OnItemClickListener implements OnClickListener {
	private int mPosition;
	private Context mViewClickContext;

	OnItemClickListener(int position, Context context) {
		mPosition = position;
		mViewClickContext = context;
	}

	public void onClick(View v) {

		Toast.makeText(mViewClickContext, "View item clicked ",
				Toast.LENGTH_SHORT).show();

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
