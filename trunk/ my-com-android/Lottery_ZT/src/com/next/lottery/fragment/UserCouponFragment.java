package com.next.lottery.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.adapter.UserCouponAdapter;

/**
 * 我的优惠券
 * 
 * @author dongfang
 *
 */
public class UserCouponFragment extends BaseFragment {

	@ViewInject(R.id.activity_user_coupon_listview)
	private ListView lvCoupon;
	private UserCouponAdapter couponAdatAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_user_coupon, container, false);
		ViewUtils.inject(this, view);

		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++)
			list.add(Integer.toString(i));
		couponAdatAdapter = new UserCouponAdapter(getActivity(), list);
		lvCoupon.setAdapter(couponAdatAdapter);

		return view;
	}

	@Override
	public void onClick(View v) {}

}
