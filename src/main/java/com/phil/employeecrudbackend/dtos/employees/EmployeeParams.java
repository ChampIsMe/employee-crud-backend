package com.phil.employeecrudbackend.dtos.employees;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeParams {
  @Schema(description = "Employee's first name", example = "Philip")
  String firstName;
  @Schema(description = "Employee's last name", example = "Okinyi")
  String lastName;
  @Schema(description = "Email Address", example = "po@example.com")
  @Email(message = "The email is not a valid email.")
  String email;
}

