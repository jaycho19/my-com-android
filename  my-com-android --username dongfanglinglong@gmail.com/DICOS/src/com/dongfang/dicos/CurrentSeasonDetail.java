package com.dongfang.dicos;

import java.io.File;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * 
 * 查看图片正式尺寸
 * 
 * @author dongfang
 * 
 */
public class CurrentSeasonDetail extends Activity // implements
// OnGestureListener
{

	// private ImageShowView mImageShowView = null;
	// private double Scale = 1.0;

	public static final String	TAG	= "CurrentSeasonDetail";

	// private Context context;

	// private GestureDetector gestureScanner;
	private ImageView			imageView;
	// private boolean bigger = true;

	private String				imgUrl;
	private String				imageName;

	// private Drawable drawable;
	// private int windowWidth = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currentseason_detail);
		// context = this;
		imageView = (ImageView) findViewById(R.id.imageview_cruuentseason_detail);

		if (null != getIntent() && getIntent().hasExtra("imgUrl")) {
			imgUrl = getIntent().getStringExtra("imgUrl");
		}

		imageName = TextUtils.isEmpty(imgUrl) ? "" : imgUrl.substring(imgUrl.lastIndexOf("/") + 1);

		String path = this.getCacheDir() + "/" + imageName + "e";

		if (!TextUtils.isEmpty(imgUrl) && new File(path).exists()) {
			imageView.setBackgroundDrawable(Drawable.createFromPath(path));

			// imageView.setLayoutParams(new
			// FrameLayout.LayoutParams(windowWidth, windowWidth *
			// drawable.getIntrinsicHeight()
			// / drawable.getIntrinsicWidth()));
		}

		// gestureScanner = new GestureDetector(this);
		// gestureScanner.setOnDoubleTapListener(new
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
	//
	// @Override
	// public boolean onTouchEvent(MotionEvent me) {
	// return gestureScanner.onTouchEvent(me);
	// }
	//
	// @Override
	// public boolean onDown(MotionEvent e) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public void onLongPress(MotionEvent e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	// float distanceY) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public void onShowPress(MotionEvent e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public boolean onSingleTapUp(MotionEvent e) {
	// // TODO Auto-generated method stub
	// return false;
	// }

}