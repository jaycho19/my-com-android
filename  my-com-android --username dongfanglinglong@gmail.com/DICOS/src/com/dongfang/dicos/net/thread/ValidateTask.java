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

import com.dongfang.dicos.kzmw.RegisterActivity;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

public class ValidateTask extends AsyncTask<String, Integer, Bundle> {
	private static final String	TAG	= "ValidateThread";
	private ProgressDialog		progressDialog;

	private Handler				handler;
	private Context				context;
	private String				uName;
	private String				uPassword;

	public ValidateTask(Context context, Handler handler, String uName, String uPassword) {
		this.handler = handler;
		this.context = context;
		this.uName = uName;
		this.uPassword = uPassword;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "", "��¼��...");
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (ValidateTask.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
					ValidateTask.this.cancel(true);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	protected Bundle doInBackground(String... params) {
		String str = new HttpActions(context).validate(uName, uPassword);
		ULog.d(TAG, str);
		Message msg = new Message();
		try {
			JSONObject json = new JSONObject(str);
			Util.setNickName(context, json.optString("nick", ""));
			Util.saveToken(context, json.optString("token", ""));
			Util.saveTS(context, json.optString("ts", ""));
			Util.saveUserNO(context, json.optString("uno", ""));

			int result = json.getInt("s");
			if (-1000 == result) {
				msg.obj = "�û������벻��Ϊ��";
			}
			else if (-1001 == result) {
				msg.obj = "�ѵ�¼";
			}
			else if (-1 == result) {
				msg.arg1 = -1;
				msg.obj = "�û������������";
			}
			else {
				msg.obj = "��¼�ɹ�";
			}
			msg.arg1 = result;
		} catch (JSONException e) {
			ULog.e(TAG, e.toString());
			msg.arg1 = -1;
			msg.obj = "�û������������";
		}

		msg.what = ComParams.HANDLER_RESULT_VALIDATE;
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
