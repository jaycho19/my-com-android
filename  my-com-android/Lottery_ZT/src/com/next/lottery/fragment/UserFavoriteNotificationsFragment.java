package com.next.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.R;
import com.next.lottery.UserFavoriteNotificationsActivity;

/**
 * 收藏变动提醒
 * 
 * @author dongfang
 * 
 */
public class UserFavoriteNotificationsFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_favorite_notifications, container, false);
		ViewUtils.inject(this, view);

		return view;
	}

	@OnClick({ R.id.fragment_user_setting_tv_favorite_notification })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_user_setting_tv_favorite_notification:
			getActivity().startActivity(new Intent(getActivity(), UserFavoriteNotificationsActivity.class));
			break;

		default:
			break;
		}
	}

}
