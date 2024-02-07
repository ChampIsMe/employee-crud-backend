package com.phil.employeecrudbackend.records.employees;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record EmployeeCreate(
    @Schema(description = "Employee's first name", example = "Philip")
    @NotBlank
    String firstName,
    @Schema(description = "Employee's last name", example = "Okinyi")
    @NotBlank
    String lastName,
    @Schema(description = "Contact number", example = "+27715153701")
    @NotBlank
    String contactNumber,
    @Schema(description = "Email Address", example = "po@example.com")
    @NotBlank
    String email,
    @Schema(description = "Date of birth", example = "2023-11-30")
    @NotBlank
    String dob,
    @Schema(description = "Street Address", example = "Long Street, Apt 404")
    @NotBlank
    String address,
    @Schema(description = "City", example = "Cape Town")
    String city,
    @Schema(description = "Postal code", example = "00100")
    @NotBlank
    String postalCode,
    @Schema(description = "Country", example = "South Africa")
    @NotBlank
    String country) {
}
//todo: Add skills and its doc schema