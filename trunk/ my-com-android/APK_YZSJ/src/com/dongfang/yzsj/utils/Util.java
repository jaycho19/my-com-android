package com.dongfang.yzsj.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/** @author dongfang */
public class Util {
	public static final String TAG = "Util";

	/** 汉字等转成unicode */
	public static String toUnicode(String s) {
		String as[] = new String[s.length()];
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			as[i] = Integer.toHexString(s.charAt(i) & 0xffff);
			s1 = s1 + "\\u" + as[i];
		}
		return s1;
	}

	/** 汉字等转成utf-8,因为需求蛋疼，不能通用 */
	public static String toUTF8(String s) {

		try {
			s = URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		s = s.replaceAll("%", "%25");
		return s;
	}

}