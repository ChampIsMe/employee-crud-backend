package com.phil.employeecrudbackend.dtos.employees;

import com.phil.employeecrudbackend.models.ExperienceSchema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public final class EmployeeCreate {
  @Schema(description = "Employee's first name", example = "Philip")
  @NotBlank String firstName;
  @Schema(description = "Employee's last name", example = "Okinyi")
  @NotBlank String lastName;
  @Schema(description = "Contact number", example = "+27715153701")
  @NotBlank String contactNumber;
  @Schema(description = "Email Address", example = "po@example.com")
  @Email(message = "The email is not a valid email.")
  @NotBlank String email;
  @Schema(description = "Date of birth", example = "2023-11-30")
  @NotBlank String dob;
  @Schema(description = "Street Address", example = "Long Street, Apt 404")
  @NotBlank String address;
  @Schema(description = "City", example = "Cape Town")
  String city;
  @Schema(description = "Postal code", example = "00100")
  @NotBlank String postalCode;
  @Schema(description = "Country", example = "South Africa")
  @NotBlank String country;
  @ArraySchema(schema = @Schema(implementation = ExperienceSchema.class))
  @NotEmpty List<ExperienceSchema> skills;
  
}
