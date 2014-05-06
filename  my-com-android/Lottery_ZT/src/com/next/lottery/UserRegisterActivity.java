package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.UserLRRegisterFragment;

/**
 * 用户注册界面
 * 
 * @author dongfang
 * 
 */

public class UserRegisterActivity extends BaseSlidingMenuActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new UserLRRegisterFragment());
		setTopTitle("注册");

	}
}