package com.next.lottery.fragment.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.next.lottery.R;
import com.next.lottery.SearchNewAcitivity;
import com.next.lottery.beans.CategoryEntity;

/**
 * 分类右边适配器
 * 
 * @author gfan
 * 
 */
public class ClassifyRightListViewAdapter extends ArrayAdapter<String> {
	private int layout = 0;
	private Context context;
	private ArrayList<CategoryEntity> arrayList ;

	public ClassifyRightListViewAdapter(Context context, int LayoutResourceId, ArrayList<CategoryEntity> arrayList) {
		super(context, LayoutResourceId);
		this.layout = LayoutResourceId;
		this.context = context;
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {
		return arrayList!=null? arrayList.size():0;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layout, null);
		TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
		tv_item.setText(arrayList.get(position).getName());
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ULog.i("onclick-->"+arrayList.get(position).getName());
				Intent intent = new Intent(context,SearchNewAcitivity.class);
				intent.putExtra("values", arrayList.get(position));
				context.startActivity(intent);
			}
		});

		return view;
	}
}
