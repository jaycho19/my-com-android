package com.next.lottery.fragment.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.views.MyImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.R;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.CollectionsBean;
import com.next.lottery.beans.CollectionsBean.CollectionsEnity;
import com.next.lottery.beans.GoodsBean;
import com.next.lottery.beans.SKUBean2;
import com.next.lottery.beans.SKUItem;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.beans.SkuList;
import com.next.lottery.db.bean.SkulistDbBean;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.dialog.ShoppingSelectSKUDialog;
import com.next.lottery.listener.OnSkuResultListener;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.utils.Keys;

/**
 * 收藏适配器
 * 
 * @author dongfang
 * 
 */
public class MyCollectionslistViewAdapter extends BaseAdapter {

	private ArrayList<CollectionsEnity>	list;
	private Context						context;

	public MyCollectionslistViewAdapter(Context context, ArrayList<CollectionsEnity> shopCartslist) {
		this.list = shopCartslist;
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
			view = LayoutInflater.from(context).inflate(R.layout.fragment_my_order_listview_item_item, null);
		}
		else {
			view.getTag();
		}
		item.initView(view, position);

		return view;
	}
	
	class Item implements View.OnClickListener {
		
		TextView title;
		TextView price;
		MyImageView myImageView;

		private void initView(View view, int position) {
			CollectionsEnity entity = list.get(position);
			title = (TextView)view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_title);
			myImageView=(MyImageView)view.findViewById(R.id.fragment_shoppingcart_all_adp_item_iv);
			title.setText(entity.getTitle());
			myImageView.setImage(entity.getPicUrl());
		
		}

		@Override
		public void onClick(View v) {
			
			
			
		}
		
	}



}
