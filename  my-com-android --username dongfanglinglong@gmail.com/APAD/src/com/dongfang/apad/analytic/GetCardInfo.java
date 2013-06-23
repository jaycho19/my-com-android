package com.dongfang.apad.analytic;

import java.io.IOException;
import java.net.Socket;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.dongfang.apad.R;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.util.Util;
import com.dongfang.apad.view.MyProgressDialog;

/**
 * 获取卡内信息
 * 
 * @author dongfang
 * 
 * 
 */
public class GetCardInfo extends AsyncTask<String, String, String> {
	public static final String	TAG	= GetCardInfo.class.getSimpleName();
	private MyProgressDialog	progressDialog;
	private Context				context;
	private Handler				handler;

	public GetCardInfo(Context context, Handler handler) {
		this.handler = handler;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = MyProgressDialog.show(context, context.getString(R.string.loading_data));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();
						GetCardInfo.this.cancel(true);
					}
					return true;
				}
				return false;
			}
		});
		progressDialog.show();
		progressDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (GetCardInfo.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
					GetCardInfo.this.cancel(true);
			}
		});
	}

	/**
	 * 往socket写入output数据，返回的内容输入到input中
	 * 
	 * @param socket
	 * @param output
	 * @param input
	 * @throws IOException
	 */
	private void getInputBytes(Socket socket, byte[] output, byte[] input) throws IOException {
		ULog.d(TAG, "OUTPUT" + Util.bytesToHexString(ComParams.READ_ID).toUpperCase());
		socket.getOutputStream().write(ComParams.READ_ID);
		socket.getInputStream().read(input);
		ULog.d(TAG, "INPUT" + Util.bytesToHexString(input).toUpperCase());
	}

	@Override
	protected String doInBackground(String... params) {
		String result = null;
		Socket socket = null;
		try {
			socket = new Socket(ComParams.IP_CARD, ComParams.PORT_CARD);
			socket.setSoTimeout(5000);

			byte[] input = new byte[16];
			getInputBytes(socket, ComParams.READ_ID, input);

		} catch (Exception e) {
			ULog.d(TAG, "IOException = " + e.toString());
			e.printStackTrace();
		} finally {
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					ULog.d(TAG, e.getMessage());
				}
				socket = null;
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {

		if (progressDialog != null)
			progressDialog.dismiss();

		if (!TextUtils.isEmpty(result)) {
			Message msg = new Message();
			msg.what = 1;
			msg.obj = result;
			handler.sendMessage(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onCancelled()
	 */
	@Override
	protected void onCancelled() {
		super.onCancelled();
		ULog.i(TAG, "--> onCancelled");
		if (progressDialog != null)
			progressDialog.dismiss();
	}

}
