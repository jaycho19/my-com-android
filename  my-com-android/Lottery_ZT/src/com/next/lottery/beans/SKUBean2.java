package com.next.lottery.beans;

import java.util.ArrayList;

/**
 * my order bean
 * 
 * @author gfan
 * 
 */
public class SKUBean2 {
	private int costPrice;
	private int itemId;
	private int marketPrice;
	private int  price;
	private String skuAttr;
	private String skuAttrname;
	private int status;
	private int stockNum;
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(int costPrice) {
		this.costPrice = costPrice;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(int marketPrice) {
		this.marketPrice = marketPrice;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getSkuAttr() {
		return skuAttr;
	}
	public void setSkuAttr(String skuAttr) {
		this.skuAttr = skuAttr;
	}
	public String getSkuAttrname() {
		return skuAttrname;
	}
	public void setSkuAttrname(String skuAttrname) {
		this.skuAttrname = skuAttrname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStockNum() {
		return stockNum;
	}
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
}
