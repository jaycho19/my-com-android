package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.fragment.adapter.ShoppingCartAllAdapter;
import com.next.lottery.utils.Keys;

public class ShoppingCartALLFragment extends BaseFragment {

	@ViewInject(R.id.fragment_shoppingcart_all_list)
	private ListView				listView;
	@ViewInject(R.id.fragment_shoppingcart_all_checkbox)
	private CheckBox				checkBoxAll;
	@ViewInject(R.id.fragment_shoppingcarts_bottom_price_tv)
	private TextView allPriceTv;

	private ShoppingCartAllAdapter	allAdapter;
	private List<String>			list;

	@ViewInject(R.id.fragment_shipping_all_settle_account_tv)
	private TextView				settleAccountTv;	// 结算

	private int						selectNum;

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

		list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		allAdapter = new ShoppingCartAllAdapter(getActivity(), list, handler);
		listView.setAdapter(allAdapter);
		
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

	@Override
	public void onClick(View v) {}

}
