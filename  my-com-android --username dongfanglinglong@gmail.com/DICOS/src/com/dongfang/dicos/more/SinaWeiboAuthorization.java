package com.dongfang.dicos.more;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dongfang.dicos.R;
import com.dongfang.dicos.sina.DialogError;
import com.dongfang.dicos.sina.SslError;
import com.dongfang.dicos.sina.UtilSina;
import com.dongfang.dicos.sina.Weibo;
import com.dongfang.dicos.sina.WeiboException;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class SinaWeiboAuthorization extends Activity implements OnClickListener {

	public static final String	tag	= "SinaWeiboAuthorization";

	/** 返回按钮 */
	private Button				bBack;

	/** 授权页面 */
	private WebView				wvSina;

	/** 授权页面载入进度 */

	private ProgressBar			pbSina;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_tongbu_sina);

		Intent intent = this.getIntent();
		String url = intent.getStringExtra(ComParams.SINA_AUTH_BY_ACTIVITY_INTENT_URL_NAME);

		if (TextUtils.isEmpty(url))
			finish();

		bBack = (Button) findViewById(R.id.button_setting_tongbu_sina_back);
		bBack.setOnClickListener(this);

		pbSina = (ProgressBar) findViewById(R.id.progressbar_setting_tongbu_sina);

		wvSina = (WebView) findViewById(R.id.webView_setting_tongbu_sina);
		wvSina.setVerticalScrollBarEnabled(false);
		wvSina.setHorizontalScrollBarEnabled(false);
		wvSina.getSettings().setJavaScriptEnabled(true);
		wvSina.setWebViewClient(new WeiboWebViewClient());

		wvSina.loadUrl(url);

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_setting_tongbu_sina_back:
			finish();
			break;
		}
	}

	/** 直接用访问用户设置url */
	private void handleRedirectUrl(WebView view, String url) {
		Bundle values = UtilSina.parseUrl(url);

		String error = values.getString("error");
		String error_code = values.getString("error_code");

		if (error == null && error_code == null) {
			Weibo.getInstance().getAuthOwnDialogListener().onComplete(values);
		} else if (error.equals("access_denied")) {
			// 用户或授权服务器拒绝授予数据访问权限
			Weibo.getInstance().getAuthOwnDialogListener().onCancel();
		} else {
			Weibo.getInstance().getAuthOwnDialogListener()
					.onWeiboException(new WeiboException(error, Integer.parseInt(error_code)));
		}
	}

	private class WeiboWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			ULog.d(tag, "Redirect URL: " + url);
			// 待后台增加对默认重定向地址的支持后修改下面的逻辑
			if (url.startsWith(ComParams.SINA_APP_URL)) {
				handleRedirectUrl(view, url);
				// WeiboDialog.this.dismiss();
				finish();
				return true;
			}
			// launch non-dialog URLs in a full browser
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			return true;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			Weibo.getInstance().getAuthOwnDialogListener().onError(new DialogError(description, errorCode, failingUrl));
			// WeiboDialog.this.dismiss();
			finish();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			ULog.d(tag, "onPageStarted URL: " + url);
			// google issue. shouldOverrideUrlLoading not executed
			if (url.startsWith(ComParams.SINA_APP_URL)) {
				handleRedirectUrl(view, url);
				view.stopLoading();
				// WeiboDialog.this.dismiss();
				finish();
				return;
			}
			super.onPageStarted(view, url, favicon);
			if (!pbSina.isShown())
				pbSina.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			ULog.d(tag, "onPageFinished URL: " + url);
			super.onPageFinished(view, url);
			pbSina.setVisibility(View.GONE);

			// mSpinner.dismiss();
			// mContent.setBackgroundColor(Color.TRANSPARENT);
			// webViewContainer.setBackgroundResource(R.drawable.dialog_bg);
			// mBtnClose.setVisibility(View.VISIBLE);
			// wvSina.setVisibility(View.VISIBLE);
		}

		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();
		}

	}

}
