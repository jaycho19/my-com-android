package com.dongfang.dicos.lottery;

import com.dongfang.dicos.util.ULog;

import android.widget.TextView;

public class PrizeInfoElement {

	private static final String	tag	= "PrizeInfoElement";

	public TextView				tvMobile1;
	public TextView				tvMobile2;
	public TextView				tvMobile3;

	public void initElement(String s1, String s2, String s3) {
		ULog.d(tag, "s1 = " + s1 + ", s2 = " + s2 + ", s3 = " + s3);
		tvMobile1.setText(s1);
		tvMobile2.setText(s2);
		tvMobile3.setText(s3);

	}
}
