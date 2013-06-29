package com.dongfang.apad.analytic;

import java.net.InetSocketAddress;
import java.net.Socket;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.view.MyProgressDialog;

/**
 * 获取卡内信息
 * 
 * @deprecated
 * @author dongfang
 * 
 * 
 */
public class SocketConnectAnalytic extends AsyncTask<Bundle, String, String> {
	public static final String	TAG				= SocketConnectAnalytic.class.getSimpleName();
	private MyProgressDialog	progressDialog;
	private Context				context;
	private Handler				handler;
	private Socket				socket;

	private int					cardFaileTimes	= 0;

	public SocketConnectAnalytic(Context context, Handler handler, Socket socket) {
		this.handler = handler;
		this.context = context;
		socket = new Socket();
		this.socket = socket;
	}

	@Override
	protected String doInBackground(Bundle... params) {
		Bundle data = params[0];
		String ip = data.getString("ip");
		int port = data.getInt("port", 8899);
		int what = data.getInt("what", 0);
		socket = new Socket();

		while (cardFaileTimes < 5) {
			try {
				if (socket.isConnected()) {
					cardFaileTimes = 0;
					ULog.d(TAG, "card connect successed! " + ip + ":" + port);
					// ((DFService) context).sendBroadcaset(what, "card connect successed! " + ip + ":" + port);
					// ((DFService) context).setSocketCard(socket);

					handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_GET_CARD_ID);
					return null;
				}
				else {
					socket.connect(new InetSocketAddress(ip, port), ComParams.SOCKET_TIMEOUT);
				}
			} catch (Exception e) {
				// e.printStackTrace();
				ULog.e(TAG, "card connect faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
				cardFaileTimes++;
				handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_CONNECT_CARD, ComParams.SOCKET_TIMEOUT);
			}
		}

		return null;
	}

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
