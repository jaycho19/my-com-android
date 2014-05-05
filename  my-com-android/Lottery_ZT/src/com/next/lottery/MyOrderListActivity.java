package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.MyOrderListFragment;

/**
 * 我的订单
 * 
 * @author gfan
 * 
 */
public class MyOrderListActivity extends BaseSlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new MyOrderListFragment());
		setTopTitle("我的订单");
	}
}