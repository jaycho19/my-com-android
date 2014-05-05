package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.fragment.UserInfoFragment;
import com.next.lottery.view.SlidingMenu;

/**
 * 详情页
 * 
 * @author fgb,dongfang
 * 
 */
public class UserInfoActivity extends BaseSlidingMenuActivity {
	private UserInfoFragment mainFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		init();
	}

	private void init() {

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

		mainFragment = new UserInfoFragment();
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
