package com.decide.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.decide.constants.JDConstants;


public class HTTPUtil {
	public static boolean checkUrl(String url){
		if(StringUtils.isEmpty(url)){
			return false;
		}else{
			if(!url.startsWith("http://")){
				return false;
			}
		}
		return filter(url);
	}
	
	public static boolean filter(String url){
		if(url.startsWith(JDConstants.JD_SUPERMARKET) || url.startsWith(JDConstants.JD_TUAN) || url.startsWith(JDConstants.JD_FASHION)){
			return false;
		}
		return true;
	}
	
	public static Map<String,String> getParams(String url){
		Map<String,String> result = new HashMap<String,String>();
		if(StringUtils.isEmpty(url)){
			return result;
		}
		String parameterStr = "";
		if(url.contains("?")){
			parameterStr = url.substring(url.indexOf("?") + 1);
		}else{
			parameterStr = url.substring(url.lastIndexOf("/") + 1);
		}
		
		String[] parameters = parameterStr.split("&");
		for(String parameter : parameters){
			String key = parameter.substring(0,parameter.indexOf("="));
			String value = parameter.substring(parameter.indexOf("=")+1);
			if(!result.containsKey(key)){
				result.put(key, value);
			}
		}
		return result;
	}
	
	public static String getParam(String url,String key){
		return getParams(url).get(key);
	}
	
	public static String setParam(String url,String key, String value){
		if(StringUtils.isEmpty(url) || StringUtils.isEmpty(key)){
			return null;
		}
		Map<String, String> map = getParams(url);
		map.put(key, value);
		System.out.println(key + ":" + value);
		return getUrl(url,map);
	}
	
	public static String getUrl(String url, Map<String,String> map){
		String result = null;
		System.out.println(url);
		if(StringUtils.isNotEmpty(url)){
			if(url.contains("?")){
				result = url.substring(0,url.indexOf("?"));
				if(CheckUtil.checkMap(map)){
					result = result + "?";
				}
				for(Entry<String,String> entry : map.entrySet()){
					result = result + entry.getKey() + "=" + entry.getValue();
					result = result + "&";
				}
			}else{
				result = url.substring(0,url.lastIndexOf("/"));
				if(CheckUtil.checkMap(map)){
					result = result + "/";
				}
				for(Entry<String,String> entry : map.entrySet()){
					result = result + entry.getKey() + "=" + entry.getValue();
					result = result + "&";
				}
			}
			
			result = result.substring(0,result.length()-1);
		}
		return result;
	}
}
