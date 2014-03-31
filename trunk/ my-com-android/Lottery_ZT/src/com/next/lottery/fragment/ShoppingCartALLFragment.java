package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.beans.BaseGateWayInterfaceEntity;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.adapter.ShoppingCartAllAdapter;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.utils.Keys;

public class ShoppingCartALLFragment extends BaseFragment {

	@ViewInject(R.id.fragment_shoppingcart_all_list)
	private ListView				listView;
	@ViewInject(R.id.fragment_shoppingcart_all_checkbox)
	private CheckBox				checkBoxAll;
	@ViewInject(R.id.fragment_shoppingcarts_bottom_price_tv)
	private TextView allPriceTv;

	private ShoppingCartAllAdapter	allAdapter;
	private ArrayList<ShopCartsInfo>			shopCartslist = new ArrayList<ShopCartsInfo>();

	@ViewInject(R.id.fragment_shipping_all_settle_account_tv)
	private TextView				settleAccountTv;	// 结算

	private int						selectNum;
	
	private ProgressDialog progDialog;

	private Handler					handler	= new Handler() {
												@Override
												public void handleMessage(Message msg) {
													// TODO Auto-generated
													// method stub
													super.handleMessage(msg);
													switch (msg.what) {
													case Keys.MSG_REFRESH_BUY_NUM_PLUS:
														int plusNum = 0;
														int allPrice=0;
														try {
															plusNum = Integer.parseInt((String) settleAccountTv
																	.getText()) + 1;
															
															allPrice = msg.arg1 + Integer.parseInt((String)allPriceTv.getText());
														} catch (NumberFormatException e) {
															e.printStackTrace();
															allPrice = msg.arg1;
														}
														ULog.i(msg.arg1+"");
														allPriceTv.setText(String.valueOf(allPrice));
														settleAccountTv.setText(plusNum > 0 ? String.valueOf(plusNum): "0");
														break;
													case Keys.MSG_REFRESH_BUY_NUM_REDUCE:
														int reduceNum = 0;
														int allPrice1=0;
														try {
															reduceNum = Integer.parseInt((String) settleAccountTv.getText()) - 1;
															allPrice1 = -msg.arg1 + Integer.parseInt((String)allPriceTv.getText());
														} catch (NumberFormatException e) {
															e.printStackTrace();
														}
														settleAccountTv.setText(reduceNum > 0 ? String.valueOf(reduceNum) : "0");
														allPriceTv.setText(String.valueOf(allPrice1<0?0:allPrice1));
														break;
													case Keys.MSG_REFRESH_BUY_NUM_ALL_SELECTED:
														settleAccountTv.setText("0");
														allPriceTv.setText("0");
														allAdapter.setAllSelected(1);
//														allAdapter.notifyDataSetInvalidated();
														allAdapter.notifyDataSetChanged();
														break;
													case Keys.MSG_REFRESH_BUY_NUM_ALL_UNSELECTED:
														allAdapter.setAllSelected(2);
														settleAccountTv.setText("0");
														allAdapter.notifyDataSetChanged();
														break;
													case Keys.MSG_REFRESH_BUY_NUM_ALL_GOODS_PRICE:
														allPriceTv.setText(msg.arg1+"");
//														allAdapter.notifyDataSetChanged();
														break;

													default:
														break;
													}
												}

											};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(null != getArguments() && getArguments().containsKey("key")){
			ULog.d("key = " + getArguments().getInt("key"));
		}
		View view = inflater.inflate(R.layout.fragment_shoppingcart_all, container, false);
		ViewUtils.inject(this, view);
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
		getShopCartList();
		
		checkBoxAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) 
					handler.sendEmptyMessage(Keys.MSG_REFRESH_BUY_NUM_ALL_SELECTED);
				else
					handler.sendEmptyMessage(Keys.MSG_REFRESH_BUY_NUM_ALL_UNSELECTED);
				
			}
		});

		return view;
	}
	
	private void initAdapter(){
//		if (null==allAdapter) {
			allAdapter = new ShoppingCartAllAdapter(getActivity(), shopCartslist, handler);
			listView.setAdapter(allAdapter);
//		}else{
//			allAdapter.notifyDataSetChanged();
//		}
	}

	private void getShopCartList() {
		String url = HttpActions.GetShopCartsList();
		ULog.d("addShopCarts url = " + url);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d(responseInfo.result);
				
				BaseGateWayInterfaceEntity<ArrayList<ShopCartsInfo>> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseGateWayInterfaceEntity<ArrayList<ShopCartsInfo>>>() {}.getType());
				if (null != bean && bean.getCode() == 0) {
					
					shopCartslist = bean.getInfo();
					initAdapter();
				}
				else {
					Toast.makeText(getActivity(), bean.getMsg(), Toast.LENGTH_LONG).show();
				}
			}
			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
			}
		});

	}

	@Override
	public void onClick(View v) {}

}
