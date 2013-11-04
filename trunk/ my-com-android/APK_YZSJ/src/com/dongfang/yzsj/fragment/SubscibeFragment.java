package com.dongfang.yzsj.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.RequestCallBack;
import com.dongfang.net.http.ResponseInfo;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.view.PullToRefreshView;
import com.dongfang.view.PullToRefreshView.OnFooterRefreshListener;
import com.dongfang.view.PullToRefreshView.OnHeaderRefreshListener;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.MyOrderBean;
import com.dongfang.yzsj.bean.OrderProduct;
import com.dongfang.yzsj.fragment.adp.OrderAdp;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;

/**
 * 我的订阅
 * 
 * @author dongfang
 * 
 */
public class SubscibeFragment extends Fragment {
	public static final String TAG = SubscibeFragment.class.getSimpleName();

	/** 每次请求长度 */
	public static final int LIMIT = 10;
	/** 每次请求页数 */
	private int pageStart = 0;
	private com.dongfang.view.ProgressDialog progDialog;

	private List<OrderProduct> listData; // 显示列表
	private ListView listView;
	private PullToRefreshView pulltoRefreshView;
	private OrderAdp orderAdp;

	private int lastTotal;// 最近一次加载更多时返回的数据量

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_favorite, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		progDialog = com.dongfang.view.ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		listData = new ArrayList<OrderProduct>();
		orderAdp = new OrderAdp(getActivity(), listData);
		listView = (ListView) view.findViewById(R.id.favorite_listview);
		listView.setAdapter(orderAdp);
		pulltoRefreshView = (PullToRefreshView) view.findViewById(R.id.favorite_PullToRefreshView);
		pulltoRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				listData.clear();
				getMyOrders(0, LIMIT);
			}
		});

		pulltoRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {

			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				getMyOrders(pageStart, LIMIT);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		if (1 > listData.size()) {
			getMyOrders(0, LIMIT);
		}
	}

	/** 获取网络数据 */
	private void getMyOrders(final int start, final int limit) {

		if (0 == start) {
			lastTotal = 0;
			listData.clear();
		}

		else if (start > 0 && limit > lastTotal) {
			Toast.makeText(getActivity(), "没有更多内容啦O(∩_∩)O", Toast.LENGTH_LONG).show();
			pulltoRefreshView.onFooterRefreshComplete();
			return;
		}

		// start = start < 0 ? 0 : start;
		// limit = limit < 10 ? 0 : limit;
		// limit = limit > 30 ? 30 : limit;

		StringBuilder url = new StringBuilder(ComParams.HTTP_MY_ORDER);
		url.append("start=").append(start * limit).append("&");
		url.append("limit=").append(limit).append("&");
		url.append("token=").append(User.getToken(getActivity())).append("&");
		url.append("userTelephone=").append(User.getPhone(getActivity()));

		ULog.i(url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				ULog.d("onSuccess  --" + responseInfo.result);
				progDialog.dismiss();
				pageStart = 1 + start;

				if (0 == start) {
					pulltoRefreshView.onHeaderRefreshComplete();
				}
				else {
					pulltoRefreshView.onFooterRefreshComplete();
				}

				MyOrderBean bean = new com.google.gson.Gson().fromJson(responseInfo.result, MyOrderBean.class);
				if (null == bean)
					return;

				ULog.d(bean.toString());

				lastTotal = bean.getListData().getObjs().size();

				listData.addAll(bean.getListData().getObjs());

				for (int i = listData.size() - 1; i > -1; i--) {
					if (listData.get(i).getStatus() == 0 || 13 == listData.get(i).getStatus()) {
						listData.remove(i);
					}
				}

				ULog.d("list length = " + listData.size());
			}

			@Override
			public void onStart() {
				ULog.i("RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.i("RequestCallBack.onFailure");
				progDialog.dismiss();

				if (0 == start) {
					pulltoRefreshView.onHeaderRefreshComplete();
				}
				else {
					pulltoRefreshView.onFooterRefreshComplete();
				}
			}
		});
	}
}
