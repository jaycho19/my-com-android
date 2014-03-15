package com.next.lottery.beans;

import java.util.ArrayList;

/**
 * SKU属性
 * 
 * @author dongfang
 * 
 */
public class SKUEntity {

	private String skuName;
	private ArrayList<String> skuTypesList;

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public ArrayList<String> getSkuTypesList() {
		return skuTypesList;
	}

	public void setSkuTypesList(ArrayList<String> skuTypesList) {
		this.skuTypesList = skuTypesList;
	}
}
