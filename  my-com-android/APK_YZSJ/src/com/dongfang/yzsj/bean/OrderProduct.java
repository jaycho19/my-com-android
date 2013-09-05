package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 订购产品包
 * 
 * @author dongfang
 * 
 */
public class OrderProduct implements Parcelable {
	private int price;// 产品的价格(可能是￥【人民币】也可能是M【M值】)
	private String payProductNo;// 产品支付ID
	private String serviceProductId;// 产品ID
	private int status = 1; // 状态，有如下状态： 0、无效，不用显示 / 1、有效/ 10、只能退订，不能购买/ 11、只能购买，不能退/ 12、只显示，不能购买也不能退/ 13、不显示，隐藏
	private String description;// 产品描述
	private int mobileProduct;// 移动端产品（数字1标识为移动端产品）
	private String spId; // 购买时需要提供的spId
	private boolean hasBuyThis = false; // 是否已经购买此产品
	private String type;// 产品资费类型（包月、按次）
	private String cspId;// 产品所属的内容提供商
	private String serviceProductName;// 产品名称

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public boolean isHasBuyThis() {
		return hasBuyThis;
	}

	public void setHasBuyThis(boolean hasBuyThis) {
		this.hasBuyThis = hasBuyThis;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPayProductNo() {
		return payProductNo;
	}

	public void setPayProductNo(String payProductNo) {
		this.payProductNo = payProductNo;
	}

	public String getServiceProductId() {
		return serviceProductId;
	}

	public void setServiceProductId(String serviceProductId) {
		this.serviceProductId = serviceProductId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMobileProduct() {
		return mobileProduct;
	}

	public void setMobileProduct(int mobileProduct) {
		this.mobileProduct = mobileProduct;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCspId() {
		return cspId;
	}

	public void setCspId(String cspId) {
		this.cspId = cspId;
	}

	public String getServiceProductName() {
		return serviceProductName;
	}

	public void setServiceProductName(String serviceProductName) {
		this.serviceProductName = serviceProductName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
		sb.append(" -- price              = ").append(price).append("\n");
		sb.append(" -- payProductNo       = ").append(payProductNo).append("\n");
		sb.append(" -- serviceProductId   = ").append(serviceProductId).append("\n");
		sb.append(" -- status             = ").append(status).append("\n");
		sb.append(" -- description        = ").append(description).append("\n");
		sb.append(" -- mobileProduct      = ").append(mobileProduct).append("\n");
		sb.append(" -- spId               = ").append(spId).append("\n");
		sb.append(" -- hasBuyThis         = ").append(hasBuyThis).append("\n");
		sb.append(" -- type               = ").append(type).append("\n");
		sb.append(" -- cspId              = ").append(cspId).append("\n");
		sb.append(" -- serviceProductName = ").append(serviceProductName).append("\n");
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(price);
		dest.writeString(payProductNo);
		dest.writeString(serviceProductId);
		dest.writeInt(status);
		dest.writeString(description);
		dest.writeInt(mobileProduct);
		dest.writeString(spId);
		dest.writeInt(hasBuyThis ? 1 : 0);
		dest.writeString(type);
		dest.writeString(cspId);
		dest.writeString(serviceProductName);
	}

	public static final Parcelable.Creator<OrderProduct> CREATOR = new Parcelable.Creator<OrderProduct>() {
		@Override
		public OrderProduct createFromParcel(Parcel in) {
			OrderProduct data = new OrderProduct();
			data.price = in.readInt();
			data.payProductNo = in.readString();
			data.serviceProductId = in.readString();
			data.status = in.readInt();
			data.description = in.readString();
			data.mobileProduct = in.readInt();
			data.spId = in.readString();
			data.hasBuyThis = in.readInt() == 1 ? true : false;
			data.type = in.readString();
			data.cspId = in.readString();
			data.serviceProductName = in.readString();
			return data;
		}

		@Override
		public OrderProduct[] newArray(int size) {
			return new OrderProduct[size];
		}
	};

}
