package com.next.lottery;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.adapter.UserCouponAdapter;

/**
 * 设置
 * 
 * @author dongfang
 * 
 */

public class UserCouponActivity extends BaseActivity {

	@ViewInject(R.id.app_top_title_iv_left)
	private ImageView ivBack;
	@ViewInject(R.id.activity_user_coupon_listview)
	private ListView lvCoupon;

	private UserCouponAdapter couponAdatAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_coupon);
		ViewUtils.inject(this);

		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++)
			list.add(Integer.toString(i));
		couponAdatAdapter = new UserCouponAdapter(this, list);
		lvCoupon.setAdapter(couponAdatAdapter);
	}

	@Override
	@OnClick({ R.id.app_top_title_iv_left })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			finish();
			break;

		default:
			break;
		}

	}

}