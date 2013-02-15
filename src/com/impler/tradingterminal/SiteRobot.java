package com.impler.tradingterminal;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import com.impler.tradingterminal.utils.ClientUtil;
import com.impler.tradingterminal.utils.KeyedMD5;
import com.impler.tradingterminal.utils.SymmetryCoder2;

import android.util.Log;

public  class SiteRobot {
    
	public static String getUser(String url, String user, String pass){
		try {
			long now = System.currentTimeMillis();
			String ppass = SymmetryCoder2.encrypt(pass, user+now);
			String md5 = KeyedMD5.getMd5Utf8(user+pass+now, "");
			String  html = toHTML(creep(new HttpGet(url+"?user="+user+"&pass="+ppass+"&date="+now+"&sign="+md5)));
			if(html==null||html.length()==0)
				return null;
			int ulen = user.length();
			if(html.length()<ulen+45||!html.startsWith(user))
				return "";
			int len = html.length()-32;
			String date = html.substring(ulen,ulen+13);
			String data = html.substring(ulen+13,len);
			String sign = html.substring(len);
			String key = SymmetryCoder2.decrypt(data, user+date);
			if(!KeyedMD5.getMd5Utf8(user+key+date, "").equals(sign))
				return "";
			return key;
		} catch (Exception e) {
			Log.e("SiteRobot", "err", e);
		}
        return null;
	}
	
	private static HttpResponse creep(HttpRequestBase req) throws ClientProtocolException, IOException{
	    HttpClient client = ClientUtil.getHttpClient();
		HttpResponse resp = null;
		resp = client.execute(req);
		return resp;
	}
	
	private static String toHTML(HttpResponse resp) throws ParseException, IOException{
	    if(resp==null)return "";
        HttpEntity entity = resp.getEntity();
        if(entity==null)return "";
        String result = EntityUtils.toString(entity);
        entity.consumeContent();
        return result;
	}
	
}
