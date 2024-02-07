package com.phil.employeecrudbackend.services.interfaces;

import com.phil.employeecrudbackend.entities.Employee;

import java.util.List;

public interface IEmployeeService {
  
  Long create(Employee employee);
  
  Employee findById(Long id);
  
  List<Employee> findAll();
  
  Employee update(Employee employee);
  
  void delete(Long id);
  
}
