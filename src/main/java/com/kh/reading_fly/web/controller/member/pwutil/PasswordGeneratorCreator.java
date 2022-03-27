package com.kh.reading_fly.web.controller.member.pwutil;

public class PasswordGeneratorCreator {

  private static final PasswordGenerator passwordGenerator
      = new PasswordGenerator.PasswordGeneratorBuilder()//
      .useDigits(true)           // 숫자포함
      .useLower(true)              // 소문자포함
      .useUpper(true)             // 대문자포함
      .usePunctuation(true)   // 특수문자포함
      .build();
  private  PasswordGeneratorCreator() {
  }

  public static String generator(int passwordLength) {
    return passwordGenerator.generate(passwordLength);
  }
}
