package com.next.lottery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.next.lottery.R;

/**
 * 搜索历史
 * 
 * @author dongfang
 * 
 */
public class SearchHistoryFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_history, container, false);
		// initView(view);
		return view;
	}
}
