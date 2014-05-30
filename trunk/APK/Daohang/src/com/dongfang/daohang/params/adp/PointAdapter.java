package com.dongfang.daohang.params.adp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dongfang.daohang.R;

public class PointAdapter extends BaseAdapter {

	private List<com.dongfang.daohang.beans.Point> list;

	private LayoutInflater inflater;// 动态布局映射

	public PointAdapter(Context context, ArrayList<com.dongfang.daohang.beans.Point> list2) {
		this.list = list2;
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
	public View getView(int position, View convertView, ViewGroup parent) {

		if (null == convertView)
			convertView = inflater.inflate(R.layout.fragment_point_item, null);

		return convertView;
	}

}
