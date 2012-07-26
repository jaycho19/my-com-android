package com.dongfang.dicos.lottery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.thread.LotteryThread;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

public class JoinInActivity extends Activity implements OnClickListener {

	public static final String	tag	= "JoinInActivity";

	/** ���ذ�ť */
	private Button				bBack;
	/** ȷ�ϰ�ť */
	private Button				bOK;

	/** СƱ���� */
	private EditText			etTickerNumber;
	/** СƱ��� */
	private EditText			etTickerAmount;

	private Context				context;
	private JoinInHandler		handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lottery_joinin);

		context = this;
		handler = new JoinInHandler();

		bBack = (Button) findViewById(R.id.button_lottery_joinin_back);
		bBack.setOnClickListener(this);

		bOK = (Button) findViewById(R.id.button_lottery_joinin_ok);
		bOK.setOnClickListener(this);

		etTickerNumber = (EditText) findViewById(R.id.editText_lottery_joinin_ticketnumber);
		etTickerAmount = (EditText) findViewById(R.id.editText_lottery_joinin_ticketamount);

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_lottery_joinin_ok:
			String sTickerNumber = etTickerNumber.getText().toString().trim();
			String sTickerAmount = etTickerAmount.getText().toString().trim();
			ULog.d(tag, "etTickerNumber " + sTickerNumber);
			ULog.d(tag, "etTickerAmount " + sTickerAmount);

			if (sTickerNumber.length() > 10 && sTickerAmount.length() > 0) {
				new LotteryThread(context, handler, sTickerNumber, sTickerAmount, Util.getPhoneNumber(context)).start();
			} else {
				Toast.makeText(JoinInActivity.this, "��������ȷ��СƱ����Ͷ�Ӧ���", Toast.LENGTH_LONG).show();
			}

			break;
		case R.id.button_lottery_joinin_back:
			finish();
			break;
		}
	}

	class JoinInHandler extends Handler {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ComParams.HANDLER_RESULT_LOTTERY:
				Bundle data = msg.getData();
				if (Actions.ACTIONS_TYPE_LOTTERY.equalsIgnoreCase(data.getString(Actions.ACTIONS_KEY_ACT))
						&& "1".equals(data.getString(Actions.ACTIONS_KEY_RESULT))) {
					Toast.makeText(context, "�齱�ɹ�", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(context, "�齱ʧ��", Toast.LENGTH_LONG).show();
				}
				break;
			}
		}

	}

}
