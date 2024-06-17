package com.oastore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VertifyCode {
  private  String account;
  private String captcha;
  private String password;
  private String checkPassword;
  private  String userId;
}
