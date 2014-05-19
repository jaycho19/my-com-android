package com.dongfang.daohang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.daohang.R;
import com.dongfang.daohang.WapActivity;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class EventsFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_events, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@OnClick({

	R.id.fragment_event_iv_ad_1, R.id.fragment_event_iv_ad_2, R.id.fragment_event_iv_kv,

	})
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), WapActivity.class);
		intent.putExtra("url", "http://www.baidu.com");
		getActivity().startActivity(intent);

	}
}
