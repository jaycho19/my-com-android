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

import com.df.util.ULog;
import com.dongfang.view.PullToRefreshView;
import com.dongfang.view.PullToRefreshView.OnFooterRefreshListener;
import com.dongfang.view.PullToRefreshView.OnHeaderRefreshListener;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.HistoryBean;
import com.dongfang.yzsj.bean.OrderProduct;
import com.dongfang.yzsj.fragment.adp.OrderAdp;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

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
				getPlayHistory(0, LIMIT);
			}
		});

		pulltoRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {

			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				getPlayHistory(pageStart, LIMIT);
			}
		});
	}

	public List<OrderProduct> getListData() {
		return listData;
	}

	public void setListData(List<OrderProduct> listData) {
		this.listData = listData;
	}

	/** 获取网络数据 */
	private void getPlayHistory(final int start, final int limit) {

		if (0 == start) {
			lastTotal = 0;
		}

		else if (start > 0 && limit > lastTotal) {
			Toast.makeText(getActivity(), "没有更多内容啦O(∩_∩)O", Toast.LENGTH_LONG).show();
			return;
		}

		// start = start < 0 ? 0 : start;
		// limit = limit < 10 ? 0 : limit;
		// limit = limit > 30 ? 30 : limit;

		StringBuilder url = new StringBuilder(ComParams.HTTP_HISTORY);
		url.append("start=").append(start).append("&");
		url.append("limit=").append(limit).append("&");
		url.append("token=").append(User.getToken(getActivity())).append("&");
		url.append("userTelephone=").append(User.getPhone(getActivity()));

		ULog.i(TAG, url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current) {
				ULog.d(TAG, "RequestCallBack.onLoading total = " + total + "; current = " + current);
			}

			@Override
			public void onSuccess(String result) {
				ULog.d(TAG, "onSuccess  --" + result);
				progDialog.dismiss();
				pageStart = 1 + start;

				if (0 == start) {
					pulltoRefreshView.onHeaderRefreshComplete();
				}
				else {
					pulltoRefreshView.onFooterRefreshComplete();
				}

				// HistoryBean bean = new com.google.gson.Gson().fromJson(result, HistoryBean.class);
				// if (null == bean)
				// return;
				//
				// lastTotal = bean.getListData().getObjs().size();
				//
				// listData.addAll(bean.getListData().getObjs());
				// ULog.d(TAG, "list length = " + listData.size());
			}

			@Override
			public void onStart() {
				ULog.i(TAG, "RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(Throwable error, String msg) {
				ULog.i(TAG, "RequestCallBack.onFailure");
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
