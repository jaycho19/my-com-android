package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.UserHelpCenterFragment;

/**
 * 
 * @author dongfang
 * 
 */
public class UserHelpCenterActivity extends BaseSlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new UserHelpCenterFragment());
		setTopTitle("帮助中心");

	}

}
