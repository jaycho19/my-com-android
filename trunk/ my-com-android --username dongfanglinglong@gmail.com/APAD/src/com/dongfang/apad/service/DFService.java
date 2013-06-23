package com.dongfang.apad.service;

import java.io.IOException;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.dongfang.apad.broadcast.UpdateDataReceiver;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.util.Util;

/**
 * 
 * @author dongfang
 * 
 */
public class DFService extends Service {
	public static final String	TAG	= DFService.class.getSimpleName();

	private Handler				handler;
	private Socket				socketCard;
	private Socket				socketTestZKT;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		ULog.i(TAG, "onCreate");

		handler = new MyHandler();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		ULog.i(TAG, "onStartCommand " + intent.toString() + " " + flags + " " + startId);
		if (null != intent && null != intent.getIntArrayExtra(ComParams.SERVICE_HANDLER_ACTION_ID)) {
			for (int i : intent.getIntArrayExtra(ComParams.SERVICE_HANDLER_ACTION_ID)) {
				ULog.d(TAG, "HandlerID = " + i);
				handler.sendEmptyMessage(i);
			}
		}
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			if (null != socketCard)
				socketCard.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (null != socketTestZKT)
				socketTestZKT.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		handler = null;

	}

	/** 发送广播 */
	private void sendBroadcaset(int what, String des, Bundle data) {
		Intent intent = new Intent(getPackageName() + "." + UpdateDataReceiver.TAG);
		if (null != data)
			intent.putExtras(data);
		if (!TextUtils.isEmpty(des))
			intent.putExtra(ComParams.BROADCAST_HANDLER_DES, des);
		intent.putExtra(ComParams.BROADCAST_HANDLER_ACTION_ID, what);
		sendBroadcast(intent);
		ULog.d(TAG, "sendBroadcaset = " + getPackageName() + "." + UpdateDataReceiver.TAG);
	}

	private void sendBroadcaset(int what, String des) {
		sendBroadcaset(what, des, null);
	}

	/** 连接失败次数 */
	private int	cardFaileTimes	= 0;

	/**
	 * 连接socket card
	 */
	private void connectCardSocket(String ip, int port, int what) {
		if (null != socketCard && socketCard.isConnected()) {
			ULog.d(TAG, "card connect has alreadly successed! " + ip + ":" + port);
			sendBroadcaset(what, "card connect successed! " + ip + ":" + port);
			return;
		}
		else {
			socketCard = null;
		}

		ULog.d(TAG, "card connecting ..." + cardFaileTimes);
		sendBroadcaset(what, "card connecting ..." + cardFaileTimes);
		if (cardFaileTimes < 5) {
			try {
				socketCard = new Socket(ip, port);
				socketCard.setSoTimeout(ComParams.SOCKET_TIMEOUT);
				if (socketCard.isConnected()) {
					cardFaileTimes = 0;
					ULog.d(TAG, "card connect successed! " + ip + ":" + port);
					sendBroadcaset(what, "card connect successed! " + ip + ":" + port);

					handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_GET_CARD_ID);
					// handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT);
				}
			} catch (Exception e) {
				ULog.e(TAG, "faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
				cardFaileTimes++;
				handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_CONNECT_CARD, ComParams.SOCKET_TIMEOUT);
			}
		}
		else {
			cardFaileTimes = 0;
			ULog.d(TAG, "card connect failed! " + ip + ":" + port);
			sendBroadcaset(what, "card connect failed! " + ip + ":" + port);
		}
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

	/** 获取卡号 */
	private void getCardId(int what) {
		byte[] input = new byte[16];
		try {
			getInputBytes(socketCard, ComParams.READ_ID, input);
			if (input[6] > 0x00) {
				byte[] cardId = new byte[input[6]];
				for (int i = 0; i < input[6]; i++) {
					cardId[i] = input[7 + input[6] - i];
				}
				// TODO: 需要使用卡号的地方
				ULog.d(TAG, "cardId = " + Util.bytesToHexString(cardId).toUpperCase());
				cardFaileTimes = 0;
				handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_USER_INFO, 1000);

			}
			else {
				handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_CARD_ID, 1000);
			}

		} catch (IOException e) {
			ULog.e(TAG, "faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
			cardFaileTimes++;
			handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_CARD_ID, 1000);

			e.printStackTrace();
		}
	}

	/** 获取用户信息 */
	private void getUserInfo(int what) {
		byte[] input = new byte[70 - 30 + 1];
		try {
			getInputBytes(socketCard, ComParams.READ_USERINFO, input);
			if (input[6] > 0x00) {
				byte[] userInfo = new byte[input[6]];
				for (int i = 0; i < input[6]; i++) {
					userInfo[i] = input[7 + i];
				}
				// TODO: 初始化用户信息
				ULog.d(TAG, "userInfo = " + Util.bytesToHexString(userInfo).toUpperCase());
				cardFaileTimes = 0;
				handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_USER_INFO, 1000);
			}
			else {
				handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_USER_INFO, 1000);
			}

		} catch (IOException e) {
			ULog.e(TAG, "faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
			cardFaileTimes++;
			handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_USER_INFO, 1000);

			e.printStackTrace();
		}
		// initData(null);
		// TODO:
	}

	private int	testZKTFaileTimes	= 0;

	/**
	 * 连接socket testZKT
	 */
	private void connectTestZKTSocket(String ip, int port, int what) {
		if (null != socketTestZKT && socketTestZKT.isConnected()) {
			ULog.d(TAG, "testZKT connect has alreadly successed! " + ip + ":" + port);
			sendBroadcaset(what, "testZKT connect successed! " + ip + ":" + port);
			return;
		}
		else {
			socketTestZKT = null;
		}

		ULog.d(TAG, "testZKT connecting ..." + testZKTFaileTimes);
		sendBroadcaset(what, "testZKT connecting ..." + testZKTFaileTimes);
		if (testZKTFaileTimes < 5) {
			try {
				socketTestZKT = new Socket(ip, port);
				socketCard.setSoTimeout(ComParams.SOCKET_TIMEOUT);

				if (socketTestZKT.isConnected()) {
					testZKTFaileTimes = 0;
					ULog.d(TAG, "testZKT connect successed! " + ip + ":" + port);
					sendBroadcaset(what, "testZKT connect successed! " + ip + ":" + port);
				}
			} catch (Exception e) {
				ULog.e(TAG, "faileTimes = " + testZKTFaileTimes + " -- " + e.getMessage());
				testZKTFaileTimes++;
				handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT, ComParams.SOCKET_TIMEOUT);
			}
		}
		else {
			testZKTFaileTimes = 0;
			ULog.d(TAG, "testZKT connect failed! " + ip + ":" + port);
			sendBroadcaset(what, "testZKT connect failed! " + ip + ":" + port);

		}
	}

	/** 重新初始化中控板连接设备 */
	private void restartTestZKT() {

	}

	/** 获取测试结果 */
	private void getTestResult() {
		// TODO: 获取测试数据流程，每一秒获取一次
	}

	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ComParams.HANDLER_SOCKET_CONNECT_CARD:
				connectCardSocket(ComParams.IP_CARD, ComParams.PORT_CARD, msg.what);
				break;
			case ComParams.HANDLER_SOCKET_GET_CARD_ID:
				getCardId(msg.what);
				break;
			case ComParams.HANDLER_SOCKET_GET_USER_INFO:
				getUserInfo(msg.what);
				break;
			case ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT:
				connectTestZKTSocket(ComParams.IP_TEST, ComParams.PORT_TEST, msg.what);
				break;
			case ComParams.HANDLER_SOCKET_GET_TEST_ZKT_RESTART:
				restartTestZKT();
				break;
			case ComParams.HANDLER_SOCKET_GET_TEST_ZKT_RESULT:
				getTestResult();
				break;
			}
		}
	}

}
