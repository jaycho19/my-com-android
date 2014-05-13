package com.next.lottery.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.R;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.nets.HttpActions;

/***
 * 购物车
 * 
 * @author dongfang
 * 
 */
public class ShoppingCartFragment extends BaseFragment {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHost				fgtHost;

	private ArrayList<ShopCartsInfo>	shopCartslist	= new ArrayList<ShopCartsInfo>();
	private ProgressDialog				progDialog;
	private TextView					tab1, tab2, tab3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
		ViewUtils.inject(this, view);
		initTabhostItems(view, inflater);
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
		getShopCartList();
		return view;
	}

	private void initTabhostItems(View view, LayoutInflater inflater) {
		fgtHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

		tab1 = (TextView) inflater.inflate(R.layout.fragment_shoppingcart_tab, null);
		tab2 = (TextView) inflater.inflate(R.layout.fragment_shoppingcart_tab, null);
		tab3 = (TextView) inflater.inflate(R.layout.fragment_shoppingcart_tab, null);
		tab1.setText("全部商品");
		tab2.setText("降价商品");
		tab3.setText("库存紧张");

		// fgtHost.getTabWidget().setLeftStripDrawable(R.drawable.tab_focus_bar_left);
		// fgtHost.getTabWidget().setDividerDrawable(R.drawable.tab_focus_bar_left);

	}

	private void AddTab(ArrayList<ShopCartsInfo>	shopCartslist) {
		Bundle args = new Bundle();
		args.putInt("key", 1);
		args.putParcelableArrayList("shopCartslist", shopCartslist);
		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), ShoppingCartALLFragment.class, args);
		args = new Bundle();
		args.putInt("key", 2);
		args.putParcelableArrayList("shopCartslist", shopCartslist);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), ShoppingCartALLFragment.class, args);
		args = new Bundle();
		args.putInt("key", 3);
		args.putParcelableArrayList("shopCartslist", shopCartslist);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab3), ShoppingCartALLFragment.class, args);
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

				BaseEntity<ArrayList<ShopCartsInfo>> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseEntity<ArrayList<ShopCartsInfo>>>() {}.getType());
				if (null != bean && bean.getCode() == 0) {

					shopCartslist = bean.getInfo();
					AddTab(shopCartslist);
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

	@OnClick({})
	public void onClick(View v) {
		ULog.d("id = " + v.getId());
		switch (v.getId()) {
		default:
			break;
		}
	}
}
