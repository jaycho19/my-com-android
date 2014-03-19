package com.next.lottery.fragment;

import com.dongfang.views.MyImageView;
import com.next.lottery.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 *  首页 季节热卖 fragment
 * 
 * @author gfan
 * 
 */
public class HomeFrammentSeasonSaleFragment extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_season_sale, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
	}
}