package com.dongfang.daohang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongfang.daohang.R;
import com.dongfang.daohang.TakeMeActivity;
import com.dongfang.daohang.bridge.ProxyBridge;
import com.dongfang.daohang.params.ComParams;
import com.dongfang.daohang.views.MyWebView;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 楼层
 * 
 * @author dongfang
 *
 */
public class FloorFragment extends BaseFragment {
	@ViewInject(R.id.fragment_floor_myWebView)
	private MyWebView webView;

	@ViewInject(R.id.top_bar_tv_title)
	private TextView title;
	@ViewInject(R.id.top_bar_btn_back)
	private View vBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_floor, container, false);
		ViewUtils.inject(this, v);

		title.setText("楼层");
		vBack.setVisibility(View.INVISIBLE);
		ProxyBridge proxyBridge = new ProxyBridge(getActivity(), webView);
		webView.addJavascriptInterface(proxyBridge, "ProxyBridge");
		webView.loadUrl(ComParams.BASE_URL);
		proxyBridge.setPosition("m=1&s=318", 3);

		
		
		return v;
	}

	@OnClick({ R.id.fragment_floor_iv_dwq })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_floor_iv_dwq:
			startActivity(new Intent(getActivity(), TakeMeActivity.class));
			// RecordAlert.show(getActivity(), "33333").show();
			break;
		default:
			break;
		}

	}

}
