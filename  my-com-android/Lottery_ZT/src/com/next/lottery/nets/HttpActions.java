package com.next.lottery.nets;

import java.net.URLEncoder;

import org.json.JSONArray;

import com.dongfang.utils.ULog;
import com.google.gson.JsonArray;
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
	 * 删除购物车接口
	 * {"userToken":"123456","merId":"1","userId":"3","Id":1}
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
	
	/*
	 * 生成订单接口
	 * {"userId":"3","merId":1,"userToken":"532fea9f115d5","userDeliveryAddressId" :3,
	 * "payModeId":1,"price":398700,"deliveryModeId":2,"branchId":0,"expressId":0,
	 * "isLgtype":2,"invoice":{"title":"发票title","type":1,"content":"发票内容",
	 * "isDetail" :2},"items":[{"itemId":1,"skuId":1,"count":2},{"itemId":2,"skuId":3,
	 * "count":2}],"coupons":["1"],"activitys":[1,2]}
	 */
	public static String creatOrder() {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("order");
		sb.append("&").append("method=").append("create");

		JsonObject json = new JsonObject();
		json.addProperty("userId", "3");
		json.addProperty("merId", 1);
		json.addProperty("userToken", "123456");
		json.addProperty("userDeliveryAddressId", 3);
		json.addProperty("payModeId", 1);
		json.addProperty("price", 398700);
		json.addProperty("deliveryModeId", 2);
		json.addProperty("branchId", 0);
		json.addProperty("expressId", 0);
		json.addProperty("isLgtype", 2);
		
		
		JsonObject jsonInvoice = new JsonObject();
		jsonInvoice.addProperty("title", "发票title");
		jsonInvoice.addProperty("type", 1);
		jsonInvoice.addProperty("content", "发票内容");
		jsonInvoice.addProperty("isDetail", 2);
		json.add("invoice", jsonInvoice);
		
		JsonArray jsonArray = new JsonArray();
		JsonObject  json1 = new JsonObject();
		json1.addProperty("itemId", 1);
		json1.addProperty("skuId", 1);
		json1.addProperty("count", 2);
		JsonObject  json11 = new JsonObject();
		json11.addProperty("itemId", 2);
		json11.addProperty("skuId", 3);
		json11.addProperty("count", 2);
		
//		"coupons":["1"],"activitys":[1,2]
		JSONArray coupons = new JSONArray(); 
		coupons.put("1");
		JSONArray activitys = new JSONArray(); 
		activitys.put(1).put(2);
		
		jsonArray.add(json1);
		jsonArray.add(json11);
		json.add("items", jsonArray);
		json.addProperty("coupons", coupons.toString());
		json.addProperty("activitys", activitys.toString());
		
		ULog.i(json.toString());
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}
	
	/*
	 * 获取订单接口
	 * {"userId":"3","merId":1,"userToken":"532fea9f115d5","page":1,"size":3}
	 */
	public static String GetMyOrderList() {
		StringBuilder sb = new StringBuilder(ComParams.HTTP_URL);
		sb.append("?").append("class=").append("order");
		sb.append("&").append("method=").append("query");

		JsonObject json = new JsonObject();
		json.addProperty("userId", "3");
		json.addProperty("merId", "1");
		json.addProperty("userToken", "123456");
		json.addProperty("page", "1");
		json.addProperty("size", 3);
		sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

}
