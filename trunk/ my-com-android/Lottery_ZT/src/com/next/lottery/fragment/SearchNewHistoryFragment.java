package com.next.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

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
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.R;
import com.next.lottery.beans.CategoryEntity;
import com.next.lottery.beans.SearchBaseBean;
import com.next.lottery.beans.SearchGoosBean;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.adapter.SearchGoodsResutlGridViewAdapter;
import com.next.lottery.nets.HttpActions;

/**
 * 新搜索历史
 * 
 * @author gfan
 * 
 */
public class SearchNewHistoryFragment extends BaseFragment  {
	@ViewInject(R.id.fragment_search_history_lv_gridView)
	private GridView		mGridView;
	@ViewInject(R.id.fragment_search_history_lv_listView)
	private GridView		mListView;

	private CategoryEntity	entity;
	private ProgressDialog	progDialog;
	private SearchBaseBean bean;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_history, container, false);
		ViewUtils.inject(this, view);
		initView(view);
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
				initGridViewAdapter(bean.getInfo());
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
			}
		});
	}

	protected void initGridViewAdapter(SearchGoosBean searchGoosBean) {
		// TODO Auto-generated method stub
		// if(leftAdapter==null){
		SearchGoodsResutlGridViewAdapter mGridViewAdapter = new SearchGoodsResutlGridViewAdapter(getActivity(),
				R.layout.fragment_home_sale_champion_tr_item, searchGoosBean.getItems());
		mGridView.setAdapter(mGridViewAdapter);
		// }else
		mGridViewAdapter.notifyDataSetChanged();
		
//		mGridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				 Intent intent = new Intent(getActivity(),GoodsDetailActivity.class);
//			     intent.putExtra("id",bean.getInfo().getItems().get(arg2).getId());
//			     getActivity().startActivity(intent);
//			}});
	}

	private void initView(View view) {
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
