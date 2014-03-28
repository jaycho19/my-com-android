package com.next.lottery.fragment;

import com.dongfang.v4.app.BaseActivity;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.R;
import com.next.lottery.R.id;
import com.next.lottery.R.layout;
import com.next.lottery.view.SlidingMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 设置
 * 
 * @author dongfang
 * 
 */

public class UserSettingFragment extends BaseFragment {

	private SlidingMenu mSlidingMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_user_setting, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	public SlidingMenu getmSlidingMenu() {
		return mSlidingMenu;
	}

	public void setmSlidingMenu(SlidingMenu mSlidingMenu) {
		this.mSlidingMenu = mSlidingMenu;
	}

	@Override
	@OnClick({ R.id.app_top_title_iv_left, R.id.app_top_title_iv_rigth })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			getActivity().finish();
			break;
		case R.id.app_top_title_iv_rigth:
			if (null != mSlidingMenu)
				mSlidingMenu.showRightView();
			break;

		default:
			break;
		}
	}
}