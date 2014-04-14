package com.next.lottery.fragment.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongfang.views.MyImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.R;
import com.next.lottery.beans.CategoryEntity;
import com.next.lottery.beans.SearchGoosBean.SearchItem;

/**
 * 搜索商品 gridView 适配器
 * 
 * @author gfan
 * 
 */
@SuppressLint("ResourceAsColor")
public class SearchGoodsResutlGridViewAdapter extends ArrayAdapter<ArrayList<CategoryEntity>> {
	private int						layout	= 0;
	private Context					context;
	private ArrayList<SearchItem>	searchItemList;
	private LayoutInflater			inflater;

	public SearchGoodsResutlGridViewAdapter(Context context, int LayoutResourceId, ArrayList<SearchItem> lefttitles) {
		super(context, LayoutResourceId);
		this.layout = LayoutResourceId;
		this.searchItemList = lefttitles;
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return searchItemList != null ? searchItemList.size() : 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		}
		else {
			convertView = inflater.inflate(layout, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.fragment_home_item_title);
			holder.imgCover = (MyImageView) convertView.findViewById(R.id.fragment_home_item_pic);

			convertView.setTag(holder);
		}
		holder.tvTitle.setText(searchItemList.get(position).getTitle());
		holder.imgCover.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.icon_home_fragment_sale_champion_test));
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(context,GoodsDetailActivity.class);
			     intent.putExtra("id",searchItemList.get(position).getId());
			     context.startActivity(intent);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView	tvTitle;
		MyImageView	imgCover;
	}

}
