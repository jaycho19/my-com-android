package com.dongfang.yzsj.dialog;

import java.io.File;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.HttpHandler;
import com.dongfang.net.http.RequestCallBack;
import com.dongfang.net.http.ResponseInfo;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.utils.Util;
import com.dongfang.view.ProgressDialog;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.UpdateBean;

/**
 * 进度条对话框
 * 
 * @author chenwansong
 * 
 */
public class UpdateDialog extends Dialog {
	private static UpdateDialog updateDialog;
	private static HttpHandler<File> handler;

	private UpdateDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * 
	 * @param context
	 *            需要显示的文字 ；传入null或者""则不显示文字
	 * @param message
	 * @return
	 */
	public static UpdateDialog show(final Context context, final UpdateBean updateBean,
			final OnOrderDialogBtnListener onlistener, boolean cancelable) {
		updateDialog = new UpdateDialog(context, R.style.CustomProgressDialog);
		updateDialog.setContentView(R.layout.activity_update_dialog);
		updateDialog.setCancelable(cancelable);
		updateDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		TextView updateInfo = (TextView) updateDialog.findViewById(R.id.update_tv_info);
		final ProgressBar updateProgressBar = (ProgressBar) updateDialog.findViewById(R.id.progressBar_download_apk);
		updateProgressBar.setProgress(0);

		StringBuilder sb = new StringBuilder("当前版本：");
		sb.append(Util.getAPKVersion(context));
		sb.append("，最新版本：").append(updateBean.getCurrentVersion());
		sb.append("，是否下载更新？");

		updateInfo.setText(sb.toString());

		Button btnOK = (Button) updateDialog.findViewById(R.id.update_btn_ok);
		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				v.setEnabled(false);
				updateProgressBar.setVisibility(View.VISIBLE);
				downloadApk(context, updateBean.getDownoadUrl(), updateProgressBar);
			}
		});

		Button btnCancel = (Button) updateDialog.findViewById(R.id.update_btn_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				updateDialog.dismiss();
				if (null != onlistener) {
					onlistener.cancel();
				}
			}
		});
		return updateDialog;
	}

	public static UpdateDialog show(final Context context, final UpdateBean updateBean,
			final OnOrderDialogBtnListener onlistener) {
		return show(context, updateBean, onlistener, true);
	}

	@Override
	public void cancel() {
		super.cancel();
		updateDialog = null;
	}

	@Override
	public void dismiss() {
		super.dismiss();
		updateDialog = null;
		if (null != handler)
			handler.stop();

	}

	private static void downloadApk(final Context context, String downloadUrl, final ProgressBar progressBar) {
		final com.dongfang.view.ProgressDialog progressDialog = ProgressDialog.show(context);

		handler = new HttpUtils().download(downloadUrl, "sdcard/download/yzsj.apk", new RequestCallBack<File>() {

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				ULog.d(current + " / " + total);
				progressBar.setMax((int) total);
				progressBar.setProgress((int) current);
			}

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				Uri uri = Uri.fromFile(new File(responseInfo.result.getPath()));
				ULog.d("install uri = " + uri.toString());
				intent.setDataAndType(uri, "application/vnd.android.package-archive");
				context.startActivity(intent);
				updateDialog.dismiss();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, "客户端升级失败！", Toast.LENGTH_LONG).show();
			}

		});

	}

	public interface OnOrderDialogBtnListener {
		public void ok(String phone, String authCode);

		public void cancel();
	}
}