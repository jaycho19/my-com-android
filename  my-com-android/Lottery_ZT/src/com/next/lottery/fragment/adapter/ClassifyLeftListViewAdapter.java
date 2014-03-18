package com.next.lottery.fragment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.next.lottery.R;
import com.next.lottery.beans.SKUBean;
import com.next.lottery.beans.SKUEntity;
import com.next.lottery.dialog.ShoppingSelectSKUDialog;

/**
 * 分类左边适配器
 * 
 * @author gfan
 * 
 */
public class ClassifyLeftListViewAdapter  extends ArrayAdapter<String> {
	private int layout = 0;
	private Context context;
	public ClassifyLeftListViewAdapter(Context context, int LayoutResourceId,
			String[] recipes2) {
		super(context, LayoutResourceId, recipes2);
		this.layout = LayoutResourceId;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layout,
				null);
		TextView tv_definition = (TextView) view
				.findViewById(R.id.tv_definition);

		String definition = this.getItem(position);
		Log.i("lottery", definition);
		tv_definition.setText(definition);

		return view;
	}
}
