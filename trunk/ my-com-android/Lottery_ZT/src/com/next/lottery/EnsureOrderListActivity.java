package com.next.lottery;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.fragment.EnsureOrderListFragment;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.view.SlidingMenu;

/**
 * 确定订单界面
 * 
 * @author gfan
 * 
 */

public class EnsureOrderListActivity extends BaseSlidingMenuActivity {
	private EnsureOrderListFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
		
	}

	private void init() {

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

		mainFragment = new EnsureOrderListFragment();
		if (null!=this.getIntent().getParcelableArrayListExtra("orderList")) {
			ArrayList<ShopCartsInfo> orderlist = this.getIntent().getParcelableArrayListExtra("orderList");
			mainFragment.setOrderlist(orderlist);
		}
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}