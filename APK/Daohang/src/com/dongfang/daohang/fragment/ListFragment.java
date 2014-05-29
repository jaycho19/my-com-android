package com.dongfang.daohang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.daohang.R;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;

public class ListFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onClick(View v) {}

}
