package com.dongfang.dicos.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;

import com.dongfang.dicos.util.ULog;

public class MyHorizontalScrollView extends HorizontalScrollView {

	private static final String	tag		= "MyHorizontalScrollView";

	private View				inner;
	private float				x;
	private Rect				normal	= new Rect();
	private Context				context;
	private List<Float>			listX	= new ArrayList<Float>();
	private int					state	= 0;						// 从左向右状态0，从右向左状态1

	public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setClickable(true);
		this.context = context;

	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setClickable(true);
		this.context = context;
	}

	public MyHorizontalScrollView(Context context) {
		super(context);
		this.setClickable(true);
		this.context = context;

	}

	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
			ULog.d(tag, "onFinishInflate");
		}
	}

	public void setHeight(int height) {
		Drawable draw = this.getBackground();
		//draw = Utility.zoomDrawable(draw, Utility.getWindowWidth(context), height);
		this.setBackgroundDrawable(draw);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (isClickable()) {
			if (inner == null) {
				return super.onTouchEvent(ev);
			} else {
				commOnTouchEvent(ev);
			}
			return super.onTouchEvent(ev);
		}
		return false;
	}

	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			x = ev.getX();
			ULog.d(tag, "commOnTouchEvent ACTION_DOWN X=" + x);
			break;
		case MotionEvent.ACTION_UP:
			if (isNeedAnimation()) {
				animation();
			}
			ULog.d(tag, "ACTION_UP");

			if (listX.size() > 2 && (listX.get(listX.size() - 1) > listX.get(listX.size() - 2))) {
				// 从左向右移
				x = 0;
				state = 0;
			} else {
				// 从右向左移
				state = 1;
			}

			listX.clear();
			listX = new ArrayList<Float>();
			break;
		case MotionEvent.ACTION_MOVE:
			final float preX = x;
			float nowX = ev.getX();
			int deltaX = (int) (preX - nowX);

			if (state == 1 && deltaX < 0) {
				// 从右向左移
				deltaX = 0;
			}
			ULog.d(tag, "commOnTouchEvent ACTION_MOVE deltaX: " + deltaX + ",preX:" + x);
			// 滚动
			scrollBy(deltaX, 0);

			x = nowX;
			// 当滚动到最上或者最下时就不会再滚动，这时移动布局
			if (isNeedMove()) {
				if (normal.isEmpty()) {
					// 保存正常的布局位置
					normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
				}
				// 移动布局
				inner.layout(inner.getLeft() - deltaX, inner.getTop(), inner.getRight() - deltaX, inner.getBottom());
			}

			// 拖动时，设置子栏目的背景为空
//			ImageView[] images = LoginActivity.subMenuLayout.getSubMenuImageView();
//			if (images != null) {
//				for (int i = 0; i < images.length; i++) {
//					if (images[i].getBackground() != null) {
//						images[i].setBackgroundDrawable(null);
//					}
//				}
//			}
			listX.add(x);
			break;

		default:
			break;
		}
	}

	// 开启动画移动

	public void animation() {
		// 开启移动动画
		int[] a = { normal.left, normal.top, normal.right, normal.bottom };
		normal.setEmpty();
		TranslateAnimation ta = new TranslateAnimation(inner.getLeft(), getLeft(), 0, 0);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// 设置回到正常的布局位置
		inner.layout(a[0], a[1], a[2], a[3]);

	}

	// 是否需要开启动画
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	// 是否需要移动布局
	public boolean isNeedMove() {

		int offset = inner.getMeasuredWidth() - getWidth();
		int scrollX = getScrollX();
		if (scrollX == 0 || scrollX == offset) {
			return true;
		}
		return false;
	}

}
