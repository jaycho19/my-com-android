package com.next.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.LRLoginActivity;
import com.next.lottery.R;
import com.next.lottery.SettingActivity;
import com.next.lottery.UserInfoActivity;
import com.next.lottery.utils.User;

/**
 * 个人中心
 * 
 * @author dongfang
 * 
 */
public class UserCenterFragment extends BaseFragment {

	@ViewInject(R.id.app_top_title_iv_rigth)
	private ImageView ivSetting;
	@ViewInject(R.id.usercenter_iv_user_head)
	private ImageView ivHead;

	@ViewInject(R.id.usercenter_tv_user_name)
	private TextView tvUserName;
	@ViewInject(R.id.usercenter_tv_user_tishenghuiyuan)
	private TextView tvTiShengHuiYuan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (!User.isLogined(getActivity())) {
			startActivity(new Intent(getActivity(), LRLoginActivity.class));
			return null;
		}
		View view = inflater.inflate(R.layout.fragment_usercenter, container, false);
		ViewUtils.inject(this, view);
		
		return view;
	}

	@Override
	@OnClick({ R.id.app_top_title_iv_rigth, R.id.usercenter_iv_user_head, R.id.usercenter_tv_user_name,
			R.id.usercenter_tv_user_tishenghuiyuan

	})
	public void onClick(View v) {
		ULog.d("id = " + v.getId());
		switch (v.getId()) {
		case R.id.app_top_title_iv_rigth:
			startActivity(new Intent(getActivity(), SettingActivity.class));
			break;
		case R.id.usercenter_iv_user_head:
		case R.id.usercenter_tv_user_name:
		case R.id.usercenter_tv_user_tishenghuiyuan:
			startActivity(new Intent(getActivity(), UserInfoActivity.class));
			break;
		default:
			break;
		}
	}
}
