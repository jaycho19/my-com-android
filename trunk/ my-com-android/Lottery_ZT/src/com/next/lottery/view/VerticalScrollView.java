package com.next.lottery.view;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.dongfang.utils.ULog;
import com.next.lottery.utils.ImageManager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class VerticalScrollView extends TelecomScrollView implements Runnable {
	private static final String			TAG						= "VerticalScrollView";
	public static final int				SCROLL_STATE_SCROLLING	= 1;
	public static final int				SCROLL_STATE_IDLE		= 2;
	private static final int			DELAY_TIME				= 50;
	private static final int			STOP_SCROLLING_OR_NOT	= 0;
	private boolean						mCanScroll;
	private int							mLastMotionX;
	private int							mLastMotionY;
	private ScrolllHandler				mHandler;
	private OnScrollListener			mScrollListener;
	private int							mFirstVisibleItem		= -1;
	private int							mLastVisibleItem		= -1;
	private ScheduledExecutorService	mScheduledExecutorService;
	private int							mExtraLoadPageCount		= 2;
	private int							mLatestLastVisibleItem	= -1;

	public VerticalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCanScroll = true;
		mHandler = new ScrolllHandler(this);
		mScheduledExecutorService = Executors.newScheduledThreadPool(1);
		mScrollListener = new OnScrollListener() {

			@Override
			public void onScrollStateChanged(VerticalScrollView view, int scrollState) {

			}

			@Override
			public void onScroll(VerticalScrollView view, int firstVisibleItem, int lastVisibleItem, int totalItemCount) {
				if (lastVisibleItem >= 0 && mLatestLastVisibleItem != lastVisibleItem) {
					ULog.d("mLastVisibleItem: " + lastVisibleItem + "mLatestLastVisibleItem :" + mLatestLastVisibleItem);
					if (mLatestLastVisibleItem < lastVisibleItem) {
						for (int i = 0; i < totalItemCount; i++) {
							if (i >= firstVisibleItem - mExtraLoadPageCount
									&& i <= lastVisibleItem + mExtraLoadPageCount) {
								ImageManager.getInstance().loadAll(getContext().toString(), i);
								ULog.d("loadAll " + i);
							}
//							else {
//								ImageManager.getInstance().recycleAllWithCache(getContext().toString(), i);
//							}
						}
					}
					else {
						for (int i = totalItemCount - 1; i >= 0; i--) {
							if (i >= firstVisibleItem - mExtraLoadPageCount
									&& i <= lastVisibleItem + mExtraLoadPageCount) {
								ImageManager.getInstance().loadAll(getContext().toString(), i);
							}
//							else {
//								ImageManager.getInstance().recycleAllWithCache(getContext().toString(), i);
//							}
						}
					}
				}
				mLatestLastVisibleItem = lastVisibleItem;
			}
		};
	}

	public void setExtraLoadPageCount(int pageCount) {
		this.mExtraLoadPageCount = pageCount;
	}

	public void setOnScrollListener(OnScrollListener scrollListener) {
		this.mScrollListener = scrollListener;
	}

	public int getFirstVisibleItem() {
		return mFirstVisibleItem;
	}

	public int getLastVisibleItem() {
		return mLastVisibleItem;
	}

	public void reset() {
		mFirstVisibleItem = -1;
		mLastVisibleItem = -1;
		mLatestLastVisibleItem = -1;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int y = (int) ev.getRawY();
		int x = (int) ev.getRawX();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 首先拦截down事件,记录y坐标
			ULog.d("onInterceptTouchEvent ACTION_DOWN");
			mCanScroll = true;
			mLastMotionY = y;
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			// deltaY > 0 是向下运动,< 0是向上运动
			int deltaY = y - mLastMotionY;
			int deletaX = x - mLastMotionX;
			ULog.d("onInterceptTouchEvent ACTION_MOVE" + "Math.abs(deletaX) > Math.abs(deltaY) :"
							+ (Math.abs(deletaX) > Math.abs(deltaY)));
			if (Math.abs(deletaX) > Math.abs(deltaY)) {
				mCanScroll = false;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mCanScroll = true;
			ULog.d("onInterceptTouchEvent ACTION_UP ACTION_CANCEL");
			break;
		}
		ULog.d("onInterceptTouchEvent mCanScroll = " + mCanScroll);
		return super.onInterceptTouchEvent(ev) && mCanScroll;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int y = (int) ev.getRawY();
		int x = (int) ev.getRawX();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			ULog.d("onTouchEvent ACTION_DOWN");
			// onInterceptTouchEvent已经记录
			// mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			ULog.d("onTouchEvent ACTION_MOVE");
			mLastMotionY = y;
			mLastMotionX = x;
			if (mScrollListener != null) {
				mScrollListener.onScrollStateChanged(this, SCROLL_STATE_SCROLLING);
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			ULog.d("onTouchEvent ACTION_UP ACTION_CANCEL");
			mLastMotionY = y;
			mLastMotionX = x;
			mScheduledExecutorService.schedule(this, DELAY_TIME, TimeUnit.MICROSECONDS);
			break;
		}
		return super.onTouchEvent(ev);
	}

	private static class ScrolllHandler extends Handler {
		private WeakReference<VerticalScrollView>	mVerticalScrollView;

		public ScrolllHandler(VerticalScrollView verticalScrollView) {
			this.mVerticalScrollView = new WeakReference<VerticalScrollView>(verticalScrollView);
		}

		@Override
		public void handleMessage(Message msg) {
			VerticalScrollView verticalScrollView = mVerticalScrollView.get();
			if (verticalScrollView != null) {
				switch (msg.what) {
				case STOP_SCROLLING_OR_NOT:
					if (verticalScrollView.mLastMotionY == verticalScrollView.getScrollY()) {
						verticalScrollView.measureVisibleChild(verticalScrollView);
						if (verticalScrollView.mScrollListener != null) {
							verticalScrollView.mScrollListener.onScrollStateChanged(verticalScrollView,
									SCROLL_STATE_IDLE);
							verticalScrollView.mScrollListener.onScroll(verticalScrollView,
									verticalScrollView.mFirstVisibleItem, verticalScrollView.mLastVisibleItem,
									((ViewGroup) (verticalScrollView.getChildAt(0))).getChildCount());
						}
					}
					else {
						verticalScrollView.mHandler.sendMessageDelayed(
								verticalScrollView.mHandler.obtainMessage(STOP_SCROLLING_OR_NOT), DELAY_TIME);
						verticalScrollView.mLastMotionY = verticalScrollView.getScrollY();
					}
					break;

				default:
					break;
				}
			}
		}
	}

	private void measureVisibleChild(VerticalScrollView verticalScrollView) {
		ULog.d("height :" + verticalScrollView.getMeasuredHeight());
		ViewGroup container = (ViewGroup) verticalScrollView.getChildAt(0);
		int start = verticalScrollView.mLastMotionY;
		int end = verticalScrollView.mLastMotionY + verticalScrollView.getMeasuredHeight();
		boolean isSelectFirst = false;
		boolean isSelectLast = false;
		if (container != null && container.getChildCount() > 0) {
			for (int i = 0; i < container.getChildCount(); i++) {
				View child = container.getChildAt(i);
				float measure = child.getTop() + child.getMeasuredHeight();
				ULog.d("start:" + start + " end:" + end + " measure:" + measure);
				if (start <= measure && !isSelectFirst) {
					verticalScrollView.mFirstVisibleItem = i;
					isSelectFirst = true;
				}
				else if (end <= measure && !isSelectLast) {
					verticalScrollView.mLastVisibleItem = i;
					isSelectLast = true;
				}
			}
			ULog.d("mFirstVisibleItem " + verticalScrollView.mFirstVisibleItem + " mLastVisibleItem"
					+ verticalScrollView.mLastVisibleItem);
		}
		
	}

	public void notifyDataSetChanged() {
		mScheduledExecutorService.schedule(this, DELAY_TIME, TimeUnit.MICROSECONDS);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mScheduledExecutorService.shutdown();
	}

	public interface OnScrollListener {
		public void onScrollStateChanged(VerticalScrollView view, int scrollState);

		public void onScroll(VerticalScrollView view, int firstVisibleItem, int lastVisibleItem, int totalItemCount);
	}

	@Override
	public void run() {
		mHandler.sendMessage(mHandler.obtainMessage(STOP_SCROLLING_OR_NOT));
	}
}
