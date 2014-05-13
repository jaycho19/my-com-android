package com.next.lottery;

import java.util.ArrayList;

import android.os.Bundle;

import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.fragment.EnsureOrderListFragment;

/**
 * 确定订单界面
 * 
 * @author dongfang
 * 
 */

public class EnsureOrderListActivity extends BaseSlidingMenuActivity {

	private EnsureOrderListFragment centerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		centerFragment = new EnsureOrderListFragment();
		if (null != this.getIntent().getParcelableArrayListExtra("orderList")) {
			ArrayList<ShopCartsInfo> orderlist = this.getIntent().getParcelableArrayListExtra("orderList");
			centerFragment.setOrderlist(orderlist);
		}
		setCenterFragment(centerFragment);
		setTopTitle("确认订单");

	}

}