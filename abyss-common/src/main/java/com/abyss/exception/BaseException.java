package com.abyss.exception;

//业务异常
public class BaseException extends RuntimeException {

  public BaseException(){}

  //调用父类信息方法获取异常信息
  public BaseException(String message) { super(message);}
}
