package com.dongfang.apad.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

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
	public static final String	TAG	= DFService.class.getSimpleName();

	private Handler				handler;
	private Socket				socketCard;
	private Socket				socketTestZKT;
	private Bundle				data;

	/** 第几次测试 */
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
		data = new Bundle();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (null != intent && null != intent.getIntArrayExtra(ComParams.SERVICE_HANDLER_ACTION_ID)) {
			if (intent.getIntArrayExtra(ComParams.SERVICE_HANDLER_ACTION_ID).length > 0) {
				for (int i : intent.getIntArrayExtra(ComParams.SERVICE_HANDLER_ACTION_ID)) {
					ULog.d(TAG, "HandlerID = " + i);
					handler.sendEmptyMessage(i);
				}
			}
		}

		if (null != intent && !TextUtils.isEmpty(intent.getStringExtra(ComParams.SERVICE_HANDLER_REMOVE_ALL))) {
			handler.removeMessages(ComParams.HANDLER_SOCKET_CONNECT_CARD);
			handler.removeMessages(ComParams.HANDLER_SOCKET_GET_CARD_ID);
			handler.removeMessages(ComParams.HANDLER_SOCKET_GET_USER_INFO);

			handler.removeMessages(ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT);
			handler.removeMessages(ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START);
			handler.removeMessages(ComParams.HANDLER_SOCKET_GET_TEST_ZKT_RESULT);

			// socketClose();
		}
		if (null != intent && !TextUtils.isEmpty(intent.getStringExtra(ComParams.SERVICE_CLEAR_TESTINFO))) {
			testResult = null;
		}

		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ULog.d(TAG, "onDestroy");
		handler = null;
		socketClose();
	}

	/** 关闭socket */
	private void socketClose() {
		try {
			if (null != socketCard) {
				socketCard.close();
				socketCard = null;
			}
		} catch (IOException e) {
			socketCard = null;
			e.printStackTrace();
		}
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

	public void setSocketCard(Socket socket) {
		socketCard = socket;
	}

	/** 发送广播 */
	private void sendBroadcaset(int what, boolean isConnected, Bundle data) {
		Intent intent = new Intent(getPackageName() + "." + UpdateDataReceiver.TAG);
		if (null != data)
			intent.putExtras(data);
		intent.putExtra(ComParams.BROADCAST_HANDLER_IS_CONNECTED, isConnected);
		intent.putExtra(ComParams.BROADCAST_HANDLER_ACTION_ID, what);
		sendBroadcast(intent);
	}

	/** 连接失败次数 */
	private int	cardFaileTimes	= 0;

	/**
	 * 往socket写入output数据，返回的内容输入到input中
	 * 
	 * @param socket
	 * @param output
	 * @param input
	 * @throws IOException
	 */
	private void getInputBytes(Socket socket, byte[] output, byte[] input) throws Exception {
		ULog.d(TAG, "OUTPUT : " + Util.bytesToHexString(output).toUpperCase());
		socket.getOutputStream().write(output);
		socket.getInputStream().read(input);
		socket.getOutputStream().flush();
		ULog.d(TAG, "INPUT  : " + Util.bytesToHexString(input).toUpperCase());
		socket.getOutputStream().flush();
	}

	/** @deprecated */
	private void getReadCardInfo(Socket socket, byte[] output, byte[] input) throws IOException {
		if (output.length > 10 && output[9] > 0x0C) {
			ULog.d(TAG, "OUTPUT 0 : " + Util.bytesToHexString(output).toUpperCase());
			int failTimes = 5;
			for (int i = 0, length = output[9]; i < length / 0x0C + (length % 0x0C > 0 ? 1 : 0); i++) {
				output[9] = (byte) (((length - i * 0x0C) > 0x0C) ? 0x0C : (length - i * 0x0C));

				ULog.d(TAG, "length  = " + output[9]);
				byte[] inputTemp = new byte[output[9] * 4 + 8];
				// getInputBytes(socket, output, inputTemp);

				if (inputTemp[4] != 0x00) {
					failTimes = 5;
					output[8] += 0x0C;
					continue;
				}
				else if (inputTemp[6] < 0x01 && failTimes > 0) {
					failTimes--;
					i--;
				}
				else {
					failTimes = 5;
					for (int j = 0; j < inputTemp[6]; j++) {
						input[i * 0x0C + j] = inputTemp[j + 8];
					}

					output[8] += 0x0C;
				}
			}

			ULog.d(TAG, "INPUT 0 : " + Util.bytesToHexString(input).toUpperCase());
		}
		else {
			// getInputBytes(socket, output, input);
		}
	}

	/**
	 * 连接socket card
	 */
	private void connectCardSocket(final String ip, final int port, final int what) {
		if (null != socketCard && socketCard.isConnected()) {
			ULog.d(TAG, "card connect has alreadly successed! " + ip + ":" + port);
			data.clear();
			data.putString(ComParams.BROADCAST_HANDLER_DES, "card connect successed! " + ip + ":" + port);
			sendBroadcaset(what, socketCard.isConnected(), data);
			return;
		}
		else {
			socketCard = null;
		}

		ULog.d(TAG, "card connecting ..." + cardFaileTimes);
		data.clear();
		data.putString(ComParams.BROADCAST_HANDLER_DES, "card connecting ..." + cardFaileTimes);
		sendBroadcaset(what, false, data);

		new Thread() {
			@Override
			public void run() {
				super.run();
				if (cardFaileTimes < 5) {
					try {
						socketCard = new Socket();
						socketCard.connect(new InetSocketAddress(ComParams.IP_CARD, ComParams.PORT_CARD), ComParams.SOCKET_TIMEOUT);
						// socketCard = new Socket(ip, port);
						// socketCard.setSoTimeout(ComParams.SOCKET_TIMEOUT);

						if (socketCard.isConnected()) {
							cardFaileTimes = 0;
							ULog.d(TAG, "card connect successed! " + ip + ":" + port);
							data.clear();
							data.putString(ComParams.BROADCAST_HANDLER_DES, "card connect successed! " + ip + ":" + port);
							sendBroadcaset(what, socketCard.isConnected(), data);
							// handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_GET_CARD_ID);
						}
					} catch (Exception e) {
						// e.printStackTrace();
						ULog.e(TAG, "card connect faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
						cardFaileTimes++;
						handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_CONNECT_CARD, ComParams.SOCKET_TIMEOUT);
					}
				}

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
								Bundle data = new Bundle();
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

	private UserInfo	userInfo;

	/** 获取用户信息 */
	private void getUserInfo(final int what) {
		if (null == userInfo)
			userInfo = new UserInfo();

		new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					byte[] input = new byte[256];
					int size = 0;

					if (cardFaileTimes < 5) {
						int failTimes = 10;
						byte[] inputTemp = new byte[60];// 不能小于 UserCommand.Rlist_INFO[i][9] * 4 + 8 + 1
						for (int i = 0, length = UserCommand.Rlist_INFO.length; i < length; i++) {
							inputTemp = null;
							inputTemp = new byte[60];

							ULog.d(TAG, "获取第" + i + "组数据！");
							getInputBytes(socketCard, UserCommand.Rlist_INFO[i], inputTemp);
							if (failTimes > 0) {
								if (!UserCommand.checkReadInput(inputTemp, UserCommand.Rlist_INFO[i][9])) {
									ULog.d(TAG, "第" + i + "组数据检查未通过 " + failTimes);
									i--;
									failTimes--;
								}
								else {
									failTimes = 10;
									// TODO: 拼装化用户信息
									for (int j = 0; j < inputTemp[6]; j++) {
										// ULog.d(TAG, "inputTemp[6] =" + inputTemp[6] + " , size = " + size + " , j = "
										// + j
										// + ", [size + j] = " + (size + j));
										input[size + j] = inputTemp[8 + j];
									}
									size += inputTemp[6];
									ULog.d(TAG, "inputTemp[6] =" + inputTemp[6] + " , size = " + size);

									inputTemp = null;
								}
							}
						}

						userInfo.setValue(input);
						if (userInfo.getUserId() > 0) {
							// ULog.e(TAG, userInfo.toString());
							Bundle data = new Bundle();
							data.putParcelable(ComParams.ACTIVITY_USERINFO, userInfo);
							sendBroadcaset(ComParams.HANDLER_SOCKET_GET_USER_INFO, true, data);
						}
						else {
							handler.sendEmptyMessageDelayed(what, 1000);
							cardFaileTimes = 0;
						}

						socketCard.close();
						socketCard = null;
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

		// initData(null);
		// TODO:
	}

	private int			testZKTFaileTimes	= 0;
	private TestResult	testResult;

	/** 连接socket testZKT */
	private void connectTestZKTSocket(final String ip, final int port, final int what) {
		if (null != socketTestZKT && socketTestZKT.isConnected()) {
			ULog.d(TAG, "testZKT connect has alreadly successed! " + ip + ":" + port);
			data.clear();
			data.putString(ComParams.BROADCAST_HANDLER_DES, "testZKT connect has alreadly successed! " + ip + ":" + port);
			sendBroadcaset(what, socketTestZKT.isConnected(), data);
			return;
		}
		else {
			socketTestZKT = null;
		}

		ULog.d(TAG, "testZKT connecting ..." + testZKTFaileTimes);
		data.clear();
		data.putString(ComParams.BROADCAST_HANDLER_DES, "testZKT connecting ..." + testZKTFaileTimes);
		sendBroadcaset(what, false, data);
		if (testZKTFaileTimes < 10) {
			new Thread() {
				@Override
				public void run() {
					super.run();
					try {
						socketTestZKT = new Socket(ip, port);
						socketCard.setSoTimeout(ComParams.SOCKET_TIMEOUT);

						if (socketTestZKT.isConnected()) {
							testZKTFaileTimes = 0;
							ULog.d(TAG, "testZKT connect successed! " + ip + ":" + port);
							data.clear();
							data.putString(ComParams.BROADCAST_HANDLER_DES, "testZKT connect successed! " + ip + ":" + port);
							sendBroadcaset(what, socketCard.isConnected(), data);
						}
						else {
							testZKTFaileTimes++;
							handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT, 1000);
						}
					} catch (Exception e) {
						ULog.e(TAG, "faileTimes = " + testZKTFaileTimes + " -- " + e.getMessage());
						testZKTFaileTimes++;
if (null != handler)						handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT, 1000);
					}
				}

			}.start();

		}
		else {
			testZKTFaileTimes = 0;
			data.clear();
			data.putString(ComParams.BROADCAST_HANDLER_DES, "testZKT connect failed! " + ip + ":" + port);
			sendBroadcaset(what, false, data);
			ULog.d(TAG, "testZKT connect failed! " + ip + ":" + port);

		}
	}

	/** 重新初始化中控板连接设备 */
	private void startTestZKT(final int what) {
		if (null == socketTestZKT || !socketTestZKT.isConnected()) {
			sendBroadcaset(what, false, null);
			return;
		}

		testResult = null;
		testResult = new TestResult();
		// ULog.d(TAG, testResult.toString());
		if (testZKTFaileTimes < 10) {
			new Thread() {
				@Override
				public void run() {
					super.run();
					byte[] input = new byte[10];
					try {
						int failtime = 0;
						while (failtime < 10) {
							getInputBytes(socketTestZKT, ZKTCommand.RCARDID, input);
							if (ZKTCommand.checkReadInput(input) && 0x02 == input[3] ) {
//								if (testResult.getId() != input[3])
									testResult.setId(input[3]);
								break;
							}
							failtime++;
							ULog.d(TAG, "获取设备类型id错误  " + failtime);
							try {
								this.sleep(300);
								ULog.d(TAG, "InterruptedException");
							} catch (InterruptedException e) {
								ULog.e(TAG, "InterruptedException");
								e.printStackTrace();
							}
						};

						failtime = 0;
						while (failtime < 10 && testResult.getId() == input[3]) {

							input = null;
							input = new byte[10];
							// if (testResult.getResultGray() > 0)
							getInputBytes(socketTestZKT, ZKTCommand.RRESTART, input);
							// else {
							// getInputBytes(socketTestZKT, ZKTCommand.RSTART, input);
							// }

							if (ZKTCommand.checkReadInput(input) && ZKTCommand.RSTART[2] == input[2]) {
								Bundle data = new Bundle();
								data.putParcelable(ComParams.ACTIVITY_TESTRESULT, testResult);
								sendBroadcaset(what, true, data);
								return;
							}

							failtime++;
							ULog.d(TAG, "初始化设备错误  " + failtime);
							try {
								this.sleep(300);
								ULog.d(TAG, "InterruptedException");
							} catch (InterruptedException e) {
								ULog.e(TAG, "InterruptedException");
								e.printStackTrace();
							}
						};

						testZKTFaileTimes = 0;
						data.clear();
						data.putString(ComParams.BROADCAST_HANDLER_DES, "无法获取中控板连接设id");
						sendBroadcaset(ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START, true, data);
						input = null;

					} catch (Exception e) {
						ULog.e(TAG, "IOException " + e.getMessage());
						testZKTFaileTimes++;
						handler.sendEmptyMessageDelayed(ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START, 1000);
					}
				}
			}.start();
		}
		else {
			ULog.d(TAG, "获取中控板控制器类型失败");
		}

	}

	/** 获取测试结果 */
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
				output[4] = 0x5D;
				output[3] = (byte) (0x01 + (0xFF - ((output[0] + output[1] + output[2]) & 0xFF)));
				try {
					getInputBytes(socketTestZKT, output, input);
					ULog.d(TAG, "checkReadInput(input) = " + ZKTCommand.checkReadInput(input));

					if (ZKTCommand.checkReadInput(input)) {

						if (input[2] == output[2]) {
							testResult.setValue(input);
							updateUserInfoFromTestResult();// 顺序必需在testResult.setValue(input) 之后；
							// 记录两次测试成绩
							Bundle data = new Bundle();
							data.putParcelable(ComParams.ACTIVITY_TESTRESULT, testResult);
							data.putParcelable(ComParams.ACTIVITY_USERINFO, userInfo);
							sendBroadcaset(what, true, data);

							if (input[3] == 0x01) {
								handler.removeMessages(what);
								// handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_WRITE_CARD_INFO);
								// handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_SAVE_TO_CLOUD);
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
					handler.sendEmptyMessageDelayed(what, 500);
				} catch (Exception e) {
					// ULog.e(TAG, "Exception " + e.getMessage());
					handler.sendEmptyMessageDelayed(what, 500);
				}
			}
		}.start();

	}

	/** 根据测算结果，修改用户信息 */
	private void updateUserInfoFromTestResult() {
		switch (testResult.getId()) {
		case 0x02:
			double grip = Double.valueOf(testResult.getResult());
			if (grip > userInfo.getGrip()) {
				userInfo.setGrip(grip);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 修改卡内容
	 * 
	 * @deprecated
	 */
	private void writeToCard() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				byte[] output = null;
				byte[] input = new byte[20];
				try {
					switch (testResult.getId()) {
					case 0x02:
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

						if (null == socketCard || !socketCard.isConnected()) {
							socketCard = new Socket();
							socketCard.connect(new InetSocketAddress(ComParams.IP_CARD, ComParams.PORT_CARD), ComParams.SOCKET_TIMEOUT);
						}

						getInputBytes(socketCard, output, input);
						ULog.d(TAG, "----------1------------------");
						break;
					default:
						break;
					}
				} catch (Exception e) {
					ULog.d(TAG, "握力计写入数据失败 = " + e.getMessage());
					handler.sendEmptyMessage(ComParams.HANDLER_SOCKET_WRITE_CARD_FAILED);
					e.printStackTrace();
				} finally {
					socketClose();
				}
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
				writeToCard();
				break;
			case ComParams.HANDLER_SOCKET_WRITE_CARD_FAILED:
				Toast.makeText(DFService.this, "握力计数据写入失败", Toast.LENGTH_LONG).show();
				break;
			case ComParams.HANDLER_SOCKET_SAVE_TO_CLOUD:
				// new SaveTestResult(DFService.this).execute(Integer.toString(userInfo.getUserId()),
				// Double.toString(Math.max(result[0], result[1])));
				break;
			case ComParams.HANDLER_SOCKET_CLOSE_CARD:
				try {
					socketCard.close();
					socketCard = null;
				} catch (Exception e) {
					socketCard = null;
				}
				break;
			case ComParams.HANDLER_SOCKET_CLOSE_TEST_ZKT:
				try {
					socketTestZKT.close();
					socketTestZKT = null;
				} catch (Exception e) {
					socketTestZKT = null;
				}
				break;
			}
		}
	}

}
