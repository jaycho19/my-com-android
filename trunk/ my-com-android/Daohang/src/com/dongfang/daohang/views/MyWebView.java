package com.dongfang.daohang.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

import com.dongfang.utils.ULog;

public class MyWebView extends WebView {
	private Context mContext;
	private MyWebViewClient myWebViewClient;

	/**
	 * 用户点击返回时认为当前页面加载完成。 复写stopLoading方法，设置webviewclient的超时状态位为false。
	 * 
	 */
	public void stopLoading() {
		super.stopLoading();
	}

	public MyWebView(Context context) {
		this(context, null);
	}

	public MyWebView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		Init();

	}

	/** 初始化网页设置 */
	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	private void Init() {
		ULog.d("Init()");
		this.getSettings().setPluginState(PluginState.ON);
		this.setVerticalScrollbarOverlay(true);
		this.getSettings().setJavaScriptEnabled(true);
		// this.getSettings().setRenderPriority(RenderPriority.HIGH); // 提高渲染的优先级,android后续后续版本不支持
		this.setFocusable(true);
		this.getSettings().setBuiltInZoomControls(true);// 显示缩放按钮设置
		this.getSettings().setSupportZoom(true); // //可以缩放设置
		// this.setInitialScale(100);
		myWebViewClient = new MyWebViewClient();
		this.setWebViewClient(myWebViewClient);
		this.setWebChromeClient(new WebChromeClient());
		// 现在部分手机上会出现点击搜索框页面放大的情况，
		// this.getSettings().setDefaultZoom(getZoomDensity(mContext));
		// this.setDownloadListener(new ExternalDownloadListener(mContext));

		/** 升级版本需要此代码，预装版本不需要此代码 */
		// this.setBackgroundColor(Color.TRANSPARENT);// 先设置背景色为transparent
		// this.setBackgroundResource(R.drawable.webview_backgound); // 设置背景图片

	}



	@Override
	public void loadUrl(String url) {
		ULog.d("url:" + url);
		String loadurl = url;
		// if (!TextUtils.isEmpty(loadurl) && !url.startsWith("javascript") && !url.startsWith("file") &&
		// !url.startsWith("rtsp://"))
		// if (loadurl.indexOf("?") != -1) {
		// loadurl = loadurl + "&" + HeaderKeys.UUID + "=" + ComParams.UUID;
		// } else {
		// loadurl = loadurl + "?" + HeaderKeys.UUID + "=" + ComParams.UUID;
		// }
		super.loadUrl(loadurl);
	}



	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		ULog.d("onScrollChanged,l:" + l + ",t:" + t + ",oldl:" + oldl + ",oldt:" + oldt);
		super.onScrollChanged(l, t, oldl, oldt);

	}

	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		if (isClickable()) {
			switch (evt.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				// if (null != progressbar && progressbar.isShown())
				// return true;
				break;
			}
		}

		ULog.d("onTouchEvent");
		return super.onTouchEvent(evt) || isClickable();

	}

	public WebSettings.ZoomDensity getZoomDensity(Context context) {
		int screenDensity = context.getResources().getDisplayMetrics().densityDpi;
		WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
		switch (screenDensity) {
		case DisplayMetrics.DENSITY_LOW:
			zoomDensity = WebSettings.ZoomDensity.CLOSE;
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			zoomDensity = WebSettings.ZoomDensity.MEDIUM;
			break;
		case DisplayMetrics.DENSITY_HIGH:
			zoomDensity = WebSettings.ZoomDensity.FAR;
			break;
		}
		return zoomDensity;
	}

	public void setWebViewFlipperinter(WebViewFlipperinter wvflipperinter) {
		webviewflipperinter = wvflipperinter;
	}

	public WebViewFlipperinter webviewflipperinter;

	public interface WebViewFlipperinter {
		public void showNextFlip();

		public void showPreviousFlip();

		// public void showMore();

	}
}
