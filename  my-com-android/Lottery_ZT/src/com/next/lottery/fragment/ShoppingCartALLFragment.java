package com.next.lottery.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.EnsureOrderListActivity;
import com.next.lottery.R;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.adapter.ShoppingCartAllAdapter;
import com.next.lottery.utils.Keys;

@SuppressLint("ValidFragment")
public class ShoppingCartALLFragment extends BaseFragment {

	@ViewInject(R.id.fragment_shoppingcart_all_list)
	private ListView listView;
	@ViewInject(R.id.fragment_shoppingcart_all_checkbox)
	private CheckBox checkBoxAll;
	@ViewInject(R.id.fragment_shipping_all_settle_account_lin)
	private LinearLayout settleAccountLin;
	@ViewInject(R.id.fragment_shoppingcarts_bottom_price_tv)
	private TextView allPriceTv;

	private ShoppingCartAllAdapter allAdapter;
	private ArrayList<ShopCartsInfo> shopCartslist = new ArrayList<ShopCartsInfo>();

	@ViewInject(R.id.fragment_shipping_all_settle_account_tv)
	private TextView settleAccountTv; // 结算

	private int selectNum;

	private ProgressDialog progDialog;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Keys.MSG_REFRESH_BUY_NUM_PLUS:
				int plusNum = 0;
				float allPrice = 0;
				try {
					plusNum = Integer.parseInt((String) settleAccountTv.getText()) + 1;
					allPrice = msg.arg1/100 + Float.parseFloat((String) allPriceTv.getText());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					allPrice = msg.arg1;
				}
				ULog.i(msg.arg1 + "");
				allPriceTv.setText(String.valueOf(allPrice));
				settleAccountTv.setText(plusNum > 0 ? String.valueOf(plusNum) : "0");
				break;
			case Keys.MSG_REFRESH_BUY_NUM_REDUCE:
				int reduceNum = 0;
				float allPrice1 = 0;
				try {
					reduceNum = Integer.parseInt((String) settleAccountTv.getText()) - 1;
					allPrice1 = -msg.arg1/100 + Float.parseFloat((String) allPriceTv.getText());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				settleAccountTv.setText(reduceNum > 0 ? String.valueOf(reduceNum) : "0");
				allPriceTv.setText(String.valueOf(allPrice1 < 0 ? 0 : allPrice1));
				break;
			case Keys.MSG_REFRESH_BUY_NUM_ALL_SELECTED:
				allAdapter.setAllSelected(true);
				// allAdapter.notifyDataSetInvalidated();
				float allPrice2 = 0;
				for (int i = 0; i < shopCartslist.size(); i++) {
					shopCartslist.get(i).setSelected(true);
					allPrice2 += shopCartslist.get(i).getPrice()/100* shopCartslist.get(i).getCount();
				}
				allPriceTv.setText(String.valueOf(allPrice2));
				settleAccountTv.setText(String.valueOf(shopCartslist.size()));
				allAdapter.notifyDataSetChanged();

				break;
			case Keys.MSG_REFRESH_BUY_NUM_ALL_UNSELECTED:
				allAdapter.setAllSelected(false);
				for (int i = 0; i < shopCartslist.size(); i++) {
					shopCartslist.get(i).setSelected(false);
				}
				settleAccountTv.setText("0");
				allPriceTv.setText("0");
				allAdapter.notifyDataSetChanged();
				break;
			case Keys.MSG_REFRESH_BUY_NUM_ALL_GOODS_PRICE:
				allPriceTv.setText(msg.arg1 + "");
				// allAdapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.i("ShoppingCartALLFragment onCreateView");
		if (null != getArguments() && getArguments().containsKey("key")) {
			ULog.d("key = " + getArguments().getInt("key"));
		}
		if (null != getArguments() && getArguments().containsKey("shopCartslist")) {
			shopCartslist = getArguments().getParcelableArrayList("shopCartslist");
		}
		View view = inflater.inflate(R.layout.fragment_shoppingcart_all, container, false);
		ViewUtils.inject(this, view);
		initAdapter();

		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
		// getShopCartList();

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

	private void initAdapter() {
		allAdapter = new ShoppingCartAllAdapter(getActivity(), shopCartslist, handler);
		listView.setAdapter(allAdapter);

		/* 切换tab之后 合计总额刷新 */
		if (allAdapter != null) {
			int sum = 0;
			int plusNum = 0;
			for (int i = 0; i < shopCartslist.size(); i++) {
				if (shopCartslist.get(i).isSelected()) {
					sum += shopCartslist.get(i).getPrice();
					plusNum++;
				}
			}
			allPriceTv.setText(Integer.toString(sum));
			settleAccountTv.setText(plusNum > 0 ? String.valueOf(plusNum) : "0");

		}
	}

	@OnClick(R.id.fragment_shipping_all_settle_account_lin)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_shipping_all_settle_account_lin:
			ULog.i("结算");
			ArrayList<ShopCartsInfo> orderlist = new ArrayList<ShopCartsInfo>();
			for (ShopCartsInfo info : shopCartslist) {
				if (info.isSelected())
					orderlist.add(info);
			}
			if (orderlist.size() == 0) {
				Toast.makeText(getActivity(), "至少选择一件商品", Toast.LENGTH_SHORT).show();
			}
			else {
				Intent intent = new Intent(getActivity(), EnsureOrderListActivity.class);
				intent.putParcelableArrayListExtra("orderList", orderlist);
				startActivity(intent);
			}

			break;

		default:
			break;
		}

	}

	public void setData(ArrayList<ShopCartsInfo> list) {
		// TODO Auto-generated method stub
		this.shopCartslist = list;
	}
}
