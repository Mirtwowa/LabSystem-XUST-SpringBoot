package com.oastore.controller;


import com.oastore.mapper.UserMapper;
import com.oastore.pojo.Result;
import com.oastore.pojo.User;
import com.oastore.service.UserService;
import com.oastore.utils.EncryptUtils;
import com.oastore.utils.JwtUtil;
import com.oastore.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {
    @Autowired
    SimpleEmailCodeAPI simpleEmailCodeAPI;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @PostMapping("/register")
    public Result register(String account,String captcha,String password,String checkPassword,String userId){
        String verify = simpleEmailCodeAPI.verifyCode(userId,captcha);
        User user = userService.findByUserName(account);
        if (user == null&& Objects.equals(verify, "校验成功！")) {
            //没有占用
            //注册
            userService.insertUser(account, password);
            User loginuser = userService.findByUserName(account);
            Map<String,Object> claims =new HashMap<>();
            claims.put("id",loginuser.getId());
            claims.put("username",loginuser.getUsername());
            claims.put("password",loginuser.getPassword());
            //生成请求头
            String token = JwtUtil.genToken(claims);
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.DAYS);
            return Result.success(token);
        } else if(user == null&& Objects.equals(verify, "验证码错误或者不存在！")){
            //占用
            return Result.error("验证码错误或者不存在！");
        }else {
            return Result.error("用户名已被占用");
        }
    }

    @RequestMapping("/userLogin")
    public Result<String> loginUser(@Pattern(regexp = "^\\s{1,100}")String account, String password){
       User loginuser = userService.findByUserName(account);
        System.out.println(account+password);
       if (loginuser==null){
           return Result.error("用户名错误");
       }
       if (EncryptUtils.getMD5String(password).equals(loginuser.getPassword())){
           Map<String,Object> claims =new HashMap<>();
           claims.put("id",loginuser.getId());
           claims.put("username",loginuser.getUsername());
           claims.put("password",loginuser.getPassword());
           //生成请求头
           String token = JwtUtil.genToken(claims);
           ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
           operations.set(token,token,1, TimeUnit.DAYS);
           return Result.success(token);
       }

       return   Result.error("密码错误");
    }
    @GetMapping("/userinfo")
    public Result<User> userInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Integer id = (Integer) map.get("ID");
        System.out.println(map);
        User user = userService.findByUserName(username);
        return Result.success(user);
    }
    @PostMapping("/resetPwd")
    public Result ResetPwd(String account,String captcha,String newPassword,String userId){
        User user = userService.findByUserName(account);
        String verify = simpleEmailCodeAPI.verifyCode(userId,captcha);
        if (user != null&& Objects.equals(verify, "校验成功！")) {
            userService.updatePwd(newPassword,account);
            Map<String,Object> claims =new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            claims.put("password",user.getPassword());
            //生成请求头
            String token = JwtUtil.genToken(claims);
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.DAYS);
            return Result.success(token);
        } else if(user != null&& Objects.equals(verify, "验证码错误或者不存在！")){
            //占用
            return Result.error("验证码错误或者不存在！");
        }else {
            return Result.error("用户名不存在");
        }
    }
}
