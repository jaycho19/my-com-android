package com.next.lottery.fragment.adapter;

import java.util.ArrayList;

import android.content.Context;
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
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.next.lottery.R;
import com.next.lottery.beans.BaseGateWayInterfaceEntity;
import com.next.lottery.beans.OrderBean.OrderEntity;
import com.next.lottery.beans.SKUBean;
import com.next.lottery.beans.SKUEntity;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ShoppingSelectSKUDialog;
import com.next.lottery.listener.OnSkuResultListener;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.utils.ComParams;
import com.next.lottery.utils.Keys;
import com.next.lottery.utils.Util;

/**
 * 我的 订单适配器
 * 
 * @author gfan
 * 
 */
public class MyOrderListViewAdapter extends BaseAdapter {

	private ArrayList<OrderEntity>	list;
	private Context						context;

	public MyOrderListViewAdapter(Context context, ArrayList<OrderEntity> arrayList) {
		this.list = arrayList;
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
			view = LayoutInflater.from(context).inflate(R.layout.fragment_my_order_listview_item, null);
		}
		else {
			view.getTag();
		}
		item.initView(view, position);
		return view;
	}

	class Item implements View.OnClickListener {
		TextView orderNum;
		TextView orderStatus;
		TextView orderTime;
		TextView orderMoney;
		LinearLayout itemll ;

		private void initView(View view, int position) {
			OrderEntity entity = list.get(position);
			
			orderNum = (TextView)view.findViewById(R.id.fragment_my_order_number_tv);
			orderStatus = (TextView)view.findViewById(R.id.fragment_my_order_status_tv);
			itemll = (LinearLayout)view.findViewById(R.id.fragment_my_order_item_ll);
			orderTime = (TextView)view.findViewById(R.id.fragment_my_order_time_tv);
			orderMoney = (TextView)view.findViewById(R.id.fragment_my_order_money_tv);
			
			
			orderNum.setText(entity.getOrderNo());
			orderTime.setText("时间："+entity.getLastUpdateTime());
			orderMoney.setText("总额：￥"+entity.getPrice());
//			orderStatus.setText(entity.get)
			for (int i = 0; itemll.getChildCount()<2; i++) {
				View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_my_order_listview_item_item, null);
				MyImageView img = (MyImageView)itemView.findViewById(R.id.fragment_shoppingcart_all_adp_item_iv);
				TextView     tvNum = (TextView)itemView.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_number);
				img.setImage(entity.getItems().get(i).getItemImg());
				ULog.i("url-->"+entity.getItems().get(i).getItemImg());
				tvNum.setText("x"+entity.getItems().get(i).getCount());
				itemll.addView(itemView);
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}



	}


}
