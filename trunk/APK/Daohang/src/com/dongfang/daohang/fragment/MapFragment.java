package com.dongfang.daohang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.daohang.R;
import com.dongfang.daohang.bridge.ProxyBridge;
import com.dongfang.daohang.params.ComParams;
import com.dongfang.daohang.views.MyWebView;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MapFragment extends BaseFragment {

	@ViewInject(R.id.my_webview_1)
	private MyWebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map, container, false);
		ViewUtils.inject(this, view);

		webView.addJavascriptInterface(new ProxyBridge(getActivity(), webView));
		webView.loadUrl(ComParams.BASE_URL);
		return view;
	}

	@Override
	public void onClick(View v) {}

}
