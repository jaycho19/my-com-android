package com.dongfang.dicos.net.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class RegisterTask extends AsyncTask<String, Integer, Bundle> {

	public static final String	TAG	= "RegisterTask";

	private Handler				handler;
	private Context				context;
	private String				uName;
	private String				uPassword;
	private String				uNickName;
	private ProgressDialog		progressDialog;
	private String[]			cno;

	public RegisterTask(Context context, Handler handler, String uName, String uPassword, String uNickName, String[] cno) {
		this.handler = handler;
		this.context = context;
		this.uName = uName;
		this.uPassword = uPassword;
		this.uNickName = uNickName;
		this.cno = cno;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "", "注册中...");
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (RegisterTask.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
					RegisterTask.this.cancel(true);
			}
		});
	}

	@Override
	protected Bundle doInBackground(String... params) {
		StringBuilder scno = new StringBuilder();
		if (!TextUtils.isEmpty(cno[0])){
			scno.append(cno[0]);
			for(int i =1 ; i < cno.length && !TextUtils.isEmpty(cno[i]); i ++){
				scno.append("|").append(cno[i]);
			}
		}
		
		
		
		String str = new HttpActions(context).register(uName, uPassword, uNickName, scno.toString());
		ULog.d(TAG, "register result =" + str);

		JSONObject json = new JSONObject();
		try {
			json = new JSONObject(str);
			ULog.e(TAG, json.optString("msg", "---"));

		} catch (JSONException e) {
			e.printStackTrace();
		}

		int result = json.optInt("s", -1);
		Message msg = new Message();
		if (1 != result && !TextUtils.isEmpty(json.optString("msg"))) {
			msg.obj = json.optString("msg");

		}
		else if (-1000 == result) {
			msg.obj = "用户名密码不得为空";
		}
		else if (-999 == result) {
			msg.obj = "用户名格式不符";
		}
		else if (-998 == result) {
			msg.obj = "密码格式不符";
		}
		else if (-997 == result) {
			msg.obj = "昵称超过长度";
		}
		else if (-1 == result) {
			msg.obj = "注册失败";
		}
		else {
			msg.obj = "注册成功";
		}

		msg.arg1 = result;
		msg.what = ComParams.HANDLER_RESULT_REGISTER;
		handler.sendMessage(msg);
		return null;
	}

	@Override
	protected void onPostExecute(Bundle result) {
		super.onPostExecute(result);
		if (progressDialog != null)
			progressDialog.dismiss();

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		ULog.i(TAG, "--> onCancelled");
		if (progressDialog != null)
			progressDialog.dismiss();

	}

}
