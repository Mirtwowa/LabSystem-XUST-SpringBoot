package com.oastore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.oastore.pojo.Employee;
import com.oastore.pojo.PageBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper  extends BaseMapper<Employee> {
    @Insert("insert into `employee` (username , email, gender, department, birth)"+
    "values (#{username},#{email},#{gender},#{department},#{birth})")
    void insertUser(Employee employee);

    @Update("update `employee` set username=#{username},email=#{email},gender=#{gender},department=#{department},birth=#{birth}" +
             " where id =#{id}")
    void change(Employee employee);

    @Delete("delete from `employee` where id=#{id}")
    void delete(Integer employee);

    @Select("select * from `employee`")
    List<Employee> selectAll();
    @Select("select * from `employee`")
    List<Employee> getAllEmployee();
//
    List<Employee> findByName(String username);

    List<Employee> search(Integer id);
}
