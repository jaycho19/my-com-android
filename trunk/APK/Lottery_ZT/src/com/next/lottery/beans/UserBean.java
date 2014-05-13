package com.next.lottery.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户相关
 * 
 * @author dongfang
 * 
 */
public class UserBean implements Parcelable {

	private int code;
	private String msg;
	private UserInfo info;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public UserInfo getInfo() {
		return info;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("code = ").append(code).append("\n");
		sb.append("msg  = ").append(msg).append("\n");
		sb.append("info = ").append(info.toString()).append("\n");

		return sb.toString();

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(code);
		dest.writeString(msg);
		dest.writeParcelable(info, 0);
	}

	public static final Parcelable.Creator<UserBean> CREATOR = new Creator<UserBean>() {

		public UserBean createFromParcel(Parcel source) {
			UserBean data = new UserBean();
			data.code = source.readInt();
			data.msg = source.readString();
			data.info = source.readParcelable(UserInfo.class.getClassLoader());
			return data;
		}

		public UserBean[] newArray(int size) {
			return new UserBean[size];
		}
	};

}
