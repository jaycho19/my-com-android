package com.dongfang.dicos.net.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class GetPasswordThread extends Thread {

	public static final String	TAG	= "GetPasswordThread";

	private Handler				handler;
	private Context				context;
	private String				phoneNumber;

	public GetPasswordThread(Context context, Handler handler, String phoneNumber) {
		this.handler = handler;
		this.context = context;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public void run() {
		String str = new HttpActions(context).getPassword(phoneNumber);
		ULog.d(TAG, "result = " + str);
		int result = Integer.valueOf(str);

		Message msg = new Message();

		if (-1000 == result) {
			msg.obj = "��������Ч���ֻ�����";
			msg.arg1 = -1000;
		} else if (-999 == result) {
			msg.arg1 = -999;
			msg.obj = "���޴˺ţ���˶�����ͺ����Ƿ���ȷ";
		} else if (-1 == result) {
			msg.arg1 = -1;
			msg.obj = "��ȡʧ��";
		} else if (1 == result || -998 == result) {
			msg.arg1 = 1;
			msg.obj = "�鿴һ���Լ��ֻ����ţ��Ѿ���ȡ����~~";
		}

		msg.what = ComParams.HANDLER_RESULT_GET_PASSWORD;
		handler.sendMessage(msg);

	}
}
