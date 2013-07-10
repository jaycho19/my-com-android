package com.dongfang.apad.bean;

import java.io.UnsupportedEncodingException;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.dongfang.apad.util.ULog;

/**
 * 持卡人卡片人物信息
 * 
 * @author dongfang
 * 
 */
public class UserInfo implements Parcelable {
	public static final String	TAG	= UserInfo.class.getSimpleName();

	private byte[]				readbyte;

	/**
	 * 卡号
	 * 
	 * @deprecated
	 * */
	private String				id;
	/** 用户号 */
	private int					userId;
	/** 发卡单位 */
	private String				fkdw;

	/** 姓名 */
	private String				name;
	/** 性别：1男0女 */
	private int					sex;
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
	/** 身高，体重 */
	private double				height, weight;
	/** 肺活量 */
	private int					vitalcapacity;
	/** 握力 */
	private double				grip;
	/** 第一反应时间 ,第二反应时间 */
	private double				firstReactiontime, secondReactiontime;
	/** 坐位体前屈 */
	private double				zuoWeiTi;
	/** 纵跳 */
	private double				verticalJump;
	/** 闭眼单脚直立 */
	private int					standing;
	/** 仰卧起坐 ,俯卧撑 */
	private int					sitUp, pushUp;
	/** 台阶测试 时间 ,台阶测试指数 */
	private int					stepTestTime;
	/** 台阶测试指数 */
	private double				stepTestExp;
	/** 一分钟后心率,两分钟后心率 */
	private int					heartRate1, heartRate2, heartRate3;
	/** 收缩压,舒张压 */
	private int					SBP, DBP;
	/** 安静心率 */
	private int					hRrest;
	/** 身体质量指数 ,体脂肪,骨骼肌,腰臀脂肪比 */
	private double				BMI, bodyFat, muscle, hipFatThan;
	/** 创 建 时 间 */
	private String				createDate;

	public UserInfo() {
		id = "";
		readbyte = new byte[256];
	}

	/**
	 * byte字符串转unicode字符
	 * 
	 * @param abyte
	 * @param st
	 * @param bEnd
	 * @return
	 */
	private String Byte2Unicode(byte abyte[], int st, int bEnd) { // 不包含bEnd
		StringBuffer sb = new StringBuffer();
		for (int i = st; i < bEnd;) {
			int lw = abyte[i++];
			lw = (lw < 0) ? 256 + lw : lw;
			int hi = abyte[i++];
			hi = (hi < 0) ? 256 + hi : hi;
			char c = (char) (lw + (hi << 8));
			sb.append(c);
		}

		return sb.toString();
	}

	/** 设置数据 */
	public void setValue(byte[] input) {

		// ULog.d(TAG, "setValue  input = " + Util.bytesToHexString(input).toUpperCase());

		for (int i = 0; i < Math.min(input.length, readbyte.length); i++) {
			readbyte[i] = input[i];
		}

		this.userId = (0xFF & input[26]) * 10000 + (0xFF & input[27]) * 100 + (0xFF & input[28]);
		this.name = Byte2Unicode(input, 29, 38);
		ULog.d(TAG, "name = " + name);

		sex = 0xFF & input[40];
		age = 0xFF & input[39];

		year = (0xFF & input[41]) * 100 + (0xFF & input[42]);
		month = 0xFF & input[43];
		day = 0xFF & input[44];

		fkdw = "";
		for (int i = 48; i < 52; i++) {
			if (31 < (0xFF & input[i]) && (0xFF & input[i]) < 127) {
				fkdw = fkdw + (char) input[i];
			}
		}

		nativePlace = Integer.toString(0xFF & input[45]) + Integer.toString(0xFF & input[46]) + Integer.toString(0xFF & input[47]);
		IDCardNO = "";
		for (int i = 52; i < 69; i++) {
			IDCardNO += Integer.toString(0xFF & input[i]);
		}

		weight = Double.valueOf((0xFF & readbyte[70]) + "." + (0xFF & readbyte[71]));
		height = Double.valueOf((0xFF & readbyte[72]) + "." + (readbyte[73] & 0xFF));
		vitalcapacity = Integer.valueOf((0xFF & readbyte[74]) + "" + (0xFF & readbyte[75]));
		grip = Double.valueOf((0xFF & readbyte[76]) + "." + (0xFF & readbyte[95]));
		firstReactiontime = Double.valueOf((0xFF & readbyte[77]) + "." + (0xFF & readbyte[78]));
		secondReactiontime = Double.valueOf((0xFF & readbyte[79]) + "." + (0xFF & readbyte[80]));

		if (readbyte[81] == 0x2D) {
			zuoWeiTi = Double.valueOf((0xFF & readbyte[82]) + "." + (0xFF & readbyte[83]));
		}
		else {
			zuoWeiTi = Double.valueOf("-" + (0xFF & readbyte[82]) + "." + (0xFF & readbyte[83]));
		}

		verticalJump = Double.valueOf((0xFF & readbyte[84]) + "." + (0xFF & readbyte[85]));
		standing = 0xFF & readbyte[86];
		sitUp = 0xFF & readbyte[87];
		pushUp = 0xFF & readbyte[88];
		stepTestTime = 0xFF & readbyte[89];
		heartRate1 = 0xFF & readbyte[90];
		heartRate2 = 0xFF & readbyte[91];
		heartRate3 = 0xFF & readbyte[92];
		stepTestExp = Double.valueOf((0xFF & readbyte[93]) + "." + (0xFF & readbyte[94]));
	}

	public String getFkdw() {
		return fkdw;
	}

	public void setFkdw(String fkdw) {
		this.fkdw = fkdw;
		for (int i = 0, length = fkdw.length(); i < length; i++) {
			readbyte[48 + i] = (byte) fkdw.charAt(i);
		}
	}

	public byte[] getReadbyte() {
		return readbyte;
	}

	public void setReadbyte(byte[] readbyte) {
		this.readbyte = readbyte;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
		readbyte[26] = (byte) (userId / 10000);
		readbyte[27] = (byte) ((userId % 100) / 100);
		readbyte[28] = (byte) (userId % 10000);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		byte[] namearr;
		try {
			namearr = name.getBytes("unicode");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			namearr = new byte[10];
		}

		for (int i = 0; i < Math.min(10, namearr.length); i++) {
			readbyte[29 + i] = namearr[i];
		}

	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
		readbyte[39] = (byte) age;

	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
		readbyte[41] = (byte) (year / 100);
		readbyte[42] = (byte) (year % 100);
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
		readbyte[43] = (byte) month;

	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
		readbyte[44] = (byte) day;
	}

	public String getIDCardNO() {
		return IDCardNO;
	}

	public void setIDCardNO(String iDCardNO) {
		IDCardNO = iDCardNO;

		if (TextUtils.isEmpty(iDCardNO) && iDCardNO.length() != 18) {
			for (int i = 52; i < 69; i++) {
				readbyte[i] = 0x00;
			}
		}
		else {
			for (int i = 52; i < 69; i++) {
				readbyte[i] = Byte.valueOf(iDCardNO.substring(i + 1, 1));
			}
		}
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;

		if (TextUtils.isEmpty(nativePlace)) {
			readbyte[45] = readbyte[46] = readbyte[47] = 0x00;
		}
		else {
			readbyte[45] = Byte.valueOf(nativePlace.substring(0, 2));
			readbyte[46] = Byte.valueOf(nativePlace.substring(2, 4));
			readbyte[47] = Byte.valueOf(nativePlace.substring(4, 6));
		}
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
		readbyte[72] = (byte) ((int) height);
		readbyte[73] = (byte) ((int) (height - (int) height) * 10);;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
		readbyte[70] = (byte) ((int) weight);
		readbyte[71] = (byte) ((int) (weight - (int) weight) * 10);;
	}

	public int getVitalcapacity() {
		return vitalcapacity;
	}

	public void setVitalcapacity(int vitalcapacity) {
		this.vitalcapacity = vitalcapacity;
		readbyte[74] = (byte) (vitalcapacity / 100);
		readbyte[75] = (byte) (vitalcapacity % 100);
	}

	public double getGrip() {
		return grip;
	}

	public void setGrip(double grip) {
		this.grip = grip;
		readbyte[76] = (byte) ((int) grip);
		readbyte[95] = (byte) ((int) ((grip - (int) grip) * 10));;
	}

	public double getFirstReactiontime() {
		return firstReactiontime;
	}

	public void setFirstReactiontime(double firstReactiontime) {
		this.firstReactiontime = firstReactiontime;
		readbyte[77] = (byte) ((int) firstReactiontime);
		readbyte[78] = (byte) ((int) (firstReactiontime - (int) firstReactiontime) * 10);;
	}

	public double getSecondReactiontime() {
		return secondReactiontime;
	}

	public void setSecondReactiontime(double secondReactiontime) {
		this.secondReactiontime = secondReactiontime;
		readbyte[79] = (byte) ((int) secondReactiontime);
		readbyte[80] = (byte) ((int) (secondReactiontime - (int) secondReactiontime) * 10);;
	}

	public double getZuoWeiTi() {
		return zuoWeiTi;
	}

	public void setZuoWeiTi(double zuoWeiTi) {
		this.zuoWeiTi = zuoWeiTi;
		if (zuoWeiTi > 0) {
			readbyte[81] = Byte.valueOf("-");
			readbyte[82] = (byte) ((int) zuoWeiTi);
			readbyte[83] = (byte) ((int) (zuoWeiTi - (int) zuoWeiTi) * 10);;
		}
		else {
			readbyte[81] = Byte.valueOf("+");
			readbyte[82] = (byte) ((int) Math.abs(zuoWeiTi));
			readbyte[83] = (byte) ((int) (Math.abs(zuoWeiTi) - (int) Math.abs(zuoWeiTi)) * 10);;
		}
	}

	public double getVerticalJump() {
		return verticalJump;
	}

	public void setVerticalJump(double verticalJump) {
		this.verticalJump = verticalJump;
		readbyte[84] = (byte) ((int) verticalJump);
		readbyte[85] = (byte) ((int) (verticalJump - (int) verticalJump) * 10);;
	}

	public int getStanding() {
		return standing;
	}

	public void setStanding(int standing) {
		this.standing = standing;
		readbyte[86] = (byte) standing;
	}

	public int getSitUp() {
		return sitUp;
	}

	public void setSitUp(int sitUp) {
		this.sitUp = sitUp;
		readbyte[87] = (byte) sitUp;
	}

	public int getPushUp() {
		return pushUp;
	}

	public void setPushUp(int pushUp) {
		this.pushUp = pushUp;
		readbyte[88] = (byte) pushUp;
	}

	public int getStepTestTime() {
		return stepTestTime;
	}

	public void setStepTestTime(int stepTestTime) {
		this.stepTestTime = stepTestTime;
		readbyte[89] = (byte) stepTestTime;
	}

	public double getStepTestExp() {
		return stepTestExp;
	}

	public void setStepTestExp(double stepTestExp) {
		this.stepTestExp = stepTestExp;
		readbyte[93] = (byte) ((int) stepTestExp);
		readbyte[94] = (byte) ((int) (stepTestExp - (int) stepTestExp) * 10);;

	}

	public int getHeartRate1() {
		return heartRate1;
	}

	public void setHeartRate1(int heartRate1) {
		this.heartRate1 = heartRate1;
		readbyte[90] = (byte) heartRate1;
	}

	public int getHeartRate2() {
		return heartRate2;
	}

	public void setHeartRate2(int heartRate2) {
		this.heartRate2 = heartRate2;
		readbyte[91] = (byte) heartRate2;

	}

	public int getHeartRate3() {
		return heartRate3;
	}

	public void setHeartRate3(int heartRate3) {
		this.heartRate3 = heartRate3;
		readbyte[92] = (byte) heartRate3;
	}

	public int getSBP() {
		return SBP;
	}

	public void setSBP(int sBP) {
		SBP = sBP;
	}

	public int getDBP() {
		return DBP;
	}

	public void setDBP(int dBP) {
		DBP = dBP;
	}

	public int gethRrest() {
		return hRrest;
	}

	public void sethRrest(int hRrest) {
		this.hRrest = hRrest;
	}

	public double getBMI() {
		return BMI;
	}

	public void setBMI(double bMI) {
		BMI = bMI;
	}

	public double getBodyFat() {
		return bodyFat;
	}

	public void setBodyFat(double bodyFat) {
		this.bodyFat = bodyFat;
	}

	public double getMuscle() {
		return muscle;
	}

	public void setMuscle(double muscle) {
		this.muscle = muscle;
	}

	public double getHipFatThan() {
		return hipFatThan;
	}

	public void setHipFatThan(double hipFatThan) {
		this.hipFatThan = hipFatThan;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
		readbyte[40] = (byte) ((sex == 0) ? 0x00 : 0x01);
	}

	/** 获取性别 */
	public String getGender() {
		return sex == 0 ? "女" : "男";
	}

	/** 出生年月日 */
	public String getDateOfBirth() {
		return year + "-" + month + "-" + day;
	}

	/** 获取显示在测试页面的用户信息 */
	public String getUserShowInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t会员号：" + userId);
		sb.append("\t\t姓名：" + name);
		sb.append("\t\t性别：" + getGender());
		// sb.append("\t\t年龄：" + age);
		// sb.append("\t\t工作单位：闲扯淡");

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id ：").append(id).append("\n");
		sb.append("用户id ：").append(userId).append("\n");
		sb.append("姓    名    ：").append(name).append("\n");
		sb.append("性    别    ：").append(getGender()).append("\n");
		sb.append("生    日    ：").append(getDateOfBirth()).append("\n");
		sb.append("籍    贯    ：").append(nativePlace).append("\n");
		sb.append("年    龄    ：").append(age).append("\n");
		sb.append("身 份 证    ：").append(IDCardNO).append("\n");
		sb.append("身    高    ：").append(height).append("\n");
		sb.append("体    重    ：").append(weight).append("\n");
		sb.append("肺 活 量    ：").append(vitalcapacity).append("\n");
		sb.append("握    力    ：").append(grip).append("\n");
		sb.append("第一反应时间：").append(firstReactiontime).append("\n");
		sb.append("第二反应时间：").append(secondReactiontime).append("\n");
		sb.append("坐位体 前 屈：").append(zuoWeiTi).append("\n");
		sb.append("纵        跳：").append(verticalJump).append("\n");
		sb.append("闭眼单脚直立：").append(standing).append("\n");
		sb.append("仰 卧 起 坐 ：").append(sitUp).append("\n");
		sb.append("俯 卧 撑    ：").append(pushUp).append("\n");
		sb.append("台阶测试时间：").append(stepTestTime).append("\n");
		sb.append("一分钟后心率：").append(heartRate1).append("\n");
		sb.append("二分钟后心率：").append(heartRate2).append("\n");
		sb.append("三分钟后心率：").append(heartRate3).append("\n");
		sb.append("台阶测试指数：").append(stepTestExp).append("\n");
		sb.append("收 缩 压    ：").append(SBP).append("\n");
		sb.append("舒 张 压    ：").append(DBP).append("\n");
		sb.append("安 静 心 率 ：").append(hRrest).append("\n");
		sb.append("身体质量指数：").append(BMI).append("\n");
		sb.append("体 脂 肪    ：").append(bodyFat).append("\n");
		sb.append("骨 骼 肌    ：").append(muscle).append("\n");
		sb.append("腰臀脂 肪 比：").append(hipFatThan).append("\n");
		sb.append("创 建 时 间 ：").append(createDate).append("\n");
		return sb.toString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeByteArray(readbyte);
		// ULog.d(TAG, "writeToParcel " + Util.bytesToHexString(readbyte).toUpperCase());
	}

	public static final Parcelable.Creator<UserInfo>	CREATOR	= new Parcelable.Creator<UserInfo>() {

																	@Override
																	public UserInfo createFromParcel(Parcel source) {
																		UserInfo ui = new UserInfo();
																		ui.id = source.readString();
																		byte[] input = new byte[256];
																		source.readByteArray(input);

																		// ULog.d(TAG, "createFromParcel " +
																		// Util.bytesToHexString(input).toUpperCase());

																		ui.setValue(input);
																		return ui;
																	}

																	@Override
																	public UserInfo[] newArray(int size) {
																		return new UserInfo[size];
																	}
																};

}
