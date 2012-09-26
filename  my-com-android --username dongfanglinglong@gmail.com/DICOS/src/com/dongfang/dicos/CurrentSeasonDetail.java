package com.dongfang.dicos;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

	// private ImageShowView mImageShowView = null;
	// private double Scale = 1.0;

	public static final String	TAG			= "CurrentSeasonDetail";

	private Context				context;

	private GestureDetector		mGestureDetector;
	private ImageView			mImageView;
	private boolean				bigger		= true;

	private String				imgUrl;
	private String				imageName;

	private Drawable			drawable;
	private int					windowWidth	= 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currentseason_detail);
		context = this;
		mImageView = (ImageView) findViewById(R.id.imageview_cruuentseason_detail);
		mImageView.setOnTouchListener(this);
		mImageView.setLongClickable(true);

		if (null != getIntent() && getIntent().hasExtra("imgUrl")) {
			imgUrl = getIntent().getStringExtra("imgUrl");
		}

		imageName = TextUtils.isEmpty(imgUrl) ? "" : imgUrl.substring(imgUrl.lastIndexOf("/") + 1);

		String path = this.getCacheDir() + "/" + imageName + "e";

		if (!TextUtils.isEmpty(imgUrl) && new File(path).exists()) {
			setImgByPath(path);

			// imageView.setLayoutParams(new
			// FrameLayout.LayoutParams(windowWidth, windowWidth *
			// drawable.getIntrinsicHeight()
			// / drawable.getIntrinsicWidth()));
		}

		mGestureDetector = new GestureDetector(new SimpleOnGestureListener() {

			@Override
			public boolean onDoubleTap(MotionEvent e) {
				ULog.d(TAG, "onDoubleTap");
				ULog.i(TAG, "imageView.getWidth() = " + mImageView.getWidth());
				if (drawable.getIntrinsicWidth() > 0) {
					if (bigger) {
						mImageView.setLayoutParams(new FrameLayout.LayoutParams(drawable.getIntrinsicWidth(), drawable
								.getIntrinsicHeight()));
						mImageView.requestLayout();
					} else {

						mImageView.setLayoutParams(new FrameLayout.LayoutParams(Util.getWindowWidth(context), Util
								.getWindowWidth(context) * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth()));
						mImageView.requestLayout();
					}

					bigger = !bigger;
				}
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

		// mGestureDetector = new GestureDetector(this);
		// mGestureDetector.setOnDoubleTapListener(new
		// GestureDetector.OnDoubleTapListener() {
		//
		// @Override
		// public boolean onDoubleTap(MotionEvent e) {
		//
		// ULog.i(TAG, "imageView.getWidth() = " + imageView.getWidth());
		// if (drawable.getIntrinsicWidth() > 0) {
		// if (bigger) {
		// imageView.setLayoutParams(new
		// FrameLayout.LayoutParams(drawable.getIntrinsicWidth(), drawable
		// .getIntrinsicHeight()));
		// imageView.requestLayout();
		// } else {
		//
		// imageView.setLayoutParams(new
		// FrameLayout.LayoutParams(Util.getWindowWidth(context), Util
		// .getWindowWidth(context) * drawable.getIntrinsicHeight() /
		// drawable.getIntrinsicWidth()));
		// imageView.requestLayout();
		// }
		//
		// bigger = !bigger;
		// }
		// ULog.v("daming", "onDoubleTap");
		// return true;
		// }
		//
		// @Override
		// public boolean onDoubleTapEvent(MotionEvent e) {
		// // 双击时产生两次
		// ULog.v("daming", "onDoubleTapEvent");
		// return false;
		// }
		//
		// @Override
		// public boolean onSingleTapConfirmed(MotionEvent e) {
		// ULog.v("daming", "onSingleTapConfirmed");
		// return false;
		// }
		// });
	}

	public void setImgByPath(String path) {
		drawable = Drawable.createFromPath(path);
		mImageView.setBackgroundDrawable(Drawable.createFromPath(path));
		windowWidth = Util.getWindowWidth(this);
		mImageView.setLayoutParams(new FrameLayout.LayoutParams(windowWidth, windowWidth
				* drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth()));
	}

	//
	// @Override
	// public boolean onDown(MotionEvent e) {
	// return false;
	// }
	//
	// @Override
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// return false;
	// }
	//
	// @Override
	// public void onLongPress(MotionEvent e) {
	//
	// }
	//
	// @Override
	// public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	// float distanceY) {
	// return false;
	// }
	//
	// @Override
	// public void onShowPress(MotionEvent e) {
	//
	// }
	//
	// @Override
	// public boolean onSingleTapUp(MotionEvent e) {
	// return false;
	// }

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		boolean result = mGestureDetector.onTouchEvent(event);

		// if (!result) {
		// if (event.getAction() == MotionEvent.ACTION_UP) {
		//
		// }
		// result = super.onTouchEvent(event);
		// }

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