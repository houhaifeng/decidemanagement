package com.decide.utils;

import java.util.List;
import java.util.Map;

public class CheckUtil {
	
	public static boolean checkMap(Map map){
		if(map != null && map.size() > 0){
			return true;
		}
		return false;
	}
	
	public static boolean checkList(List list){
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
}
