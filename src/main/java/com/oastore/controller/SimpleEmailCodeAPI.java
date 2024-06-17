package com.oastore.controller;

import com.oastore.utils.JwtUtil;
import io.github.swsk33.codepostcore.service.EmailVerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/code/simple")
public class SimpleEmailCodeAPI {

    /**
     * 自动装配邮件验证码服务
     */
    @Autowired
    private EmailVerifyCodeService emailVerifyCodeService;

    /**
     * 发送验证码
     *
     * @param account  接收用户邮件
     * @param userId 用户id
     * @return 消息
     */
    @PostMapping("/send")
    public String sendCode( String account,  String userId) {
        try {
            // 调用sendCodeAsync方法即可一键完成验证码生成发送操作
            // 参数分别是：邮箱对应的用户id、用户邮箱、验证码有效时长、验证码有效时长单位
            emailVerifyCodeService.sendCodeAsync(userId, account, 5, TimeUnit.MINUTES);
            return "已发送验证码！";
        } catch (Exception e) {
            return "发送验证码失败: " + e.getMessage();
        }
    }

    /**
     * 校验验证码
     *
     * @param userId    验证码对应的用户id
     * @param captcha 传入验证码用于校验（用户输入的验证码）
     * @return 消息
     */
    public String verifyCode( String userId,String captcha) {
        try {
            // 调用verifyCode方法即可一键完成验证码校验操作
            // 参数分别是：邮箱对应的用户id、用户传入的验证码（用于校验）
            // 校验成功返回true，并且验证码也会立即失效
            boolean result = emailVerifyCodeService.verifyCode(userId, captcha);
            return result ? "校验成功！" : "验证码错误或者不存在！";
        } catch (Exception e) {
            return "校验失败: " + e.getMessage();
        }
    }

}