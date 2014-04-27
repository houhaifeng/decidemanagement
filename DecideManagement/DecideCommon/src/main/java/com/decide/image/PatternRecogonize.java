package com.decide.image;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class PatternRecogonize {
  
  private static Logger log = Logger.getLogger(PatternRecogonize.class);
  private static final String TRAIN_DIR="trainsrc";
  private static Map<String,int[]> trainSet = new HashMap<String,int[]>(11);
  
  static{
    train(new File(TRAIN_DIR));
  }
  public static List<int[]> prepocess(File file){
    DegitalImage digitalImage = new DegitalImage(file);
    digitalImage.binearyImage();
    return digitalImage.getDigitalArray();
  }
  public static void train(File dir){
    if(!dir.isDirectory()){
      return;
    }
    File[] files = dir.listFiles();
    for(File file : files){
      if(file.isFile()){
        List<int[]> list = prepocess(file);
        String fileName = file.getName();
        String value = fileName.substring(0,fileName.lastIndexOf("."));
        char[] charArray = value.toCharArray();
        for(int i = 0 ; i < charArray.length ; i++){
        	trainSet.put(Character.toString(charArray[i]), list.get(i));
        }
      }
    }
  }
  
  public String recogoinze(File file){
    StringBuffer sb = new StringBuffer();
    List<int[]> list = prepocess(file);
    Set<Map.Entry<String, int[]>> set = trainSet.entrySet();
    for(int i = 0 ; i < list.size() ; i++){
      String value = "";
      for(Map.Entry<String, int[]> entry : set){
        if(compare(list.get(i),entry.getValue())){
          value = entry.getKey();
          break;
        }
      }
      sb.append(value);
    }
    return sb.toString();
  }
  

  public boolean compare(int[] src, int[] target){
    if(src.length != target.length){
      return false;
    }
    int index = 0;
    for(int i = 0 ; i < src.length ; i++){
      if(src[i] == target[i]){
        index++;
      }else{
        return false;
      }
    }
    return true;
  }
  public static void main(String[] strs){
    PatternRecogonize pr = new PatternRecogonize();
    Set<Map.Entry<String, int[]>> set = trainSet.entrySet();
    File dir = new File("c:/test");
    File[] files = dir.listFiles();
    for(File file : files){
      System.out.println(file.getName() + ":" + pr.recogoinze(file));
    }
  }
}
