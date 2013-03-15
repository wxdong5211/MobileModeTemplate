package com.impler.tradingterminal.utils;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class SymmetryCoder3 {

	private static final byte[] VIPARA = { 0x38, 0x37, 0x36, 0x35, 0x34, 0x33,
			0x32, 0x31, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31 };

	public static String encrypt(String data, String key) throws Exception {
		return byteArr2HexStr(encrypt(data.getBytes(), key));
	}

	public static byte[] encrypt(byte[] data, String key) throws Exception {
		return process(data, key, true);
	}

	public static String decrypt(String data, String key) throws Exception {
		return new String(decrypt(hexStr2ByteArr(data), key));
	}

	public static byte[] decrypt(byte[] data, String key) throws Exception {
		return process(data, key, false);
	}
	
	private static byte[] process(byte[] data, String key, boolean encrypt) throws Exception {
		BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));
		engine.init(encrypt, new ParametersWithIV(new KeyParameter(initKey(key)),
				VIPARA));
		byte[] enc = new byte[engine.getOutputSize(data.length)];
		int size1 = engine.processBytes(data, 0, data.length, enc, 0);
		System.out.println(size1+":"+enc.length);
		int size2 = engine.doFinal(enc, size1);
		System.out.println(size2+":"+enc.length);
		byte[] result = new byte[size1+size2];
		System.arraycopy(enc, 0, result, 0, result.length);
		return result;
	}
	
	

	private static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	private static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	private static byte[] initKey(String key) {
		if (key == null)
			key = "";
		return KeyedMD5.getMd5Utf8(key, "").substring(0, 16).getBytes();
	}

}
