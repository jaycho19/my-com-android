package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.UserCouponFragment;

/**
 * 优惠券
 * 
 * @author dongfang
 * 
 */

public class UserCouponActivity extends BaseSlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new UserCouponFragment());
		setTopTitle("我的优惠券");
	}

}