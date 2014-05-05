package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.fragment.UserHelpCenterFragment;
import com.next.lottery.view.SlidingMenu;

/**
 * 帮助中心
 * 
 * @author gfan,dongfang
 * 
 */
public class UserHelpCenterActivity extends BaseSlidingMenuActivity {
	private UserHelpCenterFragment mainFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		init();
	}

	private void init() {

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

		mainFragment = new UserHelpCenterFragment();
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
