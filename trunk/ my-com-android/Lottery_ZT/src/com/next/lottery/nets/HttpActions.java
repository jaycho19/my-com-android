package com.next.lottery.nets;

import java.net.URLEncoder;

import com.google.gson.JsonObject;
import com.next.lottery.params.ComParams;

public class HttpActions {

	public static String login(String name, String pwd) {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("user");
		sb.append("&").append("method=").append("login");

		JsonObject json = new JsonObject();
		json.addProperty("merId", "100000");
		json.addProperty("phone", "15901871159");
		json.addProperty("password", "654321");
		// json.addProperty("phone", name);
		// json.addProperty("password", pwd);
		// sb.append("&").append("params=").append(json.toString());
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	/*
	 * 添加收藏接口 {"merId":"100000","userId":"3",
	 * "userToken":"123456","itemId":"100000","price":"5000","stockNum":"99"}
	 */
	public static String doFavour() {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("favorite");
		sb.append("&").append("method=").append("add");

		JsonObject json = new JsonObject();
		json.addProperty("merId", "100000");
		json.addProperty("userId", "3");
		json.addProperty("userToken", "123456");
		json.addProperty("itemId", "100000");
		json.addProperty("price", "5000");
		json.addProperty("stockNum", "99");
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	/*
	 * 加入购物车接口 {"userToken":"123456","merId":"100000","itemId":"100000",
	 * "userId":3,"skuId":60,"count":2}
	 */
	public static String AddShopCarts() {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("shopcart");
		sb.append("&").append("method=").append("add");

		JsonObject json = new JsonObject();
		json.addProperty("merId", "100000");
		json.addProperty("userId", "3");
		json.addProperty("userToken", "123456");
		json.addProperty("itemId", "100000");
		json.addProperty("skuId", "70");
		json.addProperty("count", "2");
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	/*
	 * 获取购物车列表接口 {"userToken":"123456","merId":"100000","userId":"3"}
	 */
	public static String GetShopCartsList() {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("shopcart");
		sb.append("&").append("method=").append("get");

		JsonObject json = new JsonObject();
		json.addProperty("merId", "100000");
		json.addProperty("userId", "3");
		json.addProperty("userToken", "123456");
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

	/*
	 * 删除购物车接口 {"userToken":"123456","merId":"1","userId":"3","Id":1}
	 */
	public static String DelShopCarts() {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("shopcart");
		sb.append("&").append("method=").append("del");

		JsonObject json = new JsonObject();
		json.addProperty("merId", "1");
		json.addProperty("userId", "3");
		json.addProperty("userToken", "123456");
		json.addProperty("Id", "1");
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

}
