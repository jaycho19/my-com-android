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

public class HomeFragment extends BaseFragment {

	@ViewInject(R.id.my_webview)
	private MyWebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		ViewUtils.inject(this, view);
		
		// MyWebView v = (MyWebView) rootView.findViewById(R.id.my_webview);

		// SVG svg;
		// try {
		// svg = SVGParser.getSVGFromAsset(getActivity().getAssets(), "test1.svg");
		// Picture picture = svg.getPicture();
		// ULog.d(picture.getHeight() + "");
		// // Drawable drawable = svg.createPictureDrawable();
		// v.setImageDrawable(new PictureDrawable(picture));
		// Bitmap bmp = v.getDrawingCache();
		// Matrix matrix = new Matrix();
		// matrix.postScale(0.05f, 0.05f);
		// Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, picture.getWidth(), picture.getHeight(), matrix, true);
		// v.setImageBitmap(newbm);
		// } catch (SVGParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

//		webView.loadUrl("http://www.baidu.com");
		webView.loadUrl("http://www.google.com");
		return view;
	}

	@Override
	public void onClick(View v) {}

}
