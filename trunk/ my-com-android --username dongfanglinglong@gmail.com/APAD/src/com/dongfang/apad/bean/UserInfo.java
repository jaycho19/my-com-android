package com.dongfang.apad.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 持卡人卡片人物信息
 * 
 * @author dongfang
 * 
 */
public class UserInfo implements Parcelable {
	public static final String	TAG	= UserInfo.class.getSimpleName();

	/** 姓名 */
	private String				sName;
	/** 性别：1男0女 */
	private int					iGender;
	/** 年龄 */
	private int					iAge;
	/** 出生年月日 */
	private int					iYear;
	/** 出生年月日 */
	private int					iMonth;
	/** 出生年月日 */
	private int					iDay;
	/** 身份证号码 */
	private String				sIDCardNO;
	/** 籍贯 */
	private String				sNativePlace;

	/** 获取性别 */
	public String getGender() {
		return iGender == 0 ? "女" : "男";
	}

	/** 出生年月日 */
	public String getDateOfBirth() {
		return iYear + "-" + iMonth + "-" + iDay;
	}

	/**
	 * @return the sName
	 */
	public String getsName() {
		return sName;
	}

	/**
	 * @param sName
	 *            the sName to set
	 */
	public void setsName(String sName) {
		this.sName = sName;
	}

	/**
	 * @return the iGender
	 */
	public int getiGender() {
		return iGender;
	}

	/**
	 * @param iGender
	 *            the iGender to set
	 */
	public void setiGender(int iGender) {
		this.iGender = iGender;
	}

	/**
	 * @return the iAge
	 */
	public int getiAge() {
		return iAge;
	}

	/**
	 * @param iAge
	 *            the iAge to set
	 */
	public void setiAge(int iAge) {
		this.iAge = iAge;
	}

	/**
	 * @return the iYear
	 */
	public int getiYear() {
		return iYear;
	}

	/**
	 * @param iYear
	 *            the iYear to set
	 */
	public void setiYear(int iYear) {
		this.iYear = iYear;
	}

	/**
	 * @return the iMonth
	 */
	public int getiMonth() {
		return iMonth;
	}

	/**
	 * @param iMonth
	 *            the iMonth to set
	 */
	public void setiMonth(int iMonth) {
		this.iMonth = iMonth;
	}

	/**
	 * @return the iDay
	 */
	public int getiDay() {
		return iDay;
	}

	/**
	 * @param iDay
	 *            the iDay to set
	 */
	public void setiDay(int iDay) {
		this.iDay = iDay;
	}

	/**
	 * @return the sIDCardNO
	 */
	public String getsIDCardNO() {
		return sIDCardNO;
	}

	/**
	 * @param sIDCardNO
	 *            the sIDCardNO to set
	 */
	public void setsIDCardNO(String sIDCardNO) {
		this.sIDCardNO = sIDCardNO;
	}

	/**
	 * @return the sNativePlace
	 */
	public String getsNativePlace() {
		return sNativePlace;
	}

	/**
	 * @param sNativePlace
	 *            the sNativePlace to set
	 */
	public void setsNativePlace(String sNativePlace) {
		this.sNativePlace = sNativePlace;
	}

	/**获取显示在测试页面的用户信息*/
	public String getUserShowInfo(){
		StringBuilder sb = new StringBuilder();
		sb.append("\t会员号：000010000001");
		sb.append("\t姓名：" + sName);
		sb.append("\t性别：" + getGender());
		sb.append("\t年龄：" + getiAge());
		sb.append("\t工作单位：闲扯淡" );
		
		
		return sb.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("姓  名：").append(sName).append("\n");
		sb.append("性  别：").append(getGender()).append("\n");
		sb.append("生  日：").append(getDateOfBirth()).append("\n");
		sb.append("籍  贯：").append(sNativePlace).append("\n");
		sb.append("年  龄：").append(iAge).append("\n");
		sb.append("身份证：").append(sIDCardNO).append("\n");
		return sb.toString();
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(sName);
		dest.writeInt(iGender);
		dest.writeInt(iAge);
		dest.writeInt(iYear);
		dest.writeInt(iMonth);
		dest.writeInt(iDay);
		dest.writeString(sIDCardNO);
		dest.writeString(sNativePlace);
	}

	public static final Parcelable.Creator<UserInfo>	CREATOR	= new Parcelable.Creator<UserInfo>() {

		/* (non-Javadoc)
		 * @see android.os.Parcelable.Creator#createFromParcel(android.os.Parcel)
		 */
		@Override
		public UserInfo createFromParcel(Parcel source) {
			UserInfo ui = new UserInfo();
			ui.setsName(source.readString());
			ui.setiGender(source.readInt());
			ui.setiAge(source.readInt());
			ui.setiYear(source.readInt());
			ui.setiMonth(source.readInt());
			ui.setiDay(source.readInt());
			ui.setsIDCardNO(source.readString());
			ui.setsNativePlace(source.readString());
			return ui;
		}

		/* (non-Javadoc)
		 * @see android.os.Parcelable.Creator#newArray(int)
		 */
		@Override
		public UserInfo[] newArray(int size) {
			return new UserInfo[size];
		}};

}
