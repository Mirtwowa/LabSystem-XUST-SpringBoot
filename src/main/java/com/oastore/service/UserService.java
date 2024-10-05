package com.oastore.service;


import com.oastore.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByUserName(String account);

    void insertUser(String username, String password);

    void update(User user);

    void updateUserIcon(String userIcon);

    void updatePwd(String newPwd,String account);
}
