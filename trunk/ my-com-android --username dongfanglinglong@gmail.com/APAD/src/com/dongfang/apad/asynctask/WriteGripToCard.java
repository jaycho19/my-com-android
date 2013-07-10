package com.dongfang.apad.asynctask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.dongfang.apad.EndActivity;
import com.dongfang.apad.bean.UserInfo;
import com.dongfang.apad.net.HttpActions;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.DFException;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.util.Util;
import com.dongfang.apad.view.MyProgressDialog;
import com.google.gson.JsonObject;

/**
 * 写握力计数据
 * 
 * @author dongfang
 */
public class WriteGripToCard extends AsyncTask<String, String, String> {
	public static final String	TAG	= WriteGripToCard.class.getSimpleName();
	private MyProgressDialog	progressDialog;
	private Context				context;
	private UserInfo			userInfo;
	private Socket				socketCard;

	// private Handler handler;

	public WriteGripToCard(Context context, UserInfo userInfo) {
		this.context = context;
		this.userInfo = userInfo;
	}

	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// progressDialog = MyProgressDialog.show(context, context.getString(R.string.loading_data));
	// progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
	// @Override
	// public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// if (progressDialog != null && progressDialog.isShowing()) {
	// progressDialog.dismiss();
	// GetCardInfo.this.cancel(true);
	// }
	// return true;
	// }
	// return false;
	// }
	// });
	// progressDialog.show();
	// progressDialog.setOnDismissListener(new OnDismissListener() {
	//
	// @Override
	// public void onDismiss(DialogInterface dialog) {
	// if (GetCardInfo.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
	// GetCardInfo.this.cancel(true);
	// }
	// });
	// }

	@Override
	protected String doInBackground(String... params) {

		byte[] output = null;
		byte[] input = new byte[20];
		try {
			output = new byte[16];

			output[0] = 0x40;
			output[1] = (byte) 0x97;
			output[3] = 0x01;
			output[6] = 0x06;
			for (int i = 0; i < 7; i++)
				output[7] ^= output[i];
			// ------整数部分--------
			output[8] = 0x13;
			output[9] = 0x01;
			output[10] = userInfo.getReadbyte()[76];
			output[11] = userInfo.getReadbyte()[77];
			output[12] = userInfo.getReadbyte()[78];
			output[13] = userInfo.getReadbyte()[79];

			if (null == socketCard || !socketCard.isConnected()) {
				socketCard = new Socket();
				socketCard.connect(new InetSocketAddress(ComParams.IP_CARD, ComParams.PORT_CARD), ComParams.SOCKET_TIMEOUT);
			}

			ULog.d(TAG, "----------0------------------");
			getInputBytes(socketCard, output, input);
			ULog.d(TAG, "----------0------------------");

			// ------小数位部分--------
			output[8] = 0x17;
			output[9] = 0x01;
			output[10] = userInfo.getReadbyte()[92];
			output[11] = userInfo.getReadbyte()[93];
			output[12] = userInfo.getReadbyte()[94];
			output[13] = userInfo.getReadbyte()[95];
			ULog.d(TAG, "----------1------------------");

			getInputBytes(socketCard, output, input);
			ULog.d(TAG, "----------1------------------");

			return null;

		} catch (Exception e) {
			ULog.d(TAG, "握力计写入数据失败 = " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				socketCard.close();
				socketCard = null;
			} catch (IOException e) {
				e.printStackTrace();
				socketCard = null;
			}
		}

		return params[0];
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

		if ("1".equals(result)) {
			Toast.makeText(context, "写卡失败！", Toast.LENGTH_LONG).show();
			((EndActivity)context).reWriteToCard();
		}
		else if ("2".equals(result)) {
			Toast.makeText(context, "写卡失败！", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(context, "写卡成功！", Toast.LENGTH_LONG).show();
		}

	}

	private void getInputBytes(Socket socket, byte[] output, byte[] input) throws Exception {
		ULog.d(TAG, "OUTPUT : " + Util.bytesToHexString(output).toUpperCase());
		socket.getOutputStream().write(output);
		socket.getInputStream().read(input);
		socket.getOutputStream().flush();
		ULog.d(TAG, "INPUT  : " + Util.bytesToHexString(input).toUpperCase());
		socket.getOutputStream().flush();
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		ULog.i(TAG, "--> onCancelled");
		if (progressDialog != null)
			progressDialog.dismiss();
	}

}
