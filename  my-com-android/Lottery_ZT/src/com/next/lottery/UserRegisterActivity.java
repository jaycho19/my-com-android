package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.next.lottery.fragment.UserLRRegisterFragment;

/**
 * 用户注册界面
 * 
 * @author gfan
 * 
 */

public class UserRegisterActivity extends BaseSlidingMenuActivity {
	private UserLRRegisterFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		mainFragment = new UserLRRegisterFragment();
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {}

}