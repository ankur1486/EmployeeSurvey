package src.com.employeesurvey.adapter;

import src.com.employeesurvey.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GenderListAdapter extends BaseAdapter {

	private Context mContext;
	private int mCount = 0;

	public GenderListAdapter(Context context) {
		mContext = context;
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
			view = layoutInflater.inflate(R.layout.gender_row, null);
		} else {
			view = convertView;
		}
		return view;
	}

}
