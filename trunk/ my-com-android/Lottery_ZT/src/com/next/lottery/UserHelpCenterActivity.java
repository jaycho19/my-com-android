package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.UserHelpCenterFragment;

/**
 * 详情页
 * 
 * @author fgb,dongfang
 * 
 */
public class UserHelpCenterActivity extends BaseSlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new UserHelpCenterFragment());
		setTopTitle("我的订单");

	}

}
