package com.oastore.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oastore.mapper.EmployeeMapper;
import com.oastore.pojo.Employee;
import com.oastore.pojo.PageBean;
import com.oastore.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
   @Autowired
   EmployeeMapper employeeMapper;
   public void insertUser(Employee employee){
      employeeMapper.insertUser(employee);
   };
   public void change(Employee employee){
      employeeMapper.change(employee);
   }

   public void delete(Integer employee) {
      employeeMapper.delete(employee);
   }

   public void BatchDelete(List<Integer> list) {
      for (Integer integer : list) {
         employeeMapper.delete(integer);
      }

   }

   public Employee selectAll() {
      employeeMapper.selectAll();
      return null;
   }

    public List<Employee> getAllEmployee() {
      List<Employee> employees= employeeMapper.getAllEmployee();
      return employees;
    }

  /*  public PageBean<Employee> search(Integer pageNum, Integer pageSize, Integer id,String username) {
       PageBean<Employee> pb = new PageBean<>();
       PageHelper.startPage(pageNum, pageSize);
       Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Employee> as = employeeMapper.findByName(username);
        System.out.println(as);
        Page<Employee> p =(Page<Employee>) as;
         pb.setTotal(p.getTotal());
         pb.setItems(p.getResult());
         return pb;
    }*/
 public List<Employee> search(Integer pageNum, Integer pageSize, Integer id,String username) {
//       List<Employee> pb = new PageBean<Employee>();
//       PageHelper.startPage(pageNum, pageSize);
//       Map<String, Object> map = ThreadLocalUtil.get();
//        Integer userId = (Integer) map.get("id");
        List<Employee> as = employeeMapper.findByName(username);
//        Page<Employee> p =(Page<Employee>) as;
        /* pb.setTotal(p.getTotal());
         pb.setItems(p.getResult());*/
       return as;
    }

}
