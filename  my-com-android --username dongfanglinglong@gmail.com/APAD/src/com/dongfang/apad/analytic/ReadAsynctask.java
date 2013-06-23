package com.dongfang.apad.analytic;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
 * 获取卡id
 * 
 * @author dongfang
 * 
 * 
 */
public class ReadAsynctask extends AsyncTask<String, String, String> {
	public static final String	TAG	= ReadAsynctask.class.getSimpleName();
	private MyProgressDialog	progressDialog;
	private Context				context;
	private Handler				handler;

	public ReadAsynctask(Context context, Handler handler) {
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
						ReadAsynctask.this.cancel(true);
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
				if (ReadAsynctask.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
					ReadAsynctask.this.cancel(true);
			}
		});
	}

	@Override
	protected String doInBackground(String... params) {
		String result = null;

		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ComParams.IP_CARD, ComParams.PORT_CARD), 5000);
			OutputStream out = socket.getOutputStream();
			out.write(ComParams.READ_USERINFO);
			byte[] input = new byte[16];
			socket.getInputStream().read(input);

			ULog.d(TAG, "OUTPUT" + Util.bytesToHexString(ComParams.READ_USERINFO).toUpperCase());
			result = Util.bytesToHexString(input).toUpperCase();
			ULog.d(TAG, "INPUT" + result);

			System.out.println(socket.getInputStream().toString());

			socket.close();

		} catch (UnknownHostException e) {
			ULog.d(TAG, "UnknownHostException = " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			ULog.d(TAG, "IOException = " + e.toString());
			e.printStackTrace();
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
