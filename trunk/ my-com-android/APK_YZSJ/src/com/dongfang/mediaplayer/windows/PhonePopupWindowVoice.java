package com.dongfang.mediaplayer.windows;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.dongfang.mediaplayer.constants.Constants;
import com.dongfang.utils.ULog;
import com.dongfang.utils.Util;
import com.dongfang.view.MyVerticalSeekBar;
import com.dongfang.view.MyVerticalSeekBar.OnSeekBarChangeListener;
import com.dongfang.yzsj.R;

/**
 * O
 * 
 * @author yanghua
 * 
 */
public class PhonePopupWindowVoice extends PopupWindow {

	public static final String TAG = PhonePopupWindowVoice.class.getName();
	private Context mContext;
	private LayoutInflater mInflater;
	private final float mWidthPercentOfWindow = (float) 0.06;
	private final float mHightPercentOfWindow = (float) 0.45;

	private AudioManager mAudioManager;
	private MyVerticalSeekBar mVoiceSeekBar;

	private final int dissmissSelf = 0;
	private final int progressChanged = 1;
	private BasePopupWindow basePop = BasePopupWindow.getInstance();

	public PhonePopupWindowVoice(Context context) {
		super(context);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		View contentView = mInflater.inflate(R.layout.popupwin_voice, null);
		initView(contentView);
		setupView();
		this.setContentView(contentView);
	}

	private void initView(View view) {

		this.setTouchable(true);
		// this.setOutsideTouchable(true);
		// this.setAnimationStyle(R.style.bottom_popupwin_anim_style);
		view.setFocusableInTouchMode(true);

		this.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// mContext.unregisterReceiver(receiver);
			}
		});

		mVoiceSeekBar = (MyVerticalSeekBar) view.findViewById(R.id.sb_voice);

	}

	/**
	 * 设置音量条进度
	 * 
	 * @param progress
	 */
	public void setVoiceProgress(int progress) {
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		progress = (progress < 1) ? 0 : progress;
		progress = (progress > maxVolume) ? maxVolume : progress;

		mVoiceSeekBar.setProgress(progress);
	}

	/**
	 * 获取现在音量值
	 * 
	 * @return
	 */
	public int getVolumSeekBarProgress() {
		return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	/**
	 * 
	 * 后台播放音乐， 前台若进入视频播放， 就只播放前台视频的声音
	 * 
	 * @return AudioManager
	 */
	public AudioManager getAudioManager() {
		if (mAudioManager == null)
			mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

		if (mAudioManager != null && android.os.Build.VERSION.SDK_INT > 7) {
			ULog.i("Request audio focus");
			int ret = mAudioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
				public void onAudioFocusChange(int focusChange) {
					ULog.d("focusChange =" + focusChange);
					// Do something
				}
			}, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

			if (ret != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
				ULog.i("request audio focus fail. " + ret);
			}

			// android.provider.Settings.System.putString(mContext.getContentResolver(),
			// "headsetowner", TAG);
		}
		return mAudioManager;
	}

	private void setupView() {
		mAudioManager = getAudioManager();
		// mAudioManager = (AudioManager)
		// mContext.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		mVoiceSeekBar.setMax(maxVolume);
		mVoiceSeekBar.setProgress(currentVolume);

		ULog.d("maxVolume=" + maxVolume + "currentVolume=" + currentVolume);

		mVoiceSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(MyVerticalSeekBar verticalSeekBar) {
				ULog.d("onStopTrackingTouch progress=" + verticalSeekBar.getProgress());

			}

			@Override
			public void onStartTrackingTouch(MyVerticalSeekBar verticalSeekBar) {

			}

			@Override
			public void onProgressChanged(MyVerticalSeekBar verticalSeekBar, int progress, boolean fromUser) {
				ULog.d("progress=" + progress + "   fromUser =" + fromUser);

				if (progress == 0) {
					basePop.sendMessage(BasePopupWindow.SET_VOICE_MUTE_ICON, null);
				}
				else {
					basePop.sendMessage(BasePopupWindow.SET_VOICE_ICON, null);
				}
				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

				myHandler.sendEmptyMessage(progressChanged);

			}
		});

	}

	public void show(int xOffset, int yOffset) {
		int marginWidth = Util.dip2px(xOffset);
		int marginHight = Util.dip2px(yOffset);

		this.setWidth((int) (Constants.SCREEN_WIDTH_LANDSCAPE * mWidthPercentOfWindow));
		this.setHeight((int) (Constants.SCREEN_HEIGHT_LANDSCAPE * mHightPercentOfWindow));

		this.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.transparent_background));

		marginWidth += 0;

		this.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.RIGHT | Gravity.CENTER_VERTICAL,
				marginWidth, marginHight);
		this.update();

		// IntentFilter filter = new
		// IntentFilter("android.media.VOLUME_CHANGED_ACTION");
		// mContext.registerReceiver(receiver, filter);

		// dissmissSelfDelay();
	}

	public void show() {
		show(0, 0);
	}

	// private BroadcastReceiver receiver = new BroadcastReceiver() {
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// if ("android.media.VOLUME_CHANGED_ACTION"
	// .equals(intent.getAction())) {
	//
	// int type = intent.getIntExtra(
	// "android.media.EXTRA_VOLUME_STREAM_TYPE", 0);
	// int index = intent.getIntExtra(
	// "android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
	// int oldIndex = intent.getIntExtra(
	// "android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
	// mVoiceSeekBar.setProgress(index);
	// dissmissSelfDelay();
	//
	// ULog.d( "type=" + type + "   index=" + index
	// + "    oldIndex=" + oldIndex);
	//
	// }
	// }
	// };

	/**
	 * 立即自动消失
	 */
	protected void dissmissSelf() {
		this.dismiss();
	}

	/**
	 * 延迟自动消失
	 */
	protected void dissmissSelfDelay() {
		myHandler.removeMessages(dissmissSelf);
		myHandler.sendEmptyMessageDelayed(dissmissSelf, 3000);
	}

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case dissmissSelf:
				dissmissSelf();
				break;
			case progressChanged:
				basePop.sendMessage(BasePopupWindow.VOICE_SEEKBAR_IS_WORKING, null);
				break;

			}
		}

	};

}
