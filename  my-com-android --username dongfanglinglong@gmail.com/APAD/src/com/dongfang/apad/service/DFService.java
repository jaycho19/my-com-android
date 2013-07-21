package com.dongfang.apad.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.dongfang.apad.asynctask.WriteGripToCard;
import com.dongfang.apad.bean.TestResult;
import com.dongfang.apad.bean.UserInfo;
import com.dongfang.apad.broadcast.UpdateDataReceiver;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.param.UserCommand;
import com.dongfang.apad.param.ZKTCommand;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.util.Util;

/**
 * 
 * @author dongfang
 * 
 */
public class DFService extends Service {
	public static final String	TAG							= DFService.class.getSimpleName();
	/** 连接失败上限次数 */
	public static final int		SOCKET_CONNECT_FAILED_TIMES	= 5;

	private Handler				handler;
	/** 连接读卡器 */
	private Socket				socketCard;
	/** 连接中控板 */
	private Socket				socketTestZKT;
	/** 广播发送数据 */
	private Bundle				data;

	/** 连接失败次数 */
	private int					cardFaileTimes				= 0;
	/** 中控板连接失败次数 */
	private int					testZKTFaileTimes			= 0;
	/** 测试结果 */
	private TestResult			testResult;
	/** 用户信息 */
	private UserInfo			userInfo;
	public static OutputStream	outStream;
	public static double		max							= 0.0;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		ULog.i(TAG, "onCreate");
		handler = new MyHandler();
		data = new Bundle();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		/** 清除测试结果 */
		if (null != intent && !TextUtils.isEmpty(intent.getStringExtra(ComParams.SERVICE_CLEAR_TESTINFO))) {
			testResult = null;
			startTestZKT(-1);
		}

		/** 清除全部动作 */
		if (null != intent && !TextUtils.isEmpty(intent.getStringExtra(ComParams.SERVICE_HANDLER_REMOVE_ALL))) {
			handler.removeMessages(ComParams.HANDLER_SOCKET_CONNECT_CARD);
			handler.removeMessages(ComParams.HANDLER_SOCKET_GET_CARD_ID);
			handler.removeMessages(ComParams.HANDLER_SOCKET_GET_USER_INFO);

			handler.removeMessages(ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT);
			handler.removeMessages(ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START);
			handler.removeMessages(ComParams.HANDLER_SOCKET_GET_TEST_ZKT_RESULT);

			testZKTFaileTimes = 0;
			cardFaileTimes = 0;
			// socketClose();
		}
		/** 运行命令 */
		if (null != intent && null != intent.getIntArrayExtra(ComParams.SERVICE_HANDLER_ACTION_ID)) {
			if (intent.getIntArrayExtra(ComParams.SERVICE_HANDLER_ACTION_ID).length > 0) {
				for (int i : intent.getIntArrayExtra(ComParams.SERVICE_HANDLER_ACTION_ID)) {
					ULog.d(TAG, "HandlerID = " + i);
					handler.sendEmptyMessage(i);
				}
			}
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ULog.d(TAG, "onDestroy");
		socketClose();
		handler = null;
	}

	/** 关闭读卡器 */
	private void sokectCloseCard() {
		try {
			outStream.close();
			if (null != socketCard) {
				socketCard.close();
				socketCard = null;
			}
		} catch (IOException e) {
			socketCard = null;
			e.printStackTrace();
		}
	}

	/** 关闭中控 */
	private void sokectCloseZTK() {
		try {
			if (null != socketTestZKT) {
				socketTestZKT.close();
				socketTestZKT = null;
			}
		} catch (IOException e) {
			socketTestZKT = null;
			e.printStackTrace();
		}
	}

	/** 关闭全部socket */
	private void socketClose() {
		sokectCloseCard();
		sokectCloseZTK();
	}

	public void setSocketCard(Socket socket) {
		socketCard = socket;
	}

	/** 发送广播 */
	private void sendBroadcaset(int what, boolean isConnected, Bundle data) {
		Intent intent = new Intent(getPackageName() + "." + UpdateDataReceiver.TAG);
		if (null != data && !data.isEmpty())
			intent.putExtras(data);
		intent.putExtra(ComParams.BROADCAST_HANDLER_IS_CONNECTED, isConnected);
		intent.putExtra(ComParams.BROADCAST_HANDLER_ACTION_ID, what);
		sendBroadcast(intent);
	}

	/**
	 * 往socket写入output数据，返回的内容输入到input中
	 * 
	 * @param socket
	 * @param output
	 * @param input
	 * @throws IOException
	 */
	@SuppressLint("DefaultLocale")
	private void getInputBytes(Socket socket, byte[] output, byte[] input) throws Exception {
		ULog.d(TAG, "OUTPUT : " + Util.bytesToHexString(output).toUpperCase());
		socket.getOutputStream().write(output);
		socket.getInputStream().read(input);
		socket.getOutputStream().flush();
		ULog.d(TAG, "INPUT  : " + Util.bytesToHexString(input).toUpperCase());
	}

	/**
	 * 连接socket card
	 */
	private void connectCardSocket(final String ip, final int port, final int what) {
		if (null != socketCard && socketCard.isConnected()) {
			ULog.d(TAG, "card connect has alreadly successed! " + ip + ":" + port);
			data.clear();
			data.putString(ComParams.BROADCAST_HANDLER_DES, "Already successed connect card! " + ip + ":" + port);
			sendBroadcaset(what, socketCard.isConnected(), data);
			return;
		}
		else {
			socketCard = null;
		}

		if (cardFaileTimes > SOCKET_CONNECT_FAILED_TIMES) {
			ULog.e(TAG, "Faild connect card! " + ip + ":" + port);
			data.clear();
			data.putString(ComParams.BROADCAST_HANDLER_DES, "Faild connect card! " + ip + ":" + port);
			sendBroadcaset(what, false, data);
			handler.removeMessages(what);
			cardFaileTimes = 0;
			return;
		}
		else {
			ULog.i(TAG, "card connecting ..." + cardFaileTimes);
			data.clear();
			data.putString(ComParams.BROADCAST_HANDLER_DES, "card connecting ..." + cardFaileTimes);
			sendBroadcaset(what, false, data);
		}

		new Thread() {
			@Override
			public void run() {
				super.run();
				cardFaileTimes++;
				try {
					socketCard = null;
					socketCard = new Socket();
					socketCard.connect(new InetSocketAddress(ip, port), ComParams.SOCKET_TIMEOUT);

					if (socketCard.isConnected()) {
						ULog.i(TAG, "card connect successed! " + ip + ":" + port);
						try {
							byte[] inputTemp = new byte[20];
							getInputBytes(socketCard, UserCommand.RUSER_ws, inputTemp);

							outStream = socketCard.getOutputStream();

						} catch (Exception e) {}

						data.clear();
						data.putString(ComParams.BROADCAST_HANDLER_DES, "card connect successed! " + ip + ":" + port);
						sendBroadcaset(what, true, data);
						handler.removeMessages(what);
						cardFaileTimes = 0;

						return;
					}
				} catch (Exception e) {
					// e.printStackTrace();
					ULog.e(TAG, "card connect faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
				}
				if (null != handler)
					handler.sendEmptyMessageDelayed(what, 1000);
			}
		}.start();
	}

	/**
	 * 获取卡号
	 * 
	 * @deprecated
	 */
	private void getCardId(int what) {
		if (null == socketCard || !socketCard.isConnected()) {
			sendBroadcaset(ComParams.HANDLER_SOCKET_GET_CARD_ID, false, null);
			return;
		}

		userInfo = (null == userInfo) ? new UserInfo() : userInfo;
		new Thread() {
			@Override
			public void run() {
				super.run();
				byte[] input = new byte[20];
				try {
					if (cardFaileTimes < 10) {
						getInputBytes(socketCard, UserCommand.RCARDID, input);
						if (UserCommand.checkReadInput(input, 2)) {
							String cardId = "";
							for (int i = 0; i < input[6]; i++) {
								cardId += Integer.toHexString(input[7 + input[6] - i] & 0xFF);
							}
							cardId = cardId.toUpperCase();
							// TODO: 需要使用卡号的地方
							ULog.d(TAG, "cardId = " + cardId);
							if (!userInfo.getId().equals(cardId)) {
								userInfo = null;
								userInfo = new UserInfo();
								userInfo.setId(cardId);
								handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_GET_USER_INFO);
								// handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_USER_INFO, 500);
								data.clear();
								data.putString(ComParams.BROADCAST_HANDLER_CARD_ID, cardId);
								sendBroadcaset(ComParams.HANDLER_SOCKET_GET_CARD_ID, true, data);
							}
							else {
								data.clear();
								data.putParcelable(ComParams.ACTIVITY_USERINFO, userInfo);
								sendBroadcaset(ComParams.HANDLER_SOCKET_GET_USER_INFO, true, data);
							}
							cardFaileTimes = 0;
						}
						else {
							cardFaileTimes++;
							handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_CARD_ID, 500);
						}
					}
					else {
						cardFaileTimes = 0;
					}

				} catch (Exception e) {
					ULog.e(TAG, "faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
					cardFaileTimes++;
					handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_CARD_ID, 1000);
					e.printStackTrace();
				}

			}

		}.start();

	}

	/** 获取用户信息 */
	private void getUserInfo(final int what) {
		if (null == socketCard || !socketCard.isConnected()) {
			sendBroadcaset(what, false, null);
			return;
		}

		userInfo = (null == userInfo) ? new UserInfo() : userInfo;

		if (cardFaileTimes > SOCKET_CONNECT_FAILED_TIMES) {
			data.clear();
			data.putString(ComParams.ACTIVITY_ERRORINFO, "获取用户信息失败");
			data.putInt(ComParams.ACTIVITY_ERRORID, what);
			sendBroadcaset(ComParams.HANDLER_ERROR, true, data);
			handler.removeMessages(what);
			cardFaileTimes = 0;
			return;
		}

		new Thread() {
			@Override
			public void run() {
				super.run();
				cardFaileTimes++;
				try {
					byte[] input = new byte[256];
					int size = 0;

					int failTimes = 10; // for循环中的每一条数据都有10次失败机会；
					byte[] inputTemp = null;// 不能小于 UserCommand.Rlist_INFO[i][9] * 4 + 8 + 1
					for (int i = 0, length = UserCommand.Rlist_INFO.length; i < length; i++) {
						inputTemp = null;
						inputTemp = new byte[60];
						ULog.d(TAG, "获取第" + i + "组数据！");
						getInputBytes(socketCard, UserCommand.Rlist_INFO[i], inputTemp);

						if (failTimes > 0) {
							if (!UserCommand.checkReadInput(inputTemp, UserCommand.Rlist_INFO[i][9])) {
								ULog.i(TAG, "第" + i + "组数据检查未通过 " + failTimes);
								i--; // 下次重新获取第i组数据，和for循环中的i++抵消；
								failTimes--;
							}
							else {
								failTimes = 10;
								for (int j = 0; j < inputTemp[6]; j++) {
									// ULog.d(TAG, "inputTemp[6] =" + inputTemp[6] + " , size = " + size +
									// " , j = "+ j+ ", [size + j] = " + (size + j));
									input[size + j] = inputTemp[8 + j];
								}
								size += inputTemp[6];
								ULog.i(TAG, "第" + i + "组数据获取成功 ");
								ULog.d(TAG, "inputTemp[6] =" + inputTemp[6] + " , size = " + size);
							}
						}
						else {
							ULog.w(TAG, "第" + i + "组数据获取失败 ");
						}

						// try {
						// this.sleep(100);
						// } catch (Exception e) {
						// ULog.i(TAG, "获取用户信息sleep(100)失败 ");
						// }

					}

					userInfo.setValue(input);
					if (userInfo.getUserId() > 0) { // 根据用户id来进行判断用户是否有效
						// ULog.e(TAG, userInfo.toString());
						data.clear();
						data.putParcelable(ComParams.ACTIVITY_USERINFO, userInfo);
						sendBroadcaset(what, true, data);
						//socketClose(); // 获取到用户数据之后，关闭socket连接；
						return;
					}

				} catch (Exception e) {
					ULog.e(TAG, "faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
					e.printStackTrace();
				}
				if (null != handler)
					handler.sendEmptyMessageDelayed(what, 100);
			}

		}.start();
	}

	/** 连接socket testZKT */
	private void connectTestZKTSocket(final String ip, final int port, final int what) {
		if (null != socketTestZKT && socketTestZKT.isConnected()) {
			ULog.d(TAG, "Alreadly connected TESTZKT! " + ip + ":" + port);
			data.clear();
			data.putString(ComParams.BROADCAST_HANDLER_DES, "Alreadly connected TESTZKT! " + ip + ":" + port);
			sendBroadcaset(what, socketTestZKT.isConnected(), data);
			return;
		}

		if (testZKTFaileTimes > SOCKET_CONNECT_FAILED_TIMES) {
			ULog.i(TAG, "Failed connect TESTZKT ... " + testZKTFaileTimes);
			// data.clear();
			// data.putString(ComParams.BROADCAST_HANDLER_DES, "Failed connect TESTZKT ... " + ip + ":" + port);
			// sendBroadcaset(what, false, data);

			data.clear();
			data.putString(ComParams.ACTIVITY_ERRORINFO, "Failed connect TESTZKT ... " + ip + ":" + port);
			data.putInt(ComParams.ACTIVITY_ERRORID, what);
			sendBroadcaset(ComParams.HANDLER_ERROR, true, data);

			handler.removeMessages(what);
			testZKTFaileTimes = 0;
			return;
		}
		else {
			ULog.i(TAG, "Connecting testZKT..." + testZKTFaileTimes);
			data.clear();
			data.putString(ComParams.BROADCAST_HANDLER_DES, "Connecting TESTZKT..." + testZKTFaileTimes);
			sendBroadcaset(what, false, data);
		}

		new Thread() {
			@Override
			public void run() {
				super.run();
				testZKTFaileTimes++;
				try {
					socketTestZKT = null;
					socketTestZKT = new Socket();
					socketTestZKT.connect(new InetSocketAddress(ip, port), ComParams.SOCKET_TIMEOUT);

					if (socketTestZKT.isConnected()) {
						ULog.i(TAG, "Successed connect testZKT! " + ip + ":" + port);
						data.clear();
						data.putString(ComParams.BROADCAST_HANDLER_DES, "testZKT connect successed! " + ip + ":" + port);
						sendBroadcaset(what, true, data);
						handler.removeMessages(what);
						testZKTFaileTimes = 0;

						try {
							byte[] input = new byte[10];
							getInputBytes(socketTestZKT, ZKTCommand.RRESTART, input);
						} catch (Exception e) {

						}

						return;
					}
				} catch (Exception e) {
					ULog.e(TAG, "connectTestZKTSocket exception" + " -- " + e.getMessage());
				}
				if (null != handler)
					handler.sendEmptyMessageDelayed(what, 1000);
			}
		}.start();

	}

	/** 重新初始化中控板连接设备 */
	private void startTestZKT(final int what) {
		if (null == socketTestZKT || !socketTestZKT.isConnected()) {
			sendBroadcaset(what, false, null);
			return;
		}

		testResult = null;
		testResult = new TestResult();

		if (testZKTFaileTimes > SOCKET_CONNECT_FAILED_TIMES) {
			data.clear();
			data.putString(ComParams.ACTIVITY_ERRORINFO, "初始化中控板失败，无法获取测试类型");
			data.putInt(ComParams.ACTIVITY_ERRORID, what);
			sendBroadcaset(ComParams.HANDLER_ERROR, true, data);
			handler.removeMessages(what);
			cardFaileTimes = 0;
			return;
		}

		/**
		 * 1： 获取中控测试设备id；<br>
		 * 2： 初始化该设备
		 */
		new Thread() {
			@Override
			public void run() {
				super.run();
				testZKTFaileTimes++;

				byte[] input = new byte[10];
				try {
					int failtime = 0;
					// 获取测试设备类型id ---> 是否可以省略
					while (failtime < 5) {
						getInputBytes(socketTestZKT, ZKTCommand.RCARDID, input);
						ULog.i(TAG, "ZKT ERROR = " + ZKTCommand.getErrorInfo(input));

						if (ZKTCommand.checkReadInput(input) && 0x02 == input[3]) {
							testResult.setId(input[3]);
							break;
						}

						failtime++;
						ULog.d(TAG, "获取中控测试设备类型id错误  " + failtime);

						try {
							this.sleep(500);
						} catch (InterruptedException e) {
							ULog.e(TAG, "获取中控测试设备类型id sleep(500)失败 ");
						}
					};

					// 初始化设备
					failtime = 0;
					while (failtime < 5 && testResult.getId() == input[3]) {
						input = null;
						input = new byte[10];
						getInputBytes(socketTestZKT, ZKTCommand.RRESTART, input);
						ULog.i(TAG, "ZKT ERROR = " + ZKTCommand.getErrorInfo(input));

						if (ZKTCommand.checkReadInput(input) && ZKTCommand.RSTART[2] == input[2]) {
							data.clear();
							data.putParcelable(ComParams.ACTIVITY_TESTRESULT, testResult);
							sendBroadcaset(what, true, data);
							return;
						}

						failtime++;
						ULog.d(TAG, "初始化设备错误  " + failtime);

						try {
							this.sleep(500);
						} catch (InterruptedException e) {
							ULog.e(TAG, "初始化设备类型 sleep(500)失败 ");
						}
					}
				} catch (Exception e) {
					ULog.e(TAG, "IOException " + e.getMessage());
					if (null != handler)
						handler.sendEmptyMessageDelayed(what, 100);
				}
			}
		}.start();

	}

	/**
	 * 获取测试结果<br>
	 * 每500毫秒获取测试结果数据，知道数据获取状态为终结状态
	 * */
	private void getTestResult(final int what) {
		if (null == socketTestZKT || !socketTestZKT.isConnected()) {
			sendBroadcaset(what, false, null);
			return;
		}

		new Thread() {
			@Override
			public void run() {
				super.run();
				byte[] input = new byte[20];
				byte[] output = new byte[5];
				output[0] = 0x5B;
				output[1] = 0x03;
				output[2] = 0x31;
				output[3] = (byte) (0x01 + (0xFF - ((output[0] + output[1] + output[2]) & 0xFF)));
				output[4] = 0x5D;
				try {
					getInputBytes(socketTestZKT, output, input);
					ULog.i(TAG, "ZKT ERROR = " + ZKTCommand.getErrorInfo(input));
					ULog.i(TAG, "checkReadInput(input) = " + ZKTCommand.checkReadInput(input));

					if (ZKTCommand.checkReadInput(input)) {
						if (input[2] == output[2]) {
							testResult.setValue(input);
							data.clear();
							data.putParcelable(ComParams.ACTIVITY_TESTRESULT, testResult);
							data.putParcelable(ComParams.ACTIVITY_USERINFO, userInfo);
							sendBroadcaset(what, true, data);

							if (input[3] == 0x01) {
								handler.removeMessages(what);
								// handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_WRITE_CARD_INFO);
								// handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_SAVE_TO_CLOUD);
								input = output = null;
								return;
							}
						}
						// // else if (testResult.getResultGray()= 0 ) {
						// // testResult.setValue(input);
						// // Bundle data = new Bundle();
						// // data.putParcelable(ComParams.ACTIVITY_TESTRESULT, testResult);
						// // sendBroadcaset(what, true, data);
						// // }
						// handler.sendEmptyMessageDelayed(what, 500);
						// return;
					}
				} catch (Exception e) {
					// ULog.e(TAG, "Exception " + e.getMessage());
				}
				if (null != handler)
					handler.sendEmptyMessageDelayed(what, 500);
				input = output = null;
			}
		}.start();

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
			case ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START:
				startTestZKT(msg.what);
				break;
			case ComParams.HANDLER_SOCKET_GET_TEST_ZKT_RESULT:
				getTestResult(msg.what);
				break;
			case ComParams.HANDLER_SOCKET_WRITE_CARD_INFO:
				new WriteGripToCard(DFService.this, userInfo, socketCard).execute(max, 1.0);
				break;
			case ComParams.HANDLER_SOCKET_WRITE_CARD_FAILED:
				// Toast.makeText(DFService.this, "握力计数据写入失败", Toast.LENGTH_LONG).show();
				break;
			case ComParams.HANDLER_SOCKET_SAVE_TO_CLOUD:
				// new SaveTestResult(DFService.this).execute(Integer.toString(userInfo.getUserId()),
				// Double.toString(Math.max(result[0], result[1])));
				break;
			case ComParams.HANDLER_SOCKET_CLOSE_CARD:
				sokectCloseCard();
				break;
			case ComParams.HANDLER_SOCKET_CLOSE_TEST_ZKT:
				sokectCloseZTK();
				break;
			}
		}
	}

}
