package com.next.lottery;

import java.util.ArrayList;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.fragment.EnsureOrderListFragment;
import com.next.lottery.fragment.GoodsDetailFragment;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.fragment.UserRegisterFragment;
import com.next.lottery.view.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * 用户注册界面
 * 
 * @author gfan
 * 
 */

public class UserRegisterActivity extends BaseActivity {
	@ViewInject(R.id.slidingMenu)
	private SlidingMenu mSlidingMenu;
	private RightMenuFragment rightFragment;
	private UserRegisterFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_menu_main);
		ViewUtils.inject(this);
		init();
		
	}

	private void init() {
		// TODO Auto-generated method stub
		mSlidingMenu.setRightView(getLayoutInflater().inflate(R.layout.sliding_menu_right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

		rightFragment = new RightMenuFragment();
		t.replace(R.id.right_frame, rightFragment);
		mainFragment = new UserRegisterFragment();
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