package com.dongfang.yzsj.bean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderListData implements Parcelable {
	private int total;
	private List<OrderProduct> objs;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<OrderProduct> getObjs() {
		return objs;
	}

	public void setObjs(List<OrderProduct> objs) {
		this.objs = objs;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
		sb.append("-- total = ").append(total).append("\n");
		for (int i = 0, length = objs.size(); i < length; i++) {
			sb.append("-- objs ").append(i).append(" = ").append(objs.get(i).toString()).append("\n");
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
		dest.writeTypedList(objs);
	}

	public static final Parcelable.Creator<OrderListData> CREATOR = new Parcelable.Creator<OrderListData>() {
		@Override
		public OrderListData createFromParcel(Parcel in) {
			OrderListData data = new OrderListData();
			data.total = in.readInt();
			in.readTypedList(data.objs, OrderProduct.CREATOR);
			return data;
		}

		@Override
		public OrderListData[] newArray(int size) {
			return new OrderListData[size];
		}
	};

}
