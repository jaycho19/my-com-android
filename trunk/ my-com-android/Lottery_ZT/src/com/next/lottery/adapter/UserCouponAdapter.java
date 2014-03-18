package com.next.lottery.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.next.lottery.R;

/**
 * 购物车适配器
 * 
 * @author dongfang
 * 
 */
public class UserCouponAdapter extends BaseAdapter {

	private List<String> list;
	private Context context;

	public UserCouponAdapter(Context context, List<String> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Item item;
		if (view == null) {
			item = new Item();
			view = LayoutInflater.from(context).inflate(R.layout.activity_user_coupon_adp_item, null);
			item.initView(view);
		}
		else {
			view.getTag();
		}

		return view;
	}

	class Item implements View.OnClickListener {

		private void initView(View view) {

		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			default:
				break;
			}
		}

	}

}
