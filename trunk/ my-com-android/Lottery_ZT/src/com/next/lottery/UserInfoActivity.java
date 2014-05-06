package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.UserInfoFragment;

/**
 * 个人信息
 * 
 * @author dongfang
 * 
 */
public class UserInfoActivity extends BaseSlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new UserInfoFragment());
		setTopTitle("个人信息");

	}

}
