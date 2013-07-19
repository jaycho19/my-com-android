package com.dongfang.apad.param;

public class ZKTCommand {

	public static final byte	CMD_0		= 0x00;

	public static final byte[]	RCARDID		= { 0x5B, 0x03, 0x01, (byte) 0xA1, 0x5D };
	public static final byte[]	RSTART		= { 0x5B, 0x04, 0x30, 0x00, 0x71, 0x5D };
	public static final byte[]	RRESTART	= { 0x5B, 0x04, 0x30, 0x01, 0x70, 0x5D };

	// static {
	// RSTART[RSTART[2]] = getCheck(RSTART);
	// }

	/***
	 * 返回数据校验
	 * 
	 * @param input
	 * @return
	 */
	public static boolean checkReadInput(byte[] input) {
		try {
			if (input.length < 5 && input[0] != 0x5b && input[1] < 0x03)
				return false;

			if (input[1 + input[1]] != 0x5d)
				return false;

			// ULog.d("ZKTCommand", (input[input[1]] == getCheck(input)) + " =>" + Util.bytesToHexString(input));
			return input[input[1]] == getCheck(input);
		} catch (Throwable e) {
			return false;
		}
	}

	public static byte getCheck(byte[] input) {
		byte v = 0x00;
		for (int i = 0; i < input[1]; i++) {
			v += input[i];
		}
		return (byte) (0x01 + (0xFF - v & 0xFF));
	}

	/** 显示数据错误信息 */
	public static String getErrorInfo(byte[] input) {
		String result = " OK ";
		if (input.length > 4 && input[1] == 0x04) {
			byte rtncode = (byte) input[2];
			switch (rtncode) {
			case (byte) 0xF1:
				result = "FAIL_UNKNOWN";
				break;
			case (byte) 0xF2:
				result = "FAIL_INVALIDCMD";
				break;
			case (byte) 0xF3:
				result = "FAIL_DATASIZE";
				break;
			case (byte) 0xF4:
				result = "FAIL_INVALIDVALUE";
				break;
			case (byte) 0xF8:
				result = "FAIL_NODATA";
				break;
			case (byte) 0xFE:
				result = "FAIL_ TIMEOUT";
				break;
			case (byte) 0xFF:
				result = "FAIL_OPERATION";
				break;
			}
		}
		return result;
	}

}
