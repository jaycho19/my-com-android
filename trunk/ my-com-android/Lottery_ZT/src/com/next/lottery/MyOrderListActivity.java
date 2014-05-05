package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.MyOrderListFragment;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.fragment.UserSettingFragment;
import com.next.lottery.view.SlidingMenu;

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
	}
}