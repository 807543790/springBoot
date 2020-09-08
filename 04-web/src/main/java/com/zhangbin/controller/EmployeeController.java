package com.zhangbin.controller;

import com.zhangbin.dao.DepartmentDao;
import com.zhangbin.dao.EmployeeDao;
import com.zhangbin.pojo.Department;
import com.zhangbin.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

/**
 * 认认真真敲代码，开开心心每一天
 *
 * @Date 2020/9/6-16:46
 */
//员工列表
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;

    //员工列表
    @RequestMapping("/emps")
    public String EmpList(Model model){
        Collection<Employee> Employees = employeeDao.getAll();
        model.addAttribute("EmpList",Employees);
        return "list";
    }
    //添加员工页面
    @GetMapping("/emp")
    public String toAddEmp(Model model){
        Collection<Department> department = departmentDao.getDepartment();
        model.addAttribute("department",department);
        return "toAdd";
    }

    //添加员工表单
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        System.out.println("添加成功"+employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //添加员工表单
    @RequestMapping("/update/{id}")
    public String updateEmp(@PathVariable("id") Integer id, Model model){
//        根据id查询出来数据
        Employee emp = employeeDao.getEmployee(id);
        System.out.println(id);
        System.out.println(emp.getGender());
        System.out.println(emp);
        model.addAttribute("emp",emp);
        //查询所有部门信息
        Collection<Employee> department = employeeDao.getAll();
        model.addAttribute("department",department);
        return "update";
    }
}
