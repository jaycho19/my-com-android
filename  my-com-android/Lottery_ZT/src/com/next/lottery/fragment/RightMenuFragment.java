package com.next.lottery.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.MainActivity;
import com.next.lottery.R;
import com.next.lottery.params.ComParams;
import com.next.lottery.utils.Keys;

public class RightMenuFragment extends BaseFragment {
	@ViewInject(R.id.fragment_goods_detail_right_home_tv)
	private TextView homeTv;
	@ViewInject(R.id.fragment_goods_detail_right_fenlei_tv)
	private TextView ClassifyTv;
	@ViewInject(R.id.fragment_goods_detail_right_huodong_tv)
	private TextView disCountTv;
	@ViewInject(R.id.fragment_goods_detail_right_gouwuche_tv)
	private TextView shoppingCartsTv;
	@ViewInject(R.id.fragment_goods_detail_right_usercenter_tv)
	private TextView userterTv;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_detail_right, null);
		ViewUtils.inject(this, view);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@OnClick({ R.id.fragment_goods_detail_right_home_tv, R.id.fragment_goods_detail_right_fenlei_tv,
			R.id.fragment_goods_detail_right_huodong_tv, R.id.fragment_goods_detail_right_gouwuche_tv,
			R.id.fragment_goods_detail_right_usercenter_tv })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_goods_detail_right_home_tv:
			MainActivity.changeTab = ComParams.MENU_TAB_HOME;
			break;
		case R.id.fragment_goods_detail_right_fenlei_tv:
			MainActivity.changeTab = ComParams.MENU_TAB_CLASSIFY;
			break;
		case R.id.fragment_goods_detail_right_huodong_tv:
			MainActivity.changeTab = ComParams.MENU_TAB_TRADEMARK;
			break;
		case R.id.fragment_goods_detail_right_gouwuche_tv:
			MainActivity.changeTab = ComParams.MENU_TAB_SHOPPINGCART;
			break;
		case R.id.fragment_goods_detail_right_usercenter_tv:
			MainActivity.changeTab = ComParams.MENU_TAB_USERCENTER;
			break;
		}
		getActivity().startActivity(new Intent(getActivity(), MainActivity.class));;
		getActivity().finish();

	}

}
