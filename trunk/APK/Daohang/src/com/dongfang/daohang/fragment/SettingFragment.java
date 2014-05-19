package com.dongfang.daohang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.daohang.R;
import com.dongfang.daohang.SettingAboutActivity;
import com.dongfang.daohang.SettingFeedbackActivity;
import com.dongfang.daohang.SettingMapDownloadActivity;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@OnClick({ R.id.fragment_setting_tv_invite, R.id.fragment_setting_tv_map, R.id.fragment_setting_tv_about,
			R.id.fragment_setting_tv_evaluate, R.id.fragment_setting_tv_feedback,

	})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_setting_tv_feedback:
			getActivity().startActivity(new Intent(getActivity(), SettingFeedbackActivity.class));
			break;
		case R.id.fragment_setting_tv_about:
			getActivity().startActivity(new Intent(getActivity(), SettingAboutActivity.class));
			break;
		case R.id.fragment_setting_tv_map:
			getActivity().startActivity(new Intent(getActivity(), SettingMapDownloadActivity.class));
			break;
		case R.id.fragment_setting_tv_invite: // 邀请好友
			break;
		case R.id.fragment_setting_tv_evaluate: // 评论
			break;

		default:
			break;
		}

	}

}
