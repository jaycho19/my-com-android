package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.UserFavoriteNotificationsFragment;

/**
 * 
 * 收藏变动通知
 * 
 * @author dongfang
 * 
 */
public class UserFavoriteNotificationsActivity extends BaseSlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new UserFavoriteNotificationsFragment());
		setTopTitle("收藏变动通知");
	}

}
