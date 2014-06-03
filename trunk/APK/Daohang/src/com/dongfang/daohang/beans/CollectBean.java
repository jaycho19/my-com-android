package com.dongfang.daohang.beans;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class CollectBean implements Parcelable {
	private int total;
	private int nowPage;
	private int placeId;
	private List<CollectItem> collects;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public List<CollectItem> getCollects() {
		return collects;
	}

	public void setCollects(List<CollectItem> collects) {
		this.collects = collects;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("total  ").append(" = ").append(total).append("\n");
		sb.append("placeId").append(" = ").append(nowPage).append("\n");
		sb.append("placeId").append(" = ").append(placeId).append("\n");
		if (null != collects) {
			for (int i = 0, l = collects.size(); i < l; i++) {
				sb.append("collects[").append(i).append("] = ").append(collects.get(i).toString()).append("\n");
			}
		}
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(total);
		dest.writeInt(nowPage);
		dest.writeInt(placeId);
		dest.writeTypedList(collects);
	}

	public static final Parcelable.Creator<CollectBean> CREATOR = new Creator<CollectBean>() {

		@Override
		public CollectBean[] newArray(int size) {
			return new CollectBean[size];
		}

		// 将Parcel对象反序列化为ParcelableDate
		@Override
		public CollectBean createFromParcel(Parcel source) {
			CollectBean bean = new CollectBean();
			bean.total = source.readInt();
			bean.nowPage = source.readInt();
			bean.placeId = source.readInt();
			source.readTypedList(bean.collects, CollectItem.CREATOR);
			return bean;
		}
	};

}
