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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.next.lottery.R;
import com.next.lottery.beans.BaseGateWayInterfaceEntity;
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
 * 确认订单  订单适配器
 * 
 * @author gfan
 * 
 */
public class EnsureOrderListViewAdapter extends BaseAdapter {

	private ArrayList<ShopCartsInfo>	list;
	private Context						context;
	private boolean						isAllSelected;	// 1表示全选，2 表示全不选

	public EnsureOrderListViewAdapter(Context context, ArrayList<ShopCartsInfo> shopCartslist) {
		this.list = shopCartslist;
		this.context = context;

	}

	public void setAllSelected(boolean flag) {
		this.isAllSelected = flag;
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
			view = LayoutInflater.from(context).inflate(R.layout.fragment_ensure_order_listview_item, null);
		}
		else {
			view.getTag();
		}
		item.initView(view, position);
		return view;
	}

	class Item implements View.OnClickListener {

		ImageView		imageView;
		TextView		tvTitle;

		RelativeLayout	layoutShow;
		RelativeLayout	layoutEditSKU;

		TextView		tvSKU1Show;
		TextView		tvSKU2Show;
		TextView		tvPriceShow;
		TextView		tvNumberShow;

		TextView		tvShowPrice;

		ImageView		ivDel;
		EditText		etNumber;

		int				position;

		private void initView(View view, int position) {
			this.position = position;
			imageView = (ImageView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_iv);
			tvTitle = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_title);
			layoutShow = (RelativeLayout) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_rl);

			tvSKU1Show = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_sku1);
			tvSKU2Show = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_sku2);

			tvNumberShow = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_number);

			tvShowPrice = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_price);

			tvShowPrice.setText(list.get(position).getPrice());
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}



	}


}
