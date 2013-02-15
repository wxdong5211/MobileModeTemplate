package com.impler.tradingterminal.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class SymmetryCoder2 {
	
	private static final String VIPARA = "0102030405060708";  
	
	public static String encrypt(String data, String key) throws Exception {   
        return byteArr2HexStr(encrypt(data.getBytes(),key));   
	}
	
	public static byte[] encrypt(byte[] data, String key)  
	        throws Exception {  
	    SecretKeySpec skey = new SecretKeySpec(initKey(key), "AES");
	    IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
	    cipher.init(Cipher.ENCRYPT_MODE, skey, zeroIv);  
	    return cipher.doFinal(data);
	}
	
	public static String decrypt(String data, String key) throws Exception {   
        return new String(decrypt(hexStr2ByteArr(data),key));   
	}
	
	public static byte[] decrypt(byte[] data, String key)  
	        throws Exception {  
		SecretKeySpec skey = new SecretKeySpec(initKey(key), "AES"); 
		IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
	    cipher.init(Cipher.DECRYPT_MODE, skey, zeroIv);  
	    return cipher.doFinal(data); 
	} 
	
	private static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
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

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
	private static byte[] initKey(String key){
		if(key==null)key="";
		return KeyedMD5.getMd5Utf8(key, "").substring(0, 16).getBytes();
	}

}
