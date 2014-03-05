package src.com.employeesurvey.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import src.com.employeesurvey.R;
import src.com.employeesurvey.RightFragment;
import src.com.employeesurvey.database.EmployeeSurveyDb;
import src.com.employeesurvey.prefrences.EmployeePrefrence;
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

public class LeftPanelListAdapter extends BaseAdapter {

	private Context mContext;
	private int mCount = 1;
	private Fragment mFragment;
	private String currentTime;
	private String mLatitude;
	private String mLongitude;

	public LeftPanelListAdapter(Context context, Fragment fragment) {
		mContext = context;
		mFragment = fragment;

		mLatitude = EmployeePrefrence.getInstance().getStringValue(
				EmployeePrefrence.SET_LATITUDE, "12.77");
		mLongitude = EmployeePrefrence.getInstance().getStringValue(
				EmployeePrefrence.SET_LONGITUDE, "30.77");

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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(mContext);
			view = layoutInflater.inflate(R.layout.left_fragment_row, null);
		} else {
			view = convertView;
		}

		ToggleButton addDeleteButton = (ToggleButton) view
				.findViewById(R.id.button_add_delete);
		addDeleteButton
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							EmployeeSurveyDb.getInstance().insertLeftRow(position, 0, currentTime, mLatitude, mLongitude, 1, 1);
							mCount = mCount + 1;
							notifyDataSetChanged();
							
							
						} else {
							mCount = mCount - 1;
							notifyDataSetChanged();
							
							EmployeeSurveyDb.getInstance().getLeftRowEntries(0);
						}
					}
				});

		addDeleteButton.setOnClickListener(new onClickAddDeleteButton(position,
				mContext));

		final Button countButton = (Button) view
				.findViewById(R.id.numberPicker_button);
		countButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = null;
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View npView = inflater.inflate(
						R.layout.number_picker_dialog_layout, null);

				final NumberPicker numberPicker = (NumberPicker) npView
						.findViewById(R.id.numberPicker1);
				numberPicker.setMinValue(1);
				numberPicker.setMaxValue(20);

				alertDialog = new AlertDialog.Builder(mContext)
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

										countButton.setText("" + index);
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

		TextView groupIdTextView = (TextView) view
				.findViewById(R.id.textView_groupId);
		groupIdTextView.setText("" + (1 + position));
		TextView timeTextView = (TextView) view
				.findViewById(R.id.textView_time);

		timeTextView.setText(getcurrentTime());
		TextView locationTextView = (TextView) view
				.findViewById(R.id.textView_location);
		locationTextView.setText(mLatitude + " , " + mLongitude);
		locationTextView.setOnClickListener(new OnLocationItemClickListener(
				position, mContext));
		view.setOnClickListener(new OnItemClickListener(position, mContext));
		return view;
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

	onClickAddDeleteButton(int position, Context context) {
		mPosition = position;
		mViewClickContext = context;
	}

	public void onClick(View v) {

		Toast.makeText(mViewClickContext, "View item clicked ",
				Toast.LENGTH_SHORT).show();

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
