package com.next.lottery.fragment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.beans.SKUBean;
import com.next.lottery.beans.SKUEntity;
import com.next.lottery.dialog.ShoppingSelectSKUDialog;
import com.next.lottery.fragment.ClassifyFragment;

/**
 * 分类左边适配器
 * 
 * @author gfan
 * 
 */
@SuppressLint("ResourceAsColor")
public class ClassifyLeftListViewAdapter extends ArrayAdapter<String> {
	private int layout = 0;
	private Context context;
	@ViewInject(R.id.fragment_classify_left_tv)
	private TextView tv;
	@ViewInject(R.id.fragment_classify_left_arrow_icon)
	private ImageView img;
	private boolean IsRightShow;//判断右边的listView是否显示，这边的item作颜色字体，右边的黑色选中按钮
	private int SelectPosition = -1;
	public ClassifyLeftListViewAdapter(Context context, int LayoutResourceId,
			String[] recipes2) {
		super(context, LayoutResourceId, recipes2);
		this.layout = LayoutResourceId;
		this.context = context;
	}
	
	public void setIsRightShowAndPosition(boolean isShow,int position){
		IsRightShow = isShow;
		SelectPosition = position;
	}
	
	

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layout, null);
		ViewUtils.inject(this, view);
		tv = (TextView) view.findViewById(R.id.fragment_classify_left_tv);
		img = (ImageView)view.findViewById(R.id.fragment_classify_left_arrow_icon);
		String tvInfo = this.getItem(position);
		tv.setText(tvInfo);
		if (IsRightShow&&SelectPosition==position) {
//			tv.setTextColor(R.color.grey);
			img.setVisibility(View.VISIBLE);
		}else{
//			tv.setTextColor(R.color.black);
			img.setVisibility(View.GONE);
		}
		return view;
	}
}
