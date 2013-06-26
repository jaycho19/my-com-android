package com.dongfang.apad;

import android.os.Bundle;

public interface OnUpdateDataListener {
	public void onSocketConnectCard(boolean isConnect, Bundle data);

	public void onGetCardId(boolean isConnect, Bundle data);

	public void onGetUserInfo(boolean isConnect, Bundle data);

	public void onSocketConnectTestZKT(boolean isConnect, Bundle data);

	public void onTestZKTRestarted(boolean isConnect, Bundle data);

	public void onGetTestZKTResult(boolean isConnect, Bundle data);
}
