package com.oastore.controller;

import com.oastore.mapper.EmployeeMapper;
import com.oastore.pojo.Result;
import com.oastore.pojo.User;
import com.oastore.service.EmployeeService;
import com.oastore.service.UserService;
import com.oastore.utils.EncryptUtils;
import com.oastore.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController{
    @Autowired
    EmployeeService employeeService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    UserService userService;

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success("成功");
    }

    @PatchMapping("/updateUserIcon")
    public Result updateUserIcon(@RequestParam @URL String UserIcon){
        userService.updateUserIcon(UserIcon);
        return Result.success();
    }
    @PatchMapping("/updatePwd")
    public Result updatePassword(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }
        Map<String,Object> map =  ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        if (!user.getPassword().equals(EncryptUtils.getMD5String(oldPwd))){
            return Result.error("原密码不正确");
        }
        if (!newPwd.equals(rePwd)){
            return Result.error("密码不一致");
        }
//        userService.updatePwd(newPwd);
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
