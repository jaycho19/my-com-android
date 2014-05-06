package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.UserPWDModifyFragment;

/**
 * 用户密码修改界面
 * 
 * @author dongfang
 * 
 */
public class UserPassWordModifyActivity extends BaseSlidingMenuActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new UserPWDModifyFragment());
		setTopTitle("密码管理");

	}

}