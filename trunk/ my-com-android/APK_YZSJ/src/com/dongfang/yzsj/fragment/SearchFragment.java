package com.dongfang.yzsj.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.df.util.ULog;
import com.dongfang.utils.ACache;
import com.dongfang.view.CheckTextView;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.VODItem;
import com.dongfang.yzsj.params.ComParams;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 搜索
 * 
 * @author dongfang
 * 
 */
public class SearchFragment extends Fragment {

	public static final String TAG = "SearchFragment";

	private LinearLayout linearLayout_0, linearLayout_1, linearLayout_2; // 用于存放搜索频道

	private ArrayList<VODItem> listChannels = null; // 频道列表

	private com.dongfang.view.ProgressDialog progDialog;

	private LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		if (null != savedInstanceState && savedInstanceState.containsKey(ComParams.INTENT_SEARCH_CHANNELS)) {
			listChannels = savedInstanceState.getParcelableArrayList(ComParams.INTENT_SEARCH_CHANNELS);
		}
		initView(view);
		return view;
	}

	private void initView(View view) {
		progDialog = com.dongfang.view.ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		linearLayout_0 = (LinearLayout) view.findViewById(R.id.search_ll_0);
		linearLayout_1 = (LinearLayout) view.findViewById(R.id.search_ll_1);
		linearLayout_2 = (LinearLayout) view.findViewById(R.id.search_ll_2);

		if (null != listChannels && listChannels.size() > 1)
			initChannelItems();
	}

	@Override
	public void onResume() {
		super.onResume();
		ULog.d(TAG, "onResume");

		// 非异常中断进入时，bean为空，判断是否有缓存
		if (null == listChannels) {
			listChannels = new com.google.gson.Gson().fromJson(
					ACache.get(getActivity()).getAsString(ComParams.INTENT_SEARCH_CHANNELS),
					new TypeToken<List<VODItem>>() {}.getType());
		}

		// bean非空初始化数据
		if (null != listChannels) {
			initChannelItems();
		}
		else {
			// bean为空，网络请求数据，需对网络进行判断
			new HttpUtils().send(HttpRequest.HttpMethod.GET, ComParams.HTTP_VOD, new RequestCallBack<String>() {
				@Override
				public void onLoading(long total, long current) {
					ULog.d(TAG, "total = " + total + "; current = " + current);
				}

				@Override
				public void onSuccess(String result) {
					ULog.d(TAG, "onSuccess  --" + result);

					listChannels = new com.google.gson.Gson().fromJson(result,
							new TypeToken<List<VODItem>>() {}.getType());
					StringBuilder sb = new StringBuilder();
					for (int i = 0, length = listChannels.size(); i < length; i++)
						sb.append("vod ").append(i).append(" --> ").append(listChannels.get(i).toString());
					ULog.d(TAG, sb.toString());

					ACache.get(getActivity()).put(ComParams.INTENT_SEARCH_CHANNELS, result, 60 * 5);// 缓存数据

					initChannelItems();

					progDialog.dismiss();
				}

				@Override
				public void onStart() {
					ULog.i(TAG, "onStart");
					progDialog.show();

				}

				@Override
				public void onFailure(Throwable error, String msg) {
					progDialog.dismiss();
					ULog.i(TAG, "onFailure");
				}
			});
		}
	}

	/** 动态布局搜索频道 */
	private void initChannelItems() {
		int length = listChannels.size();
		ULog.d(TAG, " listChannels.size() " + length);
		for (int i = 0; i < Math.min(length, 5); i++) {
			CheckTextView view = (CheckTextView) inflater.inflate(R.layout.fragment_search_check_textview, null);
			view.setText(listChannels.get(i).getName());
			linearLayout_0.addView(view);
		}
		for (int i = 5; i < Math.min(length, 11); i++) {
			CheckTextView view = (CheckTextView) inflater.inflate(R.layout.fragment_search_check_textview, null);
			view.setText(listChannels.get(i).getName());
			linearLayout_1.addView(view);
		}
		for (int i = 11; i < Math.min(length, 15); i++) {
			CheckTextView view = (CheckTextView) inflater.inflate(R.layout.fragment_search_check_textview, null);
			view.setText(listChannels.get(i).getName());
			linearLayout_2.addView(view);
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (null != listChannels) {
			ULog.d(TAG, "listChannelItem.size = " + listChannels.size());
			outState.putParcelableArrayList(ComParams.INTENT_SEARCH_CHANNELS, listChannels);
		}
	}
}
