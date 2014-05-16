package com.dongfang.daohang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dongfang.daohang.R;
import com.dongfang.daohang.UserLRLoginActivity;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class UserFragment extends BaseFragment {

	@ViewInject(R.id.fragment_userlr_login_bt_register)
	private Button btResgister;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_userlr_login, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@OnClick({ R.id.fragment_userlr_login_bt_register })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_userlr_login_bt_register:
			getActivity().startActivity(new Intent(getActivity(), UserLRLoginActivity.class));
			break;

		default:
			break;
		}
	}
}
