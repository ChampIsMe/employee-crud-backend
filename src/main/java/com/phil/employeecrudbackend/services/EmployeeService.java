package com.phil.employeecrudbackend.services;

import com.phil.employeecrudbackend.entities.Employee;
import com.phil.employeecrudbackend.services.interfaces.IEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService implements IEmployeeService {
  @Override
  public Long create(Employee employee) {
    return null;
  }
  
  @Override
  public Employee findById(Long id) {
    return null;
  }
  
  @Override
  public List<Employee> findAll() {
    return null;
  }
  
  @Override
  public Employee update(Employee employee) {
    return null;
  }
  
  @Override
  public void delete(Long id) {
  
  }
}
