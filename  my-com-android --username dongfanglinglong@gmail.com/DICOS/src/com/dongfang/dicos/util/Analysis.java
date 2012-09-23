package com.dongfang.dicos.util;

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
}
