package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.UserSettingFragment;

/**
 * 系统设置
 * 
 * @author dongfang
 * 
 */
public class UserSettingActivity extends BaseSlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new UserSettingFragment());
		setTopTitle("系统设置");
	}

}
