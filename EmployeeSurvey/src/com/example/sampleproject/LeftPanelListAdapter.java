package com.example.sampleproject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LeftPanelListAdapter extends BaseAdapter {

	private Context mContext;
	private int mCount = 1;
	private Fragment mFragment;
	private String mPinCode = "";

	public LeftPanelListAdapter(Context context, Fragment fragment) throws NumberFormatException, IOException {
		mContext = context;
		mFragment = fragment;
		mPinCode = getLocation(); 
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
	public View getView(int position, View convertView, ViewGroup parent) {
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
							mCount = mCount + 1;
							notifyDataSetChanged();
						} else {
							mCount = mCount - 1;
							notifyDataSetChanged();
						}
					}
				});
		EditText mgenderSizeInput = (EditText) view
				.findViewById(R.id.editText_no_of_person);
		mgenderSizeInput.addTextChangedListener(listenerTextChangedFiltro);
		TextView groupIdTextView = (TextView) view
				.findViewById(R.id.textView_groupId);
		groupIdTextView.setText(""+(1 + position));
		TextView timeTextView = (TextView)view.findViewById(R.id.textView_time);
		Calendar now = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		timeTextView.setText(df.format(now.getTime()));
		TextView locationTextView = (TextView)view.findViewById(R.id.textView_location);
		locationTextView.setText(mPinCode);
		return view;
	}

	private TextWatcher listenerTextChangedFiltro = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			RightFragment fragment1 = (RightFragment) mFragment
					.getFragmentManager()
					.findFragmentById(R.id.right_fragmment);
			fragment1.updateList(s.toString());
		}
	};
	
	public String getLocation() throws NumberFormatException, IOException{
		String postalCode = "";
		// check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(mContext);

        if (gpsTracker.canGetLocation())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
           
            String stringLongitude = String.valueOf(gpsTracker.longitude);
            postalCode = gpsTracker.getPostalCode(mContext);
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
        return postalCode;
	}
}
