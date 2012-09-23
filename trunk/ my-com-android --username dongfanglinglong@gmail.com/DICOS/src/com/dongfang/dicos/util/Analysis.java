package com.dongfang.dicos.util;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.text.TextUtils;

import com.dongfang.dicos.kzmw.Category;

public class Analysis {

	public static final String	TAG	= "Analysis";

	/**
	 * 替换 str 中 所有中括号，冒号，反斜杠和空格之后，用都好分割成数组
	 * 
	 * @param str
	 * @return
	 */
	public static String[] analysisCurrentSeasonImgUrl(String str) {
		if (null == str || str.length() < 3)
			return null;
		// ULog.i(TAG, str);
		// str =
		// "[\"http:\\/\\/www.dicos.com.cn\\/  images\\/  app\\/action\\/9_1348134247.jpg\"]";
		ULog.i(TAG, str);

		/** 替换所有中括号，冒号，反斜杠和空格 */
		return str.replaceAll("\\[|\\]|\"|\\\\| ", "").split(",");
	}

	/** 解析卡滋美味二级菜单显示数据 */
	public static String[] analysisKZMWInfo_type(String str) {
		return analysisCurrentSeasonImgUrl(str);
	}

	/** 解析卡滋美味首页数据 */
	public static Category analysisKZMWInfo(String str) {
		if (TextUtils.isEmpty(str) || str.equals("[]") || str.equals("-999"))
			return null;

		Category category = null;
		try {
			JSONObject jsonObj = new JSONObject(str);
			if (jsonObj.isNull("cate"))
				return null;

			category = new Category();
			JSONObject cateObj = jsonObj.getJSONObject("cate");
			for (Iterator<String> iter = cateObj.keys(); iter.hasNext();) {
				String key = iter.next();
				category.add2MenuList(key, cateObj.getString(key));
			}

			if (!jsonObj.isNull("newest"))
				category.add2ImgUrls(jsonObj.getString("newest"));

		} catch (JSONException e) {
			ULog.e(TAG, e.toString());
		}

		return category;

	}
}
