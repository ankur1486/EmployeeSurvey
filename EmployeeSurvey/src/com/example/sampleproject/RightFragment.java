package com.example.sampleproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RightFragment extends Fragment {

	ListView mGenderList;
	GenderListAdapter genderListAdapter;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragment = inflater.inflate(R.layout.rightpanel_layout, container);
		init(fragment);
		return fragment;
	}

	private void init(View fragment) {
		System.out.println("*****************************88");
		mGenderList = (ListView) fragment.findViewById(R.id.listView_gender);
		genderListAdapter = new GenderListAdapter(getActivity());
		mGenderList.setAdapter(genderListAdapter);
	}

	public void updateList(String number) {
		int count = Integer.parseInt(number);
		if (genderListAdapter != null) {
			genderListAdapter.setNumberOfCounts(count);
		} 
	}
}
