package com.decide.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;

public class HTTPHelper {
	private static final String proxyHost="61.50.217.230";
	private static final int proxyPort=1080;
	public static HttpClient getHttpClient() {
		 DefaultHttpClient httpClient = new DefaultHttpClient();
		 HttpHost proxy = new HttpHost(proxyHost,proxyPort);
		 httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		 return httpClient;
	}
	
	public static String get(HttpClient client, String url) throws ClientProtocolException, IOException{
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		StringBuffer sb = new StringBuffer();
		if(entity != null){
			InputStream is = entity.getContent();
			BufferedReader br = null;
			String line = null;
			try{
				br = new BufferedReader(new InputStreamReader(is,"utf-8"));
				while((line = br.readLine()) != null){
					sb.append(line);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(is != null){
					is.close();
				}
				if(br != null){
					br.close();
				}
			}
		}
		return sb.toString();
	}
}
