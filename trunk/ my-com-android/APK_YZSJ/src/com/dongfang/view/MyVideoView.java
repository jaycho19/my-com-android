package com.dongfang.view;

/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.MediaController.MediaPlayerControl;

import com.dongfang.utils.ULog;

/**
 * Displays a video file. The VideoView class can load images from various sources (such as resources or content
 * providers), takes care of computing its measurement from the video so that it can be used in any layout manager, and
 * provides various display options such as scaling and tinting.
 */
public class MyVideoView extends SurfaceView implements MediaPlayerControl {
	private String TAG = "VideoView";

	private Context mContext;

	// settable by the client
	private Uri mUri;
	private int mDuration;

	// All the stuff we need for playing and showing a video
	private SurfaceHolder mSurfaceHolder = null;
	private MediaPlayer mMediaPlayer = null;
	private boolean mIsPrepared = false;
	private int mVideoWidth;
	private int mVideoHeight;
	private int mSurfaceWidth;
	private int mSurfaceHeight;
	// private MediaController mMediaController;
	private OnCompletionListener mOnCompletionListener;
	private OnPreparedListener mOnPreparedListener;
	private int mCurrentBufferPercentage;
	private OnErrorListener mOnErrorListener;
	private boolean mStartWhenPrepared;
	private int mSeekWhenPrepared;

	private MySizeChangeLinstener mMyChangeLinstener;

	// 视频播放状态
	public static final int STATE_ERROR = -1;
	public static final int STATE_IDLE = 0;
	public static final int STATE_PREPARING = 1;
	public static final int STATE_PREPARED = 2;
	public static final int STATE_PLAYING = 3;
	public static final int STATE_PAUSED = 4;
	public static final int STATE_PLAYBACK_COMPLETED = 5; // 播放完毕
	// public static final int STATE_SUSPEND = 6;
	// public static final int STATE_RESUME = 7;
	// public static final int STATE_SUSPEND_UNSUPPORTED = 8;
	// 8;

	// 视频暂停时，显示控件情况
	/** 不对控件进行处理 */
	public static final int STATE_PAUSE_SUPER = 0; // 不对控件进行处理
	/** 显示webview */
	public static final int STATE_PAUSE_SHOWWEBVIEW = 1; // 显示webview
	/** 显示控制栏 */
	public static final int STATE_PAUSE_SHOWCONTROLLER = 2; // 显示控制栏
	/** 显示控制栏和webview */
	public static final int STATE_PAUSE_SHOWWEBVIEWANDCONTROLLER = 3; // 显示控制栏和webview

	private int mCurrentState = STATE_IDLE; // 当前视频播放状态

	/** 视频上一个seekTo时间 */
	private int perPosition = 0; // 视频上一个seekTo时间

	/** 视频是否全屏 */
	private boolean isFullSreen = false; // 视频是否全屏
	private int mWidthMeasureSpec;
	private int mHeightMeasureSpec;

	/** 用来判断视频是否完全播放完毕 */
	private int mPreDuration = -1; // 当前播放视频的总长
	private int mPrePosition = -1; // 当前播放视频的当前位置

	public static boolean mPlayFlag = true;

	public int getVideoWidth() {
		ULog.i(TAG, "getVideoWidth() " + mVideoWidth);
		return mVideoWidth;
	}

	public int getVideoHeight() {
		ULog.i(TAG, "getVideoHeight() " + mVideoHeight);
		return mVideoHeight;
	}

	@SuppressLint("WrongCall")
	public void setVideoScale(int width, int height) {
		ULog.i(TAG, "setVideoScale() width=" + width + " height=" + height);
		this.mVideoHeight = height;
		this.mVideoWidth = width;
		ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
		localLayoutParams.height = height;
		localLayoutParams.width = width;
		setLayoutParams(localLayoutParams);
		ULog.d(TAG, "setVideoScale->mVideoWidth:" + mVideoWidth + ",mVideoHeight:" + mVideoHeight);
		onMeasure(mWidthMeasureSpec, mHeightMeasureSpec);
	}

	public interface MySizeChangeLinstener {
		public void doMyThings();
	}

	public void setMySizeChangeLinstener(MySizeChangeLinstener l) {
		ULog.i(TAG, "setMySizeChangeLinstener() ");
		mMyChangeLinstener = l;
	}

	public MyVideoView(Context context) {
		super(context);
		ULog.i(TAG, "MyVideoView(context) ");
		mContext = context;
		initVideoView();
	}

	public MyVideoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		ULog.i(TAG, "MyVideoView(context, attrs) ");
		mContext = context;
		initVideoView();
	}

	public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		ULog.i(TAG, "MyVideoView(context, attrs, defStyle) ");
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		ULog.i(TAG, "onMeasure " + "widthMeasureSpec=" + widthMeasureSpec + " heightMeasureSpec=" + heightMeasureSpec);
		if (mVideoWidth == 0) {
			ULog.d(TAG, "mVideoWidth = 0");
			this.mWidthMeasureSpec = widthMeasureSpec;
			this.mHeightMeasureSpec = heightMeasureSpec;
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
		else {
			setMeasuredDimension(mVideoWidth, mVideoHeight);
		}
	}

	public int resolveAdjustedSize(int desiredSize, int measureSpec) {
		ULog.i(TAG, "resolveAdjustedSize( " + desiredSize + "," + measureSpec + " ) ");
		int result = desiredSize;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		switch (specMode) {
		case MeasureSpec.UNSPECIFIED:
			/*
			 * Parent says we can be as big as we want. Just don't be larger than max size imposed on ourselves.
			 */
			result = desiredSize;
			break;

		case MeasureSpec.AT_MOST:
			/*
			 * Parent says we can be as big as we want, up to specSize. Don't be larger than specSize, and don't be
			 * larger than the max size imposed on ourselves.
			 */
			result = Math.min(desiredSize, specSize);
			break;

		case MeasureSpec.EXACTLY:
			// No choice. Do what we are told.
			result = specSize;
			break;
		}
		return result;
	}

	private void initVideoView() {
		ULog.i(TAG, "initVideoView() ");
		mVideoWidth = 0;
		mVideoHeight = 0;
		getHolder().addCallback(mSHCallback);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}

	public void setVideoPath(String path) {
		ULog.i(TAG, "--> setVideoPath");
		setVideoURI(Uri.parse(path));
	}

	public void setVideoURI(Uri uri) {
		ULog.i(TAG, "--> setVideoURI");
		ULog.i(TAG, "--> setVideoURI( " + uri + " )");
		mUri = uri;
		mStartWhenPrepared = false;
		mSeekWhenPrepared = 0;
		openVideo();
		requestLayout();
		invalidate();
	}

	public void stopPlayback() {
		ULog.i(TAG, "--> stopPlayback");
		try {
			if (mMediaPlayer != null) {
				mMediaPlayer.stop();
				mMediaPlayer.release();
				mMediaPlayer = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		ULog.i(TAG, "--> stop");
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
		}
	}

	public void prepare() {
		ULog.i(TAG, "--> prepare");
		if (mMediaPlayer != null) {
			try {
				mMediaPlayer.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void prepareAsync() {
		ULog.i(TAG, "--> prepareAsync");
		if (mMediaPlayer != null) {
			mMediaPlayer.prepareAsync();
		}
	}

	public void reset() {
		ULog.i(TAG, "--> reset");
		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
		}
	}

	private void openVideo() {
		ULog.i(TAG, "--> openVideo");
		if (mUri == null || mSurfaceHolder == null) {
			// not ready for playback just yet, will try again later
			return;
		}
		// Tell the music playback service to pause
		// TODO: these constants need to be published somewhere in the
		// framework.
		Intent i = new Intent("com.android.music.musicservicecommand");
		i.putExtra("command", "pause");
		mContext.sendBroadcast(i);
		release(false);
		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setOnPreparedListener(mPreparedListener);
			mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
			mIsPrepared = false;
			ULog.v(TAG, "reset duration to -1 in openVideo");
			mDuration = -1;
			mMediaPlayer.setOnCompletionListener(mCompletionListener);
			mMediaPlayer.setOnErrorListener(mErrorListener);
			mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
			mCurrentBufferPercentage = 0;
			ULog.i(TAG, "openVideo mUri: " + mUri);
			mMediaPlayer.setDataSource(mContext, mUri);
			mMediaPlayer.setDisplay(mSurfaceHolder);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setScreenOnWhilePlaying(true);
			mMediaPlayer.prepareAsync();
		} catch (IOException ex) {
			ULog.e(TAG, "Unable to open content: " + mUri);
			// mCurrentState = STATE_ERROR;
			mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
			return;
		} catch (IllegalArgumentException ex) {
			ULog.e(TAG, "Unable to open content: " + mUri);
			// mCurrentState = STATE_ERROR;
			mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
			return;
		} catch (IllegalStateException ex) {
			ULog.e(TAG, "Unable to open content: " + mUri);
			// mCurrentState = STATE_ERROR;
			mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
			return;
		}
	}

	private void setVideoDefaultScale(MediaPlayer mp) {
		ULog.i(TAG, "setVideoDefaultScale()");
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenHeight = dm.heightPixels;
		int screenWidth = dm.widthPixels;
		int videoWidth = mp.getVideoWidth();
		int videoHeight = mp.getVideoHeight();
		mVideoWidth = screenWidth;
		mVideoHeight = screenHeight - 25;

		if (videoWidth > 0 && videoHeight > 0 && !isFullSreen) {
			if (videoWidth * mVideoHeight > mVideoWidth * videoHeight) {
				ULog.i(TAG, "image too tall, correcting");
				mVideoHeight = mVideoWidth * videoHeight / videoWidth;
			}
			else if (videoWidth * mVideoHeight < mVideoWidth * videoHeight) {
				ULog.i(TAG, "image too wide, correcting");
				mVideoWidth = mVideoHeight * videoWidth / videoHeight;
			}
			else {
				// mVideoWidth = 320;
				// mVideoHeight = 240;
				mVideoWidth = (int) ((mVideoHeight * 320) / 240);
			}
		}
		else if (!isFullSreen) {
			mVideoWidth = (int) ((mVideoHeight * 320) / 240);
		}

	}

	MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
		public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
			ULog.i(TAG, "onVideoSizeChanged() width=" + width + " height=" + height);
			setVideoDefaultScale(mp);
			ULog.i(TAG, "onVideoSizeChanged() " + "mVideoWidth=" + mVideoWidth + " mVideoHeight=" + mVideoHeight);
			if (mMyChangeLinstener != null) {
				mMyChangeLinstener.doMyThings();
			}

			if (mVideoWidth != 0 && mVideoHeight != 0) {
				getHolder().setFixedSize(mVideoWidth, mVideoHeight);
			}
		}
	};

	MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
		public void onPrepared(MediaPlayer mp) {
			ULog.i(TAG, "MediaPlayer.OnPreparedListener --> onPrepared");
			// briefly show the media controller
			mIsPrepared = true;
			setVideoDefaultScale(mp);

			if (mVideoWidth != 0 && mVideoHeight != 0) {
				ULog.v(TAG, "video size: " + mVideoWidth + "/" + mVideoHeight);
				getHolder().setFixedSize(mVideoWidth, mVideoHeight);

				if (isInterrupt == true) {
					mMediaPlayer.seekTo(mPrePosition);
					// mPrePosition = -1;
					// mPreDuration = -1;

					mMediaPlayer.start();
				}
				else if (mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight) {
					// We didn't actually change the size (it was already at the size
					// we need), so we won't get a "surface changed" callback, so
					// start the video here instead of in the callback.
					ULog.v(TAG, "video mSeekWhenPrepared: " + mSeekWhenPrepared);
					if (mSeekWhenPrepared != 0) {
						mMediaPlayer.seekTo(mSeekWhenPrepared);
						// mSeekWhenPrepared = 0;
					}
					if (mStartWhenPrepared) {
						mMediaPlayer.start();
						mStartWhenPrepared = false;
					}
				}
			}
			else {

				// We don't know the video size yet, but should start anyway.
				// The video size might be reported to us later.
				if (mSeekWhenPrepared != 0) {
					mMediaPlayer.seekTo(mSeekWhenPrepared);
					// mSeekWhenPrepared = 0;
				}
				if (mStartWhenPrepared) {
					mMediaPlayer.start();
					mStartWhenPrepared = false;
				}
			}

			isError = false;
			isInterrupt = false;

			if (mOnPreparedListener != null) {
				mOnPreparedListener.onPrepared(mMediaPlayer);
			}
		}
	};

	private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
		public void onCompletion(MediaPlayer mp) {
			ULog.i(TAG, "onCompletion() ");
			ULog.e(TAG, ">>>>>>>>>>>>>> Url:" + mUri + " mSeekWhenPrepared:" + mSeekWhenPrepared);
			ULog.e(TAG, ">>>>>>>>>>>>>> duration:" + mPreDuration + " mPrePosition:" + mPrePosition);

			if (mPrePosition < (mPreDuration - 1L) && (null != mMediaPlayer)
					&& (mMediaPlayer.getCurrentPosition() > 0 && mMediaPlayer.getCurrentPosition() < mPrePosition)) {
				isInterrupt = true;
				mIsPrepared = false;
				openVideo();
			}
			else if (mOnCompletionListener != null) {
				mPrePosition = -1;
				mPreDuration = -1;
				isInterrupt = false;
				isError = false;
				mOnCompletionListener.onCompletion(mMediaPlayer);
			}
		}
	};

	private boolean isInterrupt = false;

	/**
	 * 判断是否异常停止， 例如抛错，流媒体中断等
	 * 
	 * @return
	 */
	public boolean getIsInterrupt() {
		ULog.i(TAG, "getIsInterrupt() " + isInterrupt);
		return isInterrupt;
	}

	private boolean isError = false;
	private MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {
		public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
			ULog.e(TAG, "Error: " + framework_err + "," + impl_err);
			/* If an error handler has been supplied, use it and finish. */
			ULog.e(TAG, "Url:" + mUri + " mSeekWhenPrepared:" + mSeekWhenPrepared + " mPrePosition:" + mPrePosition
					+ " mPreDuration:" + mPreDuration);
			if (mOnErrorListener != null) {
				if (mOnErrorListener.onError(mMediaPlayer, framework_err, impl_err)) {
					if (mPrePosition < mPreDuration && isInterrupt == false && !isPlaying()) {
						isInterrupt = true;
						isError = true;
						openVideo();
					}
					return true;
				}
			}
			return true;
		}
	};

	/**
	 * 判断是否出错停止
	 * 
	 * @return
	 */
	public boolean isError() {
		ULog.i(TAG, "isError() " + isError);
		return isError;
	}

	private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
		public void onBufferingUpdate(MediaPlayer mp, int percent) {
			ULog.i(TAG, "onBufferingUpdate() percent " + percent);
			mCurrentBufferPercentage = percent;
		}
	};

	/**
	 * Register a callback to be invoked when the media file is loaded and ready to go.
	 * 
	 * @param l
	 *            The callback that will be run
	 */
	public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
		mOnPreparedListener = l;
	}

	/**
	 * Register a callback to be invoked when the end of a media file has been reached during playback.
	 * 
	 * @param l
	 *            The callback that will be run
	 */
	public void setOnCompletionListener(OnCompletionListener l) {
		mOnCompletionListener = l;
	}

	/**
	 * Register a callback to be invoked when an error occurs during playback or setup. If no listener is specified, or
	 * if the listener returned false, VideoView will inform the user of any errors.
	 * 
	 * @param l
	 *            The callback that will be run
	 */
	public void setOnErrorListener(OnErrorListener l) {
		mOnErrorListener = l;
	}

	SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
		public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
			ULog.d(TAG, "--> surfaceChanged ");
			mSurfaceWidth = w;
			mSurfaceHeight = h;

			if (mMediaPlayer != null && mPlayFlag && mIsPrepared && mVideoWidth == w && mVideoHeight == h) {

				if (mSeekWhenPrepared != 0) {
					mMediaPlayer.seekTo(mSeekWhenPrepared);
				}
				mMediaPlayer.start();
			}
		}

		public void surfaceCreated(SurfaceHolder holder) {
			ULog.d(TAG, "--> surfaceCreated");
			mSurfaceHolder = holder;
			openVideo();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			ULog.d(TAG, "--> surfaceDestroyed");
			// after we return from this we
			// can't use the surface any
			// more
			mSurfaceHolder = null;
			// release(true);

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						if (mMediaPlayer != null) {
							mMediaPlayer.reset();
							mMediaPlayer.release();
							mMediaPlayer = null;
							mStartWhenPrepared = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();

			ULog.d(TAG, "--> surfaceDestroyed end");
		}
	};

	/*
	 * release the media player in any state
	 */
	private void release(boolean cleartargetstate) {
		ULog.i(TAG, "release(" + cleartargetstate + ")");
		try {
			if (mMediaPlayer != null) {
				mMediaPlayer.reset();
				mMediaPlayer.release();
				mMediaPlayer = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		ULog.i(TAG, "onTouchEvent()");
		if (isInPlaybackState()) {
			if (webViewAndControllerShown.chkController_isVISIBLE())
				webViewAndControllerShown.setController_GONE();
			else
				webViewAndControllerShown.setController_VISIBLE();
		}
		return false;
	}

	@Override
	public boolean onTrackballEvent(MotionEvent ev) {
		ULog.i(TAG, "onTrackballEvent()");
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mIsPrepared && keyCode != KeyEvent.KEYCODE_BACK && keyCode != KeyEvent.KEYCODE_VOLUME_UP
				&& keyCode != KeyEvent.KEYCODE_VOLUME_DOWN && keyCode != KeyEvent.KEYCODE_MENU
				&& keyCode != KeyEvent.KEYCODE_CALL && keyCode != KeyEvent.KEYCODE_ENDCALL && mMediaPlayer != null
		/* && mMediaController != null */) {
			ULog.v(TAG, "keyCode: " + keyCode);
			if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
				if (mMediaPlayer.isPlaying()) {
					pause();
				}
				else {
					start();
				}
				return true;
			}
			else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP && mMediaPlayer.isPlaying()) {
				pause();
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void start() {
		ULog.i(TAG, "--> start");
		if (mMediaPlayer != null && mIsPrepared) {
			mMediaPlayer.start();
			mStartWhenPrepared = false;
		}
		else {
			mStartWhenPrepared = true;
		}

		ULog.d(TAG, "start VideoState:" + mCurrentState);
		if (isInPlaybackState()) {
			if (mCurrentState == MyVideoView.STATE_PLAYBACK_COMPLETED || mCurrentState == MyVideoView.STATE_PREPARED) {
				/**
				 * 1.播放结束 2.视频正在加载中
				 */
				setCurrentViewState(STATE_PREPARED, "start()1");
			}
			else {
				setCurrentViewState(STATE_PLAYING, "start()2");
			}

			if (isPlaying() && this.getCurrentPosition() > 0) {
				webViewAndControllerShown.setWebView_GONE();
			}
			webViewAndControllerShown.setController_VISIBLE();
		}
		else {
			setCurrentViewState(STATE_PREPARED, "start()3");
		}
	}

	@Override
	public void pause() {
		ULog.i(TAG, "--> pause ");
		if (mMediaPlayer != null && mIsPrepared) {
			if (mMediaPlayer.isPlaying()) {
				new Thread() {
					public void run() {
						if (mMediaPlayer != null)
							mMediaPlayer.pause();
					};
				}.start();

				setPerPosition((perPosition > mMediaPlayer.getCurrentPosition()) ? perPosition : mMediaPlayer
						.getCurrentPosition());
			}
		}
		mStartWhenPrepared = false;
	}

	@Override
	public int getDuration() {
		ULog.i(TAG, "getDuration() " + mDuration);
		if (mMediaPlayer != null && mIsPrepared) {
			if (mDuration > 0) {
				return mDuration;
			}
			mDuration = mMediaPlayer.getDuration();
			return mDuration;
		}
		mDuration = -1;
		return mDuration;
	}

	@Override
	public int getCurrentPosition() {
		ULog.i(TAG, "getCurrentPosition()");
		if (mMediaPlayer != null && mIsPrepared) {
			if (!isDrag)
				return (mPrePosition > mMediaPlayer.getCurrentPosition()) ? mPrePosition : mMediaPlayer
						.getCurrentPosition();
			else {
				isDrag = false;
				return mMediaPlayer.getCurrentPosition();
			}
		}
		return 0;
	}

	/**
	 * 视频播放的进度条是否是拖动状态
	 */
	private boolean isDrag = false;

	public void setDrag(boolean drag) {
		this.isDrag = drag;
	}

	@Override
	public void seekTo(int msec) {
		ULog.i(TAG, "--> seekTo(" + msec + ")");
		perPosition = msec;

		if (mMediaPlayer != null && mIsPrepared) {
			mMediaPlayer.seekTo(msec);
		}
		else {
			mSeekWhenPrepared = msec;
		}
		ULog.i(TAG, "--> seekTo() mSeekWhenPrepared:" + mSeekWhenPrepared);
	}

	@Override
	public boolean isPlaying() {
		ULog.i(TAG, "--> isPlaying");
		// 捕获并处理java.lang.IllegalStateException (for HTC T329D播放sd卡上的本地视频时拔出sd卡
		// 客户端意外停止)
		try {
			if (mMediaPlayer != null && mIsPrepared) {
				ULog.i(TAG, "--> isPlaying " + mMediaPlayer.isPlaying());
				return mMediaPlayer.isPlaying();
			}
		} catch (Exception e) {
			ULog.e(TAG, e.toString());
			showErrorDialog();
			return false;
		}
		ULog.i(TAG, "--> isPlaying " + false);
		return false;
	}

	public void showErrorDialog() {
		if (mContext == null)
			return;
		// DialogFactory mFactory = new DialogFactory(mContext);
		// mFactory.setBtnClickListener(new DialogBtnClickListener() {
		// @Override
		// public void btnOnClickListener(View paramView) {
		// setCurrentViewState(MyVideoView.STATE_PLAYBACK_COMPLETED, "showErrorDialog()");
		// MediaPlayerActivity instance = MediaPlayerActivity.getInstance();
		// if (instance != null)
		// instance.finish();
		// else {
		// ULog.e(TAG, "close mediaplayer error");
		// }
		// }
		// });
		// mFactory.showExceptionDialog(DialogFactory.PLAY_ERROR);
	}

	@Override
	public int getBufferPercentage() {
		ULog.i(TAG, "getBufferPercentage() ");

		int p = -1;

		try {
			p = getCurrentPosition();
		} catch (Exception e) {
			ULog.d(TAG, "getBufferPercentage exception : " + e.toString());
		}
		ULog.d(TAG, "getBufferPercentage state1 : " + mCurrentState + " ; postion = " + p + " ; perPosition = "
				+ perPosition);

		// 视频暂停状态时，不获取视频缓存
		if (mCurrentState == STATE_PAUSED || mCurrentState == STATE_PLAYBACK_COMPLETED)
			return 0;

		if (perPosition < p) {
			setCurrentViewState(STATE_PLAYING, "getBufferPercentage");

			return 100;
		}

		ULog.d(TAG, "getBufferPercentage state3 : " + mCurrentState);

		if (mMediaPlayer != null) {
			ULog.i(TAG, "getBufferPercentage() mCurrentBufferPercentage=" + mCurrentBufferPercentage);
			return mCurrentBufferPercentage;
		}
		return 0;
	}

	@Override
	public boolean canPause() {
		ULog.i(TAG, "canPause()");
		return false;
	}

	@Override
	public boolean canSeekBackward() {
		ULog.i(TAG, "canSeekBackward()");
		return false;
	}

	@Override
	public boolean canSeekForward() {
		ULog.i(TAG, "canSeekForward()");
		return false;
	}

	public synchronized Bitmap getFrameAt() {
		ULog.i(TAG, "getFrameAt()");
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		if (null != mSurfaceHolder) {
			mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			Canvas canvas = new Canvas(bitmap);
			mSurfaceHolder.lockCanvas();
			canvas.drawBitmap(bitmap, 0, 0, null);
			mSurfaceHolder.unlockCanvasAndPost(canvas);
		}
		return bitmap;
	}

	/**
	 * 获取当前视频播放的状态
	 * 
	 * @return currentViewState
	 */
	public int getCurrentViewState() {
		ULog.i(TAG, "getCurrentViewState() " + mCurrentState);
		return mCurrentState;
	}

	/**
	 * 设置当前视频状态
	 * 
	 * @param viewstate
	 */
	private void setCurrentViewState(int viewstate) {
		ULog.d(TAG, "setCurrentViewState viewstate = " + viewstate);
		mCurrentState = viewstate;
	}

	public void setCurrentViewState(int viewstate, String logfromplace) {
		ULog.d(TAG, "setCurrentViewState:" + viewstate + ",logfromplace = " + logfromplace);
		setCurrentViewState(viewstate);
	}

	/**
	 * 有显示webview的停止
	 * 
	 * @param stopState
	 */
	public void stop(int stopState) {
		ULog.d(TAG, "stop(" + stopState + ")");
		if (stopState == STATE_PAUSE_SHOWWEBVIEW)
			webViewAndControllerShown.setWebView_VISIBLE();
		stop();
	}

	/**
	 * 1.如果pauseState == STATE_PAUSE_SHOWWEBVIEW 并且视频播未播放完毕，显示webview;<br>
	 * 2.显示控制栏，不自动隐藏<br>
	 * 3.setCurrentViewState(STATE_PAUSED)<br>
	 * 
	 * @param pauseState
	 */
	public void pause(int pauseState) {
		ULog.d(TAG, "pause pauseState = " + pauseState);
		// new Thread(){
		// public void run() {
		// pause();
		// };
		// }.start();
		pause();
		if (isInPlaybackState()) {
			setCurrentViewState(STATE_PAUSED, " pause(" + pauseState + ")");
			if (getCurrentViewState() != STATE_PLAYBACK_COMPLETED && pauseState == STATE_PAUSE_SHOWWEBVIEW)
				webViewAndControllerShown.setWebView_VISIBLE();
			webViewAndControllerShown.setController_VISIBLE();
		}
	}

	public boolean isInPlaybackState() {
		ULog.i(TAG, "isInPlaybackState() ");
		return (
		// mMediaPlayer != null &&
		mCurrentState != STATE_ERROR && mCurrentState != STATE_IDLE && mCurrentState != STATE_PREPARING);
	}

	public void setPerPosition(int position) {
		ULog.i(TAG, "setPerPosition(" + position + ") ");
		this.perPosition = position;
	}

	public boolean IsCompleted() {
		ULog.i(TAG, "IsCompleted() ");
		return (mCurrentState == STATE_PLAYBACK_COMPLETED);
	}

	/** 设置全屏属性 */
	public void setFullScreen(boolean b) {
		ULog.i(TAG, "setFullScreen(" + b + ")");
		isFullSreen = b;
	}

	/** 判断是否全屏 */
	public boolean isFullScreen() {
		ULog.i(TAG, "isFullScreen(" + isFullSreen + ")");
		return isFullSreen;
	}

	private WebViewAndControllerShown webViewAndControllerShown;

	public void setWebViewAndControllerShown(WebViewAndControllerShown webViewAndControllerShown) {
		ULog.i(TAG, "setWebViewAndControllerShown()");
		this.webViewAndControllerShown = webViewAndControllerShown;
	}

	public void setmPreDuration(int mPreDuration) {
		ULog.i(TAG, "setWebViewAndControllerShown(" + mPreDuration + ")");
		this.mPreDuration = mPreDuration;
	}

	public int getmPreDuration() {
		ULog.i(TAG, "setWebViewAndControllerShown() " + mPreDuration);
		return this.mPreDuration;
	}

	public void setmPrePosition(int mPrePosition) {
		ULog.i(TAG, "setWebViewAndControllerShown(" + mPrePosition + ")");
		this.mPrePosition = mPrePosition;
	}

	public int getPerPosition() {
		ULog.i(TAG, "getPerPosition() " + perPosition);
		return perPosition;
	}

	public boolean ismIsPrepared() {
		ULog.i(TAG, "ismIsPrepared() " + mIsPrepared);
		return mIsPrepared;
	}

	/**
	 * 用于控制webview和controller显示的接口
	 */
	public interface WebViewAndControllerShown {
		public void setWebView_VISIBLE();

		public void setWebView_GONE();

		public void setController_VISIBLE();

		public void setController_GONE();

		public boolean chkController_isVISIBLE();
	}

	@Override
	public int getAudioSessionId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
