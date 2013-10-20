package com.dongfang.mediaplayer.windows;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.dongfang.mediaplayer.Mediaplayer;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.fragment.VideoPlayerFragment;

/**
 * 
 * @author yanghua
 * 
 */
public class BasePopupWindow {

	// public static final int SHOW_OFFSIDE_BAR = 100;
	// public static final int OFFSIDE_CHANGE = 101;
	// public static final int USER_LOGIN = 102;
	// public static final int VIDEO_QUALITY = 103;
	// public static final int IKU = 104;
	// public static final int HIDE_SETTING = 105;
	// public static final int SHOW_VIDEO_QUALITY_SETTING = 106;
	// public static final int SHOW_PLAY_SETTING_BAR = 1001;

	// ====================================================================
	// for Phone_PopupWindow_VideoRate_Setting 设置 原画 超清 高清 标清
	public static final int SET_VIDEO_ORIGINALPICTURE = 0;
	public static final int SET_VIDEO_SUPERDEFINITION = 1;
	public static final int SET_VIDEO_HIGHDEFINITION = 2;
	public static final int SET_VIDEO_STANDARDDEFINITION = 3;
	// for show or hide Phone_PopupWindow_VideoRate_Setting
	public static final int SHOW_HIDE_VIDEORATE_SETTING = 4;
	// for show or hide Phone_PopupWindow_VideoList
	public static final int SHOW_HIDE_VIDEOLIST = 5;
	// for show or hide Phone_PopupWindow_Voice
	public static final int SHOW_HIDE_VOICE = 6;

	public static final int PRESS_PLAY_PAUSE_BTN = 7;
	public static final int PRESS_BACKWARDPLAY_BTN = 8;
	public static final int PRESS_FORWARDPLAY_BTN = 9;
	public static final int PRESS_FULLSCREEN2HALF_BTN = 10;
	public static final int PRESS_LOCK_BTN = 11;
	public static final int PRESS_BACK_BTN = 12;

	public static final int LONGPRESS_BACKWARDPLAY_BTN = 13;
	public static final int LONGPRESS_FORWARDPLAY_BTN = 14;

	public static final int SET_VOICE_MUTE_ICON = 15;
	public static final int SET_VOICE_ICON = 16;

	public static final int PRESS_PUSH2TV_BTN = 17;
	public static final int PRESS_DOWNLOAD_BTN = 18;

	/**
	 * 此功能是在用户使用音量seekbar和播放seekbar时，让popupwin延迟消失
	 */
	public static final int VIDEO_SEEKBAR_IS_WORKING = 19;
	public static final int VOICE_SEEKBAR_IS_WORKING = 20;
	public static final int VIDEOLIST_POPUPWIN_IS_WORKING = 21;

	public static final int VIDEO_LIST_SELECTED = 22;

	// ====================================================================

	// ====================================================================
	// phoneBottomBar上控件定义 进行显示
	/**
	 * 显示推送大屏
	 */
	public static final int SHOW_BOTTOM_BAR_PUSH2TV = 2000;
	/**
	 * 显示快退
	 */
	public static final int SHOW_BOTTOM_BAR_BACKWARDPLAY = 2001;
	/**
	 * 显示播放/暫停
	 */
	public static final int SHOW_BOTTOM_BAR_PLAYPAUSE = 2002;
	/**
	 * 显示快进
	 */
	public static final int SHOW_BOTTOM_BAR_FORWARDPLAY = 2003;
	/**
	 * 显示选择视频码率（清晰度）
	 */
	public static final int SHOW_BOTTOM_BAR_VIDEOBITRATE = 2004;
	/**
	 * 显示选择视频集数
	 */
	public static final int SHOW_BOTTOM_BAR_EPISODE = 2005;
	/**
	 * 显示播放时间
	 */
	public static final int SHOW_BOTTOM_BAR_PLAYEDTIME = 2006;
	/**
	 * 显示视频总时间
	 */
	public static final int SHOW_BOTTOM_BAR_TOTALTIME = 2007;
	/**
	 * 显示播放进度条
	 */
	public static final int SHOW_BOTTOM_BAR_SEEKBAR = 2008;

	// phoneBottomBar上控件定义 进行隐藏
	/**
	 * 隐藏推送大屏
	 */
	public static final int HIDE_BOTTOM_BAR_PUSH2TV = 3000;
	/**
	 * 隐藏快退
	 */
	public static final int HIDE_BOTTOM_BAR_BACKWARDPLAY = 3001;
	/**
	 * 隐藏播放/暫停
	 */
	public static final int HIDE_BOTTOM_BAR_PLAYPAUSE = 3002;
	/**
	 * 隐藏快进
	 */
	public static final int HIDE_BOTTOM_BAR_FORWARDPLAY = 3003;
	/**
	 * 隐藏选择视频码率（清晰度）
	 */
	public static final int HIDE_BOTTOM_BAR_VIDEOBITRATE = 3004;
	/**
	 * 隐藏选择视频集数
	 */
	public static final int HIDE_BOTTOM_BAR_EPISODE = 3005;
	/**
	 * 隐藏播放时间
	 */
	public static final int HIDE_BOTTOM_BAR_PLAYEDTIME = 3006;
	/**
	 * 隐藏视频总时间
	 */
	public static final int HIDE_BOTTOM_BAR_TOTALTIME = 3007;
	/**
	 * 隐藏播放进度条
	 */
	public static final int HIDE_BOTTOM_BAR_SEEKBAR = 3008;

	// ====================================================================
	// ====================================================================
	// phoneRightBar上控件定义 进行显示
	/**
	 * 显示下载
	 */
	public static final int SHOW_RIGHT_BAR_DOWNLOAD = 4000;
	/**
	 * 显示音量
	 */
	public static final int SHOW_RIGHT_BAR_VOICE = 4001;
	/**
	 * 显示锁屏
	 */
	public static final int SHOW_RIGHT_BAR_SCREEN_LOCK = 4002;

	// phoneRightBar上控件定义 进行隐藏
	/**
	 * 隐藏下载
	 */
	public static final int HIDE_RIGHT_BAR_DOWNLOAD = 5000;
	/**
	 * 隐藏音量
	 */
	public static final int HIDE_RIGHT_BAR_VOICE = 5001;
	/**
	 * 隐藏锁屏
	 */
	public static final int HIDE_RIGHT_BAR_SCREEN_LOCK = 5002;
	// ====================================================================

	private final int popupwindowBottombarOffsetX = 0;
	private final int popupwindowBottombarOffsetY = 10;
	private final int POPUPWINDOW_TOPBAR_OFFSET_X = 0;
	private final int POPUPWINDOW_TOPBAR_OFFSET_Y = 0;
	private final int popupwindowRightbarOffsetX = 10;
	private final int popupwindowRightbarOffsetY = -26;
	private final int popupwindowVideorateSettingOffsetX = popupwindowRightbarOffsetX;
	private final int popupwindowVideorateSettingOffsetY = popupwindowBottombarOffsetY;
	private final int popupwindowVideolistOffsetX = 0;
	private final int popupwindowVideolistOffsetY = -20;
	private final int popupwindowVoiceOffsetX = popupwindowRightbarOffsetX + 10;
	private final int popupwindowVoiceOffsetY = -26;

	private PhonePopupWindowBottomBar phoneBottomBar;
	private PhonePopupWindowTopBar phoneTop;
	private PhonePopupWindowVoice phoneVoice;

	public static BasePopupWindow mBasePopupWindow = null;

	private LandPopUpWinWorkingListener mLandPopUpWinWorkingListener;
	private Mediaplayer mMediaplayer;
	private static Context mContext;

	public static BasePopupWindow getInstance() {
		if (mBasePopupWindow == null)
			mBasePopupWindow = new BasePopupWindow();
		return mBasePopupWindow;
	}

	public static BasePopupWindow getInstance(Context context) {
		mContext = context;
		return getInstance();
	}

	public void initBasePopupWindow(Context context) {
		phoneBottomBar = new PhonePopupWindowBottomBar(context);
		phoneTop = new PhonePopupWindowTopBar(context);
		phoneVoice = new PhonePopupWindowVoice(context);

		mMediaplayer = Mediaplayer.getInstance();
	}

	public void firstLayerShow() {
		if (phoneBottomBar != null) {
			phoneBottomBar.show(popupwindowBottombarOffsetX, popupwindowBottombarOffsetY);
		}
		if (phoneTop != null) {
			phoneTop.show();
		}
		secondLayerHide();
	}

	public void firstLayerHide() {
		if (phoneBottomBar != null) {
			phoneBottomBar.dismiss();
		}
		if (phoneTop != null) {
			phoneTop.dismiss();
		}
	}

	public void dissmissAll() {
		firstLayerHide();
		secondLayerHide();
	}

	/**
	 * 隐藏第二层PopupWin
	 */
	private void secondLayerHide() {
		if (phoneVoice != null) {
			phoneVoice.dismiss();
		}
		if (phoneVoice != null) {
			phoneVoice.dismiss();
		}

	}

	/**
	 * 显示或者隐藏底下PopUpWin上面的控件
	 * 
	 * @param whichCommand
	 */
	public void popupWinBottomBarShowOrHide(int whichCommand) {
		if (phoneBottomBar != null) {}
	}

	/**
	 * 显示或者隐藏右边PopUpWin上面的控件
	 * 
	 * @param whichCommand
	 */
	public void popupWinRightBarShowOrHide(int whichCommand) {}

	/**
	 * 显示或者隐藏控制音量的PopupWin
	 */
	public void popUpWinVoiceShowOrHide() {
		if (phoneVoice.isShowing()) {
			phoneVoice.dismiss();
		}
		else {
			secondLayerHide();
			phoneVoice.show(popupwindowVoiceOffsetX, popupwindowVoiceOffsetY);
		}

	}

	/**
	 * 由系统物理按键来控制音量的改变和显示
	 * 
	 * @param flag
	 */
	public void showVoicePopWinForReceiver(int flag) {
		if (phoneVoice == null) {
			return;
		}

		if (phoneVoice.isShowing() == false) {
			secondLayerHide();
			phoneVoice.show(popupwindowRightbarOffsetX, popupwindowRightbarOffsetY);
		}

		int oldProgress = phoneVoice.getVolumSeekBarProgress();
		int minAdjustProgress = 1;
		int newProgress = 0;

		if (flag == VideoPlayerFragment.ADJUST_LOWER) {
			newProgress = oldProgress - minAdjustProgress;

		}
		else if (flag == VideoPlayerFragment.ADJUST_RAISE) {
			newProgress = oldProgress + minAdjustProgress;
		}
		phoneVoice.setVoiceProgress(newProgress);
	}

	public void setLandPopUpWinWorkingListener(LandPopUpWinWorkingListener mListener) {
		mLandPopUpWinWorkingListener = mListener;

	}

	private void notifyLandPopUpWinIsWorkingListener() {
		if (mLandPopUpWinWorkingListener != null) {
			mLandPopUpWinWorkingListener.onWorking();
		}
	}

	/**
	 * 横屏的PopupWin用户正在操作监听
	 * 
	 * @author Administrator
	 * 
	 */
	public abstract static interface LandPopUpWinWorkingListener {
		public abstract void onWorking();
	}

	public static void promptScreenIsLock(Context context) {
		String Text = context.getString(R.string.video_is_screen_lock);
		Toast.makeText(context, Text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 获得底部PopupWin 高度
	 * 
	 * @return
	 */
	public int getBottomBarHeight() {
		if (phoneBottomBar != null) {
			// return phoneBottomBar.getHeight();
			// phoneBottomBar.getContentView().measure(0, 0);
			return phoneBottomBar.getContentView().getMeasuredHeight();
		}
		else {
			return 0;
		}

	}

	/**
	 * 第一层的popupwin是否显示
	 * 
	 * @return
	 */
	public boolean isFirstLayerShowing() {
		if (phoneBottomBar != null && phoneBottomBar.isShowing() == true) {
			return true;
		}
		else {
			return false;
		}
	}

	public void videoScaleChanged() {
		if (phoneTop != null)
			phoneTop.setVideoScale();
	}

	/**
	 * 从外部传进来事件
	 * 
	 * @param what
	 * @param arg1
	 */
	public void sendMessage(int what, Object arg1) {
		Message message = basePopUpWinHandler.obtainMessage(what, arg1);
		basePopUpWinHandler.sendMessage(message);
	}

	/**
	 * 处理onclick QAS 上报的操作
	 * 
	 * @param what
	 */

	public Handler basePopUpWinHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			notifyLandPopUpWinIsWorkingListener();
			super.handleMessage(msg);
			switch (msg.what) {
			case PRESS_DOWNLOAD_BTN:
				String dlUrl = null;

				break;
			case PRESS_PUSH2TV_BTN:
				// just for test
				Toast.makeText(mContext, "推送大屏", Toast.LENGTH_SHORT).show();
				break;
			case SET_VIDEO_ORIGINALPICTURE:
			case SET_VIDEO_SUPERDEFINITION:
			case SET_VIDEO_HIGHDEFINITION:
			case SET_VIDEO_STANDARDDEFINITION:
				if (phoneBottomBar != null) {}
				break;
			case SHOW_HIDE_VIDEORATE_SETTING:
				break;
			case SHOW_HIDE_VIDEOLIST:
				break;
			case SHOW_HIDE_VOICE:
				popUpWinVoiceShowOrHide();
				break;
			case PRESS_PLAY_PAUSE_BTN:
				if (!mMediaplayer.isPlaying()) {
					mMediaplayer.start();
				}
				else {
					mMediaplayer.pause();
				}
				break;
			case PRESS_BACKWARDPLAY_BTN:
				mMediaplayer.seekToTenSecond(false);
				break;
			case PRESS_FORWARDPLAY_BTN:
				mMediaplayer.seekToTenSecond(true);
				break;
			case LONGPRESS_BACKWARDPLAY_BTN:
				mMediaplayer.seekToTwoMin(false);
				break;
			case LONGPRESS_FORWARDPLAY_BTN:
				mMediaplayer.seekToTwoMin(true);
				break;
			case PRESS_FULLSCREEN2HALF_BTN:
				((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 强制竖屏
				break;

			case PRESS_LOCK_BTN:
				break;
			case PRESS_BACK_BTN:
				((Activity) mContext).finish();
				break;
			case SET_VOICE_MUTE_ICON:
				break;
			case SET_VOICE_ICON:
				break;

			case VideoPlayerFragment.SHOW_VOICE_POPUPWINDOW_ADJUST_VOLUME:
				showVoicePopWinForReceiver((Integer) msg.obj);
				break;
			case VIDEO_SEEKBAR_IS_WORKING:
			case VOICE_SEEKBAR_IS_WORKING:
			case VIDEOLIST_POPUPWIN_IS_WORKING:
				;
				break;
			case VIDEO_LIST_SELECTED:
				break;

			}
		}
	};

}
