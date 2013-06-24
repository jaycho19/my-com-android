package com.dongfang.apad.bean;

import java.io.UnsupportedEncodingException;

import com.dongfang.apad.util.Util;

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

	private String				id;

	/** 姓名 */
	private String				name;
	/** 性别：1男0女 */
	private int					gender;
	/** 年龄 */
	private int					age;
	/** 出生年月日 */
	private int					year;
	/** 出生年月日 */
	private int					month;
	/** 出生年月日 */
	private int					day;
	/** 身份证号码 */
	private String				IDCardNO;
	/** 籍贯 */
	private String				nativePlace;

	public UserInfo() {
		init();
	}

	public void init() {
		id = "";
		IDCardNO = "";
		nativePlace = "";
		name = "";
		gender = -1;
		age = 0;
		year = 0;
		month = 0;
		day = 0;
	}

	public void setValue(byte[] input) {
		byte[] nameArr = new byte[10];
		for (int i = 29; i < 38; i++) {
			if (input[i] != 0)
				nameArr[i - 29] = input[i];
		}
		try {
			name = new String(nameArr, "UTF-16");
		} catch (UnsupportedEncodingException e) {
			name = "error";
			e.printStackTrace();
		}

		gender = input[40];

		year = input[41] * 100 + input[42];
		month = input[43];
		day = input[44];
		nativePlace = Integer.toString(input[45]) + Integer.toString(input[46]) + Integer.toString(input[47]);
		for (int i = 52; i < 69; i++) {
			IDCardNO += Integer.toString(input[i]);
		}

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return the iDCardNO
	 */
	public String getIDCardNO() {
		return IDCardNO;
	}

	/**
	 * @param iDCardNO
	 *            the iDCardNO to set
	 */
	public void setIDCardNO(String iDCardNO) {
		IDCardNO = iDCardNO;
	}

	/**
	 * @return the nativePlace
	 */
	public String getNativePlace() {
		return nativePlace;
	}

	/**
	 * @param nativePlace
	 *            the nativePlace to set
	 */
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}

	/** 获取性别 */
	public String getGender() {
		return gender == 0 ? "女" : "男";
	}

	/** 出生年月日 */
	public String getDateOfBirth() {
		return year + "-" + month + "-" + day;
	}

	/** 获取显示在测试页面的用户信息 */
	public String getUserShowInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t会员号：" + id);
		sb.append("\t\t姓名：" + name);
		sb.append("\t\t性别：" + getGender());
		sb.append("\t\t年龄：" + getAge());
		sb.append("\t\t工作单位：闲扯淡");

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
		sb.append("id ：").append(id).append("\n");
		sb.append("姓  名：").append(name).append("\n");
		sb.append("性  别：").append(getGender()).append("\n");
		sb.append("生  日：").append(getDateOfBirth()).append("\n");
		sb.append("籍  贯：").append(nativePlace).append("\n");
		sb.append("年  龄：").append(age).append("\n");
		sb.append("身份证：").append(IDCardNO).append("\n");
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
		dest.writeString(id);
		dest.writeString(name);
		dest.writeInt(gender);
		dest.writeInt(age);
		dest.writeInt(year);
		dest.writeInt(month);
		dest.writeInt(day);
		dest.writeString(IDCardNO);
		dest.writeString(nativePlace);
	}

	public static final Parcelable.Creator<UserInfo>	CREATOR	= new Parcelable.Creator<UserInfo>() {

																	@Override
																	public UserInfo createFromParcel(Parcel source) {
																		UserInfo ui = new UserInfo();
																		ui.setId(source.readString());
																		ui.setName(source.readString());
																		ui.setGender(source.readInt());
																		ui.setAge(source.readInt());
																		ui.setYear(source.readInt());
																		ui.setMonth(source.readInt());
																		ui.setDay(source.readInt());
																		ui.setIDCardNO(source.readString());
																		ui.setNativePlace(source.readString());
																		return ui;
																	}

																	@Override
																	public UserInfo[] newArray(int size) {
																		return new UserInfo[size];
																	}
																};

}
