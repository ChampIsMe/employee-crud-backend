package com.phil.employeecrudbackend.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phil.employeecrudbackend.entities.Employee;
import com.phil.employeecrudbackend.records.employees.EmployeeCreate;
import com.phil.employeecrudbackend.records.employees.EmployeeParams;
import com.phil.employeecrudbackend.records.employees.EmployeeUpdate;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
  ResponseEntity<Map<String, Long>> create(@Valid @RequestBody EmployeeCreate body) {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Employee employee = objectMapper.convertValue(body, Employee.class);
    Long id = employeeService.create(employee);
    return ResponseEntity.status(200).body(Map.of("id", id));
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
  ResponseEntity<List<Employee>> getList(@ParameterObject Pageable pageable, @Nullable @RequestParam EmployeeParams params) {
    //todo: implement filtering with params RequestParam
    List<Employee> employees = employeeService.findAll();
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
    if (!Objects.equals(body.id(), id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inconsistent update IDs. Make sure the ID in the URL path is the same as ID passed or ignore ID in your payload");
    }
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Employee employee = objectMapper.convertValue(body, Employee.class);
    employee.setId(id);
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
