package com.oastore;

import com.oastore.service.EmployeeService;
import com.oastore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OastoreApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    EmployeeService employeeService;
    @Test
    void contextLoads() {
//        userService.insertUser("123456","123456789");
        System.out.println(employeeService.getAllEmployee());
    }


}
