package com.next.lottery;

import java.util.ArrayList;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.beans.BaseGateWayInterfaceEntity;
import com.next.lottery.beans.OrderBean;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.EnsureOrderListFragment;
import com.next.lottery.fragment.GoodsDetailFragment;
import com.next.lottery.fragment.MyOrderListFragment;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.view.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

/**
 * 我的订单
 * 
 * @author gfan
 * 
 */

public class MyOrderListActivity extends BaseActivity {
	@ViewInject(R.id.slidingMenu)
	private SlidingMenu mSlidingMenu;
	private RightMenuFragment rightFragment;
	private MyOrderListFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_menu_main);
		ViewUtils.inject(this);
		init();
		
	}


	private void init() {
		mSlidingMenu.setRightView(getLayoutInflater().inflate(R.layout.sliding_menu_right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

		rightFragment = new RightMenuFragment();
		t.replace(R.id.right_frame, rightFragment);
		mainFragment = new MyOrderListFragment();
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
	public void showRight() {
		mSlidingMenu.showRightView();
	}

}