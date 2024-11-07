package com.oastore.service;


import com.oastore.pojo.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface UserService {
    User findByUserName(String account);

    void insertUser(String account, String password, String name, String stu_id);

    void update(User user);

    void updateUserIcon(String userIcon);

    void updatePwd(String newPwd,String account);

    CompletableFuture<String> insertRedis(User loginuser);
}
