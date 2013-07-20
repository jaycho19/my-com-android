package com.dongfang.apad.param;

/** @author dongfang */
public class UserCommand {

	public static byte[] check(byte[] array) {
		for (int i = 0; i < 7; i++)
			array[7] ^= array[i];
		return array;
	}

	/***
	 * 返回数据校验
	 * 
	 * @param input
	 * @return
	 */
	public static boolean checkReadInput(byte[] input, int blkNum) {
		if (0x40 == input[0] && (byte) 0x96 == input[1] && input[4] == 0x00 && input[6] == 0x08 && input[7] == (byte) 0xDE) {
			return true;
		}
		if (0x40 != input[0] || (byte) 0x98 != input[1] || 0x01 != input[3] || input[4] != 0x00) {
			return false;
		}

		// if (input[7] != (byte) (input[0] | input[1] |input[2] | input[3] | input[4]| input[5]| input[6])) {
		// return false;
		// }

		if (verticalParity(input) && input[6] != blkNum * 4) {
			return false;
		}

		return true;
	}

	/** 检测垂直校验 */
	public static boolean verticalParity(byte[] input) {
		if (input.length < 8)
			return false;

		byte Check = 0;
		for (int i = 0; i < 7; i++)
			Check ^= input[i];
		return input[7] == Check;
	}

	public static final byte[]	RCARDID		= { 0x40, (byte) 0x96, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xD6 };

	public static final byte[]	RUSER_ws	= { 0x40, (byte) 0x98, 0x00, 0x01, 0x00, 0x00, 0x02, (byte) 0xDB, 0x00, 0x01, 0x00, 0x00 };
	public static final byte[]	RUSER_0		= { 0x40, (byte) 0x98, 0x00, 0x01, 0x00, 0x00, 0x02, (byte) 0xDB, 0x00, 0x0c, 0x00, 0x00 };
	public static final byte[]	RUSER_1		= { 0x40, (byte) 0x98, 0x00, 0x01, 0x00, 0x00, 0x02, (byte) 0xDB, 0x0c, 0x0c, 0x00, 0x00 };
	public static final byte[]	RUSER_2		= { 0x40, (byte) 0x98, 0x00, 0x01, 0x00, 0x00, 0x02, (byte) 0xDB, 0x18, 0x0c, 0x00, 0x00 };
	public static final byte[]	RUSER_3		= { 0x40, (byte) 0x98, 0x00, 0x01, 0x00, 0x00, 0x02, (byte) 0xDB, 0x24, 0x0c, 0x00, 0x00 };
	public static final byte[]	RUSER_4		= { 0x40, (byte) 0x98, 0x00, 0x01, 0x00, 0x00, 0x02, (byte) 0xDB, 0x30, 0x0c, 0x00, 0x00 };
	public static final byte[]	RUSER_5		= { 0x40, (byte) 0x98, 0x00, 0x01, 0x00, 0x00, 0x02, (byte) 0xDB, 0x3c, 0x04, 0x00, 0x00 };

	// public static byte[][] Rlist_INFO = { RUSER_1 };
	public static byte[][]		Rlist_INFO	= { RUSER_0
		, RUSER_1
//		, RUSER_2, RUSER_3, RUSER_4, RUSER_5 
		};

}
