package com.dongfang.daohang.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongfang.daohang.MainDaohangActivity;
import com.dongfang.daohang.R;
import com.dongfang.daohang.WapActivity;
import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.dongfang.v4.app.MCaptureActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ActivityFragment extends BaseFragment {

	
	@ViewInject(R.id.top_bar_tv_title)
	private TextView title;
	@ViewInject(R.id.top_bar_btn_back)
	private View vBack;
	@ViewInject(R.id.top_bar_btn_qr)
	private View vQR;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_activity, container, false);
		ViewUtils.inject(this, view);
		
		title.setText("上海");
		vBack.setVisibility(View.INVISIBLE);
		vQR.setVisibility(View.VISIBLE);
		
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		StringBuilder sb = new StringBuilder();
		sb.append("requestCode = ").append(requestCode);
		sb.append("\n").append("resultCode = ").append(resultCode);
		if(null != data && data.hasExtra("result"))
			sb.append("\n").append("result = ").append(data.getStringExtra("result"));
		ULog.e(sb.toString());
		if (resultCode == Activity.RESULT_OK && requestCode == 0x00f0 && null != data && data.hasExtra("result")) {
			Intent intent = new Intent(getActivity(), MainDaohangActivity.class);
			intent.putExtras(data.getExtras());
			startActivity(intent);
		}
	}

	@OnClick({ R.id.fragment_activity_iv_ad, R.id.top_bar_btn_qr })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_bar_btn_qr:
			startActivityForResult(new Intent(getActivity(), MCaptureActivity.class), 0x00f0);
			break;
		case R.id.fragment_activity_iv_ad: {
			Intent intent = new Intent(getActivity(), WapActivity.class);
			intent.putExtra("url", "http://www.baidu.com");
			getActivity().startActivity(intent);
		}
			break;
		}

	}
}
