package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.MyOrderListFragment;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.view.SlidingMenu;

/**
 * 我的订单
 * 
 * @author gfan
 * 
 */
public class MyOrderListActivity extends BaseSlidingMenuActivity {
	private MyOrderListFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
	}

	private void init() {

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		mainFragment = new MyOrderListFragment();
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {}


}