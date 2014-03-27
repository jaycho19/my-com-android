package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.fragment.adapter.ShoppingCartAllAdapter;

public class ShoppingCartALLFragment extends BaseFragment {

	@ViewInject(R.id.fragment_shoppingcart_all_list)
	private ListView listView;

	private ShoppingCartAllAdapter allAdapter;
	private List<String> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(null != getArguments() && getArguments().containsKey("key")){
			ULog.d("key = " + getArguments().getInt("key"));
		}
		View view = inflater.inflate(R.layout.fragment_shoppingcart_all, container, false);
		ViewUtils.inject(this, view);
		list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		allAdapter = new ShoppingCartAllAdapter(getActivity(), list);
		listView.setAdapter(allAdapter);
		return view;
	}

	@Override
	public void onClick(View v) {}

}
