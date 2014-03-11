package com.next.lottery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.next.lottery.R;

/**
 * 搜索热词
 * 
 * @author dongfang
 * 
 */
public class SearchHotWordFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_hotword, container, false);
		// initView(view);
		return view;
	}
}
