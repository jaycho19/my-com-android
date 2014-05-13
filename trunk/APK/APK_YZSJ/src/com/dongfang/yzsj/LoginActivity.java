package com.dongfang.yzsj;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;

import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.RequestCallBack;
import com.dongfang.net.http.ResponseInfo;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
import com.dongfang.yzsj.fragment.LoginFragment;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;

/**
 * 登陆activity
 * 
 * @author dongfang
 * 
 */
public class LoginActivity extends BaseActivity {

	private Bundle data;
	private LoginFragment loginFragment;// 登陆Fragment
	private com.dongfang.view.ProgressDialog progDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		data = getIntent().getExtras();

		progDialog = com.dongfang.view.ProgressDialog.show(this);
		progDialog.setCancelable(true);

		findViewById(R.id.login_tv_back).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_login);
		loginFragment.setOnLoginAndVerify(new LoginFragment.OnLoginAndVerify() {

			@Override
			public void verify(boolean verify, String phoneNumber) {
				// TODO Auto-generated method stub
			}

			@Override
			public void login(boolean login, String phoneNumber) {
				if (login) {
					toResult();
					// finish();
				}
			}
		});

	}

	/** 返回信息给进入这个界面的activity */
	private void toResult() {
		if (null == data) {
			return;
		}

		if (ToDetailAsyncTask.TAG.equals(data.getString(ComParams.INTENT_TODO))) {
			new ToDetailAsyncTask(this, data.getString(ComParams.INTENT_MOVIEDETAIL_CHANNELID),
					data.getString(ComParams.INTENT_MOVIEDETAIL_CONNENTID)).execute();
		}

		else if ("TOPLAYR".equals(data.getString(ComParams.INTENT_TODO))) {
			toPlay(data.getString(ComParams.INTENT_MOVIEDETAIL_LIVEID),
					data.getInt(ComParams.INTENT_MOVIEDETAIL_TYPE, 0));
		}

	}

	/**
	 * 获取播放地址，进入播放页面
	 * 
	 * @param conntentId
	 *            内容id
	 * @param band
	 *            码流类型
	 * @param clipId
	 *            第几集
	 */
	private void toPlay(final String conntentId, final int movieType) {
		StringBuilder url = new StringBuilder(ComParams.HTTP_PLAYURL);
		url.append("token=").append(User.getToken(this));
		url.append("&").append("phone=").append(User.getPhone(this));
		url.append("&").append("contentId=").append(conntentId);
		url.append("&").append("bandwidth=").append("Media_Url_Source");
		url.append("&").append("clipId=").append(1);

		ULog.i(url.toString());

		new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d("onSuccess  --" + responseInfo.result);
				try {
					JSONObject json = new JSONObject(responseInfo.result);
					Bundle data = new Bundle();
					data.putString(ComParams.INTENT_MOVIEDETAIL_CONNENTID, conntentId);
					data.putInt(ComParams.INTENT_MOVIEDETAIL_CLIPID, 1);
					data.putInt(ComParams.INTENT_MOVIEDETAIL_TYPE, movieType);
					PlayerActivity.toPlay(LoginActivity.this, json.getString("url"), data);

					// Intent intent = new Intent(Intent.ACTION_VIEW);
					// String type = "video/*";
					// Uri uri = Uri.parse(json.getString("url"));
					// intent.setDataAndType(uri, type);
					// intent.setComponent(new
					// ComponentName("com.android.gallery3d","com.android.gallery3d.MovieActivity"));
					// startActivity(intent);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onStart() {
				ULog.i("RequestCallBack.onStart");
				progDialog.show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.i("RequestCallBack.onFailure");
				progDialog.dismiss();
			}
		});
	}

}
