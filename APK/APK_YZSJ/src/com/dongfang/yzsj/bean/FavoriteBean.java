package com.dongfang.yzsj.bean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 频道列表结构
 * 
 * @author dongfang
 */
public class FavoriteBean implements Parcelable {
	public static final String TAG = "HistoryBean";

	private boolean success;
	private List<String> error;
	private String beginTime;
	private String endTime;

	private TypeListData listData;

	private FavoriteBean() {}

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

	public TypeListData getListData() {
		return listData;
	}

	public void setListData(TypeListData listData) {
		this.listData = listData;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("success   = ").append(success).append("\n");
		sb.append("error     = ").append(error.get(0)).append("\n");
		sb.append("beginTime = ").append(beginTime).append("\n");
		sb.append("endTime   = ").append(endTime).append("\n");
		sb.append("listData  = ").append(listData.toString()).append("\n");
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
		dest.writeString(beginTime);
		dest.writeString(endTime);
		dest.writeParcelable(listData, 0);
	}

	public static final Parcelable.Creator<FavoriteBean> CREATOR = new Parcelable.Creator<FavoriteBean>() {

		@Override
		public FavoriteBean createFromParcel(Parcel in) {
			FavoriteBean data = new FavoriteBean();
			data.success = in.readInt() == 1 ? true : false;
			in.readStringList(data.error);
			data.beginTime = in.readString();
			data.endTime = in.readString();
			data.listData = in.readParcelable(Channel.class.getClassLoader());
			return data;
		}

		@Override
		public FavoriteBean[] newArray(int size) {
			return new FavoriteBean[size];
		}
	};

}
