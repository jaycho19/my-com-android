package com.next.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.LRLoginActivity;
import com.next.lottery.R;
import com.next.lottery.SettingActivity;
import com.next.lottery.utils.User;

public class UserCenterFragment extends BaseFragment {
	@ViewInject(R.id.app_top_title_iv_rigth)
	private ImageView ivSetting;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (!User.isLogined(getActivity())) {
			startActivity(new Intent(getActivity(), LRLoginActivity.class));
			return null;
		}

		View view = inflater.inflate(R.layout.fragment_usercenter, container, false);
		ViewUtils.inject(view);
		return view;
	}

	@OnClick({ R.id.app_top_title_iv_rigth })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_top_title_iv_rigth:
			startActivity(new Intent(getActivity(), SettingActivity.class));
			break;
		default:
			break;
		}
	}
}
