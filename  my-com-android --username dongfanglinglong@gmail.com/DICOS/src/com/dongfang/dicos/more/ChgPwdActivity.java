package com.dongfang.dicos.more;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.HttpActions;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

public class ChgPwdActivity extends Activity implements OnClickListener {
	public static final String	TAG	= ChgPwdActivity.class.getName();

	private Context				context;

	private Button				bOK, bBack;
	private EditText			etOldPwd, etNewPwd, etConfirmPwd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_chgpwd);

		context = this;

		bOK = (Button) findViewById(R.id.chgpwd_button_ok);
		bBack = (Button) findViewById(R.id.chgpwd_button_back);

		bOK.setOnClickListener(this);
		bBack.setOnClickListener(this);

		etOldPwd = (EditText) findViewById(R.id.chgpwd_edittext_oldpwd);
		etNewPwd = (EditText) findViewById(R.id.chgpwd_edittext_newpwd);
		etConfirmPwd = (EditText) findViewById(R.id.chgpwd_edittext_confirmpwd);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chgpwd_button_ok:
			if (etOldPwd.getText().length() < 6 || etNewPwd.getText().length() < 6) {
				new AlertDialog.Builder(this).setTitle("密码输入错误，请确保密码长度不少于6个字符！").setPositiveButton("确定", null)
						.setNegativeButton("取消", null).show();
			}
			else if (!etNewPwd.getText().toString().equals(etConfirmPwd.getText().toString())) {
				new AlertDialog.Builder(this).setTitle("密码输入错误，新密码和确认密码不一致！").setPositiveButton("确定", null)
						.setNegativeButton("取消", null).show();
			}
			else {
				new ChgPwdTask().execute(Util.getPhoneNumber(context), etOldPwd.getText().toString(), etNewPwd
						.getText().toString());
			}

			break;
		case R.id.chgpwd_button_back:
			finish();
			break;
		}
	}

	class ChgPwdTask extends AsyncTask<String, Void, Bundle> {

		private ProgressDialog	progressDialog;

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(context, "", "密码修改中");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					if (ChgPwdTask.this.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
						ChgPwdTask.this.cancel(true);
				}
			});

		}

		@Override
		protected Bundle doInBackground(String... params) {
			Bundle data = null;

			ULog.i(TAG, new HttpActions(ChgPwdActivity.this).chgPassword(params[0], params[1], params[2]) + "");
			return data;
		}

		@Override
		protected void onPostExecute(Bundle result) {
			super.onPostExecute(result);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}

			if (null != result && result.containsKey("statuscode")) {
				new AlertDialog.Builder(context).setTitle("密码修改失败！").setPositiveButton("确定", null)
						.setNegativeButton("取消", null).show();
				return;
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}

}
