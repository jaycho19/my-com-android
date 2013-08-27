package com.dongfang.yzsj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.yzsj.R;

/**
 * 我的订阅
 * 
 * @author dongfang
 * 
 */
public class SubscibeFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_vod, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {}
}
