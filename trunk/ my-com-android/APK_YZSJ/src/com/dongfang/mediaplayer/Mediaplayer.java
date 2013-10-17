package com.dongfang.mediaplayer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

import com.dongfang.utils.ULog;

/**
 * 
 * @author yanghua 2013-4-18
 * 
 */
public class Mediaplayer implements SurfaceHolder.Callback {
	public static final String				TAG							= Mediaplayer.class.getName();

	private OnErrorListener					mOnErrorListener;
	private OnSeekCompleteListener			mOnSeekCompleteListener;
	private OnVideoSizeChangedListener		mOnVideoSizeChangedListener;

	private OnMPTouchListener				mOnMPTouchListener;
	private OnMPClickListener				mOnMPClickListener;
	private OnMPLongClickListener			mOnMPLongClickListener;
	// 双击
	private OnMPDoubleClickListener			mOnMPDoubleClickListener;
	// 左右滑动
	private OnMPScrollLeftAndRightListener	mOnMPScrollLeftAndRightListener;
	// 上下滑动
	private OnMPScrollUpAndDownListener		mOnMPScrollUpAndDownListener;

	private OnSurfaceCreatedListener		mOnSurfaceCreatedListener;

	private OnCurrentPositionUpdateListener	onCurrentPositionUpdateListener;
	private OnPlayPauseStateChagedListener	onPlayPauseStateChagedListener;
	private OnDurationUpdateListener		onDurationUpdateListener;
	private OnLoadingListener				onLoadingListener;
	private OnSwitchListener				onSwitchListener;
	private OnTimeOutListener				onTimeOutListener;

	private OnInfoListener					mInternalOnInfoListener;
	private OnBufferingUpdateListener		mInternalOnBufferingUpdateListener;
	private OnPreparedListener				mInternalOnPreparedListener;
	private OnCompletionListener			mInternalOnCompletionListener;
	private OnTouchListener					mInternalOnTouchListener;
	private OnClickListener					mInternalOnClickListener;
	private OnLongClickListener				mInternalOnLongClickListener;

	private OnInfoListener					mExternalOnInfoListener;
	private OnBufferingUpdateListener		mExternalOnBufferingUpdateListener;
	private OnPreparedListener				mExternalOnPreparedListener;
	private OnCompletionListener			mExternalOnCompletionListener;

	private int								mSeekWhenPrepared;
	// private boolean mIsPrepared;
	// private boolean mIsVideoSizeKnown;

	/***/
	private SurfaceHolder					defaultSurfaceHolder;
	private android.media.MediaPlayer		mDefaultPlayer				= null;
	private PLAYSTATE						mPlayerState				= PLAYSTATE.IDLE;
	private VIDEOSIZESTATE					mVideoSizeState				= VIDEOSIZESTATE.ORIGINAL;

	/** 视频原始大小 */
	private int								mVideoOriginalWidth			= 0, mVideoOriginalHeight = 0;
	/** 视频显示大小 */
	private int								mVideoWidth					= 0, mVideoHeight = 0;
	/** surface view 大小 */
	private int								mSurfaceContainWidth		= 0, mSurfaceContainHeight = 0;
	private boolean							mIsLandscape;
	/** 视频当前播放位置 */
	private int								mCurrentPosition			= 0;
	private int								mDuration					= 0;
	private int								mBufferingPercent			= 0;

	/** 视频loading开始时的播放位置 */
	private int								mLoadingStartPosition		= 0;
	// 是否在loading中
	private BUFFERSTATE						mMediaBufferingState		= BUFFERSTATE.BUFFERING;
	private static Mediaplayer				mediaPlayer					= null;
	private VideoView						mVideoView;
	private View							mVideoViewParent;

	private Timer							mTimer;

	private boolean							mIsSurfaceCreated			= false;
	private boolean							mIsDefaultPlayerReleasing	= false;

	/** 监听手势 */
	private GestureDetector					mGestureDetector;

//	private PlayData						mPlayData					= PlayData.getInstance();
	private String 							mUri;
	private Canvas							mCanvas;

	private final int						loadingStart				= 0;
	private final int						loadingEnd					= 1;
	private final int						tryRestartMedia				= 2;
	private final int						notifyDurationChange		= 3;
	private final int						notifyCurrentPositionChange	= 4;

	// /** 视频播放状态 */
	// private enum PLAYSTATE {
	// PLAY, PAUSE, PREPARE, PREPARED, SEEK_TO, IDLE;
	// }

	/** 视频播放状态 */
	private enum PLAYSTATE {
	IDLE, PREPARE, PREPARED, ERROR;
	}

	/** 视频缓存状态 */
	private enum BUFFERSTATE {
	/** 不在缓冲状态 */
	NONEEDBUFFERING,
	/** 正在缓冲状态 */
	BUFFERING;
	}

	/** 视频显示大小状态 */
	public static enum VIDEOSIZESTATE {
	ORIGINAL, FOUR_RATIO_THREE, SIXTEEN_RATIO_NINE, FULL;
	}

	public static Mediaplayer getInstance() {
		if (mediaPlayer == null)
			mediaPlayer = new Mediaplayer();
		// if (mDefaultPlayer == null) {
		// mDefaultPlayer = new android.media.MediaPlayer();
		// }
		return mediaPlayer;
	}

	public Mediaplayer() {

		initPlayerInternalListener();

	}

	/** 播放视频 */
	public void start() {
		ULog.i( "--> Mediaplayer start()");
		if (mDefaultPlayer != null && mPlayerState == PLAYSTATE.PREPARED) {
			mDefaultPlayer.start();
			notifyPlayPauseStateChaged();
		}
		// this.mHandler.sendEmptyMessage(MESSAGE.START);
		// this.mHandler.removeMessages(MESSAGE.MONITER);
		// this.mHandler.sendEmptyMessageDelayed(MESSAGE.MONITER, 1000L);
	}

	public void stop() {
		ULog.v( "start");
		if (mDefaultPlayer != null && mPlayerState == PLAYSTATE.PREPARED) {
			mDefaultPlayer.stop();
		}
		CancelTimer();
	}

	public void pause() {
		ULog.v( "pause");
		if (mDefaultPlayer != null && mPlayerState == PLAYSTATE.PREPARED) {
			mDefaultPlayer.pause();
			notifyPlayPauseStateChaged();
		}
		// this.mHandler.sendEmptyMessage(MESSAGE.PAUSE);
	}

	public boolean isPlaying() {
		if (mDefaultPlayer != null && mPlayerState == PLAYSTATE.PREPARED) {
			return mDefaultPlayer.isPlaying();
		}
		else {
			return false;
		}
	}

	public void release() {
		ULog.v( "release");
		// mediaPlayer = null;
		if (mDefaultPlayer != null) {
			mDefaultPlayer.release();
		}
		// this.mHandler.sendEmptyMessage(MESSAGE.RELEASE);
	}

	public int getCurrentPosition() {
		int ret = 0;
		if (mDefaultPlayer != null && mPlayerState == PLAYSTATE.PREPARED) {
			ULog.d( "getCurrentPosition = " + mDefaultPlayer.getCurrentPosition());
			ret = this.mCurrentPosition = mDefaultPlayer.getCurrentPosition();
		}
		else {
			ULog.d( "getCurrentPosition = " + mCurrentPosition);
			ret = mCurrentPosition;
		}
		return ret;
	}

	public int getPreviousPosition() {
		return this.mCurrentPosition;
	}

	public int getCurrentBufferingUpdate() {
		if (mDefaultPlayer != null && mPlayerState == PLAYSTATE.PREPARED) {
			return this.mBufferingPercent;
		}
		else {
			return 0;
		}
	}

	public int getDuration() {
		if (mDefaultPlayer != null && mPlayerState == PLAYSTATE.PREPARED) {
			if (mDuration > 0) {
				return mDuration;
			}
			else {
				mDuration = mDefaultPlayer.getDuration();
				//如果超过300小时就认为返回的值是不正常的
				if (mDuration >= 300 * 60 * 60 * 1000) {
					return mDuration = 0;
				}else{
					//正常的
					return mDuration;
				}
			}

		}
		else {
			if (mDuration > 0) {
				return mDuration;
			}
			else {
				return mDuration = 0;
			}
		}
	}

	/** 重置资源 */
	private void resetMediaPlayer() {
		ULog.v( "resetMediaPlayer");
		if (mDefaultPlayer != null) {
			mDefaultPlayer.reset();
			mBufferingPercent = 0;
			mLoadingStartPosition = 0;

			mCurrentPosition = 0;
			mDuration = 0;
			mPlayerState = PLAYSTATE.IDLE;
			mVideoSizeState = VIDEOSIZESTATE.ORIGINAL;
		}
	}

	/**
	 * 释放资源
	 * 
	 * @return
	 */
	public void releaseMediaPlayer() {
		ULog.v( "releaseDefaultPlayer");
		// CancelTimerForListnerCurrentPosition();
		mIsDefaultPlayerReleasing = true;
		// 修复因为延迟重新调用播放器时用户正好退出播放页面而导致的问题
		myHandler.removeMessages(tryRestartMedia);
		mediaPlayer.stop();
		resetMediaPlayer();
		if (mDefaultPlayer != null) {
			mDefaultPlayer.release();
			mDefaultPlayer = null;
			mIsDefaultPlayerReleasing = false;
			ULog.d( "releaseMediaPlayer  mIsDefaultPlayerReleasing = " + mIsDefaultPlayerReleasing);
		}
		else {
			mIsDefaultPlayerReleasing = false;
		}
	}

	// public PLAYSTATE getCurrentState() {
	// return mPlayerState;
	// }

	public void seekTo(int seekToTargetPosition) {
		// ULog.v( "release = " + seekToTargetPosition);

		// if (this.onLoadingListener != null)
		// this.onLoadingListener.onLoading();
		// // this.mSeekToTargetPosition = paramInt;
		// this.mHandler.removeMessages(3);
		// Message localMessage = Message.obtain();
		// localMessage.what = 3;
		// localMessage.arg1 = seekToTargetPosition;
		// this.mHandler.sendMessage(localMessage);
		ULog.i( "--> seekTo  seekToTargetPosition = " + seekToTargetPosition);
		if (mDefaultPlayer != null && mPlayerState == PLAYSTATE.PREPARED) {
			mDefaultPlayer.seekTo(seekToTargetPosition);
			notifyCurrentPositionUpdate(seekToTargetPosition);
			ULog.i( "--> seekTo  PREPARED ");
		}
		else {
			mSeekWhenPrepared = seekToTargetPosition;
			ULog.i( "--> seekTo mSeekWhenPrepared ");
		}
		this.mMediaBufferingState = BUFFERSTATE.BUFFERING;
		mLoadingStartPosition = seekToTargetPosition;
	}

	public void seekToTenSecond(boolean isForward) {
		int seekToTargetPosition = 0;
		mCurrentPosition = getCurrentPosition();
		if (isForward == true) {
			seekToTargetPosition = (mCurrentPosition + 10000 > mDuration) ? mDuration : mCurrentPosition + 10000;

		}
		else {
			seekToTargetPosition = (mCurrentPosition - 10000 > 0) ? mCurrentPosition - 10000 : 0;
		}
		ULog.i( "seekToTargetPosition = " + seekToTargetPosition + "mCurrentPosition = " + mCurrentPosition
				+ "mDuration= " + mDuration);
		seekTo(seekToTargetPosition);

	}

	public void seekToTwoMin(boolean isForward) {
		int seekToTargetPosition = 0;
		mCurrentPosition = getCurrentPosition();
		if (isForward == true) {
			seekToTargetPosition = (mCurrentPosition + 2 * 60 * 1000 > mDuration) ? mDuration
					: mCurrentPosition + 2 * 60 * 1000;

		}
		else {
			seekToTargetPosition = (mCurrentPosition - 2 * 60 * 1000 > 0) ? mCurrentPosition - 2 * 60 * 1000 : 0;
		}
		ULog.i( "seekToTargetPosition = " + seekToTargetPosition + "mCurrentPosition = " + mCurrentPosition
				+ "mDuration= " + mDuration);
		seekTo(seekToTargetPosition);

	}

	/**
	 * 获得视频显示大小比例
	 */
	public VIDEOSIZESTATE getVideoSizeRatio() {
		return mVideoSizeState;
	}

	/**
	 * 设置视频显示大小比例
	 * 
	 * @param mVideoSizeState
	 */
	public void setVideoSizeRatio(VIDEOSIZESTATE mVideoSizeStateParam) {
		switch (mVideoSizeStateParam) {
		case ORIGINAL:
			this.mVideoSizeState = VIDEOSIZESTATE.ORIGINAL;
			break;
		case FOUR_RATIO_THREE:
			this.mVideoSizeState = VIDEOSIZESTATE.FOUR_RATIO_THREE;
			break;
		case SIXTEEN_RATIO_NINE:
			this.mVideoSizeState = VIDEOSIZESTATE.SIXTEEN_RATIO_NINE;
			break;
		case FULL:
			this.mVideoSizeState = VIDEOSIZESTATE.FULL;
			break;
		}
		changeVideoSizeRatio();
	}

	private void changeVideoSizeRatio() {
		calculateVideoWH();
		// if (mPlayerState == PLAYSTATE.PREPARED) {
		changeVideoSize(mVideoWidth, mVideoHeight);
		// }
	}

	/**
	 * 根据mVideoSizeState来计算出video宽高
	 */
	private void calculateVideoWH() {
		if (mIsLandscape) {
			float surfaceContainScale = (float) mSurfaceContainWidth / mSurfaceContainHeight;
			float originalScale = (float) mVideoOriginalWidth / mVideoOriginalHeight;
			ULog.i( "surfaceContainScale = " + surfaceContainScale + "originalScale" + originalScale);
			switch (mVideoSizeState) {
			case ORIGINAL:
				if (surfaceContainScale > originalScale) {
					// 撑满高，来计算宽
					mVideoHeight = mSurfaceContainHeight;
					mVideoWidth = (int) (originalScale * mSurfaceContainHeight);
				}
				else {
					// 撑满宽，来计算高
					mVideoWidth = mSurfaceContainWidth;
					mVideoHeight = (int) (mSurfaceContainWidth / originalScale);
				}
				break;
			case FOUR_RATIO_THREE:
				// mVideoHeight = mVideoOriginalHeight > mSurfaceContainHeight ?
				// mSurfaceContainHeight : mVideoOriginalHeight;
				mVideoHeight = mSurfaceContainHeight - mSurfaceContainHeight / 5;
				mVideoWidth = mVideoHeight * 4 / 3;
				break;
			case SIXTEEN_RATIO_NINE:
				// mVideoHeight = mVideoOriginalHeight > mSurfaceContainHeight ?
				// mSurfaceContainHeight : mVideoOriginalHeight;
				mVideoHeight = mSurfaceContainHeight - mSurfaceContainHeight / 5;
				mVideoWidth = mVideoHeight * 16 / 9;
				break;
			case FULL:
				mVideoHeight = mSurfaceContainHeight;
				mVideoWidth = mSurfaceContainWidth;
				break;
			}
		}
		else {
			mVideoHeight = mSurfaceContainHeight;
			mVideoWidth = mSurfaceContainWidth;
		}

		ULog.i( "mVideoWidth = " + mVideoWidth + "mVideoHeight = " + mVideoHeight);
	}

	/**
	 * 获得surface view 的宽度
	 * 
	 * @return
	 */
	public int getSurfaceContainWidth() {
		return this.mSurfaceContainWidth;
	}

	public void setSurfaceContainWidth(int width) {
		this.mSurfaceContainWidth = width;
	}

	/**
	 * 获得surface view 的高度
	 * 
	 * @return
	 */
	public int getSurfaceContainHeight() {
		return this.mSurfaceContainHeight;
	}

	public void setSurfaceContainHeight(int height) {
		this.mSurfaceContainHeight = height;
	}

	/**
	 * 获得视频播放的宽度
	 * 
	 * @return
	 */
	public int getVideoWidth() {
		return this.mVideoWidth;
	}

	public void setVideoWidth(int width) {
		this.mVideoWidth = width;
	}

	/**
	 * 获得视频播放的高度
	 * 
	 * @return
	 */
	public int getVideoHeight() {
		return this.mVideoHeight;
	}

	public void setVideoHeight(int height) {
		this.mVideoHeight = height;
	}

	public void prepareDefaultPlayer(String path) {
		ULog.e( "--> Mediaplayeer prepareDefaultPlayer()");

		try {
			mDefaultPlayer.setDataSource(path);
			mDefaultPlayer.setDisplay(this.defaultSurfaceHolder);
			mDefaultPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mDefaultPlayer.setScreenOnWhilePlaying(true);
			mDefaultPlayer.prepareAsync();
			mDefaultPlayer.setOnPreparedListener(mInternalOnPreparedListener);
			mDefaultPlayer.setOnCompletionListener(mInternalOnCompletionListener);
			mDefaultPlayer.setOnBufferingUpdateListener(mInternalOnBufferingUpdateListener);
			mDefaultPlayer.setOnInfoListener(mInternalOnInfoListener);
			mDefaultPlayer.setOnSeekCompleteListener(mOnSeekCompleteListener);
			mDefaultPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
			mDefaultPlayer.setOnErrorListener(mOnErrorListener);
			SetupTimer();
			ULog.i( "mDefaultPlayer init complete");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放一个新的video
	 */
	public void playNewVideo(String uri) {
		ULog.d( "--> Mediaplayer playNewVideo()");
		if (uri == null || uri.isEmpty()) {
			return;
		}
		if (mDefaultPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.resetMediaPlayer();
		}
		else {
			mDefaultPlayer = new android.media.MediaPlayer();
		}

		mUri = uri;
		prepareDefaultPlayer(uri);

		this.mPlayerState = PLAYSTATE.PREPARE;
		this.mMediaBufferingState = BUFFERSTATE.BUFFERING;

//		mCurrentPosition = mPlayData.getPlayTime();
//		if (mPlayData.getPlayTime() != 0 && mPlayData.isLive() == false) {
//			seekTo(mPlayData.getPlayTime());
//		}
		// mCurrentPosition = playedtime;
		// mMediaBufferingState = mMediaBufferingState.MUSTBUFFER;
		notifyLoadingState();
	}

	/**
	 * 重新播放现在的视频 保持原有的Duration
	 */
	private void tryPlayNowVideo(int oldDuration) {
		ULog.d( "--> Mediaplayer tryPlayNowVideo()");
		if (mDefaultPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.resetMediaPlayer();
		}
		else {
			mDefaultPlayer = new android.media.MediaPlayer();
		}

		prepareDefaultPlayer(mUri);

		this.mPlayerState = PLAYSTATE.PREPARE;
		this.mMediaBufferingState = BUFFERSTATE.BUFFERING;
		mDuration = oldDuration;

//		mCurrentPosition = mPlayData.getPlayTime();
//		if (mPlayData.getPlayTime() != 0 && mPlayData.isLive() == false) {
//			seekTo(mPlayData.getPlayTime());
//		}
		// mCurrentPosition = playedtime;
		// mMediaBufferingState = mMediaBufferingState.MUSTBUFFER;
		notifyLoadingState();
	}

	private void notifyCurrentPositionUpdate() {
		notifyCurrentPositionUpdate(0);
	}

	private void notifyCurrentPositionUpdate(int position) {
		// 缓冲状态不通知currentPosition改变了
		if (this.onCurrentPositionUpdateListener != null && mMediaBufferingState == BUFFERSTATE.NONEEDBUFFERING) {
			if (position == 0) {
				this.onCurrentPositionUpdateListener.onCurrentPositionUpdate(mCurrentPosition > mDuration ? mDuration
						: mCurrentPosition);
			}
			else {
				this.onCurrentPositionUpdateListener.onCurrentPositionUpdate(position > mDuration ? mDuration
						: position);
			}

		}

	}

	private void notifyDurationUpdate() {
		if (this.onDurationUpdateListener != null) {
			this.onDurationUpdateListener.onDurationUpdate(getDuration());
			ULog.i( "getDuration = " + mDuration);
		}

	}

	private void notifyPlayPauseStateChaged() {
		if (this.onPlayPauseStateChagedListener != null)
			this.onPlayPauseStateChagedListener.onPlayPauseStateChaged();
	}

	/**
	 * 通知页面现在的loading状态
	 */
	private void notifyLoadingState() {
		if (this.onLoadingListener != null) {

			// if (Build.VERSION.SDK_INT >=
			// Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			if (mMediaBufferingState == BUFFERSTATE.BUFFERING) {
				this.onLoadingListener.onLoading(-1);
			}
			else if (mMediaBufferingState == BUFFERSTATE.NONEEDBUFFERING) {
				this.onLoadingListener.onLoaded();
			}
			// }
			// else {
			// if (mBufferingPercent >= 100) {
			// this.onLoadingListener.onLoaded();
			// }
			// else {
			// this.onLoadingListener.onLoading(mBufferingPercent);
			// }
			//
			// }
		}

	}

	// private void notifyLoading() {
	// if (this.onLoadingListener != null)
	// this.onLoadingListener.onLoading();
	// }

	private void notifyTimeOut() {
		if (this.onTimeOutListener != null)
			this.onTimeOutListener.onTimeOut();
	}

	private void notifySurfaceViewCreatedListener() {
		if (mOnSurfaceCreatedListener != null) {
			mOnSurfaceCreatedListener.onSurfaceViewCreated();
		}
	}

	private boolean notifyOnTouchListener(View v, MotionEvent event) {
		if (mOnMPTouchListener != null) {
			mOnMPTouchListener.onTouch(v, event);
			return true;
		}
		return false;
	}

	private void notifyOnClickListener(View v) {
		if (mOnMPClickListener != null) {
			mOnMPClickListener.onClick(v);
		}
	}

	private void notifyOnLongClickListener(View v) {
		if (mOnMPLongClickListener != null) {
			mOnMPLongClickListener.onClick(v);
		}
	}

	private void notifyOnMPDoubleClickListener(View v) {
		if (mOnMPDoubleClickListener != null) {
			mOnMPDoubleClickListener.onDoubleClick(v);
		}
	}

	private void notifyOnSwitchListener(SurfaceHolder sh, int width, int height) {
		if (onSwitchListener != null) {
			onSwitchListener.onLarge(sh, width, height);
		}
	}

	// private void onCompletion(android.media.MediaPlayer paramMediaPlayer) {
	// if (this.mOnCompletionListener != null)
	// this.mOnCompletionListener.onCompletion(paramMediaPlayer);
	// }

	private void outErrorInfo() {
		ULog.e( "mDefaultPlayerState :" + this.mPlayerState);
	}

	// /** 视频播放状态 ， 在handler中使用 */
	// private abstract static interface MESSAGE {
	// public static final int START = 0;
	// public static final int MONITER = 1;
	// public static final int RELEASE = 2;
	// public static final int SEEK_TO = 3;
	// public static final int PAUSE = 4;
	// public static final int STOP = 5;
	// }

	// private enum TYPE {
	// DEFAULT, SEG;
	// }

	public void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener paramOnBufferingUpdateListener) {
		mExternalOnBufferingUpdateListener = paramOnBufferingUpdateListener;
	}

	public void setOnCompletionListener(MediaPlayer.OnCompletionListener paramOnCompletionListener) {
		this.mExternalOnCompletionListener = paramOnCompletionListener;
	}

	public void setOnCurrentPositionUpdateListener(OnCurrentPositionUpdateListener paramOnCurrentPositionUpdateListener) {
		this.onCurrentPositionUpdateListener = paramOnCurrentPositionUpdateListener;
		notifyCurrentPositionUpdate();
	}

	public void setOnDurationUpdateListener(OnDurationUpdateListener paramOnDurationUpdateListener) {
		this.onDurationUpdateListener = paramOnDurationUpdateListener;
	}

	public void setOnPlayPauseStateChagedListener(OnPlayPauseStateChagedListener paramonPlayPauseStateChagedListener) {
		this.onPlayPauseStateChagedListener = paramonPlayPauseStateChagedListener;
		onPlayPauseStateChagedListener.onPlayPauseStateChaged();
	}

	public void setOnErrorListener(MediaPlayer.OnErrorListener paramOnErrorListener) {
		// this.mOnErrorListener = paramOnErrorListener;
	}

	public void setOnInfoListener(MediaPlayer.OnInfoListener paramOnInfoListener) {
		this.mExternalOnInfoListener = paramOnInfoListener;
	}

	public void setOnLoadingListener(OnLoadingListener paramOnLoadingListener) {
		this.onLoadingListener = paramOnLoadingListener;
	}

	public void setOnPreparedListener(MediaPlayer.OnPreparedListener paramOnPreparedListener) {

		this.mExternalOnPreparedListener = paramOnPreparedListener;
	}

	// public void setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener
	// paramOnSeekCompleteListener) {
	// this.mOnSeekCompleteListener = paramOnSeekCompleteListener;
	// }

	public void setOnSwitchListener(OnSwitchListener paramOnSwitchListener) {
		this.onSwitchListener = paramOnSwitchListener;
	}

	public void setOnTimeOutListener(OnTimeOutListener paramOnTimeOutListener) {
		this.onTimeOutListener = paramOnTimeOutListener;
	}

	public void setOnTouchListener(OnMPTouchListener mListener) {
		mOnMPTouchListener = mListener;
	}

	public void setOnClickListener(OnMPClickListener mListener) {
		mOnMPClickListener = mListener;
	}

	public void setOnLongClickListener(OnMPLongClickListener mListener) {
		mOnMPLongClickListener = mListener;
	}

	public void setOnMPDoubleClickListener(OnMPDoubleClickListener mListener) {
		mOnMPDoubleClickListener = mListener;
	}

	public void setOnSurfaceCreatedListener(OnSurfaceCreatedListener mListener) {
		mOnSurfaceCreatedListener = mListener;
	}

	/**
	 * 比较播放位置和缓存位置
	 */
	// protected void compareCurrenPositonAndCurrentBuffering() {
	// mCurrentPosition = getCurrentPosition();
	// mDuration = getDuration();
	// int currentBufferingTime = mBufferingPercent * mDuration / 100;
	// ULog.i( "mCurrentPosition = " + mCurrentPosition +
	// "currentBufferingTime=" + currentBufferingTime
	// + "(currentBufferingTime-mCurrentPosition)/1000= " +
	// (currentBufferingTime - mCurrentPosition) / 1000);
	// if (mBufferingPercent == 100) {
	// onLoadingListener.onLoaded();
	// mMediaBufferingState = BUFFERSTATE.NONEEDBUFFERING;
	// // CancelTimerForListnerCurrentPosition();
	// return;
	// }
	// if (mMediaBufferingState == BUFFERSTATE.NONEEDBUFFERING) {
	// // 缓存小于等于1秒进入loading状态，大于等于10秒出loading状态
	// if (currentBufferingTime - mCurrentPosition <= 1 * 1000) {
	// // 需要缓存一会再继续播放
	// onLoadingListener.onLoading((currentBufferingTime - mCurrentPosition) /
	// 100);
	// mMediaBufferingState = BUFFERSTATE.BUFFERING;
	// }
	// }
	// else if (mMediaBufferingState == BUFFERSTATE.BUFFERING) {
	// if (currentBufferingTime - mCurrentPosition >= 10 * 1000) {
	// // 直接播放
	// onLoadingListener.onLoaded();
	// mMediaBufferingState = BUFFERSTATE.NONEEDBUFFERING;
	// }
	// else {
	// ULog.i( "percent = " + (currentBufferingTime - mCurrentPosition) /
	// 100 / 1);
	// onLoadingListener.onLoading((currentBufferingTime - mCurrentPosition) /
	// 100 / 1);
	// mMediaBufferingState = BUFFERSTATE.BUFFERING;
	// }
	// }
	//
	// }

	public abstract static interface OnSwitchListener {
		public abstract void onLarge(SurfaceHolder holder, int width, int height);

		public abstract void onResizeCurrent(int width, int height);

		public abstract void onSmall(SurfaceHolder holder);

		public abstract void onSwitch(SurfaceHolder holder1, SurfaceHolder holder2, int width, int height);
	}

	public abstract static interface OnMPTouchListener {
		public abstract boolean onTouch(View v, MotionEvent event);

	}

	public abstract static interface OnMPClickListener {
		public abstract boolean onClick(View v);

	}

	public abstract static interface OnMPLongClickListener {
		public abstract boolean onClick(View v);

	}

	public abstract static interface OnMPDoubleClickListener {
		public abstract void onDoubleClick(View v);
	}

	public abstract static interface OnMPScrollLeftAndRightListener {
		public abstract boolean onScroll(View v, float preX, float nowX);

	}

	public abstract static interface OnMPScrollUpAndDownListener {
		public abstract boolean onScroll(View v, float preY, float nowY);

	}

	/**
	 * 接口 用于监听 SurfaceView 是否创建
	 * 
	 */
	public abstract static interface OnSurfaceCreatedListener {
		public abstract void onSurfaceViewCreated();
	}

	public abstract static interface OnTimeOutListener {
		public abstract void onTimeOut();
	}

	public abstract static interface OnDurationUpdateListener {
		public abstract void onDurationUpdate(int paramInt);
	}

	public abstract static interface OnCurrentPositionUpdateListener {
		public abstract void onCurrentPositionUpdate(int currentPosition);
	}

	public abstract static interface OnPlayPauseStateChagedListener {
		public abstract void onPlayPauseStateChaged();
	}

	public abstract static interface OnLoadingListener {
		public abstract void onLoaded();

		public abstract void onLoading(int percent);
	}

	class InternalOnErrorListener implements MediaPlayer.OnErrorListener {
		@Override
		public boolean onError(android.media.MediaPlayer mp, int what, int extra) {
			return false;
		}
	}

	private void initPlayerInternalListener() {
		mInternalOnBufferingUpdateListener = new OnBufferingUpdateListener() {

			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				// Auto-generated
				// method
				// stub
				ULog.w( "percent = " + percent);
				mBufferingPercent = percent;
				// compareCurrenPositonAndCurrentBuffering();
				if (mExternalOnBufferingUpdateListener != null) {
					mExternalOnBufferingUpdateListener.onBufferingUpdate(mp, percent);

				}

			}
		};
		mInternalOnPreparedListener = new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				ULog.d( "--> OnPreparedListener onPrepared()");
				mPlayerState = PLAYSTATE.PREPARED;
				if (mSeekWhenPrepared != 0) {
					mediaPlayer.seekTo(mSeekWhenPrepared);
					mSeekWhenPrepared = 0;
				}
				mExternalOnPreparedListener.onPrepared(mp);
				mediaPlayer.start();
				// 加入这个是为了2.2~4.0之间的版本必须播放器在Perpared之后调用才有效
				// changeVideoSize(mVideoWidth, mVideoHeight);
			}
		};
		mInternalOnCompletionListener = new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				ULog.d( "onCompletion first mCurrentPosition = " + mCurrentPosition + "mDuration =" + mDuration);
				// mPlayerState = PLAYSTATE.ERROR;
				// return;
				// if (mPlayerState == PLAYSTATE.PREPARED) {
				// }

				// 不知道为什么有的手机播放器一播放视频直接回收到onCompletion 或者直播就不应该会自动关闭
				if ((mCurrentPosition == 0 && mDuration == 0) ) {
					ULog.d( "onCompletion not normal");
					myHandler.removeMessages(tryRestartMedia);
					myHandler.sendEmptyMessageDelayed(tryRestartMedia, 3000);
					mPlayerState = PLAYSTATE.ERROR;
					return;
				}

				boolean isEnd = false;
				if ((mCurrentPosition > 0 && mDuration > 0) && mCurrentPosition <= mDuration
						&& mCurrentPosition + 2000 >= mDuration) {
					isEnd = true;
				}
				else {
					// 非正常关闭，重新启动MediaPlayer来进行继续播放
					ULog.d( "onCompletion not normal");
					myHandler.removeMessages(tryRestartMedia);
					myHandler.sendEmptyMessageDelayed(tryRestartMedia, 3000);
					mPlayerState = PLAYSTATE.ERROR;
				}
				if (isEnd ) {
					ULog.d( "onCompletion really ");
					mPlayerState = PLAYSTATE.IDLE;
					
					mediaPlayer.releaseMediaPlayer();
					mExternalOnCompletionListener.onCompletion(mp);
				}

			}
		};
		mOnSeekCompleteListener = new OnSeekCompleteListener() {

			@Override
			public void onSeekComplete(MediaPlayer mp) {
				ULog.d( "--> OnSeekCompleteListener onSeekComplete()");
				mediaPlayer.start();
			}
		};
		mInternalOnInfoListener = new OnInfoListener() {

			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {
				ULog.d( "OnInfoListener  what = " + what + "extra = " + extra);
				switch (what) {
				case MediaPlayer.MEDIA_INFO_BUFFERING_START:
					myHandler.sendEmptyMessage(loadingStart);
					myHandler.removeMessages(loadingEnd);
					myHandler.sendEmptyMessageDelayed(loadingEnd, 3000);
					break;
				case MediaPlayer.MEDIA_INFO_BUFFERING_END:
					myHandler.removeMessages(loadingEnd);
					myHandler.sendEmptyMessage(loadingEnd);
					break;
				case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
					mediaPlayer.pause();
					// 让其重新加载一下
					seekTo(mCurrentPosition);
					break;
				default:
					break;
				}
				mExternalOnInfoListener.onInfo(mp, what, extra);
				return false;
			}
		};
		mOnErrorListener = new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// 遇到问题，进行重新播放。
				ULog.d( "onError  what = " + what + "extra = " + extra);
				// myHandler.removeMessages(tryRestartMedia);
				// myHandler.sendEmptyMessageDelayed(tryRestartMedia, 1000);
				mPlayerState = PLAYSTATE.ERROR;
				return false;
			}
		};
		mOnVideoSizeChangedListener = new OnVideoSizeChangedListener() {

			@Override
			public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
				mVideoOriginalWidth = width;
				mVideoOriginalHeight = height;
				if (mVideoOriginalWidth != 0 && mVideoOriginalHeight != 0) {
					setVideoSizeRatio(mVideoSizeState);
					notifyOnSwitchListener(defaultSurfaceHolder, width, height);
				}

			}
		};
		mInternalOnTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				boolean rv = false;
				rv = notifyOnTouchListener(v, event);
				// 如果用戶沒有处理
				if (rv == false) {
					mGestureDetector.onTouchEvent(event);
				}
				// return rv;
				return true;
			}

		};
		// mInternalOnClickListener = new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// notifyOnClickListener(v);
		// }
		// };
		// mInternalOnLongClickListener = new OnLongClickListener() {
		//
		// @Override
		// public boolean onLongClick(View v) {
		// notifyOnLongClickListener(v);
		// return true;
		// }
		// };

	}

	public void setHolder(SurfaceHolder holder) {
		defaultSurfaceHolder = holder;
	}

	/**
	 * 判断现在是否是loading状态，如果是什么时候停止loading
	 */
	protected void doNotifyLoadingListener() {

		// 如果正在loading状态
		if (mMediaBufferingState == BUFFERSTATE.BUFFERING) {
			ULog.d( "mCurrentPosition =  " + mCurrentPosition + "mLoadingStartPosition" + mLoadingStartPosition);
			if (mCurrentPosition > mLoadingStartPosition + 300) {
				mMediaBufferingState = BUFFERSTATE.NONEEDBUFFERING;
			}
			notifyLoadingState();
		}

	}

	/**
	 * 设置播放器要显示的VideoView
	 * 
	 * @param view
	 */
	public void setContentView(VideoView view) {
		mVideoView = view;
		mVideoViewParent = (View) view.getParent();
		mVideoViewParent.setOnTouchListener(mInternalOnTouchListener);
		// mVideoView.setOnClickListener(mInternalOnClickListener);
		// mVideoView.setSoundEffectsEnabled(false);
		// mVideoView.setOnLongClickListener(mInternalOnLongClickListener);

		initGestureDetector();

	}

	/**
	 * 设置播放器的显示宽宽和高度并且与surface view 一样
	 * 
	 * @param width
	 * @param height
	 */
	public void setFixedSize(int width, int height, boolean mIsLandscape) {
		ULog.i( "setFixedSize SCREEN_WIDTH = " + width + "SCREEN_HEIGHT =" + height);
		this.mIsLandscape = mIsLandscape;
		setSurfaceContainWidth(width);
		setSurfaceContainHeight(height);
		// setVideoWidth(width);
		// setVideoHeight(height);

		// setVideoSizeRatio(VIDEOSIZESTATE.ORIGINAL);
		changeSurfaceContainSize();

	}

	/**
	 * 只改变播放器显示的大小，不改变surface view 的大小
	 * 
	 * @param width
	 * @param height
	 */
	public void changeVideoSize(int width, int height) {
		if (mVideoView != null && defaultSurfaceHolder != null && mIsSurfaceCreated != false) {
			setVideoWidth(width);
			setVideoHeight(height);
			defaultSurfaceHolder.setFixedSize(mVideoWidth, mVideoHeight);
		}

	}

	/**
	 * 改变播放器的显示容器大小
	 */
	private void changeSurfaceContainSize() {
		if (mVideoView != null && defaultSurfaceHolder != null && mIsSurfaceCreated != false) {
			mVideoViewParent.setLayoutParams(new FrameLayout.LayoutParams(mSurfaceContainWidth, mSurfaceContainHeight));
		}
		if (mPlayerState == PLAYSTATE.PREPARED) {
			setVideoSizeRatio(mVideoSizeState);
			changeVideoSize(mVideoWidth, mVideoHeight);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		ULog.v( "surfaceChanged holder:" + holder + "  " + "format:" + format + "  " + "width:" + width + "  "
				+ "height:" + height);
		// surface view 的大小改变
	}

	@Override
	public void surfaceCreated(SurfaceHolder mholder) {
		ULog.v( "surfaceCreated = " + mholder);
		// mMediaplayer.SetHolder(holder);
		// mMediaplayer.prepareDefaultPlayer();
		setHolder(mholder);
		notifySurfaceViewCreatedListener();
		mIsSurfaceCreated = true;
		changeSurfaceContainSize();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		ULog.v( "surfaceDestroyed = " + holder);
		// releaseMediaPlayer();
		mIsSurfaceCreated = false;
	}

	/**
	 * surfaceView 是否是可用的
	 * 
	 * @return
	 */
	public boolean isSurfaceViewCreated() {
		return mIsSurfaceCreated;
	}

	/**
	 * 启动一个定时器进行播放位置的实时监听
	 */
	private void SetupTimer() {

		if (mTimer != null) {
			CancelTimer();
		}

		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				ULog.d( "mIsDefaultPlayerReleasing = " + mIsDefaultPlayerReleasing);
				if (mPlayerState != PLAYSTATE.PREPARED) {
					notifyLoadingState();
					return;
				}
				if (mDefaultPlayer == null || mIsDefaultPlayerReleasing == true) {
					return;
				}
				int currentPosition = mDefaultPlayer.getCurrentPosition();
				if (currentPosition < 0) {
					return;
				}
				ULog.d( "mTimer is run currentPosition = " + mDefaultPlayer.getCurrentPosition()
						+ "PreviousPosition = " + mediaPlayer.getPreviousPosition());

				// 对于RTSP的回看会一直播放下去，而不是到了时间自动关闭的问题修复
				if (currentPosition != 0 && mDuration != 0 && currentPosition >= mDuration + 1000
						) {
					// mInternalOnCompletionListener.onCompletion(mDefaultPlayer);
				}
				// 判断播放器现在位置是否发生改变
				if (currentPosition != mediaPlayer.getPreviousPosition()) {
					mCurrentPosition = currentPosition;
					myHandler.sendEmptyMessage(notifyCurrentPositionChange);
				}
				doNotifyLoadingListener();
				myHandler.sendEmptyMessage(notifyDurationChange);
			}
		}, 0, 1000);

	}

	/**
	 * 关闭一个Timer
	 */
	private void CancelTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

	}

	/**
	 * 初始化手势
	 */
	@SuppressWarnings("deprecation")
	private void initGestureDetector() {
		// 因为双击会影响单机的反应速度，如果不注册双击，让单机的反应速度加快，提高用户体验
		// 两套机制，一种没有双击，一种有双击
		if (mOnMPDoubleClickListener == null) {
			mGestureDetector = new GestureDetector(new MyGesture());
		}
		else {
			mGestureDetector = new GestureDetector(new MySimpleGesture());
		}

	}

	private class MyGesture implements OnGestureListener {

		@Override
		public boolean onDown(MotionEvent arg0) {
			return false;
		}

		@Override
		public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent arg0) {
			notifyOnLongClickListener(mVideoView);
		}

		@Override
		public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent arg0) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent arg0) {
			notifyOnClickListener(mVideoView);
			return true;
		}

	}

	private class MySimpleGesture extends SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			// 双击
			ULog.d( "onDoubleTap e =  ");
			notifyOnMPDoubleClickListener(mVideoView);
			return super.onDoubleTap(e);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// 长按
			ULog.d( "onLongPress e =  ");
			notifyOnLongClickListener(mVideoView);
			super.onLongPress(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			ULog.d( "onSingleTapConfirmed e =  ");
			// 等于onclick一次点击
			notifyOnClickListener(mVideoView);
			return super.onSingleTapConfirmed(e);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

	}

	private Handler	myHandler	= new Handler() {
									@Override
									public void handleMessage(Message msg) {
										super.handleMessage(msg);
										switch (msg.what) {
										case loadingStart:
											mediaPlayer.pause();
											onLoadingListener.onLoading(-1);
											break;
										case loadingEnd:
											mediaPlayer.start();
											onLoadingListener.onLoaded();
											break;
										case tryRestartMedia:
											tryPlayNowVideo(mDuration);
											break;
										case notifyDurationChange:
											notifyDurationUpdate();
											break;
										case notifyCurrentPositionChange:
											notifyCurrentPositionUpdate();
											break;

										}
									}
								};

}
