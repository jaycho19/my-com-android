package com.dongfang.yzsj.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.df.util.ULog;
import com.dongfang.yzsj.MainActivity;
import com.dongfang.yzsj.utils.UtilOfTime;

/** 抓取系统崩溃日志 */
public class MyUnCaughtExceptionHandler implements UncaughtExceptionHandler {
	private static final String TAG = MyUnCaughtExceptionHandler.class.getSimpleName();
	/** 异常文件 */
	private static final String EXP_FILE_NAME = "exception.log";
	private Context mContext;
	private static MyUnCaughtExceptionHandler mMyUnCaughtExceptionHandler = null;

	// private UncaughtExceptionHandler mDefaultUEH;
	// private static final int RESTART_DELAY = 5000;
	// private PendingIntent ServiceIntent = null;
	// private Thread.UncaughtExceptionHandler mDefaultHandler;
	// private Looper myLooper = null;

	/** 保证只有一个MyUnCaughtExceptionHandler实例 */
	private MyUnCaughtExceptionHandler(Context context) {
		mContext = context;
		// mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
		Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
	}

	public static MyUnCaughtExceptionHandler getInstance(Context context) {
		if (mMyUnCaughtExceptionHandler == null) {
			mMyUnCaughtExceptionHandler = new MyUnCaughtExceptionHandler(context);
			return mMyUnCaughtExceptionHandler;
		}
		return mMyUnCaughtExceptionHandler;
	}

	// public void setIntent(PendingIntent intent) {
	// ServiceIntent = intent;
	// }

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ULog.d(TAG, "Class Name -> " + ex.getClass().getName());
		ULog.d(TAG, "thread Name -> " + thread.getName());
		// ex.printStackTrace();
		// ULog.e(TAG, "UncaughtException" + ex.toString());
		// ULog.e(TAG, "--" + getStackTraceAsString(ex));
		// RePorterMessage.getInstance().saveRunLogInFile(mContext,
		// RePorterMessage.REPORT_FILE_TYPE1, getStackTraceAsString(ex));

		Bundle bundle = new Bundle();
		bundle.putString(ExpKeys.EXP_FILENAME, EXP_FILE_NAME);
		bundle.putString(ExpKeys.EXP_LOG, getStackTraceAsString(ex));
		new SaveExceptionInFile(mContext).execute(bundle);

		// 重启
		if (!(mContext instanceof MainActivity)) {
			Intent intent = ((Application) mContext).getBaseContext().getPackageManager()
					.getLaunchIntentForPackage(((Application) mContext).getBaseContext().getPackageName());
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mContext.startActivity(intent);
		}

		return;

	}

	/**
	 * getStackTraceAsString:(get ex to string)
	 * 
	 * @param @param ex
	 * @return String
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	private String getStackTraceAsString(Throwable ex) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		StringBuffer error = new StringBuffer();
		error.append(UtilOfTime.getDateWithSpecialFromat(UtilOfTime.YYYYMMDDHHMMSS));
		error.append(stringWriter.getBuffer());
		return error.toString().replaceAll("\n", ",").replace("\t", "");
	}

	// private void reStartSystem() {
	// AppManager.getInstance().reStartSystem();
	// }

	// private void errorDialog(final Throwable ex) {
	//
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// Looper.prepare();
	// myLooper = Looper.myLooper();
	// new AlertDialog.Builder(mContext).setTitle("提示").setCancelable(false).setMessage("Error:" +
	// ex.toString()).setNeutralButton("ok ", new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// ex.printStackTrace();
	// myLooper.quit();
	// System.exit(0);
	// }
	// }).create().show();
	// Looper.loop();
	// }
	// }).start();
	// }
}
