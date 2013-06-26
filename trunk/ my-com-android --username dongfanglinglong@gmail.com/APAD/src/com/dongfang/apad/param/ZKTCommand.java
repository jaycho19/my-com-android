package com.dongfang.apad.param;

import com.dongfang.apad.util.ULog;
import com.dongfang.apad.util.Util;

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

}
