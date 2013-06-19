package com.dongfang.apad.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.apad.R;

/**
 * 
 * @author chenwansong,dongfang
 * 
 */
@SuppressLint("StringFormatMatches")
public class DialogFactory {
	public static final String	TAG		= DialogFactory.class.getSimpleName();
	private Context				context;
	private Dialog				dialog;
	private Toast				toast;
	private boolean				isFold	= false;

	public DialogFactory(Context context) {
		this.context = context;
		toast = new Toast(context);
	}

	public boolean isShowing() {
		return (null != dialog) && dialog.isShowing();
	}

	public void dismiss() {
		if (isShowing())
			dialog.dismiss();
	}

	/**
	 * Make a standard toast that just contains a text view.
	 * 
	 * @param text
	 *            The text to show. Can be formatted text.
	 * @param duration
	 *            How long to display the message. Either
	 *            {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
	 * 
	 */
	// public void showToast(String text, int duration) {
	// View layout =
	// LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.toast,
	// null);
	// TextView tv = (TextView) layout.findViewById(R.id.toast_textview);
	// // Button btn = (Button) layout.findViewById(R.id.bt_toast_close);
	// tv.setText(text);
	// int textWidth = 0;
	// if (context.getResources().getConfiguration().orientation ==
	// Configuration.ORIENTATION_LANDSCAPE) {
	// textWidth = Util.getWindowHeight(context)/*
	// * - (int) (40 *
	// * Util.getWindowDensity
	// * (context))
	// */;
	// }
	// else if (context.getResources().getConfiguration().orientation ==
	// Configuration.ORIENTATION_PORTRAIT) {
	// textWidth = Util.getWindowWidth(context) /*- (int) (40 *
	// Util.getWindowDensity(context))*/;
	// }
	// tv.setLayoutParams(new LinearLayout.LayoutParams(textWidth,
	// LinearLayout.LayoutParams.WRAP_CONTENT));
	// // btn.setOnClickListener(new OnClickListener() {
	// // @Override
	// // public void onClick(View v) {
	// // toast.cancel();
	// // }
	// // });
	// // btn.setLayoutParams(new LinearLayout.LayoutParams((int) (40 *
	// // Util.getWindowDensity(context)),
	// // LinearLayout.LayoutParams.WRAP_CONTENT));
	// toast.setGravity(Gravity.BOTTOM, 0, 0);
	// toast.setDuration(duration);
	// toast.setView(layout);
	// toast.setMargin(0, 0);
	// toast.show();
	// }

	public void showToast(String text, int duration) {
		final String s = new String();
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -2,
				WindowManager.LayoutParams.TYPE_PRIORITY_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING, PixelFormat.RGBA_8888);

		lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
		lp.token = new Binder();
		lp.windowAnimations = android.R.style.Animation_Toast;
		final WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService("window");
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_factory_toast, null);
		((TextView) view.findViewById(R.id.toast_textview)).setText(text);

		wm.addView(view, lp);

		android.os.Handler handler = new android.os.Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				synchronized (s) {
					if (view.isShown())
						wm.removeView(view);
				}
			}
		}, Toast.LENGTH_LONG == duration ? 3000 : 1500);

	}

	/**
	 * 一个按钮
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            提示内容
	 * @param okContent
	 *            dialog左边按钮内容
	 * @param onClickListener
	 * @param isCancelable
	 */
	public void showDialog(String title, String content, String okContent, final onBtnClickListener onClickListener,
			boolean isCancelable) {
		showDialog3Btn(title, content, okContent, null, null, onClickListener, isCancelable);
	}

	public void showDialog(String title, String content, String okContent, final onBtnClickListener onClickListener) {
		showDialog(title, content, okContent, onClickListener, false);
	}

	/**
	 * 2个按钮
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            提示内容
	 * @param ok
	 *            dialog左边按钮内容
	 * @param cancle
	 *            dialog右边按钮内容
	 * @param onClickListener
	 *            点击事件
	 * @param isCancelable
	 *            能否被返回键取消
	 */
	public void showDialog2Btn(String title, String content, String ok, String cancle,
			final onBtnClickListener onClickListener, boolean isCancelable) {
		showDialog3Btn(title, content, ok, null, cancle, onClickListener, isCancelable);
	}

	public void showDialog2Btn(String title, String content, String ok, String cancle,
			final onBtnClickListener onClickListener) {
		this.showDialog2Btn(title, content, ok, cancle, onClickListener, false);
	}

	/**
	 * 3个按钮
	 * 
	 * @param title
	 *            提示标题
	 * @param content
	 *            提示内容
	 * @param btn_left_content
	 *            dialog左边确定按钮文字
	 * @param btn_center_content
	 *            dialog中间自定义按钮文字
	 * @param btn_right_content
	 *            dialog右边取消按钮文字
	 * @param onClickListener
	 *            点击事件
	 * @param isCancelable
	 *            能否响应返回键
	 */
	public void showDialog3Btn(String title, final String content, String btn_left_content, String btn_center_content,
			String btn_right_content, final onBtnClickListener onClickListener, boolean isCancelable) {
		dialog = new Dialog(context, R.style.dialog);
		dialog.setCancelable(isCancelable);
		dialog.setContentView(R.layout.dialog_factory_button);
		Button btn_left = (Button) dialog.findViewById(R.id.dialog_factory_bt_opt);// 左一
		Button btn_Cancel = (Button) dialog.findViewById(R.id.dialog_factory_bt_close);// 右上角

		Button btn_center = (Button) dialog.findViewById(R.id.dialog_factory_bt_ok);// 中间
		// btn_center.setVisibility(View.VISIBLE);
		Button btn_right = (Button) dialog.findViewById(R.id.dialog_factory_bt_cancle);// 右一
		// btn_right.setVisibility(View.VISIBLE);

		TextView tvContent = (TextView) dialog.findViewById(R.id.dialog_factory_tv_content);
		tvContent.setMovementMethod(new ScrollingMovementMethod());
		TextView tvTitle = (TextView) dialog.findViewById(R.id.dialog_factory_tv_title);

		tvTitle.setText((null == title) ? "" : title);
		// if (content.length() > 19) {
		// tvContent.setTextSize(21);
		// }
		if (tvContent != null) {
			tvContent.setText((null == content) ? "" : content);
		}
		if (btn_left_content != null) {
			btn_left.setText(TextUtils.isEmpty(btn_left_content) ? context.getString(R.string.ok) : btn_left_content);
		}

		if (btn_center_content != null) {
			btn_center.setText(btn_center_content);
		}
		else {
			btn_center.setVisibility(View.GONE);
		}
		if (btn_right_content != null) {
			btn_right.setText(btn_right_content);
		}
		else {
			btn_right.setVisibility(View.GONE);
		}
		if (btn_right_content == null && btn_center_content == null) {
			btn_left.setBackgroundResource(R.drawable.dialog_factory_bt_1_bg);
		}

		btn_left.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (onClickListener != null)
					onClickListener.btnLeftClickListener(v);
				dialog.dismiss();
			}
		});
		if (btn_center_content != null) {
			btn_center.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (onClickListener != null)
						onClickListener.btnNeutralClickListener(v);
					dialog.dismiss();
				}
			});
		}

		if (btn_right_content != null) {
			btn_right.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (onClickListener != null)
						onClickListener.btnRightClickListener(v);
					dialog.dismiss();
				}
			});
		}

		btn_Cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (onClickListener != null)
					onClickListener.btnCloseClickListener(v);
				dialog.dismiss();
			}
		});

		dialog.show();

	}

	/**
	 * 退出对话框点击事件
	 * 
	 * @author wchen
	 * 
	 */
	public interface onExitBtnClickListener {

		public abstract void btnLeftOnClickListener(View v);

		public abstract void btnRightOnClickListener(View v);

	}

	/**
	 * 流量订购
	 * 
	 * @author wchen
	 * 
	 */
	public interface onFolwOrderBtnClickListener {
		/**
		 * 确认订购按钮
		 * 
		 * @param v
		 */
		public abstract void btnOKOrderOnClickListener(Dialog dialog, View v);

		/**
		 * 确定 按钮
		 * 
		 * @param v
		 */
		public abstract void btnOKOnClickListener(Dialog dialog, View v);

		/**
		 * 图形“X”按钮
		 * 
		 * @param dialog
		 * @param v
		 */
		public abstract void btnCloseClickListener(Dialog dialog, View v);
	}

	/**
	 * 
	 * @author chenwansong
	 * 
	 */
	public interface onBtnClickListener {
		/**
		 * 左边确认按钮
		 * 
		 * @param v
		 */
		public abstract void btnLeftClickListener(View v);

		/**
		 * dialog中间自定义按钮
		 * 
		 * @param v
		 */
		public abstract void btnNeutralClickListener(View v);

		/**
		 * dialog最右边按钮
		 * 
		 * @param v
		 */
		public abstract void btnRightClickListener(View v);

		/**
		 * 右上角关闭
		 * 
		 * @param v
		 */
		public abstract void btnCloseClickListener(View v);
	}

	/**
	 * 订购对话框接口
	 * 
	 * @author chenwansong
	 * 
	 */
	public interface onOrderBtnClickListener {
		/**
		 * 按次购买
		 * 
		 * @param v
		 */
		public abstract void btnOrderByTime(View v, Dialog dialog);

		/**
		 * 包月购买
		 * 
		 * @param v
		 */
		public abstract void btnOrderByMonth(View v, Dialog dialog);

		/**
		 * 关闭
		 * 
		 * @param v
		 */
		public abstract void btnCloseClickListener(View v);
	}

	/**
	 * 实惠包
	 * 
	 * @author wchen
	 * 
	 */
	public interface onSHBBtnClickListener {
		/**
		 * 实惠包 订购Btn
		 * 
		 * @param v
		 */
		public abstract void btnSHBOnClickListener(View v, Dialog dialog);

		/**
		 * 按月购买btn
		 * 
		 * @param v
		 */
		public abstract void btnMONTHOnClickListener(View v, Dialog dialog);

		/**
		 * 按次购买btn
		 * 
		 * @param v
		 */
		public abstract void btnTIMEOnClickListener(View v, Dialog dialog);

		/**
		 * 关闭btn
		 * 
		 * @param v
		 */
		public abstract void btnCloseOnClickListener(View v, Dialog dialog);

	}

	/**
	 * 可分享应用程序信息
	 * 
	 * @author chenwansong
	 * 
	 */
	public class AppInfo {

		private String		AppPkgName;			// 应用包名
		private String		AppLauncherClassName;	// 接受分享内容activity名称
		private String		AppName;				// 应用名称
		private Drawable	AppIcon;				// 应用图标

		public AppInfo() {
			super();
		}

		public AppInfo(String appPkgName, String appLauncherClassName) {
			super();
			AppPkgName = appPkgName;
			AppLauncherClassName = appLauncherClassName;
		}

		public AppInfo(String appPkgName) {
			super();
			AppPkgName = appPkgName;
		}

		public String getAppPkgName() {
			return AppPkgName;
		}

		public void setAppPkgName(String appPkgName) {
			AppPkgName = appPkgName;
		}

		public String getAppLauncherClassName() {
			return AppLauncherClassName;
		}

		public void setAppLauncherClassName(String appLauncherClassName) {
			AppLauncherClassName = appLauncherClassName;
		}

		public String getAppName() {
			return AppName;
		}

		public void setAppName(String appName) {
			AppName = appName;
		}

		public Drawable getAppIcon() {
			return AppIcon;
		}

		public void setAppIcon(Drawable appIcon) {
			AppIcon = appIcon;
		}

	}
}
