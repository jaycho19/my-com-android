package com.dongfang.yzsj.bean;

/**
 * 升级返回信息
 * 
 * @author dongfang
 * 
 */
public class UpdateBean {
	private String currentVersion;
	private String downoadUrl;

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getDownoadUrl() {
		return downoadUrl;
	}

	public void setDownoadUrl(String downoadUrl) {
		this.downoadUrl = downoadUrl;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("currentVersion = ").append(currentVersion).append("\n");
		sb.append("downoadUrl     = ").append(downoadUrl);
		return sb.toString();
	}

}
