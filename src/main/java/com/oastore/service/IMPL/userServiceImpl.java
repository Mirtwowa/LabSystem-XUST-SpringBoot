package com.oastore.service.IMPL;


import com.oastore.mapper.UserMapper;
import com.oastore.pojo.User;
import com.oastore.service.UserService;
import com.oastore.utils.EncryptUtils;
import com.oastore.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class userServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findByUserName(String account) {
       User user= userMapper.findByUserName(account);
        return user;
    }

    /**
     * @param account
     * @param username
     * @param password
     * @param stu_id
     */
    @Override
    public void insertUser(String account, String password, String name, String stu_id) {
        //加密
         String md5String = EncryptUtils.getMD5String(password);
        userMapper.insertUser(account,md5String,name,stu_id);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateUserIcon(String userIcon) {
        Map<String,Object> objectMap = ThreadLocalUtil.get();
        Integer id  = (Integer) objectMap.get("id");
        userMapper.updateUserIcon(userIcon,id);
    }

    @Override
    public void updatePwd(String newPwd,String account) {
        userMapper.updatePwd(EncryptUtils.getMD5String(newPwd),account);
    }
}
