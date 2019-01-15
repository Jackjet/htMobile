package com.kwchina.work.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	public static String getJsonResult (String requestURL){
		String result = "";
		
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(requestURL);  
			HttpResponse httpResponse = client.execute(httpPost);
			
			if(httpResponse != null){  
				HttpEntity resEntity = httpResponse.getEntity();  
				if(resEntity != null){  
					result = EntityUtils.toString(resEntity,"utf-8");  
				}  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
		return result;
	}
}
