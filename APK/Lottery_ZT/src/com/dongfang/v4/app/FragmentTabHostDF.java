package com.dongfang.v4.app;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

import com.dongfang.utils.ULog;
import com.next.lottery.UserLRLoginActivity;
import com.next.lottery.utils.User;

/***
 * 重写FragmentTabHost
 * 
 * @author dongfang
 * 
 */
public class FragmentTabHostDF extends FragmentTabHost {
	private Context mContext;

	public FragmentTabHostDF(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	@Override
	public void setCurrentTab(int index) {
		if (4 == index && !User.isLogined(mContext)) {
			ULog.d("startIntent");
			mContext.startActivity(new Intent(mContext, UserLRLoginActivity.class));
			return;
		}
		super.setCurrentTab(index);
	}
}
