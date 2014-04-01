package com.next.lottery.beans;

import com.dongfang.utils.ULog;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopCartsInfo implements Parcelable {
	private String	id;
	private int		count;
	private int		stickNum;
	private String	userId;
	private String	merId;
	private String	skuId;
	private String	price;
	private int		isSelected;	// 0表示未选中 1 表示选中 writeToParcel方法里面没有写入boolean值方法
								// fuck!

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

	public int getStickNum() {
		return stickNum;
	}

	public void setStickNum(int stickNum) {
		this.stickNum = stickNum;
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

	/**
	 * 0表示未选中 1 表示选中
	 */
	public int getIsSelected() {
		return isSelected;
	}

	/**
	 * 0表示未选中 1 表示选中
	 */
	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("userId = ").append(userId).append("\n");
		sb.append("merId = ").append(merId).append("\n");
		sb.append("skuId = ").append(skuId).append("\n");
		sb.append("price = ").append(price).append("\n");
		sb.append("id = ").append(id).append("\n");
		sb.append("count = ").append(count).append("\n");
		sb.append("stickNum = ").append(stickNum).append("\n");
		sb.append("isSelected = ").append(isSelected).append("\n");
		return sb.toString();

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(userId);
		dest.writeString(merId);
		dest.writeString(skuId);
		dest.writeString(price);
		dest.writeInt(count);
		dest.writeInt(stickNum);
		dest.writeInt(isSelected);
		dest.writeString(id);
	}

	public static final Parcelable.Creator<ShopCartsInfo>	CREATOR	= new Creator<ShopCartsInfo>() {

																		public ShopCartsInfo createFromParcel(
																				Parcel source) {
																			
																			ShopCartsInfo data = new ShopCartsInfo();
																			ULog.i("source-->"+source.toString());
																			data.userId = source.readString();
																			data.merId = source.readString();
																			data.skuId = source.readString();
																			data.price = source.readString();
																			data.id = source.readString();
																			data.stickNum = source.readInt();
																			data.count = source.readInt();
																			data.isSelected = source.readInt();
																			ULog.i("data-->"+data.toString());
																			return data;
																		}

																		public ShopCartsInfo[] newArray(int size) {
																			ULog.i("size-->"+size);
																			return new ShopCartsInfo[size];
																		}
																	};

}
