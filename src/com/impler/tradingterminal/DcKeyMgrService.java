package com.impler.tradingterminal;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.impler.tradingterminal.utils.KeyedMD5;
import com.impler.tradingterminal.utils.SymmetryCoder;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DcKeyMgrService {

	private static final String NAME = "impler.dck";
    private Context context;
    
    public DcKeyMgrService(Context context) {
		this.context = context;
		
	}
    
    public void save(String key) throws Exception {
    	String dcid = getDcid();
    	long now = System.currentTimeMillis();
    	String data = SymmetryCoder.DESede.encrypt(key, dcid+now);
    	String sign = KeyedMD5.getMd5Utf8(dcid+key+now,"");
    	data = now+data+sign;
    	FileOutputStream fos = context.openFileOutput(NAME, Context.MODE_PRIVATE);  
        fos.write(data.getBytes());
        fos.close();
    }
    
    public String read() throws Exception {
    	FileInputStream fis = null;
    	try{
    		fis = context.openFileInput(NAME);  
    	}catch(FileNotFoundException e){
    		return null;
    	}
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        byte[] buf = new byte[1024];  
        int len = 0;  
        while ((len = fis.read(buf)) != -1) {  
            baos.write(buf, 0, len);  
        }  
        fis.close();  
        baos.close();  
        String data = baos.toString();
        if(data.length()<45)
        	return null;
        String date = data.substring(0,13);
        int dlen = data.length()-32;
        String sign = data.substring(dlen);
        data = data.substring(13,dlen);
        String dcid = getDcid();
        data = SymmetryCoder.DESede.decrypt(data, dcid+date);
        if(!KeyedMD5.getMd5Utf8(dcid+data+date,"").equals(sign))
        	return null;
        return data;
    }
    
    private String getDcid(){
    	TelephonyManager phoneMgr=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return phoneMgr.getDeviceId();
    }
}
