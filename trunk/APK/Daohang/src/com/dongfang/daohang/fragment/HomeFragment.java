package com.dongfang.daohang.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongfang.daohang.MainDaohangActivity;
import com.dongfang.daohang.R;
import com.dongfang.daohang.views.MyWebView;
import com.dongfang.v4.app.BaseFragment;
import com.dongfang.v4.app.MCaptureActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HomeFragment extends BaseFragment {

	@ViewInject(R.id.my_webview)
	private MyWebView webView;

	@ViewInject(R.id.fragment_home_iv_qr)
	private ImageView ivQr;
	
	Contact contact;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		ViewUtils.inject(this, view);
		// webView.loadUrl("http://211.149.200.227:30001/web/index.php?m=1&s=335&e=337");
		// webView.loadUrl("http://www.google.com");
		// webView.loadUrl("http://www.google.com");
		webView.loadUrl("file:///android_asset/index.html");
		contact = new Contact();
		webView.addJavascriptInterface(contact, "contact");
		return view;
	}

	@OnClick({ R.id.fragment_home_iv_qr, R.id.activity_maini_top_bar_btn_left, })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_home_iv_qr:
			contact.showcontacts("[{\"name\":\"zxx\", \"amount\":\"8888\", \"phone\":\"18600012345\"},{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]");
			//  getActivity().startActivity(new Intent(getActivity(), MCaptureActivity.class));
			break;
		case R.id.activity_maini_top_bar_btn_left:
			getActivity().startActivity(new Intent(getActivity(), MainDaohangActivity.class));
			break;
		default:
			break;
		}
	}

	private final class Contact {
		
		
		// JavaScript调用此方法拨打电话
		public void call(String phone) {
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)));
		}

		// Html调用此方法传递数据
		public void showcontacts(String s ) {
			String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
			
			json = TextUtils.isEmpty(s) ? json : s;
			// 调用JS中的方法
			webView.loadUrl("javascript:show('" + json + "')");
		}
	}
}
