package com.dongfang.dicos.sina;

/*
 * Copyright 2011 Sina.
 *
 * Licensed under the Apache License and Weibo License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.open.weibo.com
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * Encapsulation a Weibo error, when weibo request can not be implemented successful.
 *
 * @author  ZhangJie (zhangjie2@staff.sina.com.cn)
 */
public class WeiboException extends Exception {

	private static final long	serialVersionUID	= 475022994858770424L;
	
	
	/*304 Not Modified: û�����ݷ���.
	400 Bad Request: �������ݲ��Ϸ������߳�������Ƶ������. ��ϸ�Ĵ���������£�
	40028:�ڲ��ӿڴ���(�������ϸ�Ĵ�����Ϣ���������Ϊ��ϸ�Ĵ�����ʾ)
	40033:source_user����target_user�û�������
	40031:���õ�΢��������
	40036:���õ�΢�����ǵ�ǰ�û�������΢��
	40034:����ת���Լ���΢��
	40038:���Ϸ���΢��
	40037:���Ϸ�������
	40015:�������۲��ǵ�ǰ��¼�û�����������
	40017:���ܸ��������˿���˷�˽��
	40019:���Ϸ���˽��
	40021:�����������˽��
	40022:source����(appkey)ȱʧ
	40007:��ʽ��֧�֣�����֧��XML��JSON��ʽ
	40009:ͼƬ������ȷ��ʹ��multipart�ϴ���ͼƬ
	40011:˽�ŷ�����������
	40012:����Ϊ��
	40016:΢��idΪ��
	40018:ids����Ϊ��
	40020:����IDΪ��
	40023:�û�������
	40024:ids���࣬��ο�API�ĵ�
	40025:���ܷ�����ͬ��΢��
	40026:�봫����ȷ��Ŀ���û�uid����screen name
	40045:��֧�ֵ�ͼƬ����,֧�ֵ�ͼƬ������JPG,GIF,PNG
	40008:ͼƬ��С�����ϴ���ͼƬ��С����Ϊ5M
	40001:����������ο�API�ĵ�
	40002:���Ƕ��������ߣ�û�в���Ȩ��
	40010:˽�Ų�����
	40013:΢��̫������ȷ�ϲ�����140���ַ�
	40039:������Ϣ�������
	40040:IP���ƣ������������Դ
	40041:uid����Ϊ��
	40042:token����Ϊ��
	40043:domain��������
	40044:appkey����ȱʧ
	40029:verifier����
	40027:��ǩ����Ϊ��
	40032:�б���̫������ȷ��������ı�������10���ַ�
	40030:�б�����̫������ȷ��������ı�������70���ַ�
	40035:�б�����
	40053:Ȩ�޲��㣬ֻ�д����������Ȩ��
	40054:����������ο�API�ĵ�
	40059: ����ʧ�ܣ���¼�Ѵ���
	40060�����ݿ��������ϵϵͳ����Ա
	40061���б�����ͻ
	40062��id�б�̫����
	40063��urls�ǿյ�
	40064��urls̫����
	40065��ip�ǿ�ֵ
	40066��url�ǿ�ֵ
	40067��trend_name�ǿ�ֵ
	40068��trend_id�ǿ�ֵ
	40069��userid�ǿ�ֵ
	40070��������Ӧ�÷���api�ӿ�Ȩ��������
	40071����ϵ����user_id���������ע���û�
	40072����Ȩ��ϵ�Ѿ���ɾ��
	40073��Ŀǰ��֧��˽�з���
	40074������listʧ��
	40075����Ҫϵͳ����Ա��Ȩ��
	40076�����зǷ���
	40084������ʧ�ܣ���ҪȨ��
	40082����Ч����!
	40083����Ч״̬��
	40084��Ŀǰֻ֧��˽�з���
	401 Not Authorized: û�н��������֤.
	40101 version_rejected Oauth�汾�Ŵ���
	40102 parameter_absent Oauthȱ�ٱ�Ҫ�Ĳ���
	40103 parameter_rejected Oauth�������ܾ�
	40104 timestamp_refused Oauthʱ�������ȷ
	40105 nonce_used Oauth nonce�����Ѿ���ʹ��
	40106 signature_method_rejected Oauthǩ���㷨��֧��
	40107 signature_invalid Oauthǩ��ֵ���Ϸ�
	40108 consumer_key_unknown! Oauth consumer_key������
	40109 consumer_key_refused! Oauth consumer_key���Ϸ�
	40110 token_used! Oauth Token�Ѿ���ʹ��
	40111 Oauth Error: token_expired! Oauth Token�Ѿ�����
	40112 token_revoked! Oauth Token���Ϸ�
	40113 token_rejected! Oauth Token���Ϸ�
	40114 verifier_fail! Oauth Pin����֤ʧ��
	402 Not Start mblog: û�п�ͨ΢��
	403 Forbidden: û��Ȩ�޷��ʶ�Ӧ����Դ.
	40301 too many lists, see doc for more info ��ӵ���б�����
	40302 auth faild ��֤ʧ��
	40303 already followed �Ѿ���ע���û�
	40304 Social graph updates out of rate limit ����΢����������
	40305 update comment out of rate �������۳�������
	40306 Username and pwd auth out of rate limit �û���������֤������������
	40307 HTTP METHOD is not suported for this request �����HTTP METHOD��֧��
	40308 Update weibo out of rate limit ����΢����������
	40309 password error ���벻��ȷ
	40314 permission denied! Need a high level appkey ����Դ��Ҫappkeyӵ�и��߼�����Ȩ
	404 Not Found: �������Դ������.
	500 Internal Server Error: �������ڲ�����.
	502 Bad Gateway: ΢���ӿ�API�رջ��������� .
	503 Service Unavailable: �������Դ������.*/
	
	private int					statusCode			= -1;

	public WeiboException(String msg) {
		super(msg);
	}

	public WeiboException(Exception cause) {
		super(cause);
	}

	public WeiboException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}

	public WeiboException(String msg, Exception cause) {
		super(msg, cause);
	}

	public WeiboException(String msg, Exception cause, int statusCode) {
		super(msg, cause);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public WeiboException() {
		super();
	}

	public WeiboException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public WeiboException(Throwable throwable) {
		super(throwable);
	}

	public WeiboException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
