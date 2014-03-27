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
//		sb.append("&").append("params=").append(json.toString());
		 sb.append("&").append("params=").append(URLEncoder.encode(json.toString()));
		return sb.toString();
	}

}
