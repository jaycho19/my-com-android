package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.fragment.RightMenuFragment;
import com.next.lottery.fragment.UserPWDModifyFragment;
import com.next.lottery.view.SlidingMenu;

/**
 * 用户密码修改界面
 * 
 * @author gfan
 * 
 */
public class UserPassWordModifyActivity extends BaseSlidingMenuActivity {
	private UserPWDModifyFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
		
	}

	private void init() {

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

		mainFragment = new UserPWDModifyFragment();
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
}