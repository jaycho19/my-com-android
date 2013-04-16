package com.dongfang.dicos.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;

public class ImageShowView extends View implements Runnable {

	Bitmap			mBitmap			= null;
	int				mBitmapWidth	= 0;			// 定义图片的宽
	int				mBitmapHeight	= 0;			// 定义图片的高

	private double	Scale			= 1.0;

	Matrix			mMatrix			= new Matrix();

	public ImageShowView(Context context) {
		super(context);

		// mBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.image1)).getBitmap();

		// mBitmapWidth = mBitmap.getWidth();
		// mBitmapHeight = mBitmap.getHeight();

		// 开启线程
		new Thread(this).start();
	}

	public void setScale(double scale) {
		this.Scale = scale;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mMatrix.reset();
		mMatrix.postScale((float) Scale, (float) Scale);// 设置缩放

		Bitmap mBitmap2 = Bitmap.createBitmap(mBitmap, 0, 0, mBitmapWidth, mBitmapHeight, mMatrix, true);

		// 绘制旋转之后的图像
		ImageShowView.drawImage(canvas, mBitmap2, (320 - mBitmapWidth) / 2, 10);
		mBitmap2 = null;
	}

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			postInvalidate();
		}
	}

	private static void drawImage(Canvas canvas, Bitmap bitmap, int x, int y) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bitmap, x, y, null);
	}
}
