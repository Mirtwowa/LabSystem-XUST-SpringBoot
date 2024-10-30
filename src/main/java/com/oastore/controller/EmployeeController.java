package com.oastore.controller;

import com.github.pagehelper.PageInfo;
import com.oastore.pojo.Employee;
import com.oastore.pojo.PageBean;
import com.oastore.pojo.Result;
import com.oastore.service.EmployeeService;
import com.oastore.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @PostMapping("/addEmployee")
    public Result addEmployee(@Validated String username, String email, String gender, String department, String birth){
        Employee employee =new Employee();
        employee.setUsername(username);
        employee.setEmail(email);
        employee.setGender(gender);
        employee.setDepartment(department);
        employee.setBirth(birth);
//        employeeService.insertUser(employee);
        return Result.success();
    }
    @PutMapping ("/UpdateEmployee")
    /*public Result UpdateEmployee(Employee employee){
        employeeService.change(employee);
        return Result.success();
    }*/
    public Result UpdateEmployee(Integer id,@Validated String username, String email, String gender, String department, String birth){
        Employee employee =new Employee();
        employee.setId(id);
        employee.setUsername(username);
        employee.setEmail(email);
        employee.setGender(gender);
        employee.setDepartment(department);
        employee.setBirth(birth);
//        employeeService.change(employee);
        return Result.success();
    }
    @DeleteMapping("/deleteEmployee")
    public Result deleteEmployee(Integer id) {
//        employeeService.delete(id);
        return Result.success();
    }
    @RequestMapping("/getAllEmployee")
    public Result  getAllEmployee() {
//        List<Employee> employees = employeeService.getAllEmployee();
        return Result.success();
    }
    @GetMapping("/searchEmployee")
    public  Result<List<Employee>> searchEmployee(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String username) {
//            PageBean<Employee> pb =  employeeService.search(pageNum,pageSize, id,username);
//        List<Employee> pb =  employeeService.search(pageNum,pageSize, id,username);
            return Result.success();
    }
}
