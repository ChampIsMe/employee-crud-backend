package com.phil.employeecrudbackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phil.employeecrudbackend.dtos.employees.EmployeeParams;
import com.phil.employeecrudbackend.entities.Employee;
import com.phil.employeecrudbackend.entities.EmployeeExperience;
import com.phil.employeecrudbackend.entities.Skill;
import com.phil.employeecrudbackend.repositories.EmployeeExperienceRepo;
import com.phil.employeecrudbackend.repositories.EmployeeRepo;
import com.phil.employeecrudbackend.repositories.SkillsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
  
  @Mock
  EmployeeRepo employeeRepo;
  @Mock
  SkillsRepo skillsRepo;
  @Mock
  EmployeeExperienceRepo experienceRepo;
  @Mock
  private EmployeeService employeeService;
  @Mock
  private ObjectMapper objectMapper;
  
  private Employee employee;
  private EmployeeParams params;
  
  @BeforeEach
  public void setUp() throws ParseException {
    Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
    employee = Employee.builder()
        .dob(new SimpleDateFormat("yyyy-MM-dd", locale).parse("1993-11-30"))
        .email("po2@example.com")
        .city("Cape Town")
        .id(12L)
        .address("Long Street")
        .contactNumber("+27715153701")
        .country("South Africa")
        .postalCode("00100")
        .EmployeeExperiences(List.of(
            EmployeeExperience.builder()
                .yrs("5")
                .skill(Skill.builder()
                    .id(1L)
                    .name("JAva")
                    .build())
                .build()
        ))
        .firstName("Philip")
        .lastName("Pkinyi")
        .build();
    params = EmployeeParams.builder().email(employee.getEmail()).build();
  }
  
  // JUnit test for saveEmployee method
  @DisplayName("JUnit test for saveEmployee method")
  @Test
  public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
    // given - precondition or setup
    given(employeeService.findByCustomParams(params))
        .willReturn(List.of(employee));
    given(employeeService.create(employee))
        .willReturn(employee);
    
    Employee savedEmployee = employeeService.create(employee);
    assertThat(savedEmployee).isNotNull();
    List<Employee> employees = employeeService.findByCustomParams(params);
    assertThat(employees).isEqualTo(List.of(savedEmployee));
  }
  
  // JUnit test for saveEmployee method
  @DisplayName("JUnit test for saveEmployee method which throws exception")
  @Test
  public void givenExistingID_whenUpdateThatDoNotExistEmployee_thenThrowsException() {
    ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "No record with such ID");
    given(employeeService.update(employee)).willThrow(responseStatusException);
    org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
      employeeService.update(employee);
    });
    
    verify(employeeRepo, never()).save(any(Employee.class));
  }
}
