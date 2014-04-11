package com.next.lottery.listener;

import java.util.ArrayList;
import com.next.lottery.beans.SkuList;
public interface OnSkuResultListener {
	public void onSkuResult(ArrayList<SkuList> beanResult,int num);
}
