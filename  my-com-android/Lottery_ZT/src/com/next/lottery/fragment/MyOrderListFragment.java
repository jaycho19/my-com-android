package com.next.lottery.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.MyOrderListActivity;
import com.next.lottery.R;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.OrderBean;
import com.next.lottery.beans.OrderBean.OrderEntity;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.adapter.MyOrderListViewAdapter;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.view.PullToRefreshView;
import com.next.lottery.view.PullToRefreshView.OnFooterRefreshListener;
import com.next.lottery.view.PullToRefreshView.OnHeaderRefreshListener;

/**
 * 我的订单
 * 
 * @author gfan
 * 
 */
public class MyOrderListFragment extends BaseFragment implements OnHeaderRefreshListener, OnFooterRefreshListener {

	@ViewInject(R.id.fragment_my_order_listview)
	private ListView listView;

	ArrayList<OrderEntity> data = new ArrayList<OrderBean.OrderEntity>();
	private ProgressDialog progDialog;

	@ViewInject(R.id.fragment_my_order_pull_to_refreshview)
	private PullToRefreshView mPullToRefreshView;
	private static int page = 1;
	private int sumNum = 0;
	private MyOrderListViewAdapter allAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_order_list_layout, container, false);
		ViewUtils.inject(this, view);
		initView(view);
		getMyOrder(page);
		return view;
	}

	private void initView(View view) {

		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
		// mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.disablePullDownRefresh();

	}

	protected void initialAdapter() {
		if (allAdapter == null) {
			allAdapter = new MyOrderListViewAdapter(getActivity(), data);
			listView.setAdapter(allAdapter);
		}
		else {
			allAdapter.notifyDataSetChanged();
		}
	}

	public void setOrderlist(ArrayList<ShopCartsInfo> orderlist) {
		// this.orderBean = orderlist;
		ULog.i(orderlist.toString());
	}

	@OnClick({ R.id.btn_buy_now })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	/* 获取订单 */
	private void getMyOrder(int page) {
		String url = HttpActions.GetMyOrderList(getActivity(), page);
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

				try {
					BaseEntity<OrderBean> bean = new Gson().fromJson(responseInfo.result,
							new TypeToken<BaseEntity<OrderBean>>() {}.getType());
					if (null != bean && bean.getCode() == 0) {
						data.addAll(bean.getInfo().getData());
						if (sumNum == 0)
							sumNum = bean.getInfo().getTotal();
						initialAdapter();

						mPullToRefreshView.onFooterRefreshComplete();
					}
					else {
						Toast.makeText(getActivity(), bean.getMsg(), Toast.LENGTH_LONG).show();
					}
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mPullToRefreshView.onFooterRefreshComplete();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
				Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
				mPullToRefreshView.onFooterRefreshComplete();
			}
		});

	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		ULog.i("onFooterRefresh");
		if (data.size() < sumNum) {
			getMyOrder(page++);
		}
		else {
			mPullToRefreshView.disablePullUpRefresh();
		}

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		ULog.i("onHeaderRefresh");

	}

}
