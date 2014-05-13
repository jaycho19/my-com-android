package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dongfang.v4.app.BaseActivity;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.view.SlidingMenu;

/**
 * 
 * @author dongfang
 * 
 */
public class BaseSlidingMenuActivity extends BaseActivity {

	private SlidingMenu mSlidingMenu;
	private RightMenuFragment rightFragment;

	private TextView topTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_menu_main);

		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mSlidingMenu.setRightView(getLayoutInflater().inflate(R.layout.sliding_menu_right_frame, null));

		View centerView = getLayoutInflater().inflate(R.layout.center_frame, null);
		topTitle = (TextView) centerView.findViewById(R.id.app_top_title_tv_centre);
		centerView.findViewById(R.id.app_top_title_iv_left).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		centerView.findViewById(R.id.app_top_title_iv_rigth).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSlidingMenu.showRightView();
			}
		});

		mSlidingMenu.setCenterView(centerView);
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		rightFragment = new RightMenuFragment();
		t.replace(R.id.right_frame, rightFragment);
		t.commit();
	}

	// 设置主Fragment
	protected void setCenterFragment(android.support.v4.app.Fragment fragment) {
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.add(R.id.center_frame, fragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {}

	// 设置标题
	public void setTopTitle(String title) {
		topTitle.setText(title);
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
