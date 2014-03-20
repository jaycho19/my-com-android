package com.next.lottery.fragment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.views.MyImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;

/**
 * 活动listView适配器
 * 
 * @author gfan
 * 
 */
@SuppressLint("ResourceAsColor")
public class TradeMarkListViewAdapter extends ArrayAdapter<String> {
	private int layout = 0;
	private Context context;

	public int selectPositon = -1;

	public TradeMarkListViewAdapter(Context context, int LayoutResourceId,
			ArrayList<String> interactiveList) {
		super(context, LayoutResourceId, interactiveList);

		this.layout = LayoutResourceId;
		this.context = context;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layout, null);
			ViewUtils.inject(this, convertView);
			holder.tvDescription = (TextView) convertView
					.findViewById(R.id.fragment_trade_mark_description_tv);
			holder.tvOpen = (TextView) convertView
					.findViewById(R.id.fragment_trade_mark_open_description_tv);
			holder.img = (ImageView) convertView
					.findViewById(R.id.fragment_trade_mark_img);
			convertView.setTag(holder);
		} else
			holder = (Holder) convertView.getTag();
		String tvInfo = this.getItem(position);
		holder.tvDescription.setText(tvInfo);
		if (position == selectPositon)
			holder.tvDescription.setMaxLines(100);
		else
			holder.tvDescription.setMaxLines(2);

		holder.tvOpen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (selectPositon == position)
					selectPositon = -1;
				else
					selectPositon = position;
				notifyDataSetChanged();
			}
		});

		return convertView;
	}

	class Holder {
		ImageView img;
		TextView tvDescription;
		TextView tvOpen;
	}
}
