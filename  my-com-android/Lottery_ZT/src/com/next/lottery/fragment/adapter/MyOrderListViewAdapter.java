package com.next.lottery.fragment.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.next.lottery.R;
import com.next.lottery.beans.OrderBean.OrderEntity;

/**
 * 我的 订单适配器
 * 
 * @author gfan
 * 
 */
public class MyOrderListViewAdapter extends BaseAdapter {

	private ArrayList<OrderEntity> list;
	private Context context;

	public MyOrderListViewAdapter(Context context, ArrayList<OrderEntity> arrayList) {
		this.list = arrayList;
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
		Item item = new Item();
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.fragment_my_order_listview_item, null);
		}
		else {
			view.getTag();
		}
		item.initView(view, position);
		return view;
	}

	class Item implements View.OnClickListener {
		TextView orderNum;
		TextView orderStatus;
		TextView orderTime;
		TextView orderMoney;
		LinearLayout itemll;

		private void initView(View view, int position) {
			OrderEntity entity = list.get(position);

			orderNum = (TextView) view.findViewById(R.id.fragment_my_order_number_tv);
			orderStatus = (TextView) view.findViewById(R.id.fragment_my_order_status_tv);
			itemll = (LinearLayout) view.findViewById(R.id.fragment_my_order_item_ll);
			orderTime = (TextView) view.findViewById(R.id.fragment_my_order_time_tv);
			orderMoney = (TextView) view.findViewById(R.id.fragment_my_order_money_tv);

			orderNum.setText(entity.getOrderNo());
			orderTime.setText("时间：" + entity.getLastUpdateTime());
			orderMoney.setText("总额：￥" + entity.getPrice());
			// orderStatus.setText(entity.get)
			for (int i = 0; itemll.getChildCount() < 2; i++) {
				View v = LayoutInflater.from(context).inflate(R.layout.fragment_my_order_listview_item_item, null);
				// MyImageView img = (MyImageView) v.findViewById(R.id.fragment_shoppingcart_all_adp_item_iv);
				// TextView tvNum = (TextView) v.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_number);
				// img.setImage(entity.getItems().get(i).getItemImg());
				// ULog.i("url-->" + entity.getItems().get(i).getItemImg());
				// tvNum.setText("x"+entity.getItems().get(i).getCount());
				itemll.addView(v);
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}

}
