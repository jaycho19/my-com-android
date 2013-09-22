package com.dongfang.mediaplayer.windows;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dongfang.mediaplayer.Mediaplayer;
import com.dongfang.mediaplayer.Mediaplayer.OnCurrentPositionUpdateListener;
import com.dongfang.mediaplayer.Mediaplayer.OnDurationUpdateListener;
import com.dongfang.mediaplayer.Mediaplayer.OnPlayPauseStateChagedListener;
import com.dongfang.mediaplayer.constants.Constants;
import com.dongfang.utils.ULog;
import com.dongfang.utils.Util;
import com.dongfang.yzsj.R;

/**
 * 
 * @author dongfang
 * 
 */
public class PhonePopupWindowBottomBar extends PopupWindow implements OnClickListener, OnLongClickListener,
		OnCurrentPositionUpdateListener, OnPlayPauseStateChagedListener, OnBufferingUpdateListener,
		OnDurationUpdateListener {
	public static final String	TAG						= PhonePopupWindowBottomBar.class.getName();

	private Context				mContext;
	private LayoutInflater		mInflater;
	private float				mPercentOfWindow		= (float) 0.28;
	private TextView			mPlayedTime;
	private TextView			mTotalTime;
	private Mediaplayer			mMediaplayer			= Mediaplayer.getInstance();
	// private Timer mTimer;
	// private int mCurrentBufferPercentage;
	private SeekBar				skbProgress;
	/** 推送到TV按钮 */
//	private Button				btnPush2tv;
	private ImageButton			btnPlayPause;
	private ImageButton			btnBackwardPlay;
	private ImageButton			btnForwardPlay;
//	private ImageButton			btnFullScreen2Half;
	/** 选择视频码率（清晰度） */
//	private Button				btnVideoBitRate;
	/** 选择视频集数 */
//	private Button				btnEpisode;

	/** 播放，快进，快退的容器 */
	private View				viewMediaControl;

	private String				mCurrentVideoBitRate	= new String();
	private String				mCurrentResolutionText	= new String();

	private BasePopupWindow		basePop					= BasePopupWindow.getInstance();

	private final int			progressChanged			= 0;
	private final int			progressChangedFromUser	= 1;

	private int					mDuration;
	private int					mCurrentPosition;

	// private final int ISBUFFERORNOT = 1;

	public PhonePopupWindowBottomBar(Context context) {
		super(context);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		View contentView = mInflater.inflate(R.layout.popupwin_bottom, null);
		initView(contentView);
		setupView();
		this.setContentView(contentView);
	}

	/**
	 * 显示popupwindow
	 * 
	 * @param xOffset
	 * @param yOffset
	 */
	public void show(int xOffset, int yOffset) {

		// 为了适应各机型分辨率转成DP
		int marginWidth = Util.dip2px(xOffset);
		int marginHight = Util.dip2px(yOffset);

		this.setWidth(Constants.SCREEN_WIDTH_LANDSCAPE);
		this.setHeight(LayoutParams.WRAP_CONTENT);

		this.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.transparent_background));

		this.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.BOTTOM, marginWidth, marginHight);
		this.update();

		initMediaPlayer();

		// SetupTimer();
		// if (mPlayData.getPlayType().equals(Types.PLAYTYPE_LIVE) == false) {
		// SetupTimer();
		// }

	}

	/**
	 * 设置播放器的一些监听事件
	 */
	private void initMediaPlayer() {
		// 设置监听现在播放位置改变
		mMediaplayer.setOnCurrentPositionUpdateListener(this);
		mMediaplayer.setOnDurationUpdateListener(this);
		mMediaplayer.setOnPlayPauseStateChagedListener(this);
		mMediaplayer.setOnBufferingUpdateListener(this);
	}

	/**
	 * 显示popupwindow
	 */
	public void show() {

		show(0, 0);
	}

	/**
	 * 初始化一些popupwindow的属性
	 * 
	 * @param contentView
	 */
	private void initView(View view) {
		// this.setFocusable(true);
		this.setTouchable(true);
		// this.setOutsideTouchable(true);
		// this.setAnimationStyle(R.style.bottom_popupwin_anim_style);
		view.setFocusableInTouchMode(true);
		this.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// CancelTimer();
			}
		});

		// mMediaplayer.setOnBufferingUpdateListener(new
		// OnBufferingUpdateListener() {
		// @Override
		// public void onBufferingUpdate(android.media.MediaPlayer mp, int
		// percent) {
		// mCurrentBufferPercentage = percent;
		// }
		// });

		mPlayedTime = (TextView) view.findViewById(R.id.played_time);
		mTotalTime = (TextView) view.findViewById(R.id.total_time);
		skbProgress = (SeekBar) view.findViewById(R.id.video_seekbar);
//		btnPush2tv = (Button) view.findViewById(R.id.btn_push_to_tv);
		btnPlayPause = (ImageButton) view.findViewById(R.id.btn_play_pause);
		btnBackwardPlay = (ImageButton) view.findViewById(R.id.btn_backwardplay);
		btnForwardPlay = (ImageButton) view.findViewById(R.id.btn_forwardplay);
//		btnVideoBitRate = (Button) view.findViewById(R.id.btn_video_bit_rate);
//		btnEpisode = (Button) view.findViewById(R.id.btn_episode);
//		btnFullScreen2Half = (ImageButton) view.findViewById(R.id.btn_fullscreen2half);

		viewMediaControl = view.findViewById(R.id.mc_container);

	}

	private void setupView() {

			// mPercentOfWindow = (float) 0.28;

			btnPlayPause.setOnClickListener(this);
			btnBackwardPlay.setOnClickListener(this);
			btnForwardPlay.setOnClickListener(this);

			btnBackwardPlay.setOnLongClickListener(this);
			btnForwardPlay.setOnLongClickListener(this);

			skbProgress.setOnSeekBarChangeListener(new VideoSeekBarListener());

		



	}

	// /**
	// * 启动一个定时器进行播放位置的实时监听
	// */
	// private void SetupTimer() {
	//
	// if (mTimer == null) {
	// mTimer = new Timer();
	// mTimer.schedule(new TimerTask() {
	//
	// @Override
	// public void run() {
	// ULog.d(TAG, "mTimer is run ");
	// if (mMediaplayer != null) {
	// myHandler.sendEmptyMessage(PROGRESS_CHANGED);
	//
	// }
	//
	// }
	// }, 0, 1000);
	// }
	//
	// }

	// /**
	// * 关闭一个Timer
	// */
	// private void CancelTimer() {
	// if (mTimer != null) {
	// mTimer.cancel();
	// mTimer = null;
	// }
	//
	// }

	@Override
	public void onCurrentPositionUpdate(int currentPosition) {
		String time = Util.formatTime(currentPosition / 1000);
		this.mCurrentPosition = currentPosition;
		mPlayedTime.setText(time);
		myHandler.sendEmptyMessage(progressChanged);
	}

	@Override
	public void onDurationUpdate(int mDuration) {
		this.mDuration = mDuration;
		String totalTime = Util.formatTime(mDuration / 1000);
		mTotalTime.setText(totalTime);
	}

	@Override
	public void onPlayPauseStateChaged() {
		changePlayPauseBtnIcon();
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			skbProgress.setSecondaryProgress(percent * 10);
		}
		else {
				skbProgress.setSecondaryProgress(percent * 10);
		}

	}

	public void setProgressAndBufferingUpdate() {

		int position = mCurrentPosition;
		int duration = mDuration;
		// mCurrentBufferPercentage = mMediaplayer.getCurrentBufferingUpdate();

		ULog.w(TAG, "CurrentPosition = " + position + "; Duration = " + duration + " buffer = ");

		if (duration > 0) {
			int pos = skbProgress.getMax() * (position / 1000) / (duration / 1000);
			skbProgress.setProgress(pos);
			// skbProgress.setSecondaryProgress(mCurrentBufferPercentage * 10);
			String time = Util.formatTime(position / 1000);
			mPlayedTime.setText(time);
		}
		// 实时改变播放按钮状态 加上这句话会很慢，用其他方式解决自动变化按键
		// changePlayPauseBtnIcon();
	}

	class VideoSeekBarListener implements SeekBar.OnSeekBarChangeListener {
		private long	duration	= 0;
		private long	newposition	= 0;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (fromUser == false) {
				return;
			}
			duration = mDuration;
			newposition = (duration * progress) / 1000L;
			ULog.d(TAG, "duration    = " + duration);
			ULog.d(TAG, "CurPosition = " + mMediaplayer.getCurrentPosition());
			ULog.d(TAG, "newposition = " + newposition);

			if (mPlayedTime != null && newposition > 0) {
				String time = Util.formatTime(newposition / 1000);
				mPlayedTime.setText(time);
			}

			myHandler.sendEmptyMessage(progressChangedFromUser);

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			if (mMediaplayer != null && mMediaplayer.isPlaying())
				mMediaplayer.pause();
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			ULog.d(TAG, "newposition = " + newposition);
			if (mMediaplayer == null)
				return;

			mMediaplayer.seekTo((int) newposition);
			if (!mMediaplayer.isPlaying()) {
				mMediaplayer.start();
			}

		}

	}

	private void resetSeekBar() {
		ULog.d(TAG, "resetSeekBar ");
		mPlayedTime.setText("00:00");
		mTotalTime.setText("00:00:00");

		skbProgress.setSecondaryProgress(0);
		skbProgress.setProgress(0);

	}


	/**
	 * 根据播放器状态来显示播放或者暂停按键
	 */
	private void changePlayPauseBtnIcon() {
		if (btnPlayPause.isEnabled() == false) {
			return;
		}
		if (mMediaplayer.isPlaying()) {
			btnPlayPause.setImageResource(R.drawable.pause_icon);
		}
		else {
			btnPlayPause.setImageResource(R.drawable.video_play_icon);
		}
	}

	@Override
	public void onClick(View v) {
		ULog.d(TAG, "onClick ");
		switch (v.getId()) {
		case R.id.btn_backwardplay:
			basePop.sendMessage(BasePopupWindow.PRESS_BACKWARDPLAY_BTN, null);
			break;
		case R.id.btn_play_pause:
			basePop.sendMessage(BasePopupWindow.PRESS_PLAY_PAUSE_BTN, null);
			break;
		case R.id.btn_forwardplay:
			basePop.sendMessage(BasePopupWindow.PRESS_FORWARDPLAY_BTN, null);
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		ULog.d(TAG, "onLongClick ");
		switch (v.getId()) {

		case R.id.btn_backwardplay:
			basePop.sendMessage(BasePopupWindow.LONGPRESS_BACKWARDPLAY_BTN, null);
			return true;

		case R.id.btn_forwardplay:
			basePop.sendMessage(BasePopupWindow.LONGPRESS_FORWARDPLAY_BTN, null);
			return true;
		}
		return false;
	}

	private Handler	myHandler	= new Handler() {
									@Override
									public void handleMessage(Message msg) {
										super.handleMessage(msg);
										switch (msg.what) {
										case progressChanged:
											setProgressAndBufferingUpdate();
											break;
										case progressChangedFromUser:
											basePop.sendMessage(BasePopupWindow.VIDEO_SEEKBAR_IS_WORKING, null);
											break;

										}
									}
								};

}
