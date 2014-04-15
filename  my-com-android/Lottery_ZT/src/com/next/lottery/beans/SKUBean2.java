package com.next.lottery.beans;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;

/**
 * my order bean
 * 
 * @author gfan
 * 
 */
public class SKUBean2 {
	private int costPrice;
	@NoAutoIncrement
	private int id;
	private int itemId;
	private int marketPrice;
	private int price;
	private String skuAttr;
	private String skuAttrname;
	private int status;
	private int stockNum;
//	@Id(column="_id")
//	private int _id;//设置表主键
	
	// {
	// "costPrice":0,
	// "id":12,
	// "itemId":9,
	// "marketPrice":0,
	// "price":149000,
	// "skuAttr":"1627207:28335;20509:28381",
	// "skuAttrname":"1627207:28335:颜色分类:绿色;20509:28381:尺码:XXS",
	// "status":1,
	// "stockNum":0
	// }

	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("costPrice = ").append(costPrice ).append("\n");
		sb.append("id= ").append(id).append("\n");
		sb.append("itemId= ").append(itemId).append("\n");
		sb.append("marketPrice = ").append(marketPrice ).append("\n");
		sb.append("price = ").append(price ).append("\n");
		sb.append("skuAttr = ").append(skuAttr ).append("\n");
		sb.append("skuAttrname = ").append(skuAttrname ).append("\n");
		sb.append("status= ").append(status).append("\n");
		sb.append("stockNum= ").append(stockNum).append("\n");
		return sb.toString();
	}
	
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
