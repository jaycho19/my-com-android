package com.next.lottery.beans;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.dongfang.utils.ULog;
import com.lidroid.xutils.db.annotation.Id;

public class ShopCartsInfo implements Parcelable {
	private String id;
	private String userId;
	private String merId;
	private String itemId;
	private int price;
	private String skuId;
	private int count;
	private int stockNum;
	private boolean isSelected; 
	
	private ArrayList<String> skuList = new ArrayList<String>();
	
	public void addToSKUList(String sku){
		skuList.add(sku);
	}
	
	public void clearSKUList(){
		skuList.clear();
	}

	public ArrayList<String> getSkuList() {
		return skuList;
	}


	public void setSkuList(ArrayList<String> skuList) {
		this.skuList = skuList;
	}


	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	public int getStockNum() {
		return stockNum;
	}

	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}

	// /**
	// * 0表示未选中 1 表示选中
	// */
	// public int getIsSelected() {
	// return isSelected;
	// }
	//
	// /**
	// * 0表示未选中 1 表示选中
	// */
	// public void setIsSelected(int isSelected) {
	// this.isSelected = isSelected;
	// }

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("itemId     = ").append(itemId).append("\n");
		sb.append("userId     = ").append(userId).append("\n");
		sb.append("merId      = ").append(merId).append("\n");
		sb.append("skuId      = ").append(skuId).append("\n");
		sb.append("price      = ").append(price).append("\n");
		sb.append("id         = ").append(id).append("\n");
		sb.append("count      = ").append(count).append("\n");
		sb.append("stickNum   = ").append(stockNum).append("\n");
		sb.append("isSelected = ").append(isSelected);
		return sb.toString();

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(itemId);
		dest.writeString(userId);
		dest.writeString(merId);
		dest.writeString(skuId);
		dest.writeInt(price);
		dest.writeInt(count);
		dest.writeInt(stockNum);
		dest.writeInt(isSelected ? 1 : 0);
		dest.writeString(id);
	}

	public static final Parcelable.Creator<ShopCartsInfo> CREATOR = new Creator<ShopCartsInfo>() {

		public ShopCartsInfo createFromParcel(Parcel source) {

			ShopCartsInfo data = new ShopCartsInfo();
			ULog.i("source-->" + source.toString());
			data.itemId = source.readString();
			data.userId = source.readString();
			data.merId = source.readString();
			data.skuId = source.readString();
			data.price = source.readInt();
			data.count = source.readInt();
			data.stockNum = source.readInt();
			data.isSelected = source.readInt() == 1 ? true : false;
			data.id = source.readString();
			return data;
		}

		public ShopCartsInfo[] newArray(int size) {
			ULog.i("size-->" + size);
			return new ShopCartsInfo[size];
		}
	};

}
