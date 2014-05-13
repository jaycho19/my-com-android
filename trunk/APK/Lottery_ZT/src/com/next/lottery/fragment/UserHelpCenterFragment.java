package com.next.lottery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.BaseSlidingMenuActivity;
import com.next.lottery.R;
import com.next.lottery.view.SlidingMenu;

/**
 * 帮助中心
 * 
 * @author dongfang
 * 
 */

public class UserHelpCenterFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_user_helpcenter, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onClick(View v) {
	}
}