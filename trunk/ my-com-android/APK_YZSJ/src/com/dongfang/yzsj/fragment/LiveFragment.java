package com.dongfang.yzsj.fragment;

import com.dongfang.yzsj.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 直播
 * 
 * @author dongfang
 * 
 */
public class LiveFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_live, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {}
}
