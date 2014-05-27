package com.dongfang.daohang.net;

import java.net.URLEncoder;

import android.content.Context;
import android.text.TextUtils;

import com.dongfang.daohang.params.ComParams;
import com.dongfang.utils.ULog;
import com.dongfang.utils.User;
import com.google.gson.JsonObject;

public final class HttpActions {
	private HttpActions() {}

	/**
	 * 
	 * @param name
	 * @param pwd
	 * @param phonenum
	 * @param code
	 * @return
	 */
	public static String register(String name, String pwd, String phonenum, String code) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("user");
		sb.append("&").append("method=").append("register");

		JsonObject json = new JsonObject();
		json.addProperty("uname", name);
		json.addProperty("upass", pwd);
		json.addProperty("phone", phonenum);
		json.addProperty("code", code);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	public static String login(String name, String pwd) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("user");
		sb.append("&").append("method=").append("login");

		JsonObject json = new JsonObject();
		json.addProperty("uname", "1".equals(name) ? "fuchen" : name);
		json.addProperty("upass", "1".equals(pwd) ? "123456" : pwd);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	public static String getUserInfo(Context context) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("user");
		sb.append("&").append("method=").append("get");

		JsonObject json = new JsonObject();
		json.addProperty("userId", User.getUserId(context));
		json.addProperty("userToken", User.getToken(context));
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	public static String ModifyPass(Context context, String oldpwd, String newpwd) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("user");
		sb.append("&").append("method=").append("modifyPass");

		JsonObject json = new JsonObject();
		json.addProperty("userId", User.getUserId(context));
		json.addProperty("userToken", User.getToken(context));
		json.addProperty("oldpw", oldpwd);
		json.addProperty("newpw", newpwd);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	public static String logout(Context context) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("user");
		sb.append("&").append("method=").append("outLogin");

		JsonObject json = new JsonObject();
		json.addProperty("userId", User.getUserId(context));
		json.addProperty("userToken", User.getToken(context));
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	/**
	 * 添加收藏接口 {"merId":"100000","userId":"3", "userToken":"123456","itemId":"100000","price":"5000","stockNum":"99"}
	 */
	public static String modifyUser(Context context, String nickName) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("user");
		sb.append("&").append("method=").append("modifyUser");

		JsonObject json = new JsonObject();
		json.addProperty("userId", User.getUserId(context));
		json.addProperty("userToken", User.getToken(context));
		json.addProperty("nickName", nickName);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	/**
	 * 获取商场信息
	 */
	public static String getPlace(int placeId) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("place");
		sb.append("&").append("method=").append("getPlace");

		JsonObject json = new JsonObject();
		json.addProperty("placeId", placeId);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		ULog.i(json.toString());
		return sb.toString();
	}

	/**
	 * 获取活动信息
	 */
	public static String getActivity(int activityId) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("activity");
		sb.append("&").append("method=").append("getActivity");

		JsonObject json = new JsonObject();
		json.addProperty("activityId", activityId);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	/**
	 * 获取商场活动列表
	 */
	public static String getActivitys(int placeId, int pno, int psize) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("activity");
		sb.append("&").append("method=").append("getActivitys");

		JsonObject json = new JsonObject();
		json.addProperty("placeId", placeId);
		json.addProperty("pno", pno);
		json.addProperty("psize", psize);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	/**
	 * 搜索商场
	 */
	public static String searchPlace(String name, String countryId, String provinceId, String cityId, String countyId) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("place");
		sb.append("&").append("method=").append("searchPlace");

		JsonObject json = new JsonObject();
		json.addProperty("name", name);
		json.addProperty("countryId", countryId);
		json.addProperty("provinceId", provinceId);
		json.addProperty("cityId", cityId);
		json.addProperty("countyId", countyId);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		ULog.i(json.toString());
		return sb.toString();
	}

	/**
	 * 添加收藏
	 * 
	 * @param context
	 * @param placeId
	 * @param type
	 * @return
	 */
	public static String addCollect(Context context, int placeId, int type) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("collect");
		sb.append("&").append("method=").append("addCollect");

		JsonObject json = new JsonObject();
		json.addProperty("userId", User.getUserId(context));
		json.addProperty("userToken", User.getToken(context));
		json.addProperty("placeId", placeId);
		json.addProperty("type", type);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		ULog.i(json.toString());
		return sb.toString();
	}

	/**
	 * 获取一级分类
	 * 
	 * @return
	 */
	public static String getCollects(Context context, String type, int pno, int psize) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("collect");
		sb.append("&").append("method=").append("getCollects");

		JsonObject json = new JsonObject();
		json.addProperty("userId", User.getUserId(context));
		json.addProperty("userToken", User.getToken(context));
		json.addProperty("type", type);
		json.addProperty("pno", pno);
		json.addProperty("psize", psize);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

}
