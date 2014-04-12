package com.next.lottery.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.next.lottery.R;

/**
 * 分类右边适配器
 * 
 * @author gfan
 * 
 */
public class ClassifyRightListViewAdapter extends ArrayAdapter<String> {
	private int layout = 0;
	private Context context;

	public ClassifyRightListViewAdapter(Context context, int LayoutResourceId, String[] recipes2) {
		super(context, LayoutResourceId, recipes2);
		this.layout = LayoutResourceId;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layout, null);
		TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
		tv_item.setText(this.getItem(position));

		return view;
	}
}
