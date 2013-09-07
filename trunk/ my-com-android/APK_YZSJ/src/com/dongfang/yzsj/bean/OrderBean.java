package com.dongfang.yzsj.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 订购返回产品列表信息
 * 
 * @author dongfang
 * 
 */
public class OrderBean implements Parcelable {
	private String userId;// 用户登录Id（即手机号码）
	private String channelId;// 影片所属的频道Id
	private String contentId; // 影片id标识
	private List<OrderProduct> products;// 产品列表

	private boolean success = true;
	private List<String> error;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public List<OrderProduct> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
		sb.append("success   = ").append(success).append("\n");
		if (null != error && error.size() > 0) {
			sb.append("error     = ").append(error.get(0)).append("\n");
		}
		else {
			sb.append("error     = ").append("").append("\n");
		}
		sb.append("userId    = ").append(userId).append("\n");
		sb.append("channelId = ").append(channelId).append("\n");
		sb.append("contentId = ").append(contentId).append("\n");
		for (int i = 0, length = products.size(); i < length; i++)
			sb.append("description ").append(i).append("= ").append(products.get(i).toString()).append("\n");
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(success ? 1 : 0);
		dest.writeStringList(error);
		dest.writeString(userId);
		dest.writeString(channelId);
		dest.writeString(contentId);
		dest.writeTypedList(products);
	}

	public static final Parcelable.Creator<OrderBean> CREATOR = new Parcelable.Creator<OrderBean>() {
		@Override
		public OrderBean createFromParcel(Parcel in) {
			OrderBean data = new OrderBean();
			data.success = in.readInt() == 1 ? true : false;
			data.error = in.createStringArrayList();
			data.userId = in.readString();
			data.channelId = in.readString();
			data.contentId = in.readString();
			data.products = in.createTypedArrayList(OrderProduct.CREATOR);
			return data;
		}

		@Override
		public OrderBean[] newArray(int size) {
			return new OrderBean[size];
		}
	};

}
