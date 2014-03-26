package com.next.lottery.fragment;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.MainActivity;
import com.next.lottery.R;
import com.next.lottery.utils.ComParams;
import com.next.lottery.utils.Keys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class GoodsDetailRightMenuFragment extends Fragment implements
		OnClickListener {
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

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_goods_detail_right, null);
		ViewUtils.inject(this, view);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@OnClick({ R.id.fragment_goods_detail_right_home_tv,
			R.id.fragment_goods_detail_right_fenlei_tv,
			R.id.fragment_goods_detail_right_huodong_tv,
			R.id.fragment_goods_detail_right_gouwuche_tv,
			R.id.fragment_goods_detail_right_usercenter_tv })
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(ComParams.ACTION_UPDATE_MAINACTIVITY);// 发送刷新广播
		switch (v.getId()) {
		case R.id.fragment_goods_detail_right_home_tv:
			intent.putExtra(Keys.KEY_MAIN_ITEM, 0);

			break;
		case R.id.fragment_goods_detail_right_fenlei_tv:
			intent.putExtra(Keys.KEY_MAIN_ITEM, 1);

			break;
		case R.id.fragment_goods_detail_right_huodong_tv:
			intent.putExtra(Keys.KEY_MAIN_ITEM, 2);

			break;
		case R.id.fragment_goods_detail_right_gouwuche_tv:
			intent.putExtra(Keys.KEY_MAIN_ITEM, 3);

			break;
		case R.id.fragment_goods_detail_right_usercenter_tv:
			intent.putExtra(Keys.KEY_MAIN_ITEM, 4);

			break;

		default:
			break;
		}
		getActivity().sendBroadcast(intent);
		((GoodsDetailActivity) getActivity()).finish();

	}

}
