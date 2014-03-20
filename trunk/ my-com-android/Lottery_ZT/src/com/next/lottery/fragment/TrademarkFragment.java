package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.fragment.adapter.ClassifyLeftListViewAdapter;
import com.next.lottery.fragment.adapter.TradeMarkListViewAdapter;
import com.next.lottery.view.MyHorizontalScrollView;
import com.next.lottery.view.MyHorizontalScrollView.ItemClickListener;

public class TrademarkFragment extends BaseFragment {
	@ViewInject(R.id.fragment_trade_mark_horizontal_scroview)
	private MyHorizontalScrollView  mScrollView;
	@ViewInject(R.id.fragment_trade_mark_listview)
	private ListView mlistView;
	
	
	private List<ItemClickListener>		itemClickList				= new ArrayList<MyHorizontalScrollView.ItemClickListener>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_trademark, container, false);
		ViewUtils.inject(this, view);
		initHorizontalView();
		initInteractiveListView();
		return view;
	}

	@SuppressLint("ResourceAsColor")
	private void initHorizontalView() {
		/*************水平listview的测试数据*******/
		ArrayList<String> horizon = new ArrayList<String>();
		horizon.add("曼妮芬");horizon.add("南极人");horizon.add("黛安芬");horizon.add("安莉芳");
		horizon.add("张益达");horizon.add("曾小贤");horizon.add("吕小布");horizon.add("唐悠悠");
		horizon.add("以上这些人");horizon.add("我都不清楚");
		/*************水平listview的测试数据*******/
		mScrollView.setItemList(horizon);
		mScrollView.setTextColor(R.color.black);
		initHSViewClickListenerList(horizon);
		mScrollView.setItemClickListener(itemClickList);
		
	}
	
	private void initInteractiveListView() {
		// TODO Auto-generated method stub
		ArrayList<String> interactiveList = new ArrayList<String>();
		interactiveList.add("曼妮芬：2013-2014年 夏季新款XX蕾丝文胸八折优惠，第一次购买还有三重优惠。\n1.购买两件，第二件半价 .\n2.单价超过1000元，立减200元。\n3.仰天长啸，一声一块钱");
		interactiveList.add("jackjones：2015-2016年 冬季新款西装十一折优惠，第一次购买还有三重优惠。\n1.购买两件，第二件半价 .\n2.单价超过1000元，立减200元。\n3.仰天长啸，一声一块钱");
		interactiveList.add("selected：2019-2026年 冬季新款西装十一折优惠，第一次购买还有三重优惠。\n1.购买两件，第二件半价 .\n2.单价超过1000元，立减200元。\n3.仰天长啸，一声一块钱");
		TradeMarkListViewAdapter madapter = new TradeMarkListViewAdapter(
				getActivity(), R.layout.fragment_trademark_listvew_item, interactiveList);
		mlistView.setAdapter(madapter);
		
	}
	
	// 初始化导航栏监听
		public List<ItemClickListener> initHSViewClickListenerList(ArrayList<String> horizon) {
			for (int i = 0; i < horizon.size(); i++) {
				ItemIntent itemIntent = new ItemIntent();
				itemClickList.add(itemIntent);
			}
			return itemClickList;
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	class ItemIntent implements ItemClickListener {
		@Override
		public void onClick(View v) {
			System.gc();
			
			ULog.i("press: no."+v.getId()+"  button");
		}
	}
}

