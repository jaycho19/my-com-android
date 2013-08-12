package com.dongfang.yzsj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.yzsj.R;

/**
 * 搜索
 * 
 * @author dongfang
 * 
 */
public class SearchFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_searche, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {}

}
