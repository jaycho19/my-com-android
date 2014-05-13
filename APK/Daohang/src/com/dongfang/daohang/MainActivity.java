package com.dongfang.daohang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.daohang.views.MyWebView;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			MyWebView v = (MyWebView) rootView.findViewById(R.id.my_webview);

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

			v.loadUrl("file:///android_asset/test1.svg");
			return rootView;
		}
	}

}
