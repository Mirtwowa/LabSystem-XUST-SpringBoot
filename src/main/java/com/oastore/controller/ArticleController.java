package com.oastore.controller;

import com.oastore.pojo.Result;
import com.oastore.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/Booklist")
    //用户登陆信息以JWT形式加密后，以请求头的形式返回，验证请求头是否相同
    public Result<String> list(@RequestHeader(name = "Authorization") String token, HttpServletResponse httpServletResponse){
        try {
            Map<String,Object> claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            //http响应状态码为401
            httpServletResponse.setStatus(401);
            return Result.error("未登录");
        }
       return Result.success("example");
    }
}
