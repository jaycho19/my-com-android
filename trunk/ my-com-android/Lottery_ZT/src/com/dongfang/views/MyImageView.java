package com.dongfang.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dongfang.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;
import com.next.lottery.R;

/**
 * 网络下载 图
 * 
 * @author ericzhxi
 * 
 */
public class MyImageView extends RelativeLayout {
	public static final String TAG = MyImageView.class.getSimpleName();

	private Context context;
	private ImageView imageView;
	BitmapUtils bitmapUtils = null;
	BitmapDisplayConfig bigPicDisplayConfig = null;

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyImageView(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		this.context = context;
		ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.my_imageview, null);
		this.setGravity(Gravity.CENTER);
		addView(imageView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * 
	 * @param url
	 */
	public void setImage(String url) {

		if (TextUtils.isEmpty(url)) {
			imageView.setBackgroundColor(0xF5F5F5);
		}
		else {
			if (bitmapUtils == null) {
				bitmapUtils = BitmapHelp.getBitmapUtils(context);
			}

			bitmapUtils.display(imageView, url);
		}

	}

	public void setImage(String url, int maxWidth, int maxHeight) {

		if (TextUtils.isEmpty(url)) {
			imageView.setBackgroundColor(0xF5F5F5);
		}
		else {
			if (bitmapUtils == null) {
				bitmapUtils = BitmapHelp.getBitmapUtils(context);
			}
			if (bigPicDisplayConfig == null) {
				bigPicDisplayConfig = BitmapHelp.getBitmapDisplayConfig();
				// bigPicDisplayConfig.setShowOriginal(true);
				bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
			}
			BitmapSize bSize = new BitmapSize();
			bSize.setWidth(maxWidth);
			bSize.setHeight(maxHeight);
			bigPicDisplayConfig.setBitmapMaxSize(bSize);

			bitmapUtils.display(imageView, url, bigPicDisplayConfig);
		}

	}

	public void setImageResource(int res) {
		imageView.setImageResource(res);
	}

	public void setImageBitmap(Bitmap bm) {
		imageView.setImageBitmap(bm);
	}

	/*
	 * public void setBitmapDisplayConfig(int width, int height) { if (bigPicDisplayConfig == null) {
	 * bigPicDisplayConfig = BitmapHelp.getBitmapDisplayConfig(); //bigPicDisplayConfig.setShowOriginal(true);
	 * bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565); } BitmapSize bSize = new BitmapSize();
	 * bSize.setWidth(width); bSize.setHeight(height); bigPicDisplayConfig.setBitmapMaxSize(bSize); }
	 */
}