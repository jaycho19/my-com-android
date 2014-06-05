package com.dongfang.utils;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 各类Json数据解析
 * 
 * @author dongfang
 * 
 */
public class JsonAnalytic {
	private static final String KEY_CODE = "code";
	private static final String KEY_MSG = "msg";
	private static final String KEY_INFO = "info";

	private static JsonAnalytic jsonAnalytic = null;

	public static JsonAnalytic getInstance() {
		if (null == jsonAnalytic) {
			jsonAnalytic = new JsonAnalytic();
		}
		return jsonAnalytic;
	}

	/**
	 * 
	 * 模版解析
	 * 
	 * @param json
	 * @param tClass
	 * @return
	 * @throws DFException
	 */
	public <T> T analyseJsonT(String json, Class<T> tClass) throws DFException {
		if (TextUtils.isEmpty(json))
			throw new DFException(DFException.JSON_ANALYTIC_STRING_NULL);
		try {
			return new Gson().fromJson(json, tClass);
		} catch (Exception e) {
			throw new DFException(e);
		}
	}

	/**
	 * 
	 * 模版解析
	 * 
	 * @param json
	 * @param tClass
	 * @return
	 * @throws DFException
	 */
	public <T> T analyseJsonT(String json, Type type) throws DFException {
		if (TextUtils.isEmpty(json))
			throw new DFException(DFException.JSON_ANALYTIC_STRING_NULL);
		try {
			return new Gson().fromJson(json, type);
		} catch (Exception e) {
			throw new DFException(e);
		}
	}

	/**
	 * 模版解析
	 * 
	 * @param json
	 * @param tClass
	 * @return
	 * @return
	 * @throws DFException
	 */
	public <T> T analyseJsonTInfo(String json, Class<T> tClass) throws DFException {
		if (TextUtils.isEmpty(json))
			throw new DFException(DFException.JSON_ANALYTIC_STRING_NULL);
		try {
			JSONObject jsonObj = new JSONObject(json);
			int code = jsonObj.optInt(KEY_CODE);
			if (0 != code) {
				throw new DFException(jsonObj.optString(KEY_MSG), code);
			}
			return new Gson().fromJson(jsonObj.getString(KEY_INFO), tClass);
		} catch (Exception e) {
			throw new DFException(e);
		}
	}

	/**
	 * 模版解析2(针对返回110 的code)
	 * 
	 * @param json
	 * @param tClass
	 * @return
	 * @throws DFException
	 */

	public <T> List<T> analyseJsonTInfoList(String json, Type type) throws DFException {
		if (TextUtils.isEmpty(json))
			throw new DFException(DFException.JSON_ANALYTIC_STRING_NULL);
		try {
			JSONObject jsonObj = new JSONObject(json);
			int code = jsonObj.optInt(KEY_CODE);
			if (0 != code) {
				throw new DFException(jsonObj.optString(KEY_MSG), code);
			}
			return new Gson().fromJson(jsonObj.getString(KEY_INFO), type);
		} catch (Exception e) {
			throw new DFException(e);
		}
	}

}
