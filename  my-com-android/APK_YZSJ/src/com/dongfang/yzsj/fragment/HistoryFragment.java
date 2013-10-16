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
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.view.PullToRefreshView;
import com.dongfang.view.PullToRefreshView.OnFooterRefreshListener;
import com.dongfang.view.PullToRefreshView.OnHeaderRefreshListener;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.HistoryBean;
import com.dongfang.yzsj.bean.Movie;
import com.dongfang.yzsj.fragment.adp.HistoryAdp;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;

/**
 * 播放历史
 * 
 * @author dongfang
 * 
 */
public class HistoryFragment extends Fragment {
	public static final String TAG = HistoryFragment.class.getSimpleName();
	/** 每次请求长度 */
	public static final int LIMIT = 10;
	/** 每次请求页数 */
	private int pageStart = 0;
	private com.dongfang.view.ProgressDialog progDialog;

	private List<Movie> listData; // 显示列表
	private ListView listView;
	private PullToRefreshView pulltoRefreshView;
	private HistoryAdp historyAdp;

	private int lastTotal;// 最近一次加载更多时返回的数据量

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_favorite, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		progDialog = com.dongfang.view.ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		listData = new ArrayList<Movie>();
		historyAdp = new HistoryAdp(getActivity(), listData);
		listView = (ListView) view.findViewById(R.id.favorite_listview);
		listView.setAdapter(historyAdp);
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

	@Override
	public void onStart() {
		ULog.d(TAG, "onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		ULog.d(TAG, "onResume");
		super.onResume();
		if (User.isLogined(getActivity()) && (null == listData || listData.size() < 1)) {
			getPlayHistory(0, LIMIT);
		}
	}

	@Override
	public void onPause() {
		ULog.d(TAG, "onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		ULog.d(TAG, "onStop");
		super.onStop();
	}

	@Override
	public void onDestroy() {
		ULog.d(TAG, "onDestroy");
		super.onDestroy();
	}

	/** 获取网络数据 */
	private void getPlayHistory(final int start, final int limit) {

		if (0 == start) {
			lastTotal = 0;
		}

		else if (start > 0 && limit > lastTotal) {
			Toast.makeText(getActivity(), "没有更多内容啦O(∩_∩)O", Toast.LENGTH_LONG).show();
			pulltoRefreshView.onFooterRefreshComplete();
			return;
		}

		// start = start < 0 ? 0 : start;
		// limit = limit < 10 ? 0 : limit;
		// limit = limit > 30 ? 30 : limit;

		StringBuilder url = new StringBuilder(ComParams.HTTP_HISTORY);
		url.append("start=").append(start * limit).append("&");
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

				HistoryBean bean = new com.google.gson.Gson().fromJson(result, HistoryBean.class);
				if (null == bean)
					return;

				lastTotal = bean.getListData().getObjs().size();

				listData.addAll(bean.getListData().getObjs());
				ULog.d(TAG, "list length = " + listData.size());
			}

			@Override
			public void onStart() {
				ULog.i(TAG, "RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
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
