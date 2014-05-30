package com.dongfang.daohang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.daohang.R;
import com.dongfang.daohang.views.MyWebView;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 楼层
 * 
 * @author dongfang
 *
 */
public class FloorFragment extends BaseFragment {
	@ViewInject(R.id.fragment_floor_myWebView)
	private MyWebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_floor, null);
		ViewUtils.inject(this, v);

		webView.loadUrl("http://211.149.200.227:30001/web/index.php?m=1&s=335&e=337");
		return v;
	}

	@Override
	public void onClick(View v) {}

}
