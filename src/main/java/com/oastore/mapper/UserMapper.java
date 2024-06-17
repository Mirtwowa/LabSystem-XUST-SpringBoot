package com.oastore.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oastore.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from `user`")
    List<User> selectAll();

    @Insert("insert into user(username , password,create_time,update_time)"+
            "values (#{username},#{password},now(),now())")
    void insertUser(String username,String password);

    @Select("select * from user where username=#{account}")
    User findByUserName(String account);

    @Update("update user set name=#{name},update_time=#{updateTime} where username =#{username}")
    void update(User user);

    @Update("update user set user_pic=#{UserIcon},update_time=now() where id =#{id}")
    void updateUserIcon(String UserIcon, Integer id);

    @Update("update user set password=#{md5String},update_time=now() where id =#{id}")
    void updatePwd(String md5String, Integer id);
}
