package com.next.lottery;

import com.next.lottery.fragment.GoodsDetailFragment;
import com.next.lottery.fragment.GoodsDetailRightMenuFragment;
import com.next.lottery.view.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


public class GoodsDetailActivity extends FragmentActivity {
	private SlidingMenu mSlidingMenu;
	private GoodsDetailRightMenuFragment rightFragment;
	private GoodsDetailFragment  mainFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.sliding_menu_main);
		init();

	}

	private void init() {
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.sliding_menu_right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();

		rightFragment = new GoodsDetailRightMenuFragment();
		t.replace(R.id.right_frame, rightFragment);
		mainFragment = new GoodsDetailFragment(this);
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	public void showRight() {
		mSlidingMenu.showRightView();
	}

}
