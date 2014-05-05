package com.next.lottery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.next.lottery.R;

/**
 * 系统设置
 * 
 * @author dongfang
 * 
 */

public class UserSettingFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_setting, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onClick(View v) {}
}