package com.dongfang.dicos.net.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class RegisterThread extends Thread {

	public static final String	tag	= "RegisterThread";

	private Handler				handler;
	private Context				context;
	private String				uName;
	private String				uPassword;
	private String				uNickName;

	public RegisterThread(Context context, Handler handler, String uName, String uPassword, String uNickName) {
		this.handler = handler;
		this.context = context;
		this.uName = uName;
		this.uPassword = uPassword;
		this.uNickName = uNickName;
	}

	@Override
	public void run() {
		String str = new HttpActions(context).register(uName, uPassword, uNickName);
		ULog.d(tag, "register result =" + str);

		int result = Integer.valueOf(str);
		Message msg = new Message();
		if (-1000 == result) {
			msg.obj = "�û������벻��Ϊ��";
		} else if (-999 == result) {
			msg.obj = "�û�����ʽ����";
		} else if (-998 == result) {
			msg.obj = "�����ʽ����";
		} else if (-997 == result) {
			msg.obj = "�ǳƳ�������";
		} else if (-1 == result) {
			msg.obj = "ע��ʧ��";
		} else {
			msg.obj = "ע��ɹ�";
		}

		msg.arg1 = result;
		msg.what = ComParams.HANDLER_RESULT_REGISTER;
		handler.sendMessage(msg);
	}

}
