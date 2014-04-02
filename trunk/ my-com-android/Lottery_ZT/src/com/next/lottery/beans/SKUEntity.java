package com.next.lottery.beans;

import java.util.ArrayList;

/**
 * SKU属性
 * 
 * @author dongfang
 * 
 */
public class SKUEntity {

	private String skuId;
	private String skuName;

	private ArrayList<SKUItem> skuTypesList;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public ArrayList<SKUItem> getSkuTypesList() {
		return skuTypesList;
	}

	public void setSkuTypesList(ArrayList<SKUItem> skuTypesList) {
		this.skuTypesList = skuTypesList;
	}


}
