package com.next.lottery.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.next.lottery.EnsureOrderListActivity;
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.MyOrderListActivity;
import com.next.lottery.R;
import com.next.lottery.beans.BaseGateWayInterfaceEntity;
import com.next.lottery.beans.OrderBean;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.adapter.EnsureOrderListViewAdapter;
import com.next.lottery.fragment.adapter.MyOrderListViewAdapter;
import com.next.lottery.fragment.adapter.ShoppingCartAllAdapter;
import com.next.lottery.nets.HttpActions;

/**
 * 我的订单
 * 
 * @author gfan
 * 
 */
@SuppressLint("ValidFragment")
public class MyOrderListFragment extends BaseFragment implements OnClickListener {

	@ViewInject(R.id.fragment_my_order_listview)
	private ListView					listView;
	@ViewInject(R.id.app_top_title_tv_centre)
	private TextView					tvTitle;
	@ViewInject(R.id.app_top_title_iv_left)
	private TextView					tvBack;
	@ViewInject(R.id.app_top_title_iv_rigth)
	private TextView					tvRight;

	private OrderBean	orderBean	;
	private ProgressDialog				progDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_order_list_layout, container, false);
		ViewUtils.inject(this, view);
		initView();
		getMyOrder();
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		tvTitle.setText("我的订单");

		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

	}
	
	protected void initialAdapter() {
		// TODO Auto-generated method stub
		MyOrderListViewAdapter allAdapter = new MyOrderListViewAdapter(getActivity(), orderBean.getData());
		listView.setAdapter(allAdapter);
	}

	public void setOrderlist(ArrayList<ShopCartsInfo> orderlist) {
//		this.orderBean = orderlist;
		ULog.i(orderlist.toString());
	}

	@OnClick({ R.id.app_top_title_iv_left, R.id.app_top_title_iv_rigth, R.id.btn_buy_now })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			((MyOrderListActivity) getActivity()).finish();
			break;
		case R.id.app_top_title_iv_rigth:
			((MyOrderListActivity) getActivity()).showRight();
			break;
		default:
			break;
		}
	}

	/* 获取订单 */
	private void getMyOrder() {
		String url = HttpActions.GetMyOrderList();
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

				BaseGateWayInterfaceEntity<OrderBean> bean = new Gson().fromJson(responseInfo.result,
						new TypeToken<BaseGateWayInterfaceEntity<OrderBean>>() {}.getType());
				if (null != bean && bean.getCode() == 0) {
					orderBean = bean.getInfo();
					initialAdapter();
				}
				else {
					Toast.makeText(getActivity(), bean.getMsg(), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
				Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
			}
		});

	}

}
