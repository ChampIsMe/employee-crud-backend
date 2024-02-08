package com.phil.employeecrudbackend.services;

import com.phil.employeecrudbackend.entities.Employee;
import com.phil.employeecrudbackend.repositories.EmployeeRepo;
import com.phil.employeecrudbackend.services.interfaces.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class EmployeeService implements IEmployeeService {
  private final EmployeeRepo employeeRepo;
  
  @Override
  public Long create(Employee employee) {
    Employee result = employeeRepo.save(employee);
    return result.getId();
  }
  
  @Override
  public Employee findById(Long id) {
    return employeeRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No record with such ID"));
  }
  
  @Override
  public List<Employee> findAll() {
    //Assuming no pagination
    return employeeRepo.findAll();
  }
  
  @Override
  public Employee update(Employee employee) {
    return employeeRepo.findById(employee.getId()).map(employee1 -> employeeRepo.save(employee)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee record with such ID"));
  }
  
  @Override
  public void delete(Long id) {
    //This should be soft-delete implementation
    employeeRepo.deleteById(id);
  }
}
