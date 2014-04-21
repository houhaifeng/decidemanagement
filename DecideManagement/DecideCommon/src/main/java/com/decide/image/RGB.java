package com.decide.image;

public class RGB {  
  private int alpha;
  private int r;
  private int g;
  private int b;
  
  public RGB(int alpha, int r, int g, int b) {
    this.alpha = alpha;
    this.r = r;
    this.g = g;
    this.b = b;
  }
  
  public RGB(){
    this.alpha = 0;
    this.r = 0;
    this.g = 0;
    this.b = 0;
  }
  public int getAlpha() {
    return alpha;
  }
  public void setAlpha(int alpha) {
    this.alpha = alpha;
  }
  public int getR() {
    return r;
  }
  public void setR(int r) {
    this.r = r;
  }
  public int getG() {
    return g;
  }
  public void setG(int g) {
    this.g = g;
  }
  public int getB() {
    return b;
  }
  public void setB(int b) {
    this.b = b;
  }

  public int average(){
    return (r+b+g)/3;
  }
  public void setAll(int value){
    alpha = 255;
    r = value;
    g = value;
    b = value;
  }
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return alpha + ":" + r+":"+g+":"+b;
  }
  
   
}
