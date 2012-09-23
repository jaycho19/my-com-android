package com.dongfang.dicos.util;

public class Analysis {

	public static final String	TAG	= "Analysis";

	/**
	 * �滻 str �� ���������ţ�ð�ţ���б�ܺͿո�֮���ö��÷ָ������
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

		/** �滻���������ţ�ð�ţ���б�ܺͿո� */
		return str.replaceAll("\\[|\\]|\"|\\\\| ", "").split(",");
	}
}
