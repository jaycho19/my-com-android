package com.dongfang.apad;

import java.io.IOException;
import java.net.Socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.apad.analytic.GetCardInfo;
import com.dongfang.apad.bean.UserInfo;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.util.Util;

/**
 * 测试开始页面
 * 
 * @author dongfang
 * 
 */
public class StartActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void setBaseValues() {
		TAG = StartActivity.class.getSimpleName();
	}

	/** 连接socket网络card */
	private static final int	HANDLER_SOCKET_CONNECT_CARD			= 100;
	private static final int	HANDLER_SOCKET_GET_CARD_ID			= 101;
	private static final int	HANDLER_SOCKET_GET_USER_INFO		= 102;

	/** 连接socket网络testZKT */
	private static final int	HANDLER_SOCKET_CONNECT_TEST_ZKT		= 200;
	/** 初始化中控的测试数据 */
	private static final int	HANDLER_SOCKET_GET_TEST_ZKT_RESTART	= 201;
	/** 获取测试数据 */
	private static final int	HANDLER_SOCKET_GET_TEST_ZKT_RESULT	= 202;

	/** 返回按钮 */
	private TextView			btnBack;
	/** 测试页面名称 */
	private TextView			btnPageName;
	private TextView			tvTitle;
	/** 用户信息数据 */
	private TextView			tvUserInfo;
	/** 测试中数据 */
	private TextView			tvTestingResult;
	/** 测试类型图片 */
	private ImageView			imageView;

	/** 读卡器连接状态 */
	private TextView			tvCardSocketInfo;
	/** 中控板连接状态 */
	private TextView			tvTestZKTSocketInfo;

	private Handler				handler;
	private Intent				intent;
	private Socket				socketCard;
	private Socket				socketTestZKT;

	private GetCardInfo			getIdAsynctask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);

		btnBack = (TextView) findViewById(R.id.tv_back);
		btnBack.setOnClickListener(this);
		tvUserInfo = (TextView) findViewById(R.id.tv_userinfo);
		btnPageName = (TextView) findViewById(R.id.tv_page_name);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		imageView = (ImageView) findViewById(R.id.imageView);
		tvTestingResult = (TextView) findViewById(R.id.tv_testing_result);
		tvCardSocketInfo = (TextView) findViewById(R.id.tv_cardSocketInfo);
		tvTestZKTSocketInfo = (TextView) findViewById(R.id.tv_testZKTSocketInfo);

		intent = getIntent();
		handler = new MyHandler();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dongfang.apad.BaseActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		handler.sendEmptyMessage(HANDLER_SOCKET_CONNECT_CARD);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dongfang.apad.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		getIdAsynctask = null;
		getIdAsynctask = new GetCardInfo(this, handler);
		getIdAsynctask.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dongfang.apad.BaseActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		getIdAsynctask.cancel(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		ULog.d(TAG, v.toString());
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}
	}

	/**
	 * 初始化测试界面；<br>
	 * 初始化用户信息；<br>
	 * 尝试获取测试数据；
	 */
	private void initTestState(UserInfo userInfo) {
		btnPageName.setText(intent.getIntExtra(ComParams.ACTIVITY_PAGENAME, R.string.app_name));
		tvTitle.setText(intent.getIntExtra(ComParams.ACTIVITY_PAGENAME, R.string.app_name));
		imageView.setImageResource(intent.getIntExtra(ComParams.ACTIVITY_IMAGE_SRC_ID, R.drawable.card_notice));

		ULog.d(TAG, userInfo.toString());
		tvUserInfo.setText(userInfo.getUserShowInfo());

	}

	/** 连接失败次数 */
	private int	cardFaileTimes	= 0;

	/**
	 * 连接socket card
	 */
	private void connectCardSocket(String ip, int port) {
		tvCardSocketInfo.setText("card connecting ..." + cardFaileTimes);
		if (cardFaileTimes < 5) {
			try {
				socketCard = new Socket(ip, port);
				if (socketCard.isConnected()) {
					cardFaileTimes = 0;
					tvCardSocketInfo.setText("card connect successed! " + ip + ":" + port);

					handler.sendEmptyMessage(HANDLER_SOCKET_GET_CARD_ID);
					handler.sendEmptyMessage(HANDLER_SOCKET_CONNECT_TEST_ZKT);
				}
			} catch (Exception e) {
				ULog.e(TAG, "faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
				cardFaileTimes++;
				handler.sendEmptyMessageDelayed(HANDLER_SOCKET_CONNECT_CARD, 1000);
			}
		}
		else {
			cardFaileTimes = 0;
			tvCardSocketInfo.setText("card connect failed! " + ip + ":" + port);
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
	private void getCardId() {
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
				handler.sendEmptyMessageDelayed(HANDLER_SOCKET_GET_USER_INFO, 1000);

			}
			else {
				handler.sendEmptyMessageDelayed(HANDLER_SOCKET_GET_CARD_ID, 1000);
			}

		} catch (IOException e) {
			ULog.e(TAG, "faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
			cardFaileTimes++;
			handler.sendEmptyMessageDelayed(HANDLER_SOCKET_GET_CARD_ID, 1000);

			e.printStackTrace();
		}
	}

	/** 获取用户信息 */
	private void getUserInfo() {
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
				handler.sendEmptyMessageDelayed(HANDLER_SOCKET_GET_USER_INFO, 1000);
			}
			else {
				handler.sendEmptyMessageDelayed(HANDLER_SOCKET_GET_USER_INFO, 1000);
			}

		} catch (IOException e) {
			ULog.e(TAG, "faileTimes = " + cardFaileTimes + " -- " + e.getMessage());
			cardFaileTimes++;
			handler.sendEmptyMessageDelayed(HANDLER_SOCKET_GET_USER_INFO, 1000);

			e.printStackTrace();
		}
		initTestState(null);
	}

	private int	testZKTFaileTimes	= 0;

	/**
	 * 连接socket testZKT
	 */
	private void connectTestZKTSocket(String ip, int port) {
		tvCardSocketInfo.setText("testZKT connecting ..." + testZKTFaileTimes);

		if (testZKTFaileTimes < 5) {
			try {
				socketCard = new Socket(ip, port);
				if (socketCard.isConnected()) {
					testZKTFaileTimes = 0;
					tvCardSocketInfo.setText("testZKT connect successed! " + ip + ":" + port);
				}
			} catch (Exception e) {
				ULog.e(TAG, "faileTimes = " + testZKTFaileTimes + " -- " + e.getMessage());
				testZKTFaileTimes++;
				handler.sendEmptyMessageDelayed(HANDLER_SOCKET_CONNECT_TEST_ZKT, 1000);
			}
		}
		else {
			testZKTFaileTimes = 0;
			tvTestZKTSocketInfo.setText("testZKT connect failed! " + ip + ":" + port);
		}
	}

	/**重新初始化中控板连接设备*/
	private void restartTestZKT(){
		
	}
	/** 获取测试结果 */
	private void getTestResult() {
		// TODO: 获取测试数据流程，每一秒获取一次
	}

	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_SOCKET_CONNECT_CARD:
				connectCardSocket(ComParams.IP, ComParams.PORT);
				break;
			case HANDLER_SOCKET_GET_CARD_ID:
				getCardId();
				break;
			case HANDLER_SOCKET_GET_USER_INFO:
				getUserInfo();
				break;
			case HANDLER_SOCKET_CONNECT_TEST_ZKT:
				connectTestZKTSocket(ComParams.IP_TEST, ComParams.PORT);
				break;
			case HANDLER_SOCKET_GET_TEST_ZKT_RESULT:
				getUserInfo();
				break;
			case HANDLER_SOCKET_GET_TEST_ZKT_RESTART:
				if (null != socketTestZKT && socketTestZKT.isConnected()){
					
				}
				else{
					
				}
				break;
			case ComParams.HANDLER_SOCKET_GET_TESTING_RESULT:
				ULog.d(TAG, msg.getData().getParcelable(ComParams.ACTIVITY_TESTRESULT).toString());
				tvTestingResult.setText(msg.obj.toString());
				break;
			case ComParams.HANDLER_SOCKET_GET_TESTED_RESULT:
				Intent intent = new Intent(StartActivity.this, EndActivity.class);
				intent.putExtra(ComParams.ACTIVITY_TESTRESULT,
						msg.getData().getParcelable(ComParams.ACTIVITY_TESTRESULT));
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	}

}
