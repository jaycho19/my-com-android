package com.next.lottery;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.next.lottery.fragment.UserLRLoginFragment;

public class UserLRLoginActivity extends BaseSlidingMenuActivity {

	private UserLRLoginFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		mainFragment = new UserLRLoginFragment();
		t.replace(R.id.center_frame, mainFragment);
		t.commit();
	}

	@Override
	public void onClick(View v) {}

}
