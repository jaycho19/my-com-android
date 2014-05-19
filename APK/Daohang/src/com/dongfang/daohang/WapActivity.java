package com.dongfang.daohang;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.dongfang.daohang.views.MyWebView;
import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WapActivity extends BaseActivity {

	@ViewInject(R.id.activity_wap_webview)
	private MyWebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wap);
		String url = TextUtils.isEmpty(getIntent().getStringExtra("ulr")) ? "http://douban.fm/" : getIntent()
				.getStringExtra("ulr");
		webView.loadUrl(url);

	}

	@OnClick(R.id.fragment_user_tv_edit)
	@Override
	public void onClick(View v) {
		finish();
	}

}
