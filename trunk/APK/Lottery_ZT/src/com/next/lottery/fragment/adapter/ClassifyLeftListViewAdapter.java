package com.next.lottery.fragment.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.beans.CategoryEntity;

/**
 * 分类左边适配器
 * 
 * @author gfan
 * 
 */
@SuppressLint("ResourceAsColor")
public class ClassifyLeftListViewAdapter extends ArrayAdapter<ArrayList<CategoryEntity>> {
	private int layout = 0;
	private Context context;
	@ViewInject(R.id.fragment_classify_left_tv)
	private TextView tv;
	@ViewInject(R.id.fragment_classify_left_arrow_icon)
	private ImageView img;
	private boolean IsRightShow;// 判断右边的listView是否显示，这边的item作颜色字体，右边的黑色选中按钮
	private int SelectPosition = -1;
	private ArrayList<CategoryEntity> lefttitles ;

	public ClassifyLeftListViewAdapter(Context context, int LayoutResourceId, ArrayList<CategoryEntity> lefttitles) {
		super(context, LayoutResourceId);
		this.layout = LayoutResourceId;
		this.lefttitles = lefttitles;
		this.context = context;
	}

	public void setIsRightShowAndPosition(boolean isShow, int position) {
		IsRightShow = isShow;
		SelectPosition = position;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lefttitles!=null ?lefttitles.size():0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layout, null);
		ViewUtils.inject(this, view);
		tv = (TextView) view.findViewById(R.id.fragment_classify_left_tv);
		img = (ImageView) view.findViewById(R.id.fragment_classify_left_arrow_icon);
		String tvInfo = this.lefttitles.get(position).getName();
		tv.setText(tvInfo);
		if (IsRightShow && SelectPosition == position) {
			img.setVisibility(View.VISIBLE);
		}
		else {
			img.setVisibility(View.GONE);
		}
		return view;
	}

	public int getSelectPosition() {
		return SelectPosition;
	}

	public void setSelectPosition(int selectPosition) {
		SelectPosition = selectPosition;
	}
}
