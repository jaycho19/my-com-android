package com.dongfang.daohang.params.adp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.daohang.R;

public class ShopsListAdapter extends BaseAdapter {

	private List<com.dongfang.daohang.beans.AreaBean> list;

	private LayoutInflater inflater;// 动态布局映射

	public ShopsListAdapter(Context context, List<com.dongfang.daohang.beans.AreaBean> list) {
		this.list = list;
		this.inflater = LayoutInflater.from(context);
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
	public View getView(int position, View v, ViewGroup parent) {
		if (null == v)
			v = inflater.inflate(R.layout.shops_adapter_item, null);

		ImageView logo = (ImageView) v.findViewById(R.id.shops_adapter_item_iv_logo);
		TextView floor = (TextView) v.findViewById(R.id.shops_adapter_item_tv_floor);
		TextView name = (TextView) v.findViewById(R.id.shops_adapter_item_tv_name);
		TextView num = (TextView) v.findViewById(R.id.shops_adapter_item_tv_num);

		floor.setText(list.get(position).getPlaceName());;
		name.setText(list.get(position).getAreaName());
		num.setText(list.get(position).getAreaId());

		return v;
	}

}
