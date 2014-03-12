package com.next.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.MCaptureActivity;
import com.next.lottery.R;
import com.next.lottery.SearchAcitivity;

public class HomeFragment extends BaseFragment {
	@ViewInject(R.id.app_top_title_iv_rigth)
	private ImageView ivSearch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		ViewUtils.inject(view);

		// initView(view);
		view.findViewById(R.id.app_top_title_iv_left).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), MCaptureActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, 1);
			}
		});
		view.findViewById(R.id.app_top_title_iv_rigth).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SearchAcitivity.class));
			}
		});

		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

//	@OnClick({ R.id.app_top_title_iv_left, R.id.app_top_title_iv_rigth })
//	private void onClick(View v) {
//
//		ULog.d("v.id = " + v.getId());
//
//		switch (v.getId()) {
//		case R.id.app_top_title_iv_left:
//			break;
//		case R.id.app_top_title_iv_rigth:
//			startActivity(new Intent(getActivity(), SearchAcitivity.class));
//			break;
//		default:
//			break;
//		}
//	}

}
