package com.dongfang.yzsj.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.RequestCallBack;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.ACache;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.utils.Util;
import com.dongfang.view.CheckTextView;
import com.dongfang.view.PullToRefreshView;
import com.dongfang.view.PullToRefreshView.OnFooterRefreshListener;
import com.dongfang.view.PullToRefreshView.OnHeaderRefreshListener;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.Movie;
import com.dongfang.yzsj.bean.SeachBean;
import com.dongfang.yzsj.bean.VODItem;
import com.dongfang.yzsj.fragment.adp.TypeAdp;
import com.dongfang.yzsj.params.ComParams;
import com.google.gson.reflect.TypeToken;

/**
 * 搜索
 * 
 * @author dongfang
 * 
 */
public class SearchFragment extends Fragment {

	public static final String TAG = "SearchFragment";
	/** 每次请求长度 */
	public static final int LIMIT = 10;
	/** 每次请求页数 */
	private int pageStart = 0;

	private com.dongfang.view.ProgressDialog progDialog;
	private TextView tvSearch; // 搜索按钮
	private EditText etSearchBox; // 搜索按钮
	private CheckTextView ctvQanbu; // 全部按钮
	private LinearLayout linearLayout_0, linearLayout_1, linearLayout_2; // 用于存放搜索频道

	private Vector<String> selectChannels = null; // 保存选定的频道
	private ArrayList<VODItem> listChannels = null; // 频道列表
	private LayoutInflater inflater;

	private List<Movie> listData; // 显示列表
	private String searchValue; // 上一次的请求数据
	private TypeAdp searchAdp;
	private ListView listView;
	private PullToRefreshView pulltoRefreshView;

	private int lastTotal;// 最近一次加载更多时返回的数据量

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

		etSearchBox = (EditText) view.findViewById(R.id.search_et_input_box);
		tvSearch = (TextView) view.findViewById(R.id.search_btn);
		tvSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pageStart = 0;
				lastTotal = 0;
				// searchValue = "手机";
				searchValue = etSearchBox.getText().toString().trim();
				getSearchResult(searchValue, pageStart, LIMIT);
			}
		});

		searchAdp = new TypeAdp(getActivity(), "0");
		listData = new ArrayList<Movie>();
		searchAdp.setList(listData);
		listView = (ListView) view.findViewById(R.id.lv_search_result);
		listView.setAdapter(searchAdp);

		pulltoRefreshView = (PullToRefreshView) view.findViewById(R.id.ptrv_search_result);
		pulltoRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				listData.clear();
				getSearchResult(searchValue, 0, LIMIT);
			}
		});

		pulltoRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {
			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				getSearchResult(searchValue, pageStart, LIMIT);
			}
		});

		linearLayout_0 = (LinearLayout) view.findViewById(R.id.search_ll_0);
		linearLayout_1 = (LinearLayout) view.findViewById(R.id.search_ll_1);
		linearLayout_2 = (LinearLayout) view.findViewById(R.id.search_ll_2);

		ctvQanbu = (CheckTextView) view.findViewById(R.id.search_type_ctv_quanbu);
		// ctvQanbu.setChecked(true);
		ctvQanbu.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				selectChannels.removeAllElements(); // 清空
				if (isChecked) {
					for (VODItem vodItem : listChannels) {
						selectChannels.add(vodItem.getChannelId());
					}
				}

				buttonView.setChecked(isChecked);

				for (int i = 0, l = linearLayout_0.getChildCount(); i < l; i++) {
					((CheckTextView) linearLayout_0.getChildAt(i)).setChecked(isChecked);
				}
				for (int i = 0, l = linearLayout_1.getChildCount(); i < l; i++) {
					((CheckTextView) linearLayout_1.getChildAt(i)).setChecked(isChecked);
				}
				for (int i = 0, l = linearLayout_2.getChildCount(); i < l; i++) {
					((CheckTextView) linearLayout_2.getChildAt(i)).setChecked(isChecked);
				}
			}
		});

		selectChannels = new Vector<String>();

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
			ULog.d(TAG, ComParams.HTTP_VOD);
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
				public void onFailure(HttpException error, String msg) {
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

		if (length > 0) {
			linearLayout_0.removeAllViews();
			linearLayout_1.removeAllViews();
			linearLayout_2.removeAllViews();
		}
		
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-1, -2, 1) ;
		for (int i = 0; i < Math.min(length, 5); i++) {
			CheckTextView view = (CheckTextView) inflater.inflate(R.layout.fragment_search_check_textview, null);
			view.setLayoutParams(llp);
			view.setText(listChannels.get(i).getName());
			view.setOnCheckedChangeListener(new CheckedChangeListener(listChannels.get(i).getChannelId()));
			linearLayout_0.addView(view);
		}
		for (int i = 5; i < Math.min(length, 11); i++) {
			CheckTextView view = (CheckTextView) inflater.inflate(R.layout.fragment_search_check_textview, null);
			view.setLayoutParams(llp);
			view.setText(listChannels.get(i).getName());
			view.setOnCheckedChangeListener(new CheckedChangeListener(listChannels.get(i).getChannelId()));
			linearLayout_1.addView(view);
		}
		for (int i = 11; i < Math.min(length, 15); i++) {
			CheckTextView view = (CheckTextView) inflater.inflate(R.layout.fragment_search_check_textview, null);
			view.setLayoutParams(llp);
			view.setText(listChannels.get(i).getName());
			view.setOnCheckedChangeListener(new CheckedChangeListener(listChannels.get(i).getChannelId()));
			linearLayout_2.addView(view);
		}
	}

	/** 搜索频道点击按钮 */
	private class CheckedChangeListener implements android.widget.CompoundButton.OnCheckedChangeListener {
		private String channelId;

		public CheckedChangeListener(String channelId) {
			this.channelId = channelId;
		}

		@Override
		public void onCheckedChanged(CompoundButton paramCompoundButton, boolean param) {
			if (param) {
				if (!selectChannels.contains(channelId))
					selectChannels.add(channelId);
			}
			else {
				ctvQanbu.setChecked(false);
				selectChannels.remove(channelId);
			}
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

	private String getChannelsUrl() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0, l = selectChannels.size(); i < l; i++) {
			sb.append(selectChannels.get(i));
			if (i < (l - 1))
				sb.append(",");
		}

		return sb.toString();
	}

	/** 获取搜索结果 */
	private void getSearchResult(final String searchName, final int start, final int limit) {
		if (TextUtils.isEmpty(searchName)) {
			Toast.makeText(getActivity(), "请输入要搜索的内容", Toast.LENGTH_LONG).show();
			return;
		}
		// 已经加载了全部数据，不再进行数据获取
		else if (start > 0 && limit > lastTotal) {
			Toast.makeText(getActivity(), "没有更多内容啦O(∩_∩)O", Toast.LENGTH_LONG).show();
			pulltoRefreshView.onFooterRefreshComplete();
			return;
		}

		StringBuilder url = new StringBuilder();
		url.append(ComParams.HTTP_SEARCH);
		url.append("searchType=MEDIA_NAME&");
		url.append("start=").append(start * limit).append("&");
		url.append("limit=").append(limit).append("&");
		url.append("channelIds=").append(getChannelsUrl()).append("&");
		url.append("searchValue=").append(Util.toUTF8(searchName));

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

				SeachBean bean = new com.google.gson.Gson().fromJson(result, SeachBean.class);
				if (null == bean)
					return;

				ULog.d(TAG, bean.toString());
				if (TextUtils.isEmpty(searchValue) || !searchName.equals(searchValue)) {
					listData.clear();
				}

				lastTotal = bean.getListData().getTotal();

				listData.addAll(bean.getListData().getObjs());
				searchAdp.setChannelId(bean.getChannel().getChannelId());
				searchAdp.notifyDataSetChanged();
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
