package com.decide.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class DegitalImage {
  private BufferedImage image;
  private int width;
  private int height;
  private static final int THREDHOLD = 150;
  
  public int getHeight() {
    return height;
  }
  public void setHeight(int height) {
    this.height = height;
  }

  public int getWidth() {
    return width;
  }
  public void setWidth(int width) {
    this.width = width;
  }
  
  public DegitalImage(BufferedImage image) {
    this.image = image;
    width = image.getWidth();
    height = image.getHeight();
  }
  public DegitalImage(String imagePath) {
    try {
      this.image = ImageIO.read(new File(imagePath));
      width = image.getWidth();
      height = image.getHeight();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public DegitalImage(File file) {
    try {
      this.image = ImageIO.read(file);
      width = image.getWidth();
      height = image.getHeight();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  public void getRGBByDetail(int x, int y,int[] pixels){
    int type= image.getType();  
    if ( type ==BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )  
          image.getRaster().getDataElements(x, y, width, height, pixels );  
    else  
         image.getRGB( x, y, width, height, pixels, 0, width );  
  }
  public void getRGBPixels(int[] pixels){
     getRGBByDetail(0,0,pixels);
  }
  public void setRGB(int x, int y, int[] pixels ) {    
     int type = image.getType();    
     if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )    
         image.getRaster().setDataElements( x, y, width, height, pixels );    
     else    
         image.setRGB( x, y, width, height, pixels, 0, width );   

  }  
  public void setRGB(int[] pixels){
    setRGB(0,0,pixels);
  }
  
  public RGB getRGBValue(int rgb){
    int alpha = (rgb >> 24)& 0xff;
    int red = (rgb >> 16) &0xff;
    int green = (rgb >> 8) &0xff;
    int blue = rgb & 0xff;
    return new RGB(alpha,red,green,blue);
  }
  
  private int setRGBValue(RGB rgb){
    return (rgb.getAlpha() << 24) | (rgb.getR()<< 16) | (rgb.getG() << 8) | rgb.getB();
  }
  
  public void binearyImage(){
    int[] pixels = new int[image.getWidth()*image.getHeight()];
    getRGBPixels(pixels);
    for(int i = 0 ; i < pixels.length ; i++){
      RGB rgb = this.getRGBValue(pixels[i]);
      if(rgb.average() > THREDHOLD){
        rgb.setAll(255);
      }else{
        rgb.setAll(0);
      }
      pixels[i] = setRGBValue(rgb);
    }
    setRGB(pixels);
  }
  
  public void writeImage(String path){
    String[] strs = path.split("\\.");
    if(strs.length < 2){
      System.out.println("File path is illegal.");
      return;
    }
    try {
      ImageIO.write(image, strs[strs.length-1], new File(path));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public List<int[]> getDigitalArray(){
    List<int[]> result = new ArrayList<int[]>();
    int[] pixels = new int[image.getWidth()*image.getHeight()];
    getRGBPixels(pixels);
    List<Pair<Integer>> list = new ArrayList<Pair<Integer>>();
    int x1=0;
    int x2=0;
    int j = 0;
    for(int i = 0 ; i < width; i++){
      for(j = 0 ; j < height; j++){
        RGB rgb = this.getRGBValue(pixels[j*width + i]);
        if(rgb.average() == 0){
          break;
        }
      }
      if(x1 == 0 && (j < height)){
        x1=i;
        x2=0;
      }
      if(x1 != 0 && x2 == 0 && (j == height)){
        x2=i-1; 
        list.add(new Pair<Integer>(x1,x2));   
        x1=0;
      }
    }
    //for suning case
    for(int i = 0 ; i < list.size(); i++){
    	int pos1 = list.get(i).getFirst();
    	int pos2 = list.get(i).getSecond();
    	if(i+1 >= list.size()){
    		break;
    	}
    	int pos3 = list.get(i+1).getFirst();
    	int pos4 = list.get(i+1).getSecond();
    	if((pos2-pos1 == 4) && (pos3-pos2 == 2) && (pos4-pos3 == 1) ){
    		list.set(i, new Pair<Integer>(pos1,pos4));
    		list.remove(i+1);
    	}
    }
    for(Pair<Integer> pair : list){
       x1 = pair.getFirst();
       x2 = pair.getSecond();
       int[] array = new int[(x2-x1+1)*height];
       int index = 0;
       for(j = 0 ; j < height ; j++){
         for(int i = x1 ; i <= x2; i++){
           array[index++] = pixels[j*width+i]; 
         } 
       }
       result.add(array);
    }
    return result;
  }
  
}
