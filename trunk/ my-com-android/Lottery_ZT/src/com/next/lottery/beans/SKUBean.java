package com.next.lottery.beans;

import java.util.ArrayList;

/**
 * 商品SKU属性
 * 
 * @author dongfang
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
