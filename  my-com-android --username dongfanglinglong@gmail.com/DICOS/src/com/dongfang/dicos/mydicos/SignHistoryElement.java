package com.dongfang.dicos.mydicos;

import org.json.JSONException;
import org.json.JSONObject;

import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.util.ULog;

import android.widget.TextView;

/**
 * ��ʷǩ���е�ǩ���б�Ԫ��
 * 
 * @author dongfang
 * */
public class SignHistoryElement {
	private static final String	tag	= "SignHistoryElement";

	public TextView				tvStoreName;
	public TextView				tvSignTime;

	private String				infoJSON;

	public void initElement(String json) {
		// {"name":"ԥ԰��","time":"7��1�� 18:38"}, //address ǩ���ĵ�ַ��time ǩ����ʱ��

		ULog.d(tag, "initElement " + json);

		infoJSON = json;

		try {
			JSONObject js = new JSONObject(json);

			String name = js.getString(Actions.ACTIONS_KEY_NAME);
			tvStoreName.setText(name.contains("�¿�ʿ") ? name : "�¿�ʿ" + name);
			tvSignTime.setText(js.getString(Actions.ACTIONS_KEY_TIME));

		} catch (JSONException e) {
			ULog.d(tag, e.toString());
		}
	}
}
