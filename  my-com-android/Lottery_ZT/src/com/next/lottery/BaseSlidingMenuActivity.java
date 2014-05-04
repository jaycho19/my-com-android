package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.view.SlidingMenu;

public class BaseSlidingMenuActivity extends BaseActivity {

	private SlidingMenu mSlidingMenu;
	private RightMenuFragment rightFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_menu_main);
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mSlidingMenu.setRightView(getLayoutInflater().inflate(R.layout.sliding_menu_right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(R.layout.center_frame, null));

		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		rightFragment = new RightMenuFragment();
		t.replace(R.id.right_frame, rightFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {}

	public void showRight() {
		mSlidingMenu.showRightView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mSlidingMenu.isRightViewShow()) {
			mSlidingMenu.showRightView();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
}
