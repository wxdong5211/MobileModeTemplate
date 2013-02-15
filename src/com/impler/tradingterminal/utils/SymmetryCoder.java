package com.impler.tradingterminal.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public enum SymmetryCoder {
	
	DES{
		protected Key toKey(byte[] key) throws Exception {   
	        DESKeySpec dks = new DESKeySpec(key);   
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(getType());   
	        SecretKey secretKey = keyFactory.generateSecret(dks);   
	        return secretKey;   
	    }
	},
	DESede,
	AES,
	Blowfish,
	RC2,
	RC4;
	
	protected String getType(){return toString();};
	
	protected Key toKey(byte[] key) throws Exception {   
        SecretKey secretKey = new SecretKeySpec(key, getType());   
        return secretKey;   
    }
	
	public String decrypt(String data, String key) throws Exception {   
        return new String(decrypt(hexStr2ByteArr(data),key,this));   
	}
	
	public byte[] decrypt(byte[] data, String key) throws Exception {   
        return decrypt(data,key,this);   
	}
	
	public String encrypt(String data, String key) throws Exception {   
        return byteArr2HexStr(encrypt(data.getBytes(),key, this));   
	}
	
	public byte[] encrypt(byte[] data, String key) throws Exception {   
        return encrypt(data,key,this);   
	}
	
	public byte[] initKey() throws Exception {   
        return initKey(null,this);   
    }
	
	public byte[] initKey(String seed) throws Exception {   
        return initKey(seed,this);   
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
	
	public static String decrypt(String data, String key, SymmetryCoder type) throws Exception {   
        return new String(decrypt(hexStr2ByteArr(data),key,type));   
	}  
	
	public static byte[] decrypt(byte[] data, String key, SymmetryCoder type) throws Exception {   
        Key k = type.toKey(initKey(key,type));   
        Cipher cipher = Cipher.getInstance(type.getType());   
        cipher.init(Cipher.DECRYPT_MODE, k);   
        return cipher.doFinal(data);   
	}  
	
	public static String encrypt(String data, String key, SymmetryCoder type) throws Exception {   
        return byteArr2HexStr(encrypt(data.getBytes(),key, type));   
	}
	
	public static byte[] encrypt(byte[] data, String key, SymmetryCoder type) throws Exception {   
        Key k = type.toKey(initKey(key,type));
        Cipher cipher = Cipher.getInstance(type.getType());   
        cipher.init(Cipher.ENCRYPT_MODE, k);   
        return cipher.doFinal(data);   
	}
	
	public static byte[] initKey(SymmetryCoder type) throws Exception {   
        return initKey(null,type);   
    }
	
	public static byte[] initKey(String seed, SymmetryCoder type) throws Exception {   
        SecureRandom secureRandom = null;   
  
        if (seed != null) {   
            secureRandom = new SecureRandom(seed.getBytes());   
        } else {   
            secureRandom = new SecureRandom();   
        }   
  
        KeyGenerator kg = KeyGenerator.getInstance(type.getType());   
        kg.init(secureRandom);   
  
        SecretKey secretKey = kg.generateKey();   
  
        return secretKey.getEncoded();   
    }
	
}

