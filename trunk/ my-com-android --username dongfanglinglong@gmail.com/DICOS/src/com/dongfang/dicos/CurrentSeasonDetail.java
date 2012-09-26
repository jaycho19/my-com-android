package com.dongfang.dicos;

import java.io.File;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 
 * 查看图片正式尺寸
 * 
 * @author dongfang
 * 
 */
public class CurrentSeasonDetail extends Activity implements OnTouchListener {
	public static final String	TAG			= "CurrentSeasonDetail";

	private GestureDetector		mGestureDetector;
	private ImageView			mImageView;
	private boolean				bigger		= true;

	private String				imgUrl;

	private Drawable			drawable;
	private int					windowWidth	= 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currentseason_detail);
		
		if (null != getIntent() && getIntent().hasExtra("imgUrl")) {
			imgUrl = getIntent().getStringExtra("imgUrl");
		}
		String imageName = TextUtils.isEmpty(imgUrl) ? "" : imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		String path = this.getCacheDir() + "/" + imageName + "e";
		
		
		mImageView = (ImageView) findViewById(R.id.imageview_cruuentseason_detail);
		mImageView.setOnTouchListener(this);
		mImageView.setLongClickable(true);

		if (!TextUtils.isEmpty(imgUrl) && new File(path).exists()) {
			setImgByPath(path);
		}

		mGestureDetector = new GestureDetector(new SimpleOnGestureListener() {

			@Override
			public boolean onDoubleTap(MotionEvent e) {
				ULog.d(TAG, "onDoubleTap");
				ULog.i(TAG, "windowWidth = " + windowWidth + ", width = " + drawable.getIntrinsicWidth() + ",higth = " + drawable.getIntrinsicHeight());
				if (drawable.getIntrinsicWidth() > 0) {
					if (bigger) {
						mImageView.setLayoutParams(new LinearLayout.LayoutParams(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()));
						// mImageView.requestLayout();
					} else {
						ULog.i(TAG, "windowWidth = " + windowWidth + ", higth = " + windowWidth * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth());
						mImageView.setLayoutParams(new LinearLayout.LayoutParams(windowWidth, windowWidth * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth()));
						// mImageView.requestLayout();
					}

					bigger = !bigger;
				}
				
				ULog.i(TAG, "windowWidth = " + windowWidth + ", width = " + drawable.getIntrinsicWidth() + ",higth = " + drawable.getIntrinsicHeight());

				ULog.v("daming", "onDoubleTap");
				return true;
			}

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				ULog.d(TAG, "onSingleTapConfirmed");
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				ULog.d(TAG, "onLongPress");
			}

		});
	}

	public void setImgByPath(String path) {
		drawable = Drawable.createFromPath(path);
		mImageView.setBackgroundDrawable(Drawable.createFromPath(path));
		windowWidth = Util.getWindowWidth(this);
		mImageView.setLayoutParams(new LinearLayout.LayoutParams(windowWidth, windowWidth * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth()));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		boolean result = mGestureDetector.onTouchEvent(event);
		return result ? result : super.onTouchEvent(event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ULog.d(TAG, v.getId() + "," + event.toString());
		switch (v.getId()) {
		case R.id.imageview_cruuentseason_detail:
			return onTouchEvent(event);
		}
		return false;
	}

}