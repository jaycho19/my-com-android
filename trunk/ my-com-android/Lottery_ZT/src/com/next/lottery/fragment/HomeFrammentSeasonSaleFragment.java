package com.next.lottery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.next.lottery.R;
import com.next.lottery.listener.OnClickTypeListener;

/**
 * 首页 季节热卖 fragment
 * 
 * @author gfan
 * 
 */
public class HomeFrammentSeasonSaleFragment extends Fragment {
	private OnClickTypeListener onClickTypeListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_season_sale,
				container, false);
		initView(view);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickTypeListener.onClickType(new Bundle());
			}
		});
		return view;
	}

	private void initView(View view) {
	}

	public void setData(OnClickTypeListener onClickTypeListener) {
		this.onClickTypeListener = onClickTypeListener;
	}
}