package com.next.lottery.beans;

import java.util.ArrayList;

/**
 * my order bean
 * 
 * @author gfan
 * 
 */
public class SKUBean {
	private ArrayList<SKUEntity> skuList;

	public ArrayList<SKUEntity> getSkuList() {
		return skuList;
	}

	public void setSkuList(ArrayList<SKUEntity> skuList) {
		this.skuList = skuList;
	}
	
	private int num = 1; // 数量

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
