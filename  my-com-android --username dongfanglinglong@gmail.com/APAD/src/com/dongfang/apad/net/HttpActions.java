package com.dongfang.apad.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.DFException;

/**
 * 对平台的请求
 * 
 * @author dongfang
 */
public class HttpActions {
	public static final String	TAG	= HttpActions.class.getSimpleName();

	private Http				https;
	private List<NameValuePair>	list;

	public HttpActions(Context context) {
		https = Http.getInstance(context);
		list = new ArrayList<NameValuePair>();
	}

	/**
	 * 启动登陆接口
	 * 
	 * @param context
	 * @return json code int 接口返回状态 <br>
	 *         msg string 成功失败描述 <br>
	 *         token string 用户token
	 * @throws DFException
	 */
	public String loading(Context context) throws DFException {
		list.clear();
		return https.get(ComParams.URL_GET, list, 2000, 5000);
	}
}
