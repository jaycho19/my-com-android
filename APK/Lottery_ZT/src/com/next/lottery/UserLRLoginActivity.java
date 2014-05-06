package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.UserLRLoginFragment;
/**
 * 登录
 * 
 * @author dongfang
 * 
 */
public class UserLRLoginActivity extends BaseSlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new UserLRLoginFragment());
		setTopTitle("登录");

	}
}
