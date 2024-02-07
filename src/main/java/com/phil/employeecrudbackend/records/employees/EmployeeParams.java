package com.phil.employeecrudbackend.records.employees;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmployeeParams(
    @Schema(description = "Employee's first name", example = "Philip")
    String firstName,
    @Schema(description = "Employee's last name", example = "Okinyi")
    String lastName,
    @Schema(description = "Contact number", example = "+27715153701")
    String contactNumber,
    @Schema(description = "Email Address", example = "po@example.com")
    String email,
    @Schema(description = "Date of birth", example = "2023-11-30")
    String dob,
    @Schema(description = "Street Address", example = "Long Street, Apt 404")
    String address,
    @Schema(description = "City", example = "Cape Town")
    String city,
    @Schema(description = "Postal code", example = "00100")
    String postalCode,
    @Schema(description = "Country", example = "South Africa")
    String country) {
}

