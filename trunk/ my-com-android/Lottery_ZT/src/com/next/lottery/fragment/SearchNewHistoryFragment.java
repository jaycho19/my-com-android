package com.next.lottery.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.beans.CategoryEntity;
import com.next.lottery.beans.SearchBaseBean;
import com.next.lottery.beans.SearchGoosBean;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.adapter.SearchGoodsResultListViewAdapter;
import com.next.lottery.fragment.adapter.SearchGoodsResutlGridViewAdapter;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.params.ComParams;

/**
 * 新搜索历史
 * 
 * @author gfan
 * 
 */
public class SearchNewHistoryFragment extends BaseFragment {
	@ViewInject(R.id.fragment_search_history_lv_gridView)
	private GridView		mGridView;
	@ViewInject(R.id.fragment_search_history_lv_listView)
	private ListView		mListView;

	private CategoryEntity	entity;
	private ProgressDialog	progDialog;
	private SearchBaseBean	bean;
	private SearchGoodsResutlGridViewAdapter mGridViewAdapter;
	private SearchGoodsResultListViewAdapter mListViewAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_history, container, false);
		ViewUtils.inject(this, view);
		initView(view);
		registerBoradcastReceiver();
		try {
			entity = (CategoryEntity) getArguments().get("values");
			getDataByCategoryId();
			ULog.i(entity.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	private void getDataByCategoryId() {
		new HttpUtils().send(HttpMethod.GET, HttpActions.SearchGoods(entity), new RequestCallBack<String>() {

			@Override
			public void onStart() {
				ULog.i(getRequestUrl());
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// CategoryBean
				bean = new Gson().fromJson(responseInfo.result, SearchBaseBean.class);
				ULog.d(bean.toString());
				progDialog.dismiss();
				setListViewAdapter(bean.getInfo());
//				setGridViewAdapter(bean.getInfo());
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
			}
		});
	}

	protected void setGridViewAdapter(SearchGoosBean searchGoosBean) {
		mListView.setVisibility(View.GONE);
		mGridView.setVisibility(View.VISIBLE);
//		if (mGridViewAdapter ==null) {
			mGridViewAdapter = new SearchGoodsResutlGridViewAdapter(getActivity(),
					R.layout.fragment_home_sale_champion_tr_item, searchGoosBean.getItems());
			mGridView.setAdapter(mGridViewAdapter);
			
//		}else
//		mGridViewAdapter.notifyDataSetChanged();

	}
	
	private void setListViewAdapter(SearchGoosBean searchGoosBean){
		
		mGridView.setVisibility(View.GONE);
		mListView.setVisibility(View.VISIBLE);
//		if (mListViewAdapter==null) {
			mListViewAdapter = new SearchGoodsResultListViewAdapter(getActivity(), searchGoosBean.getItems());
			mListView.setAdapter(mListViewAdapter);
//		}else
//			mListViewAdapter.notifyDataSetChanged();
	}

	private void initView(View view) {
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
	public void registerBoradcastReceiver() {// 注册广播
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ComParams.ACTION_CHANGE_SEARCH_FRAGMENT_ADAPTER);
		getActivity().registerReceiver(receiver, myIntentFilter);
	}
	
	private BroadcastReceiver	receiver	= new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			ULog.i("BroadcastReceiver");
			String action = intent.getAction();
			if (action.equals(ComParams.ACTION_CHANGE_SEARCH_FRAGMENT_ADAPTER)) {
				
				if (mListView.getVisibility() == View.VISIBLE) 
					setGridViewAdapter(bean.getInfo());
				else
					setListViewAdapter(bean.getInfo());
				
			}
		}
	};
}
