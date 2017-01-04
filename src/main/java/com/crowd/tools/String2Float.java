package com.crowd.tools;

public class String2Float {
	public static float getFloatNum(String StrNum){
	  float reNumber = 0;
	  boolean flag = false;//判断正负标志位
	  //若以符号开头，去掉符号，记住正负
	  if(StrNum.startsWith("+")||StrNum.startsWith("-")){
	   if(StrNum.startsWith("-")){
	    flag = true;
	   } 
	   StrNum = StrNum.substring(1);
	  }
	  //分别获取整数部分和小数部分
	  String strInt = StrNum.substring(0, StrNum.indexOf("."));
	  String strDot = StrNum.substring(StrNum.indexOf(".")+1);
	  //System.out.println(strInt+"  "+strDot);
	  //获取整数值
	  for(int i=0;i<strInt.length();i++){
	   int tempNum = strInt.charAt(i)-'0';
	   reNumber = (float) (reNumber + tempNum*Math.pow(10,(strInt.length()-i-1)));
	  }
	  //获取小数值
	  if(null!=strDot){
	   for(int j=0;j<strDot.length();j++){
	    int tempNum = strDot.charAt(j)-'0';
	    //System.out.println(tempNum);
	    reNumber = (float) (reNumber + tempNum/Math.pow(10,(j+1)));
	   }
	  }
	  //System.out.println(reNumber);
	  //保留有效位数
	  reNumber = (float) ((float)Math.round(reNumber * Math.pow(10, strDot.length())) / Math.pow(10, strDot.length()));
	  
	  //System.out.println(reNumber);
	  //正负号
	  if(flag){
	   reNumber = -reNumber;
	  }
	  
	  return reNumber;
	 }
	
	 public static void main(String[] args) {
	  // TODO Auto-generated method stub
	  System.out.println("-0.332: " + getFloatNum("-0.332"));
	  System.out.println("0.332: " + getFloatNum("0.332"));
	  System.out.println("+0.332: " + getFloatNum("+0.332"));
	  System.out.println("434.332: " + getFloatNum("434.332"));
	  
	 }
}

