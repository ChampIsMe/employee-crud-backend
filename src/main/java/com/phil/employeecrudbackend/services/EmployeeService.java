package com.phil.employeecrudbackend.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phil.employeecrudbackend.dtos.employees.EmployeeParams;
import com.phil.employeecrudbackend.entities.Employee;
import com.phil.employeecrudbackend.entities.EmployeeExperience;
import com.phil.employeecrudbackend.entities.Skill;
import com.phil.employeecrudbackend.repositories.EmployeeExperienceRepo;
import com.phil.employeecrudbackend.repositories.EmployeeRepo;
import com.phil.employeecrudbackend.repositories.SkillsRepo;
import com.phil.employeecrudbackend.repositories.queryutils.QueryService;
import com.phil.employeecrudbackend.services.interfaces.IEmployeeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeService implements IEmployeeService {
  private final EmployeeRepo employeeRepo;
  private final SkillsRepo skillsRepo;
  private final EmployeeExperienceRepo experienceRepo;
  private final QueryService queryService;
  private final ObjectMapper objectMapper;
  
  @Transactional
  @Override
  public Employee create(Employee employee) {
    /*
    This could be efficient with Mysql triggers for the benefit of DBAs however this approach is used to ensure empId is returned from the transaction
    If being random was not a requirement, it could be auto incremented from 1000 but less than 9999.
    * */
    int empID = employeeRepo.generateEmployeeId();
    employee.getEmployeeExperiences().stream().filter(employeeExperience -> employeeExperience.getId() != null).forEach(employeeExperience -> skillsRepo.findById(employeeExperience.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "New skills cannot be passed with IDs. ")));
    employee.getEmployeeExperiences().forEach(employeeExperience -> {
      //Insert only new skills
      Optional<Skill> skillInDB = skillsRepo.findByName(employeeExperience.getSkill().getName());
      if (skillInDB.isEmpty()) {
        Skill skill = skillsRepo.save(employeeExperience.getSkill());
        employeeExperience.setSkill(skill);
      } else {
        employeeExperience.setSkill(skillInDB.get());
      }
    });
    employee.setEmpId(empID);
    return employeeRepo.save(employee);
  }
  
  @Override
  public Employee findById(Long id) {
    return employeeRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No record with such ID"));
  }
  
  @Override
  public List<Employee> findByCustomParams(EmployeeParams employeeParams) {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    Map<String, Object> map = objectMapper.convertValue(employeeParams, new TypeReference<>() {
    });
    if (map.keySet().isEmpty()) {
      //Assuming no pagination
      return employeeRepo.findAll();
    }
    return queryService.employeesSearchByValue(map);
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
