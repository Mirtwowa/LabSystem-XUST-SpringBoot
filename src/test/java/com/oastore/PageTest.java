package com.oastore;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oastore.mapper.EmployeeMapper;
import com.oastore.mapper.UserMapper;
import com.oastore.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PageTest
{
    @Autowired
    EmployeeMapper employeeMapper;
    /*@Test
    public void testPage(){
        Page<User> page =new Page<>(1,5);
        userMapper.select
        page.getRecords().forEach(System.out::println);
    }*/
    @Test
    public void testPage(){
        String a = "123ae45";
        System.out.println();
//        page.getRecords().forEach(System.out::println);
    }

}
