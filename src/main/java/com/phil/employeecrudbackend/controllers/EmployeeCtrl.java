package com.phil.employeecrudbackend.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phil.employeecrudbackend.dtos.employees.EmployeeCreate;
import com.phil.employeecrudbackend.dtos.employees.EmployeeParams;
import com.phil.employeecrudbackend.dtos.employees.EmployeeUpdate;
import com.phil.employeecrudbackend.entities.Employee;
import com.phil.employeecrudbackend.entities.EmployeeExperience;
import com.phil.employeecrudbackend.entities.Skill;
import com.phil.employeecrudbackend.models.ExperienceSchema;
import com.phil.employeecrudbackend.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "Employees API", description = "CRUD APIs for employees")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/employee")
public class EmployeeCtrl {
  private final EmployeeService employeeService;
  
  private final ObjectMapper objectMapper;
  
  @Operation(
      summary = "Create a new employee",
      description = "Create a employee with th schema provided")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @PostMapping("/create")
  ResponseEntity<Employee> create(@Valid @RequestBody EmployeeCreate body) {
    List<ExperienceSchema> skillsSchemas = body.getSkills();
    body.setSkills(new ArrayList<>());
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Employee employee = objectMapper.convertValue(body, Employee.class);
    List<EmployeeExperience> employeeExperiences = skillsSchemas.stream().map(skillsSchema -> EmployeeExperience.builder()
        .yrs(skillsSchema.getYrs())
        .skill(Skill.builder()
            .id(skillsSchema.getId())
            .name(skillsSchema.getSkill())
            .build())
        .seniority(skillsSchema.getSeniority())
        .build()).collect(Collectors.toList());
    employee.setEmployeeExperiences(employeeExperiences);
    return ResponseEntity.status(200).body(employeeService.create(employee));
  }
  
  @Operation(
      summary = "Retrieve a employee by Id",
      description = "Get a employee object by specifying its id. The response is employee object."
      //todo: Uncomment when auth is implemented
//      security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @GetMapping("/{id}")
  ResponseEntity<Employee> getOne(@PathVariable Long id) {
    Employee employee = employeeService.findById(id);
    return ResponseEntity.status(200).body(employee);
  }
  
  //Todo: remove @SuppressWarnings("unused") once pageable and params are used
  @SuppressWarnings("unused")
  @Operation(
      summary = "Retrieve a list of employees",
      description = "Retrieve a list of employees by query"
      //todo: Uncomment when auth is implemented
//      security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Employee.class)), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @GetMapping("/list")
  ResponseEntity<List<Employee>> getList(@ParameterObject EmployeeParams params) {
    List<Employee> employees = employeeService.findByCustomParams(params);
    return ResponseEntity.status(HttpStatus.OK).body(employees);
  }
  
  @Operation(
      summary = "Update a employee by ID",
      description = "Update a employee by ID"
      //todo: Uncomment when auth is implemented
//      security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @PatchMapping("/update/{id}")
  ResponseEntity<Employee> update(@PathVariable Long id, @Valid @RequestBody EmployeeUpdate body) {
    if (!Objects.equals(body.getId(), id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inconsistent update IDs. Make sure the ID in the URL path is the same as ID passed or ignore ID in your payload");
    }
    List<ExperienceSchema> skillsSchemas = body.getSkills();
    body.setSkills(new ArrayList<>());
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Employee employee = objectMapper.convertValue(body, Employee.class);
    employee.setId(id);
    
    List<EmployeeExperience> employeeExperiences = skillsSchemas.stream().map(skillsSchema -> EmployeeExperience.builder()
        .yrs(skillsSchema.getYrs())
        .skill(Skill.builder()
            .id(skillsSchema.getId())
            .name(skillsSchema.getSkill())
            .build())
        .seniority(skillsSchema.getSeniority())
        .build()).collect(Collectors.toList());
    employee.setEmployeeExperiences(employeeExperiences);
    Employee updatedEmployee = employeeService.update(employee);
    return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
  }
  
  @Operation(
      summary = "Delete a employee by ID",
      description = "Delete a employee by ID"
      //todo: Uncomment when auth is implemented
//      security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
      @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
  @DeleteMapping("delete/{id}")
  ResponseEntity<Map<String, Long>> deleteOne(@PathVariable Long id) {
    employeeService.delete(id);
    return ResponseEntity.status(200).body(Map.of("id", id));
  }
}
