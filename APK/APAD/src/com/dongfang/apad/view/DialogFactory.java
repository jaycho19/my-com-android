package com.dongfang.apad.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Binder;
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
	 *            How long to display the message. Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
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
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -2, WindowManager.LayoutParams.TYPE_PRIORITY_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
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
	 * 提示卡片信息
	 * 
	 * @param onClickListener
	 * @param isCancelable
	 */
	public void showDialogAboutCard(final onBtnClickListener onClickListener, boolean isCancelable) {
		if (null != dialog && dialog.isShowing())
			return;

		dialog = new Dialog(context, R.style.dialog);
		dialog.setCancelable(isCancelable);
		dialog.setContentView(R.layout.dialog_factory_button);
		Button btn_ok = (Button) dialog.findViewById(R.id.dialog_btn_ok);// 左一
		TextView tvContent = (TextView) dialog.findViewById(R.id.dialog_textview_context);
		tvContent.setMovementMethod(new ScrollingMovementMethod());

		btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (onClickListener != null)
					onClickListener.btnOkClickListener(v);
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
		public abstract void btnOkClickListener(View v);

		public abstract void btnCancleClickListener(View v);
	}

}
