package com.next.lottery.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.R;

/***
 * 购物车
 * 
 * @author dongfang
 * 
 */
public class ShoppingCartFragment extends BaseFragment {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHost fgtHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
		ViewUtils.inject(this, view);
		initTabhostItems(view, inflater);
		return view;
	}

	private void initTabhostItems(View view, LayoutInflater inflater) {
		fgtHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

		TextView tab1 = (TextView) inflater.inflate(R.layout.fragment_shoppingcart_tab, null);
		TextView tab2 = (TextView) inflater.inflate(R.layout.fragment_shoppingcart_tab, null);
		TextView tab3 = (TextView) inflater.inflate(R.layout.fragment_shoppingcart_tab, null);
		tab1.setText("全部商品");
		tab2.setText("降价商品");
		tab3.setText("库存紧张");

		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), ShoppingCartALLFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), ShoppingCartALLFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab3), ShoppingCartALLFragment.class, null);

		fgtHost.getTabWidget().setDividerDrawable(R.drawable.tab_focus_bar_left);
	}

	@OnClick({})
	public void onClick(View v) {
		ULog.d("id = " + v.getId());
		switch (v.getId()) {
		default:
			break;
		}
	}
}
