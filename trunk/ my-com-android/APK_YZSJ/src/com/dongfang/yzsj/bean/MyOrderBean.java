package com.dongfang.yzsj.bean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 订购返回产品列表信息
 * 
 * @author dongfang
 * 
 */
public class MyOrderBean implements Parcelable {
	private String command;
	private String beginTime;
	private String endTime;
	private boolean success;
	private OrderListData listData;// 产品列表
	private List<String> error;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public OrderListData getListData() {
		return listData;
	}

	public void setListData(OrderListData listData) {
		this.listData = listData;
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
		sb.append("command   = ").append(command).append("\n");
		sb.append("beginTime = ").append(beginTime).append("\n");
		sb.append("endTime   = ").append(endTime).append("\n");
		sb.append("success   = ").append(success).append("\n");
		sb.append("error     = ").append(error.get(0)).append("\n");
		sb.append("listData  = ").append(listData.toString()).append("\n");
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(command);
		dest.writeString(beginTime);
		dest.writeString(endTime);
		dest.writeInt(success ? 1 : 0);
		dest.writeStringList(error);
		dest.writeParcelable(listData, 0);
	}

	public static final Parcelable.Creator<MyOrderBean> CREATOR = new Parcelable.Creator<MyOrderBean>() {
		@Override
		public MyOrderBean createFromParcel(Parcel in) {
			MyOrderBean data = new MyOrderBean();
			data.command = in.readString();
			data.beginTime = in.readString();
			data.endTime = in.readString();
			data.success = in.readInt() == 1 ? true : false;
			in.readStringList(data.error);
			data.listData = in.readParcelable(Channel.class.getClassLoader());
			return data;
		}

		@Override
		public MyOrderBean[] newArray(int size) {
			return new MyOrderBean[size];
		}
	};

}
