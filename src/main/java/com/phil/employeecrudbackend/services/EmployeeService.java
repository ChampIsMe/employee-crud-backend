package com.phil.employeecrudbackend.services;

import com.phil.employeecrudbackend.entities.Employee;
import com.phil.employeecrudbackend.entities.EmployeeExperience;
import com.phil.employeecrudbackend.entities.Skill;
import com.phil.employeecrudbackend.repositories.EmployeeExperienceRepo;
import com.phil.employeecrudbackend.repositories.EmployeeRepo;
import com.phil.employeecrudbackend.repositories.SkillsRepo;
import com.phil.employeecrudbackend.services.interfaces.IEmployeeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeService implements IEmployeeService {
  private final EmployeeRepo employeeRepo;
  private final SkillsRepo skillsRepo;
  private final EmployeeExperienceRepo experienceRepo;
  
  @Transactional
  @Override
  public Long create(Employee employee) {
    employee.getEmployeeExperiences().stream().filter(employeeExperience -> employeeExperience.getId() != null).forEach(employeeExperience -> skillsRepo.findById(employeeExperience.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "New skills cannot be passed with IDs. ")));
    employee.getEmployeeExperiences().forEach(employeeExperience -> {
      Skill skill = skillsRepo.save(employeeExperience.getSkill());
      employeeExperience.setSkill(skill);
    });
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
  
  @Transactional
  @Override
  public Employee update(Employee employee) {
    //Handle removed experiences
    Employee existingEmployee = employeeRepo.findById(employee.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No record with such ID"));
    List<EmployeeExperience> deletedExperiences = existingEmployee.getEmployeeExperiences().stream().filter(existingExperience -> employee.getEmployeeExperiences().stream().noneMatch(employeeExperience1 -> Objects.equals(employeeExperience1.getId(), existingExperience.getId()))).collect(Collectors.toList());
    experienceRepo.deleteAll(deletedExperiences);
    //Save experiences
    employee.getEmployeeExperiences().forEach(employeeExperience -> {
      Skill saved = skillsRepo.save(employeeExperience.getSkill());
      employeeExperience.setSkill(saved);
    });
    return employeeRepo.save(employee);
  }
  
  @Transactional
  @Override
  public void delete(Long id) {
    //This should be soft-delete implementation
    employeeRepo.deleteById(id);
  }
}
