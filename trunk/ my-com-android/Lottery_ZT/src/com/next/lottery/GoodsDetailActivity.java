package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.GoodsDetailFragment;
import com.next.lottery.fragment.GoodsDetailRightMenuFragment;
import com.next.lottery.view.SlidingMenu;

/**
 * 详情页
 * 
 * @author fgb,dongfang
 * 
 */
public class GoodsDetailActivity extends BaseActivity {
	@ViewInject(R.id.slidingMenu)
	private SlidingMenu mSlidingMenu;
	private GoodsDetailRightMenuFragment rightFragment;
	private GoodsDetailFragment mainFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.sliding_menu_main);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		mSlidingMenu.setRightView(getLayoutInflater().inflate(R.layout.sliding_menu_right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

		rightFragment = new GoodsDetailRightMenuFragment();
		t.replace(R.id.right_frame, rightFragment);
		mainFragment = new GoodsDetailFragment(this);
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	public void showRight() {
		mSlidingMenu.showRightView();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
