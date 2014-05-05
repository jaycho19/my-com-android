package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.fragment.UserSettingFragment;
import com.next.lottery.view.SlidingMenu;

/**
 * 系统设置
 * 
 * @author fgb,dongfang
 * 
 */
public class UserSettingActivity extends BaseSlidingMenuActivity {
	@ViewInject(R.id.slidingMenu)
	private SlidingMenu mSlidingMenu;
	private RightMenuFragment rightFragment;
	private UserSettingFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
	}

	private void init() {

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

		mainFragment = new UserSettingFragment();
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {}

}
