package com.dongfang.daohang.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongfang.daohang.R;
import com.dongfang.daohang.UserFollowActivity;
import com.dongfang.daohang.UserInfoActivity;
import com.dongfang.daohang.UserRecordActivity;
import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class UserFragment extends BaseFragment {

	@ViewInject(R.id.fragment_userlr_login_topbar)
	private View ll;

	@ViewInject(R.id.top_bar_btn_back)
	private View v;

	@ViewInject(R.id.top_bar_tv_title)
	private TextView title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user, container, false);
		ViewUtils.inject(this, view);

		ULog.d((null != getArguments()) + "---------------------------");

		if (null != getArguments()) {
			ll.setVisibility(View.GONE);
		}

		v.setVisibility(View.INVISIBLE);
		title.setText("我的");
		return view;
	}

	@OnClick({ R.id.fragment_user_civ_icon, R.id.fragment_user_rl_1, R.id.fragment_user_rl_2, R.id.fragment_user_rl_3,
			R.id.top_bar_btn_back

	})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_user_civ_icon:
		case R.id.top_bar_btn_back:
			getActivity().startActivity(new Intent(getActivity(), UserInfoActivity.class));
			break;
		case R.id.fragment_user_rl_1:
			break;
		case R.id.fragment_user_rl_2:
			getActivity().startActivity(new Intent(getActivity(), UserRecordActivity.class));
			break;
		case R.id.fragment_user_rl_3:
			getActivity().startActivity(new Intent(getActivity(), UserFollowActivity.class));
			break;

		default:
			break;
		}
	}
}
