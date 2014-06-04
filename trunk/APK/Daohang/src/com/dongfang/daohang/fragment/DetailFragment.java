package com.dongfang.daohang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongfang.daohang.CarportActivity;
import com.dongfang.daohang.R;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DetailFragment extends BaseFragment {

	@ViewInject(R.id.fragment_detail_tv_around_carport)
	private TextView tvArroudCarport;

	@ViewInject(R.id.top_bar_tv_title)
	private TextView title;
	@ViewInject(R.id.top_bar_btn_back)
	private View vBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_detail, null);
		ViewUtils.inject(this, v);

		title.setText("详情");
		vBack.setVisibility(View.INVISIBLE);
		return v;
	}

	@OnClick({ R.id.fragment_detail_tv_around_carport })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_detail_tv_around_carport:
			getActivity().startActivity(new Intent(getActivity(), CarportActivity.class));
			break;

		default:
			break;
		}

	}

}
